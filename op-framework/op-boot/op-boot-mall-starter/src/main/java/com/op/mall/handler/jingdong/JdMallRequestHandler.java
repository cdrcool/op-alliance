package com.op.mall.handler.jingdong;

import com.op.mall.constans.MallType;
import com.op.mall.handler.MallRequestHandler;

/**
 * 京东电商请求处理类接口
 *
 * @author cdrcool
 */
public interface JdMallRequestHandler extends MallRequestHandler {

    /**
     * 返回电商类型
     *
     * @return 电商类型
     */
    default String mallType() {
        return MallType.JINGDONG.getValue();
    }
}
