package com.op.boot.mall.response.product;

import com.op.boot.mall.response.MallResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author cdrcool
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SkuStockResponse extends MallResponse {
    private List<SkuStockItemResponse> skuStockItemResponseList;

    @Data
    public static class SkuStockItemResponse {
        /**
         * skuId
         */
        private Long skuId;

        /**
         * 入参时传入的区域码area。因京东目前是3、4级地址均支持，存在areaId在传入的3级地址后填充4级地址“_0“后返回的情况
         */
        private String areaId;

        /**
         * 库存状态编号。 参考枚举值：33,39,40,36,34,99
         */
        private Integer stockStateId;

        /**
         * 库存状态描述。以下为stockStateId不同时，此字段不同的返回值：" +
         * "33 有货 现货-下单立即发货\n" +
         * "39 有货 在途-正在内部配货，预计2~6天到达本仓库\n" +
         * "40 有货 可配货-下单后从有货仓库配货\n" +
         * "36 预订\n" +
         * "34 无货\n" +
         * "99 无货开预定，此时desc返回的数值代表预计到货天数，并且该功能需要依赖合同上有无货开预定权限的用户，到货周期略长，谨慎采用该功能
         */
        private String stockStateDesc;

        /**
         * 剩余数量。\n" +
         * "当此值为-1时，为未查询到。\n" +
         * "StockStateDesc为33 或34（有货）：\n" +
         * "入参的skuNums字段，skuId对应的num<50，此字段为实际库存。\n" +
         * "入参的skuNums字段，skuId对应的50<=num<100，此字段为-1。\n" +
         * "入参的skuNums字段，skuId对应的num>100，此字段等于num。(此种情况并未返回真实京东库存)
         */
        private Integer remainNum;

    }
}
