package com.op.framework.boot.sdk.client.feign;

import com.op.framework.boot.sdk.client.feign.config.SnAuthFeignConfig;
import com.op.framework.boot.sdk.client.response.SnTokenResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 京东 Token Feign Client
 *
 * @author chengdr01
 */
@FeignClient(name = "sn-auth", url = "${sdk.third.sn.auth-url}", configuration = SnAuthFeignConfig.class)
public interface SnTokenFeignClient {

    /**
     * 该接口用于校验客户端登录参数，并提供用于换取 token 的鉴权码
     * 发起请求成功后，服务端会重定向（http302）至客户提供的鉴权码回调地址，并传递鉴权码（code）
     *
     * @param clientId     应用客户端标识
     * @param redirectUri  授权响应重定向 URI
     * @param responseType 权请求响应类型，固定值：code
     * @param scope        授权范围，暂不使用
     * @param state        由 client 使用的不透明参数，用于请求阶段和回调阶段之间的状态保持
     * @param itemcode     线上订购应用服务的版本id
     * @return 响应字符串
     */
    @GetMapping("/authorize")
    String authorize(@RequestParam("client_id") String clientId,
                     @RequestParam("redirect_uri") String redirectUri,
                     @RequestParam("response_type") String responseType,
                     @RequestParam String scope,
                     @RequestParam String state,
                     @RequestParam String itemcode);

    /**
     * 使用鉴权码获取访问令牌
     *
     * @param clientId     应用客户端标识
     * @param clientSecret 应用客户端密钥
     * @param code         鉴权码
     * @param grantType    授权许可类型，固定值：authorization_code
     * @param redirectUri  授权响应重定向 URI
     * @return 苏宁 Token 响应
     */
    @GetMapping("/token")
    SnTokenResponse getToken(@RequestParam("client_id") String clientId,
                             @RequestParam("client_secret") String clientSecret,
                             @RequestParam String code,
                             @RequestParam("grant_type") String grantType,
                             @RequestParam("redirect_uri") String redirectUri);

    /**
     * 刷新 token
     *
     * @param clientId     应用客户端标识
     * @param clientSecret 应用客户端密钥
     * @param grantType    授权许可类型，固定值：refresh_token
     * @param refreshToken 刷新token
     * @param redirectUri  授权响应重定向 URI
     * @return 苏宁 Token 响应
     */
    @PostMapping("/refresh_token")
    SnTokenResponse refreshToken(@RequestParam("client_id") String clientId,
                                 @RequestParam("client_secret") String clientSecret,
                                 @RequestParam("grant_type") String grantType,
                                 @RequestParam("refresh_token") String refreshToken,
                                 @RequestParam("redirect_uri") String redirectUri);
}
