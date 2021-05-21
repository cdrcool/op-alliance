package com.op.framework.web.common.persistence.audit;

import com.op.framework.web.common.utils.ReflectionUtils;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Properties;

/**
 * 审计拦截器
 *
 * @author cdrcool
 */
@Intercepts({@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})})
public class AuditingInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();

        Object parameter = invocation.getArgs()[1];
        List<Field> fields = ReflectionUtils.getAllDeclaredFields(parameter.getClass());

        Integer userId = 1;
        LocalDateTime now = LocalDateTime.now();

        if (SqlCommandType.INSERT == sqlCommandType) {
            for (Field field : fields) {
                if (AnnotationUtils.getAnnotation(field, CreatedBy.class) != null) {
                    field.setAccessible(true);
                    field.set(parameter, userId);
                    field.setAccessible(false);
                }
                if (AnnotationUtils.getAnnotation(field, CreatedDate.class) != null) {
                    field.setAccessible(true);
                    field.set(parameter, now);
                    field.setAccessible(false);
                }
            }
        } else if (SqlCommandType.UPDATE == sqlCommandType) {
            for (Field field : fields) {
                if (AnnotationUtils.getAnnotation(field, LastModifiedBy.class) != null) {
                    field.setAccessible(true);
                    field.set(parameter, userId);
                    field.setAccessible(false);
                }
                if (AnnotationUtils.getAnnotation(field, LastModifiedDate.class) != null) {
                    field.setAccessible(true);
                    field.set(parameter, now);
                    field.setAccessible(false);
                }
            }
        }
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof Executor) {
            return Plugin.wrap(target, this);
        }
        return target;
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
