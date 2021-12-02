package com.op.boot.recommendation.datacollect;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 数据采集类
 *
 * @author chengdr01
 */
@Component
public class DataCollect {
    private final JdbcTemplate jdbcTemplate;
    private final DataConverter dataConverter;

    public DataCollect(JdbcTemplate jdbcTemplate, DataConverter dataConverter) {
        this.jdbcTemplate = jdbcTemplate;
        this.dataConverter = dataConverter;
    }

    @Transactional
    public void collect() {
        jdbcTemplate.execute("TRUNCATE TABLE `taste_preferences_origin`");
        String sql = "INSERT INTO `taste_preferences_origin` (`user_id`, `item_id`, `preference`, `timestamp`, `category_id`, `bar_code`)\n" +
            "SELECT *\n" +
            "FROM (\n" +
            "         SELECT user_id, item_id, max(preference) AS preference, `timestamp`, category_id, bar_code\n" +
            "         FROM (\n" +
            "                  SELECT t.created_by      AS user_id,\n" +
            "                         t.material_id     AS item_id,\n" +
            "                         3                 AS preference,\n" +
            "                         t.create_on       AS `timestamp`,\n" +
            "                         m.category_id     AS category_id,\n" +
            "                         m.material_number AS bar_code\n" +
            "                  FROM (\n" +
            "                           SELECT created_by, material_id, max(created_on) AS create_on\n" +
            "                           FROM cl_shopping_car\n" +
            "                           WHERE is_deleted = 0\n" +
            "                           GROUP BY created_by, material_id\n" +
            "                       ) t\n" +
            "                           INNER JOIN cl_material m ON m.is_deleted = 0 AND m.material_id = t.material_id\n" +
            "                  GROUP BY t.created_by, t.material_id\n" +
            "                  UNION ALL\n" +
            "                  SELECT t.created_by      AS user_id,\n" +
            "                         t.material_id     AS item_id,\n" +
            "                         5                 AS preference,\n" +
            "                         t.create_on       AS `timestamp`,\n" +
            "                         m.category_id     AS category_id,\n" +
            "                         m.material_number AS bar_code\n" +
            "                  FROM (\n" +
            "                           SELECT created_by, material_id, max(created_on) AS create_on\n" +
            "                           FROM cl_purchase_apply_material\n" +
            "                           WHERE is_deleted = 0\n" +
            "                           GROUP BY created_by, material_id\n" +
            "                       ) t\n" +
            "                           INNER JOIN cl_material m ON m.is_deleted = 0 AND m.material_id = t.material_id\n" +
            "                  GROUP BY t.created_by, t.material_id\n" +
            "              ) t\n" +
            "         WHERE user_id IS NOT NULL\n" +
            "           AND item_id IS NOT NULL\n" +
            "         GROUP BY user_id, item_id\n" +
            "     ) t";
        jdbcTemplate.execute(sql);

        dataConverter.convert();
    }
}
