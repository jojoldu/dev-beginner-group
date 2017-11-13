package com.jojoldu.beginner.batch.subscribe.job;

import com.jojoldu.beginner.batch.core.QuerydslCursorItemReader;
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
    private Sender sender;
    @Autowired
    private HandlebarsFactory handlebarsFactory;

    /**
     * 1. 메일 양식 생성
     * 2. 구독자 리스트를 불러 chunk 단위로 메일 발송
     *
     */

    @Bean
    public Job job() {
        return jobBuilderFactory.get(JOB_NAME)
                .start(createContent(null, null, null))
                .next(send())
                .build();
    }

    @Bean
    @JobScope
    public Step createContent(@Value("#{jobParameters[templateId]}") String templateId,
                              @Value("#{jobParameters[startDate]}") String startDate,
                              @Value("#{jobParameters[endDate]}") String endDate) {

        return stepBuilderFactory.get(STEP_NAME+"_createContent")
                .tasklet((contribution, chunkContext) -> {
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
                .writer(writer(null, null))
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
    public ItemWriter<String> writer(
            @Value("#{jobParameters[from]}") String from,
            @Value("#{jobParameters[templateId]}") String templateId) {
        return items -> {
            SenderDto senderDto = SenderDto.builder()
                    .from(from)
                    .subject("테스트")
                    .content("테스트")
                    .build();

            for (String item : items) {
                senderDto.addTo(item);
            }

            sender.send(senderDto);
        };
    }

}
