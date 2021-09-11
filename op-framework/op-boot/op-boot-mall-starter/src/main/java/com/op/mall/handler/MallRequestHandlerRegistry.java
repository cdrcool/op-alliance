package com.op.mall.handler;

import com.op.mall.request.MallRequestAction;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 电商请求处理类的注册类
 *
 * @author cdrcool
 */
public class MallRequestHandlerRegistry {
    private static final ConcurrentHashMap<MallRequestAction, MallRequestHandler> STORE = new ConcurrentHashMap<>();

    public static void addHandler(MallRequestAction action, MallRequestHandler handler) {
        STORE.put(action, handler);
    }

    public static void removeHandler(MallRequestAction action) {
        STORE.remove(action);
    }

    public static MallRequestHandler getHandler(MallRequestAction action) {
        return STORE.get(action);
    }
}
