package com.example.system.demos.web.chatIndex;


import com.example.system.jdbc.entity.ChatIndex;

/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 * 完成对与索引界面不同跳转的业务逻辑判断的封装
 */
public interface ChatSearchService {
    /**
     * 根据用户id获取私聊索引
     * @param useId 用户的id
     * @return 返回查询的聊天
     */
    ChatIndex getPrivateChatIndexByUserId(int useId);

    /**
     * 根据房间id获取群聊索引
     * @param roomId 群聊号
     * @return 返回查询的聊天
     */
    ChatIndex getGroupChatIndexByRoomId(int roomId);
}
