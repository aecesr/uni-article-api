package com.chl.article.mapper;

import com.chl.article.model.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface UserMapper {

    @Insert("INSERT INTO t_user(phone,password,nickname,avatar,gender,birthday,address,create_time)" +
            "VALUES(#{phone},#{password},#{nickname},#{avatar},#{gender},#{birthday},#{address},#{create_time})")

    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(User user);

    @Select("SELECT * FROM t_user WHERE phone = #{phone}")
    User findUserByPhone(@Param("phone") String phone);
}
