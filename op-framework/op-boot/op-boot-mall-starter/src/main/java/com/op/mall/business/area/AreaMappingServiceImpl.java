package com.op.mall.business.area;

import com.op.mall.constans.MallType;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 地址映射 Service Impl
 *
 * @author cdrcool
 */
public class AreaMappingServiceImpl implements AreaMappingService {
    private final AreaMappingMapper areaMappingMapper;

    public AreaMappingServiceImpl(AreaMappingMapper areaMappingMapper) {
        this.areaMappingMapper = areaMappingMapper;
    }

    @Override
    public AreaDTO convert(MallType mallType, AreaDTO areaDTO) {
        List<AreaMapping> mappings = areaMappingMapper.queryAreaMappings(mallType.getValue(),
                Arrays.asList(areaDTO.getProvinceId(), areaDTO.getCityId(), areaDTO.getCountyId(), areaDTO.getTownId()));
        Map<String, String> areaMap = mappings.stream().collect(Collectors.toMap(AreaMapping::getAreaCode, AreaMapping::getMallAreaCode));

        AreaDTO result = new AreaDTO();
        result.setProvinceId(areaMap.get(areaDTO.getProvinceId()));
        result.setCityId(areaMap.get(areaDTO.getCityId()));
        result.setCountyId(areaMap.get(areaDTO.getCountyId()));
        result.setTownId(areaMap.get(areaDTO.getTownId()));

        return result;
    }

}
