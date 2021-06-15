package com.op.admin;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 应用启动类
 *
 * @author cdrcool
 */
@SpringBootApplication
public class AdminApplication implements CommandLineRunner {

    public static void main(String[] args) {
        try {
            SpringApplication.run(AdminApplication.class, args);
        } catch (Exception e) {

        }
    }

    @Override
    public void run(String... args) throws Exception {
    }
}
