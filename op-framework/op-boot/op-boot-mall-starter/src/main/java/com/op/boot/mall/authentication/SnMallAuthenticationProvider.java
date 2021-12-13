package com.op.boot.mall.authentication;

import com.op.boot.mall.account.Account;
import com.op.boot.mall.account.AccountManager;
import com.op.boot.mall.constants.MallType;
import org.springframework.stereotype.Component;

/**
 * 苏宁电商身份认证凭据提供者
 *
 * @author cdrcool
 */
@Component
public class SnMallAuthenticationProvider extends AbstractMallAuthenticationProvider {

    public SnMallAuthenticationProvider(AccountManager accountManager) {
        super(accountManager);
    }

    @Override
    protected MallAuthentication buildAuthentication(Account account) {
        return SnMallAuthentication.builder()
                .appKey(account.getClientSecret())
                .appSecret(account.getClientSecret())
                .companyCustNo(account.getAccountName())
                .build();
    }

    @Override
    protected MallType mallType() {
        return MallType.SN;
    }


}
