package com.onepiece.gateway.config.security;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 访问白名单路径时，需要移除 JWT 请求头
 *
 * @author cdrcool
 */
@Component
public class WhiteListRemoveJwtFilter implements WebFilter {
    private WhiteListConfig whiteListConfig;

    @NotNull
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, @NotNull WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        URI uri = request.getURI();

        PathMatcher pathMatcher = new AntPathMatcher();

        List<String> whiteList = Optional.ofNullable(whiteListConfig.getUrls()).orElse(new ArrayList<>());
        if (whiteList.stream().anyMatch(pattern -> pathMatcher.match(pattern, uri.getPath()))) {
            request = exchange.getRequest().mutate().header(AuthConstant.JWT_TOKEN_HEADER, "").build();
            exchange = exchange.mutate().request(request).build();
            return chain.filter(exchange);
        }

        return chain.filter(exchange);
    }

    @Autowired
    public void setWhiteListConfig(WhiteListConfig whiteListConfig) {
        this.whiteListConfig = whiteListConfig;
    }
}
