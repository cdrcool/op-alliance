package com.op.boot.mall.constants;

import com.op.boot.mall.exception.MallException;
import lombok.Getter;

import java.util.Arrays;

/**
 * 发票类型
 *
 * @author cdrcool
 */
@Getter
public enum InvoiceType {
    /**
     * 增值税专票
     */
    TAX_INVOICE(2, "增值税专票"),

    /**
     * 电子发票
     */
    ELECTRONIC_INVOICE(3, "电子发票"),
    ;

    /**
     * 值
     */
    private final Integer value;

    /**
     * 描述
     */
    private final String desc;

    InvoiceType(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    /**
     * 根据值获取枚举值
     *
     * @param value 值
     * @return 枚举值
     */
    public static InvoiceType getByValue(Integer value) {
        return Arrays.stream(values()).filter(item -> item.value.equals(value)).findAny()
                .orElseThrow(() -> new MallException("未定义的发票类型【" + value + "】"));
    }
}
