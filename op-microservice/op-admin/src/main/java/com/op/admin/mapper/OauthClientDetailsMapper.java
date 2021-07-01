package com.op.admin.mapper;

import static com.op.admin.mapper.OauthClientDetailsDynamicSqlSupport.*;
import static org.mybatis.dynamic.sql.SqlBuilder.*;

import com.op.admin.entity.OauthClientDetails;
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
 * @date 2021/07/01 12:06
 */
@Mapper
public interface OauthClientDetailsMapper {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    BasicColumn[] selectList = BasicColumn.columnList(id, clientId, clientSecret, authorizedGrantTypes, scope, webServerRedirectUri, authorities, resourceIds, accessTokenValidity, refreshTokenValidity, autoapprove, additionalInformation);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    long count(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @DeleteProvider(type=SqlProviderAdapter.class, method="delete")
    int delete(DeleteStatementProvider deleteStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @InsertProvider(type=SqlProviderAdapter.class, method="insert")
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="record.id", before=false, resultType=Integer.class)
    int insert(InsertStatementProvider<OauthClientDetails> insertStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("OauthClientDetailsResult")
    Optional<OauthClientDetails> selectOne(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results(id="OauthClientDetailsResult", value = {
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="client_id", property="clientId", jdbcType=JdbcType.VARCHAR),
        @Result(column="client_secret", property="clientSecret", jdbcType=JdbcType.VARCHAR),
        @Result(column="authorized_grant_types", property="authorizedGrantTypes", jdbcType=JdbcType.VARCHAR),
        @Result(column="scope", property="scope", jdbcType=JdbcType.VARCHAR),
        @Result(column="web_server_redirect_uri", property="webServerRedirectUri", jdbcType=JdbcType.VARCHAR),
        @Result(column="authorities", property="authorities", jdbcType=JdbcType.VARCHAR),
        @Result(column="resource_ids", property="resourceIds", jdbcType=JdbcType.VARCHAR),
        @Result(column="access_token_validity", property="accessTokenValidity", jdbcType=JdbcType.INTEGER),
        @Result(column="refresh_token_validity", property="refreshTokenValidity", jdbcType=JdbcType.INTEGER),
        @Result(column="autoapprove", property="autoapprove", jdbcType=JdbcType.VARCHAR),
        @Result(column="additional_information", property="additionalInformation", jdbcType=JdbcType.LONGVARCHAR)
    })
    List<OauthClientDetails> selectMany(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @UpdateProvider(type=SqlProviderAdapter.class, method="update")
    int update(UpdateStatementProvider updateStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default long count(CountDSLCompleter completer) {
        return MyBatis3Utils.countFrom(this::count, oauthClientDetails, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int delete(DeleteDSLCompleter completer) {
        return MyBatis3Utils.deleteFrom(this::delete, oauthClientDetails, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int deleteByPrimaryKey(Integer id_) {
        return delete(c -> 
            c.where(id, isEqualTo(id_))
        );
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int insert(OauthClientDetails record) {
        return MyBatis3Utils.insert(this::insert, record, oauthClientDetails, c ->
            c.map(clientId).toProperty("clientId")
            .map(clientSecret).toProperty("clientSecret")
            .map(authorizedGrantTypes).toProperty("authorizedGrantTypes")
            .map(scope).toProperty("scope")
            .map(webServerRedirectUri).toProperty("webServerRedirectUri")
            .map(authorities).toProperty("authorities")
            .map(resourceIds).toProperty("resourceIds")
            .map(accessTokenValidity).toProperty("accessTokenValidity")
            .map(refreshTokenValidity).toProperty("refreshTokenValidity")
            .map(autoapprove).toProperty("autoapprove")
            .map(additionalInformation).toProperty("additionalInformation")
        );
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int insertSelective(OauthClientDetails record) {
        return MyBatis3Utils.insert(this::insert, record, oauthClientDetails, c ->
            c.map(clientId).toPropertyWhenPresent("clientId", record::getClientId)
            .map(clientSecret).toPropertyWhenPresent("clientSecret", record::getClientSecret)
            .map(authorizedGrantTypes).toPropertyWhenPresent("authorizedGrantTypes", record::getAuthorizedGrantTypes)
            .map(scope).toPropertyWhenPresent("scope", record::getScope)
            .map(webServerRedirectUri).toPropertyWhenPresent("webServerRedirectUri", record::getWebServerRedirectUri)
            .map(authorities).toPropertyWhenPresent("authorities", record::getAuthorities)
            .map(resourceIds).toPropertyWhenPresent("resourceIds", record::getResourceIds)
            .map(accessTokenValidity).toPropertyWhenPresent("accessTokenValidity", record::getAccessTokenValidity)
            .map(refreshTokenValidity).toPropertyWhenPresent("refreshTokenValidity", record::getRefreshTokenValidity)
            .map(autoapprove).toPropertyWhenPresent("autoapprove", record::getAutoapprove)
            .map(additionalInformation).toPropertyWhenPresent("additionalInformation", record::getAdditionalInformation)
        );
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default Optional<OauthClientDetails> selectOne(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectOne(this::selectOne, selectList, oauthClientDetails, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default List<OauthClientDetails> select(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectList(this::selectMany, selectList, oauthClientDetails, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default List<OauthClientDetails> selectDistinct(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectDistinct(this::selectMany, selectList, oauthClientDetails, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default Optional<OauthClientDetails> selectByPrimaryKey(Integer id_) {
        return selectOne(c ->
            c.where(id, isEqualTo(id_))
        );
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int update(UpdateDSLCompleter completer) {
        return MyBatis3Utils.update(this::update, oauthClientDetails, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    static UpdateDSL<UpdateModel> updateAllColumns(OauthClientDetails record, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(clientId).equalTo(record::getClientId)
                .set(clientSecret).equalTo(record::getClientSecret)
                .set(authorizedGrantTypes).equalTo(record::getAuthorizedGrantTypes)
                .set(scope).equalTo(record::getScope)
                .set(webServerRedirectUri).equalTo(record::getWebServerRedirectUri)
                .set(authorities).equalTo(record::getAuthorities)
                .set(resourceIds).equalTo(record::getResourceIds)
                .set(accessTokenValidity).equalTo(record::getAccessTokenValidity)
                .set(refreshTokenValidity).equalTo(record::getRefreshTokenValidity)
                .set(autoapprove).equalTo(record::getAutoapprove)
                .set(additionalInformation).equalTo(record::getAdditionalInformation);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    static UpdateDSL<UpdateModel> updateSelectiveColumns(OauthClientDetails record, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(clientId).equalToWhenPresent(record::getClientId)
                .set(clientSecret).equalToWhenPresent(record::getClientSecret)
                .set(authorizedGrantTypes).equalToWhenPresent(record::getAuthorizedGrantTypes)
                .set(scope).equalToWhenPresent(record::getScope)
                .set(webServerRedirectUri).equalToWhenPresent(record::getWebServerRedirectUri)
                .set(authorities).equalToWhenPresent(record::getAuthorities)
                .set(resourceIds).equalToWhenPresent(record::getResourceIds)
                .set(accessTokenValidity).equalToWhenPresent(record::getAccessTokenValidity)
                .set(refreshTokenValidity).equalToWhenPresent(record::getRefreshTokenValidity)
                .set(autoapprove).equalToWhenPresent(record::getAutoapprove)
                .set(additionalInformation).equalToWhenPresent(record::getAdditionalInformation);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int updateByPrimaryKey(OauthClientDetails record) {
        return update(c ->
            c.set(clientId).equalTo(record::getClientId)
            .set(clientSecret).equalTo(record::getClientSecret)
            .set(authorizedGrantTypes).equalTo(record::getAuthorizedGrantTypes)
            .set(scope).equalTo(record::getScope)
            .set(webServerRedirectUri).equalTo(record::getWebServerRedirectUri)
            .set(authorities).equalTo(record::getAuthorities)
            .set(resourceIds).equalTo(record::getResourceIds)
            .set(accessTokenValidity).equalTo(record::getAccessTokenValidity)
            .set(refreshTokenValidity).equalTo(record::getRefreshTokenValidity)
            .set(autoapprove).equalTo(record::getAutoapprove)
            .set(additionalInformation).equalTo(record::getAdditionalInformation)
            .where(id, isEqualTo(record::getId))
        );
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int updateByPrimaryKeySelective(OauthClientDetails record) {
        return update(c ->
            c.set(clientId).equalToWhenPresent(record::getClientId)
            .set(clientSecret).equalToWhenPresent(record::getClientSecret)
            .set(authorizedGrantTypes).equalToWhenPresent(record::getAuthorizedGrantTypes)
            .set(scope).equalToWhenPresent(record::getScope)
            .set(webServerRedirectUri).equalToWhenPresent(record::getWebServerRedirectUri)
            .set(authorities).equalToWhenPresent(record::getAuthorities)
            .set(resourceIds).equalToWhenPresent(record::getResourceIds)
            .set(accessTokenValidity).equalToWhenPresent(record::getAccessTokenValidity)
            .set(refreshTokenValidity).equalToWhenPresent(record::getRefreshTokenValidity)
            .set(autoapprove).equalToWhenPresent(record::getAutoapprove)
            .set(additionalInformation).equalToWhenPresent(record::getAdditionalInformation)
            .where(id, isEqualTo(record::getId))
        );
    }
}