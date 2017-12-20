package com.jojoldu.beginner.web.controller;

import com.jojoldu.beginner.web.dto.MailLinkClickRequestDto;
import com.jojoldu.beginner.web.dto.MailOpenRequestDto;
import com.jojoldu.beginner.web.service.MailStatisticsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by jojoldu@gmail.com on 2017. 12. 17.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

@Controller
@AllArgsConstructor
public class MailStatisticsController {

    private MailStatisticsService service;

    @CrossOrigin
    @GetMapping("/mail/statistics/link-click")
    public String saveMailLinkClick(MailLinkClickRequestDto dto, Model model){
        String redirectUrl = service.saveMailLinkClick(dto);
        model.addAttribute("redirectUrl", redirectUrl);
        return "statistics/redirect";
    }
}
