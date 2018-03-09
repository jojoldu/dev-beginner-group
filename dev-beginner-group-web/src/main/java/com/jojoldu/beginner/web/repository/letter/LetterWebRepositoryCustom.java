package com.jojoldu.beginner.web.repository.letter;

import com.jojoldu.beginner.web.dto.archive.LetterArchiveResponseDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by jojoldu@gmail.com on 2018. 3. 6.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

public interface LetterWebRepositoryCustom {
    List<LetterArchiveResponseDto> findLetterArchiveDto(Pageable pageable);
}
