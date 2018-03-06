package com.jojoldu.beginner.mail.aws

import org.assertj.core.util.Lists
import spock.lang.Ignore
import spock.lang.Specification

/**
 * Created by jojoldu@gmail.com on 2017. 11. 10.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

class SenderTest extends Specification {

    Sender sender = new Sender()

    @Ignore
    def "메일 발송 테스트" () {
        given:
        SenderDto dto = SenderDto.builder()
                .from("admin@devbeginner.com")
                .to(Lists.newArrayList("jojoldu@gmail.com"))
                .subject("테스트")
                .content("안녕하세요")
                .build()

        when:
        sender.send(dto)

        then:
        println "전송되었습니다."
    }
}
