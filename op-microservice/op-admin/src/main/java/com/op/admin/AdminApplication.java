package com.op.admin;

import com.op.admin.entity.User;
import com.op.admin.mapper.UserMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

/**
 * 应用启动类
 *
 * @author cdrcool
 */
@SpringBootApplication
public class AdminApplication implements CommandLineRunner {
    private final UserMapper userMapper;

    public AdminApplication(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        List<User> users = userMapper.findAll();
    }
}
