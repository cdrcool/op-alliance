package com.op.boot.mall.request.aftersale;

import com.op.boot.mall.client.MallAuthentication;
import com.op.boot.mall.constans.MallMethodConstants;
import com.op.boot.mall.constans.MallType;
import com.op.boot.mall.request.MallRequest;
import com.op.boot.mall.response.aftersale.AfsWaybillResponse;

/**
 * 京东订单取消处理类
 *
 * @author cdrcool
 */
public class AfsQueryWaybillRequest<P> extends MallRequest<P, AfsWaybillResponse> {

    public AfsQueryWaybillRequest(MallType mallType, MallAuthentication authentication, P requestObj) {
        super(mallType, authentication, requestObj);
    }

    @Override
    public String getRequestMethod() {
        return MallMethodConstants.AFS_QUERY_WAYBILL;
    }

    @Override
    public Class<AfsWaybillResponse> getResponseClass() {
        return AfsWaybillResponse.class;
    }
}
