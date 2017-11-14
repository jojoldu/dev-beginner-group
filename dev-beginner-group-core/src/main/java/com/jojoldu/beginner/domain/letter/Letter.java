package com.jojoldu.beginner.domain.letter;

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

    private String content;

    private LocalDate sendDate;

    private LocalDate createDate;

    public Letter(String content, LocalDate createDate) {
        this.content = content;
        this.createDate = createDate;
    }

    public void send(LocalDate sendDate){
        this.sendDate = sendDate;
    }
}
