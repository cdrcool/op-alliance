package com.op.mall.business.area;

import com.op.mall.constans.MallType;

/**
 * 地址映射 Service
 *
 * @author cdrcool
 */
public interface AreaMappingService {

    /**
     * 电商类型
     *
     * @param mallType 电商类型
     * @param areaDTO  原地址 DTO
     * @return 电商地址 DTO
     */
    AreaDTO convert(MallType mallType, AreaDTO areaDTO);
}
