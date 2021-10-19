package com.op.mall.response.jd;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 京东账单-订单明细响应
 *
 * @author chengdr01
 */
@Data
public class JdBillOrderDetailResponse {
    /**
     * 账单编号
     */
    private String billNo;

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 订单金额 ##.00
     */
    private BigDecimal orderAmount;

    /**
     * 结算单位
     */
    private String customerName;

    /**
     * 结算时间
     */
    private Date settleDate;

    /**
     * 结清状态 2500:未结清； 2501:已结清
     */
    private String settleStatus;

    /**
     * 总退款金额 ##.00
     */
    private BigDecimal sumRefundAmount;
}
