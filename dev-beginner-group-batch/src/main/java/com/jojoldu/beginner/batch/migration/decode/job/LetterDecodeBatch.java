package com.jojoldu.beginner.batch.migration.decode.job;

import com.jojoldu.beginner.batch.core.QuerydslCursorItemReader;
import com.jojoldu.beginner.domain.letter.Letter;
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

import static com.jojoldu.beginner.batch.migration.decode.job.LetterDecodeBatch.JOB_NAME;
import static com.jojoldu.beginner.domain.letter.QLetter.letter;


/**
 * Created by jojoldu@gmail.com on 2018. 3. 6.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

@Slf4j
@Configuration
@ConditionalOnProperty(name = "job.name", havingValue = JOB_NAME)
public class LetterDecodeBatch {
    public static final String JOB_NAME = "letterDecodeBatch";
    public static final String STEP_PREFIX = JOB_NAME+"Step_";

    @Value("${chunkSize:1000}")
    private int chunkSize;

    @Autowired private EntityManagerFactory entityManagerFactory;
    @Autowired private JobBuilderFactory jobBuilderFactory;
    @Autowired private StepBuilderFactory stepBuilderFactory;
    @Autowired private QuerydslCursorItemReader<Letter> letterReader;

    @Bean
    public Job job() {
        return jobBuilderFactory.get(JOB_NAME)
                .start(decodeLetterStep())
                .build();
    }

    @Bean
    @JobScope
    public Step decodeLetterStep() {
        return stepBuilderFactory.get(STEP_PREFIX+"letter")
                .<Letter, Letter>chunk(chunkSize)
                .reader(letterReader)
                .processor(letterProcessor())
                .writer(letterWriter())
                .build();
    }

    @Bean
    @StepScope
    public QuerydslCursorItemReader<Letter> letterReader() {
        return new QuerydslCursorItemReader<>(entityManagerFactory, chunkSize, queryFactory -> queryFactory
                .selectFrom(letter));
    }

    private ItemProcessor<Letter, Letter> letterProcessor() {
        return  item -> {
            String decodedSubject = Decoder.decode(item.getSubject());
            item.updateSubject(decodedSubject);
            return item;
        };
    }

    private JpaItemWriter<Letter> letterWriter() {
        JpaItemWriter<Letter> itemWriter = new JpaItemWriter<>();
        itemWriter.setEntityManagerFactory(entityManagerFactory);
        return itemWriter;
    }
}
