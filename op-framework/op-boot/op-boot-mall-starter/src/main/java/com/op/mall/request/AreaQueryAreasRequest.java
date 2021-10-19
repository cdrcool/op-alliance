package com.op.mall.request;

import com.op.mall.client.MallAuthentication;
import com.op.mall.constans.MallMethodConstants;
import com.op.mall.constans.MallType;
import com.op.mall.response.AreaQueryAreasResponse;

/**
 * 地址查询下级地址请求
 *
 * @author cdrcool
 */
public class AreaQueryAreasRequest extends MallRequest<AreaQueryAreasResponse> {

    public AreaQueryAreasRequest(MallType mallType, MallAuthentication authentication, Object requestObj) {
        super(mallType, authentication, requestObj);
    }

    @Override
    public String getRequestMethod() {
        return MallMethodConstants.AREA_QUERY_AREAS;
    }

    @Override
    public Class<AreaQueryAreasResponse> getResponseClass() {
        return AreaQueryAreasResponse.class;
    }
}
