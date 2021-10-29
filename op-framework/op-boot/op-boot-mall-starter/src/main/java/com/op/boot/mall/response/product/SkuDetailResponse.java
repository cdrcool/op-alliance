package com.op.boot.mall.response.product;

import com.op.boot.mall.response.MallResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.List;

/**
 * 商品详情
 *
 * @author cdrcool
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SkuDetailResponse extends MallResponse {
    /**
     * 商品编号
     */
    private String skuId;

    /**
     * 品牌名称
     */
    private String brandName;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 售卖单位
     */
    private String saleUnit;

    /**
     * 重量
     */
    private String weight;

    /**
     * 产地
     */
    private String productArea;

    /**
     * 包装清单
     */
    private String wareQD;

    /**
     * 主图
     */
    private String imagePath;

    /**
     * 规格参数
     */
    private String param;

    /**
     * 状态,1上架 0下架
     */
    private String state;

    /**
     * UPC码（条形码）
     */
    private String upc;

    /**
     * 分类
     */
    private String category;

    /**
     * 商品详情页大字段
     */
    private String introduction;

    /**
     * 属性信息
     */
    private List<SkuAttributeGroup> paramDetailJson;

    /**
     * 质保信息
     */
    private String wserve;

    /**
     * 商品税率
     */
    private BigDecimal taxInfo;

    /**
     * 最低起售数量
     */
    private Integer lowestBuy;

    /**
     * 容量单位转换（例如：油品单位桶转升）
     */
    private String capacity;

    /**
     * 规格参数
     */
    private String seoModel;

    /**
     * 是否厂直商品
     */
    private String isFactoryShip;

    /**
     * PC端商品详情大字段
     */
    private String nintroduction;

    /**
     * 是否京东配送
     */
    private Integer isJDLogistics;

    /**
     * 是否京东自营
     */
    private Integer isSelf;

    /**
     * 商品分类信息
     */
    private List<SkuCategoryResponse.SkuCategoryItemResponse> categoryItemList;
}
