package com.op.admin.mapper;

import com.op.admin.entity.OrganizationMenuRelation;
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
public interface OrganizationMenuRelationMapper {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    BasicColumn[] selectList = BasicColumn.columnList(OrganizationMenuRelationDynamicSqlSupport.orgId, OrganizationMenuRelationDynamicSqlSupport.menuId, OrganizationMenuRelationDynamicSqlSupport.version, OrganizationMenuRelationDynamicSqlSupport.deleted, OrganizationMenuRelationDynamicSqlSupport.creatorId, OrganizationMenuRelationDynamicSqlSupport.createTime, OrganizationMenuRelationDynamicSqlSupport.lastModifierId, OrganizationMenuRelationDynamicSqlSupport.lastModifyTime, OrganizationMenuRelationDynamicSqlSupport.tenantId);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type = SqlProviderAdapter.class, method = "select")
    long count(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @DeleteProvider(type = SqlProviderAdapter.class, method = "delete")
    int delete(DeleteStatementProvider deleteStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @InsertProvider(type = SqlProviderAdapter.class, method = "insert")
    int insert(InsertStatementProvider<OrganizationMenuRelation> insertStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type = SqlProviderAdapter.class, method = "select")
    @ResultMap("OrganizationMenuRelationResult")
    Optional<OrganizationMenuRelation> selectOne(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type = SqlProviderAdapter.class, method = "select")
    @Results(id = "OrganizationMenuRelationResult", value = {
            @Result(column = "org_id", property = "orgId", jdbcType = JdbcType.INTEGER),
            @Result(column = "menu_id", property = "menuId", jdbcType = JdbcType.INTEGER),
            @Result(column = "version", property = "version", jdbcType = JdbcType.INTEGER),
            @Result(column = "deleted", property = "deleted", jdbcType = JdbcType.BIT),
            @Result(column = "creator_id", property = "creatorId", jdbcType = JdbcType.INTEGER),
            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "last_modifier_id", property = "lastModifierId", jdbcType = JdbcType.INTEGER),
            @Result(column = "last_modify_time", property = "lastModifyTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "tenant_id", property = "tenantId", jdbcType = JdbcType.VARCHAR)
    })
    List<OrganizationMenuRelation> selectMany(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @UpdateProvider(type = SqlProviderAdapter.class, method = "update")
    int update(UpdateStatementProvider updateStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default long count(CountDSLCompleter completer) {
        return MyBatis3Utils.countFrom(this::count, OrganizationMenuRelationDynamicSqlSupport.organizationMenuRelation, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int delete(DeleteDSLCompleter completer) {
        return MyBatis3Utils.deleteFrom(this::delete, OrganizationMenuRelationDynamicSqlSupport.organizationMenuRelation, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int insert(OrganizationMenuRelation record) {
        return MyBatis3Utils.insert(this::insert, record, OrganizationMenuRelationDynamicSqlSupport.organizationMenuRelation, c ->
                c.map(OrganizationMenuRelationDynamicSqlSupport.orgId).toProperty("orgId")
                        .map(OrganizationMenuRelationDynamicSqlSupport.menuId).toProperty("menuId")
                        .map(OrganizationMenuRelationDynamicSqlSupport.version).toProperty("version")
                        .map(OrganizationMenuRelationDynamicSqlSupport.deleted).toProperty("deleted")
                        .map(OrganizationMenuRelationDynamicSqlSupport.creatorId).toProperty("creatorId")
                        .map(OrganizationMenuRelationDynamicSqlSupport.createTime).toProperty("createTime")
                        .map(OrganizationMenuRelationDynamicSqlSupport.lastModifierId).toProperty("lastModifierId")
                        .map(OrganizationMenuRelationDynamicSqlSupport.lastModifyTime).toProperty("lastModifyTime")
                        .map(OrganizationMenuRelationDynamicSqlSupport.tenantId).toProperty("tenantId")
        );
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int insertSelective(OrganizationMenuRelation record) {
        return MyBatis3Utils.insert(this::insert, record, OrganizationMenuRelationDynamicSqlSupport.organizationMenuRelation, c ->
                c.map(OrganizationMenuRelationDynamicSqlSupport.orgId).toPropertyWhenPresent("orgId", record::getOrgId)
                        .map(OrganizationMenuRelationDynamicSqlSupport.menuId).toPropertyWhenPresent("menuId", record::getMenuId)
                        .map(OrganizationMenuRelationDynamicSqlSupport.version).toPropertyWhenPresent("version", record::getVersion)
                        .map(OrganizationMenuRelationDynamicSqlSupport.deleted).toPropertyWhenPresent("deleted", record::getDeleted)
                        .map(OrganizationMenuRelationDynamicSqlSupport.creatorId).toPropertyWhenPresent("creatorId", record::getCreatorId)
                        .map(OrganizationMenuRelationDynamicSqlSupport.createTime).toPropertyWhenPresent("createTime", record::getCreateTime)
                        .map(OrganizationMenuRelationDynamicSqlSupport.lastModifierId).toPropertyWhenPresent("lastModifierId", record::getLastModifierId)
                        .map(OrganizationMenuRelationDynamicSqlSupport.lastModifyTime).toPropertyWhenPresent("lastModifyTime", record::getLastModifyTime)
                        .map(OrganizationMenuRelationDynamicSqlSupport.tenantId).toPropertyWhenPresent("tenantId", record::getTenantId)
        );
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default Optional<OrganizationMenuRelation> selectOne(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectOne(this::selectOne, selectList, OrganizationMenuRelationDynamicSqlSupport.organizationMenuRelation, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default List<OrganizationMenuRelation> select(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectList(this::selectMany, selectList, OrganizationMenuRelationDynamicSqlSupport.organizationMenuRelation, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default List<OrganizationMenuRelation> selectDistinct(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectDistinct(this::selectMany, selectList, OrganizationMenuRelationDynamicSqlSupport.organizationMenuRelation, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int update(UpdateDSLCompleter completer) {
        return MyBatis3Utils.update(this::update, OrganizationMenuRelationDynamicSqlSupport.organizationMenuRelation, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    static UpdateDSL<UpdateModel> updateAllColumns(OrganizationMenuRelation record, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(OrganizationMenuRelationDynamicSqlSupport.orgId).equalTo(record::getOrgId)
                .set(OrganizationMenuRelationDynamicSqlSupport.menuId).equalTo(record::getMenuId)
                .set(OrganizationMenuRelationDynamicSqlSupport.version).equalTo(record::getVersion)
                .set(OrganizationMenuRelationDynamicSqlSupport.deleted).equalTo(record::getDeleted)
                .set(OrganizationMenuRelationDynamicSqlSupport.creatorId).equalTo(record::getCreatorId)
                .set(OrganizationMenuRelationDynamicSqlSupport.createTime).equalTo(record::getCreateTime)
                .set(OrganizationMenuRelationDynamicSqlSupport.lastModifierId).equalTo(record::getLastModifierId)
                .set(OrganizationMenuRelationDynamicSqlSupport.lastModifyTime).equalTo(record::getLastModifyTime)
                .set(OrganizationMenuRelationDynamicSqlSupport.tenantId).equalTo(record::getTenantId);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    static UpdateDSL<UpdateModel> updateSelectiveColumns(OrganizationMenuRelation record, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(OrganizationMenuRelationDynamicSqlSupport.orgId).equalToWhenPresent(record::getOrgId)
                .set(OrganizationMenuRelationDynamicSqlSupport.menuId).equalToWhenPresent(record::getMenuId)
                .set(OrganizationMenuRelationDynamicSqlSupport.version).equalToWhenPresent(record::getVersion)
                .set(OrganizationMenuRelationDynamicSqlSupport.deleted).equalToWhenPresent(record::getDeleted)
                .set(OrganizationMenuRelationDynamicSqlSupport.creatorId).equalToWhenPresent(record::getCreatorId)
                .set(OrganizationMenuRelationDynamicSqlSupport.createTime).equalToWhenPresent(record::getCreateTime)
                .set(OrganizationMenuRelationDynamicSqlSupport.lastModifierId).equalToWhenPresent(record::getLastModifierId)
                .set(OrganizationMenuRelationDynamicSqlSupport.lastModifyTime).equalToWhenPresent(record::getLastModifyTime)
                .set(OrganizationMenuRelationDynamicSqlSupport.tenantId).equalToWhenPresent(record::getTenantId);
    }
}