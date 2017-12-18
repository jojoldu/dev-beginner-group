package com.jojoldu.beginner.web.service

import com.jojoldu.beginner.domain.subscriber.Subscriber
import com.jojoldu.beginner.domain.subscriber.SubscriberRepository
import com.jojoldu.beginner.mail.aws.Sender
import com.jojoldu.beginner.web.exception.InvalidParameterException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.SpyBean
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

    @Autowired
    SubscriberRepository subscriberRepository

    @SpyBean
    Sender sender

    def cleanup() {
       subscriberRepository.deleteAll()
    }

    def "구독하면 해당 이메일로 인증 메일이 도착함" () {
        given:
        String email = "jojoldu@gmail.com"

        when:
        subscribeService.saveWaitingList(email)

        then:
        true
    }

    def "인증메일의 링크를 클릭하면 구독 완료됨" () {
        given:
        String email = "admin@devbeginner.com"
        String message = "devbeginner"
        subscriberRepository.save(new Subscriber(email, message))

        when:
        subscribeService.certifyComplete(email, message)

        then:
        Subscriber subscriber = subscriberRepository.findAll().get(0)
        subscriber.isCertified() == true
    }

    def "인증메일의 링크가 잘못되면 오류 발생" () {
        given:
        String email = "admin@devbeginner.com"
        String message = "devbeginner"
        subscriberRepository.save(new Subscriber(email, message))

        when:
        subscribeService.certifyComplete(email, "aa")

        then:
        def e = thrown(InvalidParameterException.class)
    }
}
