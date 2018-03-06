package com.jojoldu.beginner.batch.migration.decode

import com.jojoldu.beginner.domain.letter.Letter
import com.jojoldu.beginner.domain.letter.LetterRepository
import org.springframework.batch.core.BatchStatus
import org.springframework.batch.core.JobExecution
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.test.JobLauncherTestUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource
import spock.lang.Specification

/**
 * Created by jojoldu@gmail.com on 2018. 3. 6.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

@SpringBootTest
@TestPropertySource(properties = "job.name=letterDecodeBatch")
class LetterDecodeBatchTest extends Specification {

    @Autowired JobLauncherTestUtils jobLauncherTestUtils

    @Autowired LetterRepository letterRepository

    def cleanup() {
        letterRepository.deleteAll()
    }

    def "인코딩된 Letter의 subject가 디코딩되서 반영된다."() {
        given:
        Letter letter = Letter.builder()
                .subject("%5B%EC%B4%88%EB%B3%B4%EA%B0%9C%EB%B0%9C%EC%9E%90%EB%AA%A8%EC%9E%84%5D%202017%EB%85%84%2012%EC%9B%94%204%EC%A3%BC%EC%B0%A8%20%EB%89%B4%EC%8A%A4%EB%A0%88%ED%84%B0")
                .sender("admin@devbeginner.com")
                .build()
        letterRepository.save(letter)

        def jobParameters = new JobParametersBuilder()
                .addDate("version", new Date())
                .toJobParameters()
        when:
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters)

        then:
        jobExecution.getStatus() == BatchStatus.COMPLETED
        Letter updated = letterRepository.findAll().get(0)
        updated.subject == "[초보개발자모임] 2017년 12월 4주차 뉴스레터"
    }

    def "이미 디코딩된 Letter의 subject는 디코딩되지 않는다."() {
        given:
        Letter letter = Letter.builder()
                .subject("[초보개발자모임] 2017년 12월 4주차 뉴스레터")
                .sender("admin@devbeginner.com")
                .build()
        letterRepository.save(letter)

        def jobParameters = new JobParametersBuilder()
                .addDate("version", new Date())
                .toJobParameters()
        when:
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters)

        then:
        jobExecution.getStatus() == BatchStatus.COMPLETED
        Letter updated = letterRepository.findAll().get(0)
        updated.subject == "[초보개발자모임] 2017년 12월 4주차 뉴스레터"
    }
}
