package com.op.recommend.core.retrieval;

import com.op.recommend.core.Item;
import com.op.recommend.core.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.ujmp.core.Matrix;
import org.ujmp.core.calculation.Calculation;
import org.ujmp.core.objectmatrix.DenseObjectMatrix2D;
import org.ujmp.jdbc.ImportMatrixJDBC;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 基于相似用户的候选物品召回类
 *
 * @author cdrcool
 */
@Slf4j
@Component
public class UserSimilarityBasedRetrieval implements CandidatesRetrieval {
    private final DataSource dataSource;

    public UserSimilarityBasedRetrieval(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Item> decide(User user, Item item, List<Item> candidates) {
        DenseObjectMatrix2D matrix;
        try {
            matrix = ImportMatrixJDBC.fromDatabase(dataSource.getConnection(), null);
        } catch (Exception e) {
            log.error("加载用户-用户矩阵异常", e);
            return new ArrayList<>();
        }

        int index = 0;
        Matrix curMatrix = matrix.selectColumns(Calculation.Ret.NEW, index).sortrows(Calculation.Ret.NEW, 0, true);
        BigDecimal[] curArray = curMatrix.toBigDecimalArray()[0];

        BigDecimal molecular = new BigDecimal("0");
        BigDecimal denominator = new BigDecimal("0");
        for (int i = 0; i < curArray.length; i++) {
            if (i != index) {
                BigDecimal similarity = curArray[i];
                molecular = molecular.add(similarity.multiply(new BigDecimal("0")));
                denominator = denominator.add(similarity);
            }
        }


        return null;
    }
}
