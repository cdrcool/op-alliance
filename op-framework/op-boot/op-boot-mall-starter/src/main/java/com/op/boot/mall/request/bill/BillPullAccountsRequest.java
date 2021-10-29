package com.op.boot.mall.request.bill;

import com.op.boot.mall.client.MallAuthentication;
import com.op.boot.mall.constans.MallMethodConstants;
import com.op.boot.mall.constans.MallType;
import com.op.boot.mall.request.MallRequest;
import com.op.boot.mall.response.bill.BillPullOrdersResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 账单拉取对账单请求
 *
 * @param <P> 请求对象类型
 * @author cdrcool
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BillPullAccountsRequest<P> extends MallRequest<P, BillPullOrdersResponse> {
    /**
     * 开始期间（yyyy-MM）
     */
    private String beginPeriod;

    /**
     * 结束期间（yyyy-MM）
     */
    private String endPeriod;

    public BillPullAccountsRequest(MallType mallType, MallAuthentication authentication, P requestObj) {
        super(mallType, authentication, requestObj);
    }

    @Override
    public String getRequestMethod() {
        return MallMethodConstants.BILL_PULL_ACCOUNTS;
    }

    @Override
    public Class<BillPullOrdersResponse> getResponseClass() {
        return BillPullOrdersResponse.class;
    }
}
