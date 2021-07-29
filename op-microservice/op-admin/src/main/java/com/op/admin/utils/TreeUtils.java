package com.op.admin.utils;

import org.apache.commons.collections4.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
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
     * @param <E>              entity 泛型
     * @param <V>              vo 泛型
     * @param <ID>             id 泛型
     * @return 树 VO 列表
     */
    public static <E, V, ID> List<V> buildTree(List<E> entities,
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
     * @param voFunction       entity -> vo 映射
     * @param childrenConsumer (vo, children) 消费者
     * @param ancestorId       祖先 id
     * @param filter           过滤条件
     * @param <E>              entity 泛型
     * @param <V>              vo 泛型
     * @param <ID>             id 泛型
     * @return 树 VO 列表
     */
    public static <E, ID, V> List<V> buildTreeRecursion(List<E> entities,
                                                        Function<E, ID> pidFunction,
                                                        Function<E, ID> idFunction,
                                                        Function<E, V> voFunction,
                                                        BiConsumer<V, List<V>> childrenConsumer,
                                                        ID ancestorId,
                                                        Predicate<E> filter) {
        Predicate<E> predicate = Optional.ofNullable(filter).orElse(v -> true);

        Map<Boolean, List<E>> descendantMap = entities.stream()
                .collect(Collectors.partitioningBy(item -> pidFunction.apply(item).equals(ancestorId)));
        List<E> children = descendantMap.get(true);
        return children.stream().map(child -> {
            V result = voFunction.apply(child);

            List<V> curChildren = buildTreeRecursion(descendantMap.get(false), pidFunction, idFunction, voFunction, childrenConsumer, idFunction.apply(child), filter);
            if (CollectionUtils.isNotEmpty(curChildren)) {
                childrenConsumer.accept(result, curChildren);
                return result;
            }

            if (predicate.test(child)) {
                return result;
            }

            return null;
        }).filter(Objects::nonNull).collect(Collectors.toList());
    }
}
