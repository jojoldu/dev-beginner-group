package com.jojoldu.admin.repository.lettercontent

import com.jojoldu.admin.dto.letter.save.LetterContentResponseDto
import com.jojoldu.beginner.domain.letter.Letter
import com.jojoldu.beginner.domain.letter.LetterContent
import com.jojoldu.beginner.domain.letter.LetterRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import spock.lang.Specification

/**
 * Created by jojoldu@gmail.com on 2018. 3. 8.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

@SpringBootTest
class LetterContentWebRepositoryTest extends Specification {

    @Autowired
    LetterRepository letterRepository

    @Autowired
    LetterContentWebRepository repository

    void setup() {
        repository.deleteAllInBatch()
    }

    void cleanup() {
        letterRepository.deleteAllInBatch()
        repository.deleteAllInBatch()
    }

    def "LetterContentMap이 없어도 조회된다."() {
        given:
        createContent("1", "1", "1", "1", "1")
        createContent("2", "2", "2", "2", "2")

        Pageable pageable = new PageRequest(0,20, Sort.Direction.DESC, "id")

        when:
        List<LetterContentResponseDto> result = repository.findLetterContentDto(pageable)

        then:
        result.size() == 2
        result.get(0).getTitle() == "2"
        result.get(1).getTitle() == "1"
    }

    def "LetterContentMap과 Letter가 있으면 같이 조회된다."() {
        given:
        LetterContent content1 = createContent("1", "1", "1", "1", "1")
        LetterContent content2 = createContent("2", "2", "2", "2", "2")
        createLetter(Arrays.asList(content1, content2))
        createContent("3", "3", "3", "3", "3")

        Pageable pageable = new PageRequest(0,20, Sort.Direction.DESC, "id")

        when:
        List<LetterContentResponseDto> result = repository.findLetterContentDto(pageable)

        then:
        result.size() == 3
        result.get(0).getTitle() == "3"
        result.get(1).getTitle() == "2"
        result.get(2).getTitle() == "1"
        result.get(2).getLetterId() == 1L
    }

    def "Paging 조회 된다."() {
        given:
        for(int i=0;i<100;i++){
            createContent(i+"a", i+"a", i+"a", i+"a", i+"a")
        }

        Pageable pageable = new PageRequest(2,20, Sort.Direction.DESC, "id")

        when:
        List<LetterContentResponseDto> result = repository.findLetterContentDto(pageable)

        then:
        result.size() == 20
        result.get(0).getTitle() == "59a"
        result.get(19).getTitle() == "40a"
    }

    LetterContent createContent(String title, String content, String contentMarkdown, String link, String img) {
        return repository.save(LetterContent.builder()
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
