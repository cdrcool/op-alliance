package com.op.framework.web.common.api.validation.validator;

import com.op.framework.web.common.api.validation.LogicDeleteAware;
import com.op.framework.web.common.api.validation.annotation.SublistNotEmpty;
import org.apache.commons.collections4.CollectionUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

/**
 * 子表非空校验器
 * 业务中采用逻辑删除，所以非空校验需要判断是否全部逻辑删除
 *
 * @author cdrcool
 */
public class SublistNotEmptyValidator implements ConstraintValidator<SublistNotEmpty, List<? extends LogicDeleteAware>> {

    @Override
    public void initialize(SublistNotEmpty constraintAnnotation) {

    }

    @Override
    public boolean isValid(List<? extends LogicDeleteAware> value, ConstraintValidatorContext context) {
        if (CollectionUtils.isEmpty(value)) {
            return true;
        }

        return !value.stream().allMatch(LogicDeleteAware::isDeleted);
    }
}
