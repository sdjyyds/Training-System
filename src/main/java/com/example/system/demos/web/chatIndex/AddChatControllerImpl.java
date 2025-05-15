package com.example.system.demos.web.chatIndex;

import com.example.system.jdbc.entity.ChatRoom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 * 添加聊天消息的实现
 */
@RestController
@RequestMapping("/chat")
public class AddChatControllerImpl implements AddChatController{
    //聊天消息的创建
    @Autowired
    private AddChatService addChatService;

    /**
     * 添加私聊消息
     * @param receiverId 发送者的id
     * @param content 消息
     * @param session 会话
     * @return 返回聊天频道是否创建
     */
    @PostMapping("/addPrivateChat")
    @Override
    public String addPrivateChat(int receiverId, String content, HttpSession session) {
        int senderId = Integer.parseInt(session.getAttribute("user").toString());
        addChatService.addPrivateChat(senderId, receiverId, content);
        return "私聊频道已创建";
    }

    /**
     * 添加群聊消息
     * @param roomId 房间号
     * @param content 消息
     * @param session 会话
     * @return 返回聊天频道是否创建
     */
    @PostMapping("/addGroupChat")
    @Override
    public String addGroupChat(int roomId, String content, HttpSession session) {
        int senderId = Integer.parseInt(session.getAttribute("user").toString());
        addChatService.addGroupChat(senderId, roomId, content);
        return "群聊频道已创建";
    }

    @PostMapping("/addRoom")
    @Override
    public Map<String, Object> addRoom(@RequestBody ChatRoom chatRoom) {
        System.out.println(chatRoom);
        Map<String, Object> response = new HashMap<>();
        boolean success = addChatService.addChatRoom(chatRoom);
        if (success) {
            response.put("status", "success");
        } else {
            response.put("status", "fail");
            response.put("message", "数据库写入失败");
        }
        return response;
    }

    @PostMapping("/renameRoom")
    public Map<String, Object> renameRoom(@RequestParam int roomId, @RequestParam String newName, @RequestParam(required = false) String chatImage) {
        Map<String, Object> res = new HashMap<>();
        boolean success = addChatService.updateChatRoomName(roomId, newName, chatImage);
        if (success) {
            res.put("status", "success");
        } else {
            res.put("status", "fail");
            res.put("message", "修改失败，群聊不存在");
        }
        return res;
    }

    @PostMapping("/deleteRoom")
    public Map<String, Object> deleteRoom(@RequestParam("roomId") int roomId) {
        Map<String, Object> res = new HashMap<>();
        if (addChatService.deleteChatRoom(roomId)) {
            res.put("status", "success");
        } else {
            res.put("status", "fail");
            res.put("message", "删除失败，ID不存在");
        }
        return res;
    }


}
