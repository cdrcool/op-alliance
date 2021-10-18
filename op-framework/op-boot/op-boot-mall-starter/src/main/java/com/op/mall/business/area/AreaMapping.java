package com.op.mall.business.area;

import lombok.Data;

/**
 * 地址映射表
 *
 * @author cdrcool
 */
@Data
public class AreaMapping {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 地址编码
     */
    private String areaCode;

    /**
     * 电商类型
     */
    private String mallType;

    /**
     * 电商地址编码
     */
    private String mallAreaCode;
}
