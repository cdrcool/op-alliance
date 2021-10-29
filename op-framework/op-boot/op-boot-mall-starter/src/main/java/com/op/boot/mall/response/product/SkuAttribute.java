package com.op.boot.mall.response.product;

import lombok.Data;

import java.util.List;

/**
 * 属性信息
 *
 * @author cdrcool
 */
@Data
public class SkuAttribute {
    /**
     * 属性名称
     */
    private String attName;

    /**
     * 属性值信息
     */
    private List<String> vals;

}
