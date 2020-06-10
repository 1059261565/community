package com.weikai.community.mapper;

import com.weikai.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface UserMapper {

        @Insert("insert into community_user (name,account_Id,token,gmt_create,gmt_modified) values (#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified})")
        void insertUser(User user);
}
