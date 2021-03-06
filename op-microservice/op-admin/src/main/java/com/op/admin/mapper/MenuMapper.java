package com.op.admin.mapper;

import static com.op.admin.mapper.MenuDynamicSqlSupport.*;
import static org.mybatis.dynamic.sql.SqlBuilder.*;

import com.op.admin.entity.Menu;
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
public interface MenuMapper {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    BasicColumn[] selectList = BasicColumn.columnList(id, pid, menuName, menuIcon, menuPath, isShow, permission, menuNo, version, deleted, creatorId, createTime, lastModifierId, lastModifyTime, tenantId);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    long count(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @DeleteProvider(type=SqlProviderAdapter.class, method="delete")
    int delete(DeleteStatementProvider deleteStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @InsertProvider(type=SqlProviderAdapter.class, method="insert")
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="record.id", before=false, resultType=Integer.class)
    int insert(InsertStatementProvider<Menu> insertStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("MenuResult")
    Optional<Menu> selectOne(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results(id="MenuResult", value = {
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="pid", property="pid", jdbcType=JdbcType.INTEGER),
        @Result(column="menu_name", property="menuName", jdbcType=JdbcType.VARCHAR),
        @Result(column="menu_icon", property="menuIcon", jdbcType=JdbcType.VARCHAR),
        @Result(column="menu_path", property="menuPath", jdbcType=JdbcType.VARCHAR),
        @Result(column="is_show", property="isShow", jdbcType=JdbcType.BIT),
        @Result(column="permission", property="permission", jdbcType=JdbcType.VARCHAR),
        @Result(column="menu_no", property="menuNo", jdbcType=JdbcType.INTEGER),
        @Result(column="version", property="version", jdbcType=JdbcType.INTEGER),
        @Result(column="deleted", property="deleted", jdbcType=JdbcType.BIT),
        @Result(column="creator_id", property="creatorId", jdbcType=JdbcType.INTEGER),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="last_modifier_id", property="lastModifierId", jdbcType=JdbcType.INTEGER),
        @Result(column="last_modify_time", property="lastModifyTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="tenant_id", property="tenantId", jdbcType=JdbcType.VARCHAR)
    })
    List<Menu> selectMany(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @UpdateProvider(type=SqlProviderAdapter.class, method="update")
    int update(UpdateStatementProvider updateStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default long count(CountDSLCompleter completer) {
        return MyBatis3Utils.countFrom(this::count, menu, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int delete(DeleteDSLCompleter completer) {
        return MyBatis3Utils.deleteFrom(this::delete, menu, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int deleteByPrimaryKey(Integer id_) {
        return delete(c -> 
            c.where(id, isEqualTo(id_))
        );
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int insert(Menu record) {
        return MyBatis3Utils.insert(this::insert, record, menu, c ->
            c.map(pid).toProperty("pid")
            .map(menuName).toProperty("menuName")
            .map(menuIcon).toProperty("menuIcon")
            .map(menuPath).toProperty("menuPath")
            .map(isShow).toProperty("isShow")
            .map(permission).toProperty("permission")
            .map(menuNo).toProperty("menuNo")
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
    default int insertSelective(Menu record) {
        return MyBatis3Utils.insert(this::insert, record, menu, c ->
            c.map(pid).toPropertyWhenPresent("pid", record::getPid)
            .map(menuName).toPropertyWhenPresent("menuName", record::getMenuName)
            .map(menuIcon).toPropertyWhenPresent("menuIcon", record::getMenuIcon)
            .map(menuPath).toPropertyWhenPresent("menuPath", record::getMenuPath)
            .map(isShow).toPropertyWhenPresent("isShow", record::getIsShow)
            .map(permission).toPropertyWhenPresent("permission", record::getPermission)
            .map(menuNo).toPropertyWhenPresent("menuNo", record::getMenuNo)
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
    default Optional<Menu> selectOne(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectOne(this::selectOne, selectList, menu, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default List<Menu> select(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectList(this::selectMany, selectList, menu, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default List<Menu> selectDistinct(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectDistinct(this::selectMany, selectList, menu, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default Optional<Menu> selectByPrimaryKey(Integer id_) {
        return selectOne(c ->
            c.where(id, isEqualTo(id_))
        );
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int update(UpdateDSLCompleter completer) {
        return MyBatis3Utils.update(this::update, menu, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    static UpdateDSL<UpdateModel> updateAllColumns(Menu record, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(pid).equalTo(record::getPid)
                .set(menuName).equalTo(record::getMenuName)
                .set(menuIcon).equalTo(record::getMenuIcon)
                .set(menuPath).equalTo(record::getMenuPath)
                .set(isShow).equalTo(record::getIsShow)
                .set(permission).equalTo(record::getPermission)
                .set(menuNo).equalTo(record::getMenuNo)
                .set(version).equalTo(record::getVersion)
                .set(deleted).equalTo(record::getDeleted)
                .set(creatorId).equalTo(record::getCreatorId)
                .set(createTime).equalTo(record::getCreateTime)
                .set(lastModifierId).equalTo(record::getLastModifierId)
                .set(lastModifyTime).equalTo(record::getLastModifyTime)
                .set(tenantId).equalTo(record::getTenantId);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    static UpdateDSL<UpdateModel> updateSelectiveColumns(Menu record, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(pid).equalToWhenPresent(record::getPid)
                .set(menuName).equalToWhenPresent(record::getMenuName)
                .set(menuIcon).equalToWhenPresent(record::getMenuIcon)
                .set(menuPath).equalToWhenPresent(record::getMenuPath)
                .set(isShow).equalToWhenPresent(record::getIsShow)
                .set(permission).equalToWhenPresent(record::getPermission)
                .set(menuNo).equalToWhenPresent(record::getMenuNo)
                .set(version).equalToWhenPresent(record::getVersion)
                .set(deleted).equalToWhenPresent(record::getDeleted)
                .set(creatorId).equalToWhenPresent(record::getCreatorId)
                .set(createTime).equalToWhenPresent(record::getCreateTime)
                .set(lastModifierId).equalToWhenPresent(record::getLastModifierId)
                .set(lastModifyTime).equalToWhenPresent(record::getLastModifyTime)
                .set(tenantId).equalToWhenPresent(record::getTenantId);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int updateByPrimaryKey(Menu record) {
        return update(c ->
            c.set(pid).equalTo(record::getPid)
            .set(menuName).equalTo(record::getMenuName)
            .set(menuIcon).equalTo(record::getMenuIcon)
            .set(menuPath).equalTo(record::getMenuPath)
            .set(isShow).equalTo(record::getIsShow)
            .set(permission).equalTo(record::getPermission)
            .set(menuNo).equalTo(record::getMenuNo)
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
    default int updateByPrimaryKeySelective(Menu record) {
        return update(c ->
            c.set(pid).equalToWhenPresent(record::getPid)
            .set(menuName).equalToWhenPresent(record::getMenuName)
            .set(menuIcon).equalToWhenPresent(record::getMenuIcon)
            .set(menuPath).equalToWhenPresent(record::getMenuPath)
            .set(isShow).equalToWhenPresent(record::getIsShow)
            .set(permission).equalToWhenPresent(record::getPermission)
            .set(menuNo).equalToWhenPresent(record::getMenuNo)
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