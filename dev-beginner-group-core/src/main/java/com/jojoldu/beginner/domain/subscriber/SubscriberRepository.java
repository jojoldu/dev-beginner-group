package com.jojoldu.beginner.domain.subscriber;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created by jojoldu@gmail.com on 2017. 11. 9.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

public interface SubscriberRepository extends JpaRepository<Subscriber, Long> {
    Optional<Subscriber> findTopByEmail(String email);
}
