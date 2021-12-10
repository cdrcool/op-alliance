package com.op.boot.mall.business.authentication;

import com.op.boot.mall.authentication.AbstractMallAuthenticationProvider;
import com.op.boot.mall.authentication.MallAuthentication;
import com.op.boot.mall.constants.MallType;
import org.springframework.stereotype.Component;

/**
 * 苏宁电商身份认证凭据提供者
 *
 * @author cdrcool
 */
@Component
public class SnMallAuthenticationProvider extends AbstractMallAuthenticationProvider {

    @Override
    protected MallAuthentication loadAuthentication(String accountName) {
        return null;
    }

    @Override
    protected MallType mallType() {
        return MallType.SN;
    }


}
