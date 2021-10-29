package com.op.boot.mall.response.aftersale;

import com.op.boot.mall.response.MallResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author cdrcool
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AfsAttributeResponse extends MallResponse {
    private List<AfsAttributeItemResponse> afsAttributeItemResponseList;

    @Data
    public static class AfsAttributeItemResponse {
        /**
         * 商品编码
         */
        private Long wareId;

        /**
         * 商品可售后数量
         */
        private Integer wareNum;

        /**
         * 支持的服务类型. 10退货,20换货,30维修预期. 为空可能代表当前没有可以售后的商品
         */
        private List<Integer> customerExpect;

        /**
         * 支持的返回京东方式. 4上门取件,7客户送货,40客户发货. 为空可能代表当前没有可以售后的商品
         */
        private List<Integer> pickupWareType;
    }
}
