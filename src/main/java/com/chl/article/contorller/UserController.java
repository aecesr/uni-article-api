package com.chl.article.contorller;

import com.chl.article.common.ResponseResult;
import com.chl.article.common.ResultCode;
import com.chl.article.mapper.UserMapper;
import com.chl.article.model.dto.LoginDto;
import com.chl.article.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDate;

/**
 * @program: uni-article-api
 * @description:
 * @Author: 曹红亮
 * @create: 2022-03-22 20:45
 **/
@RestController
@RequestMapping("api/v1/users")
@Slf4j
@CrossOrigin
public class UserController {
    @Resource
    private UserService userService;

    @PostMapping("/login")
    public ResponseResult login(@RequestBody LoginDto LoginDto) {
        log.info("loginDto:"+LoginDto);
        boolean login = userService.login(LoginDto);
        if (login) {
            return ResponseResult.success(userService.getUser(LoginDto.getPhone()));

        } else {
            return ResponseResult.failure(ResultCode.USER_SIGN_IN_FAIL);
        }

    }

}
