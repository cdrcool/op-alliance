package com.onepiece.gateway.config.security;

import com.onepiece.gateway.feignclient.ResourcePermissionFeignClient;
import com.onepiece.gateway.feignclient.WhiteResourceFeignClient;
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
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    public static final String WHITE_RESOURCE_KEY = "white:resource";
    /**
     * 资源及相应权限缓存 key
     */
    private static final String RESOURCE_PERMISSION_KEY = "resource:permission";

    private final RedisTemplate<String, Object> redisTemplate;
    private final WhiteResourceFeignClient whiteResourceFeignClient;
    private final ResourcePermissionFeignClient resourcePermissionFeignClient;

    public AuthorizationManager(RedisTemplate<String, Object> redisTemplate,
                                WhiteResourceFeignClient whiteResourceFeignClient,
                                ResourcePermissionFeignClient resourcePermissionFeignClient) {
        this.redisTemplate = redisTemplate;
        this.whiteResourceFeignClient = whiteResourceFeignClient;
        this.resourcePermissionFeignClient = resourcePermissionFeignClient;
    }

    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> mono, AuthorizationContext context) {
        ServerHttpRequest request = context.getExchange().getRequest();
        URI uri = request.getURI();

        PathMatcher pathMatcher = new AntPathMatcher();

        // 白名单路径直接放行
        List<String> whiteResources = (List<String>) redisTemplate.opsForValue().get(WHITE_RESOURCE_KEY);
        if (whiteResources == null) {
            whiteResources = whiteResourceFeignClient.getWhiteResourcePaths();
        }
        if (!CollectionUtils.isEmpty(whiteResources) && whiteResources.stream()
                .anyMatch(pattern -> pathMatcher.match(pattern, uri.getPath()))) {
            return Mono.just(new AuthorizationDecision(true));
        }

        // 对跨域的预检请求直接放行
        if (request.getMethod() == HttpMethod.OPTIONS) {
            return Mono.just(new AuthorizationDecision(true));
        }

        // 从缓存中获取资源动作及其所需的权限
        Map<String, String> resourceActionPermissionsMap;
        if (redisTemplate.hasKey(RESOURCE_PERMISSION_KEY)) {
            Map<Object, Object> cacheMap = redisTemplate.opsForHash().entries(RESOURCE_PERMISSION_KEY);
            resourceActionPermissionsMap = cacheMap.entrySet().stream()
                    .collect(Collectors.toMap(e -> (String) e.getKey(), e -> (String) e.getValue()));
        } else {
            resourceActionPermissionsMap = resourcePermissionFeignClient.refreshResourcePermissions();
        }

        List<String> authorities = resourceActionPermissionsMap.keySet().stream()
                .filter(url -> pathMatcher.match(url, uri.getPath()))
                .map(resourceActionPermissionsMap::get)
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
