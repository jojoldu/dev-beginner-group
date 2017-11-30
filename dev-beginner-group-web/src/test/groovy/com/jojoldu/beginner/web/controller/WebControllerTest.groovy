package com.jojoldu.beginner.web.controller

import com.jojoldu.beginner.web.dto.SubscribeRequestDto
import com.jojoldu.beginner.web.dto.SubscribeResponseDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.embedded.LocalServerPort
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import spock.lang.Specification

/**
 * Created by jojoldu@gmail.com on 2017. 11. 16.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class WebControllerTest extends Specification {

    @LocalServerPort
    private int port

    @Autowired
    private TestRestTemplate restTemplate

    def "이메일 양식으로 보내면 통과" (){
        given:
        SubscribeRequestDto dto = new SubscribeRequestDto("admin@devbeginner.com")
        String url = "http://localhost:" + port + "/subscribe"

        when:
        String result = this.restTemplate.postForObject(url, dto, SubscribeResponseDto.class)

        then:
        result.length() > 1
    }

    def "이메일 양식이 아니면 400 Error 발생" (){
        given:
        SubscribeRequestDto dto = new SubscribeRequestDto("admin@devbeginner")
        String url = "http://localhost:" + port + "/subscribe"

        when:
        ResponseEntity<SubscribeResponseDto> response = this.restTemplate.postForEntity(url, dto, SubscribeResponseDto.class)

        then:
        response.getStatusCode() == HttpStatus.BAD_REQUEST
    }

}
