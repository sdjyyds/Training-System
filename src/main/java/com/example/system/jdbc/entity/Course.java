package com.example.system.jdbc.entity;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class Course {
    private Integer id; // 课程唯一 ID
    private String name; // 课程名称
    private BigDecimal price; // 课程价格
    private String shortDescription; // 简明介绍
    private String longDescription; // 详细介绍
    private String imageUrl; // 课程图片
    private Timestamp createdAt; // 创建时间
    private String videoUrl; // 课程视频

    public Course() {
    }

    public Course(Integer id, String name, BigDecimal price, String shortDescription,
                  String longDescription, String imageUrl, Timestamp createdAt, String videoUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.imageUrl = imageUrl;
        this.createdAt = createdAt;
        this.videoUrl = videoUrl;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", shortDescription='" + shortDescription + '\'' +
                ", longDescription='" + longDescription + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", createdAt=" + createdAt +
                ", videoUrl='" + videoUrl + '\'' +
                '}';
    }
}

