package com.jojoldu.beginner.domain.letter;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by jojoldu@gmail.com on 2017. 12. 11.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

public interface LetterContentRepository extends JpaRepository<LetterContent, Long>{

    List<LetterContent> findAllByIdIn (List<Long> ids);

    List<LetterContent> findAllOrOrderByIdDesc();
}
