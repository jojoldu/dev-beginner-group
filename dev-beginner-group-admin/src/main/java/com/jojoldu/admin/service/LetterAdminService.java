package com.jojoldu.admin.service;

import com.jojoldu.admin.dto.LetterAdminRequestDto;
import com.jojoldu.beginner.domain.letter.LetterRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Created by jojoldu@gmail.com on 2017. 11. 24.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

@Service
@AllArgsConstructor
public class LetterAdminService {

    private LetterRepository letterRepository;

    public Long save(LetterAdminRequestDto dto){
        return letterRepository.save(dto.toEntity()).getId();
    }
}
