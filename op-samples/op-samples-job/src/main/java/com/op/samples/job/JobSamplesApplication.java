package com.op.samples.job;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 应用启动类
 *
 * @author cdrcool
 */
@EnableScheduling
@SpringBootApplication
public class JobSamplesApplication {

    public static void main(String[] args) {
        SpringApplication.run(JobSamplesApplication.class, args);
    }
}
