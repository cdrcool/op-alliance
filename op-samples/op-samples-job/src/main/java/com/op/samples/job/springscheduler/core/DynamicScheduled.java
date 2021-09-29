package com.op.samples.job.springscheduler.core;

import java.lang.annotation.*;

/**
 * 动态任务调度注解
 *
 * @author chengdr01
 */
@Documented
@Inherited
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DynamicScheduled {

    /**
     * @return job handler name
     */
    String value();


    /**
     * @return 执行任务前的初始化方法名
     */
    String init() default "";

    /**
     * @return 执行任务后的销毁方法名
     */
    String destroy() default "";
}
