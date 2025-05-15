package com.example.system.jdbc.dao;

import com.example.system.jdbc.entity.ChatIndex;
import com.example.system.jdbc.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 * 与数据库中的users表对应
 */
public interface UserDao {
    /**
     * 根据主键删除
     *
     * @param id users的id
     * @return 返回删除的行数
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 插入user实体
     *
     * @param record user的实体
     * @return 返回插入的行数
     */
    int insert(User record);

    /**
     * 选择性的插入
     *
     * @param record user的实体
     * @return 返回插入的行数
     */
    int insertSelective(User record);

    /**
     * 根据phone字段查找对应的user
     *
     * @param phone user的phone
     * @return 返回是否存在该手机号
     */
    Boolean existPhone(String phone);

    /**
     * 根据email字段查找对应的user
     *
     * @param email user的email
     * @return 返回是否存在该邮箱
     */
    Boolean existEmail(String email);

    /**
     * 根据id字段查找对应的user
     *
     * @param id user的id
     * @return 返回user
     */
    User selectByPrimaryKey(Integer id);

    /**
     * 根据email和password字段查找对应的user
     *
     * @param account  user的email
     * @param password user的password
     * @return 返回user
     */
    User findByEmailAndPassword(@Param("email") String account, @Param("password") String password);

    /**
     * 根据phone和password字段查找对应的user
     *
     * @param account user的phone
     * @param password user的password
     * @return 返回user
     */
    User findByPhoneAndPassword(@Param("phone") String account, @Param("password") String password);

    /**
     * 根据id字段查找对应的username
     * @param id user的id
     * @return 返回username
     */
    String selectUserNameById(Integer id);

    /**
     * 根据user的id字段查找通讯过的user
     * @param id user的id
     * @return 返回user
     */
    List<ChatIndex> selectPrivateChatUser(Integer id);

    /**
     * 根据user的id字段查找通讯过的聊天室
     * @param id user的id
     * @return 返回聊天室
     */
    List<ChatIndex> selectChatRoomsName(Integer id);

    /**
     * 根据email字段查找对应的user
     * @param email user的email
     * @return 返回user
     */
    int selectPrimaryKeyByEmail(String email);

    /**
     * 根据phone字段查找对应的user
     * @param phone user的phone
     * @return 返回user
     */
    int selectPrimaryKeyByPhone(String phone);

    /**
     * 选择性的更新
     * @param record user的实体
     * @return 返回更新的行数
     */
    int updateByPrimaryKeySelective(User record);

    /**
     * 更新
     * @param record user的实体
     * @return 返回更新的行数
     */
    int updateByPrimaryKey(User record);
}