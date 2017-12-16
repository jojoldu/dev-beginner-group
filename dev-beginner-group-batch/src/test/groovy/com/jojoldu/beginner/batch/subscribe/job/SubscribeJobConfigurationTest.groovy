package com.jojoldu.beginner.batch.subscribe.job

import com.jojoldu.beginner.domain.letter.Letter
import com.jojoldu.beginner.domain.letter.LetterRepository
import com.jojoldu.beginner.domain.letter.LetterStatus
import com.jojoldu.beginner.domain.subscriber.Subscriber
import com.jojoldu.beginner.domain.subscriber.SubscriberRepository
import org.springframework.batch.core.BatchStatus
import org.springframework.batch.core.JobExecution
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.test.JobLauncherTestUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource
import spock.lang.Ignore
import spock.lang.Specification

/**
 * Created by jojoldu@gmail.com on 2017. 11. 11.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */
@SpringBootTest
@TestPropertySource(properties = "job.name=subscribeBatch")
@Ignore
class SubscribeJobConfigurationTest extends Specification {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils

    @Autowired
    private SubscriberRepository subscriberRepository

    @Autowired
    private LetterRepository letterRepository

    def cleanup () {
        subscriberRepository.deleteAll()
        letterRepository.deleteAll()
    }

    @Ignore
    def "정기 메일 발송" () {
        given:
        def message = "aa"
        def subscriber = new Subscriber("jojoldu@gmail.com", message)
        subscriber.certify()
        subscriberRepository.save(subscriber)
        letterRepository.save(Letter.builder()
                .subject("안녕하세요? 11월 1주차 정기메일입니다.")
                .sender("admin@devbeginner.com")
                .build())

        def jobParameters = new JobParametersBuilder()
                .addString("letterId", "1")
                .addDate("version", new Date())
                .toJobParameters()

        when:
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters)

        then:
        jobExecution.status == BatchStatus.COMPLETED
        Letter letter = letterRepository.findOne(1L)
        letter.status == LetterStatus.COMPLETE

    }
}
