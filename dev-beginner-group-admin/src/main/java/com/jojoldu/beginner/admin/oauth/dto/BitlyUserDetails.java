package com.jojoldu.beginner.admin.oauth.dto;

import com.jojoldu.beginner.admin.oauth.domain.Role;
import com.jojoldu.beginner.admin.oauth.domain.bitly.BitlyUser;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;

/**
 * Created by jojoldu@gmail.com on 16/06/2018
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

@Getter
public class BitlyUserDetails implements UserDetails {

    private String username;
    private Role role;

    public BitlyUserDetails(BitlyUser bitlyUser) {
        this.username = bitlyUser.getUsername();
        this.role = bitlyUser.getRole();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new HashSet<>(1);
        authorities.add(new SimpleGrantedAuthority(role != null? role.name() : Role.GUEST.name()));
        return authorities;
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
