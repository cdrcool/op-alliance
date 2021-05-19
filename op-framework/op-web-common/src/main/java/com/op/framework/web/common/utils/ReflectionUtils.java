package com.op.framework.web.common.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 反射工具类
 *
 * @author cdrcool
 */
public class ReflectionUtils {

    /**
     * 获取类继承结构中所有声明的字段
     *
     * @param clazz 类class
     * @return 字段列表
     */
    public static List<Field> getAllDeclaredFields(Class<?> clazz) {
        List<Field> fields = new ArrayList<>();
        do {
            fields.addAll(Arrays.stream(clazz.getDeclaredFields())
                    .filter(field -> !java.lang.reflect.Modifier.isFinal(field.getModifiers()))
                    .collect(Collectors.toList()));
            clazz = clazz.getSuperclass();
        } while (clazz != null);
        return fields;
    }
}
