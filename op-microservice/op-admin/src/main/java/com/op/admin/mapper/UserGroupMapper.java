package com.op.admin.mapper;

import static com.op.admin.mapper.UserGroupDynamicSqlSupport.*;
import static org.mybatis.dynamic.sql.SqlBuilder.*;

import com.op.admin.entity.UserGroup;
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
 * @date 2021/06/17 11:26
 */
@Mapper
public interface UserGroupMapper {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    BasicColumn[] selectList = BasicColumn.columnList(id, groupName, groupDesc, groupNo, version, deleted, creatorId, createTime, lastModifierId, lastModifyTime, tenantId);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    long count(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @DeleteProvider(type=SqlProviderAdapter.class, method="delete")
    int delete(DeleteStatementProvider deleteStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @InsertProvider(type=SqlProviderAdapter.class, method="insert")
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="record.id", before=false, resultType=Integer.class)
    int insert(InsertStatementProvider<UserGroup> insertStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("UserGroupResult")
    Optional<UserGroup> selectOne(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results(id="UserGroupResult", value = {
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="group_name", property="groupName", jdbcType=JdbcType.VARCHAR),
        @Result(column="group_desc", property="groupDesc", jdbcType=JdbcType.VARCHAR),
        @Result(column="group_no", property="groupNo", jdbcType=JdbcType.INTEGER),
        @Result(column="version", property="version", jdbcType=JdbcType.INTEGER),
        @Result(column="deleted", property="deleted", jdbcType=JdbcType.BIT),
        @Result(column="creator_id", property="creatorId", jdbcType=JdbcType.INTEGER),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="last_modifier_id", property="lastModifierId", jdbcType=JdbcType.INTEGER),
        @Result(column="last_modify_time", property="lastModifyTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="tenant_id", property="tenantId", jdbcType=JdbcType.VARCHAR)
    })
    List<UserGroup> selectMany(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @UpdateProvider(type=SqlProviderAdapter.class, method="update")
    int update(UpdateStatementProvider updateStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default long count(CountDSLCompleter completer) {
        return MyBatis3Utils.countFrom(this::count, userGroup, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int delete(DeleteDSLCompleter completer) {
        return MyBatis3Utils.deleteFrom(this::delete, userGroup, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int deleteByPrimaryKey(Integer id_) {
        return delete(c -> 
            c.where(id, isEqualTo(id_))
        );
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int insert(UserGroup record) {
        return MyBatis3Utils.insert(this::insert, record, userGroup, c ->
            c.map(groupName).toProperty("groupName")
            .map(groupDesc).toProperty("groupDesc")
            .map(groupNo).toProperty("groupNo")
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
    default int insertSelective(UserGroup record) {
        return MyBatis3Utils.insert(this::insert, record, userGroup, c ->
            c.map(groupName).toPropertyWhenPresent("groupName", record::getGroupName)
            .map(groupDesc).toPropertyWhenPresent("groupDesc", record::getGroupDesc)
            .map(groupNo).toPropertyWhenPresent("groupNo", record::getGroupNo)
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
    default Optional<UserGroup> selectOne(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectOne(this::selectOne, selectList, userGroup, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default List<UserGroup> select(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectList(this::selectMany, selectList, userGroup, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default List<UserGroup> selectDistinct(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectDistinct(this::selectMany, selectList, userGroup, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default Optional<UserGroup> selectByPrimaryKey(Integer id_) {
        return selectOne(c ->
            c.where(id, isEqualTo(id_))
        );
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int update(UpdateDSLCompleter completer) {
        return MyBatis3Utils.update(this::update, userGroup, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    static UpdateDSL<UpdateModel> updateAllColumns(UserGroup record, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(groupName).equalTo(record::getGroupName)
                .set(groupDesc).equalTo(record::getGroupDesc)
                .set(groupNo).equalTo(record::getGroupNo)
                .set(version).equalTo(record::getVersion)
                .set(deleted).equalTo(record::getDeleted)
                .set(creatorId).equalTo(record::getCreatorId)
                .set(createTime).equalTo(record::getCreateTime)
                .set(lastModifierId).equalTo(record::getLastModifierId)
                .set(lastModifyTime).equalTo(record::getLastModifyTime)
                .set(tenantId).equalTo(record::getTenantId);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    static UpdateDSL<UpdateModel> updateSelectiveColumns(UserGroup record, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(groupName).equalToWhenPresent(record::getGroupName)
                .set(groupDesc).equalToWhenPresent(record::getGroupDesc)
                .set(groupNo).equalToWhenPresent(record::getGroupNo)
                .set(version).equalToWhenPresent(record::getVersion)
                .set(deleted).equalToWhenPresent(record::getDeleted)
                .set(creatorId).equalToWhenPresent(record::getCreatorId)
                .set(createTime).equalToWhenPresent(record::getCreateTime)
                .set(lastModifierId).equalToWhenPresent(record::getLastModifierId)
                .set(lastModifyTime).equalToWhenPresent(record::getLastModifyTime)
                .set(tenantId).equalToWhenPresent(record::getTenantId);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int updateByPrimaryKey(UserGroup record) {
        return update(c ->
            c.set(groupName).equalTo(record::getGroupName)
            .set(groupDesc).equalTo(record::getGroupDesc)
            .set(groupNo).equalTo(record::getGroupNo)
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
    default int updateByPrimaryKeySelective(UserGroup record) {
        return update(c ->
            c.set(groupName).equalToWhenPresent(record::getGroupName)
            .set(groupDesc).equalToWhenPresent(record::getGroupDesc)
            .set(groupNo).equalToWhenPresent(record::getGroupNo)
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