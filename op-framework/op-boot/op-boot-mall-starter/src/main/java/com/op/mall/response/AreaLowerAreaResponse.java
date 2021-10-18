package com.op.mall.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 地址查询下级地址响应
 *
 * @author cdrcool
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AreaLowerAreaResponse extends MallResponse {
    /**
     * 下级地址列表
     */
    private List<AreaLowerAreaItem> areaItems;

    @Data
    public static class AreaLowerAreaItem {
        /**
         * 地址编码
         */
        private String areaCode;

        /**
         * 地址级别
         */
        private Integer areaLevel;
    }


}
