package com.op.recommend;

import lombok.Data;
import org.ujmp.core.Matrix;
import org.ujmp.core.SparseMatrix;
import org.ujmp.core.calculation.Calculation;
import org.ujmp.core.objectmatrix.DenseObjectMatrix2D;
import org.ujmp.jdbc.ExportMatrixJDBC;
import org.ujmp.jdbc.ImportMatrixJDBC;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author cdrcool
 */
public class UserSimilarityDemo {
    private static final Map<String, Integer> USER_ID_INDEX_MAP = new ConcurrentHashMap<>();
    private static final Map<String, Integer> MATERIAL_ID_INDEX_MAP = new ConcurrentHashMap<>();

    /**
     * 计算并存储用户相似度
     *
     * @param list 用户-物品评分对象列表
     */
    public static void calcAndSaveUserSimilarity(List<UserMaterialScore> list) throws SQLException, ClassNotFoundException {
        List<String> userIds = list.stream().map(UserMaterialScore::getUserId).distinct().collect(Collectors.toList());
        List<String> materialIds = list.stream().map(UserMaterialScore::getMaterialId).distinct().collect(Collectors.toList());

        IntStream.range(0, userIds.size()).forEach(index -> USER_ID_INDEX_MAP.put(userIds.get(index), index));
        IntStream.range(0, materialIds.size()).forEach(index -> MATERIAL_ID_INDEX_MAP.put(materialIds.get(index), index));

        Matrix sparse = SparseMatrix.Factory.zeros(userIds.size(), materialIds.size());
        list.forEach(item -> sparse.setAsInt(item.getScore(), USER_ID_INDEX_MAP.get(item.getUserId()),
                MATERIAL_ID_INDEX_MAP.get(item.getMaterialId())));


        Matrix similarity = sparse.cosineSimilarity(Calculation.Ret.NEW, true);
        ExportMatrixJDBC.toDatabase(similarity, null, null, null, null);
    }

    /**
     * 查找最相似的 N 个用户
     *
     * @param userId 当前用户ID
     * @param limit  最大返回数量
     * @return 最相似的 N 个用户的 IDS
     */
    public List<String> findMostSimilarUsers(String userId, int limit) throws Exception {
        DenseObjectMatrix2D matrix = ImportMatrixJDBC.fromDatabase(null, null, null, null);
        double[][] array = matrix.toDoubleArray();

        int index = USER_ID_INDEX_MAP.get(userId);

        List<Double> collect = Arrays.stream(array[index])
                .boxed()
                .sorted(Comparator.reverseOrder())
                .limit(limit)
                .collect(Collectors.toList());
        // TODO 获取索引列表 -> 获取用户ID列表
        return null;
    }

    /**
     * 用户-物品评分对象
     */
    @Data
    public static class UserMaterialScore {
        /**
         * 用户ID
         */
        private String userId;

        /**
         * 物品ID
         */
        private String materialId;

        /**
         * 用户评分
         */
        private Integer score;
    }

    /**
     * 用户相似性结果
     */
    @Data
    public static class UserSimilarityResult {
        /**
         * 用户ID 1
         */
        private String userId1;

        /**
         * 用户ID 2
         */
        private String userId2;

        /**
         * 相似度
         */
        private BigDecimal value;
    }
}
