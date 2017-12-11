package com.jojoldu.beginner.domain.letter;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.annotation.Nonnull;
import javax.persistence.*;

/**
 * Created by jojoldu@gmail.com on 2017. 12. 10.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LetterContentMap {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "letter_id", foreignKey = @ForeignKey(name = "FK_LETTER_CONTENT_MAP_LETTER"))
    private Letter letter;

    @ManyToOne
    @JoinColumn(name = "letter_content_id", foreignKey = @ForeignKey(name = "FK_LETTER_CONTENT_MAP_CONTENT"))
    private LetterContent letterContent;

    public LetterContentMap(@Nonnull Letter letter, @Nonnull LetterContent letterContent) {
        this.letter = letter;
        this.letterContent = letterContent;
    }
}
