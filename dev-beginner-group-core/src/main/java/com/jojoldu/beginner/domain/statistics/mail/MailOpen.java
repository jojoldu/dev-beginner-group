package com.jojoldu.beginner.domain.statistics.mail;

import com.jojoldu.beginner.domain.BaseTimeEntity;
import com.jojoldu.beginner.domain.letter.Letter;
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
public class MailOpen extends BaseTimeEntity{

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "subscriber_id", foreignKey = @ForeignKey(name = "FK_MAIL_OPEN_SUBSCRIBER"))
    private Subscriber subscriber;

    @ManyToOne
    @JoinColumn(name = "letter_id", foreignKey = @ForeignKey(name = "FK_MAIL_OPEN_LETTER"))
    private Letter letter;

    @Builder
    public MailOpen(Subscriber subscriber, Letter letter) {
        this.subscriber = subscriber;
        this.letter = letter;
    }
}
