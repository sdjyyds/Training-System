package com.example.system.jdbc.dao;

import com.example.system.jdbc.entity.UserSensitive;
/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 * 与数据库中的product表对应
 */
public interface UserSensitiveDao {
    int deleteByPrimaryKey(Integer userId);

    int insert(UserSensitive record);

    int insertSelective(UserSensitive record);

    UserSensitive selectByPrimaryKey(Integer userId);

    int updateByPrimaryKeySelective(UserSensitive record);

    int updateByPrimaryKey(UserSensitive record);
}