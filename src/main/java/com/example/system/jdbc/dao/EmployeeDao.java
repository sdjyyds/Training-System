package com.example.system.jdbc.dao;

import com.example.system.jdbc.entity.Employee;
/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 * 与employee表对应
 */

public interface EmployeeDao {
    /**
     * 根据id字段删除对应的employee
     * @param id employee的id
     * @return 返回删除的行数
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 插入employee实体
     * @param record employee实体
     * @return 返回插入的行数
     */
    int insert(Employee record);

    /**
     * 选择性的插入employee
     * @param record employee实体
     * @return 返回插入的行数
     */
    int insertSelective(Employee record);

    /**
     * 根据id字段查找对应的employee
     * @param id employee的id
     * @return 返回employee实体
     */
    Employee selectByPrimaryKey(Integer id);

    /**
     * 有选择性的更新employee实体的数据
     * @param record employee的实体
     * @return 返回更新的行数
     */
    int updateByPrimaryKeySelective(Employee record);

    /**
     * 更新employee实体的数据
     * @param record employee的实体
     * @return 返回更新的行数
     */
    int updateByPrimaryKey(Employee record);
}