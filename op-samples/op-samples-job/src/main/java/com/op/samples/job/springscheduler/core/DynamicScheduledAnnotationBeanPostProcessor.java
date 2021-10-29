package com.op.samples.job.springscheduler.core;

import com.op.samples.job.springscheduler.core.definition.JobDefinitionRepository;
import com.op.samples.job.springscheduler.core.definition.MethodJobDefinition;
import com.op.samples.job.springscheduler.core.manager.TaskManager;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.MethodIntrospector;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;

/**
 * Bean post-processor that registers methods annotated with @DynamicScheduled
 *
 * @author cdrcool
 */
@Slf4j
@Component
public class DynamicScheduledAnnotationBeanPostProcessor implements ApplicationContextAware, SmartInitializingSingleton, DisposableBean {
    private final JobDefinitionRepository jobDefinitionRepository;
    private final TaskManager taskManager;
    private ApplicationContext applicationContext;


    public DynamicScheduledAnnotationBeanPostProcessor(JobDefinitionRepository jobDefinitionRepository, TaskManager taskManager) {
        this.jobDefinitionRepository = jobDefinitionRepository;
        this.taskManager = taskManager;
    }

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void afterSingletonsInstantiated() {
        String[] beanNames = applicationContext.getBeanNamesForType(Object.class, false, true);
        Arrays.stream(beanNames).forEach(beanName -> {
            Object bean = applicationContext.getBean(beanName);

            Map<Method, DynamicScheduled> annotatedMethods = MethodIntrospector.selectMethods(bean.getClass(),
                    (MethodIntrospector.MetadataLookup<DynamicScheduled>) method -> AnnotatedElementUtils.findMergedAnnotation(method, DynamicScheduled.class));
            if (CollectionUtils.isEmpty(annotatedMethods)) {
                return;
            }

            annotatedMethods.forEach(((method, dynamicScheduled) -> {
                String name = dynamicScheduled.value().trim();
                if (!StringUtils.hasLength(name)) {
                    throw new RuntimeException("不合法的任务名称，bean：" + bean.getClass().getName() + "，method：" + method.getName());
                }

                if (jobDefinitionRepository.exist(name)) {
                    throw new RuntimeException("重名的任务名称：" + name);
                }

                Method initMethod = null;
                Method destroyMethod = null;
                if (dynamicScheduled.init().trim().length() > 0) {
                    try {
                        initMethod = bean.getClass().getDeclaredMethod(dynamicScheduled.init());
                        initMethod.setAccessible(true);
                    } catch (NoSuchMethodException e) {
                        throw new RuntimeException("未找到初始化方法，bean：" + bean.getClass().getName() + "，method：" + method.getName() + "，initMethod：" + dynamicScheduled.init());
                    }
                }
                if (dynamicScheduled.destroy().trim().length() > 0) {
                    try {
                        destroyMethod = bean.getClass().getDeclaredMethod(dynamicScheduled.destroy());
                        destroyMethod.setAccessible(true);
                    } catch (NoSuchMethodException e) {
                        throw new RuntimeException("未找到销毁方法，bean：" + bean.getClass().getName() + "，method：" + method.getName() + "，destroyMethod：" + dynamicScheduled.destroy());
                    }
                }

                jobDefinitionRepository.add(name, new MethodJobDefinition(name, bean, method, initMethod, destroyMethod));
            }));

//            taskManager.scheduleAllTasks();
        });
    }

    @Override
    public void destroy() {
        // todo 终止所有定时任务
    }
}
