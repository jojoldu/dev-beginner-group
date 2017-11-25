package com.jojoldu.beginner.domain.letter;

import com.jojoldu.beginner.util.Constants;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import javax.persistence.Column;
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

    @Column(nullable = false)
    private String subject;

    @Column(nullable = false)
    private String sender;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String markdown;

    private LocalDate sendDate;

    private LocalDate createDate;

    private LetterStatus status;

    @Builder
    public Letter(String subject, String sender, String content, String markdown) {
        this.subject = subject;
        this.sender = StringUtils.isEmpty(sender)? Constants.ADMIN_EMAIL : sender;
        this.content = content;
        this.markdown = markdown;
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
