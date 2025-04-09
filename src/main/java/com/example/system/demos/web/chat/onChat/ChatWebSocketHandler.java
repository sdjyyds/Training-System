package com.example.system.demos.web.chat.onChat;

import com.example.system.demos.web.chat.showChat.ShowChatService;
import com.example.system.jdbc.entity.Message;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 */
@Component // 声明为 Spring Bean，由 Spring 容器管理
public class ChatWebSocketHandler implements WebSocketHandler {

    /**
     * userSessions：记录每个用户的 WebSocket 连接（允许多个浏览器或标签页）
     * roomSessions：记录每个聊天室对应的 WebSocket 连接集合
     */
    private final Map<Integer, List<WebSocketSession>> userSessions = new ConcurrentHashMap<>();
    private final Map<Integer, List<WebSocketSession>> roomSessions = new ConcurrentHashMap<>();

    @Autowired
    private ShowChatService chatService; // 业务逻辑接口，处理消息持久化与用户名查询

    private final ObjectMapper objectMapper = new ObjectMapper(); // 用于序列化和反序列化 JSON 消息

    /**
     * 当用户通过 WebSocket 建立连接后调用
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        Map<String, String> pathParams = getPathParams(session); // 从路径中提取类型和 ID
        String type = pathParams.get("type"); // "private" or "group"
        int chatId = Integer.parseInt(pathParams.get("id"));
        System.out.println("session: " + session);
        System.out.println(session.getAttributes().get("user"));
        if (session.getAttributes().get("user") == null) {
            try {
                session.close();
            } catch (IOException ignored) {}
            return;
        }
        Integer userId = Integer.parseInt(session.getAttributes().get("user").toString()); // 从 session 中获取当前用户 ID
        System.out.println("用户 " + userId + " 建立连接，类型: " + type + "，目标ID: " + chatId);

        // 将此连接添加到当前用户的连接列表中
        userSessions.computeIfAbsent(userId, k -> new ArrayList<>()).add(session);

        // 如果是群聊类型，再添加到对应聊天室的连接列表中
        if ("group".equals(type)) {
            roomSessions.computeIfAbsent(chatId, k -> new ArrayList<>()).add(session);
        }
    }

    /**
     * 客户端发送消息后调用（服务端接收到消息）
     */
    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws IOException {
        Map<String, String> pathParams = getPathParams(session);
        String type = pathParams.get("type"); // "private" or "group"
        int chatId = Integer.parseInt(pathParams.get("id"));
        Integer senderId = Integer.parseInt(session.getAttributes().get("user").toString()); // 从 session 中获取当前用户

        if (senderId == null) return; // 安全判断

        // 反序列化前端传来的消息 JSON
        Map<String, Object> payload = objectMapper.readValue(message.getPayload().toString(), Map.class);
        String content = (String) payload.get("content");

        // 根据 senderId 查询发送者昵称
        String senderName = chatService.getUsernameById(senderId);

        // 构造后端广播的消息对象
        Message msg = new Message();
        msg.setSenderName(senderName);
        msg.setContent(content);

        // 根据聊天类型分别处理
        if ("private".equals(type)) {
            chatService.savePrivateMessage(senderId, chatId, content); // 保存到数据库
            broadcastPrivateMessage(senderId, chatId, msg); // 转发消息
        } else if ("group".equals(type)) {
            chatService.saveGroupMessage(senderId, chatId, content); // 保存群聊消息
            broadcastGroupMessage(chatId, msg); // 群发消息
        }
    }

    /**
     * 发送私聊消息：发给 sender 和 receiver 两端（确保两边都更新）
     */
    private void broadcastPrivateMessage(int senderId, int receiverId, Message msg) throws IOException {
        String json = objectMapper.writeValueAsString(msg);

        // 给对方发送
        for (WebSocketSession session : userSessions.getOrDefault(receiverId, Collections.emptyList())) {
            if (session.isOpen()) session.sendMessage(new TextMessage(json));
        }
        // 给自己发送
        for (WebSocketSession session : userSessions.getOrDefault(senderId, Collections.emptyList())) {
            if (session.isOpen()) session.sendMessage(new TextMessage(json));
        }
    }

    /**
     * 群聊消息广播给所有当前加入该聊天室的用户
     */
    private void broadcastGroupMessage(int roomId, Message msg) throws IOException {
        String json = objectMapper.writeValueAsString(msg);

        for (WebSocketSession session : roomSessions.getOrDefault(roomId, Collections.emptyList())) {
            if (session.isOpen()) session.sendMessage(new TextMessage(json));
        }
    }

    /**
     * 当连接关闭时调用（前端断开连接、刷新、关闭标签页等）
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        if (session.getAttributes().get("user") != null) {
            Integer userId = Integer.parseInt(session.getAttributes().get("user").toString());
            // 从 userSessions 中移除该连接
            userSessions.getOrDefault(userId, new ArrayList<>()).remove(session);
            // 从所有房间中也尝试清理该连接（防止内存泄漏）
            for (List<WebSocketSession> list : roomSessions.values()) {
                list.remove(session);
            }
        }
    }

    /**
     * 连接中发生异常
     */
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) {
        exception.printStackTrace();
    }

    /**
     * 是否支持消息分片（我们不支持）
     */
    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    /**
     * 从 URI 路径中提取路径参数：类型 + ID
     * 例如路径为 /ws/chat/private/3，得到 {type=private, id=3}
     */
    private Map<String, String> getPathParams(WebSocketSession session) {
        String path = session.getUri().getPath();  // e.g., "/ws/chat/private/3"
        String[] segments = path.split("/");
        Map<String, String> map = new HashMap<>();
        map.put("type", segments[3]);  // "private" or "group"
        map.put("id", segments[4]);    // chatId
        return map;
    }
}




