package com.op.mall.handler.jingdong;

import com.jd.open.api.sdk.JdClient;
import com.op.mall.handler.MallRequestHandler;
import lombok.Getter;

/**
 * 京东电商请求处理抽象类
 *
 * @author cdrcool
 */
@Getter
public abstract class JdMallRequestHandler implements MallRequestHandler {
    /**
     * 京东 sdk client
     */
    private final JdClient jdClient;

    public JdMallRequestHandler(JdClient jdClient) {
        this.jdClient = jdClient;
    }
}
