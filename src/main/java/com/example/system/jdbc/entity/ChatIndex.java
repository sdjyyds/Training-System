package com.example.system.jdbc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 * 聊天列表
 */
@Component
@Data
@NoArgsConstructor //⾃动⽣成⽆参构造器
@AllArgsConstructor //⾃动⽣成全参构造器
public class ChatIndex {
    String chatName;
    String chatImage;
    int id;
    String type;
}
