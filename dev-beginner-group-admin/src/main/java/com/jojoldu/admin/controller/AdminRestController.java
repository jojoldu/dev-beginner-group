package com.jojoldu.admin.controller;

import com.jojoldu.admin.dto.LetterAdminSaveRequestDto;
import com.jojoldu.admin.dto.LetterAdminSendRequestDto;
import com.jojoldu.admin.dto.LetterContentRequestDto;
import com.jojoldu.admin.dto.mail.LetterSendMailDto;
import com.jojoldu.admin.service.LetterAdminService;
import com.jojoldu.admin.service.MailAsyncSender;
import com.jojoldu.beginner.domain.letter.Letter;
import com.jojoldu.beginner.domain.letter.LetterContentRepository;
import com.jojoldu.beginner.util.Constants;
import com.jojoldu.staticuploader.aws.StaticUploader;
import lombok.AllArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * Created by jojoldu@gmail.com on 2017. 12. 14.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

@RestController
@AllArgsConstructor
public class AdminRestController {

    private MailAsyncSender mailAsyncSender;
    private StaticUploader staticUploader;
    private LetterAdminService letterAdminService;
    private LetterContentRepository letterContentRepository;

    @PostMapping("/image/upload")
    public String uploadImage(@RequestParam("data") MultipartFile file) throws IOException {
        return staticUploader.upload(file);
    }

    @PostMapping("/letter-content/save")
    public Long saveContent(@RequestBody LetterContentRequestDto requestDto) {
        return letterContentRepository.save(requestDto.toEntity()).getId();
    }

    @PostMapping("/letter/save")
    public Long saveLetter(@RequestBody LetterAdminSaveRequestDto requestDto) {
        Letter letter = letterAdminService.saveLetter(requestDto);
        sendAll(letterAdminService.createLetterSend(letter.getId(), Constants.TEST_USERS));
        return letter.getId();
    }

    @PostMapping("/letter/send")
    public int sendLetter(@RequestBody LetterAdminSendRequestDto requestDto) {
        List<LetterSendMailDto> mails;
        if(StringUtils.isEmpty(requestDto.getEmail())) {
            mails = letterAdminService.createLetterSend(requestDto.getLetterId());
        } else {
            mails = letterAdminService.createLetterSend(requestDto.getLetterId(), Collections.singletonList(requestDto.getEmail()));
        }

        sendAll(mails);
        return mails.size();
    }

    @PostMapping("/letter/send/test")
    public Long sendLetterToTestUser(@RequestBody LetterAdminSendRequestDto requestDto) {
        sendAll(letterAdminService.createLetterSend(requestDto.getLetterId(), Constants.TEST_USERS));
        return requestDto.getLetterId();
    }

    private void sendAll(List<LetterSendMailDto> mails) {
        for (LetterSendMailDto dto : mails) {
            mailAsyncSender.send(dto);
        }
    }
}
