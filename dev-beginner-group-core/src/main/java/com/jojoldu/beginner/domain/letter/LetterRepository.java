package com.jojoldu.beginner.domain.letter;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by jojoldu@gmail.com on 2017. 11. 14.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

public interface LetterRepository extends JpaRepository<Letter, Long>, LetterRepositoryCustom {
}
