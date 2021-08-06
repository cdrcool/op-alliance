package com.op.admin.mapper;

import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

import javax.annotation.Generated;
import java.sql.JDBCType;
import java.time.LocalDateTime;

public final class UserOrganizationRelationDynamicSqlSupport {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final UserOrganizationRelation userOrganizationRelation = new UserOrganizationRelation();

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Integer> userId = userOrganizationRelation.userId;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Integer> orgId = userOrganizationRelation.orgId;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Integer> version = userOrganizationRelation.version;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Boolean> deleted = userOrganizationRelation.deleted;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Integer> creatorId = userOrganizationRelation.creatorId;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<LocalDateTime> createTime = userOrganizationRelation.createTime;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Integer> lastModifierId = userOrganizationRelation.lastModifierId;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<LocalDateTime> lastModifyTime = userOrganizationRelation.lastModifyTime;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> tenantId = userOrganizationRelation.tenantId;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final class UserOrganizationRelation extends SqlTable {
        public final SqlColumn<Integer> userId = column("user_id", JDBCType.INTEGER);

        public final SqlColumn<Integer> orgId = column("org_id", JDBCType.INTEGER);

        public final SqlColumn<Integer> version = column("version", JDBCType.INTEGER);

        public final SqlColumn<Boolean> deleted = column("deleted", JDBCType.BIT);

        public final SqlColumn<Integer> creatorId = column("creator_id", JDBCType.INTEGER);

        public final SqlColumn<LocalDateTime> createTime = column("create_time", JDBCType.TIMESTAMP);

        public final SqlColumn<Integer> lastModifierId = column("last_modifier_id", JDBCType.INTEGER);

        public final SqlColumn<LocalDateTime> lastModifyTime = column("last_modify_time", JDBCType.TIMESTAMP);

        public final SqlColumn<String> tenantId = column("tenant_id", JDBCType.VARCHAR);

        public UserOrganizationRelation() {
            super("admin_user_organization_relation");
        }
    }
}