package com.jojoldu.admin.controller;

import com.jojoldu.admin.dto.LetterAdminSaveRequestDto;
import com.jojoldu.admin.dto.LetterAdminSendRequestDto;
import com.jojoldu.admin.dto.LetterContentRequestDto;
import com.jojoldu.admin.dto.mail.MailSendDto;
import com.jojoldu.admin.service.LetterAdminService;
import com.jojoldu.admin.service.MailAsyncSender;
import com.jojoldu.beginner.domain.letter.LetterContentRepository;
import com.jojoldu.beginner.util.Constants;
import com.jojoldu.staticuploader.aws.StaticUploader;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
        Long letterId = letterAdminService.saveLetter(requestDto);
        sendAll(letterId, Constants.TEST_USERS);
        return letterId;
    }

    @PostMapping("/letter/send")
    public int sendLetter(@RequestBody LetterAdminSendRequestDto requestDto) {
        List<MailSendDto> mails = letterAdminService.createSendMailList(requestDto.getLetterId(), requestDto.getEmails());
        mailAsyncSender.sendAll(mails);
        return mails.size();
    }

    @PostMapping("/letter/send/test")
    public Long sendLetterToTestUser(@RequestBody LetterAdminSendRequestDto requestDto) {
        sendAll(requestDto.getLetterId(), Constants.TEST_USERS);
        return requestDto.getLetterId();
    }

    private void sendAll(Long letterId, List<String> emails) {
        List<MailSendDto> mails = letterAdminService.createSendMailList(letterId, emails);
        mailAsyncSender.sendAll(mails);
    }
}
