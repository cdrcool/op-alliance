package com.op.admin.mapper;

import static com.op.admin.mapper.UserDynamicSqlSupport.*;
import static org.mybatis.dynamic.sql.SqlBuilder.*;

import com.op.admin.entity.User;
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
 * @date 2021/08/18 10:31
 */
@Mapper
public interface UserMapper {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    BasicColumn[] selectList = BasicColumn.columnList(id, orgId, username, password, nickname, avatar, signature, phone, email, gender, birthday, status, userNo, version, deleted, creatorId, createTime, lastModifierId, lastModifyTime, tenantId);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    long count(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @DeleteProvider(type=SqlProviderAdapter.class, method="delete")
    int delete(DeleteStatementProvider deleteStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @InsertProvider(type=SqlProviderAdapter.class, method="insert")
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="record.id", before=false, resultType=Integer.class)
    int insert(InsertStatementProvider<User> insertStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("UserResult")
    Optional<User> selectOne(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results(id="UserResult", value = {
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="org_id", property="orgId", jdbcType=JdbcType.INTEGER),
        @Result(column="username", property="username", jdbcType=JdbcType.VARCHAR),
        @Result(column="password", property="password", jdbcType=JdbcType.VARCHAR),
        @Result(column="nickname", property="nickname", jdbcType=JdbcType.VARCHAR),
        @Result(column="avatar", property="avatar", jdbcType=JdbcType.VARCHAR),
        @Result(column="signature", property="signature", jdbcType=JdbcType.VARCHAR),
        @Result(column="phone", property="phone", jdbcType=JdbcType.VARCHAR),
        @Result(column="email", property="email", jdbcType=JdbcType.VARCHAR),
        @Result(column="gender", property="gender", jdbcType=JdbcType.INTEGER),
        @Result(column="birthday", property="birthday", jdbcType=JdbcType.DATE),
        @Result(column="status", property="status", jdbcType=JdbcType.INTEGER),
        @Result(column="user_no", property="userNo", jdbcType=JdbcType.INTEGER),
        @Result(column="version", property="version", jdbcType=JdbcType.INTEGER),
        @Result(column="deleted", property="deleted", jdbcType=JdbcType.BIT),
        @Result(column="creator_id", property="creatorId", jdbcType=JdbcType.INTEGER),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="last_modifier_id", property="lastModifierId", jdbcType=JdbcType.INTEGER),
        @Result(column="last_modify_time", property="lastModifyTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="tenant_id", property="tenantId", jdbcType=JdbcType.VARCHAR)
    })
    List<User> selectMany(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @UpdateProvider(type=SqlProviderAdapter.class, method="update")
    int update(UpdateStatementProvider updateStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default long count(CountDSLCompleter completer) {
        return MyBatis3Utils.countFrom(this::count, user, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int delete(DeleteDSLCompleter completer) {
        return MyBatis3Utils.deleteFrom(this::delete, user, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int deleteByPrimaryKey(Integer id_) {
        return delete(c -> 
            c.where(id, isEqualTo(id_))
        );
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int insert(User record) {
        return MyBatis3Utils.insert(this::insert, record, user, c ->
            c.map(orgId).toProperty("orgId")
            .map(username).toProperty("username")
            .map(password).toProperty("password")
            .map(nickname).toProperty("nickname")
            .map(avatar).toProperty("avatar")
            .map(signature).toProperty("signature")
            .map(phone).toProperty("phone")
            .map(email).toProperty("email")
            .map(gender).toProperty("gender")
            .map(birthday).toProperty("birthday")
            .map(status).toProperty("status")
            .map(userNo).toProperty("userNo")
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
    default int insertSelective(User record) {
        return MyBatis3Utils.insert(this::insert, record, user, c ->
            c.map(orgId).toPropertyWhenPresent("orgId", record::getOrgId)
            .map(username).toPropertyWhenPresent("username", record::getUsername)
            .map(password).toPropertyWhenPresent("password", record::getPassword)
            .map(nickname).toPropertyWhenPresent("nickname", record::getNickname)
            .map(avatar).toPropertyWhenPresent("avatar", record::getAvatar)
            .map(signature).toPropertyWhenPresent("signature", record::getSignature)
            .map(phone).toPropertyWhenPresent("phone", record::getPhone)
            .map(email).toPropertyWhenPresent("email", record::getEmail)
            .map(gender).toPropertyWhenPresent("gender", record::getGender)
            .map(birthday).toPropertyWhenPresent("birthday", record::getBirthday)
            .map(status).toPropertyWhenPresent("status", record::getStatus)
            .map(userNo).toPropertyWhenPresent("userNo", record::getUserNo)
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
    default Optional<User> selectOne(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectOne(this::selectOne, selectList, user, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default List<User> select(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectList(this::selectMany, selectList, user, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default List<User> selectDistinct(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectDistinct(this::selectMany, selectList, user, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default Optional<User> selectByPrimaryKey(Integer id_) {
        return selectOne(c ->
            c.where(id, isEqualTo(id_))
        );
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int update(UpdateDSLCompleter completer) {
        return MyBatis3Utils.update(this::update, user, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    static UpdateDSL<UpdateModel> updateAllColumns(User record, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(orgId).equalTo(record::getOrgId)
                .set(username).equalTo(record::getUsername)
                .set(password).equalTo(record::getPassword)
                .set(nickname).equalTo(record::getNickname)
                .set(avatar).equalTo(record::getAvatar)
                .set(signature).equalTo(record::getSignature)
                .set(phone).equalTo(record::getPhone)
                .set(email).equalTo(record::getEmail)
                .set(gender).equalTo(record::getGender)
                .set(birthday).equalTo(record::getBirthday)
                .set(status).equalTo(record::getStatus)
                .set(userNo).equalTo(record::getUserNo)
                .set(version).equalTo(record::getVersion)
                .set(deleted).equalTo(record::getDeleted)
                .set(creatorId).equalTo(record::getCreatorId)
                .set(createTime).equalTo(record::getCreateTime)
                .set(lastModifierId).equalTo(record::getLastModifierId)
                .set(lastModifyTime).equalTo(record::getLastModifyTime)
                .set(tenantId).equalTo(record::getTenantId);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    static UpdateDSL<UpdateModel> updateSelectiveColumns(User record, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(orgId).equalToWhenPresent(record::getOrgId)
                .set(username).equalToWhenPresent(record::getUsername)
                .set(password).equalToWhenPresent(record::getPassword)
                .set(nickname).equalToWhenPresent(record::getNickname)
                .set(avatar).equalToWhenPresent(record::getAvatar)
                .set(signature).equalToWhenPresent(record::getSignature)
                .set(phone).equalToWhenPresent(record::getPhone)
                .set(email).equalToWhenPresent(record::getEmail)
                .set(gender).equalToWhenPresent(record::getGender)
                .set(birthday).equalToWhenPresent(record::getBirthday)
                .set(status).equalToWhenPresent(record::getStatus)
                .set(userNo).equalToWhenPresent(record::getUserNo)
                .set(version).equalToWhenPresent(record::getVersion)
                .set(deleted).equalToWhenPresent(record::getDeleted)
                .set(creatorId).equalToWhenPresent(record::getCreatorId)
                .set(createTime).equalToWhenPresent(record::getCreateTime)
                .set(lastModifierId).equalToWhenPresent(record::getLastModifierId)
                .set(lastModifyTime).equalToWhenPresent(record::getLastModifyTime)
                .set(tenantId).equalToWhenPresent(record::getTenantId);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int updateByPrimaryKey(User record) {
        return update(c ->
            c.set(orgId).equalTo(record::getOrgId)
            .set(username).equalTo(record::getUsername)
            .set(password).equalTo(record::getPassword)
            .set(nickname).equalTo(record::getNickname)
            .set(avatar).equalTo(record::getAvatar)
            .set(signature).equalTo(record::getSignature)
            .set(phone).equalTo(record::getPhone)
            .set(email).equalTo(record::getEmail)
            .set(gender).equalTo(record::getGender)
            .set(birthday).equalTo(record::getBirthday)
            .set(status).equalTo(record::getStatus)
            .set(userNo).equalTo(record::getUserNo)
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
    default int updateByPrimaryKeySelective(User record) {
        return update(c ->
            c.set(orgId).equalToWhenPresent(record::getOrgId)
            .set(username).equalToWhenPresent(record::getUsername)
            .set(password).equalToWhenPresent(record::getPassword)
            .set(nickname).equalToWhenPresent(record::getNickname)
            .set(avatar).equalToWhenPresent(record::getAvatar)
            .set(signature).equalToWhenPresent(record::getSignature)
            .set(phone).equalToWhenPresent(record::getPhone)
            .set(email).equalToWhenPresent(record::getEmail)
            .set(gender).equalToWhenPresent(record::getGender)
            .set(birthday).equalToWhenPresent(record::getBirthday)
            .set(status).equalToWhenPresent(record::getStatus)
            .set(userNo).equalToWhenPresent(record::getUserNo)
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