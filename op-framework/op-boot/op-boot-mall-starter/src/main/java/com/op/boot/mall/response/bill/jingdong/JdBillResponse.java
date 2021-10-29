package com.op.boot.mall.response.bill.jingdong;

import lombok.Data;

/**
 * 京东账单响应
 *
 * @author cdrcool
 */
@Data
public class JdBillResponse {
    /**
     * 基础数据
     */
    private JdBillBaseDataResponse baseData;

    /**
     * 已出账单详情
     */
    private JdBillInfoResponse billInfo;

    /**
     * 账单明细
     */
    private JdBillDetailResponse billDetail;

    /**
     * 账单下的所有订单明细
     */
    private JdBillOrderStatusResponse orderStatusDetail;

    /**
     * 订单明细
     */
    private JdBillOrderDetailResponse orderDetail;
}
