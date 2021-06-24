package com.op.authorization.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.security.KeyPair;

/**
 * 遗留授权服务器（spring-security-oauth2）的一个实例，它使用一个 not-rotating 密钥并公开 JWK 端点。
 * <p>
 * 详见<a target="_blank" href="https://docs.spring.io/spring-security-oauth2-boot/docs/current-SNAPSHOT/reference/htmlsingle/">Spring Security OAuth Autoconfig's documentation</a>
 *
 * @author cdrcool
 */
@EnableAuthorizationServer
@Configuration
public class AuthorizationConfiguration extends AuthorizationServerConfigurerAdapter {
    private final AuthenticationManager authenticationManager;
    private final KeyPair keyPair;
    private final ClientDetailsService clientDetailsService;
    private final UserDetailsService userDetailsService;

    public AuthorizationConfiguration(AuthenticationConfiguration authenticationConfiguration,
                                      KeyPair keyPair,
                                      ClientDetailsService clientDetailsService,
                                      UserDetailsService userDetailsService) throws Exception {
        this.authenticationManager = authenticationConfiguration.getAuthenticationManager();
        this.keyPair = keyPair;
        this.clientDetailsService = clientDetailsService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        security
                // 客户端|密码模式下，自动进行身份验证（clientId/clientSecret）
                .allowFormAuthenticationForClients()
                // 已通过身份验证用户，可以访问 token 验证端口
                .checkTokenAccess("isAuthenticated()")
                // 开放公钥端点（不开放这个端点，会导致前端密码模式登陆时 403）
                .tokenKeyAccess("permitAll()");
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(clientDetailsService);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints
                // 以支持 OAuth2 密码模式
                .authenticationManager(this.authenticationManager)
                // 密码模式刷新 token 时会用到
                .userDetailsService(userDetailsService)
                .tokenStore(tokenStore())
                // token 转换为 jwt 格式
                .accessTokenConverter(jwtAccessTokenConverter());
    }

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        // 设置 rsa key pair
        converter.setKeyPair(this.keyPair);

        DefaultAccessTokenConverter accessTokenConverter = new DefaultAccessTokenConverter();
        // 添加 sub 属性
        accessTokenConverter.setUserTokenConverter(new SubjectAttributeUserTokenConverter());
        converter.setAccessTokenConverter(accessTokenConverter);

        return converter;
    }
}
