package com.op.framework.boot.sdk.client.account.model;

import com.op.framework.boot.sdk.client.response.JdTokenResponse;
import com.op.framework.boot.sdk.client.response.SnTokenResponse;
import lombok.Data;

/**
 * 第三方Token响应
 *
 * @author chengdr01
 */
@Data
public class ThirdTokenResponse {
    /**
     * 访问令牌
     */
    private String accessToken;

    /**
     * 刷新令牌
     */
    private String refreshToken;

    /**
     * 当前时间
     */
    private Long time;

    /**
     * 访问令牌过期时间，单位：秒
     */
    private Integer expiresIn;

    /**
     * 刷新令牌过期时间，单位：秒
     */
    private Long refreshTokenExpires;

    /**
     * 账号类型（第三方不会返回该字段，由应用赋值）
     */
    private String accountType;

    /**
     * 账号名（第三方不会返回该字段，由应用赋值）
     */
    private String account;

    public static ThirdTokenResponse buildFrom(JdTokenResponse jdTokenResponse) {
        ThirdTokenResponse response = new ThirdTokenResponse();
        response.setAccessToken(jdTokenResponse.getAccessToken());
        response.setRefreshToken(jdTokenResponse.getRefreshToken());
        response.setTime(jdTokenResponse.getTime());
        response.setExpiresIn(jdTokenResponse.getExpiresIn());
        // 京东未返回刷新token过期时间，默认设置7天后过期
        response.setRefreshTokenExpires(response.getRefreshTokenExpires() == null ? (7 * 24 * 60 * 60) : response.getRefreshTokenExpires());
        return response;
    }

    public static ThirdTokenResponse buildFrom(SnTokenResponse snTokenResponse) {
        ThirdTokenResponse response = new ThirdTokenResponse();
        response.setAccessToken(snTokenResponse.getAccessToken());
        response.setRefreshToken(snTokenResponse.getRefreshToken());
        // 苏宁未返回当前时间
        response.setTime(snTokenResponse.getTime() == null ? System.currentTimeMillis() : snTokenResponse.getTime());
        response.setExpiresIn(snTokenResponse.getExpiresIn());
        response.setRefreshTokenExpires(snTokenResponse.getReExpiresIn());
        return response;
    }
}
