package com.example.system.jdbc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 * 与数据库中的user_sensitive表对应
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class UserSensitive{
    /**
     * 关联 users 表的 user_id
     */
    private Integer userId;

    /**
     * 用户密码哈希值
     */
    private String passwordHash;

    /**
     * 用户工资信息
     */
    private BigDecimal salary;

}