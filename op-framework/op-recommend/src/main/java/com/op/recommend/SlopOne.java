package com.op.recommend;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Slop One
 *
 * @author cdrcool
 */
@Slf4j
public class SlopOne {

    /**
     * 计算物品间的评分偏差
     *
     * @param scores 物品-用户评分列表
     * @return 物品间的评分偏差列表
     */
    public static List<GoodsScoreDeviation> calcGoodsDeviations(List<GoodsUserScore> scores) {
        List<GoodsScoreDeviation> deviationList = new ArrayList<>();

        Map<String, List<GoodsUserScore>> goodsUserScoreMap = scores.stream()
                .collect(Collectors.groupingBy(GoodsUserScore::getGoodsId));

        List<String> goodsIds = new ArrayList<>(goodsUserScoreMap.keySet());
        for (int i = 0; i < goodsIds.size() - 1; i++) {
            for (int j = i + 1; j < goodsIds.size(); j ++) {
                String goodsId1 = goodsIds.get(i);
                String goodsId2 = goodsIds.get(j);

                List<GoodsUserScore> userScores1 = goodsUserScoreMap.get(goodsId1);
                List<GoodsUserScore> userScores2 = goodsUserScoreMap.get(goodsId2);

                Map<String, BigDecimal> userScoresMap1 = userScores1.stream()
                        .collect(Collectors.toMap(GoodsUserScore::getUserId, GoodsUserScore::getScore));
                Map<String, BigDecimal> userScoresMap2 = userScores2.stream()
                        .collect(Collectors.toMap(GoodsUserScore::getUserId, GoodsUserScore::getScore));

                Set<String> userIds1 = userScoresMap1.keySet();
                Set<String> userIds2 = userScoresMap2.keySet();
                Set<String> sameUserIds = userIds1.stream().filter(userIds2::contains).collect(Collectors.toSet());

                Optional<BigDecimal> optional = sameUserIds.stream()
                        .map(userId -> userScoresMap1.get(userId).subtract(userScoresMap2.get(userId)))
                        .reduce(BigDecimal::add);

                optional.ifPresent(sum -> {
                    GoodsScoreDeviation deviation1 = new GoodsScoreDeviation();
                    deviation1.setGoodsId1(goodsId1);
                    deviation1.setGoodsId2(goodsId2);
                    deviation1.setValue(sum.divide(BigDecimal.valueOf(sameUserIds.size()), 2, RoundingMode.HALF_UP));
                    deviation1.setCount(sameUserIds.size());
                    deviationList.add(deviation1);
                    log.info("goodsId1: {}, goodsId2: {}, value:{}, count:{}", goodsId1, goodsId2, deviation1.getValue(), sameUserIds.size());

                    GoodsScoreDeviation deviation2 = new GoodsScoreDeviation();
                    deviation2.setGoodsId1(goodsId2);
                    deviation2.setGoodsId2(goodsId1);
                    deviation2.setValue(deviation1.getValue().multiply(BigDecimal.valueOf(-1)));
                    deviation2.setCount(sameUserIds.size());
                    deviationList.add(deviation2);
                    log.info("goodsId2: {}, goodsId1: {}, value:{}, count:{}", goodsId2, goodsId1, deviation2.getValue(), sameUserIds.size());
                });
            }
        }


        return deviationList;
    }

    /**
     * 物品-用户评分对象
     */
    @Data
    public static class GoodsUserScore {
        /**
         * 物品ID
         */
        private String goodsId;

        /**
         * 用户ID
         */
        private String userId;

        /**
         * 用户评分
         */
        private BigDecimal score;
    }

    /**
     * 物品-评分偏差对象
     */
    @Data
    public static class GoodsScoreDeviation {
        /**
         * 物品ID 1
         */
        private String goodsId1;

        /**
         * 物品ID 2
         */
        private String goodsId2;

        /**
         * 评分偏差
         */
        private BigDecimal value;

        /**
         * 评分人数
         */
        private Integer count;
    }
}
