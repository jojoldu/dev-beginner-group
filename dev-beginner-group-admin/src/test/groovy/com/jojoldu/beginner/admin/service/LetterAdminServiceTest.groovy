package com.jojoldu.beginner.admin.service

import com.jojoldu.beginner.admin.dto.LetterPageRequestDto
import com.jojoldu.beginner.domain.letter.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

/**
 * Created by jojoldu@gmail.com on 2018. 3. 7.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

@SpringBootTest
class LetterAdminServiceTest extends Specification {

    @Autowired
    LetterContentMapRepository mapRepository

    @Autowired
    LetterAdminService letterAdminService

    @Autowired
    LetterRepository letterRepository

    @Autowired
    LetterContentRepository letterContentRepository

    void setup() {
        deleteAll()
    }

    void cleanup() {
        deleteAll()
    }

    void deleteAll() {
        mapRepository.deleteAllInBatch()
        letterContentRepository.deleteAllInBatch()
        letterRepository.deleteAllInBatch()
    }

    def "content 조회시 letterId가 포함된다."() {
        given:
        LetterContent content1 = createContent("title1", "content1", "markdown1", "link1", "img1")
        LetterContent content2 = createContent("title2", "content2", "markdown2", "link2", "img2")
        LetterContent content3 = createContent("title3", "content3", "markdown3", "link3", "img3")
        createLetter(Arrays.asList(content1, content2, content3))

        LetterPageRequestDto request  = new LetterPageRequestDto(0)

        when:
        def responses = letterAdminService.findByPageable(request)

        then:
        responses.size() == 3
        responses.get(0).getLetterId() >= 1L
        responses.get(1).getLetterId() >= 1L
        responses.get(2).getLetterId() >= 1L
    }

    LetterContent createContent(String title, String content, String contentMarkdown, String link, String img) {
        return letterContentRepository.save(LetterContent.builder()
                .title(title)
                .content(content)
                .contentMarkdown(contentMarkdown)
                .link(link)
                .img(img)
                .build())
    }

    Letter createLetter(List<LetterContent> letterContents){
        return letterRepository.save(Letter.builder()
                .subject("2018년 3월 1주차")
                .sender("admin@devbeginner.com")
                .archiveUrl("https://aws.s3.com")
                .letterContents(letterContents)
                .build())
    }
}
