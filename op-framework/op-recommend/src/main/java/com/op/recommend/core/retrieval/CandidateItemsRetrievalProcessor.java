package com.op.recommend.core.retrieval;

import com.op.recommend.core.Item;
import com.op.recommend.core.User;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 候选物品召回处理类
 *
 * @author chengdr01
 */
@Component
public class CandidateItemsRetrievalProcessor {
    private final List<CandidateItemsRetrieval> retrievals;

    public CandidateItemsRetrievalProcessor(List<CandidateItemsRetrieval> retrievals) {
        this.retrievals = retrievals;
    }

    public List<Item> process(User user, Item item, List<Item> candidates, List<Item> histories) {
        List<Item> results = retrievals.stream()
                .map(retrieval -> retrieval.decide(user, item, candidates))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        Map<String, Item> historyMap = Optional.ofNullable(histories).orElse(new ArrayList<>()).stream()
                .collect(Collectors.toMap(Item::getId, o -> o));

        return results.stream().filter(o -> historyMap.containsKey(o.getId())).collect(Collectors.toList());
    }
}
