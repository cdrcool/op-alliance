package com.op.mall.handler;

import com.op.mall.constans.MallType;

import java.text.MessageFormat;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 电商请求处理类的注册类
 *
 * @author cdrcool
 */
public class MallRequestHandlerRegistry {
    private static final ConcurrentHashMap<String, MallRequestHandler> STORE = new ConcurrentHashMap<>();

    public static void addHandler(String mallType, String method, MallRequestHandler handler) {
        STORE.put(formatKey(mallType, method), handler);
    }

    public static void removeHandler(String mallType, String method) {
        STORE.remove(formatKey(mallType, method));
    }

    public static MallRequestHandler getHandler(String mallType, String method) {
        return STORE.get(formatKey(mallType, method));
    }

    private static String formatKey(String mallType, String method) {
        return MessageFormat.format("{0}-{1}", mallType, method);
    }
}
