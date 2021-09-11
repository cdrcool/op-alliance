package com.op.mall;

import com.op.mall.business.BusinessException;
import com.op.mall.handler.MallRequestHandler;
import com.op.mall.handler.MallRequestHandlerRegistry;
import com.op.mall.request.MallRequest;
import com.op.mall.request.MallRequestAction;
import com.op.mall.response.MallResponse;

import java.util.Map;
import java.util.function.Supplier;

/**
 * 电商请求执行类
 *
 * @author chengdr01
 */
public class MallRequestExecutor {

    /**
     * 执行电商请求
     *
     * @param action             电商请求动作
     * @param requestSupplierMap 电商请求对象提供者 map
     * @param <T>                请求对象泛型
     * @param <R>                请求响应泛型
     * @return 请求响应
     */
    public <T extends MallRequest<R>, R extends MallResponse> R execute(MallRequestAction action, Map<String, Supplier<T>> requestSupplierMap) {
        // 1. 获取当前电商请求对象
        T request = requestSupplierMap.getOrDefault(action.getMallType(), () -> null).get();

        // 2. 获取当前电商请求处理类
        MallRequestHandler handler = MallRequestHandlerRegistry.getHandler(action);
        if (handler == null) {
            throw new BusinessException("未注册的电商请求动作");
        }

        // 3. 执行电商请求
        R response = handler.handle(request);

        // 4. 返回电商请求响应
        return response;
    }
}
