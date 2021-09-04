package com.onepiece.gateway.config.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onepiece.gateway.response.ApiResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;


/**
 * 拒绝请求处理类
 *
 * @author cdrcool
 */
@Slf4j
@Component
public class RestfulAccessDeniedHandler implements ServerAccessDeniedHandler {
    private final ObjectMapper objectMapper;

    public RestfulAccessDeniedHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @SneakyThrows
    @Override
    public Mono<Void> handle(ServerWebExchange exchange, AccessDeniedException denied) {
        ServerHttpResponse response = exchange.getResponse();

        response.setStatusCode(HttpStatus.OK);
        response.getHeaders().set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        response.getHeaders().set("Access-Control-Allow-Origin", "*");
        response.getHeaders().set("Cache-Control", "no-cache");

        ApiResponse<Void> apiResponse = ApiResponse.<Void>builder().code(403).message("权限不够，拒绝请求").build();

        return response.writeWith(Mono.create(monoSink -> {
            try {
                byte[] bytes = objectMapper.writeValueAsBytes(apiResponse);
                DataBuffer dataBuffer = response.bufferFactory().wrap(bytes);

                monoSink.success(dataBuffer);
            } catch (JsonProcessingException jsonProcessingException) {
                log.error("ApiResponse 对象序列化异常", jsonProcessingException);
                monoSink.error(jsonProcessingException);
            }
        }));
    }
}
