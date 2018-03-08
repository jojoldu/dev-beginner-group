package com.jojoldu.admin.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * Created by jojoldu@gmail.com on 2017. 12. 15.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

@NoArgsConstructor
@Setter
@Getter
public class LetterPageRequestDto {
    private static final int DEFAULT_SIZE = 20;

    private int page;

    public LetterPageRequestDto(int page) {
        this.page = page;
    }

    public Pageable toPageable(){
        return new PageRequest(page, DEFAULT_SIZE, Sort.Direction.DESC, "id");
    }
}
