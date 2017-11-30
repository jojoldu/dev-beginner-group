package com.jojoldu.beginner.batch.subscribe.job

import com.github.jknack.handlebars.Template
import com.jojoldu.beginner.mail.aws.Sender
import com.jojoldu.beginner.mail.aws.SenderDto
import com.jojoldu.beginner.mail.template.HandlebarsFactory
import org.assertj.core.util.Lists
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource
import spock.lang.Specification

/**
 * Created by jojoldu@gmail.com on 2017. 11. 11.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

@SpringBootTest
@TestPropertySource(properties = "job.name=subscribeBatch")
class MailSendTest extends Specification {

    @Autowired
    Sender sender

    @Autowired
    HandlebarsFactory handlebarsFactory

    def "Batch에서 Mail모듈을 사용하여 메일 전송"() {
//        Template template = handlebarsFactory.get("WeeklyLetter")
//        String content = template.apply("")
//
//        given:
//        SenderDto dto = SenderDto.builder()
//                .from("admin@devbeginner.com")
//                .to(Lists.newArrayList("jojoldu@gmail.com"))
//                .subject("테스트")
//                .content(content)
//                .build()
//
//        when:
//        sender.send(dto)
//
//        then:
//        println "전송되었습니다."
    }


}
