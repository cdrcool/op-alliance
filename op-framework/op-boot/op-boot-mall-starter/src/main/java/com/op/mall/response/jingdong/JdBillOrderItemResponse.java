package com.op.mall.response.jingdong;

import lombok.Data;

/**
 * 京东账单-订单明细项响应
 *
 * @author chengdr01
 */
@Data
public class JdBillOrderItemResponse {
    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 结算单位
     */
    private String customerName;

    /**
     * 还款状态:2500=未结清; 2501=已结清; 4001=还款成功; 4002=还款失败; 5001=正常; 5002=延期; 5003=逾期; 5004=已结清;
     * 5005=延期已结清; 5006=逾期已结清
     */
    private Integer repayStatus;
}
