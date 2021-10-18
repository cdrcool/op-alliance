package com.op.mall.request;

import com.op.mall.client.MallAuthentication;
import com.op.mall.constans.MallMethodConstants;
import com.op.mall.constans.MallType;
import com.op.mall.response.AreaLowerAreaResponse;

/**
 * 地址查询下级地址请求
 *
 * @author cdrcool
 */
public class AreaLowerAreaRequest extends MallRequest<AreaLowerAreaResponse> {

    public AreaLowerAreaRequest(MallType mallType, MallAuthentication authentication, Object requestObj) {
        super(mallType, authentication, requestObj);
    }

    @Override
    public String getRequestMethod() {
        return MallMethodConstants.AREA_MAPPING;
    }

    @Override
    public Class<AreaLowerAreaResponse> getResponseClass() {
        return AreaLowerAreaResponse.class;
    }
}
