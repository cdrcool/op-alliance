package com.op.recommend.core.retrieval;

import com.op.recommend.core.Item;
import com.op.recommend.core.User;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 基于价格的候选物品召回类
 *
 * @author chengdr01
 */
@Component
public class PriceBasedRetrieval implements CandidatesRetrieval {

    @Override
    public List<Item> decide(User user, Item item, List<Item> candidates) {
        return null;
    }
}
