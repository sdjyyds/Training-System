package com.example.system.demos.web.chat.fileLoad;

import com.example.system.demos.web.chat.showChat.ShowChatService;
import com.example.system.demos.web.manageUser.util.FileStorageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Map;

/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 * 文件上传
 */
@RestController
@RequestMapping("/file")
@Slf4j
public class SendFileController {
    // 文件上传目录
    @Value("${file.upload.dir}")
    private String uploadDir;
    // 保存文件
    @Autowired
    private ShowChatService chatService;

    /**
     * 文件上传
     * @param file 文件
     * @param senderId 发送者的id
     * @param roomId 房间号
     * @param receiverId 接受者
     * @param type 文件类型
     * @return 返回
     */
    @PostMapping("/upload/file")
    public ResponseEntity<?> handleFileUpload(@RequestParam("file") MultipartFile file,
                                              @RequestParam("senderId") int senderId,
                                              @RequestParam(value = "roomId", required = false) Integer roomId,
                                              @RequestParam(value = "receiverId", required = false) Integer receiverId,
                                              @RequestParam("type") String type) {
        log.info("开始文件上传，文件名：{}", file.getOriginalFilename());
        try {
            FileStorageUtil.FileSaveResult result = FileStorageUtil.saveFile(file, uploadDir);
            String filename = result.getFilename();
            String fileUrl = result.getRelativeUrl("/uploads/");
            // 构建内容（用于数据库存储）
            String content = "[file]/uploads/" + filename;
            // 数据库存储
            log.info("开始保存文件到数据库，发送者ID：{}, 房间号：{}, 内容：{}", senderId, roomId, content);
            if ("roomChat".equals(type)) {
                chatService.saveGroupMessage(senderId, roomId, content);
            } else {
                chatService.savePrivateMessage(senderId, receiverId, content);
            }
            log.info("文件保存到数据库成功");
            // 构建文件访问 URL（假设 /uploads 映射到 uploadDir）
            return ResponseEntity.ok(Map.of(
                    "status", "success",
                    "filename", filename,
                    "fileUrl", fileUrl
            ));
        } catch (IOException e) {
            log.error("文件上传失败，错误信息：{}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", e.getMessage()));
        }
    }

}