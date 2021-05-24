package com.op.framework.boot.sdk.client.api.impl;

import com.jd.open.api.sdk.domain.vopdz.QueryAddressOpenProvider.response.queryJdAreaIdList.OpenRpcResult;
import com.jd.open.api.sdk.domain.vopdz.QueryAddressOpenProvider.response.queryJdAreaIdList.QueryJdAreaIdListOpenResp;
import com.jd.open.api.sdk.request.vopdz.VopAddressQueryJdAreaIdListRequest;
import com.jd.open.api.sdk.response.vopdz.VopAddressQueryJdAreaIdListResponse;
import com.op.framework.boot.sdk.client.api.AreaApi;
import com.op.framework.boot.sdk.client.base.JdSdkClient;
import com.op.framework.boot.sdk.client.base.JdSdkRequest;
import com.op.framework.boot.sdk.client.exception.JdInvokeException;
import com.op.framework.boot.sdk.client.response.AreaResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 京东区域 API 接口
 *
 * @author cdrcool
 */
@Slf4j
@Service
public class JdAreaApiImpl implements AreaApi {
    private final JdSdkClient jdSdkClient;

    public JdAreaApiImpl(JdSdkClient jdSdkClient) {
        this.jdSdkClient = jdSdkClient;
    }

    @Override
    public List<AreaResponse> getProvinces() {
        return getAreas("1", 1);
    }

    @Override
    public List<AreaResponse> getCities(String provinceCode) {
        return getAreas(provinceCode, 2);
    }

    @Override
    public List<AreaResponse> getCounties(String cityCode) {
        return getAreas(cityCode, 3);
    }

    @Override
    public List<AreaResponse> getTowns(String areaCode) {
        return getAreas(areaCode, 4);
    }

    @Override
    public List<AreaResponse> getAreas(String areaCode, Integer areaLevel) {
        VopAddressQueryJdAreaIdListRequest request = new VopAddressQueryJdAreaIdListRequest();
        request.setJdAreaId(Long.parseLong(areaCode));
        request.setAreaLevel(areaLevel);

        VopAddressQueryJdAreaIdListResponse response = jdSdkClient.execute(new JdSdkRequest<>(request));
        OpenRpcResult result = response.getOpenRpcResult();
        if (!result.getSuccess()) {
            log.error("查询京东四级地址id列表失败，错误码：【{}】，错误消息：【{}】", result.getResultCode(), result.getResultMessage());
            throw new JdInvokeException(result.getResultCode(), "查询京东四级地址id列表失败：" + result.getResultMessage());
        }

        QueryJdAreaIdListOpenResp areaIdListOpenResp = result.getResult();
        return Optional.ofNullable(areaIdListOpenResp.getAreaInfoList()).orElse(new ArrayList<>()).stream()
                .map(AreaResponse::buildFrom).collect(Collectors.toList());
    }
}
