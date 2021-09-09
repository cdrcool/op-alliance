package com.op.mall.handler;

import com.op.mall.constans.MallType;

/**
 * 电商请求处理代理类接口
 *
 * @author chengdr01
 */
public interface MallRequestHandlerProxy extends MallRequestHandler {

    /**
     * 如果可以处理当前电商请求对象，就返回 true
     *
     * @param mallType 电商类型
     * @return true or false
     */
    boolean supports(MallType mallType);

    /**
     * 对象创建后要执行的动作
     */
    @Override
    default void postConstruct() {

    }

    /**
     * 对象销毁前要执行的动作
     */
    @Override
    default void preDestroy() {

    }
}
