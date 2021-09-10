package com.op.mall.experiment;

import com.op.mall.request.MallRequest;
import com.op.mall.response.MallResponse;

/**
 * 京东电商请求执行策略类
 *
 * @author chengdr01
 */
public class JdMallRequestExecutorStrategy implements MallRequestExecutorStrategy {

    @Override
    public <T extends MallResponse> T handle(MallRequest<T> mallRequest) {
        return null;
    }
}
