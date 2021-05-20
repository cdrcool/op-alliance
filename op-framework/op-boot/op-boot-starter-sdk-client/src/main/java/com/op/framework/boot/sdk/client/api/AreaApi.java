package com.op.framework.boot.sdk.client.api;

import com.op.framework.boot.sdk.client.response.AreaResponse;

import java.util.List;

/**
 * 区域 API 接口
 *
 * @author cdrcool
 */
public interface AreaApi {

    /**
     * 获取一级地址列表
     *
     * @return 一级地址列表
     */
    List<AreaResponse> getProvinces();

    /**
     * 获取二级地址列表
     * <p>
     * 注意：苏宁不是传递省编码，而是省运输代码
     *
     * @param provinceCode 省编码
     * @return 二级地址列表
     */
    List<AreaResponse> getCities(String provinceCode);

    /**
     * 获取三级地址列表
     * <p>
     * 注意：苏宁不是传递市编码，而是市运输代码
     *
     * @param cityCode 市编码
     * @return 三级地址列表
     */
    List<AreaResponse> getCounties(String cityCode);

    /**
     * 获取四级地址列表
     * <p>
     * 注意：苏宁不是传递区编码，而是区运输代码
     *
     * @param countyCode 区编码
     * @return 四级地址列表
     */
    List<AreaResponse> getTowns(String countyCode);

    /**
     * 获取区域列表
     * <p>
     * 注意：苏宁不是传递区域编码，而是区域运输代码
     *
     * @param areaCode  区域编码
     * @param areaLevel 区域级别
     * @return 区域列表
     */
    List<AreaResponse> getAreas(String areaCode, Integer areaLevel);
}
