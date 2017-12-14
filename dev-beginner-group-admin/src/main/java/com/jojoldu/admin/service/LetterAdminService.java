package com.jojoldu.admin.service;

import com.github.jknack.handlebars.Template;
import com.google.common.collect.ImmutableMap;
import com.jojoldu.admin.dto.LetterAdminRequestDto;
import com.jojoldu.admin.dto.LetterContentResponseDto;
import com.jojoldu.admin.dto.LetterPageRequestDto;
import com.jojoldu.beginner.domain.letter.*;
import com.jojoldu.beginner.mail.aws.Sender;
import com.jojoldu.beginner.mail.aws.SenderDto;
import com.jojoldu.beginner.mail.template.HandlebarsFactory;
import com.jojoldu.beginner.util.Constants;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
    private Sender sender;
    private HandlebarsFactory handlebarsFactory;

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
        compileContent(letter.getSubject(), letter.getContentEntity())
                .ifPresent(content -> {
                    SenderDto senderDto = SenderDto.builder()
                            .to(Constants.TEST_USERS)
                            .subject(letter.getSubject())
                            .content(content)
                            .build();
                    sender.send(senderDto);
                });
    }

    private Optional<String> compileContent(String subject, List<LetterContent> contentList){
        final Template template = handlebarsFactory.get("newsletter");
        Map<String, List<LetterContent>> map = ImmutableMap.of("posts", contentList);
        try {
            return Optional.of(template.apply(map));
        } catch (IOException e) {
            log.error(String.format("Handlebars Template Exception: subject: %s", subject));
            return Optional.empty();
        }
    }
}
