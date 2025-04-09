package com.example.system.jdbc.dao;

import com.example.system.jdbc.entity.ChatIndex;
import com.example.system.jdbc.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserDao {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);
    int insertSelective(User record);
    Boolean existPhone(String phone);
    Boolean existEmail(String email);
    User selectByPrimaryKey(Integer id);
    //这个sql语句如何写？
    User findByEmailAndPassword(@Param("email") String account, @Param("password") String password);
    //这个sql语句如何写？
    User findByPhoneAndPassword(@Param("phone") String account, @Param("password") String password);
    String selectUserNameById(Integer id);
    List<ChatIndex> selectPrivateChatUser(Integer id);
    List<ChatIndex> selectChatRoomsName(Integer id);
    int selectPrimaryKeyByEmail(String email);
    int selectPrimaryKeyByPhone(String phone);
    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}