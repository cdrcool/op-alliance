package com.op.authorization.feignclient;

import com.op.authorization.feignclient.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 用户 Feign Client
 *
 * @author cdrcool
 */
@FeignClient(value = "op-admin", contextId = "user", url = "http://localhost:8082")
public interface UserFeignClient {

    /**
     * 根据用户名查找用户
     *
     * @param username 用户名
     * @return 用户 dto
     */
    @GetMapping("/user/getByUsername")
    UserDTO getByUsername(String username);
}
