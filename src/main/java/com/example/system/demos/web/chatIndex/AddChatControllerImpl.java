package com.example.system.demos.web.chatIndex;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 */
@RestController
@RequestMapping("/chat")
public class AddChatControllerImpl implements AddChatController{
    @Autowired
    private AddChatService chatCreateService;
    @PostMapping("/addPrivateChat")
    @Override
    public String addPrivateChat(int receiverId, String content, HttpSession session) {
        int senderId = Integer.parseInt(session.getAttribute("user").toString());
        chatCreateService.addPrivateChat(senderId, receiverId, content);
        return "私聊频道已创建";
    }

    @PostMapping("/addGroupChat")
    @Override
    public String addGroupChat(int roomId, String content, HttpSession session) {
        int senderId = Integer.parseInt(session.getAttribute("user").toString());
        chatCreateService.addGroupChat(senderId, roomId, content);
        return "群聊频道已创建";
    }
    @PostMapping("/createRoom")
    @Override
    public Boolean createRoom(String roomName, String roomImage) {

        return null;
    }
}
