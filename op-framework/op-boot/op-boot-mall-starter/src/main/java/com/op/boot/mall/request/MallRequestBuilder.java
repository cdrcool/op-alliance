package com.op.boot.mall.request;

import com.op.boot.mall.client.MallAuthentication;
import com.op.boot.mall.client.MallAuthenticationManager;
import com.op.boot.mall.constans.MallType;

/**
 * 商城请求构造者
 *
 * @author cdrcool
 */
public abstract class MallRequestBuilder {
    /**
     * 电商身份认证凭据管理者
     */
    private final MallAuthenticationManager mallAuthenticationManager;

    public MallRequestBuilder(MallAuthenticationManager mallAuthenticationManager) {
        this.mallAuthenticationManager = mallAuthenticationManager;
    }

    /**
     * 获取电商身份认证凭据
     *
     * @param mallType   电商类型
     * @param taxpayerId 纳税人识别号
     * @return 电商身份认证凭据
     */
    protected final MallAuthentication loadMallAuthentication(MallType mallType, String taxpayerId) {
        return mallAuthenticationManager.loadAuthentication(mallType, taxpayerId);
    }

    /**
     * 如果可以处理当前请求，就返回 true
     *
     * @param mallType 电商类型
     * @return true or false
     */
    public abstract boolean supports(MallType mallType);
}
