package com.jojoldu.admin.controller;

import com.jojoldu.admin.dto.LetterPageRequestDto;
import com.jojoldu.admin.service.LetterAdminService;
import com.jojoldu.beginner.domain.letter.LetterContentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by jojoldu@gmail.com on 2017. 11. 21.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

@Controller
@AllArgsConstructor
public class AdminController {

    private LetterAdminService service;

    @GetMapping("/")
    public String main() {
        return "main";
    }

    @GetMapping("/letter/form/save")
    public String letterSaveForm(Model model, LetterPageRequestDto pageDto) {
        model.addAttribute("contents", service.findByPageable(pageDto));
        return "letter/save";
    }
}
