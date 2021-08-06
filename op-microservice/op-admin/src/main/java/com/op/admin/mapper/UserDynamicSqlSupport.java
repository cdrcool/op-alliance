package com.op.admin.mapper;

import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

import javax.annotation.Generated;
import java.sql.JDBCType;
import java.time.LocalDate;
import java.time.LocalDateTime;

public final class UserDynamicSqlSupport {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final User user = new User();

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Integer> id = user.id;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Integer> orgId = user.orgId;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> username = user.username;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> password = user.password;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> nickname = user.nickname;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> avatar = user.avatar;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> signature = user.signature;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> phone = user.phone;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> email = user.email;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Integer> gender = user.gender;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<LocalDate> birthday = user.birthday;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Integer> status = user.status;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Integer> userNo = user.userNo;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<LocalDateTime> lastLoginTime = user.lastLoginTime;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Integer> version = user.version;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Boolean> deleted = user.deleted;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Integer> creatorId = user.creatorId;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<LocalDateTime> createTime = user.createTime;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Integer> lastModifierId = user.lastModifierId;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<LocalDateTime> lastModifyTime = user.lastModifyTime;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> tenantId = user.tenantId;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final class User extends SqlTable {
        public final SqlColumn<Integer> id = column("id", JDBCType.INTEGER);

        public final SqlColumn<Integer> orgId = column("org_id", JDBCType.INTEGER);

        public final SqlColumn<String> username = column("username", JDBCType.VARCHAR);

        public final SqlColumn<String> password = column("password", JDBCType.VARCHAR);

        public final SqlColumn<String> nickname = column("nickname", JDBCType.VARCHAR);

        public final SqlColumn<String> avatar = column("avatar", JDBCType.VARCHAR);

        public final SqlColumn<String> signature = column("signature", JDBCType.VARCHAR);

        public final SqlColumn<String> phone = column("phone", JDBCType.VARCHAR);

        public final SqlColumn<String> email = column("email", JDBCType.VARCHAR);

        public final SqlColumn<Integer> gender = column("gender", JDBCType.INTEGER);

        public final SqlColumn<LocalDate> birthday = column("birthday", JDBCType.DATE);

        public final SqlColumn<Integer> status = column("status", JDBCType.INTEGER);

        public final SqlColumn<Integer> userNo = column("user_no", JDBCType.INTEGER);

        public final SqlColumn<LocalDateTime> lastLoginTime = column("last_login_time", JDBCType.TIMESTAMP);

        public final SqlColumn<Integer> version = column("version", JDBCType.INTEGER);

        public final SqlColumn<Boolean> deleted = column("deleted", JDBCType.BIT);

        public final SqlColumn<Integer> creatorId = column("creator_id", JDBCType.INTEGER);

        public final SqlColumn<LocalDateTime> createTime = column("create_time", JDBCType.TIMESTAMP);

        public final SqlColumn<Integer> lastModifierId = column("last_modifier_id", JDBCType.INTEGER);

        public final SqlColumn<LocalDateTime> lastModifyTime = column("last_modify_time", JDBCType.TIMESTAMP);

        public final SqlColumn<String> tenantId = column("tenant_id", JDBCType.VARCHAR);

        public User() {
            super("admin_user");
        }
    }
}