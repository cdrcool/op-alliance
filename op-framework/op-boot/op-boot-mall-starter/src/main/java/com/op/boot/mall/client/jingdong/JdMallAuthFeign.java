package com.op.boot.mall.client.jingdong;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 京东电商授权 Feign
 *
 * @author cdrcool
 */
@FeignClient(name = "jd-mall-auth", url = "${mall.jd.auth-url}", configuration = JdMallTokenFeignConfig.class)
public interface JdMallAuthFeign {

    /**
     * 该接口用于校验客户端登录参数，并提供用于换取 token 的鉴权码
     * 发起请求成功后，服务端会重定向（http302）至客户提供的鉴权码回调地址，并传递鉴权码（code）
     * <p>
     * code有效期5分钟
     *
     * @param appKey       应用 key
     * @param redirectUri  鉴权码回调地址，需要 url encode 编码
     * @param username     登录用户名，需要 url encode 编码
     * @param password     加密密码（加密规则：1、先将明文密码进行MD5；2、再使用RSA秘钥加密；3、将加密后的二进制记过使用base64编码为字符串；4、最后使用 url encode 进行编码）
     * @param responseType 权请求响应类型，固定值：code
     * @param scope        授权范围，固定值：snsapi_base
     * @param state        由 client 使用的不透明参数，用于请求阶段和回调阶段之间的状态保持
     * @return 响应字符串
     */
    @GetMapping("/authorizeForVOP")
    JdBaseResponse<Object> authorizeForVop(@RequestParam("app_key") String appKey,
                                           @RequestParam("redirect_uri") String redirectUri,
                                           @RequestParam String username,
                                           @RequestParam String password,
                                           @RequestParam("response_type") String responseType,
                                           @RequestParam String scope,
                                           @RequestParam String state);

    /**
     * 使用鉴权码获取 token
     *
     * @param appKey    应用 key
     * @param appSecret 应用密钥
     * @param code      鉴权码
     * @param grantType 授权许可类型，固定值：authorization_code
     * @return 京东 Token 响应
     */
    @GetMapping("/access_token")
    JdTokenResponse accessToken(@RequestParam("app_key") String appKey,
                                @RequestParam("app_secret") String appSecret,
                                @RequestParam String code,
                                @RequestParam("grant_type") String grantType);

    /**
     * 刷新 token
     *
     * @param appKey       应用 key
     * @param appSecret    应用密钥
     * @param grantType    授权许可类型，固定值：refresh_token
     * @param refreshToken 刷新 token
     * @return 京东 Token 响应
     */
    @PostMapping("/refresh_token")
    JdTokenResponse refreshToken(@RequestParam("app_key") String appKey,
                                 @RequestParam("app_secret") String appSecret,
                                 @RequestParam("grant_type") String grantType,
                                 @RequestParam("refresh_token") String refreshToken);
}
