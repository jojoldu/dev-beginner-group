package com.jojoldu.beginner.admin.oauth.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jojoldu.beginner.admin.bitly.domain.BitlyUser;
import com.jojoldu.beginner.admin.bitly.domain.BitlyUserRepository;
import com.jojoldu.beginner.admin.oauth.dto.BitlyUserOauthDto;
import com.jojoldu.beginner.admin.oauth.dto.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

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
    private final HttpSession httpSession;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        BitlyUserOauthDto bitlyUserDto = createDto(authentication);
        saveOrUpdate(bitlyUserDto);
        httpSession.setAttribute(SessionUser.SESSION_KEY, bitlyUserDto.toSessionDto());
        response.sendRedirect("/");
    }

    private BitlyUserOauthDto createDto(Authentication authentication) {
        BitlyUserOauthDto bitlyUserDto = convertToDto(authentication);
        bitlyUserDto.setAccessToken(extractAccessToken(authentication));

        return bitlyUserDto;
    }

    private BitlyUserOauthDto convertToDto(Authentication authentication) {
        OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) authentication;
        Object details = oAuth2Authentication.getUserAuthentication().getDetails();
        return objectMapper.convertValue(details, BitlyUserOauthDto.class);
    }

    private String extractAccessToken(Authentication authentication) {
        @SuppressWarnings("unchecked")
        Map<String, String> details = objectMapper.convertValue(authentication.getDetails(), Map.class);
        return details.get("tokenValue");
    }

    private void saveOrUpdate(BitlyUserOauthDto dto) {
        Optional<BitlyUser> optional = bitlyUserRepository.findByUsername(dto.getLogin());

        if(optional.isPresent()){
            BitlyUser entity = optional.get();
            entity.refreshToken(dto.getAccessToken());
            bitlyUserRepository.save(entity);
        } else {
            bitlyUserRepository.save(dto.toNewEntity());
        }
    }
}
