package com.jojoldu.beginner.web.controller;

import com.jojoldu.beginner.web.dto.SubscribeRequestDto;
import com.jojoldu.beginner.web.dto.SubscribeResponseDto;
import com.jojoldu.beginner.web.service.SubscribeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


/**
 * Created by jojoldu@gmail.com on 2017. 11. 16.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

@RestController
@AllArgsConstructor
public class WebController {

    private SubscribeService subscribeService;

    @PostMapping("/subscribe")
    public SubscribeResponseDto subscribe(@RequestBody @Valid SubscribeRequestDto requestDto) {
        boolean result = subscribeService.saveWaitingList(requestDto.getEmail());
        return new SubscribeResponseDto(result);
    }

}
