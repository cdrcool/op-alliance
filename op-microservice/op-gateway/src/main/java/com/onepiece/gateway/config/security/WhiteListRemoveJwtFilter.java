package com.onepiece.gateway.config.security;

import com.onepiece.gateway.feignclient.WhiteResourceFeignClient;
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
import java.util.List;

import static com.onepiece.gateway.config.security.AuthorizationManager.WHITE_RESOURCE_KEY;

/**
 * 访问白名单路径时，需要移除 JWT 请求头
 *
 * @author cdrcool
 */
@Component
public class WhiteListRemoveJwtFilter implements WebFilter {
    private final RedisTemplate<String, Object> redisTemplate;
    private final WhiteResourceFeignClient whiteResourceFeignClient;

    public WhiteListRemoveJwtFilter(RedisTemplate<String, Object> redisTemplate, WhiteResourceFeignClient whiteResourceFeignClient) {
        this.redisTemplate = redisTemplate;
        this.whiteResourceFeignClient = whiteResourceFeignClient;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        URI uri = request.getURI();

        PathMatcher pathMatcher = new AntPathMatcher();

        List<String> whiteResources = (List<String>) redisTemplate.opsForValue().get(WHITE_RESOURCE_KEY);
        if (whiteResources == null) {
            whiteResources = whiteResourceFeignClient.getWhiteResourcePaths();
        }
        if (whiteResources.stream().anyMatch(pattern -> pathMatcher.match(pattern, uri.getPath()))) {
            // 不移除 JWT 请求头，不然在请求用户相关接口（菜单）时不能获取当前用户信息，后面有需要移除时再增加“是否移除”配置项
//          request = exchange.getRequest().mutate().header(AuthConstant.JWT_TOKEN_HEADER, "").build();
            exchange = exchange.mutate().request(request).build();
            return chain.filter(exchange);
        }

        return chain.filter(exchange);
    }
}
