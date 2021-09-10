package com.op.mall.experiment;

import com.op.mall.request.MallRequest;
import com.op.mall.response.MallResponse;

public interface MallRequestExecutorStrategy {

    <T extends MallResponse> T handle(MallRequest<T> mallRequest);
}
