package com.op.boot.mall.handler;

import com.op.boot.mall.request.MallRequest;
import com.op.boot.mall.request.MallRequestAction;
import com.op.boot.mall.response.MallResponse;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 电商请求处理类的注册类
 *
 * @author cdrcool
 */
public class MallRequestHandlerRegistry {
    private static final ConcurrentHashMap<MallRequestAction, MallRequestHandler> STORE = new ConcurrentHashMap<>();

    public static <T extends MallRequest<?, R>, R extends MallResponse> void addHandler(MallRequestAction action, MallRequestHandler<T, ?, R> handler) {
        STORE.put(action, handler);
    }

    public static void removeHandler(MallRequestAction action) {
        STORE.remove(action);
    }

    public static <T extends MallRequest<?, R>, R extends MallResponse> MallRequestHandler<T, ?, R> getHandler(MallRequestAction action) {
        return STORE.get(action);
    }
}
