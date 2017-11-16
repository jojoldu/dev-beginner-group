package com.jojoldu.beginner.web;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by jojoldu@gmail.com on 2017. 11. 16.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

public class SimpleTest {

    @Test
    public void 숫자NULL_0_동시체크 (){
        Long NULL = null;
        Long ZERO = 0L;
        Long POSITIVE = 1L;

        assertThat(isEmptyNumber(NULL), is(true));
        assertThat(isEmptyNumber(ZERO), is(true));
        assertThat(isEmptyNumber(POSITIVE), is(false));

    }

    public boolean isEmptyNumber(Long number){
        return number == null || number.equals(0L);
    }
}
