package com.op.framework.boot.sdk.client;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * sdk 自动装配类
 *
 * @author cdrcool
 */
@EnableScheduling
@EnableAsync
@EnableFeignClients
@MapperScan(basePackages = "com.op.sdk.client.account.mapper")
@EnableConfigurationProperties(SdkProperties.class)
@ComponentScan
@Configuration
public class SdkAutoConfiguration {
}
