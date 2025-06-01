package com.example.system.jdbc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 * 聊天室
 */
@Data
@NoArgsConstructor //⾃动⽣成⽆参构造器
@AllArgsConstructor //⾃动⽣成全参构造器
public class ChatRoom{
    /**
     * 聊天室唯一 ID
     */
    private Integer id;

    /**
     * 聊天室名称
     */
    private String name;

    /**
     * 聊天室创建时间
     */
    private Date createdAt;
    private String chatImage;
}