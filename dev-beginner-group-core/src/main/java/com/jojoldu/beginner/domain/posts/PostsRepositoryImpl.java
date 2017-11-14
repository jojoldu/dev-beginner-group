package com.jojoldu.beginner.domain.posts;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.List;

import static com.jojoldu.beginner.domain.posts.QPosts.posts;

/**
 * Created by jojoldu@gmail.com on 2017. 11. 9.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

@AllArgsConstructor
public class PostsRepositoryImpl implements PostsRepositoryCustom{

    private JPAQueryFactory queryFactory;

    @Override
    public List<Posts> findAllByBetweenDate(LocalDate startDate, LocalDate endDate) {
        return queryFactory.selectFrom(posts)
                .where(posts.publishDate.between(startDate, endDate))
                .fetch();
    }
}
