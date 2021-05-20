package com.op.framework.boot.sdk.client.response;

import com.jd.open.api.sdk.domain.vopdz.QueryAddressOpenProvider.response.queryJdAreaIdList.AreaInfoBaseResp;
import com.suning.api.entity.govbus.CityGetResponse;
import com.suning.api.entity.govbus.CountyGetResponse;
import com.suning.api.entity.govbus.ProvinceGetResponse;
import com.suning.api.entity.govbus.TownGetResponse;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 区域响应对象
 *
 * @author cdrcool
 */
@NoArgsConstructor
@Data
public class AreaResponse {
    /**
     * 区域名
     */
    private String areaName;

    /**
     * 区域编码
     */
    private String areaCode;

    /**
     * 唯一运输区域代码
     */
    private String transportCode;

    public static AreaResponse buildFrom(AreaInfoBaseResp resp) {
        AreaResponse response = new AreaResponse();
        response.setAreaName(resp.getAreaName());
        response.setAreaCode(String.valueOf(resp.getAreaId()));
        return response;
    }

    public static AreaResponse buildFrom(ProvinceGetResponse.ResultInfo resultInfo) {
        AreaResponse response = new AreaResponse();
        response.setAreaName(resultInfo.getName());
        response.setAreaCode(String.valueOf(resultInfo.getProvCode()));
        response.setTransportCode(String.valueOf(resultInfo.getTransportCode()));
        return response;
    }

    public static AreaResponse buildFrom(CityGetResponse.ResultInfo resultInfo) {
        AreaResponse response = new AreaResponse();
        response.setAreaName(resultInfo.getName());
        response.setAreaCode(String.valueOf(resultInfo.getCityCode()));
        response.setTransportCode(String.valueOf(resultInfo.getTransportCode()));
        return response;
    }

    public static AreaResponse buildFrom(CountyGetResponse.ResultInfo resultInfo) {
        AreaResponse response = new AreaResponse();
        response.setAreaName(resultInfo.getName());
        response.setAreaCode(String.valueOf(resultInfo.getDistrictCode()));
        response.setTransportCode(String.valueOf(resultInfo.getTransportCode()));
        return response;
    }

    public static AreaResponse buildFrom(TownGetResponse.ResultInfo resultInfo) {
        AreaResponse response = new AreaResponse();
        response.setAreaName(resultInfo.getName());
        response.setAreaCode(String.valueOf(resultInfo.getTownCode()));
        response.setTransportCode(String.valueOf(resultInfo.getTransportCode()));
        return response;
    }
}
