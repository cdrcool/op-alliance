package com.samples.report.pdf;

import lombok.Data;

/**
 * @author chengdr01
 */
@Data
public class GoodsInfo {
    /**
     * 商品简图
     */
    private String mainImg;

    /**
     * 商品名称
     */
    private String materialName;

    /**
     * 商品编号
     */
    private String serialNumber;

    /**
     * 品牌
     */
    private String band;

    /**
     * 单位
     */
    private String unit;

    /**
     * 商品数量
     */
    private String num;

    /**
     * 验收数量
     */
    private String checkNum;

    /**
     * 备注
     */
    private String remark;
}
