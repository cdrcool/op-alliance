package com.op.framework.web.common.api.validation;

/**
 * 添加子表非空注解{@link com.op.framework.web.common.api.validation.annotation.SublistNotEmpty}
 * 或子表不能重复注解{@link com.op.framework.web.common.api.validation.annotation.SublistNotRepeated}时，
 * 需要告知程序如何识别数据是被逻辑删除的
 *
 * @author cdrcool
 */
public interface LogicDeleteAware {

    /**
     * 标识数据是否已被逻辑删除
     *
     * @return true or false
     */
    boolean isDeleted();
}
