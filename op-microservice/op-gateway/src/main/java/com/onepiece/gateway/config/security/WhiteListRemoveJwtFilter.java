package com.onepiece.gateway.config.security;

import org.springframework.data.redis.core.RedisTemplate;
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

import static com.onepiece.gateway.config.security.AuthConstant.REMOVE_AUTHORIZATION_FLAG;
import static com.onepiece.gateway.config.security.AuthorizationManager.WHITE_RESOURCE_KEY;

/**
 * 访问白名单路径时，需要移除 JWT 请求头
 *
 * @author cdrcool
 */
@Component
public class WhiteListRemoveJwtFilter implements WebFilter {
    private final RedisTemplate<String, Object> redisTemplate;

    public WhiteListRemoveJwtFilter(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        URI uri = request.getURI();

        PathMatcher pathMatcher = new AntPathMatcher();

        List<String> whiteResources = (List<String>) redisTemplate.opsForValue().get(WHITE_RESOURCE_KEY);
        if (whiteResources == null) {
            whiteResources = new ArrayList<>();
        }
        Optional<String> optional = whiteResources.stream()
                .filter(pattern -> pathMatcher.match(pattern.replace(REMOVE_AUTHORIZATION_FLAG, ""), uri.getPath())).findAny();
        if (optional.isPresent() && optional.get().endsWith(REMOVE_AUTHORIZATION_FLAG)) {
            request = exchange.getRequest().mutate().header(AuthConstant.JWT_TOKEN_HEADER, "").build();
            exchange = exchange.mutate().request(request).build();
            return chain.filter(exchange);
        }

        return chain.filter(exchange);
    }
}
