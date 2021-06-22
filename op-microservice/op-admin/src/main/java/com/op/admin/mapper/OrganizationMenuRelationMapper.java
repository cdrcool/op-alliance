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

import static com.op.admin.mapper.OrganizationMenuRelationDynamicSqlSupport.*;

/**
 * @author Mybatis Generator
 * @date 2021/06/18 11:20
 */
@Mapper
public interface OrganizationMenuRelationMapper {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    BasicColumn[] selectList = BasicColumn.columnList(orgId, menuId, version, deleted, creatorId, createTime, lastModifierId, lastModifyTime, tenantId);

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
        return MyBatis3Utils.countFrom(this::count, organizationMenuRelation, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int delete(DeleteDSLCompleter completer) {
        return MyBatis3Utils.deleteFrom(this::delete, organizationMenuRelation, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int insert(OrganizationMenuRelation record) {
        return MyBatis3Utils.insert(this::insert, record, organizationMenuRelation, c ->
                c.map(orgId).toProperty("orgId")
                        .map(menuId).toProperty("menuId")
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
    default int insertSelective(OrganizationMenuRelation record) {
        return MyBatis3Utils.insert(this::insert, record, organizationMenuRelation, c ->
                c.map(orgId).toPropertyWhenPresent("orgId", record::getOrgId)
                        .map(menuId).toPropertyWhenPresent("menuId", record::getMenuId)
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
    default Optional<OrganizationMenuRelation> selectOne(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectOne(this::selectOne, selectList, organizationMenuRelation, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default List<OrganizationMenuRelation> select(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectList(this::selectMany, selectList, organizationMenuRelation, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default List<OrganizationMenuRelation> selectDistinct(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectDistinct(this::selectMany, selectList, organizationMenuRelation, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int update(UpdateDSLCompleter completer) {
        return MyBatis3Utils.update(this::update, organizationMenuRelation, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    static UpdateDSL<UpdateModel> updateAllColumns(OrganizationMenuRelation record, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(orgId).equalTo(record::getOrgId)
                .set(menuId).equalTo(record::getMenuId)
                .set(version).equalTo(record::getVersion)
                .set(deleted).equalTo(record::getDeleted)
                .set(creatorId).equalTo(record::getCreatorId)
                .set(createTime).equalTo(record::getCreateTime)
                .set(lastModifierId).equalTo(record::getLastModifierId)
                .set(lastModifyTime).equalTo(record::getLastModifyTime)
                .set(tenantId).equalTo(record::getTenantId);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    static UpdateDSL<UpdateModel> updateSelectiveColumns(OrganizationMenuRelation record, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(orgId).equalToWhenPresent(record::getOrgId)
                .set(menuId).equalToWhenPresent(record::getMenuId)
                .set(version).equalToWhenPresent(record::getVersion)
                .set(deleted).equalToWhenPresent(record::getDeleted)
                .set(creatorId).equalToWhenPresent(record::getCreatorId)
                .set(createTime).equalToWhenPresent(record::getCreateTime)
                .set(lastModifierId).equalToWhenPresent(record::getLastModifierId)
                .set(lastModifyTime).equalToWhenPresent(record::getLastModifyTime)
                .set(tenantId).equalToWhenPresent(record::getTenantId);
    }
}