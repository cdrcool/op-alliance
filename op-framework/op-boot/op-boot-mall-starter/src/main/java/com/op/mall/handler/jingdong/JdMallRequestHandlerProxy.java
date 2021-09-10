package com.op.mall.handler.jingdong;

import com.op.mall.constans.MallType;
import com.op.mall.handler.MallRequestHandler;
import com.op.mall.handler.MallRequestHandlerProxy;
import com.op.mall.handler.MallRequestHandlerRegistry;
import com.op.mall.request.MallRequest;
import com.op.mall.response.MallResponse;

/**
 * 京东电商请求处理代理类
 *
 * @author chengdr01
 */
public class JdMallRequestHandlerProxy implements MallRequestHandlerProxy {

    @Override
    public <T extends MallResponse> T handle(MallRequest<T> mallRequest) {
        MallRequestHandler handler = MallRequestHandlerRegistry.getHandler(mallRequest.getMallType(), mallRequest.getRequestMethod());
        return handler.handle(mallRequest);
    }

    @Override
    public boolean supports(MallType mallType) {
        return MallType.JINGDONG.equals(mallType);
    }
}
