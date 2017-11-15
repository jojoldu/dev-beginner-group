package com.jojoldu.beginner.domain.letter;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;

/**
 * Created by jojoldu@gmail.com on 2017. 11. 14.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

@NoArgsConstructor
@Getter
@Entity
public class Letter {

    @Id
    @GeneratedValue
    private Long id;

    private String subject;
    private String from;
    private String content;

    private LocalDate sendDate;

    private LocalDate createDate;

    private LetterStatus status;

    @Builder
    public Letter(String subject, String from, String content) {
        this.subject = subject;
        this.from = from;
        this.content = content;
        this.createDate = LocalDate.now();
    }

    public void sending() {
        this.sendDate = LocalDate.now();
        this.status = LetterStatus.SENDING;
    }

    public void sendComplete(){
        this.sendDate = LocalDate.now();
        this.status = LetterStatus.COMPLETE;
    }
}
