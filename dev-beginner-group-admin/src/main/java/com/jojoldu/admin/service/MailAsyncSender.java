package com.jojoldu.admin.service;

import com.jojoldu.admin.dto.mail.MailSendDto;
import com.jojoldu.beginner.mail.aws.Sender;
import com.jojoldu.beginner.mail.aws.SenderDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * Created by jojoldu@gmail.com on 2018. 2. 26.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

@Slf4j
@AllArgsConstructor
@Component
public class MailAsyncSender {
    private Sender sender;
    private NewsLetterFactory newsLetterFactory;

    @Async
    public void sendAll(List<MailSendDto> mails) {
        for (MailSendDto dto : mails) {
            send(dto);
        }
    }

    //SES가 초당 28밖에 안되서 동기로 다시 전환
    public void send(MailSendDto dto) {
        String content = newsLetterFactory.createContent(dto.getContentGroup());

        sender.send(SenderDto.builder()
                .to(Collections.singletonList(dto.getEmail()))
                .subject(dto.getSubject())
                .content(content)
                .build());
    }
}