package com.jojoldu.beginner.web.service;

import com.jojoldu.beginner.domain.subscriber.Subscriber;
import com.jojoldu.beginner.domain.subscriber.SubscriberRepository;
import com.jojoldu.beginner.mail.aws.Sender;
import com.jojoldu.beginner.mail.aws.SenderDto;
import com.jojoldu.beginner.mail.util.MailFromType;
import com.jojoldu.beginner.util.CryptoComponent;
import com.jojoldu.beginner.web.config.WebProperties;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collections;

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

    @Transactional
    public boolean saveWaitingList(String email){
        final LocalDate now = LocalDate.now();
        final String certifyMessage = createCertifyMessage(email, now);
        subscriberRepository.save(new Subscriber(email, now, certifyMessage));

        try{
            sender.send(createSenderDto(email, certifyMessage));
        } catch (Exception e){
            log.error("이메일 등록 실패", e);
            return false;
        }
        return true;
    }

    private String createCertifyMessage(String email, LocalDate now){
        return cryptoComponent.sha256(email+now);
    }

    private SenderDto createSenderDto(String email, String certifyMessage){
        return SenderDto.builder()
                .to(Collections.singletonList(email))
                .from(MailFromType.ADMIN.getEmail())
                .content(createCertifyContent(certifyMessage))
                .subject("초보개발자모임 구독 이메일 인증입니다.")
                .build();
    }

    private String createCertifyContent(String certifyMessage){
        final String link = String.format("%s/certifyEmail?message=%s", webProperties.getWebUrl(), certifyMessage);
        return String.format("<a href='%s'> %s", link, link);
    }
}
