package com.op.framework.web.common.api.validation.validator;

import com.op.framework.web.common.api.validation.annotation.DayOfWeek;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

/**
 * 星期校验器
 *
 * @author cdrcool
 */
public class DayOfWeekValidator implements ConstraintValidator<DayOfWeek, String> {
    /**
     * 星期常量数组
     */
    private static final List<String> DAY_OF_WEEK = Arrays.asList("sunday", "monday", "tuesday", "wednesday", "thursday", "friday", "saturday");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (StringUtils.isBlank(value)) {
            return true;
        }

        return DAY_OF_WEEK.contains(value.trim().toLowerCase());
    }
}
