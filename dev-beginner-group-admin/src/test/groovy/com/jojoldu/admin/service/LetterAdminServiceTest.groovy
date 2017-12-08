package com.jojoldu.admin.service

import com.jojoldu.admin.dto.LetterAdminRequestDto
import com.jojoldu.beginner.domain.letter.Letter
import com.jojoldu.beginner.domain.letter.LetterRepository
import com.jojoldu.beginner.util.Constants
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

/**
 * Created by jojoldu@gmail.com on 2017. 12. 8.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

@SpringBootTest
class LetterAdminServiceTest extends Specification {

    @Autowired
    LetterAdminService letterAdminService

    @Autowired
    LetterRepository letterRepository

    def cleanup() {
        letterRepository.deleteAll()
    }

    def "newsletter.hbs에 content가 추가되어 메일 전송된다" () {
        given:
        String content = "<h1>11월 2주차</h1>\n" +
                "    <p>안녕하세요?<br>\n" +
                "        11월 2주차 뉴스레터입니다.</p>\n" +
                "    <h3>베스트 기사</h3>\n" +
                "    <p>\n" +
                "        <img src=\"https://s3.ap-northeast-2.amazonaws.com/devbeginner.com/%E1%84%92%E1%85%A2%E1%86%B7%E1%84%90%E1%85%A9%E1%84%85%E1%85%B5%20%E1%84%83%E1%85%B1%E1%84%84%E1%85%AE%E1%86%BC.gif\" alt=\"햄토리 뒤뚱.gif\">\n" +
                "    </p>"

        def subject = "뉴스레터 테스트"
        def dto = LetterAdminRequestDto.builder()
                .subject(subject)
                .sender(Constants.ADMIN_EMAIL)
                .markdown("### 테스트")
                .content(content)
                .build()
        when:
        letterAdminService.saveAndSend(dto)

        then:
        def letters = letterRepository.findAll()
        letters.size() == 1
        letters.get(0).sender == Constants.ADMIN_EMAIL
        letters.get(0).subject == subject
    }
}
