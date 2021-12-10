package com.op.boot.mall.token.request.zkh;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

/**
 * 震坤行 Token 获取请求
 *
 * @author chengdr01
 */
@Builder
@Data
public class ZkhTokenAcquireRequest {
    /**
     * 对接账号
     */
    @JsonProperty("client_id")
    private String clientId;

    /**
     * 即对接账号的密码 (由震坤行人员提供)
     */
    @JsonProperty("client_secret")
    private String clientSecret;

    /**
     * 当前时间，格式（yyyy-MM-dd hh:mm:ss） 与震坤行服务器时差不能相差半小时以上，震坤行服务器时间为北京时间
     */
    @JsonProperty("timestamp")
    private String timestamp;

    /**
     * 震坤行用户名
     */
    @JsonProperty("timestamp")
    private String username;

    /**
     * 震坤行的密码，该字段需要把震坤行提供的密码进行 32 位 md5 加密，然后将结果转成小写进行传输。
     */
    @JsonProperty("timestamp")
    private String password;
}
