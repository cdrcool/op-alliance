package com.op.web;

import com.op.framework.boot.sdk.client.api.impl.JdAreaApiImpl;
import com.op.framework.boot.sdk.client.response.AreaResponse;
import lombok.SneakyThrows;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 京东区域数据同步
 *
 * @author cdrcool
 */
@Service
public class JdAreaSyncService {

    private final JdAreaApiImpl jdAreaApi;
    private final JdbcTemplate jdbcTemplate;

    public JdAreaSyncService(JdAreaApiImpl jdAreaApi, JdbcTemplate jdbcTemplate) {
        this.jdAreaApi = jdAreaApi;
        this.jdbcTemplate = jdbcTemplate;
    }

    public void init() throws IOException {
        Path path = Paths.get("D:\\area.sql");
        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
            init(1, 1, 1, writer);
        }
    }

    private void init(Integer areaCode, int areaLevel, long parentId, BufferedWriter writer) {
        if (areaLevel < 5) {
            Map<String, Integer> map = jdAreaApi.getAreas(String.valueOf(areaCode), areaLevel).stream()
                    .collect(Collectors.toMap(AreaResponse::getAreaName, i -> Integer.parseInt(String.valueOf(i.getAreaCode()))));
            if (!CollectionUtils.isEmpty(map)) {
                map.forEach((k, v) -> {
                    long id = insert(k, v, parentId, areaLevel, writer);
                    init(v, areaLevel + 1, id, writer);
                });
            }
        }
    }

    @SneakyThrows
    private long insert(String areaName, Integer areaCode, long parentId, int areaLevel, BufferedWriter writer) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        String insertSql = "INSERT INTO cl_area (parent_id, `level`, area_name, jingdong_code) VALUES (?, ?, ?, ?)";
        writer.write(insertSql + "; areaName=" + areaName + ", areaCode=" + areaCode + "parentId" + parentId + ", areaLevel=" + areaLevel );
        writer.newLine();
        int num = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);

            ps.setLong(1, parentId);
            ps.setInt(2, areaLevel);
            ps.setString(3, areaName);
            ps.setLong(4, areaCode);

            return ps;
        }, keyHolder);

        if (num > 0) {
            return Objects.requireNonNull(keyHolder.getKey()).longValue();
        }

        throw new RuntimeException("插入失败，parentId：" + parentId +
                "，areaLevel：" + areaLevel + "，areaName：" + areaName + "，areaCode：" + areaCode);
    }

    @PostConstruct
    public void update() throws IOException {
        Path path = Paths.get("D:\\area.sql");
        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
            update(1, 1, 1, writer);
        }
    }

    private void update(Integer areaCode, Integer areaLevel, long parentId, BufferedWriter writer) {
        if (areaLevel < 5) {
            Map<Integer, String> map = jdAreaApi.getAreas(String.valueOf(areaCode), areaLevel).stream()
                    .collect(Collectors.toMap(i -> Integer.parseInt(String.valueOf(i.getAreaCode())), AreaResponse::getAreaName));

            String selectSql = "SELECT id, area_name, jingdong_code FROM cl_area WHERE is_deleted = 0 AND parent_id = ? AND level = ?";
            Map<Integer, Map<String, Object>> dataMap = jdbcTemplate.queryForList(selectSql, new Object[]{parentId, areaLevel}).stream()
                    .collect(Collectors.toMap(o -> Integer.parseInt(String.valueOf(o.get("jingdong_code"))), o -> o));

            if (!CollectionUtils.isEmpty(map)) {
                map.forEach((k, v) -> {
                    long id;
                    if (!dataMap.containsKey(k)) {
                        id = insert(v, k, parentId, areaLevel + 1, writer);
                    } else {
                        Map<String, Object> data = dataMap.get(k);
                        if (!v.equals(data.get("area_name"))) {
                            update(v, Long.parseLong(String.valueOf(data.get("id"))), writer);
                        }
                        id = Long.parseLong(String.valueOf(data.get("id")));
                    }
                    update(k, areaLevel + 1, id, writer);
                });
            }

            if (!CollectionUtils.isEmpty(dataMap)) {
                dataMap.forEach((k, v) -> {
                    if (!map.containsKey(k)) {
                        delete(Long.parseLong(String.valueOf(v.get("id"))), writer);
                    }
                });
            }
        }
    }

    @SneakyThrows
    private void update(String areaName, long id, BufferedWriter writer) {
        String updateSql = "UPDATE cl_area SET area_name = ? WHERE id = ?";
        writer.write(updateSql + "; areaName=" + areaName + ", id=" + id);
        writer.newLine();
        int num = jdbcTemplate.update(updateSql, areaName, id);

        if (num <= 0) {
            throw new RuntimeException("更新失败，areaName：" + areaName + "，id：" + id);
        }
    }

    @SneakyThrows
    private void delete(long id, BufferedWriter writer) {
        String deleteSql = "UPDATE cl_area SET is_deleted = 1 WHERE id = ?";
        writer.write(deleteSql + "; id=" + id);
        writer.newLine();
        int num = jdbcTemplate.update(deleteSql, id);

        if (num <= 0) {
            throw new RuntimeException("删除失败，id：" + id);
        }
    }
}
