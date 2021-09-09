package com.op.mall;

import com.op.mall.request.MallRequest;
import com.op.mall.response.MallResponse;

import java.util.List;

/**
 * 电商请求执行接口
 *
 * @author chengdr01
 */
public interface MallRequestExecutor {

    /**
     * 执行电商请求
     *
     * @param mallRequest 电商请求对象
     * @return 电商请求响应
     */
    <T extends MallResponse> T execute(MallRequest<T> mallRequest);

    /**
     * 批量执行电商请求
     *
     * @param mallRequests 电商请求对象列表
     * @return 电商请求响应列表
     */
    <T extends MallResponse> List<T> batchExecute(List<MallRequest<T>> mallRequests);
}
