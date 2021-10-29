package com.op.admin.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;
import java.util.concurrent.Executor;

/**
 * Web Mvc 配置类
 *
 * @author cdrcool
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {
    /**
     * 默认的线程池
     */
    private final Executor executor;

    public WebMvcConfig(@Qualifier("applicationTaskExecutor") Executor executor) {
        this.executor = executor;
    }

    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
        configurer.setTaskExecutor((AsyncTaskExecutor) executor);
        super.configureAsyncSupport(configurer);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        // 注册 Spring Data Jpa Pageable 的参数分解器
        argumentResolvers.add(new PageableHandlerMethodArgumentResolver());
    }
}
