package com.op.boot.mall.request.area;

import com.op.boot.mall.client.MallAuthentication;
import com.op.boot.mall.constans.MallMethodConstants;
import com.op.boot.mall.constans.MallType;
import com.op.boot.mall.request.MallRequest;
import com.op.boot.mall.response.area.AreaQueryAreasResponse;

/**
 * 地址查询下级地址请求
 *
 * @param <P> 请求对象类型
 * @author cdrcool
 */
public class AreaQueryAreasRequest<P> extends MallRequest<P, AreaQueryAreasResponse> {

    public AreaQueryAreasRequest(MallType mallType, MallAuthentication authentication, P requestObj) {
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
