package com.op.admin.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.op.admin.dto.UserChangePasswordDto;
import com.op.admin.dto.UserCreateDto;
import com.op.admin.dto.UserUpdateDto;
import com.op.admin.entity.User;
import com.op.admin.mapper.UserMapper;
import com.op.admin.mapping.UserMapping;
import com.op.admin.service.UserService;
import com.op.admin.utils.PasswordGenerator;
import com.op.admin.vo.UserVo;
import com.op.framework.web.common.api.response.exception.BusinessException;
import org.mybatis.dynamic.sql.SortSpecification;
import org.mybatis.dynamic.sql.select.SelectDSLCompleter;
import org.mybatis.dynamic.sql.select.SimpleSortSpecification;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户 Service Impl
 *
 * @author cdrcool
 */
@Service
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    private final UserMapping userMapping;

    public UserServiceImpl(UserMapper userMapper, UserMapping userMapping) {
        this.userMapper = userMapper;
        this.userMapping = userMapping;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String create(UserCreateDto userCreateDto) {
        User user = userMapping.toUser(userCreateDto);
        // 新建用户随机生成6位数密码
        String password = PasswordGenerator.generate(6);
        user.setPassword(password);
        userMapper.insert(user);
        return password;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void changePassword(UserChangePasswordDto changePasswordDto) {
        Integer id = changePasswordDto.getId();
        User user = userMapper.selectByPrimaryKey(id).orElseThrow(() -> new BusinessException("找不到用户，用户id：" + id));
        userMapping.update(changePasswordDto, user);
        userMapper.updateByPrimaryKey(user);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void update(UserUpdateDto userUpdateDto) {
        Integer id = userUpdateDto.getId();
        User user = userMapper.selectByPrimaryKey(id).orElseThrow(() -> new BusinessException("找不到用户，用户id：" + id));
        userMapping.update(userUpdateDto, user);
        userMapper.updateByPrimaryKey(user);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteById(Integer id) {
        userMapper.deleteByPrimaryKey(id);
    }

    @Override
    public UserVo findById(Integer id) {
        User user = userMapper.selectByPrimaryKey(id).orElse(new User());
        return userMapping.toUserVo(user);
    }

    @Override
    public Page<UserVo> queryPage(Pageable pageable) {
        SortSpecification[] specifications = pageable.getSort().stream()
                .map(order -> {
                    SortSpecification specification = SimpleSortSpecification.of(order.getProperty());
                    if (order.isDescending()) {
                        return specification.descending();
                    }
                    return specification;
                }).toArray(SortSpecification[]::new);
        return PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize()).doSelectPage(() ->
                userMapping.toUserVoList(userMapper.select(SelectDSLCompleter.allRowsOrderedBy(specifications))));
    }
}
