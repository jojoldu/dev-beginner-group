package com.jojoldu.beginner.admin.repository.lettercontent;

import com.jojoldu.beginner.admin.dto.letter.save.LetterContentResponseDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.jojoldu.beginner.domain.letter.QLetter.letter;
import static com.jojoldu.beginner.domain.letter.QLetterContent.letterContent;
import static com.jojoldu.beginner.domain.letter.QLetterContentMap.letterContentMap;

/**
 * Created by jojoldu@gmail.com on 2018. 3. 8.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

@AllArgsConstructor
public class LetterContentAdminRepositoryImpl implements LetterContentAdminRepositoryCustom {
    private JPAQueryFactory queryFactory;

    public List<LetterContentResponseDto> findLetterContentDto(Pageable pageable){
        return queryFactory.select(Projections.fields(LetterContentResponseDto.class,
                letterContent.id,
                letter.id.as("letterId"),
                letterContent.title,
                letterContent.link,
                letterContent.img,
                letterContent.content,
                letterContent.contentMarkdown,
                letterContent.createdDate,
                letterContent.modifiedDate))
                .from(letterContentMap)
                .join(letterContentMap.letter, letter)
                .rightJoin(letterContentMap.letterContent, letterContent)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(letterContent.id.desc())
                .fetch();
    }

}
