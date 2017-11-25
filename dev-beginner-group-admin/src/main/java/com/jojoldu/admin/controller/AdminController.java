package com.jojoldu.admin.controller;

import com.jojoldu.admin.dto.LetterAdminRequestDto;
import com.jojoldu.admin.service.LetterAdminService;
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

    @GetMapping("/")
    public String main() {
        return "main";
    }

    @PostMapping("/upload-image")
    @ResponseBody
    public String uploadImage(@RequestParam("data") MultipartFile file) throws IOException {
        return staticUploader.upload(file);
    }

    @PostMapping("/upload-content")
    @ResponseBody
    public Long uploadContent(@RequestBody LetterAdminRequestDto requestDto) {
        return letterAdminService.saveAndSend(requestDto);
    }

}
