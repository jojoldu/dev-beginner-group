package com.jojoldu.beginner.admin.domain;

import com.jojoldu.beginner.domain.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.annotation.Nonnull;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class BitlyUser extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String accessToken;
    private String username;

    @Builder
    public BitlyUser(@Nonnull String accessToken, @Nonnull String username) {
        this.accessToken = accessToken;
        this.username = username;
    }
}
