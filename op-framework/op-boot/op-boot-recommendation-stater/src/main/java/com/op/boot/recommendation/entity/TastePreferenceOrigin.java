package com.op.boot.recommendation.entity;

import lombok.Data;

import java.sql.Timestamp;

/**
 * 用户口味偏好原始表
 *
 * @author chengdr01
 */
@Data
public class TastePreferenceOrigin {
    /**
     * 用户 id
     */
    private String userId;

    /**
     * 物品 id
     */
    private Long itemId;

    /**
     * 偏好
     */
    private Float preference;

    /**
     * 时间戳
     */
    private Timestamp timestamp;

    /**
     * 分类 id
     */
    private String categoryId;

    /**
     * 条形码
     */
    private String barCode;
}
