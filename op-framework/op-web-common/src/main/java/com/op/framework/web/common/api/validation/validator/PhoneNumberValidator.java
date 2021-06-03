package com.op.framework.web.common.api.validation.validator;

import com.op.framework.web.common.api.validation.annotation.PhoneNumber;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 手机号校验器
 *
 * @author cdrcool
 */
public class PhoneNumberValidator implements ConstraintValidator<PhoneNumber, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (StringUtils.isBlank(value)) {
            return true;
        }

        return value.matches("[0-9]+") && value.length() > 8 && value.length() < 14;
    }
}

