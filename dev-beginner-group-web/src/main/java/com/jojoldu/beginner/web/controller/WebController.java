package com.jojoldu.beginner.web.controller;

import com.jojoldu.beginner.web.dto.model.SubscribeErrorModel;
import com.jojoldu.beginner.web.dto.model.SubscribeSuccessModel;
import com.jojoldu.beginner.web.exception.DevBeginnerException;
import com.jojoldu.beginner.web.service.SubscribeService;
import com.jojoldu.beginner.web.util.FormUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * Created by jojoldu@gmail.com on 2017. 11. 16.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

@Controller
@AllArgsConstructor
public class WebController {

    private SubscribeService subscribeService;

    @GetMapping("/index")
    public String index(){
        return "index";
    }


    @GetMapping("/")
    public String getMain(){
        return "redirect:/subscribe/form";
    }

    @GetMapping("/subscribe/form")
    public String getSubscribeForm(){
        return "subscribe/form";
    }

    @PostMapping("/subscribe")
    public String subscribe(@RequestParam String email, Model model) {
        if (!FormUtils.isEmail(email)) {
            model.addAttribute("model", new SubscribeErrorModel("초대장 발송 실패", email+"는 Email양식에 맞지 않습니다."));
            return "subscribe/error";
        }

        try{
            subscribeService.saveWaitingList(email);
        } catch (DevBeginnerException dbe){
            model.addAttribute("model", new SubscribeErrorModel("초대장 발송 실패", dbe.getMessage()));
            return "subscribe/error";
        }

        model.addAttribute("model", SubscribeSuccessModel.createInvite(email));
        return "subscribe/success";
    }

    @GetMapping("/subscribe/certify")
    public String certifySubscribe(@RequestParam String email,
                                   @RequestParam String message,
                                   Model model){
        try{
            subscribeService.certifyComplete(email, message);
        } catch (DevBeginnerException dbe){
            model.addAttribute("model", new SubscribeErrorModel("Email 인증 실패", dbe.getMessage()));
            return "subscribe/error";
        }

        model.addAttribute("model", SubscribeSuccessModel.createCertify(email));
        return "subscribe/success";
    }


}
