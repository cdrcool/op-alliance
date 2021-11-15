package com.op.recommend;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.List;

/**
 * 余弦相似度
 *
 * @author cdrcool
 */
public class CosineSimilarity {

    /**
     * 计算两个向量的余弦相似度
     *
     * @param vector1 向量1
     * @param vector2 向量2
     * @return 余弦相似度
     */
    public static BigDecimal calc(List<Double> vector1, List<Double> vector2) {
        int length = vector1.size();

        BigDecimal numerator = new BigDecimal("0");
        BigDecimal denominator1 = new BigDecimal("0");
        BigDecimal denominator2 = new BigDecimal("0");
        for (int i = 0; i < length; i++) {
            Double v1 = vector1.get(i);
            Double v2 = vector2.get(i);

            if (v1 != null && v2 != null) {
                BigDecimal m = BigDecimal.valueOf(v1);
                BigDecimal n = BigDecimal.valueOf(v2);

                numerator = numerator.add(m.multiply(n));

                denominator1 = denominator1.add(m.pow(2));
                denominator2 = denominator2.add(n.pow(2));
            }
        }

        denominator1 = sqrt(denominator1, 2);
        denominator2 = sqrt(denominator2, 2);
        return numerator.divide(denominator1.multiply(denominator2), 2, RoundingMode.HALF_UP);
    }

    /**
     * 计算平方根
     *
     * @param value 原值
     * @param scale 小数位
     * @return 平方根的值
     */
    public static BigDecimal sqrt(BigDecimal value, int scale) {
        BigDecimal x0 = new BigDecimal("0");
        BigDecimal x1 = BigDecimal.valueOf(Math.sqrt(value.doubleValue()));
        while (!x0.equals(x1)) {
            x0 = x1;
            x1 = value.divide(x0, scale, RoundingMode.HALF_UP);
            x1 = x1.add(x0);
            x1 = x1.divide(new BigDecimal("2"), scale, RoundingMode.HALF_UP);

        }
        return x1;
    }

    public static BigDecimal sqrt2(BigDecimal value, int scale) {
        BigDecimal num2 = BigDecimal.valueOf(2);
        int precision = 100;
        MathContext mc = new MathContext(precision, RoundingMode.HALF_UP);
        BigDecimal deviation = value;
        int cnt = 0;
        while (cnt < precision) {
            deviation = (deviation.add(value.divide(deviation, mc))).divide(num2, mc);
            cnt++;
        }
        deviation = deviation.setScale(scale, BigDecimal.ROUND_HALF_UP);
        return deviation;
    }
}
