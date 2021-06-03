package com.op.framework.web.common.api.validation.annotation;


import com.op.framework.web.common.api.validation.validator.SublistNotRepeatedValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

/**
 * 子表不重复校验注解
 *
 * @author cdrcool
 */
@Constraint(validatedBy = SublistNotRepeatedValidator.class)
@Documented
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
public @interface SublistNotRepeated {

    String message() default "Exist repeated records";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String property() default "id";
}
