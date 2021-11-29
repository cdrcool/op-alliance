package com.op.recommend.core.retrieval;

import com.op.recommend.core.Item;
import com.op.recommend.core.User;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 候选物品召回代理类
 *
 * @author chengdr01
 */
@Component
public class CandidateItemsRetrievalProxy implements CandidatesRetrieval {
    private final List<CandidatesRetrieval> retrievals;

    public CandidateItemsRetrievalProxy(List<CandidatesRetrieval> retrievals) {
        this.retrievals = retrievals;
    }

    @Override
    public List<Item> decide(User user, Item item, List<Item> candidates) {
        return retrievals.stream()
                .map(retrieval -> retrieval.decide(user, item, candidates))
                .flatMap(Collection::stream)
                .collect(Collectors.collectingAndThen(Collectors.toCollection(() ->
                        new TreeSet<>(Comparator.comparing(Item::getId))), ArrayList::new));
    }
}
