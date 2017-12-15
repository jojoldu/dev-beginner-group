package com.jojoldu.beginner.domain.posts;

import com.jojoldu.beginner.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Created by jojoldu@gmail.com on 2017. 11. 9.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

@Getter
@NoArgsConstructor
@Entity
@Table(indexes = @Index(name = "IDX_POSTS_PUBLISH_DATE", columnList = "publishDate"))
public class Posts extends BaseTimeEntity {

    @Id
    @GeneratedValue
    private Long id;

    private LocalDate publishDate;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;
    private long favoriteCount;

    @Builder
    public Posts(LocalDate publishDate, String content, long favoriteCount) {
        this.publishDate = publishDate;
        this.content = content;
        this.favoriteCount = favoriteCount;
    }
}
