package com.jojoldu.admin.dto

import com.jojoldu.beginner.domain.letter.LetterContent
import spock.lang.Specification

/**
 * Created by jojoldu@gmail.com on 2017. 12. 18.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

class MailLinkRedirectDtoTest extends Specification {

    def "LetterContent의 링크는 DTO에서 통계링크로 전환된다." () {
        given:
        def link = "http://jojoldu.tistory.com"
        def baseUrl = "http://localhost:8090"
        LetterContent letterContent = new LetterContent()
        letterContent.id = 2L
        letterContent.link = link

        when:
        MailLinkRedirectDto dto = MailLinkRedirectDto.builder()
                .subscriberId(1L)
                .baseUrl(baseUrl)
                .letterContent(letterContent)
                .build()
        then:
        dto.link == baseUrl+"/mail/statistics/link-click?subscriberId=1&letterContentId=2&redirectUrl="+link
    }
}
