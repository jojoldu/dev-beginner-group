package com.jojoldu.admin.controller;

import com.jojoldu.admin.dto.LetterAdminRequestDto;
import com.jojoldu.admin.dto.LetterContentRequestDto;
import com.jojoldu.admin.service.LetterAdminService;
import com.jojoldu.beginner.domain.letter.LetterContentRepository;
import com.jojoldu.staticuploader.aws.StaticUploader;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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
    @ResponseBody
    public String uploadImage(@RequestParam("data") MultipartFile file) throws IOException {
        return staticUploader.upload(file);
    }

    @PostMapping("/letter-content/save")
    @ResponseBody
    public Long saveContent(@RequestBody LetterContentRequestDto requestDto) {
        return letterContentRepository.save(requestDto.toEntity()).getId();
    }

    @PostMapping("/letter/save")
    @ResponseBody
    public Long saveLetter(@RequestBody LetterAdminRequestDto requestDto) {
        return letterAdminService.saveAndSend(requestDto);
    }
}
