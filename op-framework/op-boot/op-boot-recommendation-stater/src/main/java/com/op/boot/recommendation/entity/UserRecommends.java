package com.op.boot.recommendation.entity;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

/**
 * 用户推荐物品集
 *
 * @author chengdr01
 */
@Builder
@Data
public class UserRecommends {
    /**
     * 用户 id
     */
    private Long userId;

    /**
     * 分类 id
     */
    private Long categoryId;

    /**
     * 推荐的物品 ids（多个逗号分隔）
     */
    private String itemIds;

    /**
     * 时间戳
     */
    private Timestamp timestamp;

    /**
     * 推荐类型
     */
    private Integer recommenderType;
}
