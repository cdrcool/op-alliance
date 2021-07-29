package com.op.admin.utils;

import org.apache.commons.collections4.CollectionUtils;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;
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
     * @param <ID>             id 泛型
     * @return 树 VO 列表
     */
    public static <V, E, ID> List<V> buildTree(List<E> entities,
                                               Function<E, V> voFunction,
                                               Function<V, ID> pidFunction,
                                               Function<V, ID> idFunction,
                                               BiConsumer<V, List<V>> childrenConsumer,
                                               ID ancestorId) {
        List<V> voList = entities.stream().map(voFunction).collect(Collectors.toList());
        Map<ID, V> idMap = voList.stream().collect(Collectors.toMap(idFunction, o -> o));
        Map<ID, List<V>> pidMap = voList.stream().collect(Collectors.groupingBy(pidFunction));

        pidMap.forEach((k, v) -> {
            V parent = idMap.get(k);
            if (parent != null) {
                childrenConsumer.accept(parent, v);
            }
        });

        return pidMap.get(ancestorId);
    }

    /**
     * 递归构造树 VO 列表
     *
     * @param pidFunction      vo -> pid 映射
     * @param idFunction       vo -> id 映射
     * @param childrenConsumer (vo, children) 消费者
     * @param ancestorId       祖先 id
     * @param filter           过滤条件
     * @param <V>              vo 泛型
     * @param <ID>             id 泛型
     * @return 树 VO 列表
     */
    public static <V, ID> List<V> buildTreeRecursion(List<V> list,
                                                     Function<V, ID> pidFunction,
                                                     Function<V, ID> idFunction,
                                                     BiConsumer<V, List<V>> childrenConsumer,
                                                     ID ancestorId,
                                                     Predicate<V> filter) {
        Predicate<V> predicate = Optional.ofNullable(filter).orElse(v -> true);

        Map<Boolean, List<V>> descendantMap = list.stream()
                .collect(Collectors.partitioningBy(item -> pidFunction.apply(item).equals(ancestorId)));
        List<V> children = descendantMap.get(true);
        Iterator<V> iterator = children.iterator();
        while (iterator.hasNext()) {
            V child = iterator.next();
            List<V> curChildren = buildTreeRecursion(descendantMap.get(false), pidFunction, idFunction, childrenConsumer, idFunction.apply(child), filter);
            List<V> filteredChildren = curChildren.stream().filter(predicate).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(filteredChildren)) {
                childrenConsumer.accept(child, filteredChildren);
            } else if (!predicate.test(child)) {
                iterator.remove();
            }
        }
        return children;
    }
}
