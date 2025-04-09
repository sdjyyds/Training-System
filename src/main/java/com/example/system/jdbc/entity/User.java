package com.example.system.jdbc.entity;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * users
 * @author 
 */
@Component
public class User implements Serializable {
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
    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHometown() {
        return hometown;
    }

    public void setHometown(String hometown) {
        this.hometown = hometown;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(username, user.username) && Objects.equals(email, user.email) && Objects.equals(phone, user.phone) && Objects.equals(address, user.address) && Objects.equals(hometown, user.hometown) && Objects.equals(role, user.role) && Objects.equals(createdAt, user.createdAt) && Objects.equals(userImage, user.userImage);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id, username, email, phone, address, hometown, role, createdAt, userImage);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", hometown='" + hometown + '\'' +
                ", role='" + role + '\'' +
                ", createdAt=" + createdAt +
                ", userImage='" + userImage + '\'' +
                '}';
    }
}