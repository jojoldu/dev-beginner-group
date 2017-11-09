package com.jojoldu.beginner.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by jojoldu@gmail.com on 2017. 11. 9.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

public interface PostsRepository extends JpaRepository<Posts, Long>{
}
