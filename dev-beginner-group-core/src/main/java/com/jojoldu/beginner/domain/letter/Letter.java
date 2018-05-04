package com.jojoldu.beginner.domain.letter;

import com.jojoldu.beginner.domain.BaseTimeEntity;
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
public class Letter extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 500)
    private String subject;

    @Column(nullable = false)
    private String sender;

    private LocalDate sendDate;

    private LetterStatus status;

    @Column(length = 700)
    private String archiveUrl;

    @OneToMany(mappedBy = "letter", cascade = CascadeType.ALL)
    private List<LetterContentMap> letterContents = new ArrayList<>();

    @Builder
    public Letter(@Nonnull String subject, String sender, String archiveUrl, List<LetterContent> letterContents) {
        this.subject = subject;
        this.sender = StringUtils.isEmpty(sender)? Constants.ADMIN_EMAIL : sender;
        this.archiveUrl = archiveUrl;
        this.addContents(letterContents);
    }

    public List<LetterContent> getContentEntities(){
        return this.getLetterContents().stream()
                .map(LetterContentMap::getLetterContent)
                .collect(Collectors.toList());
    }

    public void updateArchive(String archiveUrl){
        this.archiveUrl = archiveUrl;
    }

    public void updateSubject(String subject){
        this.subject = subject;
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
