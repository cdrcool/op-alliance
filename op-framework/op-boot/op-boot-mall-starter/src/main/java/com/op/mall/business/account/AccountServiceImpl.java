package com.op.mall.business.account;

import com.op.mall.constans.MallType;

/**
 * 对账 Service Impl
 *
 * @author cdrcool
 */
public class AccountServiceImpl implements AccountService {

    @Override
    public void pullAccounts(String startPeriod, String endPeriod, MallType mallType, String account) {
        // 判断是否已拉取当前电商当前账号当前期间的数据，如果未拉取，则拉取

        // * 拉取当前电商当前账号当前期间的数据，遍历判断是否已入库，如果未如果，则入库

    }
}
