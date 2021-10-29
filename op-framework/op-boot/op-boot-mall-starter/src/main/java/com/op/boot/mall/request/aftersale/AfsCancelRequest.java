package com.op.boot.mall.request.aftersale;

import com.op.boot.mall.client.MallAuthentication;
import com.op.boot.mall.constans.MallMethodConstants;
import com.op.boot.mall.constans.MallType;
import com.op.boot.mall.request.MallRequest;
import com.op.boot.mall.response.aftersale.AfsCancelResponse;

/**
 * 京东订单取消处理类
 *
 * @author cdrcool
 */
public class AfsCancelRequest<P> extends MallRequest<P, AfsCancelResponse> {

    public AfsCancelRequest(MallType mallType, MallAuthentication authentication, P requestObj) {
        super(mallType, authentication, requestObj);
    }

    @Override
    public String getRequestMethod() {
        return MallMethodConstants.AFS_CANCEL;
    }

    @Override
    public Class<AfsCancelResponse> getResponseClass() {
        return AfsCancelResponse.class;
    }
}
