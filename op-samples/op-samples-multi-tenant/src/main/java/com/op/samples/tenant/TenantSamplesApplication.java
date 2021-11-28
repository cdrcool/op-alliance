package com.op.samples.tenant;

import com.op.samples.tenant.entity.Menu;
import com.op.samples.tenant.mapper.MenuMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

/**
 * 应用启动类
 *
 * @author cdrcool
 */
@Slf4j
@SpringBootApplication
public class TenantSamplesApplication implements CommandLineRunner {
    private final MenuMapper menuMapper;

    public TenantSamplesApplication(MenuMapper menuMapper) {
        this.menuMapper = menuMapper;
    }

    public static void main(String[] args) {
        SpringApplication.run(TenantSamplesApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        List<Menu> menus = menuMapper.selectAll();
        log.info("menus: {}", menus);
    }
}
