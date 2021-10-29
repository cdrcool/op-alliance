package com.op.samples.job.springscheduler.core.definition;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.function.Consumer;

/**
 * 方法级任务处理实现类
 *
 * @author cdrcool
 */
@Slf4j
public class MethodJobDefinition extends AbstractJobDefinition implements JobDefinition {

    /**
     * 目标对象
     */
    private final Object target;

    /**
     * 执行方法
     */
    private final Method method;

    /**
     * 初始化方法
     */
    private final Method initMethod;

    /**
     * 销毁方法
     */
    private final Method destroyMethod;

    public MethodJobDefinition(String jobName, Object target, Method method, Method initMethod, Method destroyMethod) {
        super(jobName);
        this.target = target;
        this.method = method;
        this.initMethod = initMethod;
        this.destroyMethod = destroyMethod;
    }

    @Override
    public Consumer<Void> getConsumer() {
        return (param) -> {
            if (initMethod != null) {
                try {
                    initMethod.invoke(target);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    String message = MessageFormat.format("调用定时任务【{0}】的初始化方法【{1}】异常", this.getJobName(), this.initMethod);
                    log.error(message);
                    throw new RuntimeException(message, e);
                }
            }

            try {
                method.invoke(target);
            } catch (IllegalAccessException | InvocationTargetException e) {
                String message = MessageFormat.format("调用定时任务【{0}】的执行方法【{1}】异常", this.getJobName(), this.initMethod);
                log.error(message);
                throw new RuntimeException(message, e);
            }

            if (destroyMethod != null) {
                try {
                    destroyMethod.invoke(target);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    String message = MessageFormat.format("调用定时任务【{0}】的销毁方法【{1}】异常", this.getJobName(), this.initMethod);
                    log.error(message);
                    throw new RuntimeException(message, e);
                }
            }
        };
    }
}
