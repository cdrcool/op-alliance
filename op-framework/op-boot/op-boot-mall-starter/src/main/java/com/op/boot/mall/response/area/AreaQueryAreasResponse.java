package com.op.boot.mall.response.area;

import com.op.boot.mall.response.MallResponse;
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
public class AreaQueryAreasResponse extends MallResponse {
    /**
     * 下级地址列表
     */
    private List<AreaQueryAreasItem> items;

    @Data
    public static class AreaQueryAreasItem {
        /**
         * 地址编码
         */
        private String areaCode;

        /**
         * 地址名称
         */
        private String areaName;
    }


}
