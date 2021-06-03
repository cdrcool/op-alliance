package com.op.framework.web.common.api.validation.annotation;

import com.op.framework.web.common.api.validation.validator.PasswordMatchValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotEmpty;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

/**
 * 密码匹配校验注解
 *
 * @author cdrcool
 */
@NotEmpty
@Constraint(validatedBy = PasswordMatchValidator.class)
@Documented
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordMatch {

    String message() default "Password not match";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * @return 密码字段名
     */
    String password();

    /**
     * @return 确认密码字段名
     */
    String confirmPassword();
}
