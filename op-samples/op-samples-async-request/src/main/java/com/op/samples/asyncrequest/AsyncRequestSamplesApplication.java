package com.op.samples.asyncrequest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * 应用启动类
 *
 * @author cdrcool
 */
@EnableAsync
@SpringBootApplication
public class AsyncRequestSamplesApplication {

    public static void main(String[] args) {
        SpringApplication.run(AsyncRequestSamplesApplication.class, args);
    }
}
