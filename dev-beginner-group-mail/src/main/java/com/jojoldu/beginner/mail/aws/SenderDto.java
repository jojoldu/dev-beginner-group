package com.jojoldu.beginner.mail.aws;

import com.amazonaws.services.simpleemail.model.*;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jojoldu@gmail.com on 2017. 11. 10.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

@Getter
public class SenderDto {
    private String from = "admin@devbeginner.com";
    private List<String> to = new ArrayList<>();
    private String subject;
    private String content;

    @Builder
    public SenderDto(String from, List<String> to, String subject, String content) {
        this.from = from;
        this.to = to;
        this.subject = subject;
        this.content = content;
    }

    public void addTo(String email){
        if(to == null){
            this.to = new ArrayList<>();
        }
        this.to.add(email);
    }

    public SendEmailRequest toSendRequestDto(){
        Destination destination = new Destination()
                .withToAddresses(this.to);

        Message message = new Message()
                .withSubject(createContent(this.subject))
                .withBody(new Body()
                        .withHtml(createContent(this.content)));

        return new SendEmailRequest()
                .withSource(this.from)
                .withDestination(destination)
                .withMessage(message);
    }

    private Content createContent(String text) {
        return new Content()
                .withCharset("UTF-8")
                .withData(text);
    }
}
