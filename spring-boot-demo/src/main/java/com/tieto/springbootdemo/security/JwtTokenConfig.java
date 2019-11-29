package com.tieto.springbootdemo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
public class JwtTokenConfig {
    @Bean
    public TokenStore jwtTokenStoreBean(){
        return new JwtTokenStore(jwtAccessTokenConverterBean());
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverterBean(){
        JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
        accessTokenConverter.setSigningKey("internet_plus");
        return accessTokenConverter;
    }
}
