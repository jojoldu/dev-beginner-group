package com.jojoldu.beginner.domain.letter;

import com.jojoldu.beginner.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.annotation.Nonnull;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by jojoldu@gmail.com on 2017. 12. 10.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

@Entity
@Getter
@NoArgsConstructor
public class LetterContent extends BaseTimeEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String link;

    private String img;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String contentMarkdown;

    @OneToMany(mappedBy = "letter", cascade = CascadeType.ALL)
    private List<LetterContentMap> letterContents = new ArrayList<>();

    @Builder
    public LetterContent(@Nonnull String title, @Nonnull String link, @Nonnull String img, @Nonnull String content, @Nonnull String contentMarkdown) {
        this.title = title;
        this.link = link;
        this.img = img;
        this.content = content;
        this.contentMarkdown = contentMarkdown;
    }

}
