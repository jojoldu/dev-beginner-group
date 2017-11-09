package com.jojoldu.beginner.domain.posts;

import lombok.Builder;
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

@Getter
@NoArgsConstructor
@Entity
public class Posts {

    @Id
    @GeneratedValue
    private Long id;

    private LocalDate publishDate;
    private String content;
    private long favoriteCount;

    @Builder
    public Posts(LocalDate publishDate, String content, long favoriteCount) {
        this.publishDate = publishDate;
        this.content = content;
        this.favoriteCount = favoriteCount;
    }
}
