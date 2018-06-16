package com.jojoldu.beginner.admin.oauth.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jojoldu.beginner.admin.oauth.domain.Role;
import com.jojoldu.beginner.admin.oauth.domain.bitly.BitlyUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Created by jojoldu@gmail.com on 16/06/2018
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class BitlyUserDto {

    private String login;
    private String name;
    private List<BitlyEmail> emails;

    public String getEmail(){
        return emails.stream()
                .filter(e -> e.isPrimary && e.isVerified)
                .map(BitlyEmail::getEmail)
                .findFirst()
                .orElseGet(() -> emails.get(0).getEmail());
    }

    public BitlyUser toEntity() {
        return BitlyUser.builder()
                .email(getEmail())
                .name(name)
                .username(login)
                .role(Role.GUEST)
                .build();
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class BitlyEmail {
        private String email;

        @JsonProperty("is_primary")
        private boolean isPrimary;

        @JsonProperty("is_verified")
        private boolean isVerified;
    }
}
