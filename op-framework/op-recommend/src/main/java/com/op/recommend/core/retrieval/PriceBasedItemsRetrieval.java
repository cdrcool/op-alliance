package com.op.recommend.core.retrieval;

import com.op.recommend.core.Item;
import com.op.recommend.core.User;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 基于价格召回候选物品
 *
 * @author chengdr01
 */
@Component
public class PriceBasedItemsRetrieval implements CandidateItemsRetrieval {

    @Override
    public List<Item> decide(User user, Item item, List<Item> candidates) {
        return null;
    }
}
