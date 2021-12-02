package com.op.boot.recommendation.store;

import com.op.boot.recommendation.entity.ItemSimilarities;
import com.op.boot.recommendation.entity.UserRecommends;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 推荐结果 Repository jdbc 实现类
 *
 * @author chengdr01
 */
@Slf4j
@Component
public class JdbcRecommendsRepository implements RecommendsRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcRecommendsRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void saveUserRecommends(UserRecommends recommends) {
        // 删除之前的推荐记录
        String deleteSql = "DELETE FROM user_recommends WHERE user_id = ? AND category_id = ? AND recommender_type = ?";
        jdbcTemplate.update(deleteSql, recommends.getUserId(), recommends.getCategoryId(), recommends.getRecommenderType());

        // 插入新的推荐记录
        String insertSql = "INSERT INTO user_recommends (user_id, category_id, item_ids, `timestamp`, recommender_type) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(insertSql,
                recommends.getUserId(),
                recommends.getCategoryId(),
                recommends.getItemIds(),
                new Timestamp(System.currentTimeMillis()),
                recommends.getRecommenderType());
    }

    @Override
    public Map<Integer, List<Long>> getUserRecommends(long userId, long categoryId) {
        String sql = "SELECT item_ids, recommender_type FROM user_recommends WHERE user_id = ? AND category_id = ? LIMIT 1";
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, userId, categoryId);
        return list.stream().collect(Collectors.toMap(o -> (int) o.get("recommender_type"),
                o -> Arrays.stream(((String) o.get("recommender_type")).split(",")).map(Long::valueOf).collect(Collectors.toList())));
    }

    @Override
    public List<Long> getUserRecommends(long userId, long categoryId, int recommenderType) {
        String sql = "SELECT item_ids FROM user_recommends WHERE user_id = ? AND category_id = ? AND recommender_type = ? LIMIT 1";
        List<String> itemIds = jdbcTemplate.queryForList(sql, String.class, userId, categoryId, recommenderType);
        return CollectionUtils.isEmpty(itemIds) ? new ArrayList<>() :
                Arrays.stream(itemIds.get(0).split(",")).map(Long::valueOf).collect(Collectors.toList());
    }

    @Override
    public void saveItemSimilarities(ItemSimilarities similarities) {
        // 删除之前的推荐记录
        String deleteSql = "DELETE FROM item_similarities WHERE item_id = ? AND category_id = ? AND recommender_type = ?";
        jdbcTemplate.update(deleteSql, similarities.getItemId(), similarities.getCategoryId(), similarities.getRecommenderType());

        // 插入新的推荐记录
        String insertSql = "INSERT INTO item_similarities (item_id, category_id, item_ids, `timestamp`, recommender_type) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(insertSql,
                similarities.getItemId(),
                similarities.getCategoryId(),
                similarities.getItemIds(),
                new Timestamp(System.currentTimeMillis()),
                similarities.getRecommenderType());
    }

    @Override
    public Map<Integer, List<Long>> getItemSimilarities(long itemId, long categoryId) {
        String sql = "SELECT item_ids, recommender_type FROM item_similarities WHERE item_id = ? AND category_id = ? LIMIT 1";
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, itemId, categoryId);
        return list.stream().collect(Collectors.toMap(o -> (int) o.get("recommender_type"),
                o -> Arrays.stream(((String) o.get("recommender_type")).split(",")).map(Long::valueOf).collect(Collectors.toList())));
    }

    @Override
    public List<Long> getItemSimilarities(long itemId, long categoryId, int recommenderType) {
        String sql = "SELECT item_ids FROM item_similarities WHERE item_id = ? AND category_id = ? AND recommender_type = ? LIMIT 1";
        List<String> itemIds = jdbcTemplate.queryForList(sql, String.class, itemId, categoryId, recommenderType);
        return CollectionUtils.isEmpty(itemIds) ? new ArrayList<>() :
                Arrays.stream(itemIds.get(0).split(",")).map(Long::valueOf).collect(Collectors.toList());
    }
}
