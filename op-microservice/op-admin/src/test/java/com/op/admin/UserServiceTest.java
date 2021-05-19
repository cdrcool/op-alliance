package com.op.admin;

import com.github.pagehelper.Page;
import com.op.admin.dto.UserChangePasswordDto;
import com.op.admin.dto.UserCreateDto;
import com.op.admin.dto.UserUpdateDto;
import com.op.admin.mapper.UserDynamicSqlSupport;
import com.op.admin.service.UserService;
import com.op.admin.vo.UserVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @Test
    public void create() {
        UserCreateDto userCreateDto = new UserCreateDto();
        userCreateDto.setUsername("tom");
        userCreateDto.setUserNo(2);
        userService.create(userCreateDto);
    }

    @Test
    public void update() {
        UserUpdateDto userUpdateDto = new UserUpdateDto();
        userUpdateDto.setId(1);
        userUpdateDto.setBirthday(LocalDate.parse("2002-01-24", DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        userService.update(userUpdateDto);
    }

    @Test
    public void changePassword() {
        UserChangePasswordDto changePasswordDto = new UserChangePasswordDto();
        changePasswordDto.setId(1);
        changePasswordDto.setOldPassword("123");
        changePasswordDto.setNewPassword("admin");
        changePasswordDto.setNewPasswordConfirm("admin");
        userService.changePassword(changePasswordDto);
    }

    @Test
    public void findById() {
        UserVo userVo = userService.findById(1);
    }

    @Test
    public void deleteById() {
        userService.deleteById(1);
    }

    @Test
    public void queryPage() {
        Pageable pageable = PageRequest.of(1, 10, Sort.by(Sort.Order.desc(UserDynamicSqlSupport.id.name())));
        Page<UserVo> page = userService.queryPage(pageable);
    }
}
