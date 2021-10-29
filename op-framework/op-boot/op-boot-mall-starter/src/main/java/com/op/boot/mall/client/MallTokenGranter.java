package com.op.boot.mall.client;

import com.op.boot.mall.constans.MallType;

import java.util.Map;
import java.util.Optional;

/**
 * 电商 Token 授予者
 *
 * @author cdrcool
 */
public interface MallTokenGranter {

    /**
     * 申请电商 token
     *
     * @param params 请求参数
     * @return 电商 token
     */
    Optional<MallTokenResponse> requestToken(Map<String, String> params);

    /**
     * 刷新电商 token
     *
     * @param params 请求参数
     * @return 电商token
     */
    MallTokenResponse refreshToken(Map<String, String> params);

    /**
     * 如果可以处理当前请求，就返回 true
     *
     * @param mallType 电商类型
     * @return true or false
     */
    boolean supports(MallType mallType);
}
