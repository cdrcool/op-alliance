package com.op.framework.boot.sdk.client;

import com.op.framework.boot.sdk.client.account.controller.JdAccountController;
import com.op.framework.boot.sdk.client.account.controller.SnAccountController;
import com.op.framework.boot.sdk.client.account.controller.ThirdAccountController;
import com.op.framework.boot.sdk.client.account.mapper.ThirdAccountMapper;
import com.op.framework.boot.sdk.client.account.service.JdAccountServiceImpl;
import com.op.framework.boot.sdk.client.account.service.SnAccountServiceImpl;
import com.op.framework.boot.sdk.client.account.service.ThirdAccountService;
import com.op.framework.boot.sdk.client.account.task.ThirdTokenRefreshTask;
import com.op.framework.boot.sdk.client.third.JdTokenFeignClient;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Map;

/**
 * sdk 自动装配类
 *
 * @author cdrcool
 */
@ConditionalOnBean({RedisTemplate.class})
@EnableAsync
@EnableScheduling
@EnableFeignClients
@MapperScan(basePackages = "com.op.framework.boot.sdk.client.account.mapper")
@EnableConfigurationProperties(SdkProperties.class)
@Configuration
public class SdkAutoConfiguration {
    private final SdkProperties sdkProperties;
    private final ThirdAccountMapper thirdAccountMapper;
    private final JdTokenFeignClient jdTokenFeignClient;
    private final RedisTemplate<String, Object> redisTemplate;

    public SdkAutoConfiguration(SdkProperties sdkProperties,
                                ThirdAccountMapper thirdAccountMapper,
                                JdTokenFeignClient jdTokenFeignClient, RedisTemplate<String, Object> redisTemplate) {
        this.sdkProperties = sdkProperties;
        this.thirdAccountMapper = thirdAccountMapper;
        this.jdTokenFeignClient = jdTokenFeignClient;
        this.redisTemplate = redisTemplate;
    }

    @Bean
    public JdAccountController jdAccountController() {
        return new JdAccountController(jdAccountServiceImpl());
    }

    @Bean
    public JdAccountServiceImpl jdAccountServiceImpl() {
        return new JdAccountServiceImpl(sdkProperties, thirdAccountMapper, jdTokenFeignClient, redisTemplate);
    }

    @Bean
    public SnAccountController snAccountController() {
        return new SnAccountController(jdAccountServiceImpl());
    }

    @Bean
    public SnAccountServiceImpl snAccountServiceImpl() {
        return new SnAccountServiceImpl(sdkProperties, thirdAccountMapper, redisTemplate);
    }

    @ConditionalOnProperty(prefix = "sdk", value = "refresh-token-cron")
    @Bean
    public ThirdTokenRefreshTask thirdTokenRefreshTask(Map<String, ThirdAccountService> thirdAccountServices) {
        return new ThirdTokenRefreshTask(thirdAccountServices);
    }
}
