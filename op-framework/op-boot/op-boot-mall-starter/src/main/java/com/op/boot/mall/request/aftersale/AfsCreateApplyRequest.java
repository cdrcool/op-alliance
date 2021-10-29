package com.op.boot.mall.request.aftersale;

import com.op.boot.mall.client.MallAuthentication;
import com.op.boot.mall.constans.MallMethodConstants;
import com.op.boot.mall.constans.MallType;
import com.op.boot.mall.request.MallRequest;
import com.op.boot.mall.response.aftersale.AfsCreateApplyResponse;

/**
 * 京东订单取消处理类
 *
 * @author cdrcool
 */
public class AfsCreateApplyRequest<P> extends MallRequest<P, AfsCreateApplyResponse> {

    public AfsCreateApplyRequest(MallType mallType, MallAuthentication authentication, P requestObj) {
        super(mallType, authentication, requestObj);
    }

    @Override
    public String getRequestMethod() {
        return MallMethodConstants.AFS_CREATE_APPLY;
    }

    @Override
    public Class<AfsCreateApplyResponse> getResponseClass() {
        return AfsCreateApplyResponse.class;
    }
}
