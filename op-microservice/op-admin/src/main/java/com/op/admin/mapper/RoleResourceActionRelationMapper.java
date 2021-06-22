package com.op.admin.mapper;

import com.op.admin.entity.RoleResourceActionRelation;
import org.apache.ibatis.annotations.*;
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

import javax.annotation.Generated;
import java.util.List;
import java.util.Optional;

import static com.op.admin.mapper.RoleResourceActionRelationDynamicSqlSupport.*;

/**
 * @author Mybatis Generator
 * @date 2021/06/18 11:20
 */
@Mapper
public interface RoleResourceActionRelationMapper {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    BasicColumn[] selectList = BasicColumn.columnList(roleId, actionId, version, deleted, creatorId, createTime, lastModifierId, lastModifyTime, tenantId);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type = SqlProviderAdapter.class, method = "select")
    long count(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @DeleteProvider(type = SqlProviderAdapter.class, method = "delete")
    int delete(DeleteStatementProvider deleteStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @InsertProvider(type = SqlProviderAdapter.class, method = "insert")
    int insert(InsertStatementProvider<RoleResourceActionRelation> insertStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type = SqlProviderAdapter.class, method = "select")
    @ResultMap("RoleResourceActionRelationResult")
    Optional<RoleResourceActionRelation> selectOne(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type = SqlProviderAdapter.class, method = "select")
    @Results(id = "RoleResourceActionRelationResult", value = {
            @Result(column = "role_id", property = "roleId", jdbcType = JdbcType.INTEGER),
            @Result(column = "action_id", property = "actionId", jdbcType = JdbcType.INTEGER),
            @Result(column = "version", property = "version", jdbcType = JdbcType.INTEGER),
            @Result(column = "deleted", property = "deleted", jdbcType = JdbcType.BIT),
            @Result(column = "creator_id", property = "creatorId", jdbcType = JdbcType.INTEGER),
            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "last_modifier_id", property = "lastModifierId", jdbcType = JdbcType.INTEGER),
            @Result(column = "last_modify_time", property = "lastModifyTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "tenant_id", property = "tenantId", jdbcType = JdbcType.VARCHAR)
    })
    List<RoleResourceActionRelation> selectMany(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @UpdateProvider(type = SqlProviderAdapter.class, method = "update")
    int update(UpdateStatementProvider updateStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default long count(CountDSLCompleter completer) {
        return MyBatis3Utils.countFrom(this::count, roleResourceActionRelation, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int delete(DeleteDSLCompleter completer) {
        return MyBatis3Utils.deleteFrom(this::delete, roleResourceActionRelation, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int insert(RoleResourceActionRelation record) {
        return MyBatis3Utils.insert(this::insert, record, roleResourceActionRelation, c ->
                c.map(roleId).toProperty("roleId")
                        .map(actionId).toProperty("actionId")
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
    default int insertSelective(RoleResourceActionRelation record) {
        return MyBatis3Utils.insert(this::insert, record, roleResourceActionRelation, c ->
                c.map(roleId).toPropertyWhenPresent("roleId", record::getRoleId)
                        .map(actionId).toPropertyWhenPresent("actionId", record::getActionId)
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
    default Optional<RoleResourceActionRelation> selectOne(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectOne(this::selectOne, selectList, roleResourceActionRelation, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default List<RoleResourceActionRelation> select(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectList(this::selectMany, selectList, roleResourceActionRelation, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default List<RoleResourceActionRelation> selectDistinct(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectDistinct(this::selectMany, selectList, roleResourceActionRelation, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int update(UpdateDSLCompleter completer) {
        return MyBatis3Utils.update(this::update, roleResourceActionRelation, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    static UpdateDSL<UpdateModel> updateAllColumns(RoleResourceActionRelation record, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(roleId).equalTo(record::getRoleId)
                .set(actionId).equalTo(record::getActionId)
                .set(version).equalTo(record::getVersion)
                .set(deleted).equalTo(record::getDeleted)
                .set(creatorId).equalTo(record::getCreatorId)
                .set(createTime).equalTo(record::getCreateTime)
                .set(lastModifierId).equalTo(record::getLastModifierId)
                .set(lastModifyTime).equalTo(record::getLastModifyTime)
                .set(tenantId).equalTo(record::getTenantId);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    static UpdateDSL<UpdateModel> updateSelectiveColumns(RoleResourceActionRelation record, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(roleId).equalToWhenPresent(record::getRoleId)
                .set(actionId).equalToWhenPresent(record::getActionId)
                .set(version).equalToWhenPresent(record::getVersion)
                .set(deleted).equalToWhenPresent(record::getDeleted)
                .set(creatorId).equalToWhenPresent(record::getCreatorId)
                .set(createTime).equalToWhenPresent(record::getCreateTime)
                .set(lastModifierId).equalToWhenPresent(record::getLastModifierId)
                .set(lastModifyTime).equalToWhenPresent(record::getLastModifyTime)
                .set(tenantId).equalToWhenPresent(record::getTenantId);
    }
}