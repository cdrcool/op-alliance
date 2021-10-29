package com.op.boot.mall.response.product;

import com.op.boot.mall.response.MallResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author cdrcool
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SkuSaleabilityResponse extends MallResponse {
    private List<SkuSaleabilityItemResponse> skuSaleabilityItemResponseList;

    @Data
    public static class SkuSaleabilityItemResponse {
        /**
         * 商品编号
         */
        private Long skuId;

        /**
         * 商品名称
         */
        private String name;

        /**
         * 是否可售，1：是，0：否
         */
        private Integer saleState;

        /**
         * 是否可开专票，1：支持，0：不支持
         */
        private String isCanVAT;

        /**
         * 无理由退货类型：0,1,2,3,4,5,6,7,8\n" +
         * "0、3：不支持7天无理由退货；\n" +
         * "1、5、8或null：支持7天无理由退货；\n" +
         * "2：支持90天无理由退货；\n" +
         * "4、7：支持15天无理由退货；\n" +
         * "6：支持30天无理由退货；\n" +
         * "（提示客户取到其他枚举值，无效）
         */
        private String noReasonToReturn;

        /**
         * 无理由退货文案类型：\n" +
         * "null：文案空；\n" +
         * "0：文案空；\n" +
         * "1：支持7天无理由退货；\n" +
         * "2：支持7天无理由退货（拆封后不支持）；\n" +
         * "3：支持7天无理由退货（激活后不支持）\n" +
         * "4：支持7天无理由退货（使用后不支持）；\n" +
         * "5：支持7天无理由退货（安装后不支持）；\n" +
         * "12：支持15天无理由退货；\n" +
         * "13：支持15天无理由退货（拆封后不支持）；\n" +
         * "14：支持15天无理由退货（激活后不支持）；\n" +
         * "15：支持15天无理由退货（使用后不支持）；\n" +
         * "16：支持15天无理由退货（安装后不支持）；\n" +
         * "22：支持30天无理由退货；\n" +
         * "23：支持30天无理由退货（安装后不支持）；\n" +
         * "24：支持30天无理由退货（拆封后不支持）；\n" +
         * "25：支持30天无理由退货（使用后不支持）；\n" +
         * "26：支持30天无理由退货（激活后不支持）；\n" +
         * "（提示客户取到其他枚举值，无效）
         */
        private String thwa;

        private Integer is7ToReturn;

        /**
         * 是否自营
         */
        private Integer isSelf;

        /**
         * 是否京东配送
         */
        private Integer isJDLogistics;

        /**
         * 商品税率
         */
        private BigDecimal taxInfo;
    }

}
