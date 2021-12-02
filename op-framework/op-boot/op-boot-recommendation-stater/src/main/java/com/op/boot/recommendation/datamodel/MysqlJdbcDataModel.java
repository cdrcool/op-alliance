package com.op.boot.recommendation.datamodel;

import lombok.extern.slf4j.Slf4j;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.common.jdbc.AbstractJDBCComponent;
import org.apache.mahout.cf.taste.impl.model.jdbc.MySQLJDBCDataModel;
import org.apache.mahout.common.IOUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 扩展的 {@link MySQLJDBCDataModel}
 *
 * @author chengdr01
 */
@Slf4j
public class MysqlJdbcDataModel extends MySQLJDBCDataModel {
    public static final String DEFAULT_CATEGORY_ID_COLUMN = "category_id";
    public static final String DEFAULT_BAR_CODE_COLUMN = "bar_code";
    private final String getCategoryItemsSql;
    private final String getCategoryHeatItemsSql;
    private final String getBarCodeItemsSql;

    public MysqlJdbcDataModel() throws TasteException {
        this(DEFAULT_DATASOURCE_NAME);
    }

    public MysqlJdbcDataModel(String dataSourceName) throws TasteException {
        this(AbstractJDBCComponent.lookupDataSource(dataSourceName),
                DEFAULT_PREFERENCE_TABLE,
                DEFAULT_USER_ID_COLUMN,
                DEFAULT_CATEGORY_ID_COLUMN,
                DEFAULT_PREFERENCE_COLUMN,
                DEFAULT_PREFERENCE_TIME_COLUMN,
                DEFAULT_ITEM_ID_COLUMN,
                DEFAULT_BAR_CODE_COLUMN);
    }

    public MysqlJdbcDataModel(DataSource dataSource) {
        this(dataSource,
                DEFAULT_PREFERENCE_TABLE,
                DEFAULT_USER_ID_COLUMN,
                DEFAULT_CATEGORY_ID_COLUMN,
                DEFAULT_PREFERENCE_COLUMN,
                DEFAULT_PREFERENCE_TIME_COLUMN,
                DEFAULT_ITEM_ID_COLUMN,
                DEFAULT_BAR_CODE_COLUMN);
    }

    public MysqlJdbcDataModel(DataSource dataSource, String preferenceTable, String userIdColumn, String itemIdColumn, String preferenceColumn, String timestampColumn) {
        this(dataSource, preferenceTable, userIdColumn, itemIdColumn, preferenceColumn, timestampColumn, DEFAULT_CATEGORY_ID_COLUMN, DEFAULT_BAR_CODE_COLUMN);
    }

    public MysqlJdbcDataModel(DataSource dataSource, String preferenceTable, String userIdColumn, String itemIdColumn, String preferenceColumn, String timestampColumn, String categoryIdColumn, String barCodeColumn) {
        super(dataSource, preferenceTable, userIdColumn, itemIdColumn, preferenceColumn, timestampColumn);
        this.getCategoryItemsSql = "SELECT " + categoryIdColumn + ", " + itemIdColumn + " FROM " + preferenceTable +
                " GROUP BY " + categoryIdColumn + ", " + itemIdColumn;
        this.getCategoryHeatItemsSql = "SELECT " + categoryIdColumn + ", GROUP_CONCAT(" + itemIdColumn + ") AS item_ids FROM (" +
                " SELECT " + categoryIdColumn + ", " + itemIdColumn + ", SUM(" + preferenceColumn + ") AS " + preferenceColumn +
                " FROM " + preferenceTable +
                " GROUP BY " + categoryIdColumn + ", " + itemIdColumn +
                " ORDER BY SUM(" + preferenceColumn + ") DESC" +
                " LIMIT ?" +
                " ) t WHERE " + preferenceColumn + " > ?" +
                " GROUP BY " + categoryIdColumn;
        this.getBarCodeItemsSql = "SELECT " + barCodeColumn + ", GROUP_CONCAT(" + itemIdColumn + ") AS item_ids FROM (" +
                " SELECT " + barCodeColumn + ", " + itemIdColumn + " FROM " + preferenceTable + "" +
                " WHERE " + barCodeColumn + " IS NOT NULL AND " + barCodeColumn + " != '' GROUP BY " + barCodeColumn + ", " + itemIdColumn +
                ") t" +
                " GROUP BY " + barCodeColumn + " HAVING COUNT(*) > 1";
    }

