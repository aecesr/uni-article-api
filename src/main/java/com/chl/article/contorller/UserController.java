package com.chl.article.contorller;

import com.chl.article.common.ResponseResult;
import com.chl.article.common.ResultCode;
import com.chl.article.model.dto.BindPhoneDto;
import com.chl.article.model.dto.LoginDto;
import com.chl.article.model.dto.WxLoginDto;
import com.chl.article.model.entity.User;
import com.chl.article.service.RedisService;
import com.chl.article.service.impl.UserServiceImpl;
import com.chl.article.util.FileResource;
import com.chl.article.util.SmsUtil;
import com.chl.article.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    private UserServiceImpl userService;

    @Resource
    private SmsUtil smsUtil;

    @Resource
    private RedisService redisService;
    @Resource
    private FileResource fileResource;


    @GetMapping("/")
    public String logins() {

        return " aaaa";


    }

    @PostMapping("/login")
    public ResponseResult login(@RequestBody LoginDto LoginDto) {
        log.info("loginDto:" + LoginDto);
        boolean login = userService.login(LoginDto);
        if (login) {
            return ResponseResult.success(userService.getUser(LoginDto.getPhone()));

        } else {
            return ResponseResult.failure(ResultCode.USER_SIGN_IN_FAIL);
        }

    }

    @PostMapping("/sms")
    public ResponseResult sendSms(@RequestParam String phone) {

        String Code = StringUtil.getVerifyCode();

        boolean b = smsUtil.sendSms(phone, Code);
        redisService.set(phone, Code, 1L);
        return ResponseResult.success(Code);

//        if (b) {
//            redisService.set(phone, Code, 1L);
//            return ResponseResult.success(Code);
//        } else {
//            redisService.set(phone, Code, 1L);
//            return ResponseResult.failure(ResultCode.SMS_ERROR);
//        }
    }

    @PostMapping("/login/sms")
    public ResponseResult loginByCode(@RequestBody LoginDto LoginDto) {
        log.info("LoginDto:" + LoginDto);
        boolean b = userService.loginByCode(LoginDto);
        log.info("b:::" + b);
        if (b) {
            return ResponseResult.success(userService.getUser(LoginDto.getPhone()));
        } else {
            return ResponseResult.failure(ResultCode.USER_SIGN_IN_FAIL);
        }
    }

    //    微信登录
    @PostMapping("/login/wx")
    public ResponseResult wxlogin(@RequestBody WxLoginDto wxLoginDto) {
        log.info("LoginDto:" + wxLoginDto);
        User b = userService.wxLogin(wxLoginDto);
        log.info("b:::" + b);
        if (b == null) {
            return ResponseResult.success(userService.findeByOpenId(wxLoginDto.getWxOpenid()));
        } else {
            return ResponseResult.success(userService.findeByOpenId(wxLoginDto.getWxOpenid()));
        }
    }

    //    注销用户接口
    @PostMapping("/destroy/sms")
    public boolean destroyByCode(@RequestBody String phone) {
        boolean b = userService.destroyByCode(phone);
        if (b) {
            System.out.println(b);
            return true;
        }
        return false;
    }

    @PostMapping("/upload")
    public ResponseResult uploadFile(MultipartFile file) {
        String path = null;
        if (file != null) {
            String originalFilename = file.getOriginalFilename();
            path = userService.uplaodFile(file);

        }
        if (StringUtils.isNoneBlank(path)) {
            path = fileResource.getOssHost() + path;
        }
        System.out.println(path + "--------------------------");
        return ResponseResult.success(path);
    }

    @PostMapping("/update")
    public User update(@RequestBody User user) {
        return userService.userupdate(user);
    }

    //绑定手机
    @PostMapping(value = "/bind")
    public ResponseResult bindPhone(@RequestBody BindPhoneDto bindPhoneDto) {
        log.info(String.valueOf(bindPhoneDto));
        User user = userService.bindPhone(bindPhoneDto);
        return ResponseResult.success(user);
    }

}
