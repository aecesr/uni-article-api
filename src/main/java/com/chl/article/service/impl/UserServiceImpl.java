package com.chl.article.service.impl;

import com.chl.article.mapper.UserMapper;
import com.chl.article.model.dto.LoginDto;
import com.chl.article.model.entity.User;
import com.chl.article.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @program: uni-article-api
 * @description:
 * @Author: 曹红亮
 * @create: 2022-03-22 20:28
 **/
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

    @Override
    public boolean login(LoginDto loginDto) {
        User user = getUser(loginDto.getPhone());
        if (user != null) {
            return loginDto.getPassword().equals(user.getPassword());
        }
        return false;
    }

    @Override
    public User getUser(String phone) {
        return userMapper.findUserByPhone(phone);
    }

}
