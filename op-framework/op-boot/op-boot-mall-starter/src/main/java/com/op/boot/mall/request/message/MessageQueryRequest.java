package com.op.boot.mall.request.message;

import com.op.boot.mall.client.MallAuthentication;
import com.op.boot.mall.constans.MallMethodConstants;
import com.op.boot.mall.constans.MallType;
import com.op.boot.mall.request.MallRequest;
import com.op.boot.mall.response.message.MessageQueryResponse;

/**
 * 京东订单取消处理类
 *
 * @author cdrcool
 */
public class MessageQueryRequest<P> extends MallRequest<P, MessageQueryResponse> {

    public MessageQueryRequest(MallType mallType, MallAuthentication authentication, P requestObj) {
        super(mallType, authentication, requestObj);
    }

    @Override
    public String getRequestMethod() {
        return MallMethodConstants.MESSAGE_QUERY;
    }

    @Override
    public Class<MessageQueryResponse> getResponseClass() {
        return MessageQueryResponse.class;
    }
}
