package com.op.admin.mapper;

import com.op.admin.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 用户 Mapper
 *
 * @author chengdr01
 */
@Mapper
public interface UserMapper {
    /**
     * 插入用户
     *
     * @param user 单据类型
     */
    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("INSERT INTO admin_user(id, org_id, username, password, nickname, avatar, signature, phone, email, gender, birthday, status, user_no) " +
            "VALUES(#{id}, #{orgId}, #{username}, #{password}, #{nickname}, #{avatar}, #{signature}, #{phone}, #{email}, #{gender}, #{birthday}, #{status}, #{userNo})")
    void insert(User user);

    /**
     * 查找所有用户
     *
     * @return 用户列表
     */
    @Select("SELECT * FROM admin_user")
    List<User> findAll();
}
