package com.op.boot.mall.authentication;

import com.op.boot.mall.account.Account;
import com.op.boot.mall.account.AccountManager;
import com.op.boot.mall.constants.MallType;
import org.springframework.stereotype.Component;

/**
 * 京东电商金采身份认证凭据提供者
 *
 * @author cdrcool
 */
@Component
public class JdMallBillAuthenticationProvider extends AbstractMallAuthenticationProvider {

    public JdMallBillAuthenticationProvider(AccountManager accountManager) {
        super(accountManager);
    }

    @Override
    protected MallAuthentication buildAuthentication(Account account) {
        return JdMallBillAuthentication.builder()
                .accessToken(account.getAccessToken())
                .build();
    }

    @Override
    protected MallType mallType() {
        return MallType.JD_BILL;
    }
}
