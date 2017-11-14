package com.jojoldu.beginner.domain.posts;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by jojoldu@gmail.com on 2017. 11. 9.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

public interface PostsRepositoryCustom {

    List<Posts> findAllByBetweenDate(LocalDate startDate, LocalDate endDate);

}
