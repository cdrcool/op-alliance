package com.op.boot.mall;

import com.op.boot.mall.properties.JdMallProperties;
import com.op.boot.mall.properties.SnMallProperties;
import com.op.boot.mall.properties.ZkhMallProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 电商采购自动装配类
 *
 * @author chengdr01
 */
@EnableConfigurationProperties({JdMallProperties.class, SnMallProperties.class, ZkhMallProperties.class})
@Configuration
public class MallAutoConfig {
}
