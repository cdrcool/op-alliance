package com.op.mall.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Objects;

/**
 * 电商请求动作
 *
 * @author cdrcool
 */
@AllArgsConstructor
@Data
public class MallRequestAction {
    /**
     * 电商类型
     */
    private String mallType;

    /**
     * 请求方法
     */
    private String method;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MallRequestAction that = (MallRequestAction) o;
        return Objects.equals(mallType, that.mallType) && Objects.equals(method, that.method);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mallType, method);
    }
}