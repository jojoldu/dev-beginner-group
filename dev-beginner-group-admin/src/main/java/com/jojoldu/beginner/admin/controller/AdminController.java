package com.jojoldu.beginner.admin.controller;

import com.jojoldu.beginner.admin.dto.LetterPageRequestDto;
import com.jojoldu.beginner.admin.service.LetterAdminService;
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
        return "index";
    }

    @GetMapping("/admin/content/form/save")
    public String contentSaveForm() {
        return "content/save";
    }

    @GetMapping("/admin/letter/form/save")
    public String letterSaveForm(Model model, LetterPageRequestDto pageDto) {
        model.addAttribute("contents", service.findByPageable(pageDto));
        return "letter/save";
    }
}
