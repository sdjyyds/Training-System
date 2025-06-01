package com.example.system.demos.web.chatRoom;

import com.example.system.jdbc.entity.ChatRoom;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@Slf4j
public class ChatRoomController {
    //聊天消息的创建
    @Autowired
    private ChatRoomService addChatService;

//    /**
//     * 添加私聊消息
//     * @param receiverId 发送者的id
//     * @param content 消息
//     * @param senderId 发送者id
//     * @return 返回聊天频道是否创建
//     */
//    @PostMapping("/addPrivateChat")
//    public String addPrivateChat(int receiverId, String content,@RequestParam( "userId") int  senderId) {
//        log.info("用户{}向用户{}发送私聊消息：{}", senderId, receiverId, content);
//        addChatService.addPrivateChat(senderId, receiverId, content);
//        log.info("私聊频道已创建");
//        return "私聊频道已创建";
//    }
//
//    /**
//     * 添加群聊消息
//     * @param roomId 房间号
//     * @param content 消息
//     * @param senderId 发送者id
//     * @return 返回聊天频道是否创建
//     */
//    @PostMapping("/addGroupChat")
//    public String addGroupChat(int roomId, String content,@RequestParam( "userId") int  senderId) {
//        log.info("用户{}向房间{}发送群聊消息：{}", senderId, roomId, content);
//        addChatService.addGroupChat(senderId, roomId, content);
//        log.info("群聊频道已创建");
//        return "群聊频道已创建";
//    }

    @PostMapping("/addRoom")
    public Map<String, Object> addRoom(@RequestBody ChatRoom chatRoom) {
        log.info("创建新房间：{}", chatRoom);
        Map<String, Object> response = new HashMap<>();
        boolean success = addChatService.addChatRoom(chatRoom);
        if (success) {
            log.info("房间创建成功");
            response.put("status", "success");
        } else {
            log.error("房间创建失败");
            response.put("status", "fail");
            response.put("message", "数据库写入失败");
        }
        return response;
    }

    @PostMapping("/renameRoom")
    public Map<String, Object> renameRoom(@RequestParam int roomId, @RequestParam String newName, @RequestParam(required = false) String chatImage) {
        log.info("修改房间{}的名称为{}", roomId, newName);
        Map<String, Object> res = new HashMap<>();
        boolean success = addChatService.updateChatRoomName(roomId, newName, chatImage);
        if (success) {
            log.info("房间名称修改成功");
            res.put("status", "success");
        } else {
            log.error("房间名称修改失败");
            res.put("status", "fail");
            res.put("message", "修改失败，群聊不存在");
        }
        return res;
    }

    @PostMapping("/deleteRoom")
    public Map<String, Object> deleteRoom(@RequestParam("roomId") int roomId) {
        log.info("删除房间{}", roomId);
        Map<String, Object> res = new HashMap<>();
        if (addChatService.deleteChatRoom(roomId)) {
            log.info("房间删除成功");
            res.put("status", "success");
        } else {
            log.error("房间删除失败");
            res.put("status", "fail");
            res.put("message", "删除失败，ID不存在");
        }
        return res;
    }
}