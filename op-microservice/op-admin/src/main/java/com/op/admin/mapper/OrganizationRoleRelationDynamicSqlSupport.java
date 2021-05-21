package com.op.admin.mapper;

import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

import javax.annotation.Generated;
import java.sql.JDBCType;
import java.time.LocalDateTime;

public final class OrganizationRoleRelationDynamicSqlSupport {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final OrganizationRoleRelation organizationRoleRelation = new OrganizationRoleRelation();

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Integer> orgId = organizationRoleRelation.orgId;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Integer> roleId = organizationRoleRelation.roleId;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Integer> version = organizationRoleRelation.version;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Boolean> deleted = organizationRoleRelation.deleted;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Integer> creatorId = organizationRoleRelation.creatorId;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<LocalDateTime> createTime = organizationRoleRelation.createTime;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Integer> lastModifierId = organizationRoleRelation.lastModifierId;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<LocalDateTime> lastModifyTime = organizationRoleRelation.lastModifyTime;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> tenantId = organizationRoleRelation.tenantId;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final class OrganizationRoleRelation extends SqlTable {
        public final SqlColumn<Integer> orgId = column("org_id", JDBCType.INTEGER);

        public final SqlColumn<Integer> roleId = column("role_id", JDBCType.INTEGER);

        public final SqlColumn<Integer> version = column("version", JDBCType.INTEGER);

        public final SqlColumn<Boolean> deleted = column("deleted", JDBCType.BIT);

        public final SqlColumn<Integer> creatorId = column("creator_id", JDBCType.INTEGER);

        public final SqlColumn<LocalDateTime> createTime = column("create_time", JDBCType.TIMESTAMP);

        public final SqlColumn<Integer> lastModifierId = column("last_modifier_id", JDBCType.INTEGER);

        public final SqlColumn<LocalDateTime> lastModifyTime = column("last_modify_time", JDBCType.TIMESTAMP);

        public final SqlColumn<String> tenantId = column("tenant_id", JDBCType.VARCHAR);

        public OrganizationRoleRelation() {
            super("admin_organization_role_relation");
        }
    }
}