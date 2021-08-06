package com.op.framework.web.common.security;

/**
 * 安全上下文存放类
 *
 * @author chengdr01
 */
public class SecurityContextHolder {
    private static final ThreadLocal<SecurityContext> CONTEXT_HOLDER = new ThreadLocal<>();

    public static void clearContext() {
        CONTEXT_HOLDER.remove();
    }

    public static SecurityContext getContext() {
        SecurityContext ctx = CONTEXT_HOLDER.get();
        if (ctx == null) {
            ctx = SecurityContext.builder().build();
            CONTEXT_HOLDER.set(ctx);
        }
        return ctx;
    }

    public static void setContext(SecurityContext context) {
        CONTEXT_HOLDER.set(context);
    }
}
