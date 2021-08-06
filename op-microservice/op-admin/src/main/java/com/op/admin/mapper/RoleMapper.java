package com.op.admin.mapper;

import com.op.admin.entity.Role;
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

import static com.op.admin.mapper.RoleDynamicSqlSupport.*;
import static org.mybatis.dynamic.sql.SqlBuilder.isEqualTo;

/**
 * @author Mybatis Generator
 * @date 2021/08/06 05:58
 */
@Mapper
public interface RoleMapper {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    BasicColumn[] selectList = BasicColumn.columnList(id, roleName, roleCode, roleDesc, status, roleNo, userCount, version, deleted, creatorId, createTime, lastModifierId, lastModifyTime, tenantId);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type = SqlProviderAdapter.class, method = "select")
    long count(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @DeleteProvider(type = SqlProviderAdapter.class, method = "delete")
    int delete(DeleteStatementProvider deleteStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @InsertProvider(type = SqlProviderAdapter.class, method = "insert")
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "record.id", before = false, resultType = Integer.class)
    int insert(InsertStatementProvider<Role> insertStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type = SqlProviderAdapter.class, method = "select")
    @ResultMap("RoleResult")
    Optional<Role> selectOne(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type = SqlProviderAdapter.class, method = "select")
    @Results(id = "RoleResult", value = {
            @Result(column = "id", property = "id", jdbcType = JdbcType.INTEGER, id = true),
            @Result(column = "role_name", property = "roleName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "role_code", property = "roleCode", jdbcType = JdbcType.VARCHAR),
            @Result(column = "role_desc", property = "roleDesc", jdbcType = JdbcType.VARCHAR),
            @Result(column = "status", property = "status", jdbcType = JdbcType.INTEGER),
            @Result(column = "role_no", property = "roleNo", jdbcType = JdbcType.INTEGER),
            @Result(column = "user_count", property = "userCount", jdbcType = JdbcType.INTEGER),
            @Result(column = "version", property = "version", jdbcType = JdbcType.INTEGER),
            @Result(column = "deleted", property = "deleted", jdbcType = JdbcType.BIT),
            @Result(column = "creator_id", property = "creatorId", jdbcType = JdbcType.INTEGER),
            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "last_modifier_id", property = "lastModifierId", jdbcType = JdbcType.INTEGER),
            @Result(column = "last_modify_time", property = "lastModifyTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "tenant_id", property = "tenantId", jdbcType = JdbcType.VARCHAR)
    })
    List<Role> selectMany(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @UpdateProvider(type = SqlProviderAdapter.class, method = "update")
    int update(UpdateStatementProvider updateStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default long count(CountDSLCompleter completer) {
        return MyBatis3Utils.countFrom(this::count, role, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int delete(DeleteDSLCompleter completer) {
        return MyBatis3Utils.deleteFrom(this::delete, role, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int deleteByPrimaryKey(Integer id_) {
        return delete(c ->
                c.where(id, isEqualTo(id_))
        );
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int insert(Role record) {
        return MyBatis3Utils.insert(this::insert, record, role, c ->
                c.map(roleName).toProperty("roleName")
                        .map(roleCode).toProperty("roleCode")
                        .map(roleDesc).toProperty("roleDesc")
                        .map(status).toProperty("status")
                        .map(roleNo).toProperty("roleNo")
                        .map(userCount).toProperty("userCount")
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
    default int insertSelective(Role record) {
        return MyBatis3Utils.insert(this::insert, record, role, c ->
                c.map(roleName).toPropertyWhenPresent("roleName", record::getRoleName)
                        .map(roleCode).toPropertyWhenPresent("roleCode", record::getRoleCode)
                        .map(roleDesc).toPropertyWhenPresent("roleDesc", record::getRoleDesc)
                        .map(status).toPropertyWhenPresent("status", record::getStatus)
                        .map(roleNo).toPropertyWhenPresent("roleNo", record::getRoleNo)
                        .map(userCount).toPropertyWhenPresent("userCount", record::getUserCount)
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
    default Optional<Role> selectOne(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectOne(this::selectOne, selectList, role, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default List<Role> select(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectList(this::selectMany, selectList, role, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default List<Role> selectDistinct(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectDistinct(this::selectMany, selectList, role, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default Optional<Role> selectByPrimaryKey(Integer id_) {
        return selectOne(c ->
                c.where(id, isEqualTo(id_))
        );
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int update(UpdateDSLCompleter completer) {
        return MyBatis3Utils.update(this::update, role, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    static UpdateDSL<UpdateModel> updateAllColumns(Role record, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(roleName).equalTo(record::getRoleName)
                .set(roleCode).equalTo(record::getRoleCode)
                .set(roleDesc).equalTo(record::getRoleDesc)
                .set(status).equalTo(record::getStatus)
                .set(roleNo).equalTo(record::getRoleNo)
                .set(userCount).equalTo(record::getUserCount)
                .set(version).equalTo(record::getVersion)
                .set(deleted).equalTo(record::getDeleted)
                .set(creatorId).equalTo(record::getCreatorId)
                .set(createTime).equalTo(record::getCreateTime)
                .set(lastModifierId).equalTo(record::getLastModifierId)
                .set(lastModifyTime).equalTo(record::getLastModifyTime)
                .set(tenantId).equalTo(record::getTenantId);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    static UpdateDSL<UpdateModel> updateSelectiveColumns(Role record, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(roleName).equalToWhenPresent(record::getRoleName)
                .set(roleCode).equalToWhenPresent(record::getRoleCode)
                .set(roleDesc).equalToWhenPresent(record::getRoleDesc)
                .set(status).equalToWhenPresent(record::getStatus)
                .set(roleNo).equalToWhenPresent(record::getRoleNo)
                .set(userCount).equalToWhenPresent(record::getUserCount)
                .set(version).equalToWhenPresent(record::getVersion)
                .set(deleted).equalToWhenPresent(record::getDeleted)
                .set(creatorId).equalToWhenPresent(record::getCreatorId)
                .set(createTime).equalToWhenPresent(record::getCreateTime)
                .set(lastModifierId).equalToWhenPresent(record::getLastModifierId)
                .set(lastModifyTime).equalToWhenPresent(record::getLastModifyTime)
                .set(tenantId).equalToWhenPresent(record::getTenantId);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int updateByPrimaryKey(Role record) {
        return update(c ->
                c.set(roleName).equalTo(record::getRoleName)
                        .set(roleCode).equalTo(record::getRoleCode)
                        .set(roleDesc).equalTo(record::getRoleDesc)
                        .set(status).equalTo(record::getStatus)
                        .set(roleNo).equalTo(record::getRoleNo)
                        .set(userCount).equalTo(record::getUserCount)
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
    default int updateByPrimaryKeySelective(Role record) {
        return update(c ->
                c.set(roleName).equalToWhenPresent(record::getRoleName)
                        .set(roleCode).equalToWhenPresent(record::getRoleCode)
                        .set(roleDesc).equalToWhenPresent(record::getRoleDesc)
                        .set(status).equalToWhenPresent(record::getStatus)
                        .set(roleNo).equalToWhenPresent(record::getRoleNo)
                        .set(userCount).equalToWhenPresent(record::getUserCount)
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