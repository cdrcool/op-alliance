package com.op.mall.business;

import com.op.mall.client.MallAuthenticationProvider;
import com.op.mall.client.jingdong.JdMallAuthentication;
import com.op.mall.constans.MallType;

/**
 * 京东电商身份认证凭据提供者
 *
 * @author cdrcool
 */
public class JdMallAuthenticationProvider implements MallAuthenticationProvider {

    @Override
    public JdMallAuthentication loadAuthentication(String taxpayerId) {
        return null;
    }

    @Override
    public boolean supports(MallType mallType) {
        return MallType.JINGDONG.equals(mallType);
    }
}
