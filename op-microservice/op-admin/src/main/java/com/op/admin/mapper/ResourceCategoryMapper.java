package com.op.admin.mapper;

import static com.op.admin.mapper.ResourceCategoryDynamicSqlSupport.*;
import static org.mybatis.dynamic.sql.SqlBuilder.*;

import com.op.admin.entity.ResourceCategory;
import java.util.List;
import java.util.Optional;
import javax.annotation.Generated;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.dynamic.sql.BasicColumn;
import org.mybatis.dynamic.sql.delete.DeleteDSLCompleter;
import org.mybatis.dynamic.sql.delete.render.DeleteStatementProvider;
import org.mybatis.dynamic.sql.insert.render.InsertStatementProvider;
import org.mybatis.dynamic.sql.select.CountDSLCompleter;
import org.mybatis.dynamic.sql.select.SelectDSLCompleter;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.mybatis.dynamic.sql.update.UpdateDSL;
import org.mybatis.dynamic.sql.update.UpdateDSLCompleter;
import org.mybatis.dynamic.sql.update.UpdateModel;
import org.mybatis.dynamic.sql.update.render.UpdateStatementProvider;
import org.mybatis.dynamic.sql.util.SqlProviderAdapter;
import org.mybatis.dynamic.sql.util.mybatis3.MyBatis3Utils;

/**
 * @author Mybatis Generator
 * @date 2021/07/16 11:06
 */
