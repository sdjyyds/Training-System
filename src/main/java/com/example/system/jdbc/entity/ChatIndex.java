package com.example.system.jdbc.entity;

import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 * 聊天列表
 */
@Component
public class ChatIndex {
    String chatName;
    String chatImage;
    int id;
    String type;

    public ChatIndex(int id, String chatName, String chatImage, String type) {
        this.id = id;
        this.chatName = chatName;
        this.chatImage = chatImage;
        this.type = type;
    }

    public ChatIndex() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getChatName() {
        return chatName;
    }

    public void setChatName(String chatName) {
        this.chatName = chatName;
    }

    public String getChatImage() {
        return chatImage;
    }

    public void setChatImage(String chatImage) {
        this.chatImage = chatImage;
    }

    @Override
    public String toString() {
        return "ChatIndex{" +
                "chatName='" + chatName + '\'' +
                ", chatImage='" + chatImage + '\'' +
                ", id=" + id +
                ", type='" + type + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChatIndex)) return false;
        ChatIndex chatIndex = (ChatIndex) o;
        return id == chatIndex.id && Objects.equals(chatName, chatIndex.chatName) && Objects.equals(chatImage, chatIndex.chatImage) && Objects.equals(type, chatIndex.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(chatName, chatImage, id, type);
    }
}
