package com.weikai.community.mapper;

import com.weikai.community.dto.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


@Mapper
public interface UserMapper {

        @Insert("insert into user (name,accountId,token,gmt_create,gmt_modified) values (#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified})")
        void insertUser(User user);

        @Select("select * from user where token=#{token}")
        User findBytoken(@Param("token") String token);
}
