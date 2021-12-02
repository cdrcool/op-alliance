package com.op.boot.recommendation.datacollect;

import com.op.boot.recommendation.entity.TastePreference;
import com.op.boot.recommendation.entity.TastePreferenceOrigin;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 数据转换类
 *
 * @author chengdr01
 */
@Slf4j
@Component
public class DataConverter {
    private final JdbcTemplate jdbcTemplate;

    public DataConverter(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void convert() {
        Map<String, Long> userIdMap = new HashMap<>(1024);

        String clearPreferencesSql = "TRUNCATE TABLE `taste_preferences`";
        jdbcTemplate.update(clearPreferencesSql);
        log.info("清除用户口味偏好表成功");

        String countSql = "SELECT COUNT(*) FROM taste_preferences_origin";
        String pageSql = "SELECT * FROM taste_preferences_origin ORDER BY user_id DESC, item_id DESC LIMIT ?, ?";

        long total = Optional.ofNullable(jdbcTemplate.queryForObject(countSql, Long.class)).orElse(0L);
        int pageSize = 1000;
        long pageNum = total % pageSize == 0 ? total / pageSize : total / pageSize + 1;

        log.info("总共有【{}】条数据需要同步，每次同步【{}】条", total, pageSize);

        for (int i = 0; i < pageNum; i++) {
            log.info("当前同步至第【{}】页", i);

            long offset = (long) pageSize * i;
            List<TastePreferenceOrigin> originList = jdbcTemplate.query(pageSql, new BeanPropertyRowMapper<>(TastePreferenceOrigin.class), offset, pageSize);
            List<TastePreference> targetList = originList.stream()
                    .map(origin -> {
                        Long userId = userIdMap.computeIfAbsent(origin.getUserId(), k -> UUID.fromString(k).getMostSignificantBits() & Long.MAX_VALUE);
                        TastePreference target = new TastePreference();
                        target.setUserId(userId);
                        target.setItemId(origin.getItemId());
                        target.setPreference(origin.getPreference());
                        target.setTimestamp(origin.getTimestamp());
                        target.setCategoryId(origin.getCategoryId());
                        target.setBarCode(origin.getBarCode());
                        return target;
                    }).collect(Collectors.toList());

            String insertSql = "INSERT INTO `taste_preferences` (`user_id`, `item_id`, `preference`, `timestamp`, `category_id`, `bar_code`) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";
            jdbcTemplate.batchUpdate(insertSql, targetList.stream()
                    .map(target -> new Object[]{
                            target.getUserId(),
                            target.getItemId(),
                            target.getPreference(),
                            target.getTimestamp(),
                            target.getCategoryId(),
                            target.getBarCode()
                    }).collect(Collectors.toList()));
        }
        log.info("同步用户口味偏好表完成");

        String clearMappingSql = "TRUNCATE TABLE `user_id_mapping`";
        jdbcTemplate.update(clearMappingSql);
        log.info("清除原有的用户ID映射成功");

        String insertMappingSql = "INSERT INTO `user_id_mapping` (`origin_id`, `new_id`) VALUES (?, ?)";
        jdbcTemplate.batchUpdate(insertMappingSql, userIdMap.entrySet().stream()
                .map(entry -> new Object[]{entry.getKey(), entry.getValue()}).collect(Collectors.toList()));
        log.info("插入新的用户ID映射成功");
    }
}
