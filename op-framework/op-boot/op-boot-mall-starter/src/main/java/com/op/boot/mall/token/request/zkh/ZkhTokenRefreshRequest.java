package com.op.boot.mall.token.request.zkh;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

/**
 * 震坤行 Token 刷新请求
 *
 * @author chengdr01
 */
@Builder
@Data
public class ZkhTokenRefreshRequest {
    /**
     * 授权时获取的 refresh_token
     */
    @JsonProperty("refresh_token")
    private String refreshToken;

    /**
     * 即对接账号（由震坤行人员提供）
     */
    @JsonProperty("client_id")
    private String clientId;

    /**
     * 即对接账号的密码 (由震坤行人员提供)
     */
    @JsonProperty("client_secret")
    private String clientSecret;
}
