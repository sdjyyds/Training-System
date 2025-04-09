package com.example.system.jdbc.dao;

import com.example.system.jdbc.entity.UserSensitive;

public interface UserSensitiveDao {
    int deleteByPrimaryKey(Integer userId);

    int insert(UserSensitive record);

    int insertSelective(UserSensitive record);

    UserSensitive selectByPrimaryKey(Integer userId);

    int updateByPrimaryKeySelective(UserSensitive record);

    int updateByPrimaryKey(UserSensitive record);
}