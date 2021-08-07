package com.onepiece.gateway.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

/**
 * 资源权限 Feign Client
 *
 * @author cdrcool
 */
@FeignClient(value = "op-admin", contextId = "resourceCategory")
public interface ResourcePermissionFeignClient {

    /**
     * 刷新资源路径及其对应的权限
     *
     * @return 资源路径及其对应的权限 map
     */
    @PostMapping("refreshResourcePermissions")
    Map<String, String> refreshResourcePermissions();
}
