package com.op.framework.web.common.security;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 安全上下文
 *
 * @author cdrcool
 */
@Builder
@Data
public class SecurityContext {
    /**
     * 用户名
     */
    private final String userName;

    /**
     * 用户权限列表
     */
    private final List<String> authorities;
}
