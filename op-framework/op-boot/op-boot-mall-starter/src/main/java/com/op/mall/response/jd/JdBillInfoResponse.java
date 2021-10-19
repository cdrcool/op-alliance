package com.op.mall.response.jd;

import lombok.Data;

import java.util.List;

/**
 * 京东账单-已出账单详情响应
 *
 * @author chengdr01
 */
@Data
public class JdBillInfoResponse {
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
     * 账单详情
     */
    private List<JdBillItemResponse> results;
}
