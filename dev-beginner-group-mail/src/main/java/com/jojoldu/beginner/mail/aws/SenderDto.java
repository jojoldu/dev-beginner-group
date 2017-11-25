package com.jojoldu.beginner.mail.aws;

import com.amazonaws.services.simpleemail.model.*;
import com.jojoldu.beginner.util.Constants;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by jojoldu@gmail.com on 2017. 11. 10.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

@Slf4j
@Getter
public class SenderDto {
    private String from;
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

    public List<SendEmailRequest> toSendRequestDtos(){
        return to.stream()
                .map(this::createSendRequestDto)
                .collect(Collectors.toList());
    }

    private SendEmailRequest createSendRequestDto(String receiver) {
        Destination destination = new Destination()
                .withToAddresses(receiver);

        Message message = null;
        try {
            message = new Message()
                    .withSubject(createContent(URLDecoder.decode(this.subject, "UTF-8")))
                    .withBody(new Body()
                            .withHtml(createContent(URLDecoder.decode(this.content, "UTF-8"))));
        } catch (UnsupportedEncodingException e) {
            log.error("SenderRequest Decode Error ", e);
        }

        return new SendEmailRequest()
                .withSource(getFromOrDefault())
                .withDestination(destination)
                .withMessage(message);
    }

    private String getFromOrDefault(){
        return StringUtils.isEmpty(this.from)? Constants.ADMIN_EMAIL : this.from;
    }

    private Content createContent(String text) {
        return new Content()
                .withCharset("UTF-8")
                .withData(text);
    }
}
