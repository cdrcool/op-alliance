package com.op.boot.recommendation.rescorer;

import org.apache.mahout.cf.taste.recommender.IDRescorer;

import java.util.List;

/**
 * 用于过滤非当前分类下的物品
 *
 * @author chengdr01
 */
public class CategoryItemIdsIDRescorer implements IDRescorer {
    private final List<Long> itemIds;

    public CategoryItemIdsIDRescorer(List<Long> itemIds) {
        this.itemIds = itemIds;
    }

    @Override
    public double rescore(long id, double originalScore) {
        return originalScore;
    }

    @Override
    public boolean isFiltered(long id) {
        return !itemIds.contains(id);
    }
}
