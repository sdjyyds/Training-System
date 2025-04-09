package com.example.system.jdbc.entity;

import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * user_sensitive
 * @author 
 */
@Component
public class UserSensitive implements Serializable {
    /**
     * 关联 users 表的 user_id
     */
    private Integer userId;

    /**
     * 用户密码哈希值
     */
    private String passwordHash;

    /**
     * 用户工资信息
     */
    private BigDecimal salary;

    private static final long serialVersionUID = 1L;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        UserSensitive other = (UserSensitive) that;
        return (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getPasswordHash() == null ? other.getPasswordHash() == null : this.getPasswordHash().equals(other.getPasswordHash()))
            && (this.getSalary() == null ? other.getSalary() == null : this.getSalary().equals(other.getSalary()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getPasswordHash() == null) ? 0 : getPasswordHash().hashCode());
        result = prime * result + ((getSalary() == null) ? 0 : getSalary().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", userId=").append(userId);
        sb.append(", passwordHash=").append(passwordHash);
        sb.append(", salary=").append(salary);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}