package com.op.boot.mall.properties;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 苏宁电商配置属性
 *
 * @author cdrcool
 */
@ConfigurationProperties(prefix = "mall.sn")
@EqualsAndHashCode(callSuper = true)
@Data
public class SnMallProperties extends BaseProperties {
    /**
     * 请求类型
     */
    private RequestType requestType;
}
