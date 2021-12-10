package com.op.boot.mall.authentication;

import com.op.boot.mall.constants.MallType;

/**
 * 电商身份认证凭据提供者
 *
 * @author cdrcool
 */
public interface MallAuthenticationProvider {

    /**
     * 获取电商身份认证凭据
     *
     * @param mallType 电商类型
     * @param accountName 账号名
     * @return 电商身份认证凭据
     */
    MallAuthentication loadAuthentication(MallType mallType, String accountName);
}
