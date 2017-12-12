package com.jojoldu.admin.controller;

import com.jojoldu.admin.dto.LetterContentDto;
import com.jojoldu.admin.service.LetterAdminService;
import com.jojoldu.beginner.domain.letter.LetterContentRepository;
import com.jojoldu.staticuploader.aws.StaticUploader;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Created by jojoldu@gmail.com on 2017. 11. 21.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

@Controller
@AllArgsConstructor
public class AdminController {

    private StaticUploader staticUploader;
    private LetterAdminService letterAdminService;
    private LetterContentRepository letterContentRepository;

    @GetMapping("/")
    public String main() {
        return "main";
    }

    @PostMapping("/image/upload")
    @ResponseBody
    public String uploadImage(@RequestParam("data") MultipartFile file) throws IOException {
        return staticUploader.upload(file);
    }

    @PostMapping("/letter-content/save")
    @ResponseBody
    public Long uploadContent(@RequestBody LetterContentDto requestDto) {
        return letterContentRepository.save(requestDto.toEntity()).getId();
    }



}
