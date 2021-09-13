package com.op.mall.business;

import com.op.mall.client.MallAuthenticationManager;
import com.op.mall.constans.MallType;

/**
 * 商城请求构造者
 *
 * @author chengdr01
 */
public abstract class MallRequestBuilder {
    /**
     * 电商发票请求构造者
     */
    private final MallAuthenticationManager mallAuthenticationManager;

    public MallRequestBuilder(MallAuthenticationManager mallAuthenticationManager) {
        this.mallAuthenticationManager = mallAuthenticationManager;
    }

    /**
     * 返回电商身份认证凭据管理者
     *
     * @return 电商身份认证凭据管理者
     */
    public final MallAuthenticationManager getMallAuthenticationManager() {
        return mallAuthenticationManager;
    }

    /**
     * 如果可以处理当前请求，就返回 true
     *
     * @param mallType 电商类型
     * @return true or false
     */
    public abstract boolean supports(MallType mallType);
}
