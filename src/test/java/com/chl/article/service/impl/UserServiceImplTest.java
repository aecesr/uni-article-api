package com.chl.article.service.impl;

import com.chl.article.model.dto.LoginDto;
import com.chl.article.model.entity.User;
import com.chl.article.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;


/*
 * @description:
 * @author 曹红亮
 * @date 2022/3/23 14:56
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
@Slf4j
class UserServiceImplTest {
     @Resource
     private UserService userService;

    @Test
    void login() {
        LoginDto loginDto = LoginDto.builder()
                .phone("15505121008")
                .password("123")
                .build();
        boolean flag = userService.login(loginDto);
        assertTrue(flag);
    }

    @Test
    void getUser() {
        User user = userService.getUser("15505121008");
        assertNotNull(user);
        log.info(String.valueOf(user));
    }
}