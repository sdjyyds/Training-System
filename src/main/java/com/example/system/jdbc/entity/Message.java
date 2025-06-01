package com.example.system.jdbc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 * 用来封装消息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    private String senderName;
    private String content;
}
