package com.jojoldu.admin.service;

import com.google.common.collect.ImmutableMap;
import com.jojoldu.admin.config.WebProperties;
import com.jojoldu.admin.dto.LetterAdminRequestDto;
import com.jojoldu.admin.dto.LetterContentResponseDto;
import com.jojoldu.admin.dto.LetterPageRequestDto;
import com.jojoldu.admin.dto.MailLinkRedirectDto;
import com.jojoldu.beginner.domain.letter.*;
import com.jojoldu.beginner.domain.subscriber.Subscriber;
import com.jojoldu.beginner.domain.subscriber.SubscriberRepository;
import com.jojoldu.beginner.mail.aws.Sender;
import com.jojoldu.beginner.mail.aws.SenderDto;
import com.jojoldu.beginner.mail.template.TemplateComponent;
import com.jojoldu.beginner.util.Constants;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    private LetterContentRepository letterContentRepository;
    private SubscriberRepository subscriberRepository;
    private Sender sender;
    private TemplateComponent templateComponent;
    private WebProperties webProperties;

    @Transactional(readOnly = true)
    public List<LetterContentResponseDto> findByPageable(LetterPageRequestDto dto){
        return letterContentRepository.findAll(dto.toPageable())
                .getContent().stream()
                .map(LetterContentResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public Long saveAndSend(LetterAdminRequestDto dto){
        final Letter letter = createLetter(dto);
        sendTestUser(letter);
        return letter.getId();
    }

    Letter createLetter(LetterAdminRequestDto dto) {
        Letter letter = dto.toEntity();
        letter.addContents(letterContentRepository.findAllByIdIn(dto.getContentIds()));
        return letterRepository.save(letter);
    }

    private void sendTestUser(Letter letter){
        List<Subscriber> subscribers = subscriberRepository.findAllByEmailIn(Constants.TEST_USERS);
        for (Subscriber subscriber : subscribers) {
            send(letter, subscriber);
        }
    }

    private void send(Letter letter, Subscriber subscriber) {
        List<MailLinkRedirectDto> dtos = letter.getContentEntities().stream()
                .map(entity -> new MailLinkRedirectDto(subscriber.getId(), webProperties.getWebUrl(), entity))
                .collect(Collectors.toList());

        Map<String, Object> model = ImmutableMap.of("posts", dtos, "openUrl", webProperties.getWebUrl()+"/mail/statistics/open");
        String content = templateComponent.template("newsletter", model);

        SenderDto senderDto = SenderDto.builder()
                .to(Collections.singletonList(subscriber.getEmail()))
                .subject(letter.getSubject())
                .content(content)
                .build();
        sender.send(senderDto);
    }

}
