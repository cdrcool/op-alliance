package com.op.authorization.feignclient;

import com.op.authorization.feignclient.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 用户 Feign Client
 *
 * @author cdrcool
 */
@FeignClient(value = "op-admin", contextId = "user")
public interface UserFeignClient {

    /**
     * 根据用户名查找用户
     *
     * @param username 用户名
     * @return 用户 dto
     */
    @GetMapping("/user/getByUsername")
    UserDTO getByUsername(@RequestParam("username") String username);
}
