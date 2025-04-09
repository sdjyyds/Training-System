package com.example.system.jdbc.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * sessions
 * @author 
 */
public class Session implements Serializable {
    /**
     * Session 记录唯一 ID
     */
    private Integer id;

    /**
     * 关联 users 表的 user_id
     */
    private Integer userId;

    /**
     * 会话令牌，用于用户认证
     */
    private String sessionToken;

    /**
     * 会话过期时间
     */
    private Date expiresAt;

    private static final long serialVersionUID = 1L;

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

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public Date getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Date expiresAt) {
        this.expiresAt = expiresAt;
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
        Session other = (Session) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getSessionToken() == null ? other.getSessionToken() == null : this.getSessionToken().equals(other.getSessionToken()))
            && (this.getExpiresAt() == null ? other.getExpiresAt() == null : this.getExpiresAt().equals(other.getExpiresAt()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getSessionToken() == null) ? 0 : getSessionToken().hashCode());
        result = prime * result + ((getExpiresAt() == null) ? 0 : getExpiresAt().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", sessionToken=").append(sessionToken);
        sb.append(", expiresAt=").append(expiresAt);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}