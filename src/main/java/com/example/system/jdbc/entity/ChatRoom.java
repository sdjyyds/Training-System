package com.example.system.jdbc.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * chat_rooms
 * @author 
 */
public class ChatRoom implements Serializable {
    /**
     * 聊天室唯一 ID
     */
    private Integer id;

    /**
     * 聊天室名称
     */
    private String name;

    /**
     * 聊天室创建时间
     */
    private Date createdAt;
    private String chatImage;

    private static final long serialVersionUID = 1L;

    public String getChatImage() {
        return chatImage;
    }

    public void setChatImage(String chatImage) {
        this.chatImage = chatImage;
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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChatRoom)) return false;
        ChatRoom chatRoom = (ChatRoom) o;
        return Objects.equals(id, chatRoom.id) && Objects.equals(name, chatRoom.name) && Objects.equals(createdAt, chatRoom.createdAt) && Objects.equals(chatImage, chatRoom.chatImage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, createdAt, chatImage);
    }

    @Override
    public String toString() {
        return "ChatRoom{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", createdAt=" + createdAt +
                ", chatImage='" + chatImage + '\'' +
                '}';
    }
}