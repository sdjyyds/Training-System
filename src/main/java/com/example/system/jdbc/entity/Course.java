package com.example.system.jdbc.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course {
    private Integer id; // 课程唯一 ID
    private String name; // 课程名称
    private BigDecimal price; // 课程价格
    private String shortDescription; // 简明介绍
    private String longDescription; // 详细介绍
    private String imageUrl; // 课程图片
    private Timestamp createdAt; // 创建时间
    private String videoUrl; // 课程视频

}

