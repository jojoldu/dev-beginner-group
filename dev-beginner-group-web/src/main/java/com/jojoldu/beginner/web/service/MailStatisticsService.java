package com.jojoldu.beginner.web.service;

import com.jojoldu.beginner.domain.letter.Letter;
import com.jojoldu.beginner.domain.letter.LetterContent;
import com.jojoldu.beginner.domain.letter.LetterContentRepository;
import com.jojoldu.beginner.domain.letter.LetterRepository;
import com.jojoldu.beginner.domain.statistics.mail.MailLinkClick;
import com.jojoldu.beginner.domain.statistics.mail.MailLinkClickRepository;
import com.jojoldu.beginner.domain.statistics.mail.MailOpen;
import com.jojoldu.beginner.domain.statistics.mail.MailOpenRepository;
import com.jojoldu.beginner.domain.subscriber.Subscriber;
import com.jojoldu.beginner.domain.subscriber.SubscriberRepository;
import com.jojoldu.beginner.web.dto.MailLinkClickRequestDto;
import com.jojoldu.beginner.web.dto.MailOpenRequestDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Created by jojoldu@gmail.com on 2017. 12. 17.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

@AllArgsConstructor
@Service
public class MailStatisticsService {

    private SubscriberRepository subscriberRepository;
    private LetterContentRepository letterContentRepository;
    private MailLinkClickRepository mailLinkClickRepository;

    @Transactional
    public String saveMailLinkClick(MailLinkClickRequestDto dto){
        Optional<Subscriber> subscriberOptional = subscriberRepository.findById(dto.getSubscriberId());
        Optional<LetterContent> letterContentOptional = letterContentRepository.findById(dto.getLetterContentId());

        if(subscriberOptional.isPresent() && letterContentOptional.isPresent()){
            final Subscriber subscriber = subscriberOptional.get();
            final LetterContent letterContent = letterContentOptional.get();
            mailLinkClickRepository.save(new MailLinkClick(subscriber, letterContent));
            return letterContent.getLink();
        }

        return dto.getRedirectUrl();
    }
}
