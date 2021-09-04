package com.onepiece.gateway.config.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;
import org.springframework.util.PathMatcher;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.onepiece.gateway.config.security.AuthConstant.REMOVE_AUTHORIZATION_FLAG;

/**
 * 鉴权管理器，用于判断是否有资源访问权限
 *
 * @author cdrcool
 */
@Slf4j
@Component
public class AuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {
    /**
     * 权限前缀
     */
    private static final String AUTHORITY_PREFIX = "SCOPE_";
    /**
     * 白名单资源缓存 key
     */
    public static final String WHITE_RESOURCE_KEY = "op-admin::whiteResourcePaths::";
    /**
     * 资源路径及相应权限缓存 key
     */
    private static final String RESOURCE_PERMISSION_KEY = "op-admin::resourcePathPermissions::";

    private final RedisTemplate<String, Object> redisTemplate;

    public AuthorizationManager(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> mono, AuthorizationContext context) {
        ServerHttpRequest request = context.getExchange().getRequest();
        URI uri = request.getURI();

        PathMatcher pathMatcher = new AntPathMatcher();

        // 对跨域的预检请求直接放行
        if (request.getMethod() == HttpMethod.OPTIONS) {
            return Mono.just(new AuthorizationDecision(true));
        }

        // 白名单路径直接放行
        List<String> whiteResources = (List<String>) redisTemplate.opsForValue().get(WHITE_RESOURCE_KEY);
        if (whiteResources == null) {
            whiteResources = new ArrayList<>();
        }
        if (!CollectionUtils.isEmpty(whiteResources) && whiteResources.stream()
                .map(url -> url.replace(REMOVE_AUTHORIZATION_FLAG, ""))
                .anyMatch(pattern -> pathMatcher.match(pattern, uri.getPath()))) {
            return Mono.just(new AuthorizationDecision(true));
        }

        // 从缓存中获取资源路径及其所需的权限
        Map<String, String> resourcePathPermissionsMap = (Map<String, String>) redisTemplate.opsForValue().get(RESOURCE_PERMISSION_KEY);
        if (resourcePathPermissionsMap == null) {
            resourcePathPermissionsMap = new HashMap<>(0);
        }

        List<String> authorities = resourcePathPermissionsMap.keySet().stream()
                .filter(url -> pathMatcher.match(url, uri.getPath()))
                .map(resourcePathPermissionsMap::get)
                .map(item -> AUTHORITY_PREFIX + item)
                .collect(Collectors.toList());

        // 对未设置权限的资源直接放行
        if (CollectionUtils.isEmpty(authorities)) {
            return Mono.just(new AuthorizationDecision(true));
        }

        return mono
                .filter(Authentication::isAuthenticated)
                .flatMapIterable(Authentication::getAuthorities)
                .map(GrantedAuthority::getAuthority)
                .any(authorities::contains)
                .map(AuthorizationDecision::new)
                .defaultIfEmpty(new AuthorizationDecision(false));
    }
}
