package com.op.mall.handler.jingdong;

import com.op.mall.client.jingdong.JdMallClient;
import com.op.mall.handler.MallRequestHandler;

/**
 * 京东电商请求处理抽象类
 *
 * @author cdrcool
 */
public abstract class JdMallRequestHandler implements MallRequestHandler {
    /**
     * 京东电商 client
     */
    private final JdMallClient jdMallClient;

    public JdMallRequestHandler(JdMallClient jdMallClient) {
        this.jdMallClient = jdMallClient;
    }

    /**
     * 返回京东电商 client
     *
     * @return 京东电商 client
     */
    protected JdMallClient getJdMallClient() {
        return jdMallClient;
    }
}
