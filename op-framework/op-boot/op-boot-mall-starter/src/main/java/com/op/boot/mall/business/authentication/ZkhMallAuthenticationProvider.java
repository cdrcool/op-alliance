package com.op.boot.mall.business.authentication;

import com.op.boot.mall.authentication.AbstractMallAuthenticationProvider;
import com.op.boot.mall.authentication.MallAuthentication;
import com.op.boot.mall.constants.MallType;
import org.springframework.stereotype.Component;

/**
 * 震坤行电商金彩身份认证凭据提供者
 *
 * @author cdrcool
 */
@Component
public class ZkhMallAuthenticationProvider extends AbstractMallAuthenticationProvider {

    @Override
    protected MallAuthentication loadAuthentication(String accountName) {
        return null;
    }

    @Override
    protected MallType mallType() {
        return MallType.ZKH;
    }


}
