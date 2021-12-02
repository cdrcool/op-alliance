package com.op.boot.recommendation.entity;

import lombok.Data;

/**
 * 用户 id 映射
 *
 * @author chengdr01
 */
@Data
public class UserIdMapping {
    /**
     * 原 id
     */
    private String originId;

    /**
     * 新 id
     */
    private Long newId;
}
