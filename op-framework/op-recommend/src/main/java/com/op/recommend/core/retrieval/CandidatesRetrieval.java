package com.op.recommend.core.retrieval;

import com.op.recommend.core.Item;
import com.op.recommend.core.User;

import java.util.List;

/**
 * 候选物品召回接口
 *
 * @author chengdr01
 */
public interface CandidatesRetrieval {

    /**
     * 过滤候选物品集
     *
     * @param user       当前用户
     * @param item       当前物品
     * @param candidates 候选物品集
     * @return 过滤后的物品集
     */
    List<Item> decide(User user, Item item, List<Item> candidates);
}
