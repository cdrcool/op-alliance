package com.op.boot.mall.authentication;

import com.op.boot.mall.account.Account;
import com.op.boot.mall.account.AccountManager;
import com.op.boot.mall.constants.MallType;
import com.op.boot.mall.exception.MallException;

import java.util.Optional;

/**
 * 电商身份认证凭据提供者抽象类
 *
 * @author cdrcool
 */
public abstract class AbstractMallAuthenticationProvider implements MallAuthenticationProvider {
    private final AccountManager accountManager;

    protected AbstractMallAuthenticationProvider(AccountManager accountManager) {
        this.accountManager = accountManager;
    }

    @Override
    public MallAuthentication loadAuthentication(MallType mallType, String accountName) {
        if (!mallType().equals(mallType)) {
            return null;
        }

        Optional<Account> optional = accountManager.findOne(accountName, MallType.JD.getValue());
        return optional.map(this::buildAuthentication)
                .orElseThrow(() -> new MallException("未配置电商账号【" + MallType.JD.getDesc() + "- " + accountName + "】"));
    }


    /**
     * 构建电商身份认证凭据
     *
     * @param account 电商账号
     * @return 电商身份认证凭据
     */
    protected abstract MallAuthentication buildAuthentication(Account account);

    /**
     * 设置电商类型
     *
     * @return 电商类型
     */
    protected abstract MallType mallType();
}
