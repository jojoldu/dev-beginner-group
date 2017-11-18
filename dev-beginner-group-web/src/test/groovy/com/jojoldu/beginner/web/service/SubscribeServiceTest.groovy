package com.jojoldu.beginner.web.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

/**
 * Created by jojoldu@gmail.com on 2017. 11. 16.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

@SpringBootTest
class SubscribeServiceTest extends Specification {

    @Autowired
    SubscribeService subscribeService

    def "구독하면 해당 이메일로 인증 메일이 도착함" () {
        given:
        String email = "admin@devbeginner.com"

        when:
        boolean result = subscribeService.saveWaitingList(email)

        then:
        result == true
    }
}
