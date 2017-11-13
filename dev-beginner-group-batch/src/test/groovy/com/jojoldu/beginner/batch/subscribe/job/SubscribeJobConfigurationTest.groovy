package com.jojoldu.beginner.batch.subscribe.job

import com.jojoldu.beginner.domain.subscriber.Subscriber
import com.jojoldu.beginner.domain.subscriber.SubscriberRepository
import org.springframework.batch.core.BatchStatus
import org.springframework.batch.core.JobExecution
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.test.JobLauncherTestUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource
import spock.lang.Specification

import java.time.LocalDate

/**
 * Created by jojoldu@gmail.com on 2017. 11. 11.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */
@SpringBootTest
@TestPropertySource(properties = "job.name=subscribeBatch")
class SubscribeJobConfigurationTest extends Specification {

    String content = "오늘 playnode 2017이 개최되었습니다!\n" +
            "전 이제 nodejs에 관심이 좀 멀어져서 ㅠㅠ 가지 않았지만,\n" +
            "구피님께서 다녀오셔서 후기를 남겨주셨습니다.\n" +
            "(읽으면서 타입스크립트 내용은 공감이 되면서 더욱 정적언어를 사랑하게 된다는...)\n" +
            "javascript에 관심이 많으신 분들은 한번 읽어보셨으면 합니다\n" +
            "https://wckhg89.github.io/archivers/playnode2017"

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils

    @Autowired
    private SubscriberRepository subscriberRepository

    def "메일발송 배치 " () {
        given:
        subscriberRepository.save(new Subscriber("jojoldu@gmail.com", LocalDate.of(2017,11,13)))

        JobParametersBuilder builder = new JobParametersBuilder()
                .addString("templateId", "1")
                .addString("startDate", "20171106")
                .addString("endDate", "20171112")
                .addString("from", "admin@devbeginner.com")
                .addString("version", LocalDate.now().toString())

        when:
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(builder.toJobParameters())

        then:
        jobExecution.status == BatchStatus.COMPLETED
    }
}
