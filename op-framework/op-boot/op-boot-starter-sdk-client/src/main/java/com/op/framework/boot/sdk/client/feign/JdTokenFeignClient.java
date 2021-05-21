package com.op.framework.boot.sdk.client.feign;

import com.op.framework.boot.sdk.client.config.JdFeignConfig;
import com.op.framework.boot.sdk.client.response.JdTokenResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 京东 feign client
 *
 * @author cdrcool
 */
@FeignClient(name = "jd", url = "${sdk.accounts.jd.auth-url}", path = "/oauth2", configuration = JdFeignConfig.class)
public interface JdTokenFeignClient {

    /**
     * 该接口用于校验客户端登录参数，并提供用于换取 token 的鉴权码
     *
     * @param appKey       应用key
     * @param redirectUri  鉴权码回调地址，需要url encode编码
     * @param username     登录用户名，需要url encode编码
     * @param password     加密密码，加密规则见下：
     *                     1、先将明文密码进行MD5；2、再使用RSA秘钥加密；3、将加密后的二进制记过使用base64编码为字符串；4、最后使用urlencode进行编码
     * @param responseType 固定值：code
     * @param scope        固定值：snsapi_base
     * @param state        由client使用的不透明参数，用于请求阶段和回调阶段之间的状态保持
     * @return 鉴权码
     */
    @GetMapping("/authorizeForVOP")
    String authorizeForVop(@RequestParam("app_key") String appKey,
                           @RequestParam("redirect_uri") String redirectUri,
                           @RequestParam("username") String username,
                           @RequestParam("password") String password,
                           @RequestParam("response_type") String responseType,
                           @RequestParam("scope") String scope,
                           @RequestParam("state") String state);


    /**
     * 使用鉴权码换取 accessToken
     *
     * @param appKey    应用key
     * @param appSecret 应用密钥
     * @param grantType 固定值：authorization_code
     * @param code      鉴权码
     * @return 京东token响应
     */
    @GetMapping("/access_token")
    JdTokenResponse accessToken(@RequestParam("app_key") String appKey,
                                @RequestParam("app_secret") String appSecret,
                                @RequestParam("grant_type") String grantType,
                                @RequestParam("code") String code);

    /**
     * token失效后, 可使用此接口刷新
     * token有效期24小时, 在1小时内重复刷新会返回相同token, 同一时间可能存在多个有效token
     *
     * @param appKey       应用key
     * @param appSecret    应用密钥
     * @param grantType    固定值：refresh_token
     * @param refreshToken 刷新token
     * @return 京东token响应
     */
    @PostMapping("/refresh_token")
    JdTokenResponse refreshToken(@RequestParam("app_key") String appKey,
                                 @RequestParam("app_secret") String appSecret,
                                 @RequestParam("grant_type") String grantType,
                                 @RequestParam("refresh_token") String refreshToken);
}
