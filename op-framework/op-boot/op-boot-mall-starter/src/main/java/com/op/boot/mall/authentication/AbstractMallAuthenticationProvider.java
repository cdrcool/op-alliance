package com.op.boot.mall.authentication;

import com.op.boot.mall.constants.MallType;

/**
 * 电商身份认证凭据提供者抽象类
 *
 * @author cdrcool
 */
public abstract class AbstractMallAuthenticationProvider implements MallAuthenticationProvider {

    @Override
    public MallAuthentication loadAuthentication(MallType mallType, String accountName) {
        if (!mallType().equals(mallType)) {
            return null;
        }

        return loadAuthentication(accountName);
    }


    /**
     * 获取电商身份认证凭据
     *
     * @param accountName 账号名
     * @return 电商身份认证凭据
     */
    protected abstract MallAuthentication loadAuthentication(String accountName);

    /**
     * 设置电商类型
     *
     * @return 电商类型
     */
    protected abstract MallType mallType();
}
