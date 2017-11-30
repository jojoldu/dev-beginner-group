package com.jojoldu.beginner.batch.subscribe.job;

import com.jojoldu.beginner.batch.core.QuerydslCursorItemReader;
import com.jojoldu.beginner.domain.letter.Letter;
import com.jojoldu.beginner.domain.letter.LetterRepository;
import com.jojoldu.beginner.domain.posts.PostsRepository;
import com.jojoldu.beginner.domain.subscriber.Subscriber;
import com.jojoldu.beginner.mail.aws.Sender;
import com.jojoldu.beginner.mail.aws.SenderDto;
import com.jojoldu.beginner.mail.template.HandlebarsFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;

import java.util.ArrayList;

import static com.jojoldu.beginner.batch.subscribe.job.SubscribeJobConfiguration.JOB_NAME;
import static com.jojoldu.beginner.domain.subscriber.QSubscriber.subscriber;

/**
 * Created by jojoldu@gmail.com on 2017. 11. 9.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

@Slf4j
@Configuration
@ConditionalOnProperty(name = "job.name", havingValue = JOB_NAME)
public class SubscribeJobConfiguration {
    public static final String JOB_NAME = "subscribeBatch";
    private static final String STEP_NAME = JOB_NAME+"Step";

    @Value("${chunkSize:1000}")
    private int chunkSize;

    @Autowired
    private EntityManagerFactory entityManagerFactory;
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private LetterRepository letterRepository;

    @Autowired
    private Sender sender;

    @Autowired
    private MailCacheComponent mailCacheComponent;

    private static String START_DATE_PARAM = null;
    private static String END_DATE_PARAM = null;
    private static String FROM_PARAM = null;
    private static String LETTER_ID_PARAM = null;

    /**
     * 1. 메일 양식 생성
     * 2. 구독자 리스트를 불러 chunk 단위로 메일 발송
     *
     */

    @Bean
    public Job job() {
        return jobBuilderFactory.get(JOB_NAME)
                .start(createContent(LETTER_ID_PARAM))
                .next(start(LETTER_ID_PARAM))
                .next(send())
                .next(end(LETTER_ID_PARAM))
                .build();
    }

    @Bean
    @JobScope
    public Step createContent(
            @Value("#{jobParameters[letterId]}") String letterId) {
        return stepBuilderFactory.get(STEP_NAME+"_createContent")
                .tasklet((contribution, chunkContext) -> {
                    Letter letter = letterRepository.findOne(Long.valueOf(letterId));
                    mailCacheComponent.init(letter.getSubject(), letter.getContent(), letter.getSender());
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    @JobScope
    public Step start(@Value("#{jobParameters[letterId]}") String letterId) {
        return stepBuilderFactory.get(STEP_NAME+"_start")
                .tasklet((contribution, chunkContext) -> {
                    Letter letter = letterRepository.findOne(Long.valueOf(letterId));
                    letter.sending();
                    letterRepository.save(letter);
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    @JobScope
    public Step end(@Value("#{jobParameters[letterId]}") String letterId) {
        return stepBuilderFactory.get(STEP_NAME+"_end")
                .tasklet((contribution, chunkContext) -> {
                    Letter letter = letterRepository.findOne(Long.valueOf(letterId));
                    letter.sendComplete();
                    letterRepository.save(letter);
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    @JobScope
    public Step send() {
        return stepBuilderFactory.get(STEP_NAME)
                .<Subscriber, String>chunk(chunkSize)
                .reader(reader)
                .processor(processor())
                .writer(writer())
                .build();
    }

    @Autowired
    private QuerydslCursorItemReader<Subscriber> reader;

    @Bean
    public QuerydslCursorItemReader<Subscriber> reader() {
        return new QuerydslCursorItemReader<>(entityManagerFactory, chunkSize, queryFactory -> queryFactory
                .selectFrom(subscriber)
                .where(subscriber.active.eq(true)
                        .and(subscriber.certified.eq(true))));

    }

    private ItemProcessor<Subscriber, String> processor() {
        return Subscriber::getEmail;
    }

    @Bean
    @StepScope
    public ItemWriter<String> writer() {
        return items -> {
            SenderDto senderDto = SenderDto.builder()
                    .from(mailCacheComponent.getFrom())
                    .subject(mailCacheComponent.getSubject())
                    .content(mailCacheComponent.getContent())
                    .to(new ArrayList<>(items))
                    .build();

            sender.send(senderDto);
        };
    }

}
