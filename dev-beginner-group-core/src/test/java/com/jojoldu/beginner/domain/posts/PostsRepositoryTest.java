package com.jojoldu.beginner.domain.posts;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by jojoldu@gmail.com on 2017. 11. 9.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostsRepositoryTest {

    @Autowired
    private PostsRepository postsRepository;

    @After
    public void cleanup() {
        postsRepository.deleteAll();
    }

    @Test
    public void 프로젝트_초기_설정테스트 () throws Exception {
        //given
        postsRepository.save(Posts.builder()
                .publishDate(LocalDate.now())
                .content("안녕하세요")
                .favoriteCount(1L)
                .build());

        //when
        List<Posts> postsList = postsRepository.findAll();

        //then
        assertThat(postsList.size(), is(1));
    }
}
