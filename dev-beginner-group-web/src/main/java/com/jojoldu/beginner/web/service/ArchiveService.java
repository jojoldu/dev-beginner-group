package com.jojoldu.beginner.web.service;

import com.jojoldu.beginner.web.dto.archive.LetterArchiveResponseDto;
import com.jojoldu.beginner.web.repository.letter.LetterWebRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by jojoldu@gmail.com on 2018. 3. 9.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

@Service
@AllArgsConstructor
public class ArchiveService {

    private LetterWebRepository letterWebRepository;

    @Transactional(readOnly = true)
    public List<LetterArchiveResponseDto> findArchives(Pageable pageable){
        return letterWebRepository.findLetterArchiveDto(pageable);
    }
}
