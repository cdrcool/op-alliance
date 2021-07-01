package com.op.admin.mapper;

import static org.mybatis.dynamic.sql.SqlBuilder.*;

import com.op.admin.entity.Role;
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
public interface RoleMapper {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    BasicColumn[] selectList = BasicColumn.columnList(RoleDynamicSqlSupport.id, RoleDynamicSqlSupport.roleName, RoleDynamicSqlSupport.roleCode, RoleDynamicSqlSupport.roleDesc, RoleDynamicSqlSupport.status, RoleDynamicSqlSupport.roleNo, RoleDynamicSqlSupport.userCount, RoleDynamicSqlSupport.version, RoleDynamicSqlSupport.deleted, RoleDynamicSqlSupport.creatorId, RoleDynamicSqlSupport.createTime, RoleDynamicSqlSupport.lastModifierId, RoleDynamicSqlSupport.lastModifyTime, RoleDynamicSqlSupport.tenantId);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    long count(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @DeleteProvider(type=SqlProviderAdapter.class, method="delete")
    int delete(DeleteStatementProvider deleteStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @InsertProvider(type=SqlProviderAdapter.class, method="insert")
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="record.id", before=false, resultType=Integer.class)
    int insert(InsertStatementProvider<Role> insertStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("RoleResult")
    Optional<Role> selectOne(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results(id="RoleResult", value = {
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="role_name", property="roleName", jdbcType=JdbcType.VARCHAR),
        @Result(column="role_code", property="roleCode", jdbcType=JdbcType.VARCHAR),
        @Result(column="role_desc", property="roleDesc", jdbcType=JdbcType.VARCHAR),
        @Result(column="status", property="status", jdbcType=JdbcType.INTEGER),
        @Result(column="role_no", property="roleNo", jdbcType=JdbcType.INTEGER),
        @Result(column="user_count", property="userCount", jdbcType=JdbcType.INTEGER),
        @Result(column="version", property="version", jdbcType=JdbcType.INTEGER),
        @Result(column="deleted", property="deleted", jdbcType=JdbcType.BIT),
        @Result(column="creator_id", property="creatorId", jdbcType=JdbcType.INTEGER),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="last_modifier_id", property="lastModifierId", jdbcType=JdbcType.INTEGER),
        @Result(column="last_modify_time", property="lastModifyTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="tenant_id", property="tenantId", jdbcType=JdbcType.VARCHAR)
    })
    List<Role> selectMany(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @UpdateProvider(type=SqlProviderAdapter.class, method="update")
    int update(UpdateStatementProvider updateStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default long count(CountDSLCompleter completer) {
        return MyBatis3Utils.countFrom(this::count, RoleDynamicSqlSupport.role, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int delete(DeleteDSLCompleter completer) {
        return MyBatis3Utils.deleteFrom(this::delete, RoleDynamicSqlSupport.role, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int deleteByPrimaryKey(Integer id_) {
        return delete(c -> 
            c.where(RoleDynamicSqlSupport.id, isEqualTo(id_))
        );
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int insert(Role record) {
        return MyBatis3Utils.insert(this::insert, record, RoleDynamicSqlSupport.role, c ->
            c.map(RoleDynamicSqlSupport.roleName).toProperty("roleName")
            .map(RoleDynamicSqlSupport.roleCode).toProperty("roleCode")
            .map(RoleDynamicSqlSupport.roleDesc).toProperty("roleDesc")
            .map(RoleDynamicSqlSupport.status).toProperty("status")
            .map(RoleDynamicSqlSupport.roleNo).toProperty("roleNo")
            .map(RoleDynamicSqlSupport.userCount).toProperty("userCount")
            .map(RoleDynamicSqlSupport.version).toProperty("version")
            .map(RoleDynamicSqlSupport.deleted).toProperty("deleted")
            .map(RoleDynamicSqlSupport.creatorId).toProperty("creatorId")
            .map(RoleDynamicSqlSupport.createTime).toProperty("createTime")
            .map(RoleDynamicSqlSupport.lastModifierId).toProperty("lastModifierId")
            .map(RoleDynamicSqlSupport.lastModifyTime).toProperty("lastModifyTime")
            .map(RoleDynamicSqlSupport.tenantId).toProperty("tenantId")
        );
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int insertSelective(Role record) {
        return MyBatis3Utils.insert(this::insert, record, RoleDynamicSqlSupport.role, c ->
            c.map(RoleDynamicSqlSupport.roleName).toPropertyWhenPresent("roleName", record::getRoleName)
            .map(RoleDynamicSqlSupport.roleCode).toPropertyWhenPresent("roleCode", record::getRoleCode)
            .map(RoleDynamicSqlSupport.roleDesc).toPropertyWhenPresent("roleDesc", record::getRoleDesc)
            .map(RoleDynamicSqlSupport.status).toPropertyWhenPresent("status", record::getStatus)
            .map(RoleDynamicSqlSupport.roleNo).toPropertyWhenPresent("roleNo", record::getRoleNo)
            .map(RoleDynamicSqlSupport.userCount).toPropertyWhenPresent("userCount", record::getUserCount)
            .map(RoleDynamicSqlSupport.version).toPropertyWhenPresent("version", record::getVersion)
            .map(RoleDynamicSqlSupport.deleted).toPropertyWhenPresent("deleted", record::getDeleted)
            .map(RoleDynamicSqlSupport.creatorId).toPropertyWhenPresent("creatorId", record::getCreatorId)
            .map(RoleDynamicSqlSupport.createTime).toPropertyWhenPresent("createTime", record::getCreateTime)
            .map(RoleDynamicSqlSupport.lastModifierId).toPropertyWhenPresent("lastModifierId", record::getLastModifierId)
            .map(RoleDynamicSqlSupport.lastModifyTime).toPropertyWhenPresent("lastModifyTime", record::getLastModifyTime)
            .map(RoleDynamicSqlSupport.tenantId).toPropertyWhenPresent("tenantId", record::getTenantId)
        );
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default Optional<Role> selectOne(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectOne(this::selectOne, selectList, RoleDynamicSqlSupport.role, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default List<Role> select(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectList(this::selectMany, selectList, RoleDynamicSqlSupport.role, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default List<Role> selectDistinct(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectDistinct(this::selectMany, selectList, RoleDynamicSqlSupport.role, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default Optional<Role> selectByPrimaryKey(Integer id_) {
        return selectOne(c ->
            c.where(RoleDynamicSqlSupport.id, isEqualTo(id_))
        );
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int update(UpdateDSLCompleter completer) {
        return MyBatis3Utils.update(this::update, RoleDynamicSqlSupport.role, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    static UpdateDSL<UpdateModel> updateAllColumns(Role record, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(RoleDynamicSqlSupport.roleName).equalTo(record::getRoleName)
                .set(RoleDynamicSqlSupport.roleCode).equalTo(record::getRoleCode)
                .set(RoleDynamicSqlSupport.roleDesc).equalTo(record::getRoleDesc)
                .set(RoleDynamicSqlSupport.status).equalTo(record::getStatus)
                .set(RoleDynamicSqlSupport.roleNo).equalTo(record::getRoleNo)
                .set(RoleDynamicSqlSupport.userCount).equalTo(record::getUserCount)
                .set(RoleDynamicSqlSupport.version).equalTo(record::getVersion)
                .set(RoleDynamicSqlSupport.deleted).equalTo(record::getDeleted)
                .set(RoleDynamicSqlSupport.creatorId).equalTo(record::getCreatorId)
                .set(RoleDynamicSqlSupport.createTime).equalTo(record::getCreateTime)
                .set(RoleDynamicSqlSupport.lastModifierId).equalTo(record::getLastModifierId)
                .set(RoleDynamicSqlSupport.lastModifyTime).equalTo(record::getLastModifyTime)
                .set(RoleDynamicSqlSupport.tenantId).equalTo(record::getTenantId);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    static UpdateDSL<UpdateModel> updateSelectiveColumns(Role record, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(RoleDynamicSqlSupport.roleName).equalToWhenPresent(record::getRoleName)
                .set(RoleDynamicSqlSupport.roleCode).equalToWhenPresent(record::getRoleCode)
                .set(RoleDynamicSqlSupport.roleDesc).equalToWhenPresent(record::getRoleDesc)
                .set(RoleDynamicSqlSupport.status).equalToWhenPresent(record::getStatus)
                .set(RoleDynamicSqlSupport.roleNo).equalToWhenPresent(record::getRoleNo)
                .set(RoleDynamicSqlSupport.userCount).equalToWhenPresent(record::getUserCount)
                .set(RoleDynamicSqlSupport.version).equalToWhenPresent(record::getVersion)
                .set(RoleDynamicSqlSupport.deleted).equalToWhenPresent(record::getDeleted)
                .set(RoleDynamicSqlSupport.creatorId).equalToWhenPresent(record::getCreatorId)
                .set(RoleDynamicSqlSupport.createTime).equalToWhenPresent(record::getCreateTime)
                .set(RoleDynamicSqlSupport.lastModifierId).equalToWhenPresent(record::getLastModifierId)
                .set(RoleDynamicSqlSupport.lastModifyTime).equalToWhenPresent(record::getLastModifyTime)
                .set(RoleDynamicSqlSupport.tenantId).equalToWhenPresent(record::getTenantId);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int updateByPrimaryKey(Role record) {
        return update(c ->
            c.set(RoleDynamicSqlSupport.roleName).equalTo(record::getRoleName)
            .set(RoleDynamicSqlSupport.roleCode).equalTo(record::getRoleCode)
            .set(RoleDynamicSqlSupport.roleDesc).equalTo(record::getRoleDesc)
            .set(RoleDynamicSqlSupport.status).equalTo(record::getStatus)
            .set(RoleDynamicSqlSupport.roleNo).equalTo(record::getRoleNo)
            .set(RoleDynamicSqlSupport.userCount).equalTo(record::getUserCount)
            .set(RoleDynamicSqlSupport.version).equalTo(record::getVersion)
            .set(RoleDynamicSqlSupport.deleted).equalTo(record::getDeleted)
            .set(RoleDynamicSqlSupport.creatorId).equalTo(record::getCreatorId)
            .set(RoleDynamicSqlSupport.createTime).equalTo(record::getCreateTime)
            .set(RoleDynamicSqlSupport.lastModifierId).equalTo(record::getLastModifierId)
            .set(RoleDynamicSqlSupport.lastModifyTime).equalTo(record::getLastModifyTime)
            .set(RoleDynamicSqlSupport.tenantId).equalTo(record::getTenantId)
            .where(RoleDynamicSqlSupport.id, isEqualTo(record::getId))
        );
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int updateByPrimaryKeySelective(Role record) {
        return update(c ->
            c.set(RoleDynamicSqlSupport.roleName).equalToWhenPresent(record::getRoleName)
            .set(RoleDynamicSqlSupport.roleCode).equalToWhenPresent(record::getRoleCode)
            .set(RoleDynamicSqlSupport.roleDesc).equalToWhenPresent(record::getRoleDesc)
            .set(RoleDynamicSqlSupport.status).equalToWhenPresent(record::getStatus)
            .set(RoleDynamicSqlSupport.roleNo).equalToWhenPresent(record::getRoleNo)
            .set(RoleDynamicSqlSupport.userCount).equalToWhenPresent(record::getUserCount)
            .set(RoleDynamicSqlSupport.version).equalToWhenPresent(record::getVersion)
            .set(RoleDynamicSqlSupport.deleted).equalToWhenPresent(record::getDeleted)
            .set(RoleDynamicSqlSupport.creatorId).equalToWhenPresent(record::getCreatorId)
            .set(RoleDynamicSqlSupport.createTime).equalToWhenPresent(record::getCreateTime)
            .set(RoleDynamicSqlSupport.lastModifierId).equalToWhenPresent(record::getLastModifierId)
            .set(RoleDynamicSqlSupport.lastModifyTime).equalToWhenPresent(record::getLastModifyTime)
            .set(RoleDynamicSqlSupport.tenantId).equalToWhenPresent(record::getTenantId)
            .where(RoleDynamicSqlSupport.id, isEqualTo(record::getId))
        );
    }
}