package com.example.system.jdbc.dao;

import com.example.system.jdbc.entity.Employee;

public interface EmployeeDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Employee record);

    int insertSelective(Employee record);

    Employee selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Employee record);

    int updateByPrimaryKey(Employee record);
}