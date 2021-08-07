package com.onepiece.gateway.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * 白名单资源 Feign Client
 *
 * @author cdrcool
 */
@FeignClient(value = "op-admin", contextId = "whiteResource")
public interface WhiteResourceFeignClient {

    /**
     * 获取白名单资源路径列表
     *
     * @return 白名单资源路径列表
     */
    @GetMapping
    List<String> getWhiteResourcePaths();
}