    /**
     * 获取各分类下的物品 ids
     *
     * @return 各分类下的物品 ids
     */
    public Map<Long, List<Long>> getCategoryItemsMap() {
        log.debug("Retrieving items of all categories");

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = super.getDataSource().getConnection();
            stmt = conn.prepareStatement(getCategoryItemsSql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            stmt.setFetchDirection(ResultSet.FETCH_FORWARD);
            stmt.setFetchSize(getFetchSize());

            log.info("Executing SQL query: {}", getCategoryItemsSql);
            rs = stmt.executeQuery();

            Map<Long, List<Long>> result = new HashMap<>(1024);
            while (rs.next()) {
                long categoryId = getLongColumn(rs, 1);
                List<Long> list = result.getOrDefault(categoryId, new ArrayList<>());
                list.add(getLongColumn(rs, 2));
                result.put(categoryId, list);
            }

            return result;
        } catch (SQLException exception) {
            log.error("Exception while retrieving items of all categories", exception);
            return new HashMap<>(0);
        } finally {
            IOUtils.quietClose(rs, stmt, conn);
        }
    }

    /**
     * 获取各分类下热门的物品 ids
     *
     * @param limit     最大返回个数
     * @param threshold 阈值
     * @return 热门的物品 ids
     */
    public Map<Long, List<String>> getCategoryHeatItems(int limit, int threshold) {
        log.debug("Retrieving heat items of each categories");

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = super.getDataSource().getConnection();
            stmt = conn.prepareStatement(getCategoryHeatItemsSql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            stmt.setFetchDirection(ResultSet.FETCH_FORWARD);
            stmt.setFetchSize(getFetchSize());

            stmt.setInt(1, limit);
            stmt.setInt(2, threshold);

            log.info("Executing SQL query: {}", getCategoryHeatItemsSql);
            rs = stmt.executeQuery();

            Map<Long, List<String>> result = new HashMap<>(1024);
            while (rs.next()) {
                long categoryId = getLongColumn(rs, 1);
                List<String> list = result.getOrDefault(categoryId, new ArrayList<>());
                list.add(rs.getString(2));
                result.put(categoryId, list);
            }

            return result;
        } catch (SQLException exception) {
            log.error("Exception while retrieving heat items of each categories", exception);
            return new HashMap<>(0);
        } finally {
            IOUtils.quietClose(rs, stmt, conn);
        }
    }

    /**
     * 获取各条形码相同的物品 ids
     *
     * @return 各条形码相同的物品 ids
     */
    public Map<String, List<Long>> getBarCodeItemsMap() {
        log.debug("Retrieving items of all bar codes");

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = super.getDataSource().getConnection();
            stmt = conn.prepareStatement(getBarCodeItemsSql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            stmt.setFetchDirection(ResultSet.FETCH_FORWARD);
            stmt.setFetchSize(getFetchSize());

            log.info("Executing SQL query: {}", getBarCodeItemsSql);
            rs = stmt.executeQuery();

            Map<String, List<Long>> result = new HashMap<>(1024);
            while (rs.next()) {
                String barCode = rs.getString(1);
                List<Long> itemIds = Arrays.stream(rs.getString(2).split(",")).map(Long::valueOf).collect(Collectors.toList());
                result.put(barCode, itemIds);
            }

            return result;
        } catch (SQLException exception) {
            log.error("Exception while retrieving items of all bar codes", exception);
            return new HashMap<>(0);
        } finally {
            IOUtils.quietClose(rs, stmt, conn);
        }
    }
}
