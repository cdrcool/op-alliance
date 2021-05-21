package com.op.framework.boot.sdk.client.api.impl;

import com.op.framework.boot.sdk.client.api.AreaApi;
import com.op.framework.boot.sdk.client.base.SnSdkClient;
import com.op.framework.boot.sdk.client.base.SnSdkRequest;
import com.op.framework.boot.sdk.client.exception.SnInvokeException;
import com.op.framework.boot.sdk.client.response.AreaResponse;
import com.suning.api.SuningResponse;
import com.suning.api.entity.govbus.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 苏宁区域 API 接口
 *
 * @author cdrcool
 */
@Slf4j
@Service
public class SnAreaApiImpl implements AreaApi {
    private final SnSdkClient snSdkClient;

    public SnAreaApiImpl(SnSdkClient snSdkClient) {
        this.snSdkClient = snSdkClient;
    }

    @Override
    public List<AreaResponse> getProvinces() {
        ProvinceGetRequest request = new ProvinceGetRequest();

        ProvinceGetResponse response = snSdkClient.execute(new SnSdkRequest<>(request));
        SuningResponse.SnError snError = response.getSnerror();
        if (snError != null) {
            log.error("获取苏宁一级地址接口失败，错误码：【{}】，错误消息：【{}】", snError.getErrorCode(), snError.getErrorMsg());
            throw new SnInvokeException(snError.getErrorCode(), "获取苏宁一级地址接口失败：" + snError.getErrorMsg());
        }

        ProvinceGetResponse.SnBody snBody = response.getSnbody();
        ProvinceGetResponse.GetProvince getProvince = snBody.getGetProvince();
        return getProvince.getResultInfo().stream().map(AreaResponse::buildFrom).collect(Collectors.toList());
    }

    @Override
    public List<AreaResponse> getCities(String provinceCode) {
        CityGetRequest request = new CityGetRequest();
        request.setTransportCode(provinceCode);

        CityGetResponse response = snSdkClient.execute(new SnSdkRequest<>(request));
        SuningResponse.SnError snError = response.getSnerror();
        if (snError != null) {
            log.error("获取苏宁二级地址接口失败，错误码：【{}】，错误消息：【{}】", snError.getErrorCode(), snError.getErrorMsg());
            throw new SnInvokeException(snError.getErrorCode(), "获取苏宁二级地址接口失败：" + snError.getErrorMsg());
        }

        CityGetResponse.SnBody snBody = response.getSnbody();
        CityGetResponse.GetCity getCity = snBody.getGetCity();
        return getCity.getResultInfo().stream().map(AreaResponse::buildFrom).collect(Collectors.toList());
    }

    @Override
    public List<AreaResponse> getCounties(String cityCode) {
        CountyGetRequest request = new CountyGetRequest();
        request.setTransportCode(cityCode);

        CountyGetResponse response = snSdkClient.execute(new SnSdkRequest<>(request));

        SuningResponse.SnError snError = response.getSnerror();
        if (snError != null) {
            log.error("获取苏宁三级地址接口失败，错误码：【{}】，错误消息：【{}】", snError.getErrorCode(), snError.getErrorMsg());
            throw new SnInvokeException(snError.getErrorCode(), "获取苏宁三级地址接口失败：" + snError.getErrorMsg());
        }

        CountyGetResponse.SnBody snBody = response.getSnbody();
        CountyGetResponse.GetCounty getCounty = snBody.getGetCounty();
        return getCounty.getResultInfo().stream().map(AreaResponse::buildFrom).collect(Collectors.toList());
    }

    @Override
    public List<AreaResponse> getTowns(String areaCode) {
        TownGetRequest request = new TownGetRequest();
        request.setTransportCode(areaCode);

        TownGetResponse response = snSdkClient.execute(new SnSdkRequest<>(request));

        TownGetResponse.SnError snError = response.getSnerror();
        if (snError != null) {
            log.error("获取苏宁四级地址接口失败，错误码：【{}】，错误消息：【{}】", snError.getErrorCode(), snError.getErrorMsg());
            throw new SnInvokeException(snError.getErrorCode(), "获取苏宁四级地址接口失败：" + snError.getErrorMsg());
        }

        TownGetResponse.SnBody snBody = response.getSnbody();
        TownGetResponse.GetTown getTown = snBody.getGetTown();
        return getTown.getResultInfo().stream().map(AreaResponse::buildFrom).collect(Collectors.toList());
    }

    @Override
    public List<AreaResponse> getAreas(String areaCode, Integer areaLevel) {
        switch (areaLevel) {
            case 1:
                return getProvinces();
            case 2:
                return getCities(areaCode);
            case 3:
                return getCounties(areaCode);
            case 4:
                return getTowns(areaCode);
            default:
                return new ArrayList<>();
        }
    }
}
