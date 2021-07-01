package com.op.admin.mapper;

import com.op.admin.entity.RoleMenuRelation;
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

/**
 * @author Mybatis Generator
 * @date 2021/06/18 11:20
 */
@Mapper
public interface RoleMenuRelationMapper {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    BasicColumn[] selectList = BasicColumn.columnList(RoleMenuRelationDynamicSqlSupport.roleId, RoleMenuRelationDynamicSqlSupport.menuId, RoleMenuRelationDynamicSqlSupport.version, RoleMenuRelationDynamicSqlSupport.deleted, RoleMenuRelationDynamicSqlSupport.creatorId, RoleMenuRelationDynamicSqlSupport.createTime, RoleMenuRelationDynamicSqlSupport.lastModifierId, RoleMenuRelationDynamicSqlSupport.lastModifyTime, RoleMenuRelationDynamicSqlSupport.tenantId);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type = SqlProviderAdapter.class, method = "select")
    long count(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @DeleteProvider(type = SqlProviderAdapter.class, method = "delete")
    int delete(DeleteStatementProvider deleteStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @InsertProvider(type = SqlProviderAdapter.class, method = "insert")
    int insert(InsertStatementProvider<RoleMenuRelation> insertStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type = SqlProviderAdapter.class, method = "select")
    @ResultMap("RoleMenuRelationResult")
    Optional<RoleMenuRelation> selectOne(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type = SqlProviderAdapter.class, method = "select")
    @Results(id = "RoleMenuRelationResult", value = {
            @Result(column = "role_id", property = "roleId", jdbcType = JdbcType.INTEGER),
            @Result(column = "menu_id", property = "menuId", jdbcType = JdbcType.INTEGER),
            @Result(column = "version", property = "version", jdbcType = JdbcType.INTEGER),
            @Result(column = "deleted", property = "deleted", jdbcType = JdbcType.BIT),
            @Result(column = "creator_id", property = "creatorId", jdbcType = JdbcType.INTEGER),
            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "last_modifier_id", property = "lastModifierId", jdbcType = JdbcType.INTEGER),
            @Result(column = "last_modify_time", property = "lastModifyTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "tenant_id", property = "tenantId", jdbcType = JdbcType.VARCHAR)
    })
    List<RoleMenuRelation> selectMany(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @UpdateProvider(type = SqlProviderAdapter.class, method = "update")
    int update(UpdateStatementProvider updateStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default long count(CountDSLCompleter completer) {
        return MyBatis3Utils.countFrom(this::count, RoleMenuRelationDynamicSqlSupport.roleMenuRelation, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int delete(DeleteDSLCompleter completer) {
        return MyBatis3Utils.deleteFrom(this::delete, RoleMenuRelationDynamicSqlSupport.roleMenuRelation, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int insert(RoleMenuRelation record) {
        return MyBatis3Utils.insert(this::insert, record, RoleMenuRelationDynamicSqlSupport.roleMenuRelation, c ->
                c.map(RoleMenuRelationDynamicSqlSupport.roleId).toProperty("roleId")
                        .map(RoleMenuRelationDynamicSqlSupport.menuId).toProperty("menuId")
                        .map(RoleMenuRelationDynamicSqlSupport.version).toProperty("version")
                        .map(RoleMenuRelationDynamicSqlSupport.deleted).toProperty("deleted")
                        .map(RoleMenuRelationDynamicSqlSupport.creatorId).toProperty("creatorId")
                        .map(RoleMenuRelationDynamicSqlSupport.createTime).toProperty("createTime")
                        .map(RoleMenuRelationDynamicSqlSupport.lastModifierId).toProperty("lastModifierId")
                        .map(RoleMenuRelationDynamicSqlSupport.lastModifyTime).toProperty("lastModifyTime")
                        .map(RoleMenuRelationDynamicSqlSupport.tenantId).toProperty("tenantId")
        );
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int insertSelective(RoleMenuRelation record) {
        return MyBatis3Utils.insert(this::insert, record, RoleMenuRelationDynamicSqlSupport.roleMenuRelation, c ->
                c.map(RoleMenuRelationDynamicSqlSupport.roleId).toPropertyWhenPresent("roleId", record::getRoleId)
                        .map(RoleMenuRelationDynamicSqlSupport.menuId).toPropertyWhenPresent("menuId", record::getMenuId)
                        .map(RoleMenuRelationDynamicSqlSupport.version).toPropertyWhenPresent("version", record::getVersion)
                        .map(RoleMenuRelationDynamicSqlSupport.deleted).toPropertyWhenPresent("deleted", record::getDeleted)
                        .map(RoleMenuRelationDynamicSqlSupport.creatorId).toPropertyWhenPresent("creatorId", record::getCreatorId)
                        .map(RoleMenuRelationDynamicSqlSupport.createTime).toPropertyWhenPresent("createTime", record::getCreateTime)
                        .map(RoleMenuRelationDynamicSqlSupport.lastModifierId).toPropertyWhenPresent("lastModifierId", record::getLastModifierId)
                        .map(RoleMenuRelationDynamicSqlSupport.lastModifyTime).toPropertyWhenPresent("lastModifyTime", record::getLastModifyTime)
                        .map(RoleMenuRelationDynamicSqlSupport.tenantId).toPropertyWhenPresent("tenantId", record::getTenantId)
        );
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default Optional<RoleMenuRelation> selectOne(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectOne(this::selectOne, selectList, RoleMenuRelationDynamicSqlSupport.roleMenuRelation, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default List<RoleMenuRelation> select(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectList(this::selectMany, selectList, RoleMenuRelationDynamicSqlSupport.roleMenuRelation, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default List<RoleMenuRelation> selectDistinct(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectDistinct(this::selectMany, selectList, RoleMenuRelationDynamicSqlSupport.roleMenuRelation, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int update(UpdateDSLCompleter completer) {
        return MyBatis3Utils.update(this::update, RoleMenuRelationDynamicSqlSupport.roleMenuRelation, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    static UpdateDSL<UpdateModel> updateAllColumns(RoleMenuRelation record, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(RoleMenuRelationDynamicSqlSupport.roleId).equalTo(record::getRoleId)
                .set(RoleMenuRelationDynamicSqlSupport.menuId).equalTo(record::getMenuId)
                .set(RoleMenuRelationDynamicSqlSupport.version).equalTo(record::getVersion)
                .set(RoleMenuRelationDynamicSqlSupport.deleted).equalTo(record::getDeleted)
                .set(RoleMenuRelationDynamicSqlSupport.creatorId).equalTo(record::getCreatorId)
                .set(RoleMenuRelationDynamicSqlSupport.createTime).equalTo(record::getCreateTime)
                .set(RoleMenuRelationDynamicSqlSupport.lastModifierId).equalTo(record::getLastModifierId)
                .set(RoleMenuRelationDynamicSqlSupport.lastModifyTime).equalTo(record::getLastModifyTime)
                .set(RoleMenuRelationDynamicSqlSupport.tenantId).equalTo(record::getTenantId);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    static UpdateDSL<UpdateModel> updateSelectiveColumns(RoleMenuRelation record, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(RoleMenuRelationDynamicSqlSupport.roleId).equalToWhenPresent(record::getRoleId)
                .set(RoleMenuRelationDynamicSqlSupport.menuId).equalToWhenPresent(record::getMenuId)
                .set(RoleMenuRelationDynamicSqlSupport.version).equalToWhenPresent(record::getVersion)
                .set(RoleMenuRelationDynamicSqlSupport.deleted).equalToWhenPresent(record::getDeleted)
                .set(RoleMenuRelationDynamicSqlSupport.creatorId).equalToWhenPresent(record::getCreatorId)
                .set(RoleMenuRelationDynamicSqlSupport.createTime).equalToWhenPresent(record::getCreateTime)
                .set(RoleMenuRelationDynamicSqlSupport.lastModifierId).equalToWhenPresent(record::getLastModifierId)
                .set(RoleMenuRelationDynamicSqlSupport.lastModifyTime).equalToWhenPresent(record::getLastModifyTime)
                .set(RoleMenuRelationDynamicSqlSupport.tenantId).equalToWhenPresent(record::getTenantId);
    }
}