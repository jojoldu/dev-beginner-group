package com.jojoldu.beginner.batch.migration.decode.job;

import com.jojoldu.beginner.batch.core.QuerydslCursorItemReader;
import com.jojoldu.beginner.domain.letter.LetterContent;
import com.jojoldu.beginner.util.Decoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;

import static com.jojoldu.beginner.batch.migration.decode.job.LetterContentDecodeBatch.JOB_NAME;
import static com.jojoldu.beginner.domain.letter.QLetterContent.letterContent;


/**
 * Created by jojoldu@gmail.com on 2018. 3. 6.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

@Slf4j
@Configuration
@ConditionalOnProperty(name = "job.name", havingValue = JOB_NAME)
public class LetterContentDecodeBatch {
    public static final String JOB_NAME = "letterContentDecodeBatch";
    public static final String STEP_PREFIX = JOB_NAME+"Step_";

    @Value("${chunkSize:1000}")
    private int chunkSize;

    @Autowired private EntityManagerFactory entityManagerFactory;
    @Autowired private JobBuilderFactory jobBuilderFactory;
    @Autowired private StepBuilderFactory stepBuilderFactory;
    @Autowired private QuerydslCursorItemReader<LetterContent> letterContentReader;

    @Bean
    public Job job() {
        return jobBuilderFactory.get(JOB_NAME)
                .start(decodeLetterContentStep())
                .build();
    }

    @Bean
    @JobScope
    public Step decodeLetterContentStep() {
        return stepBuilderFactory.get(STEP_PREFIX+"letter")
                .<LetterContent, LetterContent>chunk(chunkSize)
                .reader(letterContentReader)
                .processor(LetterContentProcessor())
                .writer(LetterContentWriter())
                .build();
    }

    @Bean
    @StepScope
    public QuerydslCursorItemReader<LetterContent> letterContentReader() {
        return new QuerydslCursorItemReader<>(entityManagerFactory, chunkSize, queryFactory -> queryFactory
                .selectFrom(letterContent));
    }

    private ItemProcessor<LetterContent, LetterContent> LetterContentProcessor() {
        return  item -> {
            String decodedTitle = Decoder.decode(item.getTitle());
            String decodedMarkdown = Decoder.decode(item.getContentMarkdown());
            item.updateTitleAndMarkdown(decodedTitle, decodedMarkdown);
            return item;
        };
    }

    private JpaItemWriter<LetterContent> LetterContentWriter() {
        JpaItemWriter<LetterContent> itemWriter = new JpaItemWriter<>();
        itemWriter.setEntityManagerFactory(entityManagerFactory);
        return itemWriter;
    }
}
