package com.op.admin.mapper;

import static org.mybatis.dynamic.sql.SqlBuilder.*;

import com.op.admin.entity.ResourceAction;
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
 * @date 2021/06/24 03:12
 */
@Mapper
public interface ResourceActionMapper {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    BasicColumn[] selectList = BasicColumn.columnList(ResourceActionDynamicSqlSupport.id, ResourceActionDynamicSqlSupport.resourceId, ResourceActionDynamicSqlSupport.actionName, ResourceActionDynamicSqlSupport.actionPath, ResourceActionDynamicSqlSupport.actionDesc, ResourceActionDynamicSqlSupport.actionNo, ResourceActionDynamicSqlSupport.permissionName, ResourceActionDynamicSqlSupport.version, ResourceActionDynamicSqlSupport.deleted, ResourceActionDynamicSqlSupport.creatorId, ResourceActionDynamicSqlSupport.createTime, ResourceActionDynamicSqlSupport.lastModifierId, ResourceActionDynamicSqlSupport.lastModifyTime, ResourceActionDynamicSqlSupport.tenantId);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    long count(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @DeleteProvider(type=SqlProviderAdapter.class, method="delete")
    int delete(DeleteStatementProvider deleteStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @InsertProvider(type=SqlProviderAdapter.class, method="insert")
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="record.id", before=false, resultType=Integer.class)
    int insert(InsertStatementProvider<ResourceAction> insertStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("ResourceActionResult")
    Optional<ResourceAction> selectOne(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results(id="ResourceActionResult", value = {
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="resource_id", property="resourceId", jdbcType=JdbcType.INTEGER),
        @Result(column="action_name", property="actionName", jdbcType=JdbcType.VARCHAR),
        @Result(column="action_path", property="actionPath", jdbcType=JdbcType.VARCHAR),
        @Result(column="action_desc", property="actionDesc", jdbcType=JdbcType.VARCHAR),
        @Result(column="action_no", property="actionNo", jdbcType=JdbcType.INTEGER),
        @Result(column="permission_name", property="permissionName", jdbcType=JdbcType.VARCHAR),
        @Result(column="version", property="version", jdbcType=JdbcType.INTEGER),
        @Result(column="deleted", property="deleted", jdbcType=JdbcType.BIT),
        @Result(column="creator_id", property="creatorId", jdbcType=JdbcType.INTEGER),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="last_modifier_id", property="lastModifierId", jdbcType=JdbcType.INTEGER),
        @Result(column="last_modify_time", property="lastModifyTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="tenant_id", property="tenantId", jdbcType=JdbcType.VARCHAR)
    })
    List<ResourceAction> selectMany(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @UpdateProvider(type=SqlProviderAdapter.class, method="update")
    int update(UpdateStatementProvider updateStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default long count(CountDSLCompleter completer) {
        return MyBatis3Utils.countFrom(this::count, ResourceActionDynamicSqlSupport.resourceAction, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int delete(DeleteDSLCompleter completer) {
        return MyBatis3Utils.deleteFrom(this::delete, ResourceActionDynamicSqlSupport.resourceAction, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int deleteByPrimaryKey(Integer id_) {
        return delete(c -> 
            c.where(ResourceActionDynamicSqlSupport.id, isEqualTo(id_))
        );
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int insert(ResourceAction record) {
        return MyBatis3Utils.insert(this::insert, record, ResourceActionDynamicSqlSupport.resourceAction, c ->
            c.map(ResourceActionDynamicSqlSupport.resourceId).toProperty("resourceId")
            .map(ResourceActionDynamicSqlSupport.actionName).toProperty("actionName")
            .map(ResourceActionDynamicSqlSupport.actionPath).toProperty("actionPath")
            .map(ResourceActionDynamicSqlSupport.actionDesc).toProperty("actionDesc")
            .map(ResourceActionDynamicSqlSupport.actionNo).toProperty("actionNo")
            .map(ResourceActionDynamicSqlSupport.permissionName).toProperty("permissionName")
            .map(ResourceActionDynamicSqlSupport.version).toProperty("version")
            .map(ResourceActionDynamicSqlSupport.deleted).toProperty("deleted")
            .map(ResourceActionDynamicSqlSupport.creatorId).toProperty("creatorId")
            .map(ResourceActionDynamicSqlSupport.createTime).toProperty("createTime")
            .map(ResourceActionDynamicSqlSupport.lastModifierId).toProperty("lastModifierId")
            .map(ResourceActionDynamicSqlSupport.lastModifyTime).toProperty("lastModifyTime")
            .map(ResourceActionDynamicSqlSupport.tenantId).toProperty("tenantId")
        );
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int insertSelective(ResourceAction record) {
        return MyBatis3Utils.insert(this::insert, record, ResourceActionDynamicSqlSupport.resourceAction, c ->
            c.map(ResourceActionDynamicSqlSupport.resourceId).toPropertyWhenPresent("resourceId", record::getResourceId)
            .map(ResourceActionDynamicSqlSupport.actionName).toPropertyWhenPresent("actionName", record::getActionName)
            .map(ResourceActionDynamicSqlSupport.actionPath).toPropertyWhenPresent("actionPath", record::getActionPath)
            .map(ResourceActionDynamicSqlSupport.actionDesc).toPropertyWhenPresent("actionDesc", record::getActionDesc)
            .map(ResourceActionDynamicSqlSupport.actionNo).toPropertyWhenPresent("actionNo", record::getActionNo)
            .map(ResourceActionDynamicSqlSupport.permissionName).toPropertyWhenPresent("permissionName", record::getPermissionName)
            .map(ResourceActionDynamicSqlSupport.version).toPropertyWhenPresent("version", record::getVersion)
            .map(ResourceActionDynamicSqlSupport.deleted).toPropertyWhenPresent("deleted", record::getDeleted)
            .map(ResourceActionDynamicSqlSupport.creatorId).toPropertyWhenPresent("creatorId", record::getCreatorId)
            .map(ResourceActionDynamicSqlSupport.createTime).toPropertyWhenPresent("createTime", record::getCreateTime)
            .map(ResourceActionDynamicSqlSupport.lastModifierId).toPropertyWhenPresent("lastModifierId", record::getLastModifierId)
            .map(ResourceActionDynamicSqlSupport.lastModifyTime).toPropertyWhenPresent("lastModifyTime", record::getLastModifyTime)
            .map(ResourceActionDynamicSqlSupport.tenantId).toPropertyWhenPresent("tenantId", record::getTenantId)
        );
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default Optional<ResourceAction> selectOne(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectOne(this::selectOne, selectList, ResourceActionDynamicSqlSupport.resourceAction, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default List<ResourceAction> select(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectList(this::selectMany, selectList, ResourceActionDynamicSqlSupport.resourceAction, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default List<ResourceAction> selectDistinct(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectDistinct(this::selectMany, selectList, ResourceActionDynamicSqlSupport.resourceAction, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default Optional<ResourceAction> selectByPrimaryKey(Integer id_) {
        return selectOne(c ->
            c.where(ResourceActionDynamicSqlSupport.id, isEqualTo(id_))
        );
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int update(UpdateDSLCompleter completer) {
        return MyBatis3Utils.update(this::update, ResourceActionDynamicSqlSupport.resourceAction, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    static UpdateDSL<UpdateModel> updateAllColumns(ResourceAction record, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(ResourceActionDynamicSqlSupport.resourceId).equalTo(record::getResourceId)
                .set(ResourceActionDynamicSqlSupport.actionName).equalTo(record::getActionName)
                .set(ResourceActionDynamicSqlSupport.actionPath).equalTo(record::getActionPath)
                .set(ResourceActionDynamicSqlSupport.actionDesc).equalTo(record::getActionDesc)
                .set(ResourceActionDynamicSqlSupport.actionNo).equalTo(record::getActionNo)
                .set(ResourceActionDynamicSqlSupport.permissionName).equalTo(record::getPermissionName)
                .set(ResourceActionDynamicSqlSupport.version).equalTo(record::getVersion)
                .set(ResourceActionDynamicSqlSupport.deleted).equalTo(record::getDeleted)
                .set(ResourceActionDynamicSqlSupport.creatorId).equalTo(record::getCreatorId)
                .set(ResourceActionDynamicSqlSupport.createTime).equalTo(record::getCreateTime)
                .set(ResourceActionDynamicSqlSupport.lastModifierId).equalTo(record::getLastModifierId)
                .set(ResourceActionDynamicSqlSupport.lastModifyTime).equalTo(record::getLastModifyTime)
                .set(ResourceActionDynamicSqlSupport.tenantId).equalTo(record::getTenantId);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    static UpdateDSL<UpdateModel> updateSelectiveColumns(ResourceAction record, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(ResourceActionDynamicSqlSupport.resourceId).equalToWhenPresent(record::getResourceId)
                .set(ResourceActionDynamicSqlSupport.actionName).equalToWhenPresent(record::getActionName)
                .set(ResourceActionDynamicSqlSupport.actionPath).equalToWhenPresent(record::getActionPath)
                .set(ResourceActionDynamicSqlSupport.actionDesc).equalToWhenPresent(record::getActionDesc)
                .set(ResourceActionDynamicSqlSupport.actionNo).equalToWhenPresent(record::getActionNo)
                .set(ResourceActionDynamicSqlSupport.permissionName).equalToWhenPresent(record::getPermissionName)
                .set(ResourceActionDynamicSqlSupport.version).equalToWhenPresent(record::getVersion)
                .set(ResourceActionDynamicSqlSupport.deleted).equalToWhenPresent(record::getDeleted)
                .set(ResourceActionDynamicSqlSupport.creatorId).equalToWhenPresent(record::getCreatorId)
                .set(ResourceActionDynamicSqlSupport.createTime).equalToWhenPresent(record::getCreateTime)
                .set(ResourceActionDynamicSqlSupport.lastModifierId).equalToWhenPresent(record::getLastModifierId)
                .set(ResourceActionDynamicSqlSupport.lastModifyTime).equalToWhenPresent(record::getLastModifyTime)
                .set(ResourceActionDynamicSqlSupport.tenantId).equalToWhenPresent(record::getTenantId);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int updateByPrimaryKey(ResourceAction record) {
        return update(c ->
            c.set(ResourceActionDynamicSqlSupport.resourceId).equalTo(record::getResourceId)
            .set(ResourceActionDynamicSqlSupport.actionName).equalTo(record::getActionName)
            .set(ResourceActionDynamicSqlSupport.actionPath).equalTo(record::getActionPath)
            .set(ResourceActionDynamicSqlSupport.actionDesc).equalTo(record::getActionDesc)
            .set(ResourceActionDynamicSqlSupport.actionNo).equalTo(record::getActionNo)
            .set(ResourceActionDynamicSqlSupport.permissionName).equalTo(record::getPermissionName)
            .set(ResourceActionDynamicSqlSupport.version).equalTo(record::getVersion)
            .set(ResourceActionDynamicSqlSupport.deleted).equalTo(record::getDeleted)
            .set(ResourceActionDynamicSqlSupport.creatorId).equalTo(record::getCreatorId)
            .set(ResourceActionDynamicSqlSupport.createTime).equalTo(record::getCreateTime)
            .set(ResourceActionDynamicSqlSupport.lastModifierId).equalTo(record::getLastModifierId)
            .set(ResourceActionDynamicSqlSupport.lastModifyTime).equalTo(record::getLastModifyTime)
            .set(ResourceActionDynamicSqlSupport.tenantId).equalTo(record::getTenantId)
            .where(ResourceActionDynamicSqlSupport.id, isEqualTo(record::getId))
        );
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int updateByPrimaryKeySelective(ResourceAction record) {
        return update(c ->
            c.set(ResourceActionDynamicSqlSupport.resourceId).equalToWhenPresent(record::getResourceId)
            .set(ResourceActionDynamicSqlSupport.actionName).equalToWhenPresent(record::getActionName)
            .set(ResourceActionDynamicSqlSupport.actionPath).equalToWhenPresent(record::getActionPath)
            .set(ResourceActionDynamicSqlSupport.actionDesc).equalToWhenPresent(record::getActionDesc)
            .set(ResourceActionDynamicSqlSupport.actionNo).equalToWhenPresent(record::getActionNo)
            .set(ResourceActionDynamicSqlSupport.permissionName).equalToWhenPresent(record::getPermissionName)
            .set(ResourceActionDynamicSqlSupport.version).equalToWhenPresent(record::getVersion)
            .set(ResourceActionDynamicSqlSupport.deleted).equalToWhenPresent(record::getDeleted)
            .set(ResourceActionDynamicSqlSupport.creatorId).equalToWhenPresent(record::getCreatorId)
            .set(ResourceActionDynamicSqlSupport.createTime).equalToWhenPresent(record::getCreateTime)
            .set(ResourceActionDynamicSqlSupport.lastModifierId).equalToWhenPresent(record::getLastModifierId)
            .set(ResourceActionDynamicSqlSupport.lastModifyTime).equalToWhenPresent(record::getLastModifyTime)
            .set(ResourceActionDynamicSqlSupport.tenantId).equalToWhenPresent(record::getTenantId)
            .where(ResourceActionDynamicSqlSupport.id, isEqualTo(record::getId))
        );
    }
}