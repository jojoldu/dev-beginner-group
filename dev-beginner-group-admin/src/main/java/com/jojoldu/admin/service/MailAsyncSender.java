package com.jojoldu.admin.service;

import com.jojoldu.admin.dto.LetterSendMailDto;
import com.jojoldu.beginner.mail.aws.Sender;
import com.jojoldu.beginner.mail.aws.SenderDto;
import com.jojoldu.beginner.mail.template.TemplateComponent;
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
    private TemplateComponent templateComponent;

    @Async
    public void sendAll(List<LetterSendMailDto> mails) {
        for (LetterSendMailDto mail : mails) {
            send(mail);
        }
    }

    public void send(LetterSendMailDto dto) {
        String content = templateComponent.template("newsletter", dto.getModel());

        sender.send(SenderDto.builder()
                .to(Collections.singletonList(dto.getEmail()))
                .subject(dto.getSubject())
                .content(content)
                .build());
    }
}
