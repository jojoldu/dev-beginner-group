package com.jojoldu.beginner.admin.oauth.domain.bitly;

import com.jojoldu.beginner.admin.oauth.dto.BitlyUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * Created by jojoldu@gmail.com on 16/06/2018
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

@RequiredArgsConstructor
@Component
public class BitlyUserService implements UserDetailsService {

    private BitlyUserRepository bitlyUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return bitlyUserRepository.findByUsername(username)
                .map(BitlyUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다. username: "+username));
    }
}
