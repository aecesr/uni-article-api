package com.chl.article.mapper;

import com.chl.article.model.dto.WxLoginDto;
import com.chl.article.model.entity.User;
import org.apache.ibatis.annotations.*;

public interface UserMapper {

    @Insert("INSERT INTO t_user(phone,wx_openid,password,nickname,avatar,gender,birthday,address,create_time,icon,banner)" +
            "VALUES(#{phone},#{wxOpenid},#{password},#{nickname},#{avatar},#{gender},#{birthday},#{address},#{create_time},#{icon},#{banner})")

    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(User user);

    @Select("SELECT * FROM t_user WHERE phone = #{phone}")
    User findUserByPhone(@Param("phone") String phone);

    @Delete("delete  from t_user WHERE phone=#{phone}")
    User deleteUserByPhone(@Param("phone") String phone);

    //    根据openid去找用户
    @Select("select * from t_user where wx_openid=#{wxOpenid}")
    User findUserByOpenId(@Param("wxOpenid") String wxOpenid);

    // 修改用户信息
    @Update("UPDATE t_user SET password=#{password},nickname=#{nickname},avatar=#{avatar}," +
            "gender=#{gender},birthday=#{birthday},address=#{address},banner=#{banner},wx_openid=#{wxOpenid},phone=#{phone} WHERE id=#{id} ")
    void updateUser(User user);

    //绑定手机号
    @Update(("UPDATE t_user SET wx_openid=#{wxOpenid} WHERE phone=#{phone} "))
    void bandPhone(@Param("phone") String phone, @Param("wxOpenid") String wxOpenid);


    //根据wxOpenId删除用户

    @Delete(("DELETE FROM t_user WHERE wx_openid=#{wxOpenId} "))
    void deleteUserByOpenId(@Param("wxOpenId") String wxOpenId);


}
