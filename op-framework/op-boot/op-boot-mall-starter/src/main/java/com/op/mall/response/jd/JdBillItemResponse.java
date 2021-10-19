package com.op.mall.response.jd;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 京东账单-账单详情项响应
 *
 * @author chengdr01
 */
@Data
public class JdBillItemResponse {
    /**
     * 账单编号
     */
    private String billNo;

    /**
     * 账单开始日期
     */
    private Date billStartDate;

    /**
     * 账单结束日期
     */
    private Date billEndDate;

    /**
     * 应还款日期
     */
    private Date repayDate;

    /**
     * 账单金额
     */
    private BigDecimal billAmount;

    /**
     * 还款状态: 2500=未结清, 2501=已结清, 4001=还款成功, 4002=还款失败, 5001=正常, 5002=延期, 5003=逾期, 5004=已结清,
     * 5005=延期已结清, 5006=逾期已结清
     */
    private Integer repayStatus;

    /**
     * 逾期天数
     */
    private Integer overDays;

    /**
     * 未还本金
     */
    private BigDecimal nrRepayPri;

    /**
     * 违约金
     */
    private BigDecimal overAmount;
}
