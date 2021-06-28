package com.op.authorization;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 应用启动类
 *
 * @author cdrcool
 */
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class AuthorizationPastApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthorizationPastApplication.class, args);
    }
}
