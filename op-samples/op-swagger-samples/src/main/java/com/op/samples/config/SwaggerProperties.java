package com.op.samples.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Swagger 配置属性
 *
 * @author chengdr01
 */
@Data
@ConfigurationProperties("swagger")
public class SwaggerProperties {
    /**
     * 是否开启 Swagger（生产环境一般关闭）
     */
    private Boolean enable = true;

    /**
     * 应用名
     */
    private String appName;

    /**
     * 应用描述
     */
    private String appDesc;

    /**
     * 应用版本
     */
    private String appVersion;

    /**
     * 联系人
     */
    private Contact contact = new Contact();

    /**
     * 接口调试地址
     */
    private String tryHost;

    @Data
    public static class Contact {
        /**
         * 联系人姓名
         */
        private String name;

        /**
         * 联系人主页
         */
        private String url;

        /**
         * 联系人邮箱
         */
        private String email;
    }
}
