package com.jojoldu.admin.service

import com.jojoldu.admin.dto.LetterAdminRequestDto
import com.jojoldu.beginner.domain.letter.Letter
import com.jojoldu.beginner.domain.letter.LetterContent
import com.jojoldu.beginner.domain.letter.LetterContentRepository
import com.jojoldu.beginner.domain.letter.LetterRepository
import com.jojoldu.beginner.mail.aws.Sender
import com.jojoldu.beginner.mail.aws.SenderDto
import org.junit.Ignore
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.SpyBean
import spock.lang.Specification

import static org.mockito.Matchers.any
import static org.mockito.Mockito.doReturn
import static org.mockito.Mockito.times
import static org.mockito.Mockito.verify

/**
 * Created by jojoldu@gmail.com on 2017. 12. 8.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

@SpringBootTest
class LetterAdminServiceTest extends Specification {

    @Autowired
    LetterRepository letterRepository

    @Autowired
    LetterContentRepository contentRepository

    @SpyBean
    LetterAdminService letterAdminService

    @SpyBean
    Sender sender

    def cleanup() {
        letterRepository.deleteAll()
        contentRepository.deleteAll()
    }

    def "newsletter.hbs에 content가 추가되어 메일 전송된다" () {
        given:
        String img = "https://s3.ap-northeast-2.amazonaws.com/devbeginner.com/%E1%84%8E%E1%85%A9%E1%84%80%E1%85%A2%E1%84%86%E1%85%A9.png"
        def subject = "뉴스레터 테스트"
        def spyLetter = Spy(Letter.builder()
                .subject(subject)
                .build())

        spyLetter.getContentEntities() >> Arrays.asList(
                LetterContent.builder()
                        .title("뉴스레터#1")
                        .content("초보개발자모임 첫 뉴스레터 발송을 축하하는 의미 <br> 매주 월요일 오전에 지난주에 있었던 개발 이야기를 전달해드립니다.")
                        .contentMarkdown("초보개발자모임 첫 뉴스레터 발송을 축하하는 의미")
                        .link("http://jojoldu.tistory.com/")
                        .img(img)
                        .build(),
                LetterContent.builder()
                        .title("뉴스레터#2")
                        .content("초보개발자모임 페이스북 페이지 링크 소개")
                        .contentMarkdown("초보개발자모임 페이스북 페이지 링크 소개")
                        .link("https://www.facebook.com/devbeginner/")
                        .img(img)
                        .build(),
        )

        def requestDto = LetterAdminRequestDto.builder()
                .subject("테스트")
                .sender("admin@devbeginner.com")
                .build()

        doReturn(spyLetter)
                .when(letterAdminService)
                .createLetter(requestDto)

        when:
        letterAdminService.saveAndSend(requestDto)

        then:
        verify(sender, times(1)).send(any(SenderDto.class))
    }

    def "[통합] newsletter.hbs에 content가 추가되어 메일 전송된다" () {
        given:
        String img = "https://s3.ap-northeast-2.amazonaws.com/devbeginner.com/%E1%84%8E%E1%85%A9%E1%84%80%E1%85%A2%E1%84%86%E1%85%A9.png"

        LetterContent savedContent1 = contentRepository.save(LetterContent.builder()
                .title("뉴스레터#1")
                .content("초보개발자모임 첫 뉴스레터 발송을 축하하는 의미 <br> 매주 월요일 오전에 지난주에 있었던 개발 이야기를 전달해드립니다.")
                .contentMarkdown("초보개발자모임 첫 뉴스레터 발송을 축하하는 의미")
                .link("http://jojoldu.tistory.com/")
                .img(img)
                .build())

        LetterContent savedContent2 = contentRepository.save(LetterContent.builder()
                .title("뉴스레터#2")
                .content("초보개발자모임 페이스북 페이지 링크 소개")
                .contentMarkdown("초보개발자모임 페이스북 페이지 링크 소개")
                .link("https://www.facebook.com/devbeginner/")
                .img(img)
                .build())


        def requestDto = LetterAdminRequestDto.builder()
                .subject("뉴스레터 테스트")
                .sender("admin@devbeginner.com")
                .contentIds(Arrays.asList(savedContent1.id, savedContent2.id))
                .build()

        when:
        letterAdminService.saveAndSend(requestDto)

        then:
        verify(sender, times(1)).send(any(SenderDto.class))
    }
}
