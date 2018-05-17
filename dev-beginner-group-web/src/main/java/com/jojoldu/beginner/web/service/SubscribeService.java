package com.jojoldu.beginner.web.service;

import com.google.common.collect.ImmutableMap;
import com.jojoldu.beginner.domain.subscriber.Subscriber;
import com.jojoldu.beginner.domain.subscriber.SubscriberRepository;
import com.jojoldu.beginner.mail.aws.Sender;
import com.jojoldu.beginner.mail.aws.SenderDto;
import com.jojoldu.beginner.mail.template.TemplateComponent;
import com.jojoldu.beginner.mail.util.MailFromType;
import com.jojoldu.beginner.util.CryptoComponent;
import com.jojoldu.beginner.web.config.WebProperties;
import com.jojoldu.beginner.web.exception.DuplicateException;
import com.jojoldu.beginner.web.exception.EmailServerException;
import com.jojoldu.beginner.web.exception.InvalidParameterException;
import com.jojoldu.beginner.web.exception.NotFoundResourceException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

/**
 * Created by jojoldu@gmail.com on 2017. 11. 17.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

@Slf4j
@AllArgsConstructor
@Service
public class SubscribeService {

    private SubscriberRepository subscriberRepository;
    private CryptoComponent cryptoComponent;
    private Sender sender;
    private WebProperties webProperties;
    private TemplateComponent templateComponent;

    @Transactional
    public void saveWaitingList(String email){
        verifyDuplicateEmail(email);
        final LocalDate now = LocalDate.now();
        final String certifyMessage = createCertifyMessage(email, now);
        subscriberRepository.save(new Subscriber(email, certifyMessage));

        try{
            sender.send(createSenderDto(email, certifyMessage));
        } catch (Exception e){
            log.error("이메일 등록 실패", e);
            throw new EmailServerException();
        }
    }

    @Transactional
    public void certifyComplete(String email, String certifyMessage){
        Subscriber subscriber = subscriberRepository.findTopByEmail(email)
                .orElseThrow(()->new NotFoundResourceException("이메일"));

        if(!subscriber.getCertifyMessage().equals(certifyMessage)){
            throw new InvalidParameterException("인증코드");
        }

        subscriber.certify();
    }

    private void verifyDuplicateEmail(String email){
        Optional<Subscriber> optional = subscriberRepository.findTopByEmail(email);

        if(optional.isPresent()){
            throw new DuplicateException("email");
        }
    }

    private String createCertifyMessage(String email, LocalDate now){
        return cryptoComponent.sha256(email+now);
    }

    private SenderDto createSenderDto(String email, String certifyMessage){
        return SenderDto.builder()
                .to(Collections.singletonList(email))
                .from(MailFromType.ADMIN.getEmail())
                .content(createContent(email, certifyMessage))
                .subject("초보개발자모임 구독 이메일 인증입니다.")
                .build();
    }

    private String createContent(String email, String certifyMessage){
        final String link = createCertifyLink(email, certifyMessage);
        Map<String, Object> model = ImmutableMap.<String, Object>builder()
                .put("link", link)
                .build();

        return templateComponent.template("certify", model);
    }

    String createCertifyLink(String email, String certifyMessage) {
        return String.format("%s/subscribe/certify?email=%s&message=%s", webProperties.getUrl(), email, certifyMessage);
    }

}
