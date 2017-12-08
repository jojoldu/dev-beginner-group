package com.jojoldu.admin.service;

import com.github.jknack.handlebars.Template;
import com.google.common.collect.ImmutableMap;
import com.jojoldu.admin.dto.LetterAdminRequestDto;
import com.jojoldu.beginner.domain.letter.Letter;
import com.jojoldu.beginner.domain.letter.LetterRepository;
import com.jojoldu.beginner.mail.aws.Sender;
import com.jojoldu.beginner.mail.aws.SenderDto;
import com.jojoldu.beginner.mail.template.HandlebarsFactory;
import com.jojoldu.beginner.util.Constants;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

/**
 * Created by jojoldu@gmail.com on 2017. 11. 24.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

@Slf4j
@Service
@AllArgsConstructor
public class LetterAdminService {

    private LetterRepository letterRepository;
    private Sender sender;
    private HandlebarsFactory handlebarsFactory;

    @Transactional
    public Long saveAndSend(LetterAdminRequestDto dto){
        final Letter letter = letterRepository.save(dto.toEntity());
        sendTestUser(letter);
        return letter.getId();
    }

    private void sendTestUser(Letter letter){
        compileContent(letter.getContent())
                .ifPresent(content -> {
                    SenderDto senderDto = SenderDto.builder()
                            .to(Constants.TEST_USERS)
                            .subject(letter.getSubject())
                            .content(content)
                            .build();
                    sender.send(senderDto);
                });
    }

    private Optional<String> compileContent(String content){
        final Template template = handlebarsFactory.get("newsletter");
        Map<String, String> map = ImmutableMap.of("content", content);
        try {
            return Optional.of(template.apply(map));
        } catch (IOException e) {
            log.error(String.format("Handlebars Template Exception: content: %s", content));
            return Optional.empty();
        }
    }
}
