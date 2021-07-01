package com.op.admin.mapper;

import com.op.admin.entity.UserGroupResourceActionRelation;
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
public interface UserGroupResourceActionRelationMapper {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    BasicColumn[] selectList = BasicColumn.columnList(UserGroupResourceActionRelationDynamicSqlSupport.groupId, UserGroupResourceActionRelationDynamicSqlSupport.actionId, UserGroupResourceActionRelationDynamicSqlSupport.version, UserGroupResourceActionRelationDynamicSqlSupport.deleted, UserGroupResourceActionRelationDynamicSqlSupport.creatorId, UserGroupResourceActionRelationDynamicSqlSupport.createTime, UserGroupResourceActionRelationDynamicSqlSupport.lastModifierId, UserGroupResourceActionRelationDynamicSqlSupport.lastModifyTime, UserGroupResourceActionRelationDynamicSqlSupport.tenantId);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    long count(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @DeleteProvider(type=SqlProviderAdapter.class, method="delete")
    int delete(DeleteStatementProvider deleteStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @InsertProvider(type=SqlProviderAdapter.class, method="insert")
    int insert(InsertStatementProvider<UserGroupResourceActionRelation> insertStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("UserGroupResourceActionRelationResult")
    Optional<UserGroupResourceActionRelation> selectOne(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results(id="UserGroupResourceActionRelationResult", value = {
        @Result(column="group_id", property="groupId", jdbcType=JdbcType.INTEGER),
        @Result(column="action_id", property="actionId", jdbcType=JdbcType.INTEGER),
        @Result(column="version", property="version", jdbcType=JdbcType.INTEGER),
        @Result(column="deleted", property="deleted", jdbcType=JdbcType.BIT),
        @Result(column="creator_id", property="creatorId", jdbcType=JdbcType.INTEGER),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="last_modifier_id", property="lastModifierId", jdbcType=JdbcType.INTEGER),
        @Result(column="last_modify_time", property="lastModifyTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="tenant_id", property="tenantId", jdbcType=JdbcType.VARCHAR)
    })
    List<UserGroupResourceActionRelation> selectMany(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @UpdateProvider(type=SqlProviderAdapter.class, method="update")
    int update(UpdateStatementProvider updateStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default long count(CountDSLCompleter completer) {
        return MyBatis3Utils.countFrom(this::count, UserGroupResourceActionRelationDynamicSqlSupport.userGroupResourceActionRelation, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int delete(DeleteDSLCompleter completer) {
        return MyBatis3Utils.deleteFrom(this::delete, UserGroupResourceActionRelationDynamicSqlSupport.userGroupResourceActionRelation, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int insert(UserGroupResourceActionRelation record) {
        return MyBatis3Utils.insert(this::insert, record, UserGroupResourceActionRelationDynamicSqlSupport.userGroupResourceActionRelation, c ->
            c.map(UserGroupResourceActionRelationDynamicSqlSupport.groupId).toProperty("groupId")
            .map(UserGroupResourceActionRelationDynamicSqlSupport.actionId).toProperty("actionId")
            .map(UserGroupResourceActionRelationDynamicSqlSupport.version).toProperty("version")
            .map(UserGroupResourceActionRelationDynamicSqlSupport.deleted).toProperty("deleted")
            .map(UserGroupResourceActionRelationDynamicSqlSupport.creatorId).toProperty("creatorId")
            .map(UserGroupResourceActionRelationDynamicSqlSupport.createTime).toProperty("createTime")
            .map(UserGroupResourceActionRelationDynamicSqlSupport.lastModifierId).toProperty("lastModifierId")
            .map(UserGroupResourceActionRelationDynamicSqlSupport.lastModifyTime).toProperty("lastModifyTime")
            .map(UserGroupResourceActionRelationDynamicSqlSupport.tenantId).toProperty("tenantId")
        );
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int insertSelective(UserGroupResourceActionRelation record) {
        return MyBatis3Utils.insert(this::insert, record, UserGroupResourceActionRelationDynamicSqlSupport.userGroupResourceActionRelation, c ->
            c.map(UserGroupResourceActionRelationDynamicSqlSupport.groupId).toPropertyWhenPresent("groupId", record::getGroupId)
            .map(UserGroupResourceActionRelationDynamicSqlSupport.actionId).toPropertyWhenPresent("actionId", record::getActionId)
            .map(UserGroupResourceActionRelationDynamicSqlSupport.version).toPropertyWhenPresent("version", record::getVersion)
            .map(UserGroupResourceActionRelationDynamicSqlSupport.deleted).toPropertyWhenPresent("deleted", record::getDeleted)
            .map(UserGroupResourceActionRelationDynamicSqlSupport.creatorId).toPropertyWhenPresent("creatorId", record::getCreatorId)
            .map(UserGroupResourceActionRelationDynamicSqlSupport.createTime).toPropertyWhenPresent("createTime", record::getCreateTime)
            .map(UserGroupResourceActionRelationDynamicSqlSupport.lastModifierId).toPropertyWhenPresent("lastModifierId", record::getLastModifierId)
            .map(UserGroupResourceActionRelationDynamicSqlSupport.lastModifyTime).toPropertyWhenPresent("lastModifyTime", record::getLastModifyTime)
            .map(UserGroupResourceActionRelationDynamicSqlSupport.tenantId).toPropertyWhenPresent("tenantId", record::getTenantId)
        );
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default Optional<UserGroupResourceActionRelation> selectOne(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectOne(this::selectOne, selectList, UserGroupResourceActionRelationDynamicSqlSupport.userGroupResourceActionRelation, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default List<UserGroupResourceActionRelation> select(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectList(this::selectMany, selectList, UserGroupResourceActionRelationDynamicSqlSupport.userGroupResourceActionRelation, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default List<UserGroupResourceActionRelation> selectDistinct(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectDistinct(this::selectMany, selectList, UserGroupResourceActionRelationDynamicSqlSupport.userGroupResourceActionRelation, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int update(UpdateDSLCompleter completer) {
        return MyBatis3Utils.update(this::update, UserGroupResourceActionRelationDynamicSqlSupport.userGroupResourceActionRelation, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    static UpdateDSL<UpdateModel> updateAllColumns(UserGroupResourceActionRelation record, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(UserGroupResourceActionRelationDynamicSqlSupport.groupId).equalTo(record::getGroupId)
                .set(UserGroupResourceActionRelationDynamicSqlSupport.actionId).equalTo(record::getActionId)
                .set(UserGroupResourceActionRelationDynamicSqlSupport.version).equalTo(record::getVersion)
                .set(UserGroupResourceActionRelationDynamicSqlSupport.deleted).equalTo(record::getDeleted)
                .set(UserGroupResourceActionRelationDynamicSqlSupport.creatorId).equalTo(record::getCreatorId)
                .set(UserGroupResourceActionRelationDynamicSqlSupport.createTime).equalTo(record::getCreateTime)
                .set(UserGroupResourceActionRelationDynamicSqlSupport.lastModifierId).equalTo(record::getLastModifierId)
                .set(UserGroupResourceActionRelationDynamicSqlSupport.lastModifyTime).equalTo(record::getLastModifyTime)
                .set(UserGroupResourceActionRelationDynamicSqlSupport.tenantId).equalTo(record::getTenantId);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    static UpdateDSL<UpdateModel> updateSelectiveColumns(UserGroupResourceActionRelation record, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(UserGroupResourceActionRelationDynamicSqlSupport.groupId).equalToWhenPresent(record::getGroupId)
                .set(UserGroupResourceActionRelationDynamicSqlSupport.actionId).equalToWhenPresent(record::getActionId)
                .set(UserGroupResourceActionRelationDynamicSqlSupport.version).equalToWhenPresent(record::getVersion)
                .set(UserGroupResourceActionRelationDynamicSqlSupport.deleted).equalToWhenPresent(record::getDeleted)
                .set(UserGroupResourceActionRelationDynamicSqlSupport.creatorId).equalToWhenPresent(record::getCreatorId)
                .set(UserGroupResourceActionRelationDynamicSqlSupport.createTime).equalToWhenPresent(record::getCreateTime)
                .set(UserGroupResourceActionRelationDynamicSqlSupport.lastModifierId).equalToWhenPresent(record::getLastModifierId)
                .set(UserGroupResourceActionRelationDynamicSqlSupport.lastModifyTime).equalToWhenPresent(record::getLastModifyTime)
                .set(UserGroupResourceActionRelationDynamicSqlSupport.tenantId).equalToWhenPresent(record::getTenantId);
    }
}