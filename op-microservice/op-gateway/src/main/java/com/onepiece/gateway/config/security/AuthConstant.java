package com.onepiece.gateway.config.security;

/**
 * 权限相关常量定义
 *
 * @author cdrcool
 */
public interface AuthConstant {
    /**
     * JWT 存储权限属性
     */
    String AUTHORITY_CLAIM_NAME = "authorities";

    /**
     * 认证信息 Http 请求头
     */
    String JWT_TOKEN_HEADER = "Authorization";

    /**
     * JWT 令牌前缀
     */
    String JWT_TOKEN_PREFIX = "Bearer ";

    /**
     * 安全上下文 Http 请求头
     */
    String SECURITY_CONTEXT_HEADER = "security-context";

}
