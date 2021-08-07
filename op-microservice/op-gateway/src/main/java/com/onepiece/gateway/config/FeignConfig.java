package com.onepiece.gateway.config;

import feign.codec.Decoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.boot.autoconfigure.http.HttpMessageConvertersAutoConfiguration;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

/**
 * Feign 配置类
 *
 * @author cdrcool
 */
@Configuration
public class FeignConfig {

    /**
     * 默认非 Reactive 项目才会注入
     *
     * @see HttpMessageConvertersAutoConfiguration
     */
    @Bean
    public Decoder decoder() {
        return new ResponseEntityDecoder(new SpringDecoder(httpMessageConverter()));
    }

    public ObjectFactory<HttpMessageConverters> httpMessageConverter() {
        HttpMessageConverters httpMessageConverters = new HttpMessageConverters(new MappingJackson2HttpMessageConverter());
        return () -> httpMessageConverters;
    }
}
