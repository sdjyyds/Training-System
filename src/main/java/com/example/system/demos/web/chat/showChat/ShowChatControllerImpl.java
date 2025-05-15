package com.example.system.demos.web.chat.showChat;

import com.example.system.jdbc.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpSession;
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
public class ShowChatControllerImpl implements ShowChatController {
    //导入服务对象
    @Autowired
    ShowChatService showChatService;

    /**
     * 得到群聊消息
     *
     * @param roomId  群聊号
     * @param session 用来得到会话中标注的用户Id
     * @return 返回群聊消息
     */
    @GetMapping("/group")
    @Override
    public List<Message> getGroupMessages(@RequestParam("roomId") int roomId, HttpSession session) {
        if (session.getAttribute("user") == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "请先登录");
        }
        // 你可以加一层判断：当前用户是否在这个群
        //int myUserId = Integer.parseInt(session.getAttribute("user").toString());

        return showChatService.getGroupMessages(roomId);
    }

    /**
     * 得到私聊消息
     *
     * @param userId  对方的用户id
     * @param session 用来得到会话中标注的用户Id
     * @return 返回私聊消息
     */
    @GetMapping("/private")
    @Override
    public List<Message> getPrivateMessages(@RequestParam("userId") int userId, HttpSession session) {
//        System.out.println("session:   dsdsd" + session);
//        System.out.println("session.getAttribute(\"user\"): " + session.getAttribute("user"));

        if (session.getAttribute("user") == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "请先登录");
        }
        int myUserId = Integer.parseInt(session.getAttribute("user").toString());
        //    System.out.println("jjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjj");
        // 查询的是当前用户与 userId 的聊天记录
        return showChatService.getPrivateMessages(userId, myUserId);
    }
}

