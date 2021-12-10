package com.op.boot.mall.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

/**
 * JSON 工具类
 *
 * @author chengdr01
 */
@Slf4j
public class JsonUtils {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 将对象序列化为字符串
     *
     * @param obj 对象
     * @return json 字符串
     */
    public static String toString(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error("序列化对象【" + obj.toString() + "异常】", e);
        }

        return "";
    }

    /**
     * 将字符串反序列化为对象
     *
     * @param json  json 字符串
     * @param clazz 对象的 class
     * @param <T>   对象的泛型
     * @return 对象
     */
    public static <T> T parse(String json, Class<T> clazz) {
        if (StringUtils.isNotBlank(json)) {
            try {
                return objectMapper.readValue(json, clazz);
            } catch (IOException e) {
                log.error("反序列化字符串【" + json + "到【" + clazz.getName() + "】异常】", e);
            }
        }

        return null;
    }

    /**
     * 将字符串反序列化为对象
     *
     * @param json          json 字符串
     * @param typeReference {@link TypeReference}
     * @param <T>           对象的泛型
     * @return 对象
     */
    public static <T> T parse(String json, TypeReference<T> typeReference) {
        if (StringUtils.isNotBlank(json)) {
            try {
                return objectMapper.readValue(json, typeReference);
            } catch (IOException e) {
                log.error("反序列化字符串【" + json + "到【" + typeReference.getType() + "】异常】", e);
            }
        }

        return null;
    }
}
