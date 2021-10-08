package com.op.admin.utils;

import org.apache.commons.collections4.CollectionUtils;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
     * @param entities         实体列表
     * @param pidFunction      entity -> pid 映射
     * @param idFunction       entity -> id 映射
     * @param voFunction       entity -> vo 映射
     * @param childrenConsumer (vo, children) 消费者
     * @param ancestorId       祖先 id
     * @param predicate        过滤条件
     * @param comparator       排序
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
                                                        Predicate<E> predicate,
                                                        Comparator<E> comparator) {
        Predicate<E> filter = Optional.ofNullable(predicate).orElse(v -> true);

        Map<Boolean, List<E>> descendantMap = entities.stream()
                .collect(Collectors.partitioningBy(item -> pidFunction.apply(item).equals(ancestorId)));
        List<E> children = descendantMap.get(true);
        Stream<E> stream = children.stream();
        if (comparator != null) {
            stream = stream.sorted(comparator);
        }
        return stream
                .map(child -> {
                    V result = voFunction.apply(child);

                    List<V> curChildren = buildTreeRecursion(
                            descendantMap.get(false),
                            pidFunction,
                            idFunction,
                            voFunction,
                            childrenConsumer,
                            idFunction.apply(child),
                            predicate,
                            comparator);
                    if (CollectionUtils.isNotEmpty(curChildren)) {
                        childrenConsumer.accept(result, curChildren);
                        return result;
                    }

                    if (filter.test(child)) {
                        return result;
                    }

                    return null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    /**
     * 获取所有后代节点的 ids
     *
     * @param entities    entities
     * @param pidFunction entity -> pid 映射
     * @param idFunction  entity -> id 映射
     * @param ancestorId  祖先 id
     * @param <E>         entity 泛型
     * @param <ID>        id 泛型
     * @return 后代节点的 ids
     */
    public static <E, ID> List<ID> getDescendantIds(List<E> entities,
                                                    Function<E, ID> pidFunction,
                                                    Function<E, ID> idFunction,
                                                    ID ancestorId) {
        List<ID> descendantIds = new ArrayList<>();

        Map<Boolean, List<E>> descendantMap = entities.stream()
                .collect(Collectors.partitioningBy(item -> pidFunction.apply(item).equals(ancestorId)));
        List<E> children = descendantMap.get(true);
        children.forEach(child -> {
            descendantIds.addAll(getDescendantIds(descendantMap.get(false), pidFunction, idFunction, idFunction.apply(child)));

            if (pidFunction.apply(child).equals(ancestorId)) {
                descendantIds.add(idFunction.apply(child));
            }
        });

        return descendantIds;
    }
}
