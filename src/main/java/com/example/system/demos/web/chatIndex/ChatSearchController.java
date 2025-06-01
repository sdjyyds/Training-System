package com.example.system.demos.web.chatIndex;

import com.example.system.jdbc.entity.ChatIndex;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 * 查找聊天对象的实现类
 */
@RestController
@RequestMapping("/chat")
@Slf4j
public class ChatSearchController {
    // 查找消息的服务
    @Autowired
    private ChatSearchService chatSearchService;

    /**
     * 根据客户端请求加载不同的消息
     * @param type user or room
     * @param chatId user id or room id
     * @return 返回不同的消息
     */
    @GetMapping("/search")
    public ResponseEntity<ChatIndex> search(String type,int chatId) {
        log.info("开始搜索聊天对象，类型：{}, ID：{}", type, chatId);
        // 如果 type 或者 chatId 为空
        if (type == null || chatId == 0 ) {
            log.error("搜索聊天对象失败，参数为空");
            return ResponseEntity.ok(null); // 注意：这仍然是 Content-Length: 0
        }
        // 根据 type 和 id 查询
        ChatIndex result = null;
        if ("user".equals(type)) {
            log.info("开始搜索私聊对象，用户ID：{}", chatId);
            result = chatSearchService.getPrivateChatIndexByUserId(chatId);
        } else if ("room".equals(type)) {
            log.info("开始搜索群聊对象，房间ID：{}", chatId);
            result = chatSearchService.getGroupChatIndexByRoomId(chatId);
        }
        // 如果查询结果为空
        if (result == null) {
            log.error("搜索聊天对象失败，结果为空");
            // 返回一个标志错误的空对象
            return ResponseEntity.ok(new ChatIndex("未找到", "",  -1, ""));
        }
        log.info("搜索聊天对象成功，结果：{}", result);
        return ResponseEntity.ok(result);
    }

}