package com.op.sdk.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 应用启动类
 *
 * @author cdrcool
 */
@EnableScheduling
@EnableAsync
@EnableFeignClients
@SpringBootApplication
public class SdkClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(SdkClientApplication.class, args);
    }
}
