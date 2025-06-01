package com.example.system.jdbc.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 * 与数据库中的user表对应
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class User{
    /**
     * 用户唯一 ID
     */
    private Integer id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户邮箱，唯一
     */
    private String email;

    /**
     * 用户手机号，唯一
     */
    private String phone;

    /**
     * 用户居住地址
     */
    private String address;

    /**
     * 用户籍贯
     */
    private String hometown;

    /**
     * 用户角色，区分不同身份
     */
    private String role;

    /**
     * 用户创建时间
     */
    private Date createdAt;
    /**
     * 用户头像
     */
    private String userImage;
}