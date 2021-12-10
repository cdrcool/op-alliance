package com.op.boot.mall.token.granter;

import com.op.boot.mall.constants.MallType;
import com.op.boot.mall.token.request.TokenAcquireRequest;
import com.op.boot.mall.token.request.TokenRefreshRequest;
import com.op.boot.mall.token.response.MallTokenResponse;

import java.util.Optional;

/**
 * 电商 Token Granter
 *
 * @author chengdr01
 */
public interface MallTokenGranter {

    /**
     * 获取电商 Token
     *
     * @param tokenAcquireRequest Token 获取请求
     * @return 电商 Token 响应
     */
    Optional<MallTokenResponse> acquireToken(TokenAcquireRequest tokenAcquireRequest);

    /**
     * 刷新电商 Token
     *
     * @param tokenRefreshRequest Token 刷新请求
     * @return 电商 Token 响应
     */
    MallTokenResponse refreshToken(TokenRefreshRequest tokenRefreshRequest);

    /**
     * 如果可以处理当前请求，就返回 true
     *
     * @param mallType 电商类型
     * @return true or false
     */
    boolean supports(MallType mallType);
}
