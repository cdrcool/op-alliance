package com.op.mall.request;

import com.op.mall.client.MallAuthentication;
import com.op.mall.constans.MallMethodConstants;
import com.op.mall.constans.MallType;
import com.op.mall.response.BillPullBillsResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 账单拉取对账单请求
 *
 * @author chengdr01
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BillPullBillsRequest extends MallRequest<BillPullBillsResponse> {
    /**
     * 开始期间（yyyy-MM）
     */
    private String beginPeriod;

    /**
     * 结束期间（yyyy-MM）
     */
    private String endPeriod;

    public BillPullBillsRequest(MallType mallType, MallAuthentication authentication, Object requestObj) {
        super(mallType, authentication, requestObj);
    }

    @Override
    public String getRequestMethod() {
        return MallMethodConstants.BILL_PULL_BILLS;
    }

    @Override
    public Class<BillPullBillsResponse> getResponseClass() {
        return BillPullBillsResponse.class;
    }
}
