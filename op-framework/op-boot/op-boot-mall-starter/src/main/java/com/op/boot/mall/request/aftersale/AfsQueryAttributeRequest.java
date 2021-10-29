package com.op.boot.mall.request.aftersale;

import com.op.boot.mall.client.MallAuthentication;
import com.op.boot.mall.constans.MallMethodConstants;
import com.op.boot.mall.constans.MallType;
import com.op.boot.mall.request.MallRequest;
import com.op.boot.mall.response.aftersale.AfsAttributeResponse;

/**
 * 京东订单取消处理类
 *
 * @author cdrcool
 */
public class AfsQueryAttributeRequest<P>  extends MallRequest<P, AfsAttributeResponse> {

    public AfsQueryAttributeRequest(MallType mallType, MallAuthentication authentication, P requestObj) {
        super(mallType, authentication, requestObj);
    }

    @Override
    public String getRequestMethod() {
        return MallMethodConstants.AFS_QUERY_ATTRIBUTES;
    }

    @Override
    public Class<AfsAttributeResponse> getResponseClass() {
        return AfsAttributeResponse.class;
    }
}
