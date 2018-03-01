package com.jojoldu.beginner.domain.subscriber;

import java.util.List;

/**
 * Created by jojoldu@gmail.com on 2018. 3. 1.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

public interface SubscriberRepositoryCustom {
    List<Subscriber> findAllActive(List<String> emails);
}
