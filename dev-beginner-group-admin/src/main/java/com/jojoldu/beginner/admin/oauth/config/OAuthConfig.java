package com.jojoldu.beginner.admin.oauth.config;

import com.jojoldu.beginner.admin.bitly.domain.BitlyUserRepository;
import com.jojoldu.beginner.admin.bitly.domain.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.AuthoritiesExtractor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

import javax.servlet.Filter;

import static java.util.Collections.singletonList;

/**
 * Created by jojoldu@gmail.com on 16/06/2018
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

@RequiredArgsConstructor
@Configuration
@EnableOAuth2Client
public class OAuthConfig {

    private final OAuth2ClientContext oauth2ClientContext;
    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
    private final BitlyUserRepository bitlyUserRepository;

    @Bean
    public Filter ssoFilter() {
        OAuth2ClientAuthenticationProcessingFilter oauth2Filter = new OAuth2ClientAuthenticationProcessingFilter("/login");
        oauth2Filter.setRestTemplate(new OAuth2RestTemplate(bitlyClient(), oauth2ClientContext));
        UserInfoTokenServices tokenServices = new UserInfoTokenServices(bitlyResource().getUserInfoUri(), bitlyClient().getClientId());
        tokenServices.setAuthoritiesExtractor(authoritiesExtractor());
        oauth2Filter.setTokenServices(tokenServices);
        oauth2Filter.setAuthenticationSuccessHandler(customAuthenticationSuccessHandler);
        return oauth2Filter;
    }

    /**
     * 권한 할당
     */
    @Bean
    public AuthoritiesExtractor authoritiesExtractor() {
        return map -> {
            String username = (String) map.get("login");
            GrantedAuthority authority = bitlyUserRepository.findByUsername(username)
                    .map(b -> b.getRole().getAuthority())
                    .orElse(Role.GUEST.getAuthority());
            return singletonList(authority);
        };
    }

    @Bean
    @ConfigurationProperties("bitly.client")
    public OAuth2ProtectedResourceDetails bitlyClient() {
        return new AuthorizationCodeResourceDetails();
    }

    @Bean
    @ConfigurationProperties("bitly.resource")
    public ResourceServerProperties bitlyResource() {
        return new ResourceServerProperties();
    }

    @Bean
    public FilterRegistrationBean oauth2ClientFilterRegistration(OAuth2ClientContextFilter filter) {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(filter);
        registration.setOrder(-100);
        return registration;
    }
}