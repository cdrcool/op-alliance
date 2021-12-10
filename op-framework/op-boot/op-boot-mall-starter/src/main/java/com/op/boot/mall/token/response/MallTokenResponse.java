package com.op.boot.mall.token.response;

import com.op.boot.mall.constants.MallType;
import lombok.Builder;
import lombok.Data;

/**
 * 电商 Token 响应
 *
 * @author chengdr01
 */
@Builder
@Data
public class MallTokenResponse {
    /**
     * 电商类型
     */
    private MallType mallType;

    /**
     * 账号名
     */
    private String accountName;

    /**
     * 访问令牌
     */
    private String accessToken;

    /**
     * 刷新令牌
     */
    private String refreshToken;

    /**
     * 访问令牌过期时间
     */
    private Long accessTokenExpiresAt;

    /**
     * 刷新令牌过期时间
     */
    private Long refreshTokenExpiresAt;

    /**
     * 未返回刷新令牌过期时间时，默认为 7 天
     * <p>
     * 京东返回的改字段没有值
     *
     * @return 刷新令牌过期时间
     */
    public Long getRefreshTokenExpiresAt() {
        return refreshTokenExpiresAt == null ? System.currentTimeMillis() + 7 * 86400 * 1000 : refreshTokenExpiresAt;
    }
}
