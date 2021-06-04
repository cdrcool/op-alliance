package com.op.admin.service.impl;

import com.github.pagehelper.PageHelper;
import com.op.admin.dto.UserChangePasswordDTO;
import com.op.admin.dto.UserCreateDTO;
import com.op.admin.dto.UserListQueryDTO;
import com.op.admin.dto.UserUpdateDTO;
import com.op.admin.entity.User;
import com.op.admin.mapper.UserDynamicSqlSupport;
import com.op.admin.mapper.UserMapper;
import com.op.admin.mapping.UserMapping;
import com.op.admin.service.UserService;
import com.op.admin.utils.BCryptPasswordEncoder;
import com.op.admin.utils.PasswordGenerator;
import com.op.admin.vo.UserVO;
import com.op.framework.web.common.api.response.exception.BusinessException;
import org.mybatis.dynamic.sql.SortSpecification;
import org.mybatis.dynamic.sql.render.RenderingStrategies;
import org.mybatis.dynamic.sql.select.SimpleSortSpecification;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.mybatis.dynamic.sql.SqlBuilder.*;

/**
 * 用户 Service Impl
 *
 * @author cdrcool
 */
@Service
public class UserServiceImpl implements UserService {
    /**
     * 密码编码器
     */
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private final UserMapper userMapper;
    private final UserMapping userMapping;

    public UserServiceImpl(UserMapper userMapper, UserMapping userMapping) {
        this.userMapper = userMapper;
        this.userMapping = userMapping;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String create(UserCreateDTO userCreateDto) {
        User user = userMapping.toUser(userCreateDto);

        // 新建用户随机生成6位数密码
        String password = PasswordGenerator.generate(6);
        user.setPassword(passwordEncoder.encode(password));

        userMapper.insert(user);
        return password;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void changePassword(UserChangePasswordDTO changePasswordDto) {
        Integer id = changePasswordDto.getId();
        User user = userMapper.selectByPrimaryKey(id).orElseThrow(() -> new BusinessException("找不到用户，用户id：" + id));

        String newPassword = changePasswordDto.getPassword();
        if (!passwordEncoder.matches(newPassword, user.getPassword())) {
            throw new BusinessException("旧密码不正确");
        }

        userMapping.update(changePasswordDto, user);

        user.setPassword(passwordEncoder.encode(newPassword));
        userMapper.updateByPrimaryKey(user);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void update(UserUpdateDTO userUpdateDto) {
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

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @Override
    public UserVO findById(Integer id) {
        User user = userMapper.selectByPrimaryKey(id).orElse(new User());
        return userMapping.toUserVo(user);
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @Override
    public Page<UserVO> queryPage(Pageable pageable, UserListQueryDTO queryDTO) {
        SortSpecification[] specifications = pageable.getSort().stream()
                .map(order -> {
                    SortSpecification specification = SimpleSortSpecification.of(order.getProperty());
                    if (order.isDescending()) {
                        return specification.descending();
                    }
                    return specification;
                }).toArray(SortSpecification[]::new);

        SelectStatementProvider selectStatementProvider = select(UserMapper.selectList)
                .from(UserDynamicSqlSupport.user)
                .where(UserDynamicSqlSupport.orgId, isEqualTo(queryDTO.getOrgId()))
                .and(UserDynamicSqlSupport.gender, isEqualTo(queryDTO.getGender()))
                .and(UserDynamicSqlSupport.status, isIn(queryDTO.getStatus()))
                .and(UserDynamicSqlSupport.username, isLike(queryDTO.getSearchText()),
                        or(UserDynamicSqlSupport.nickname, isLike(queryDTO.getSearchText())),
                        or(UserDynamicSqlSupport.phone, isLike(queryDTO.getSearchText())),
                        or(UserDynamicSqlSupport.email, isLike(queryDTO.getSearchText())))
                .orderBy(specifications)
                .limit(pageable.getPageSize()).offset(pageable.getOffset())
                .build().render(RenderingStrategies.MYBATIS3);

        com.github.pagehelper.Page<UserVO> result = PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize()).doSelectPage(() ->
                userMapping.toUserVoList(userMapper.selectMany(selectStatementProvider)));

        return new PageImpl<>(result.getResult(), pageable, result.getTotal());
    }
}
