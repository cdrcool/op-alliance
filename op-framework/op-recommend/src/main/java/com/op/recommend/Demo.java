package com.op.recommend;

import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Demo
 *
 * @author cdrcool
 */
@Slf4j
public class Demo {

    public static void main(String[] args) {
        List<Double> martrix = Arrays.asList(5d, 1d, 2d, 4d);
        List<Double> titanic = Arrays.asList(1d, 5d, null, 3d);
        List<Double> dieHard = Arrays.asList(null, 2d, 3d, 5d);
        List<Double> forrestGump = Arrays.asList(2d, 5d, 5d, 3d);
        List<Double> wallE = Arrays.asList(2d, 5d, 4d, null);
        System.out.println("result1: " + CosineSimilarity.calc(martrix, titanic));
        System.out.println("result2: " + CosineSimilarity.calc(martrix, dieHard));
        System.out.println("result3: " + CosineSimilarity.calc(martrix, forrestGump));
        System.out.println("result4: " + CosineSimilarity.calc(martrix, wallE));
        System.out.println("result5: " + CosineSimilarity.calc(titanic, dieHard));
        System.out.println("result6: " + CosineSimilarity.calc(titanic, forrestGump));
        System.out.println("result7: " + CosineSimilarity.calc(titanic, wallE));
        System.out.println("result8: " + CosineSimilarity.calc(dieHard, forrestGump));
        System.out.println("result9: " + CosineSimilarity.calc(dieHard, wallE));
        System.out.println("result10: " + CosineSimilarity.calc(forrestGump, wallE));

        List<Double> user1 = Arrays.asList(5d, 1d, null, 2d, 2d);
        List<Double> user2 = Arrays.asList(1d, 5d, 2d, 5d, 5d);
        List<Double> user3 = Arrays.asList(2d, null, 3d, 5d, 4d);
        List<Double> user4 = Arrays.asList(4d, 3d, 5d, 3d, null);
        System.out.println("result11: " + CosineSimilarity.calc(user1, user2));
        System.out.println("result12: " + CosineSimilarity.calc(user1, user3));
        System.out.println("result13: " + CosineSimilarity.calc(user1, user4));
        System.out.println("result14: " + CosineSimilarity.calc(user2, user3));
        System.out.println("result15: " + CosineSimilarity.calc(user2, user4));
        System.out.println("result16: " + CosineSimilarity.calc(user3, user4));

        xx("D");
    }

    public static void xx(String userId) {
        // 1. 查询所有物品
        List<String> goodsIds = Arrays.asList("martrix", "titanic", "dieHard", "forrestGump", "wallE");

        // 2. 查询所有物品-用户评分
        List<SlopOne.GoodsUserScore> goodsUserScores = queryGoodsUserScores();
        SlopOne.calcGoodsDeviations(goodsUserScores);

        // 3. 计算所有物品-评分偏差
        List<SlopOne.GoodsScoreDeviation> deviations = SlopOne.calcGoodsDeviations(goodsUserScores);

        // 4. 找出用户评分/未评分的商品
        List<SlopOne.GoodsUserScore> userScores = goodsUserScores.stream()
                .filter(goodsUserScore -> goodsUserScore.getUserId().equals(userId))
                .collect(Collectors.toList());
        List<String> scoreGoodsIds = userScores.stream().map(SlopOne.GoodsUserScore::getGoodsId).collect(Collectors.toList());
        List<String> unScoreGoodsIds = goodsIds.stream().filter(goodsId -> !scoreGoodsIds.contains(goodsId)).collect(Collectors.toList());

        List<BigDecimal> xx = unScoreGoodsIds.stream().map(unScoreGoodsId -> {
            log.info("unScoreGoodsId: {}", unScoreGoodsId);
            BigDecimal aa = new BigDecimal("0");
            BigDecimal vv = new BigDecimal("0");

            for (SlopOne.GoodsUserScore userScore : userScores) {
                SlopOne.GoodsScoreDeviation deviation = deviations.stream()
                        .filter(item -> item.getGoodsId1().equals(userScore.getGoodsId()) && item.getGoodsId2().equals(unScoreGoodsId))
                        .findAny().get();
                aa = aa.add(BigDecimal.valueOf(deviation.getCount()).multiply(userScore.getScore().subtract(deviation.getValue())));
                vv = vv.add(BigDecimal.valueOf(deviation.getCount()));
            }

            return aa.divide(vv, 2, RoundingMode.HALF_UP);
        }).collect(Collectors.toList());

    }

