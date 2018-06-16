package com.jojoldu.beginner.admin.oauth.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jojoldu.beginner.admin.oauth.domain.bitly.BitlyUser;
import com.jojoldu.beginner.admin.oauth.domain.bitly.BitlyUserRepository;
import com.jojoldu.beginner.admin.oauth.dto.BitlyUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Created by jojoldu@gmail.com on 16/06/2018
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

@RequiredArgsConstructor
@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final ObjectMapper objectMapper;
    private final BitlyUserRepository bitlyUserRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        BitlyUser bitlyUser = getOrSave(convertToDto(authentication));
        bitlyUser.refreshToken(extractAccessToken(authentication));
        bitlyUserRepository.save(bitlyUser);

        response.sendRedirect("/");
    }

    private BitlyUserDto convertToDto(Authentication authentication){
        OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) authentication;
        Object details = oAuth2Authentication.getUserAuthentication().getDetails();
        return objectMapper.convertValue(details, BitlyUserDto.class);
    }

    private String extractAccessToken(Authentication authentication){
        @SuppressWarnings("unchecked")
        Map<String, String> details = objectMapper.convertValue(authentication.getDetails(), Map.class);
        return details.get("tokenValue");
    }

    private BitlyUser getOrSave(BitlyUserDto dto){
        return bitlyUserRepository.findByUsername(dto.getLogin())
                .orElseGet(() -> bitlyUserRepository.save(dto.toEntity()));
    }
}
