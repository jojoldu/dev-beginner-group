package com.jojoldu.beginner.domain.letter

import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import spock.lang.Specification

/**
 * Created by jojoldu@gmail.com on 2017. 11. 25.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

@SpringBootTest
class LetterRepositoryTest extends Specification {

    @Autowired
    LetterRepository letterRepository

    def cleanup() {
        letterRepository.deleteAll()
    }

    def "큰 타입의 문자열도 content에 저장될 수 있다." () {
        given:
        Letter letter = Letter.builder()
                .sender("admin@devbeginner.com")
                .subject("테스트")
                .content("%3Ch1%3E11%EC%9B%94%202%EC%A3%BC%EC%B0%A8%3C%2Fh1%3E%0A%3Cp%3E%EC%95%88%EB%85%95%ED%95%98%EC%84%B8%EC%9A%94%3F%3Cbr%3E%0A11%EC%9B%94%202%EC%A3%BC%EC%B0%A8%20%EB%89%B4%EC%8A%A4%EB%A0%88%ED%84%B0%EC%9E%85%EB%8B%88%EB%8B%A4.%3C%2Fp%3E%0A%3Cp%3E%3Cimg%20src%3D%22https%3A%2F%2Fs3.ap-northeast-2.amazonaws.com%2Fdevbeginner.com%2F%25E1%2584%2592%25E1%2585%25A2%25E1%2586%25B7%25E1%2584%2590%25E1%2585%25A9%25E1%2584%2585%25E1%2585%25B5%2520%25E1%2584%2583%25E1%2585%25B1%25E1%2584%2584%25E1%2585%25AE%25E1%2586%25BC.gif%22%20alt%3D%22%E1%84%92%E1%85%A2%E1%86%B7%E1%84%90%E1%85%A9%E1%84%85%E1%85%B5%20%E1%84%83%E1%85%B1%E1%84%84%E1%85%AE%E1%86%BC.gif%22%3E%3C%2Fp%3E%0A")
                .build()

        when:
        Letter saved = letterRepository.save(letter)

        then:
        saved.id == 1
    }
}
