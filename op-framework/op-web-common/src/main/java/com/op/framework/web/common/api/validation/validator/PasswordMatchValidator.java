package com.op.framework.web.common.api.validation.validator;

import com.op.framework.web.common.api.validation.annotation.FieldsMatch;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorContextImpl;
import org.springframework.util.ReflectionUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;

/**
 * 密码匹配校验器
 *
 * @author cdrcool
 */
public class PasswordMatchValidator implements ConstraintValidator<FieldsMatch, Object> {

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        FieldsMatch fieldsMatch = (FieldsMatch) ((ConstraintValidatorContextImpl) context)
                .getConstraintDescriptor().getAnnotation();

        Field passwordField = ReflectionUtils.findField(value.getClass(), fieldsMatch.first());
        Field confirmPasswordField = ReflectionUtils.findField(value.getClass(), fieldsMatch.second());
        if (passwordField == null || confirmPasswordField == null) {
            return false;
        }

        passwordField.setAccessible(true);
        confirmPasswordField.setAccessible(true);

        String password;
        try {
            password = (String) passwordField.get(value);
        } catch (IllegalAccessException e) {
            return false;
        }
        String confirmPassword;
        try {
            confirmPassword = (String) confirmPasswordField.get(value);
        } catch (IllegalAccessException e) {
            return false;
        }

        return password.equals(confirmPassword);
    }
}