@Mapper
public interface ResourceCategoryMapper {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    BasicColumn[] selectList = BasicColumn.columnList(id, categoryName, categoryIcon, serverName, categoryNo, version, deleted, creatorId, createTime, lastModifierId, lastModifyTime, tenantId);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    long count(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @DeleteProvider(type=SqlProviderAdapter.class, method="delete")
    int delete(DeleteStatementProvider deleteStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @InsertProvider(type=SqlProviderAdapter.class, method="insert")
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="record.id", before=false, resultType=Integer.class)
    int insert(InsertStatementProvider<ResourceCategory> insertStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("ResourceCategoryResult")
    Optional<ResourceCategory> selectOne(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results(id="ResourceCategoryResult", value = {
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="category_name", property="categoryName", jdbcType=JdbcType.VARCHAR),
        @Result(column="category_icon", property="categoryIcon", jdbcType=JdbcType.VARCHAR),
        @Result(column="server_name", property="serverName", jdbcType=JdbcType.VARCHAR),
        @Result(column="category_no", property="categoryNo", jdbcType=JdbcType.INTEGER),
        @Result(column="version", property="version", jdbcType=JdbcType.INTEGER),
        @Result(column="deleted", property="deleted", jdbcType=JdbcType.BIT),
        @Result(column="creator_id", property="creatorId", jdbcType=JdbcType.INTEGER),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="last_modifier_id", property="lastModifierId", jdbcType=JdbcType.INTEGER),
        @Result(column="last_modify_time", property="lastModifyTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="tenant_id", property="tenantId", jdbcType=JdbcType.VARCHAR)
    })
    List<ResourceCategory> selectMany(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @UpdateProvider(type=SqlProviderAdapter.class, method="update")
    int update(UpdateStatementProvider updateStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default long count(CountDSLCompleter completer) {
        return MyBatis3Utils.countFrom(this::count, resourceCategory, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int delete(DeleteDSLCompleter completer) {
        return MyBatis3Utils.deleteFrom(this::delete, resourceCategory, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int deleteByPrimaryKey(Integer id_) {
        return delete(c -> 
            c.where(id, isEqualTo(id_))
        );
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int insert(ResourceCategory record) {
        return MyBatis3Utils.insert(this::insert, record, resourceCategory, c ->
            c.map(categoryName).toProperty("categoryName")
            .map(categoryIcon).toProperty("categoryIcon")
            .map(serverName).toProperty("serverName")
            .map(categoryNo).toProperty("categoryNo")
            .map(version).toProperty("version")
            .map(deleted).toProperty("deleted")
            .map(creatorId).toProperty("creatorId")
            .map(createTime).toProperty("createTime")
            .map(lastModifierId).toProperty("lastModifierId")
            .map(lastModifyTime).toProperty("lastModifyTime")
            .map(tenantId).toProperty("tenantId")
        );
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int insertSelective(ResourceCategory record) {
        return MyBatis3Utils.insert(this::insert, record, resourceCategory, c ->
            c.map(categoryName).toPropertyWhenPresent("categoryName", record::getCategoryName)
            .map(categoryIcon).toPropertyWhenPresent("categoryIcon", record::getCategoryIcon)
            .map(serverName).toPropertyWhenPresent("serverName", record::getServerName)
            .map(categoryNo).toPropertyWhenPresent("categoryNo", record::getCategoryNo)
            .map(version).toPropertyWhenPresent("version", record::getVersion)
            .map(deleted).toPropertyWhenPresent("deleted", record::getDeleted)
            .map(creatorId).toPropertyWhenPresent("creatorId", record::getCreatorId)
            .map(createTime).toPropertyWhenPresent("createTime", record::getCreateTime)
            .map(lastModifierId).toPropertyWhenPresent("lastModifierId", record::getLastModifierId)
            .map(lastModifyTime).toPropertyWhenPresent("lastModifyTime", record::getLastModifyTime)
            .map(tenantId).toPropertyWhenPresent("tenantId", record::getTenantId)
        );
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default Optional<ResourceCategory> selectOne(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectOne(this::selectOne, selectList, resourceCategory, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default List<ResourceCategory> select(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectList(this::selectMany, selectList, resourceCategory, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default List<ResourceCategory> selectDistinct(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectDistinct(this::selectMany, selectList, resourceCategory, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default Optional<ResourceCategory> selectByPrimaryKey(Integer id_) {
        return selectOne(c ->
            c.where(id, isEqualTo(id_))
        );
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int update(UpdateDSLCompleter completer) {
        return MyBatis3Utils.update(this::update, resourceCategory, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    static UpdateDSL<UpdateModel> updateAllColumns(ResourceCategory record, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(categoryName).equalTo(record::getCategoryName)
                .set(categoryIcon).equalTo(record::getCategoryIcon)
                .set(serverName).equalTo(record::getServerName)
                .set(categoryNo).equalTo(record::getCategoryNo)
                .set(version).equalTo(record::getVersion)
                .set(deleted).equalTo(record::getDeleted)
                .set(creatorId).equalTo(record::getCreatorId)
                .set(createTime).equalTo(record::getCreateTime)
                .set(lastModifierId).equalTo(record::getLastModifierId)
                .set(lastModifyTime).equalTo(record::getLastModifyTime)
                .set(tenantId).equalTo(record::getTenantId);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    static UpdateDSL<UpdateModel> updateSelectiveColumns(ResourceCategory record, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(categoryName).equalToWhenPresent(record::getCategoryName)
                .set(categoryIcon).equalToWhenPresent(record::getCategoryIcon)
                .set(serverName).equalToWhenPresent(record::getServerName)
                .set(categoryNo).equalToWhenPresent(record::getCategoryNo)
                .set(version).equalToWhenPresent(record::getVersion)
                .set(deleted).equalToWhenPresent(record::getDeleted)
                .set(creatorId).equalToWhenPresent(record::getCreatorId)
                .set(createTime).equalToWhenPresent(record::getCreateTime)
                .set(lastModifierId).equalToWhenPresent(record::getLastModifierId)
                .set(lastModifyTime).equalToWhenPresent(record::getLastModifyTime)
                .set(tenantId).equalToWhenPresent(record::getTenantId);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int updateByPrimaryKey(ResourceCategory record) {
        return update(c ->
            c.set(categoryName).equalTo(record::getCategoryName)
            .set(categoryIcon).equalTo(record::getCategoryIcon)
            .set(serverName).equalTo(record::getServerName)
            .set(categoryNo).equalTo(record::getCategoryNo)
            .set(version).equalTo(record::getVersion)
            .set(deleted).equalTo(record::getDeleted)
            .set(creatorId).equalTo(record::getCreatorId)
            .set(createTime).equalTo(record::getCreateTime)
            .set(lastModifierId).equalTo(record::getLastModifierId)
            .set(lastModifyTime).equalTo(record::getLastModifyTime)
            .set(tenantId).equalTo(record::getTenantId)
            .where(id, isEqualTo(record::getId))
        );
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int updateByPrimaryKeySelective(ResourceCategory record) {
        return update(c ->
            c.set(categoryName).equalToWhenPresent(record::getCategoryName)
            .set(categoryIcon).equalToWhenPresent(record::getCategoryIcon)
            .set(serverName).equalToWhenPresent(record::getServerName)
            .set(categoryNo).equalToWhenPresent(record::getCategoryNo)
            .set(version).equalToWhenPresent(record::getVersion)
            .set(deleted).equalToWhenPresent(record::getDeleted)
            .set(creatorId).equalToWhenPresent(record::getCreatorId)
            .set(createTime).equalToWhenPresent(record::getCreateTime)
            .set(lastModifierId).equalToWhenPresent(record::getLastModifierId)
            .set(lastModifyTime).equalToWhenPresent(record::getLastModifyTime)
            .set(tenantId).equalToWhenPresent(record::getTenantId)
            .where(id, isEqualTo(record::getId))
        );
    }
}