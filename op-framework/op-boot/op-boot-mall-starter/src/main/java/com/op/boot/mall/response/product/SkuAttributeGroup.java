package com.op.boot.mall.response.product;

import lombok.Data;

import java.util.List;

/**
 * 属性组
 *
 * @author cdrcool
 */
@Data
public class SkuAttributeGroup {
    /**
     * 属性组名
     */
    private String groupName;

    /**
     * 属性信息
     */
    private List<SkuAttribute> atts;
}
