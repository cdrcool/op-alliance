package com.op.mall.business.area;

import lombok.Data;

/**
 * 地址
 *
 * @author cdrcool
 */
@Data
public class Area {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 父ID
     */
    private Integer parentId;

    /**
     * 地址编码
     */
    private String areaCode;

    /**
     * 地址名称
     */
    private String areaName;

    /**
     * 地址级别
     */
    private Integer areaLevel;
}