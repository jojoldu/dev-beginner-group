package com.jojoldu.beginner.web.repository.letter;

import com.jojoldu.beginner.web.dto.archive.LetterArchiveResponseDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.jojoldu.beginner.domain.letter.QLetter.letter;

/**
 * Created by jojoldu@gmail.com on 2018. 3. 6.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

@AllArgsConstructor
public class LetterWebRepositoryImpl implements LetterWebRepositoryCustom {
    private JPAQueryFactory queryFactory;

    public List<LetterArchiveResponseDto> findLetterArchiveDto(Pageable pageable){
        return queryFactory.select(Projections.fields(LetterArchiveResponseDto.class,
                letter.subject,
                letter.archiveUrl,
                letter.sendDate))
                .from(letter)
                .where(letter.archiveUrl.isNotEmpty())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(letter.id.desc())
                .fetch();
    }
}
