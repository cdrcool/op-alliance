package com.op.authorization.feignclient;

import com.op.authorization.feignclient.dto.OauthClientDetailsDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * oauth-client Feign Client
 *
 * @author cdrcool
 */
@FeignClient(value = "op-admin", contextId = "oauthClientDetails")
public interface OauthClientFeignClient {

    /**
     * 根据客户端标识查找 oauth-client
     *
     * @param clientId 客户端标识
     * @return oauth-client dto
     */
    @GetMapping("/oauthClientDetails/getByClientId")
    OauthClientDetailsDTO getByClientId(@RequestParam("clientId") String clientId);
}
