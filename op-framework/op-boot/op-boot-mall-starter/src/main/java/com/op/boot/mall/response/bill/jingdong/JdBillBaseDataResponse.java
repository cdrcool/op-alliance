package com.op.boot.mall.response.bill.jingdong;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 京东账单-基础数据响应
 *
 * @author cdrcool
 */
@Data
public class JdBillBaseDataResponse {
    /**
     * 用户总额度
     */
    private BigDecimal quota;

    /**
     * 用户可用额度
     */
    private BigDecimal availQuota;

    /**
     * 用户已占用额度
     */
    private BigDecimal occQuota;

    /**
     * 额度类型：3001:共享额度;3002:上线额度;3003:单次额度
     */
    private String quotaType;

    /**
     * 待还款总金额 ##.00
     */
    private BigDecimal sumAmt;

    /**
     * 待还款逾期总金额 ##.00
     */
    private BigDecimal sumOverAmt;

    /**
     * 待还本金 ##.00
     */
    private BigDecimal sumNrPri;

    /**
     * 待还延期 ##.00
     */
    private BigDecimal sumNrDefer;

    /**
     * 待还逾期 ##.00
     */
    private BigDecimal sumNrDelin;
}
