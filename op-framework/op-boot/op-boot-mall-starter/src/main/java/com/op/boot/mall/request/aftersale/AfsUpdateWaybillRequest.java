package com.op.boot.mall.request.aftersale;

import com.op.boot.mall.client.MallAuthentication;
import com.op.boot.mall.constans.MallMethodConstants;
import com.op.boot.mall.constans.MallType;
import com.op.boot.mall.request.MallRequest;
import com.op.boot.mall.response.aftersale.AfsUpdateWaybillResponse;

/**
 * 京东订单取消处理类
 *
 * @author cdrcool
 */
public class AfsUpdateWaybillRequest<P>  extends MallRequest<P, AfsUpdateWaybillResponse> {

    public AfsUpdateWaybillRequest(MallType mallType, MallAuthentication authentication, P requestObj) {
        super(mallType, authentication, requestObj);
    }

    @Override
    public String getRequestMethod() {
        return MallMethodConstants.AFS_UPDATE_WAYBILL;
    }

    @Override
    public Class<AfsUpdateWaybillResponse> getResponseClass() {
        return AfsUpdateWaybillResponse.class;
    }
}
