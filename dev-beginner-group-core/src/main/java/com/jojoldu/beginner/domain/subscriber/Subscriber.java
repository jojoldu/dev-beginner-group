package com.jojoldu.beginner.domain.subscriber;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;

/**
 * Created by jojoldu@gmail.com on 2017. 11. 9.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

@NoArgsConstructor
@Getter
@Entity
public class Subscriber {

    @Id
    @GeneratedValue
    private Long id;

    private String email;
    private LocalDate createDate;
    private boolean certified;

    public Subscriber(String email, LocalDate createDate) {
        this.email = email;
        this.createDate = createDate;
        this.certified = false;
    }

    public void certify(){
        this.certified = true;
    }
}
