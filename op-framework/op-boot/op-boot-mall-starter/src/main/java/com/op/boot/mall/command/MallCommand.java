package com.op.boot.mall.command;

import com.op.boot.mall.request.MallRequest;
import com.op.boot.mall.response.MallResponse;

/**
 * 电商命令接口
 *
 * @author chengdr01
 */
public interface MallCommand<T extends MallRequest<P, R>, P, R extends MallResponse> {

    /**
     * 执行电商命令
     *
     * @param mallRequest 电商请求
     * @return 电商响应
     */
    R execute(T mallRequest);
}
