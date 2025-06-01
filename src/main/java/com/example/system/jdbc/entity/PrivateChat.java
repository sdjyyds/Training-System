package com.example.system.jdbc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 * 与数据库中的private_chat表对应
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrivateChat{
    /**
     * 私聊消息唯一 ID
     */
    private Integer id;

    /**
     * 消息发送者用户 ID
     */
    private Integer senderId;

    /**
     * 消息接收者用户 ID
     */
    private Integer receiverId;

    /**
     * 消息发送时间
     */
    private Date timestamp;

    /**
     * 聊天消息内容
     */
    private String message;
}