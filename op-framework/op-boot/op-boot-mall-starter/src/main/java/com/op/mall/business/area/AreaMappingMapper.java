package com.op.mall.business.area;

import java.util.List;

/**
 * 地址映射 Mapper
 *
 * @author cdrcool
 */
public interface AreaMappingMapper {

    /**
     * 查询地址映射列表
     *
     * @param mallType  电商类型
     * @param areaCodes 地址编码列表
     * @return 地址映射列表
     */
    List<AreaMapping> queryAreaMappings(String mallType, List<String> areaCodes);
}
