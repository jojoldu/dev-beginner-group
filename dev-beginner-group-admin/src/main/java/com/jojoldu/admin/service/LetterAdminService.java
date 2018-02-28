package com.jojoldu.admin.service;

import com.jojoldu.admin.config.WebProperties;
import com.jojoldu.admin.dto.LetterAdminSaveRequestDto;
import com.jojoldu.admin.dto.LetterContentResponseDto;
import com.jojoldu.admin.dto.LetterPageRequestDto;
import com.jojoldu.admin.dto.mail.LetterSendMailDto;
import com.jojoldu.admin.dto.mail.MailContentDto;
import com.jojoldu.beginner.domain.letter.Letter;
import com.jojoldu.beginner.domain.letter.LetterContent;
import com.jojoldu.beginner.domain.letter.LetterContentRepository;
import com.jojoldu.beginner.domain.letter.LetterRepository;
import com.jojoldu.beginner.domain.subscriber.Subscriber;
import com.jojoldu.beginner.domain.subscriber.SubscriberRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
    private WebProperties webProperties;

    @Transactional(readOnly = true)
    public List<LetterContentResponseDto> findByPageable(LetterPageRequestDto dto){
        return letterContentRepository.findAll(dto.toPageable())
                .getContent().stream()
                .map(LetterContentResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<LetterSendMailDto> createLetterSend(Long letterId){
        Letter letter = findLetter(letterId);
        List<Subscriber> subscribers = subscriberRepository.findAllActive();
        return createLetterSendMailDto(letter, subscribers);
    }

    @Transactional(readOnly = true)
    public List<LetterSendMailDto> createLetterSend(Long letterId, List<String> emails){
        Letter letter = findLetter(letterId);
        List<Subscriber> subscribers = subscriberRepository.findAllByEmailIn(emails);
        return createLetterSendMailDto(letter, subscribers);
    }

    private Letter findLetter(Long letterId) {
        return letterRepository.findById(letterId)
                .orElseThrow(() -> new IllegalArgumentException(String.format("해당하는 Letter가 없습니다. ID: %d", letterId)));
    }


    @Transactional
    public Letter saveLetter(LetterAdminSaveRequestDto dto) {
        Letter letter = dto.toEntity();
        letter.addContents(letterContentRepository.findAllByIdIn(dto.getContentIds()));
        return letterRepository.save(letter);
    }

    private List<LetterSendMailDto> createLetterSendMailDto(Letter letter, List<Subscriber> subscribers){
        return subscribers.stream()
                .map(subscriber -> new LetterSendMailDto(letter.getId(), letter.getSubject(), subscriber.getEmail(), createRedirectDto(letter.getContentEntities(), subscriber.getId())))
                .collect(Collectors.toList());
    }

    private List<MailContentDto> createRedirectDto(List<LetterContent> contents, Long subscriberId) {
        return contents.stream()
                .map(content -> new MailContentDto(subscriberId, webProperties.getWebUrl(), content))
                .collect(Collectors.toList());
    }
}
