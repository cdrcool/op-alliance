package com.op.recommend.core.retrieval;

import com.op.recommend.core.Item;
import com.op.recommend.core.User;

import java.util.List;

/**
 * 推荐系统召回层接口，支持多路召回
 *
 * @author chengdr01
 */
public interface CandidateItemsRetrieval {

    /**
     * 处理召回动作
     *
     * @param user       当前用户
     * @param item       当前物品
     * @param candidates 候选物品集
     * @return 过滤后的物品集
     */
    List<Item> decide(User user, Item item, List<Item> candidates);
}
