package com.op.mall.client.jingdong;

import lombok.Data;

/**
 * 京东电商配置属性
 *
 * @author cdrcool
 */
@Data
public class JdMallProperties {
    /**
     * 服务地址
     */
    private String serverUrl;

    /**
     * 连接超时
     */
    private long connectTimeout;

    /**
     * 读超时
     */
    private long readTimeout;

    /**
     * 待明确
     */
    private String fuzz;
}
