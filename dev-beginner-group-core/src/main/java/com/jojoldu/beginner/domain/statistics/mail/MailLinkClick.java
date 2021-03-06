package com.jojoldu.beginner.domain.statistics.mail;

import com.jojoldu.beginner.domain.BaseTimeEntity;
import com.jojoldu.beginner.domain.letter.LetterContent;
import com.jojoldu.beginner.domain.subscriber.Subscriber;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Created by jojoldu@gmail.com on 2017. 12. 17.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

@NoArgsConstructor
@Entity
public class MailLinkClick extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "subscriber_id", foreignKey = @ForeignKey(name = "FK_MAIL_OPEN_SUBSCRIBER"))
    private Subscriber subscriber;

    @ManyToOne
    @JoinColumn(name = "letter_content_id", foreignKey = @ForeignKey(name = "FK_MAIL_OPEN_LETTER_CONTENT"))
    private LetterContent letterContent;

    @Builder
    public MailLinkClick(Subscriber subscriber, LetterContent letterContent) {
        this.subscriber = subscriber;
        this.letterContent = letterContent;
    }
}