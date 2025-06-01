package com.example.system.demos.web.chat.onChat;

import com.example.system.demos.web.chat.showChat.ShowChatService;
import com.example.system.jdbc.entity.Message;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * WebSocket聊天室核心处理器
 * 功能：
 * 1. 管理用户/群组的WebSocket会话
 * 2. 处理私聊/群聊消息收发
 * 3. 维护会话生命周期
 *
 * @author jds
 * @version 1.1
 */
@Component
@Slf4j
public class ChatWebSocketHandler implements WebSocketHandler {

    //------------------------ 会话存储结构 ------------------------
    /**
     * 用户会话映射表
     * Key: 用户ID
     * Value: 该用户所有活跃的WebSocket会话（支持多设备登录）
     */
    private final Map<Integer, List<WebSocketSession>> userSessions = new ConcurrentHashMap<>();

    /**
     * 群聊会话映射表
     * Key: 群聊ID
     * Value: 当前群聊中所有用户的WebSocket会话
     */
    private final Map<Integer, List<WebSocketSession>> roomSessions = new ConcurrentHashMap<>();

    //------------------------ 依赖服务 ------------------------
    @Autowired
    private ShowChatService chatService; // 消息持久化服务

    private final ObjectMapper objectMapper = new ObjectMapper(); // JSON序列化工具

    //------------------------ 核心方法 ------------------------

    /**
     * 处理新WebSocket连接
     * 流程：
     * 1. 解析路径参数 → 2. 验证会话 → 3. 注册会话
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        // 1. 从URI路径中提取参数：/chat/{type}/{chatId}/{userId}
        Map<String, String> pathParams = getPathParams(session);
        String type = pathParams.get("type");      // 聊天类型: private/group
        int chatId = Integer.parseInt(pathParams.get("chatId")); // 聊天目标ID
        int userId = Integer.parseInt(pathParams.get("userId")); // 当前用户ID

        log.info("新连接: 用户[{}]→{}聊天[{}]", userId, type, chatId);

        // 2. 注册会话到用户映射表
        userSessions.computeIfAbsent(userId, k -> new ArrayList<>()).add(session);

        // 3. 如果是群聊，额外注册到群聊映射表
        if ("group".equals(type)) {
            roomSessions.computeIfAbsent(chatId, k -> new ArrayList<>()).add(session);
            log.info("用户[{}]加入群组[{}]", userId, chatId);
        }
    }

    /**
     * 处理收到的WebSocket消息
     * 流程：
     * 1. 解析消息 → 2. 持久化消息 → 3. 广播消息
     */
    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws IOException {
        // 1. 解析基础参数
        Map<String, String> pathParams = getPathParams(session);
        String type = pathParams.get("type");
        int chatId = Integer.parseInt(pathParams.get("chatId"));
        Integer senderId = Integer.parseInt(pathParams.get("userId"));

        // 2. 解析消息内容（JSON格式）
        Map<String, Object> payload = objectMapper.readValue(message.getPayload().toString(), Map.class);
        String content = (String) payload.get("content"); // 消息正文
        String senderName = chatService.getUsernameById(senderId); // 获取发送者名称

        // 3. 构建消息对象
        Message msg = new Message();
        msg.setSenderName(senderName);
        msg.setContent(content);

        log.info("消息路由: {}[{}]→{}[{}]", senderName, senderId, type, chatId);

        // 4. 根据聊天类型处理
        if ("private".equals(type)) {
            chatService.savePrivateMessage(senderId, chatId, content); // 持久化
            broadcastPrivateMessage(senderId, chatId, msg); // 广播
        } else if ("group".equals(type)) {
            chatService.saveGroupMessage(senderId, chatId, content);
            broadcastGroupMessage(chatId, msg);
        }
    }

    //------------------------ 广播方法 ------------------------

    /**
     * 广播私聊消息（发送者+接收者双向通知）
     */
    private void broadcastPrivateMessage(int senderId, int receiverId, Message msg) throws IOException {
        String json = objectMapper.writeValueAsString(msg); // JSON序列化

        // 1. 发送给接收者
        sendToUser(receiverId, json, "私聊消息接收");

        // 2. 回显给发送者
        sendToUser(senderId, json, "私聊消息回显");
    }

    /**
     * 广播群聊消息（全房间成员）
     */
    private void broadcastGroupMessage(int roomId, Message msg) throws IOException {
        String json = objectMapper.writeValueAsString(msg);

        // 遍历房间所有会话并发送
        for (WebSocketSession session : roomSessions.getOrDefault(roomId, Collections.emptyList())) {
            if (session.isOpen()) {
                session.sendMessage(new TextMessage(json));
                log.info("群聊[{}]广播→用户[{}]", roomId,
                        session.getAttributes().get("user"));
            }
        }
    }

    //------------------------ 辅助方法 ------------------------

    /**
     * 向指定用户的所有会话发送消息
     */
    private void sendToUser(int userId, String json, String logDesc) throws IOException {
        for (WebSocketSession session : userSessions.getOrDefault(userId, Collections.emptyList())) {
            if (session.isOpen()) {
                session.sendMessage(new TextMessage(json));
                log.info("{}→用户[{}]", logDesc, userId);
            }
        }
    }

    /**
     * 解析WebSocket连接路径参数
     * 标准格式：/chat/{type}/{chatId}/{userId}
     */
    private Map<String, String> getPathParams(WebSocketSession session) {
        String path = session.getUri().getPath();
        String[] segments = path.split("/");
        if (segments.length < 6) {
            throw new IllegalArgumentException("路径格式错误，预期：/chat/{type}/{chatId}/{userId}");
        }

        Map<String, String> params = new HashMap<>();
        params.put("type", segments[3]);   // 聊天类型
        params.put("chatId", segments[4]); // 聊天目标ID
        params.put("userId", segments[5]); // 当前用户ID
        return params;
    }

    //------------------------ 生命周期方法 ------------------------

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        // 从所有会话表中移除该连接
        if (session.getAttributes().get("user") != null) {
            Integer userId = Integer.parseInt(session.getAttributes().get("user").toString());
            userSessions.getOrDefault(userId, new ArrayList<>()).remove(session);
            roomSessions.values().forEach(list -> list.remove(session));
            log.info("连接关闭: 用户[{}], 原因: {}", userId, status.getReason());
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) {
        log.error("传输异常: {}", exception.getMessage(), exception);
        try {
            session.close(CloseStatus.SERVER_ERROR); // 强制关闭异常连接
        } catch (IOException e) {
            log.warn("关闭异常会话失败", e);
        }
    }

    @Override
    public boolean supportsPartialMessages() {
        return false; // 不支持分片消息
    }
}
