package com.op.admin.mapper;

import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

import javax.annotation.Generated;
import java.sql.JDBCType;
import java.time.LocalDateTime;

public final class ResourceDynamicSqlSupport {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final Resource resource = new Resource();

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Integer> id = resource.id;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Integer> categoryId = resource.categoryId;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> resourceName = resource.resourceName;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> resourcePath = resource.resourcePath;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> resourceDesc = resource.resourceDesc;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Integer> resourceNo = resource.resourceNo;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Integer> version = resource.version;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Boolean> deleted = resource.deleted;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Integer> creatorId = resource.creatorId;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<LocalDateTime> createTime = resource.createTime;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Integer> lastModifierId = resource.lastModifierId;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<LocalDateTime> lastModifyTime = resource.lastModifyTime;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> tenantId = resource.tenantId;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final class Resource extends SqlTable {
        public final SqlColumn<Integer> id = column("id", JDBCType.INTEGER);

        public final SqlColumn<Integer> categoryId = column("category_id", JDBCType.INTEGER);

        public final SqlColumn<String> resourceName = column("resource_name", JDBCType.VARCHAR);

        public final SqlColumn<String> resourcePath = column("resource_path", JDBCType.VARCHAR);

        public final SqlColumn<String> resourceDesc = column("resource_desc", JDBCType.VARCHAR);

        public final SqlColumn<Integer> resourceNo = column("resource_no", JDBCType.INTEGER);

        public final SqlColumn<Integer> version = column("version", JDBCType.INTEGER);

        public final SqlColumn<Boolean> deleted = column("deleted", JDBCType.BIT);

        public final SqlColumn<Integer> creatorId = column("creator_id", JDBCType.INTEGER);

        public final SqlColumn<LocalDateTime> createTime = column("create_time", JDBCType.TIMESTAMP);

        public final SqlColumn<Integer> lastModifierId = column("last_modifier_id", JDBCType.INTEGER);

        public final SqlColumn<LocalDateTime> lastModifyTime = column("last_modify_time", JDBCType.TIMESTAMP);

        public final SqlColumn<String> tenantId = column("tenant_id", JDBCType.VARCHAR);

        public Resource() {
            super("admin_resource");
        }
    }
}