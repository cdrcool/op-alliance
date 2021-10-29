package com.op.boot.mall;

import com.op.boot.mall.properties.JdMallProperties;
import com.op.boot.mall.properties.SnMallProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 电商采购自动装配类
 *
 * @author cdrcool
 */
@EnableFeignClients(basePackageClasses = MallPurchaseAutoConfig.class)
@EnableConfigurationProperties({JdMallProperties.class, SnMallProperties.class})
@ComponentScan(basePackageClasses = MallPurchaseAutoConfig.class)
@Configuration
public class MallPurchaseAutoConfig {
}
