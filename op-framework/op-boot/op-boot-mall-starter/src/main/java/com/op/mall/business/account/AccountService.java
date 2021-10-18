package com.op.mall.business.account;

import com.op.mall.constans.MallType;

/**
 * 对账 Service
 *
 * @author cdrcool
 */
public interface AccountService {

    /**
     * 拉取对账单
     *
     * @param startPeriod 账单起始期间，未指定时为当前月上一月
     * @param endPeriod   账单结束期间，未指定时为当前月上一月
     * @param mallType    电商类型
     * @param account     电商账号，未指定时拉取所有账号的对账单
     */
    void pullAccounts(String startPeriod, String endPeriod, MallType mallType, String account);
}
