package com.op.boot.mall.response.order;

import com.op.boot.mall.response.MallResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 查询订单详情响应
 *
 * @author cdrcool
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class OrderQueryDetailResponse extends MallResponse {
    /**
     * 父订单号. 为0时, 此订单为父单
     */
    private Object parentOrder;

    /**
     * 订单状态. 1取消订单,0有效
     */
    private Integer cancelState;

    /**
     * 京东订单编号
     */
    private Long orderId;

    /**
     * 物流状态. 0新建, 1妥投, 2拒收
     */
    private Integer state;

    /**
     * 预占确认状态. 0确认预占,1确认预占
     */
    private Integer confirmState;

    /**
     * 订单类型. 1父订单,2子订单
     */
    private Integer type;

    /**
     * 运费
     */
    private BigDecimal freight;

    /**
     * 商品列表
     */
    private List<OrderSkuResponse> sku;

    /**
     * 订单总金额=商品总金额+运费
     */
    private BigDecimal orderPrice;

    /**
     * 订单未含税金额
     */
    private BigDecimal orderNakedPrice;

    /**
     * 订单税额
     */
    private BigDecimal orderTaxPrice;

    /**
     * 订单类别
     */
    private Integer orderType;

    /**
     * 订单创建时间. 输出格式为yyyy-MM-dd HH:mm:ss
     */
    private Date createOrderTime;

    /**
     * 订单创建时间. 输出格式为yyyy-MM-dd HH:mm:ss 未完成时，此参数返回null
     */
    private Date finishTime;

    /**
     * 京东状态, 1.新单, 2.等待支付, 3.等待支付确认, 4.延迟付款确认, 5.订单暂停, 6.店长最终审核, 7.等待打印, " +
     * "8.等待出库, 9.等待打包, 10.等待发货, 11.自提途中, 12.上门提货, 13.自提退货, 14.确认自提, 16.等待确认收货, 17.配送退货, " +
     * "18.货到付款确认, 19.已完成, 21.收款确认, 22.锁定, 29.等待三方出库, 30.等待三方发货, 31.等待三方发货完成
     */
    private Integer orderState;

    /**
     * 收货地址
     */
    private String address;

    /**
     * 姓名
     */
    private String name;

    /**
     * 联系方式
     */
    private String mobile;

    /**
     * 支付方式枚举值111, 1：货到付, 4：预存款, 5：公司转, 101：京东金采, " +
     * "102：商城金采(一般不适用，仅限确认开通商城账期的特殊情况使用，请与业务确认后使用, " +
     * "20为混合支付
     */
    private Integer paymentType;

    /**
     * 京东配送订单的出库时间/厂家直送订单的确认发货时间2019-10-12 10:21:44
     */
    private Date outTime;

    /**
     * 下单时的开票类型2增值税专用发票；3 电子票
     */
    private Integer invoiceType;

    /**
     * 子订单详情
     */
    private List<OrderQueryDetailResponse> childOrder;

    /**
     * 订单详情响应体sku信息
     */
    @Data
    public static class OrderSkuResponse {
        /**
         * 商品名称
         */
        private String name;

        /**
         * 京东商品编号
         */
        private Long skuId;

        /**
         * 商品数量
         */
        private Integer num;

        /**
         * 京东三级分类
         */
        private Integer category;

        /**
         * 商品价格
         */
        private BigDecimal price;

        /**
         * 商品税率。例如：本参数值返回13，代表税率为“13%”
         */
        private BigDecimal tax;

        /**
         * 主商品ID
         */
        private Long oid;

        /**
         * 商品类型
         */
        private Integer type;

        /**
         * 运费拆分价格
         */
        private Integer splitFreight;

        /**
         * 商品税额
         */
        private BigDecimal taxPrice;

        /**
         * 商品未含税金额
         */
        private BigDecimal nakedPrice;
    }


}
