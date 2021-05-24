package com.op.framework.boot.sdk.client;

import com.op.framework.boot.sdk.client.account.controller.JdAccountController;
import com.op.framework.boot.sdk.client.account.controller.SnAccountController;
import com.op.framework.boot.sdk.client.account.mapper.ThirdAccountMapper;
import com.op.framework.boot.sdk.client.account.service.JdAccountServiceImpl;
import com.op.framework.boot.sdk.client.account.service.SnAccountServiceImpl;
import com.op.framework.boot.sdk.client.account.service.ThirdAccountService;
import com.op.framework.boot.sdk.client.account.task.ThirdTokenRefreshTask;
import com.op.framework.boot.sdk.client.api.impl.JdAreaApiImpl;
import com.op.framework.boot.sdk.client.api.impl.JdInvoiceApiImpl;
import com.op.framework.boot.sdk.client.api.impl.SnAreaApiImpl;
import com.op.framework.boot.sdk.client.api.impl.SnInvoiceApiImpl;
import com.op.framework.boot.sdk.client.base.JdSdkClient;
import com.op.framework.boot.sdk.client.base.JdSdkClientAdapter;
import com.op.framework.boot.sdk.client.base.SnSdkClient;
import com.op.framework.boot.sdk.client.base.SnSdkClientAdapter;
import com.op.framework.boot.sdk.client.feign.JdAuthFeignClient;
import com.op.framework.boot.sdk.client.feign.JdSdkFeignClient;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
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
@EnableFeignClients(basePackages = "com.op.framework.boot.sdk.client.feign")
@MapperScan(basePackages = "com.op.framework.boot.sdk.client.account.mapper")
@EnableConfigurationProperties(SdkProperties.class)
@Configuration
public class SdkAutoConfiguration {
    private final SdkProperties sdkProperties;
    private final ThirdAccountMapper thirdAccountMapper;
    private final RedisTemplate<String, Object> redisTemplate;
    private  JdAuthFeignClient jdAuthFeignClient;
    private  JdSdkFeignClient jdSdkFeignClient;

    public SdkAutoConfiguration(SdkProperties sdkProperties,
                                ThirdAccountMapper thirdAccountMapper,
                                RedisTemplate<String, Object> redisTemplate) {
        this.sdkProperties = sdkProperties;
        this.thirdAccountMapper = thirdAccountMapper;
        this.redisTemplate = redisTemplate;
    }

    @Bean
    public JdAccountController jdAccountController() {
        return new JdAccountController(jdAccountService());
    }

    @Bean
    public JdAccountServiceImpl jdAccountService() {
        return new JdAccountServiceImpl(sdkProperties, thirdAccountMapper, jdAuthFeignClient, redisTemplate);
    }

    @Bean
    public SnAccountController snAccountController() {
        return new SnAccountController(snAccountService());
    }

    @Bean
    public SnAccountServiceImpl snAccountService() {
        return new SnAccountServiceImpl(sdkProperties, thirdAccountMapper, redisTemplate);
    }

    @ConditionalOnProperty(prefix = "sdk", value = "refresh-token-cron")
    @Bean
    public ThirdTokenRefreshTask thirdTokenRefreshTask(Map<String, ThirdAccountService> thirdAccountServices) {
        return new ThirdTokenRefreshTask(thirdAccountServices);
    }

    @Bean
    public JdAreaApiImpl jdAreaApi() {
        return new JdAreaApiImpl(jdSdkClient());
    }

    @Bean
    public JdInvoiceApiImpl jdInvoiceApi() {
        return new JdInvoiceApiImpl(jdSdkClient());
    }

    @Bean
    public JdSdkClient jdSdkClient() {
        String requestType = sdkProperties.getRequestType();
        return "feign".equals(requestType) ? jdSdkFeignClient : new JdSdkClientAdapter(sdkProperties, jdAccountService());
    }

    @Bean
    public SnAreaApiImpl snAreaApi() {
        return new SnAreaApiImpl(snSdkClient());
    }

    @Bean
    public SnInvoiceApiImpl snInvoiceApi() {
        return new SnInvoiceApiImpl(snSdkClient());
    }

    @Bean
    public SnSdkClient snSdkClient() {
        return new SnSdkClientAdapter(sdkProperties, snAccountService());
    }

    @Autowired
    public void setJdAuthFeignClient(JdAuthFeignClient jdAuthFeignClient) {
        this.jdAuthFeignClient = jdAuthFeignClient;
    }

    @Autowired
    public void setJdSdkFeignClient(JdSdkFeignClient jdSdkFeignClient) {
        this.jdSdkFeignClient = jdSdkFeignClient;
    }
}
