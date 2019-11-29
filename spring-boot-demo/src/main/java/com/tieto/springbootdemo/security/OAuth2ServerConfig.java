package com.tieto.springbootdemo.security;

import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

@Configuration
public class OAuth2ServerConfig {
    private static final String DEMO_RESOURCE_ID = "alarm";

    @Configuration
    @EnableResourceServer
    protected static class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

        @Override
        public void configure(ResourceServerSecurityConfigurer resources) {
            resources.resourceId(DEMO_RESOURCE_ID).stateless(true);
        }

        @Override
        public void configure(HttpSecurity http) throws Exception {
            http
                    // Since we want the protected resources to be accessible in the UI as well we need
                    // session creation to be allowed (it's disabled by default in 2.0.6)
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                    .and()
                    .requestMatchers().anyRequest()
                    .and()
                    .anonymous()
                    .and()
                    .authorizeRequests()
                    .antMatchers(HttpMethod.GET,"/api/**").access("#oauth2.hasScope('query')")
                    .antMatchers(HttpMethod.POST,"/api/**").access("#oauth2.hasScope('create')")
                    .antMatchers(HttpMethod.PUT,"/api/**").access("#oauth2.hasScope('update')")
                    .antMatchers(HttpMethod.DELETE,"/api/**").access("#oauth2.hasScope('delete')");
        }
    }


    @Configuration
    @EnableAuthorizationServer
    protected static class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

        @Autowired
        AuthenticationManager authenticationManager;
        @Autowired
        UserDetailsService userDetailsService;
        @Autowired
        TokenStore jwtTokenStore;
        @Autowired
        AccessTokenConverter jwtAccessTokenConverter;
        @Autowired
        PasswordEncoder passwordEncoder;

        @Value("${app.client1.id}")
        private String client1Id;
        @Value("${app.client1.secret}")
        private String client1Secret;
        @Value("${app.client2.id}")
        private String client2Id;
        @Value("${app.client2.secret}")
        private String client2Secret;

        @Override
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
            String encoded1 = passwordEncoder.encode(client1Secret);
            String encoded2 = passwordEncoder.encode(client2Secret);

            //2 clients, 1 for password and another for client credential
            clients.inMemory().withClient(client1Id)
                    .resourceIds(DEMO_RESOURCE_ID)
                    .authorizedGrantTypes("password", "client_credentials", "refresh_token")
                    .scopes("query", "create", "update", "delete", "trust")
                    .authorities("client")
                    .secret(encoded1)
                    .and().withClient(client2Id)
                    .resourceIds(DEMO_RESOURCE_ID)
                    .authorizedGrantTypes("password", "refresh_token")
                    .scopes("query")
                    .authorities("client")
                    .secret(encoded2);
        }

        @Override
        public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
            endpoints
                    .tokenStore(jwtTokenStore)
                    .authenticationManager(authenticationManager)
                    .userDetailsService(userDetailsService)
                    .accessTokenConverter(jwtAccessTokenConverter)
                    .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.GET);

            endpoints.reuseRefreshTokens(true);
        }

        @Override
        public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
            oauthServer.allowFormAuthenticationForClients();
        }

    }
}
