package com.op.mall.response.jingdong;

import lombok.Data;

import java.util.List;

/**
 * 京东账单-账单下的所有订单明细响应
 *
 * @author chengdr01
 */
@Data
public class JdBillOrderStatusResponse {
    /**
     * 页码
     */
    private Integer pageNo;

    /**
     * 页数
     */
    private Integer pageSize;

    /**
     * 总记录数
     */
    private Integer totalRecord;

    /**
     * 总页码
     */
    private Integer totalPage;

    /**
     * 订单详情
     */
    private List<JdBillOrderItemResponse> results;
}