    private static List<SlopOne.GoodsUserScore> queryGoodsUserScores() {
        List<SlopOne.GoodsUserScore> goodsUserScores = new ArrayList<>();

        SlopOne.GoodsUserScore martrixUserScoreA = new SlopOne.GoodsUserScore();
        martrixUserScoreA.setGoodsId("martrix");
        martrixUserScoreA.setUserId("A");
        martrixUserScoreA.setScore(BigDecimal.valueOf(5));
        goodsUserScores.add(martrixUserScoreA);

        SlopOne.GoodsUserScore martrixUserScoreB = new SlopOne.GoodsUserScore();
        martrixUserScoreB.setGoodsId("martrix");
        martrixUserScoreB.setUserId("B");
        martrixUserScoreB.setScore(BigDecimal.valueOf(1));
        goodsUserScores.add(martrixUserScoreB);

        SlopOne.GoodsUserScore martrixUserScoreC = new SlopOne.GoodsUserScore();
        martrixUserScoreC.setGoodsId("martrix");
        martrixUserScoreC.setUserId("C");
        martrixUserScoreC.setScore(BigDecimal.valueOf(2));
        goodsUserScores.add(martrixUserScoreC);

        SlopOne.GoodsUserScore martrixUserScoreD = new SlopOne.GoodsUserScore();
        martrixUserScoreD.setGoodsId("martrix");
        martrixUserScoreD.setUserId("D");
        martrixUserScoreD.setScore(BigDecimal.valueOf(4));
        goodsUserScores.add(martrixUserScoreD);


        SlopOne.GoodsUserScore titanicUserScoreA = new SlopOne.GoodsUserScore();
        titanicUserScoreA.setGoodsId("titanic");
        titanicUserScoreA.setUserId("A");
        titanicUserScoreA.setScore(BigDecimal.valueOf(1));
        goodsUserScores.add(titanicUserScoreA);

        SlopOne.GoodsUserScore titanicUserScoreB = new SlopOne.GoodsUserScore();
        titanicUserScoreB.setGoodsId("titanic");
        titanicUserScoreB.setUserId("B");
        titanicUserScoreB.setScore(BigDecimal.valueOf(5));
        goodsUserScores.add(titanicUserScoreB);

        SlopOne.GoodsUserScore titanicUserScoreD = new SlopOne.GoodsUserScore();
        titanicUserScoreD.setGoodsId("titanic");
        titanicUserScoreD.setUserId("D");
        titanicUserScoreD.setScore(BigDecimal.valueOf(3));
        goodsUserScores.add(titanicUserScoreD);


        SlopOne.GoodsUserScore dieHardUserScoreB = new SlopOne.GoodsUserScore();
        dieHardUserScoreB.setGoodsId("dieHard");
        dieHardUserScoreB.setUserId("B");
        dieHardUserScoreB.setScore(BigDecimal.valueOf(2));
        goodsUserScores.add(dieHardUserScoreB);

        SlopOne.GoodsUserScore dieHardUserScoreC = new SlopOne.GoodsUserScore();
        dieHardUserScoreC.setGoodsId("dieHard");
        dieHardUserScoreC.setUserId("C");
        dieHardUserScoreC.setScore(BigDecimal.valueOf(3));
        goodsUserScores.add(dieHardUserScoreC);

        SlopOne.GoodsUserScore dieHardUserScoreD = new SlopOne.GoodsUserScore();
        dieHardUserScoreD.setGoodsId("dieHard");
        dieHardUserScoreD.setUserId("D");
        dieHardUserScoreD.setScore(BigDecimal.valueOf(5));
        goodsUserScores.add(dieHardUserScoreD);


        SlopOne.GoodsUserScore forrestGumpUserScoreA = new SlopOne.GoodsUserScore();
        forrestGumpUserScoreA.setGoodsId("forrestGump");
        forrestGumpUserScoreA.setUserId("A");
        forrestGumpUserScoreA.setScore(BigDecimal.valueOf(2));
        goodsUserScores.add(forrestGumpUserScoreA);

        SlopOne.GoodsUserScore forrestGumpUserScoreB = new SlopOne.GoodsUserScore();
        forrestGumpUserScoreB.setGoodsId("forrestGump");
        forrestGumpUserScoreB.setUserId("B");
        forrestGumpUserScoreB.setScore(BigDecimal.valueOf(5));
        goodsUserScores.add(forrestGumpUserScoreB);

        SlopOne.GoodsUserScore forrestGumpUserScoreC = new SlopOne.GoodsUserScore();
        forrestGumpUserScoreC.setGoodsId("forrestGump");
        forrestGumpUserScoreC.setUserId("C");
        forrestGumpUserScoreC.setScore(BigDecimal.valueOf(5));
        goodsUserScores.add(forrestGumpUserScoreC);

        SlopOne.GoodsUserScore forrestGumpUserScoreD = new SlopOne.GoodsUserScore();
        forrestGumpUserScoreD.setGoodsId("forrestGump");
        forrestGumpUserScoreD.setUserId("D");
        forrestGumpUserScoreD.setScore(BigDecimal.valueOf(3));
        goodsUserScores.add(forrestGumpUserScoreD);


        SlopOne.GoodsUserScore wallEUserScoreA = new SlopOne.GoodsUserScore();
        wallEUserScoreA.setGoodsId("wallE");
        wallEUserScoreA.setUserId("A");
        wallEUserScoreA.setScore(BigDecimal.valueOf(2));
        goodsUserScores.add(wallEUserScoreA);

        SlopOne.GoodsUserScore wallEUserScoreB = new SlopOne.GoodsUserScore();
        wallEUserScoreB.setGoodsId("wallE");
        wallEUserScoreB.setUserId("B");
        wallEUserScoreB.setScore(BigDecimal.valueOf(5));
        goodsUserScores.add(wallEUserScoreB);

        SlopOne.GoodsUserScore wallEUserScoreC = new SlopOne.GoodsUserScore();
        wallEUserScoreC.setGoodsId("wallE");
        wallEUserScoreC.setUserId("C");
        wallEUserScoreC.setScore(BigDecimal.valueOf(4));
        goodsUserScores.add(wallEUserScoreC);

        return goodsUserScores;
    }
}
