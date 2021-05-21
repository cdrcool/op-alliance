package com.op.admin.mapper;

import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

import javax.annotation.Generated;
import java.sql.JDBCType;
import java.time.LocalDateTime;

public final class UserResourceActionRelationDynamicSqlSupport {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final UserResourceActionRelation userResourceActionRelation = new UserResourceActionRelation();

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Integer> userId = userResourceActionRelation.userId;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Integer> actionId = userResourceActionRelation.actionId;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Integer> version = userResourceActionRelation.version;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Boolean> deleted = userResourceActionRelation.deleted;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Integer> creatorId = userResourceActionRelation.creatorId;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<LocalDateTime> createTime = userResourceActionRelation.createTime;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Integer> lastModifierId = userResourceActionRelation.lastModifierId;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<LocalDateTime> lastModifyTime = userResourceActionRelation.lastModifyTime;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> tenantId = userResourceActionRelation.tenantId;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final class UserResourceActionRelation extends SqlTable {
        public final SqlColumn<Integer> userId = column("user_id", JDBCType.INTEGER);

        public final SqlColumn<Integer> actionId = column("action_id", JDBCType.INTEGER);

        public final SqlColumn<Integer> version = column("version", JDBCType.INTEGER);

        public final SqlColumn<Boolean> deleted = column("deleted", JDBCType.BIT);

        public final SqlColumn<Integer> creatorId = column("creator_id", JDBCType.INTEGER);

        public final SqlColumn<LocalDateTime> createTime = column("create_time", JDBCType.TIMESTAMP);

        public final SqlColumn<Integer> lastModifierId = column("last_modifier_id", JDBCType.INTEGER);

        public final SqlColumn<LocalDateTime> lastModifyTime = column("last_modify_time", JDBCType.TIMESTAMP);

        public final SqlColumn<String> tenantId = column("tenant_id", JDBCType.VARCHAR);

        public UserResourceActionRelation() {
            super("admin_user_resource_action_relation");
        }
    }
}