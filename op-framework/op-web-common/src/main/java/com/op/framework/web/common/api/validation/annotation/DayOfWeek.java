package com.op.framework.web.common.api.validation.annotation;

import com.op.framework.web.common.api.validation.validator.DayOfWeekValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 星期校验注解
 *
 * @author cdrcool
 */
@Constraint(validatedBy = DayOfWeekValidator.class)
@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DayOfWeek {

    String message() default "Unknown day of week";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}