package com.op.boot.recommendation.dto;

import lombok.Builder;
import lombok.Data;

/**
 * 推荐响应
 *
 * @author chengdr01
 */
@Builder
@Data
public class RecommendResponse {
    /**
     * 物品 id
     */
    private Long itemId;

    /**
     * 推荐类型
     */
    private Integer recommenderType;

    /**
     * 推荐类型描述
     */
    private String recommenderDesc;
}
