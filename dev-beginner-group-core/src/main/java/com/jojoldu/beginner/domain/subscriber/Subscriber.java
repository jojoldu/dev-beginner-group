package com.jojoldu.beginner.domain.subscriber;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.annotation.Nonnull;
import javax.persistence.*;
import java.time.LocalDate;

/**
 * Created by jojoldu@gmail.com on 2017. 11. 9.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

@NoArgsConstructor
@Getter
@Entity
@Table(uniqueConstraints = @UniqueConstraint(name = "UNI_SUBSCRIBER_EMAIL", columnNames = {"email"}))
public class Subscriber {

    @Id
    @GeneratedValue
    private Long id;

    private String email;
    private LocalDate createDate;
    private boolean certified;
    private String certifyMessage;
    private boolean active;

    public Subscriber(@Nonnull String email, @Nonnull LocalDate createDate, @Nonnull String certifyMessage) {
        this.email = email;
        this.createDate = createDate;
        this.certifyMessage = certifyMessage;
        this.certified = false;
        this.active = true;
    }

    public void certify(){
        this.certified = true;
    }
    public void inActive() {
        this.active = false;
    }
}
