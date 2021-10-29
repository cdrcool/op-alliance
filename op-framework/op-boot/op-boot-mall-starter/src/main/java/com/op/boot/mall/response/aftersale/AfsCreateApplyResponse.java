package com.op.boot.mall.response.aftersale;

import com.op.boot.mall.response.MallResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author cdrcool
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AfsCreateApplyResponse extends MallResponse {
    private Long skuId;

    /**
     * 处理结果1：成功，0：失败
     */
    private Integer status;

    /**
     * 售后服务单号，若申请退货的商品为厂送品，此字段有值
     */
    private String sheetId;

    /**
     * 失败原因
     */
    private String unableReason;

    private Boolean result;
}
