package com.jojoldu.admin.repository.lettercontent;

import com.jojoldu.admin.dto.letter.save.LetterContentResponseDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by jojoldu@gmail.com on 2018. 3. 8.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

public interface LetterContentWebRepositoryCustom {
    List<LetterContentResponseDto> findLetterContentDto(Pageable pageable);
}
