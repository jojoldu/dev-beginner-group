package com.jojoldu.beginner.domain.letter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Created by jojoldu@gmail.com on 2017. 12. 11.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

public interface LetterContentRepository extends JpaRepository<LetterContent, Long>{

    Optional<LetterContent> findById(Long id);

    List<LetterContent> findAllByIdIn (List<Long> ids);

    Page<LetterContent> findAll(Pageable pageable);
}
