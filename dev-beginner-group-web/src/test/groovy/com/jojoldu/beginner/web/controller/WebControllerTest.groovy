package com.jojoldu.beginner.web.controller

import com.jojoldu.beginner.web.dto.SubscribeRequestDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.embedded.LocalServerPort
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
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
//        given:
//        SubscribeRequestDto dto = new SubscribeRequestDto("admin@devbeginner.com")
//        String url = "http://localhost:" + port + "/"
//        when:
//        this.restTemplate.postForEntity()
//        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/",
//                String.class)).contains("Hello World");
    }

}
