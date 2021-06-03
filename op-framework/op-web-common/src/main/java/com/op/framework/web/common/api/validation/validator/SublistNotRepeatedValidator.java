package com.op.framework.web.common.api.validation.validator;

import com.op.framework.web.common.api.validation.LogicDeleteAware;
import com.op.framework.web.common.api.validation.annotation.SublistNotRepeated;
import org.apache.commons.collections4.CollectionUtils;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorContextImpl;
import org.springframework.util.ReflectionUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 子表不重复校验器
 * 业务中采用逻辑删除，所以非空校验需要额外处理
 *
 * @author cdrcool
 */
public class SublistNotRepeatedValidator implements ConstraintValidator<SublistNotRepeated, List<? extends LogicDeleteAware>> {

    @Override
    public void initialize(SublistNotRepeated constraintAnnotation) {

    }

    @Override
    public boolean isValid(List<? extends LogicDeleteAware> value, ConstraintValidatorContext context) {
        if (CollectionUtils.isEmpty(value)) {
            return true;
        }

        List<? extends LogicDeleteAware> dataList = value.stream()
                .filter(item -> !item.isDeleted())
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(dataList)) {
            return true;
        }

        SublistNotRepeated sublistNotRepeated = (SublistNotRepeated) ((ConstraintValidatorContextImpl) context)
                .getConstraintDescriptor().getAnnotation();
        return dataList.stream()
                .map(data -> {
                    Field field = ReflectionUtils.findField(data.getClass(), sublistNotRepeated.property());
                    if (field == null) {
                        return null;
                    }
                    return ReflectionUtils.getField(field, data);
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(item -> item, item -> 1, Integer::sum))
                .entrySet()
                .stream()
                .allMatch(entry -> entry.getValue() <= 1);
    }
}
