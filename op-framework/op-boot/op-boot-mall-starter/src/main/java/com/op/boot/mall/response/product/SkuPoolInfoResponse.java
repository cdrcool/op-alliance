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
public class SkuPoolInfoResponse extends MallResponse {
    private List<SkuPoolInfoItemResponse> skuPoolInfoItemResponseList;

    @Data
    public static class SkuPoolInfoItemResponse {
        /**
         * 商品池名称
         */
        private String poolName;

        /**
         * 商品池编号
         */
        private String bizPoolId;
    }


}
