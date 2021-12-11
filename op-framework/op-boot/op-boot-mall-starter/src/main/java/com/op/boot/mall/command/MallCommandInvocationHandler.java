package com.op.boot.mall.command;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 电商命令调用 Handler
 * <p>
 * 直接基于 Java 原生实现，需要考虑依赖（日志记录）注入问题，可使用 Spring AOP
 * <p>
 * 由于已有命令调用者，直接由命令调用者记录请求日志记录
 * <p>
 * 使用方式如下（留作备份）：
 * <p>
 * MallCommandInvocationHandler invocationHandler = new MallCommandInvocationHandler(mallCommand);
 * return (MallCommand) Proxy.newProxyInstance(mallCommand.getClass().getClassLoader(), mallCommand.getClass().getInterfaces(), invocationHandler);
 *
 * @author cdrcool
 */
public class MallCommandInvocationHandler implements InvocationHandler {
    private final MallCommand<?, ?> mallCommand;

    public MallCommandInvocationHandler(MallCommand<?, ?> mallCommand) {
        this.mallCommand = mallCommand;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return method.invoke(mallCommand, args);
    }
}
