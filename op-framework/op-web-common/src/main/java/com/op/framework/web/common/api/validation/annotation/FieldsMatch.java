package com.op.framework.web.common.api.validation.annotation;

import com.op.framework.web.common.api.validation.validator.PasswordMatchValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;

/**
 * 字段（密码）匹配校验注解
 *
 * @author cdrcool
 */
@Constraint(validatedBy = PasswordMatchValidator.class)
@Documented
@Target({TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldsMatch {

    String message() default "Fields not match";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    /**
     * @return 字段名1
     */
    String first();

    /**
     * @return 字段名2
     */
    String second();
}
