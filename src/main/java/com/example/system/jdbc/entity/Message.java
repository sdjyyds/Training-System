package com.example.system.jdbc.entity;

/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 */
public class Message {
    private String senderName;
    private String content;

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Message{" +
                "senderName='" + senderName + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
