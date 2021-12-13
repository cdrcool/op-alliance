package com.op.boot.mall.authentication;

import com.op.boot.mall.account.Account;
import com.op.boot.mall.account.AccountManager;
import com.op.boot.mall.constants.MallType;
import org.springframework.stereotype.Component;

/**
 * 震坤行电商金彩身份认证凭据提供者
 *
 * @author cdrcool
 */
@Component
public class ZkhMallAuthenticationProvider extends AbstractMallAuthenticationProvider {

    public ZkhMallAuthenticationProvider(AccountManager accountManager) {
        super(accountManager);
    }

    @Override
    protected MallAuthentication buildAuthentication(Account account) {
        return ZkhMallAuthentication.builder()
                .accessToken(account.getAccessToken())
                .build();
    }

    @Override
    protected MallType mallType() {
        return MallType.ZKH;
    }


}
