package com.example.system.jdbc.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCourse {
    private Integer id; // 记录 ID
    private Integer userId; // 用户 ID
    private Integer courseId; // 课程 ID
    private Timestamp purchasedAt; // 购买时间
}

