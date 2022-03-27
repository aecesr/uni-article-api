package com.chl.article.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.chl.article.mapper.UserMapper;
import com.chl.article.model.dto.BindPhoneDto;
import com.chl.article.model.dto.LoginDto;
import com.chl.article.model.dto.WxLoginDto;
import com.chl.article.model.entity.User;
import com.chl.article.service.RedisService;
import com.chl.article.service.UserService;
import com.chl.article.util.AliyunResource;
import com.chl.article.util.FileResource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.UUID;

/**
 * @program: uni-article-api
 * @description:
 * @Author: 曹红亮
 * @create: 2022-03-22 20:28
 **/
@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private RedisService redisService;
    @Resource
    private FileResource fileResource;
    @Resource
    private AliyunResource aliyunResource;

    @Override
    public boolean login(LoginDto loginDto) {
        User user = getUser(loginDto.getPhone());
        if (user != null) {
            return DigestUtils.md5Hex(loginDto.getPassword()).equals(user.getPassword());
        }
        return false;
    }

    @Override
    public User userupdate(User user) {
        //先根据手机号查出数据库原用户信息
        User savedUser = getUser(user.getPhone());
        System.out.println(savedUser + ":saveuser--------------------");
        //如果是修改密码的请求，需要将传来的密码加密
        if (!user.getPassword().equals(savedUser.getPassword())) {
            savedUser.setPassword(DigestUtils.md5Hex(user.getPassword()));
        } else {
            //否则就是修改其他信息，密码直接赋值，以免被覆盖为空
            savedUser.setPassword(user.getPassword());
        }
        savedUser.setPhone(user.getPhone());
        savedUser.setNickname(user.getNickname());
        savedUser.setAvatar(user.getAvatar());
        savedUser.setGender(user.getGender());
        savedUser.setBirthday(user.getBirthday());
        savedUser.setAddress(user.getAddress());
        savedUser.setBanner(user.getBanner());
        //更新数据
        userMapper.updateUser(savedUser);
        System.out.println("saveuser--------------------:" + savedUser);
        //将修改后的用户信息返回
        return savedUser;
    }

    //绑定手机
    @Override
    public User bindPhone(BindPhoneDto bindPhoneDto) {
        // 此时根据手机号查出数据库中用户信息
        User savedUser = userMapper.findUserByPhone(bindPhoneDto.getPhone());
        if(savedUser==null){
            User userByOpenId = userMapper.findUserByOpenId(bindPhoneDto.getWxOpenId());
            System.out.println(bindPhoneDto.getWxOpenId());
            userByOpenId.setPhone(bindPhoneDto.getPhone());
            System.out.println("1-----------"+userByOpenId);
            userMapper.updateUser(userByOpenId);
            savedUser = userMapper.findUserByPhone(bindPhoneDto.getWxOpenId());
            System.out.println("2-----------"+savedUser);
        }else {
            log.info("1-----" + savedUser);
            // 检查redis中是否有该手机号存储的短信
            boolean flag = redisService.existsKey(bindPhoneDto.getPhone());
            log.info("2------" + flag);
            if (flag) {
                // 取出验证码
                String saveCode = redisService.getValue(bindPhoneDto.getPhone(), String.class);
                //验证码通过
                if (saveCode.equalsIgnoreCase(bindPhoneDto.getCode())) {
                    // 该用户对应的wxOpenId如果空，表示还没绑定
                    if (savedUser.getWxOpenid() == null || savedUser.getWxOpenid().trim().length() == 0) {
                        // 删除wxOpenId对应的用户记录（合并账号）,要先做这条语句哦，要不然会把主账号也删掉
                        userMapper.deleteUserByOpenId(bindPhoneDto.getWxOpenId());
                        //补全该用户的wxOpenId
                        System.out.println(bindPhoneDto.getWxOpenId());
                        savedUser.setWxOpenid(bindPhoneDto.getWxOpenId());
                        log.info("3-----" + savedUser);
                        // 更新该手机号对应的记录信息（持久化wxOpenId）
                        userMapper.bandPhone(bindPhoneDto.getPhone(), bindPhoneDto.getWxOpenId());
                        savedUser = userMapper.findUserByPhone(bindPhoneDto.getPhone());
                    }
                }
            }
        }

        // 返回用户信息
        return savedUser;
    }

    @Override
    public User getUser(String phone) {
        return userMapper.findUserByPhone(phone);
    }
    //根据手机号销毁用户数据

    @Override
    public String uplaodFile(MultipartFile file) {
//        读取配置文件信息
        String endPoint = fileResource.getEndPoint();
        String accessKeyId = aliyunResource.getAccessKeyId();
        String accessKeySecret = aliyunResource.getAccessKeySecret();
        System.out.println(endPoint + "-------------------------------------------" + accessKeyId + "-------------------------------------------" + accessKeySecret);
//        创建OSSlient实例
        OSS ossClient = new OSSClientBuilder().build(endPoint, accessKeyId, accessKeySecret);
        String originalFilename = file.getOriginalFilename();
//        分隔文件名，获得文件后缀名
        assert originalFilename != null;
        String[] split = originalFilename.split("\\.");
        String s = split[split.length - 1];
//        拼接得到新的上传文件名
        String uploadFileName = fileResource.getObjectName() + UUID.randomUUID() + "." + s;
//        上传网络需要用的字节流
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
        } catch (IOException e) {
            System.out.println("上传文件异常");
        }
//        执行阿里云上传文件操作
        ossClient.putObject(fileResource.getBucketName(), uploadFileName, inputStream);
//        关闭OSSlient
        ossClient.shutdown();
        return uploadFileName;
    }

    @Override
    public User wxLogin(WxLoginDto wxLoginDto) {
        User user = findeByOpenId(wxLoginDto.getWxOpenid());
        if (user == null) {
            user = User.builder()
                    .phone("")
                    .wxOpenid(wxLoginDto.getWxOpenid())
                    .nickname(wxLoginDto.getNickname())
                    .avatar(wxLoginDto.getAvatar())
                    .gender(Integer.valueOf(wxLoginDto.getGender()))
                    .address("江苏省南京市")
                    .banner("https://chl-bucket.oss-cn-hangzhou.aliyuncs.com/img/banner4.jpg")
                    .create_time(new Date()).build();
            System.out.println(user);
            userMapper.insert(user);

        }
        return user;
    }

    @Override
    public User findeByOpenId(String wxOpenId) {
        return userMapper.findUserByOpenId(wxOpenId);
    }

    @Override
    public boolean destroyByCode(String phone) {
        userMapper.deleteUserByPhone(phone);
        return true;
    }

    @Override
    public boolean loginByCode(LoginDto loginDto) {
        boolean b = redisService.existsKey(loginDto.getPhone());
        if (b) {
            String value = redisService.getValue(loginDto.getPhone(), String.class);
            if (value.equalsIgnoreCase(loginDto.getCode())) {
                User user = getUser(loginDto.getPhone());
                System.out.println(user);
                if (user == null) {
                    User saveUser = User.builder()
                            .phone(loginDto.getPhone())
                            .nickname("新用户")
                            .avatar("../../static/img/nologin.jpeg")
                            .create_time(new Date())
                            .banner("../../static/img/banner5.jpg")
                            .build();
                    userMapper.insert(saveUser);

                }
                return true;
            }

        }
        return false;
    }
}
