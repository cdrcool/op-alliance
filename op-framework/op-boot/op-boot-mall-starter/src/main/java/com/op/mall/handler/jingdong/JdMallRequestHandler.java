package com.op.mall.handler.jingdong;

import com.op.mall.client.jingdong.JdMallClient;
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
     * 京东电商 client
     */
    private final JdMallClient jdMallClient;

    public JdMallRequestHandler(JdMallClient jdMallClient) {
        this.jdMallClient = jdMallClient;
    }
}
