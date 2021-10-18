package com.op.mall.business.area;

import lombok.Data;

/**
 * 地址 DTO
 *
 * @author cdrcool
 */
@Data
public class AreaDTO {
    /**
     * 省ID
     */
    private String provinceId;

    /**
     * 市ID
     */
    private String cityId;

    /**
     * 区ID
     */
    private String countyId;

    /**
     * 镇ID
     */
    private String townId;

    /**
     * 省名称
     */
    private String provinceName;

    /**
     * 市名称
     */
    private String cityName;

    /**
     * 区名称
     */
    private String countyName;

    /**
     * 镇名称
     */
    private String townName;
}
