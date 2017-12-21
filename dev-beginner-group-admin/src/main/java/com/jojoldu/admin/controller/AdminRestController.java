package com.jojoldu.admin.controller;

import com.jojoldu.admin.dto.LetterAdminSaveRequestDto;
import com.jojoldu.admin.dto.LetterAdminSendRequestDto;
import com.jojoldu.admin.dto.LetterContentRequestDto;
import com.jojoldu.admin.dto.LetterSendMailDto;
import com.jojoldu.admin.service.LetterAdminService;
import com.jojoldu.beginner.domain.letter.Letter;
import com.jojoldu.beginner.domain.letter.LetterContentRepository;
import com.jojoldu.staticuploader.aws.StaticUploader;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
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
        final Letter letter = letterAdminService.saveLetter(requestDto);
        asyncSendMail(letterAdminService.createTestEmail(letter));
        return letter.getId();
    }

    @PostMapping("/letter/send")
    public int sendLetter(@RequestBody LetterAdminSendRequestDto requestDto) {
        List<LetterSendMailDto> mails = letterAdminService.createLetterSend(requestDto.getLetterId());
        asyncSendMail(mails);
        return mails.size();
    }

    private void asyncSendMail(List<LetterSendMailDto> mails) {
        for (LetterSendMailDto mail : mails) {
            letterAdminService.send(mail);
        }
    }
}
