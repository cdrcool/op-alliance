package com.op.boot.mall.client.jingdong.bill;

import com.op.boot.mall.client.jingdong.JdBaseResponse;
import com.op.boot.mall.client.jingdong.JdMallTokenFeignConfig;
import com.op.boot.mall.client.jingdong.JdTokenResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 京东电商金采授权 Feign
 *
 * @author cdrcool
 */
@FeignClient(name = "jd-mall-bill-auth", url = "${mall.jd-bill.auth-url}", configuration = JdMallTokenFeignConfig.class)
public interface JdMallBillAuthFeign {

    /**
     * 获取Access Token
     *
     * @param grantType 授权许可类型，固定值：access_token
     * @param clientId  对接账号
     * @param timestamp 当前时间，格式（yyyy-MM-dd hh:mm:ss） 与京东服务器时差不能相差半小时以上，京东服务器时间为北京时间
     * @param username  京东用户名
     * @param password  京东的密码，该字段需要把京东提供的密码进行 32 位 md5 加密，然后将结果转成小写进行传输。
     * @param sign      签名,
     *                  生成规则如下：
     *                  1. 按照以下顺序将字符串拼接起来
     *                  client_secret+timestamp+client_id+username+password+grant_type+client_secret
     *                  其中
     *                  client_secret 的值是京东分配的，以邮件形式发送给客户。
     *                  timestamp 与同名入参传值一致。
     *                  client_id 与同名入参传值一致。
     *                  username 与同名入参传值一致。
     *                  password， 32 位小写 MD5 值，与同名入参传值一致。
     *                  grant_type，与同名入参传值一致。
     *                  2、将上述拼接的字符串使用 32 位 md5 加密，然后将结果转成大写进行传输。
     * @return com.mingyuan.framework.biz.cl.jd.token.JdTokenResponse
     */
    @PostMapping("/accessToken")
    JdBaseResponse<JdTokenResponse> getAccessToken(@RequestParam("grant_type") String grantType,
                                                   @RequestParam("client_id") String clientId,
                                                   @RequestParam("timestamp") String timestamp,
                                                   @RequestParam("username") String username,
                                                   @RequestParam("password") String password,
                                                   @RequestParam("sign") String sign);


    /**
     * 刷新 token
     *
     * @param refreshToken 授权时获取的 refresh_token
     * @param clientId     即对接账号(由京东人员提供)
     * @param clientSecret 即对接账号的密码 (由京东人员提供)
     * @return com.mingyuan.framework.biz.cl.jd.token.JdTokenResponse
     */
    @PostMapping("/refresh_token")
    JdTokenResponse refreshToken(@RequestParam("refresh_token") String refreshToken,
                                                 @RequestParam("client_id") String clientId,
                                                 @RequestParam("client_secret") String clientSecret);
}
