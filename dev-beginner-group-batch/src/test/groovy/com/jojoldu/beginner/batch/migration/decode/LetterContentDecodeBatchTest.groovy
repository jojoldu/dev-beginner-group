package com.jojoldu.beginner.batch.migration.decode

import com.jojoldu.beginner.domain.letter.LetterContent
import com.jojoldu.beginner.domain.letter.LetterContentRepository
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
@TestPropertySource(properties = "job.name=letterContentDecodeBatch")
class LetterContentDecodeBatchTest extends Specification {

    @Autowired JobLauncherTestUtils jobLauncherTestUtils

    @Autowired LetterContentRepository letterContentRepository

    def cleanup() {
        letterContentRepository.deleteAll()
    }

    def "인코딩된 LetterContent의 subject가 디코딩되서 반영된다."() {
        given:
        LetterContent letterContent = LetterContent.builder()
                .title("%EB%B6%88%ED%96%89%ED%95%9C%20%EC%B2%9C%EA%B5%AD")
                .contentMarkdown("%EC%A3%BC%EB%B3%80%EC%9D%98%20%EB%A7%8E%EC%9D%80%20%EB%B6%84%EB%93%A4%EC%9D%B4%20%EC%A2%8B%EC%9D%80%20%ED%9A%8C%EC%82%AC%EB%A5%BC%20%EC%B7%A8%EC%97%85%ED%95%98%EA%B3%A0%EB%82%98%EC%84%9C%20%EC%A0%84%EA%B3%BC%20%EB%8B%A4%EB%A5%B4%EA%B2%8C%20%ED%96%89%EB%8F%99%ED%95%98%EB%8A%94%EA%B1%B8%20%EC%A2%85%EC%A2%85%20%EB%B3%B4%EA%B2%8C%20%EB%90%A9%EB%8B%88%EB%8B%A4.%0A%EC%98%86%EC%97%90%EC%84%9C%20%EB%B3%B4%EB%A9%B4%EC%84%9C%20%EC%83%9D%EA%B0%81%EB%A7%8C%20%ED%95%98%EB%8D%98%20%EA%B2%83%EB%93%A4%EC%9D%84%20%EA%B8%80%EB%A1%9C%20%EB%82%A8%EA%B2%BC%EC%8A%B5%EB%8B%88%EB%8B%A4.%0A%0A%EA%B2%BD%EC%9A%B0%EC%97%90%20%EB%94%B0%EB%9D%BC%EC%84%9C%20%EB%B6%88%ED%8E%B8%ED%95%98%EC%8B%A4%EC%88%98%EB%8F%84%20%EC%9E%88%EC%9C%BC%EC%8B%9C%EA%B2%A0%EC%A7%80%EB%A7%8C%2C%20%EA%B7%B8%EB%9E%98%EB%8F%84%20%EB%8F%84%EC%9B%80%EC%9D%B4%20%EB%90%98%EC%8B%9C%EA%B8%B8%20%EB%B0%94%EB%9E%8D%EB%8B%88%EB%8B%A4.")
                .link("http://localhost")
                .img("aaa")
                .content("aaa")
                .build()
        letterContentRepository.save(letterContent)

        def jobParameters = new JobParametersBuilder()
                .addDate("version", new Date())
                .toJobParameters()
        when:
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters)

        then:
        jobExecution.getStatus() == BatchStatus.COMPLETED
        LetterContent updated = letterContentRepository.findAll().get(0)
        updated.title == "불행한 천국"
        updated.contentMarkdown == "주변의 많은 분들이 좋은 회사를 취업하고나서 전과 다르게 행동하는걸 종종 보게 됩니다.\n" +
                "옆에서 보면서 생각만 하던 것들을 글로 남겼습니다.\n" +
                "\n" +
                "경우에 따라서 불편하실수도 있으시겠지만, 그래도 도움이 되시길 바랍니다."
    }

}
