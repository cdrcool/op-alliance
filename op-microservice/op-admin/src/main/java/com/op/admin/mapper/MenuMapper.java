package com.op.admin.mapper;

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
 * @date 2021/06/24 03:12
 */
@Mapper
public interface MenuMapper {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    BasicColumn[] selectList = BasicColumn.columnList(MenuDynamicSqlSupport.id, MenuDynamicSqlSupport.pid, MenuDynamicSqlSupport.parentIds, MenuDynamicSqlSupport.menuName, MenuDynamicSqlSupport.menuCode, MenuDynamicSqlSupport.menuIcon, MenuDynamicSqlSupport.menuRoute, MenuDynamicSqlSupport.menuLevel, MenuDynamicSqlSupport.menuNo, MenuDynamicSqlSupport.isDirectory, MenuDynamicSqlSupport.isHidden, MenuDynamicSqlSupport.version, MenuDynamicSqlSupport.deleted, MenuDynamicSqlSupport.creatorId, MenuDynamicSqlSupport.createTime, MenuDynamicSqlSupport.lastModifierId, MenuDynamicSqlSupport.lastModifyTime, MenuDynamicSqlSupport.tenantId);

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
        @Result(column="parent_ids", property="parentIds", jdbcType=JdbcType.VARCHAR),
        @Result(column="menu_name", property="menuName", jdbcType=JdbcType.VARCHAR),
        @Result(column="menu_code", property="menuCode", jdbcType=JdbcType.VARCHAR),
        @Result(column="menu_icon", property="menuIcon", jdbcType=JdbcType.VARCHAR),
        @Result(column="menu_route", property="menuRoute", jdbcType=JdbcType.VARCHAR),
        @Result(column="menu_level", property="menuLevel", jdbcType=JdbcType.INTEGER),
        @Result(column="menu_no", property="menuNo", jdbcType=JdbcType.INTEGER),
        @Result(column="is_directory", property="isDirectory", jdbcType=JdbcType.BIT),
        @Result(column="is_hidden", property="isHidden", jdbcType=JdbcType.BIT),
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
        return MyBatis3Utils.countFrom(this::count, MenuDynamicSqlSupport.menu, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int delete(DeleteDSLCompleter completer) {
        return MyBatis3Utils.deleteFrom(this::delete, MenuDynamicSqlSupport.menu, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int deleteByPrimaryKey(Integer id_) {
        return delete(c -> 
            c.where(MenuDynamicSqlSupport.id, isEqualTo(id_))
        );
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int insert(Menu record) {
        return MyBatis3Utils.insert(this::insert, record, MenuDynamicSqlSupport.menu, c ->
            c.map(MenuDynamicSqlSupport.pid).toProperty("pid")
            .map(MenuDynamicSqlSupport.parentIds).toProperty("parentIds")
            .map(MenuDynamicSqlSupport.menuName).toProperty("menuName")
            .map(MenuDynamicSqlSupport.menuCode).toProperty("menuCode")
            .map(MenuDynamicSqlSupport.menuIcon).toProperty("menuIcon")
            .map(MenuDynamicSqlSupport.menuRoute).toProperty("menuRoute")
            .map(MenuDynamicSqlSupport.menuLevel).toProperty("menuLevel")
            .map(MenuDynamicSqlSupport.menuNo).toProperty("menuNo")
            .map(MenuDynamicSqlSupport.isDirectory).toProperty("isDirectory")
            .map(MenuDynamicSqlSupport.isHidden).toProperty("isHidden")
            .map(MenuDynamicSqlSupport.version).toProperty("version")
            .map(MenuDynamicSqlSupport.deleted).toProperty("deleted")
            .map(MenuDynamicSqlSupport.creatorId).toProperty("creatorId")
            .map(MenuDynamicSqlSupport.createTime).toProperty("createTime")
            .map(MenuDynamicSqlSupport.lastModifierId).toProperty("lastModifierId")
            .map(MenuDynamicSqlSupport.lastModifyTime).toProperty("lastModifyTime")
            .map(MenuDynamicSqlSupport.tenantId).toProperty("tenantId")
        );
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int insertSelective(Menu record) {
        return MyBatis3Utils.insert(this::insert, record, MenuDynamicSqlSupport.menu, c ->
            c.map(MenuDynamicSqlSupport.pid).toPropertyWhenPresent("pid", record::getPid)
            .map(MenuDynamicSqlSupport.parentIds).toPropertyWhenPresent("parentIds", record::getParentIds)
            .map(MenuDynamicSqlSupport.menuName).toPropertyWhenPresent("menuName", record::getMenuName)
            .map(MenuDynamicSqlSupport.menuCode).toPropertyWhenPresent("menuCode", record::getMenuCode)
            .map(MenuDynamicSqlSupport.menuIcon).toPropertyWhenPresent("menuIcon", record::getMenuIcon)
            .map(MenuDynamicSqlSupport.menuRoute).toPropertyWhenPresent("menuRoute", record::getMenuRoute)
            .map(MenuDynamicSqlSupport.menuLevel).toPropertyWhenPresent("menuLevel", record::getMenuLevel)
            .map(MenuDynamicSqlSupport.menuNo).toPropertyWhenPresent("menuNo", record::getMenuNo)
            .map(MenuDynamicSqlSupport.isDirectory).toPropertyWhenPresent("isDirectory", record::getIsDirectory)
            .map(MenuDynamicSqlSupport.isHidden).toPropertyWhenPresent("isHidden", record::getIsHidden)
            .map(MenuDynamicSqlSupport.version).toPropertyWhenPresent("version", record::getVersion)
            .map(MenuDynamicSqlSupport.deleted).toPropertyWhenPresent("deleted", record::getDeleted)
            .map(MenuDynamicSqlSupport.creatorId).toPropertyWhenPresent("creatorId", record::getCreatorId)
            .map(MenuDynamicSqlSupport.createTime).toPropertyWhenPresent("createTime", record::getCreateTime)
            .map(MenuDynamicSqlSupport.lastModifierId).toPropertyWhenPresent("lastModifierId", record::getLastModifierId)
            .map(MenuDynamicSqlSupport.lastModifyTime).toPropertyWhenPresent("lastModifyTime", record::getLastModifyTime)
            .map(MenuDynamicSqlSupport.tenantId).toPropertyWhenPresent("tenantId", record::getTenantId)
        );
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default Optional<Menu> selectOne(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectOne(this::selectOne, selectList, MenuDynamicSqlSupport.menu, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default List<Menu> select(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectList(this::selectMany, selectList, MenuDynamicSqlSupport.menu, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default List<Menu> selectDistinct(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectDistinct(this::selectMany, selectList, MenuDynamicSqlSupport.menu, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default Optional<Menu> selectByPrimaryKey(Integer id_) {
        return selectOne(c ->
            c.where(MenuDynamicSqlSupport.id, isEqualTo(id_))
        );
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int update(UpdateDSLCompleter completer) {
        return MyBatis3Utils.update(this::update, MenuDynamicSqlSupport.menu, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    static UpdateDSL<UpdateModel> updateAllColumns(Menu record, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(MenuDynamicSqlSupport.pid).equalTo(record::getPid)
                .set(MenuDynamicSqlSupport.parentIds).equalTo(record::getParentIds)
                .set(MenuDynamicSqlSupport.menuName).equalTo(record::getMenuName)
                .set(MenuDynamicSqlSupport.menuCode).equalTo(record::getMenuCode)
                .set(MenuDynamicSqlSupport.menuIcon).equalTo(record::getMenuIcon)
                .set(MenuDynamicSqlSupport.menuRoute).equalTo(record::getMenuRoute)
                .set(MenuDynamicSqlSupport.menuLevel).equalTo(record::getMenuLevel)
                .set(MenuDynamicSqlSupport.menuNo).equalTo(record::getMenuNo)
                .set(MenuDynamicSqlSupport.isDirectory).equalTo(record::getIsDirectory)
                .set(MenuDynamicSqlSupport.isHidden).equalTo(record::getIsHidden)
                .set(MenuDynamicSqlSupport.version).equalTo(record::getVersion)
                .set(MenuDynamicSqlSupport.deleted).equalTo(record::getDeleted)
                .set(MenuDynamicSqlSupport.creatorId).equalTo(record::getCreatorId)
                .set(MenuDynamicSqlSupport.createTime).equalTo(record::getCreateTime)
                .set(MenuDynamicSqlSupport.lastModifierId).equalTo(record::getLastModifierId)
                .set(MenuDynamicSqlSupport.lastModifyTime).equalTo(record::getLastModifyTime)
                .set(MenuDynamicSqlSupport.tenantId).equalTo(record::getTenantId);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    static UpdateDSL<UpdateModel> updateSelectiveColumns(Menu record, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(MenuDynamicSqlSupport.pid).equalToWhenPresent(record::getPid)
                .set(MenuDynamicSqlSupport.parentIds).equalToWhenPresent(record::getParentIds)
                .set(MenuDynamicSqlSupport.menuName).equalToWhenPresent(record::getMenuName)
                .set(MenuDynamicSqlSupport.menuCode).equalToWhenPresent(record::getMenuCode)
                .set(MenuDynamicSqlSupport.menuIcon).equalToWhenPresent(record::getMenuIcon)
                .set(MenuDynamicSqlSupport.menuRoute).equalToWhenPresent(record::getMenuRoute)
                .set(MenuDynamicSqlSupport.menuLevel).equalToWhenPresent(record::getMenuLevel)
                .set(MenuDynamicSqlSupport.menuNo).equalToWhenPresent(record::getMenuNo)
                .set(MenuDynamicSqlSupport.isDirectory).equalToWhenPresent(record::getIsDirectory)
                .set(MenuDynamicSqlSupport.isHidden).equalToWhenPresent(record::getIsHidden)
                .set(MenuDynamicSqlSupport.version).equalToWhenPresent(record::getVersion)
                .set(MenuDynamicSqlSupport.deleted).equalToWhenPresent(record::getDeleted)
                .set(MenuDynamicSqlSupport.creatorId).equalToWhenPresent(record::getCreatorId)
                .set(MenuDynamicSqlSupport.createTime).equalToWhenPresent(record::getCreateTime)
                .set(MenuDynamicSqlSupport.lastModifierId).equalToWhenPresent(record::getLastModifierId)
                .set(MenuDynamicSqlSupport.lastModifyTime).equalToWhenPresent(record::getLastModifyTime)
                .set(MenuDynamicSqlSupport.tenantId).equalToWhenPresent(record::getTenantId);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int updateByPrimaryKey(Menu record) {
        return update(c ->
            c.set(MenuDynamicSqlSupport.pid).equalTo(record::getPid)
            .set(MenuDynamicSqlSupport.parentIds).equalTo(record::getParentIds)
            .set(MenuDynamicSqlSupport.menuName).equalTo(record::getMenuName)
            .set(MenuDynamicSqlSupport.menuCode).equalTo(record::getMenuCode)
            .set(MenuDynamicSqlSupport.menuIcon).equalTo(record::getMenuIcon)
            .set(MenuDynamicSqlSupport.menuRoute).equalTo(record::getMenuRoute)
            .set(MenuDynamicSqlSupport.menuLevel).equalTo(record::getMenuLevel)
            .set(MenuDynamicSqlSupport.menuNo).equalTo(record::getMenuNo)
            .set(MenuDynamicSqlSupport.isDirectory).equalTo(record::getIsDirectory)
            .set(MenuDynamicSqlSupport.isHidden).equalTo(record::getIsHidden)
            .set(MenuDynamicSqlSupport.version).equalTo(record::getVersion)
            .set(MenuDynamicSqlSupport.deleted).equalTo(record::getDeleted)
            .set(MenuDynamicSqlSupport.creatorId).equalTo(record::getCreatorId)
            .set(MenuDynamicSqlSupport.createTime).equalTo(record::getCreateTime)
            .set(MenuDynamicSqlSupport.lastModifierId).equalTo(record::getLastModifierId)
            .set(MenuDynamicSqlSupport.lastModifyTime).equalTo(record::getLastModifyTime)
            .set(MenuDynamicSqlSupport.tenantId).equalTo(record::getTenantId)
            .where(MenuDynamicSqlSupport.id, isEqualTo(record::getId))
        );
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int updateByPrimaryKeySelective(Menu record) {
        return update(c ->
            c.set(MenuDynamicSqlSupport.pid).equalToWhenPresent(record::getPid)
            .set(MenuDynamicSqlSupport.parentIds).equalToWhenPresent(record::getParentIds)
            .set(MenuDynamicSqlSupport.menuName).equalToWhenPresent(record::getMenuName)
            .set(MenuDynamicSqlSupport.menuCode).equalToWhenPresent(record::getMenuCode)
            .set(MenuDynamicSqlSupport.menuIcon).equalToWhenPresent(record::getMenuIcon)
            .set(MenuDynamicSqlSupport.menuRoute).equalToWhenPresent(record::getMenuRoute)
            .set(MenuDynamicSqlSupport.menuLevel).equalToWhenPresent(record::getMenuLevel)
            .set(MenuDynamicSqlSupport.menuNo).equalToWhenPresent(record::getMenuNo)
            .set(MenuDynamicSqlSupport.isDirectory).equalToWhenPresent(record::getIsDirectory)
            .set(MenuDynamicSqlSupport.isHidden).equalToWhenPresent(record::getIsHidden)
            .set(MenuDynamicSqlSupport.version).equalToWhenPresent(record::getVersion)
            .set(MenuDynamicSqlSupport.deleted).equalToWhenPresent(record::getDeleted)
            .set(MenuDynamicSqlSupport.creatorId).equalToWhenPresent(record::getCreatorId)
            .set(MenuDynamicSqlSupport.createTime).equalToWhenPresent(record::getCreateTime)
            .set(MenuDynamicSqlSupport.lastModifierId).equalToWhenPresent(record::getLastModifierId)
            .set(MenuDynamicSqlSupport.lastModifyTime).equalToWhenPresent(record::getLastModifyTime)
            .set(MenuDynamicSqlSupport.tenantId).equalToWhenPresent(record::getTenantId)
            .where(MenuDynamicSqlSupport.id, isEqualTo(record::getId))
        );
    }
}