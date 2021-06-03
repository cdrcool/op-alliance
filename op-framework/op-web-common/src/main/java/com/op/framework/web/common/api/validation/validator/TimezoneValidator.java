package com.op.framework.web.common.api.validation.validator;

import com.op.framework.web.common.api.validation.annotation.Timezone;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.TimeZone;

/**
 * 时区校验器
 *
 * @author cdrcool
 */
public class TimezoneValidator implements ConstraintValidator<Timezone, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (StringUtils.isBlank(value)) {
            return true;
        }

        return Arrays.asList(TimeZone.getAvailableIDs()).contains(value);
    }
}
