package com.op.admin.mapper;

import com.op.admin.entity.UserResourceActionRelation;
import java.util.List;
import java.util.Optional;
import javax.annotation.Generated;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
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
 * @date 2021/06/24 03:12
 */
@Mapper
public interface UserResourceActionRelationMapper {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    BasicColumn[] selectList = BasicColumn.columnList(UserResourceActionRelationDynamicSqlSupport.userId, UserResourceActionRelationDynamicSqlSupport.actionId, UserResourceActionRelationDynamicSqlSupport.version, UserResourceActionRelationDynamicSqlSupport.deleted, UserResourceActionRelationDynamicSqlSupport.creatorId, UserResourceActionRelationDynamicSqlSupport.createTime, UserResourceActionRelationDynamicSqlSupport.lastModifierId, UserResourceActionRelationDynamicSqlSupport.lastModifyTime, UserResourceActionRelationDynamicSqlSupport.tenantId);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    long count(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @DeleteProvider(type=SqlProviderAdapter.class, method="delete")
    int delete(DeleteStatementProvider deleteStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @InsertProvider(type=SqlProviderAdapter.class, method="insert")
    int insert(InsertStatementProvider<UserResourceActionRelation> insertStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("UserResourceActionRelationResult")
    Optional<UserResourceActionRelation> selectOne(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results(id="UserResourceActionRelationResult", value = {
        @Result(column="user_id", property="userId", jdbcType=JdbcType.INTEGER),
        @Result(column="action_id", property="actionId", jdbcType=JdbcType.INTEGER),
        @Result(column="version", property="version", jdbcType=JdbcType.INTEGER),
        @Result(column="deleted", property="deleted", jdbcType=JdbcType.BIT),
        @Result(column="creator_id", property="creatorId", jdbcType=JdbcType.INTEGER),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="last_modifier_id", property="lastModifierId", jdbcType=JdbcType.INTEGER),
        @Result(column="last_modify_time", property="lastModifyTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="tenant_id", property="tenantId", jdbcType=JdbcType.VARCHAR)
    })
    List<UserResourceActionRelation> selectMany(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @UpdateProvider(type=SqlProviderAdapter.class, method="update")
    int update(UpdateStatementProvider updateStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default long count(CountDSLCompleter completer) {
        return MyBatis3Utils.countFrom(this::count, UserResourceActionRelationDynamicSqlSupport.userResourceActionRelation, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int delete(DeleteDSLCompleter completer) {
        return MyBatis3Utils.deleteFrom(this::delete, UserResourceActionRelationDynamicSqlSupport.userResourceActionRelation, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int insert(UserResourceActionRelation record) {
        return MyBatis3Utils.insert(this::insert, record, UserResourceActionRelationDynamicSqlSupport.userResourceActionRelation, c ->
            c.map(UserResourceActionRelationDynamicSqlSupport.userId).toProperty("userId")
            .map(UserResourceActionRelationDynamicSqlSupport.actionId).toProperty("actionId")
            .map(UserResourceActionRelationDynamicSqlSupport.version).toProperty("version")
            .map(UserResourceActionRelationDynamicSqlSupport.deleted).toProperty("deleted")
            .map(UserResourceActionRelationDynamicSqlSupport.creatorId).toProperty("creatorId")
            .map(UserResourceActionRelationDynamicSqlSupport.createTime).toProperty("createTime")
            .map(UserResourceActionRelationDynamicSqlSupport.lastModifierId).toProperty("lastModifierId")
            .map(UserResourceActionRelationDynamicSqlSupport.lastModifyTime).toProperty("lastModifyTime")
            .map(UserResourceActionRelationDynamicSqlSupport.tenantId).toProperty("tenantId")
        );
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int insertSelective(UserResourceActionRelation record) {
        return MyBatis3Utils.insert(this::insert, record, UserResourceActionRelationDynamicSqlSupport.userResourceActionRelation, c ->
            c.map(UserResourceActionRelationDynamicSqlSupport.userId).toPropertyWhenPresent("userId", record::getUserId)
            .map(UserResourceActionRelationDynamicSqlSupport.actionId).toPropertyWhenPresent("actionId", record::getActionId)
            .map(UserResourceActionRelationDynamicSqlSupport.version).toPropertyWhenPresent("version", record::getVersion)
            .map(UserResourceActionRelationDynamicSqlSupport.deleted).toPropertyWhenPresent("deleted", record::getDeleted)
            .map(UserResourceActionRelationDynamicSqlSupport.creatorId).toPropertyWhenPresent("creatorId", record::getCreatorId)
            .map(UserResourceActionRelationDynamicSqlSupport.createTime).toPropertyWhenPresent("createTime", record::getCreateTime)
            .map(UserResourceActionRelationDynamicSqlSupport.lastModifierId).toPropertyWhenPresent("lastModifierId", record::getLastModifierId)
            .map(UserResourceActionRelationDynamicSqlSupport.lastModifyTime).toPropertyWhenPresent("lastModifyTime", record::getLastModifyTime)
            .map(UserResourceActionRelationDynamicSqlSupport.tenantId).toPropertyWhenPresent("tenantId", record::getTenantId)
        );
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default Optional<UserResourceActionRelation> selectOne(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectOne(this::selectOne, selectList, UserResourceActionRelationDynamicSqlSupport.userResourceActionRelation, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default List<UserResourceActionRelation> select(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectList(this::selectMany, selectList, UserResourceActionRelationDynamicSqlSupport.userResourceActionRelation, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default List<UserResourceActionRelation> selectDistinct(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectDistinct(this::selectMany, selectList, UserResourceActionRelationDynamicSqlSupport.userResourceActionRelation, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int update(UpdateDSLCompleter completer) {
        return MyBatis3Utils.update(this::update, UserResourceActionRelationDynamicSqlSupport.userResourceActionRelation, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    static UpdateDSL<UpdateModel> updateAllColumns(UserResourceActionRelation record, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(UserResourceActionRelationDynamicSqlSupport.userId).equalTo(record::getUserId)
                .set(UserResourceActionRelationDynamicSqlSupport.actionId).equalTo(record::getActionId)
                .set(UserResourceActionRelationDynamicSqlSupport.version).equalTo(record::getVersion)
                .set(UserResourceActionRelationDynamicSqlSupport.deleted).equalTo(record::getDeleted)
                .set(UserResourceActionRelationDynamicSqlSupport.creatorId).equalTo(record::getCreatorId)
                .set(UserResourceActionRelationDynamicSqlSupport.createTime).equalTo(record::getCreateTime)
                .set(UserResourceActionRelationDynamicSqlSupport.lastModifierId).equalTo(record::getLastModifierId)
                .set(UserResourceActionRelationDynamicSqlSupport.lastModifyTime).equalTo(record::getLastModifyTime)
                .set(UserResourceActionRelationDynamicSqlSupport.tenantId).equalTo(record::getTenantId);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    static UpdateDSL<UpdateModel> updateSelectiveColumns(UserResourceActionRelation record, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(UserResourceActionRelationDynamicSqlSupport.userId).equalToWhenPresent(record::getUserId)
                .set(UserResourceActionRelationDynamicSqlSupport.actionId).equalToWhenPresent(record::getActionId)
                .set(UserResourceActionRelationDynamicSqlSupport.version).equalToWhenPresent(record::getVersion)
                .set(UserResourceActionRelationDynamicSqlSupport.deleted).equalToWhenPresent(record::getDeleted)
                .set(UserResourceActionRelationDynamicSqlSupport.creatorId).equalToWhenPresent(record::getCreatorId)
                .set(UserResourceActionRelationDynamicSqlSupport.createTime).equalToWhenPresent(record::getCreateTime)
                .set(UserResourceActionRelationDynamicSqlSupport.lastModifierId).equalToWhenPresent(record::getLastModifierId)
                .set(UserResourceActionRelationDynamicSqlSupport.lastModifyTime).equalToWhenPresent(record::getLastModifyTime)
                .set(UserResourceActionRelationDynamicSqlSupport.tenantId).equalToWhenPresent(record::getTenantId);
    }
}