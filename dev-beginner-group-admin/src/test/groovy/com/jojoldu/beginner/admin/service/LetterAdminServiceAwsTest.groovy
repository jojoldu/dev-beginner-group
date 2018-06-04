package com.jojoldu.beginner.admin.service

import com.jojoldu.beginner.admin.dto.LetterAdminSaveRequestDto
import com.jojoldu.beginner.admin.dto.mail.MailSendDto
import com.jojoldu.beginner.domain.letter.LetterContent
import com.jojoldu.beginner.domain.letter.LetterContentRepository
import com.jojoldu.beginner.domain.letter.LetterRepository
import com.jojoldu.beginner.domain.subscriber.Subscriber
import com.jojoldu.beginner.domain.subscriber.SubscriberRepository
import com.jojoldu.beginner.mail.aws.Sender
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.SpyBean
import spock.lang.Ignore
import spock.lang.Specification

/**
 * Created by jojoldu@gmail.com on 2017. 12. 8.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

@SpringBootTest
class LetterAdminServiceAwsTest extends Specification {

    @Autowired
    LetterRepository letterRepository

    @Autowired
    SubscriberRepository subscriberRepository

    @Autowired
    LetterContentRepository letterContentRepository

    @SpyBean
    LetterAdminService letterAdminService

    @SpyBean
    Sender sender

    def setup() {
        subscriberRepository.deleteAll()
        letterContentRepository.deleteAll()
    }

    def cleanup() {
        subscriberRepository.deleteAll()
        letterRepository.deleteAll()
        letterContentRepository.deleteAll()
    }

    @Ignore
    def "[통합] 지정한 뉴스레터 ID로 메일 발송한다" () {
        given:
        String img = "https://s3.ap-northeast-2.amazonaws.com/devbeginner.com/%E1%84%8E%E1%85%A9%E1%84%80%E1%85%A2%E1%84%86%E1%85%A9.png"
        def subject = "뉴스레터 테스트"
        def email = "jojoldu@gmail.com"
        createSubscriber(email)

        LetterContent savedContent1 = createContent(
                "뉴스레터#1",
                "초보개발자모임 첫 뉴스레터 발송을 축하하는 의미",
                "초보개발자모임 첫 뉴스레터 발송을 축하하는 의미",
                "http://jojoldu.tistory.com/",
                img)

        LetterContent savedContent2 = createContent(
                "뉴스레터#2",
                "초보개발자모임 페이스북 페이지 링크 소개",
                "초보개발자모임 페이스북 페이지 링크 소개",
                "https://www.facebook.com/devbeginner/",
                img)

        letterAdminService.saveLetter(LetterAdminSaveRequestDto.builder()
                .subject(subject)
                .sender("admin@devbeginner.com")
                .contentIds(Arrays.asList(savedContent1.id, savedContent2.id))
                .build())

        when:
        List<MailSendDto> mails = letterAdminService.createSendMailList(1L, null)

        then:
        mails.size() == 1
        mails.get(0).email == email
        mails.get(0).subject == subject
    }

    private LetterContent createContent(String title, String content, String contentMarkdown, String link, String img) {
        return letterContentRepository.save(LetterContent.builder()
                .title(title)
                .content(content)
                .contentMarkdown(contentMarkdown)
                .link(link)
                .img(img)
                .build())
    }

    private void createSubscriber(String email){
        Subscriber subscriber = Subscriber.builder()
                .email(email)
                .certifyMessage("aaa")
                .build()

        subscriber.certify()
        subscriberRepository.save(subscriber)
    }
}
