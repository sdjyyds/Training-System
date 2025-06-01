package com.example.system.jdbc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 * 与数据库中的employee表对应
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee{
    /**
     * 员工唯一 ID
     */
    private Integer id;

    /**
     * 关联 users 表的 user_id
     */
    private Integer userId;

    /**
     * 员工所属部门
     */
    private String department;
}