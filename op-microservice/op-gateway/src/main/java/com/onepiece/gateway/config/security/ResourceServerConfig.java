package com.onepiece.gateway.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import reactor.core.publisher.Mono;

/**
 * 资源服务器配置类
 *
 * @author cdrcool
 */
@EnableWebFluxSecurity
public class ResourceServerConfig {
    private final ReactiveAuthorizationManager<AuthorizationContext> authorizationManager;
    private final ServerAuthenticationEntryPoint serverAuthenticationEntryPoint;
    private final ServerAccessDeniedHandler serverAccessDeniedHandler;
    private final WhiteListRemoveJwtFilter whiteListRemoveJwtFilter;

    public ResourceServerConfig(ReactiveAuthorizationManager<AuthorizationContext> authorizationManager,
                                ServerAuthenticationEntryPoint serverAuthenticationEntryPoint,
                                ServerAccessDeniedHandler serverAccessDeniedHandler,
                                WhiteListRemoveJwtFilter whiteListRemoveJwtFilter) {
        this.authorizationManager = authorizationManager;
        this.serverAuthenticationEntryPoint = serverAuthenticationEntryPoint;
        this.serverAccessDeniedHandler = serverAccessDeniedHandler;
        this.whiteListRemoveJwtFilter = whiteListRemoveJwtFilter;
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http
                .csrf().disable()
                .authorizeExchange()
                .anyExchange().access(authorizationManager).and()
                .exceptionHandling()
                .authenticationEntryPoint(serverAuthenticationEntryPoint)
                .accessDeniedHandler(serverAccessDeniedHandler).and()
                .oauth2ResourceServer().jwt().jwtAuthenticationConverter(jwtAuthenticationConverter()).and().and()
                // 对白名单路径，直接移除 JWT 请求头
                .addFilterBefore(whiteListRemoveJwtFilter, SecurityWebFiltersOrder.AUTHENTICATION);
        return http.build();
    }

    /**
     * 从 JWT 中解析 authorities（默认是取值为 client 的 scope）
     */
    @Bean
    public Converter<Jwt, ? extends Mono<? extends AbstractAuthenticationToken>> jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName(AuthConstant.AUTHORITY_CLAIM_NAME);
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        return new ReactiveJwtAuthenticationConverterAdapter(jwtAuthenticationConverter);
    }
}
