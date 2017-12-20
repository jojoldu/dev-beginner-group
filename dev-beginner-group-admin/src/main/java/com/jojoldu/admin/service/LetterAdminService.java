package com.jojoldu.admin.service;

import com.google.common.collect.ImmutableMap;
import com.jojoldu.admin.config.WebProperties;
import com.jojoldu.admin.dto.*;
import com.jojoldu.beginner.domain.letter.*;
import com.jojoldu.beginner.domain.subscriber.Subscriber;
import com.jojoldu.beginner.domain.subscriber.SubscriberRepository;
import com.jojoldu.beginner.mail.aws.Sender;
import com.jojoldu.beginner.mail.aws.SenderDto;
import com.jojoldu.beginner.mail.template.TemplateComponent;
import com.jojoldu.beginner.util.Constants;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
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

    /**
     * send가 Async 구동이라 상위 메소드에서 Transactional 사용못함
     */
    public Long saveAndSendToTest(LetterAdminSaveRequestDto dto){
        final List<LetterSendMailDto> sendMailDtos = createLetterAndTestMail(dto);

        for (LetterSendMailDto sendMailDto : sendMailDtos) {
            send(sendMailDto);
        }

        return sendMailDtos.get(0).getLetterId();
    }

    public Long sendLetter(LetterAdminSendRequestDto requestDto) {
        Long letterId = requestDto.getLetterId();

        createLetterSend(letterId)
                .forEach(this::send);

        return letterId;
    }

    @Transactional(readOnly = true)
    public List<LetterSendMailDto> createLetterSend(Long letterId){
        Letter letter = letterRepository.findById(letterId)
                .orElseThrow(() -> new IllegalArgumentException(String.format("해당하는 Letter가 없습니다. ID: %d", letterId)));

        List<Subscriber> subscribers = subscriberRepository.findAllActive();
        return createLetterSendMailDto(letter, subscribers);
    }

    @Transactional
    public List<LetterSendMailDto> createLetterAndTestMail(LetterAdminSaveRequestDto dto){
        final Letter letter = saveLetter(dto);
        List<Subscriber> subscribers = subscriberRepository.findAllByEmailIn(Constants.TEST_USERS);
        return createLetterSendMailDto(letter, subscribers);
    }

    Letter saveLetter(LetterAdminSaveRequestDto dto) {
        Letter letter = dto.toEntity();
        letter.addContents(letterContentRepository.findAllByIdIn(dto.getContentIds()));
        return letterRepository.save(letter);
    }

    private List<LetterSendMailDto> createLetterSendMailDto(Letter letter, List<Subscriber> subscribers){
        return subscribers.stream()
                .map(subscriber -> new LetterSendMailDto(letter.getId(), letter.getSubject(), subscriber.getEmail(), createRedirectDto(letter, subscriber)))
                .collect(Collectors.toList());
    }

    private List<MailLinkRedirectDto> createRedirectDto(Letter letter, Subscriber subscriber) {
        return letter.getContentEntities().stream()
                .map(entity -> new MailLinkRedirectDto(subscriber.getId(), webProperties.getWebUrl(), entity))
                .collect(Collectors.toList());
    }

    @Async
    public void send(LetterSendMailDto dto) {
        Map<String, Object> model = ImmutableMap.of("posts", dto.getRedirectDtos(), "openUrl", webProperties.getWebUrl()+"/mail/statistics/open");
        String content = templateComponent.template("newsletter", model);

        sender.send(SenderDto.builder()
                .to(Collections.singletonList(dto.getEmail()))
                .subject(dto.getSubject())
                .content(content)
                .build());
    }



}
