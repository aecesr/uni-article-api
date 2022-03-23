package com.chl.article.service;

import com.chl.article.model.dto.LoginDto;
import com.chl.article.model.entity.User;

public interface UserService {
    boolean login(LoginDto loginDto) ;

    User getUser(String phone);
}
