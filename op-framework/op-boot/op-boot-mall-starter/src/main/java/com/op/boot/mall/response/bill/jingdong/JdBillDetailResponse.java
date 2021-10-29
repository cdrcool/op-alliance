package com.op.boot.mall.response.bill.jingdong;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 京东账单-账单明细响应
 *
 * @author cdrcool
 */
@Data
public class JdBillDetailResponse {
    /**
     * 账单编号
     */
    private String billNo;

    /**
     * 账单类型:5101=固定账单;5102=滚动账单;5106=周结账单;5107=月结账单;5108=月末账单
     */
    private String billType;

    /**
     * 服务费率（执行利率） ##.0000000000
     */
    private BigDecimal execRate;

    /**
     * 已还本金 ##.00
     */
    private BigDecimal sumPri;

    /**
     * 未还本金 ##.00
     */
    private BigDecimal sumNrPri;

    /**
     * 已还延期服务费 ##.00
     */
    private BigDecimal sumDefer;

    /**
     * 未还延期服务费 ##.00
     */
    private BigDecimal sumNrDefer;

    /**
     * 已还违约服务费 ##.00
     */
    private BigDecimal sumDelin;

    /**
     * 未还违约服务费 ##.00
     */
    private BigDecimal sumNrDelin;

    /**
     * 还款日期（应还款日）
     */
    private Date repayDate;

    /**
     * 违约开始日期
     */
    private Date delinDate;

    /**
     * 状态：5001=正常;5002=延期;5003=逾期;5004=已结清;5005=延期已结清;5006=逾期已结清
     */
    private String status;
}
