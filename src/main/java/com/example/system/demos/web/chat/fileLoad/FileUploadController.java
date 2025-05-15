package com.example.system.demos.web.chat.fileLoad;

import com.example.system.demos.web.chat.showChat.ShowChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 * 封装文件上传
 */
public interface FileUploadController {
    /**
     * 文件上传
     * @param file 文件
     * @param senderId 发送者的id
     * @param roomId 房间号
     * @param receiverId 接受者
     * @param type 文件类型
     * @return 返回
     */
    ResponseEntity<?> handleFileUpload(@RequestParam("file") MultipartFile file,
                                              @RequestParam("senderId") int senderId,
                                              @RequestParam(value = "roomId", required = false) Integer roomId,
                                              @RequestParam(value = "receiverId", required = false) Integer receiverId,
                                              @RequestParam("type") String type);
}
