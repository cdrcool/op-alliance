package com.op.admin.mapper;

import com.op.admin.entity.OrganizationRoleRelation;
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
public interface OrganizationRoleRelationMapper {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    BasicColumn[] selectList = BasicColumn.columnList(OrganizationRoleRelationDynamicSqlSupport.orgId, OrganizationRoleRelationDynamicSqlSupport.roleId, OrganizationRoleRelationDynamicSqlSupport.version, OrganizationRoleRelationDynamicSqlSupport.deleted, OrganizationRoleRelationDynamicSqlSupport.creatorId, OrganizationRoleRelationDynamicSqlSupport.createTime, OrganizationRoleRelationDynamicSqlSupport.lastModifierId, OrganizationRoleRelationDynamicSqlSupport.lastModifyTime, OrganizationRoleRelationDynamicSqlSupport.tenantId);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    long count(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @DeleteProvider(type=SqlProviderAdapter.class, method="delete")
    int delete(DeleteStatementProvider deleteStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @InsertProvider(type=SqlProviderAdapter.class, method="insert")
    int insert(InsertStatementProvider<OrganizationRoleRelation> insertStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("OrganizationRoleRelationResult")
    Optional<OrganizationRoleRelation> selectOne(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results(id="OrganizationRoleRelationResult", value = {
        @Result(column="org_id", property="orgId", jdbcType=JdbcType.INTEGER),
        @Result(column="role_id", property="roleId", jdbcType=JdbcType.INTEGER),
        @Result(column="version", property="version", jdbcType=JdbcType.INTEGER),
        @Result(column="deleted", property="deleted", jdbcType=JdbcType.BIT),
        @Result(column="creator_id", property="creatorId", jdbcType=JdbcType.INTEGER),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="last_modifier_id", property="lastModifierId", jdbcType=JdbcType.INTEGER),
        @Result(column="last_modify_time", property="lastModifyTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="tenant_id", property="tenantId", jdbcType=JdbcType.VARCHAR)
    })
    List<OrganizationRoleRelation> selectMany(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @UpdateProvider(type=SqlProviderAdapter.class, method="update")
    int update(UpdateStatementProvider updateStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default long count(CountDSLCompleter completer) {
        return MyBatis3Utils.countFrom(this::count, OrganizationRoleRelationDynamicSqlSupport.organizationRoleRelation, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int delete(DeleteDSLCompleter completer) {
        return MyBatis3Utils.deleteFrom(this::delete, OrganizationRoleRelationDynamicSqlSupport.organizationRoleRelation, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int insert(OrganizationRoleRelation record) {
        return MyBatis3Utils.insert(this::insert, record, OrganizationRoleRelationDynamicSqlSupport.organizationRoleRelation, c ->
            c.map(OrganizationRoleRelationDynamicSqlSupport.orgId).toProperty("orgId")
            .map(OrganizationRoleRelationDynamicSqlSupport.roleId).toProperty("roleId")
            .map(OrganizationRoleRelationDynamicSqlSupport.version).toProperty("version")
            .map(OrganizationRoleRelationDynamicSqlSupport.deleted).toProperty("deleted")
            .map(OrganizationRoleRelationDynamicSqlSupport.creatorId).toProperty("creatorId")
            .map(OrganizationRoleRelationDynamicSqlSupport.createTime).toProperty("createTime")
            .map(OrganizationRoleRelationDynamicSqlSupport.lastModifierId).toProperty("lastModifierId")
            .map(OrganizationRoleRelationDynamicSqlSupport.lastModifyTime).toProperty("lastModifyTime")
            .map(OrganizationRoleRelationDynamicSqlSupport.tenantId).toProperty("tenantId")
        );
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int insertSelective(OrganizationRoleRelation record) {
        return MyBatis3Utils.insert(this::insert, record, OrganizationRoleRelationDynamicSqlSupport.organizationRoleRelation, c ->
            c.map(OrganizationRoleRelationDynamicSqlSupport.orgId).toPropertyWhenPresent("orgId", record::getOrgId)
            .map(OrganizationRoleRelationDynamicSqlSupport.roleId).toPropertyWhenPresent("roleId", record::getRoleId)
            .map(OrganizationRoleRelationDynamicSqlSupport.version).toPropertyWhenPresent("version", record::getVersion)
            .map(OrganizationRoleRelationDynamicSqlSupport.deleted).toPropertyWhenPresent("deleted", record::getDeleted)
            .map(OrganizationRoleRelationDynamicSqlSupport.creatorId).toPropertyWhenPresent("creatorId", record::getCreatorId)
            .map(OrganizationRoleRelationDynamicSqlSupport.createTime).toPropertyWhenPresent("createTime", record::getCreateTime)
            .map(OrganizationRoleRelationDynamicSqlSupport.lastModifierId).toPropertyWhenPresent("lastModifierId", record::getLastModifierId)
            .map(OrganizationRoleRelationDynamicSqlSupport.lastModifyTime).toPropertyWhenPresent("lastModifyTime", record::getLastModifyTime)
            .map(OrganizationRoleRelationDynamicSqlSupport.tenantId).toPropertyWhenPresent("tenantId", record::getTenantId)
        );
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default Optional<OrganizationRoleRelation> selectOne(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectOne(this::selectOne, selectList, OrganizationRoleRelationDynamicSqlSupport.organizationRoleRelation, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default List<OrganizationRoleRelation> select(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectList(this::selectMany, selectList, OrganizationRoleRelationDynamicSqlSupport.organizationRoleRelation, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default List<OrganizationRoleRelation> selectDistinct(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectDistinct(this::selectMany, selectList, OrganizationRoleRelationDynamicSqlSupport.organizationRoleRelation, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int update(UpdateDSLCompleter completer) {
        return MyBatis3Utils.update(this::update, OrganizationRoleRelationDynamicSqlSupport.organizationRoleRelation, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    static UpdateDSL<UpdateModel> updateAllColumns(OrganizationRoleRelation record, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(OrganizationRoleRelationDynamicSqlSupport.orgId).equalTo(record::getOrgId)
                .set(OrganizationRoleRelationDynamicSqlSupport.roleId).equalTo(record::getRoleId)
                .set(OrganizationRoleRelationDynamicSqlSupport.version).equalTo(record::getVersion)
                .set(OrganizationRoleRelationDynamicSqlSupport.deleted).equalTo(record::getDeleted)
                .set(OrganizationRoleRelationDynamicSqlSupport.creatorId).equalTo(record::getCreatorId)
                .set(OrganizationRoleRelationDynamicSqlSupport.createTime).equalTo(record::getCreateTime)
                .set(OrganizationRoleRelationDynamicSqlSupport.lastModifierId).equalTo(record::getLastModifierId)
                .set(OrganizationRoleRelationDynamicSqlSupport.lastModifyTime).equalTo(record::getLastModifyTime)
                .set(OrganizationRoleRelationDynamicSqlSupport.tenantId).equalTo(record::getTenantId);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    static UpdateDSL<UpdateModel> updateSelectiveColumns(OrganizationRoleRelation record, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(OrganizationRoleRelationDynamicSqlSupport.orgId).equalToWhenPresent(record::getOrgId)
                .set(OrganizationRoleRelationDynamicSqlSupport.roleId).equalToWhenPresent(record::getRoleId)
                .set(OrganizationRoleRelationDynamicSqlSupport.version).equalToWhenPresent(record::getVersion)
                .set(OrganizationRoleRelationDynamicSqlSupport.deleted).equalToWhenPresent(record::getDeleted)
                .set(OrganizationRoleRelationDynamicSqlSupport.creatorId).equalToWhenPresent(record::getCreatorId)
                .set(OrganizationRoleRelationDynamicSqlSupport.createTime).equalToWhenPresent(record::getCreateTime)
                .set(OrganizationRoleRelationDynamicSqlSupport.lastModifierId).equalToWhenPresent(record::getLastModifierId)
                .set(OrganizationRoleRelationDynamicSqlSupport.lastModifyTime).equalToWhenPresent(record::getLastModifyTime)
                .set(OrganizationRoleRelationDynamicSqlSupport.tenantId).equalToWhenPresent(record::getTenantId);
    }
}