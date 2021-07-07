package com.op.admin.utils;

import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 树工具类
 *
 * @author chengdr01
 */
public class TreeUtils {

    /**
     * 构造树 VO 列表
     *
     * @param entities         entities
     * @param voFunction       entity -> vo 映射
     * @param pidFunction      vo -> pid 映射
     * @param idFunction       vo -> id 映射
     * @param childrenConsumer (vo, children) 消费者
     * @param ancestorId       祖先 id
     * @param <V>              vo 泛型
     * @param <E>              entity 泛型
     * @return 树 VO 列表
     */
    public static <V, E> List<V> buildTree(List<E> entities,
                                           Function<E, V> voFunction,
                                           Function<V, Integer> pidFunction,
                                           Function<V, Integer> idFunction,
                                           BiConsumer<V, List<V>> childrenConsumer,
                                           Integer ancestorId) {
        List<V> voList =  entities.stream().map(voFunction).collect(Collectors.toList());
        Map<Integer, V> idMap = voList.stream().collect(Collectors.toMap(idFunction, o -> o));
        Map<Integer, List<V>> pidMap = voList.stream().collect(Collectors.groupingBy(pidFunction));

        pidMap.forEach((k, v) -> {
            V parent = idMap.get(k);
            if (parent != null) {
                childrenConsumer.accept(parent, v);
            }
        });

        return pidMap.get(ancestorId);
    }
}
