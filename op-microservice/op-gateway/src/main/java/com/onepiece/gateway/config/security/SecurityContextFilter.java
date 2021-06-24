package com.onepiece.gateway.config.security;

import com.nimbusds.jose.JWSObject;
import com.op.framework.web.common.api.response.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.text.ParseException;

/**
 * 解析 JWT 信息，并将解析后的值放入请求的 header 中
 *
 * @author cdrcool
 */
@Slf4j
@Component
public class SecurityContextFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String token = exchange.getRequest().getHeaders().getFirst(AuthConstant.JWT_TOKEN_HEADER);
        if (StringUtils.isEmpty(token)) {
            return chain.filter(exchange);
        }

        try {
            String realToken = token.replace(AuthConstant.JWT_TOKEN_PREFIX, "");
            JWSObject jwsObject = JWSObject.parse(realToken);
            String payload = jwsObject.getPayload().toString();
            ServerHttpRequest request = exchange.getRequest().mutate().header(AuthConstant.SECURITY_CONTEXT_HEADER, payload).build();
            exchange = exchange.mutate().request(request).build();
        } catch (ParseException e) {
            log.error("解析 token 异常", e);
            throw new BusinessException("解析 token 异常", e);
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
