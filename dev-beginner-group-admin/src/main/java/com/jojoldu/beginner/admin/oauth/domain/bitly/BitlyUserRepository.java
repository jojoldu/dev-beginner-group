package com.jojoldu.beginner.admin.oauth.domain.bitly;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created by jojoldu@gmail.com on 16/06/2018
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

public interface BitlyUserRepository extends JpaRepository<BitlyUser, Long> {

    Optional<BitlyUser> findByUsername(String username);
    Optional<BitlyUser> findByEmail(String email);
}
