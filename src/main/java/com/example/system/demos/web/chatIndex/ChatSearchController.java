package com.example.system.demos.web.chatIndex;

import com.example.system.jdbc.entity.ChatIndex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 */
@RestController
@RequestMapping("/chat")
public class ChatSearchController {
    @Autowired
    ChatSearchService chatSearchService;

    @GetMapping("/search")
    public ResponseEntity<ChatIndex> search(@RequestParam String type, @RequestParam int id, HttpSession session) {
        if (type == null || id == 0 || session.getAttribute("user") == null) {
            return ResponseEntity.ok(null); // 注意：这仍然是 Content-Length: 0
        }

        ChatIndex result = null;
        if ("user".equals(type)) result = chatSearchService.getPrivateChatIndexByUserId(id);
        else if ("room".equals(type)) result = chatSearchService.getGroupChatIndexByRoomId(id);

        if (result == null) {
            // 返回一个标志错误的空对象
            return ResponseEntity.ok(new ChatIndex(-1, "未找到",  "", ""));
        }

        return ResponseEntity.ok(result);
    }

}

