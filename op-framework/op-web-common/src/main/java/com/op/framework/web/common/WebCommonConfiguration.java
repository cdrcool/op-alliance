package com.op.framework.web.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.op.framework.web.common.api.response.ApiResponseAdvice;
import com.op.framework.web.common.persistence.audit.AuditingInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Web Common 配置类
 *
 * @author cdrcool
 */
@Configuration
public class WebCommonConfiguration {

    /**
     * API 响应通知类
     */
    @ConditionalOnMissingBean
    @Bean
    public ApiResponseAdvice apiResponseAdvice(ObjectMapper objectMapper) {
        return new ApiResponseAdvice(objectMapper);
    }

    /**
     * Mybatis 审计拦截器
     */
    @Bean
    public AuditingInterceptor auditingInterceptor() {
        return new AuditingInterceptor();
    }
}
