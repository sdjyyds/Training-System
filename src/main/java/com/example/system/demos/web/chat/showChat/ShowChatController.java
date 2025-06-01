package com.example.system.demos.web.chat.showChat;

import com.example.system.jdbc.entity.Message;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * 根据客户端请求加载不同的消息
 *
 * @author jds
 * @version 1.1
 * @since 1.0.0
 */
@RestController
@RequestMapping("/chat/message")
@Slf4j
public class ShowChatController {

    //导入服务对象
    @Autowired
    ShowChatService showChatService;

    /**
     * 得到群聊消息
     *
     * @param roomId  群聊号
     * @param userId  用户id
     * @return 返回群聊消息
     */
    @GetMapping("/group")
    public List<Message> getGroupMessages(int roomId, int userId) {
        log.info("用户 {} 请求获取群聊 {} 的消息", userId, roomId);
        List<Message> messages = showChatService.getGroupMessages(roomId);
        log.info("返回群聊消息数量: {}", messages.size());
        return messages;
    }

    /**
     * 得到私聊消息
     *
     * @param userId  对方的用户id
     * @return 返回私聊消息
     */
    @GetMapping("/private")
    public List<Message> getPrivateMessages(int userId,int myUserId) {
        log.info("用户 {} 请求获取与用户 {} 的私聊记录", myUserId, userId);
        List<Message> messages = showChatService.getPrivateMessages(userId, myUserId);
        log.info("返回私聊消息数量: {}", messages.size());
        return messages;
    }
}
