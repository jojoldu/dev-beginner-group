package com.jojoldu.beginner.web.repository.letter

import com.jojoldu.beginner.domain.letter.Letter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import spock.lang.Specification

/**
 * Created by jojoldu@gmail.com on 2018. 3. 9.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

@SpringBootTest
class LetterWebRepositoryTest extends Specification {

    @Autowired
    LetterWebRepository letterWebRepository

    void setup() {
        letterWebRepository.deleteAllInBatch()
    }

    void cleanup() {
        letterWebRepository.deleteAllInBatch()
    }

    def "archiveUrl이 없으면 조회되지 않는다."() {
        given:
        createLetter("subject1", "url1")
        createLetter("subject2", "url2")
        createLetter("subject3", null)
        Pageable request = new PageRequest(0, 20, Sort.Direction.DESC, "id")

        when:
        def result = letterWebRepository.findLetterArchiveDto(request)

        then:
        result.size() == 2
        result.get(0).getSubject() == "subject2"
        result.get(1).getSubject() == "subject1"
    }

    Letter createLetter(String subject, String archiveUrl){
        def letter = Letter.builder()
                .subject(subject)
                .archiveUrl(archiveUrl)
                .sender("admin@devbeginner.com")
                .build()
        return letterWebRepository.save(letter)
    }
}
