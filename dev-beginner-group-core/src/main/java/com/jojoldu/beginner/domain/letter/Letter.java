package com.jojoldu.beginner.domain.letter;

import com.jojoldu.beginner.util.Constants;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import javax.annotation.Nonnull;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    private LocalDate sendDate;

    private LocalDate createDate;

    private LetterStatus status;

    @OneToMany(mappedBy = "letter", cascade = CascadeType.ALL)
    private List<LetterContentMap> letterContents = new ArrayList<>();

    @Builder
    public Letter(@Nonnull String subject, String sender, List<LetterContent> letterContents) {
        this.subject = subject;
        this.sender = StringUtils.isEmpty(sender)? Constants.ADMIN_EMAIL : sender;
        this.createDate = LocalDate.now();
        this.addContents(letterContents);
    }

    public List<LetterContent> getContentEntity(){
        return this.getLetterContents().stream()
                .map(LetterContentMap::getLetterContent)
                .collect(Collectors.toList());
    }

    public void addContents(List<LetterContent> contents){
        if(contents != null){
            for (LetterContent content : contents) {
                this.addContent(content);
            }
        }
    }

    public void addContent(LetterContent content) {
        this.letterContents.add(new LetterContentMap(this, content));
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
