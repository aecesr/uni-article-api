package com.chl.article.service;

import com.chl.article.model.dto.BindPhoneDto;
import com.chl.article.model.dto.LoginDto;
import com.chl.article.model.dto.WxLoginDto;
import com.chl.article.model.entity.User;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    boolean login(LoginDto loginDto);

    User getUser(String phone);

    boolean loginByCode(LoginDto loginDto);

    boolean destroyByCode(String phone);

    //微信登录
    User wxLogin(WxLoginDto wxLoginDto);

    //根据微信openid查询用户
    User findeByOpenId(String wxOpenId);

    //上传文件到OSS
    String uplaodFile(MultipartFile file);

    //修改个人信息
    User userupdate(User user);

    //    绑定手机
    User bindPhone(BindPhoneDto bindPhoneDto);

}
