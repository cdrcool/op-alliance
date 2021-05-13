package com.op.web.config;

import io.swagger.models.auth.In;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

/**
 * Swagger 配置类
 *
 * @author cdrcool
 */
@EnableOpenApi
@EnableConfigurationProperties(SwaggerProperties.class)
@Configuration
public class SwaggerConfig implements WebMvcConfigurer {
    private final SwaggerProperties properties;

    public SwaggerConfig(SwaggerProperties properties) {
        this.properties = properties;
    }

    /**
     * 全局 Docket
     */
    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.OAS_30).pathMapping("/")
                // 设置是否开启 Swagger
                .enable(properties.getEnable())
                // 设置 API 元信息
                .apiInfo(apiInfo())
                // 设置支持的通讯协议集合
                .protocols(new HashSet<>(Arrays.asList("https", "http")))
                // 设置发布哪些接口文档
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                // 设置授权信息
                .securitySchemes(securitySchemes())
                // 设置授权信息全局应用
                .securityContexts(securityContexts())
                // 设置接口调试地址
                .host(properties.getTryHost());
    }

    /**
     * 设置 API 元信息
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title(properties.getAppName() + " Api Doc")
                .description(properties.getAppDesc())
                .version(properties.getAppVersion())
                .contact(new Contact(properties.getContact().getName(), properties.getContact().getUrl(), properties.getContact().getEmail()))
                .build();
    }

    /**
     * 设置授权信息
     */
    private List<SecurityScheme> securitySchemes() {
        ApiKey apiKey = new ApiKey("Authorization", "token", In.HEADER.toValue());
        return Collections.singletonList(apiKey);
    }

    /**
     * 设置授权信息全局应用
     */
    private List<SecurityContext> securityContexts() {
        return Collections.singletonList(
                SecurityContext.builder()
                        .securityReferences(Collections.singletonList(
                                new SecurityReference(
                                        "Authorization",
                                        new AuthorizationScope[]{new AuthorizationScope("global", "")})))
                        .build()
        );
    }

    /**
     * 拦截器排除 Swagger 设置，所有拦截器都会自动排除 Swagger 的相关资源
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        Field registrationsField = ReflectionUtils.findField(InterceptorRegistry.class, "registrations");
        if (registrationsField != null) {
            registrationsField.setAccessible(true);
            @SuppressWarnings("unchecked")
            List<InterceptorRegistration> registrations = (List<InterceptorRegistration>) ReflectionUtils.getField(registrationsField, registry);
            if (registrations != null) {
                registrations.forEach(registration -> registration
                        .excludePathPatterns("/swagger**/**")
                        .excludePathPatterns("/webjars/**")
                        .excludePathPatterns("/v3/**"));
            }
        }
    }

}
