package com.example.system.demos.web.chat.onChat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;


/**
 * WebSocket 配置类
 *
 * 此类用于注册 WebSocket 的处理器（WebSocketHandler）并进行连接路径、拦截器等配置。
 */
@Configuration                      // 声明这是一个配置类（相当于 XML 配置）
@EnableWebSocket                   // 开启 WebSocket 功能
public class WebSocketConfig implements WebSocketConfigurer {

    /**
     * 注入你实现的 WebSocket 处理器（负责处理连接、消息、关闭等事件）
     */
    @Autowired
    private ChatWebSocketHandler chatWebSocketHandler;

    /**
     * 注册 WebSocket 处理器并配置路径、拦截器、跨域等
     *
     * @param registry 用于注册 WebSocket handler
     */
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(chatWebSocketHandler, "/ws/chat/{type}/{id}") // 设置连接路径：支持路径变量 type（"private"或"group"）、id（对方用户ID或群聊ID）

                // 添加握手拦截器：在 WebSocket 建立连接前执行，可用于读取 HTTP Session 等信息
                // HttpSessionHandshakeInterceptor 会自动将 HttpSession 中的所有属性复制到 WebSocketSession 的 attributes 中
                .addInterceptors(new HttpSessionHandshakeInterceptor())

                // 允许跨域访问（可按需设置为指定域名）
                .setAllowedOrigins("*");
    }
}