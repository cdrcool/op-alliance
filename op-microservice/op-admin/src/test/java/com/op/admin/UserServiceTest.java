package com.op.admin;

import com.op.admin.mapper.UserDynamicSqlSupport;
import com.op.admin.dto.UserChangePasswordDTO;
import com.op.admin.dto.UserCreateDTO;
import com.op.admin.dto.UserSaveDTO;
import com.op.admin.service.UserService;
import com.op.admin.vo.UserVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
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
        UserCreateDTO userCreateDto = new UserCreateDTO();
        userCreateDto.setOrgId(1);
        userCreateDto.setUsername("tom");
        userCreateDto.setUserNo(2);
        userService.create(userCreateDto);
    }

    @Test
    public void update() {
        UserSaveDTO userSaveDto = new UserSaveDTO();
        userSaveDto.setId(1);
        userSaveDto.setBirthday(LocalDate.parse("2002-01-24", DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        userService.save(userSaveDto);
    }

    @Test
    public void changePassword() {
        UserChangePasswordDTO changePasswordDto = new UserChangePasswordDTO();
        changePasswordDto.setId(1);
        changePasswordDto.setOldPassword("123");
        changePasswordDto.setPassword("admin");
        changePasswordDto.setConfirmPassword("admin");
        userService.changePassword(changePasswordDto);
    }

    @Test
    public void findById() {
        UserVO userVo = userService.findById(1);
    }

    @Test
    public void deleteById() {
        userService.deleteById(1);
    }

    @Test
    public void queryPage() {
        Pageable pageable = PageRequest.of(1, 10, Sort.by(Sort.Order.desc(UserDynamicSqlSupport.id.name())));
        Page<UserVO> page = userService.queryPage(pageable, null);
    }
}
