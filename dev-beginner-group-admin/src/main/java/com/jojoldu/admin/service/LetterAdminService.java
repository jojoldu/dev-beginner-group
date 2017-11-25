package com.jojoldu.admin.service;

import com.jojoldu.admin.dto.LetterAdminRequestDto;
import com.jojoldu.beginner.domain.letter.Letter;
import com.jojoldu.beginner.domain.letter.LetterRepository;
import com.jojoldu.beginner.mail.aws.Sender;
import com.jojoldu.beginner.mail.aws.SenderDto;
import com.jojoldu.beginner.util.Constants;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by jojoldu@gmail.com on 2017. 11. 24.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

@Service
@AllArgsConstructor
public class LetterAdminService {

    private LetterRepository letterRepository;
    private Sender sender;

    @Transactional
    public Long saveAndSend(LetterAdminRequestDto dto){
        final Letter letter = letterRepository.save(dto.toEntity());
        sendTestUser(letter);
        return letter.getId();
    }

    private void sendTestUser(Letter letter){
        SenderDto senderDto = SenderDto.builder()
                .to(Constants.TEST_USERS)
                .subject(letter.getSubject())
                .content(letter.getContent())
                .build();
        sender.send(senderDto);
    }
}
