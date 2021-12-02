package com.op.boot.recommendation.rescorer;

import org.apache.mahout.cf.taste.recommender.Rescorer;
import org.apache.mahout.common.LongPair;

import java.util.List;

/**
 * 用于过滤非当前分类下的物品
 *
 * @author chengdr01
 */
public class CategoryItemIdsRescorer implements Rescorer<LongPair> {
    private final List<Long> itemIds;

    public CategoryItemIdsRescorer(List<Long> itemIds) {
        this.itemIds = itemIds;
    }

    @Override
    public double rescore(LongPair thing, double originalScore) {
        return originalScore;
    }

    @Override
    public boolean isFiltered(LongPair thing) {
        return !itemIds.contains(thing.getFirst());
    }
}
