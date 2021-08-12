package com.op.admin.mapper;

import java.sql.JDBCType;
import javax.annotation.Generated;
import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

public final class OauthClientDetailsDynamicSqlSupport {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final OauthClientDetails oauthClientDetails = new OauthClientDetails();

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Integer> id = oauthClientDetails.id;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> clientId = oauthClientDetails.clientId;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> clientSecret = oauthClientDetails.clientSecret;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> authorizedGrantTypes = oauthClientDetails.authorizedGrantTypes;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> scope = oauthClientDetails.scope;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> webServerRedirectUri = oauthClientDetails.webServerRedirectUri;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> authorities = oauthClientDetails.authorities;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> resourceIds = oauthClientDetails.resourceIds;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Integer> accessTokenValidity = oauthClientDetails.accessTokenValidity;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Integer> refreshTokenValidity = oauthClientDetails.refreshTokenValidity;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> autoapprove = oauthClientDetails.autoapprove;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> additionalInformation = oauthClientDetails.additionalInformation;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final class OauthClientDetails extends SqlTable {
        public final SqlColumn<Integer> id = column("id", JDBCType.INTEGER);

        public final SqlColumn<String> clientId = column("client_id", JDBCType.VARCHAR);

        public final SqlColumn<String> clientSecret = column("client_secret", JDBCType.VARCHAR);

        public final SqlColumn<String> authorizedGrantTypes = column("authorized_grant_types", JDBCType.VARCHAR);

        public final SqlColumn<String> scope = column("scope", JDBCType.VARCHAR);

        public final SqlColumn<String> webServerRedirectUri = column("web_server_redirect_uri", JDBCType.VARCHAR);

        public final SqlColumn<String> authorities = column("authorities", JDBCType.VARCHAR);

        public final SqlColumn<String> resourceIds = column("resource_ids", JDBCType.VARCHAR);

        public final SqlColumn<Integer> accessTokenValidity = column("access_token_validity", JDBCType.INTEGER);

        public final SqlColumn<Integer> refreshTokenValidity = column("refresh_token_validity", JDBCType.INTEGER);

        public final SqlColumn<String> autoapprove = column("autoapprove", JDBCType.VARCHAR);

        public final SqlColumn<String> additionalInformation = column("additional_information", JDBCType.LONGVARCHAR);

        public OauthClientDetails() {
            super("admin_oauth_client_details");
        }
    }
}