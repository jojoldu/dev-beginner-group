package com.jojoldu.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jojoldu@gmail.com on 2017. 12. 9.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LetterContentDto {
    private List<LetterPostDto> posts = new ArrayList<>();

    public void add(LetterPostDto dto) {
        this.posts.add(dto);
    }
}
