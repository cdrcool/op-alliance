package com.op.boot.mall.response.order;

import com.op.boot.mall.response.MallResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.List;

/**
 * 订单提交响应
 *
 * @author cdrcool
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class OrderSubmitResponse extends MallResponse {
    /**
     * 订单号
     */
    private String orderId;

    /**
     * 订单来源
     */
    private Integer sourceType;

    /**
     * 运费
     */
    private BigDecimal freight;

    /**
     * 订单总金额
     */
    private BigDecimal orderPrice;

    /**
     * 订单未税金额
     */
    private BigDecimal orderNakedPrice;

    /**
     * 订单税额
     */
    private BigDecimal orderTaxPrice;

    /**
     * 订单包含的商品信息列表
     */
    private List<OrderSubmitSkuResponse> orderSubmitSkuResponseList;

    /**
     * 订单提交 sku 响应
     */
    @Data
    public static class OrderSubmitSkuResponse {
        /**
         * 子订单id
         */
        private String subOrderId;

        /**
         * 京东商品编号
         */
        private String skuId;

        /**
         * 购买商品数量
         */
        private Integer num;

        /**
         * 商品分类编号
         */
        private Integer category;

        /**
         * 商品单价
         */
        private BigDecimal price;

        /**
         * 商品名称
         */
        private String name;

        /**
         * 商品税率
         */
        private BigDecimal tax;

        /**
         * 商品税额
         */
        private BigDecimal taxPrice;

        /**
         * 商品未税价
         */
        private BigDecimal nakedPrice;

        /**
         * 商品类型：0-普通；1-附件；2-赠品
         */
        private Integer type;

        /**
         * 主商品 sku id，如果本身是主商品，则 oid 为 0
         */
        private Long oid;
    }
}
