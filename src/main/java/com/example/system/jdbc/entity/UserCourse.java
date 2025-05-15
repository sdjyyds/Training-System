package com.example.system.jdbc.entity;
import java.sql.Timestamp;

public class UserCourse {
    private Integer id; // 记录 ID
    private Integer userId; // 用户 ID
    private Integer courseId; // 课程 ID
    private Timestamp purchasedAt; // 购买时间

    public UserCourse() {
    }

    public UserCourse(Integer id, Integer userId, Integer courseId, Timestamp purchasedAt) {
        this.id = id;
        this.userId = userId;
        this.courseId = courseId;
        this.purchasedAt = purchasedAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public Timestamp getPurchasedAt() {
        return purchasedAt;
    }

    public void setPurchasedAt(Timestamp purchasedAt) {
        this.purchasedAt = purchasedAt;
    }

    @Override
    public String toString() {
        return "UserCourse{" +
                "id=" + id +
                ", userId=" + userId +
                ", courseId=" + courseId +
                ", purchasedAt=" + purchasedAt +
                '}';
    }
}

