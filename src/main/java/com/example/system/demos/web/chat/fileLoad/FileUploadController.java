package com.example.system.demos.web.chat.fileLoad;

import com.example.system.demos.web.chat.showChat.ShowChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 */
@RestController
@RequestMapping("/file")
public class FileUploadController {

    @Value("${file.upload.dir}")
    private String uploadDir;

    @Autowired
    private ShowChatService chatService;

    @PostMapping("/upload")
    public ResponseEntity<?> handleFileUpload(@RequestParam("file") MultipartFile file,
                                              @RequestParam("senderId") int senderId,
                                              @RequestParam(value = "roomId", required = false) Integer roomId,
                                              @RequestParam(value = "receiverId", required = false) Integer receiverId,
                                              @RequestParam("type") String type) {
        try {
            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String filename = UUID.randomUUID() + extension;

            // 保存文件
            File dest = new File(uploadDir, filename);
            file.transferTo(dest);

            // 构建内容（用于数据库存储）
            String content = "[file]" + filename;

            // 数据库存储
            if ("group".equals(type)) {
                chatService.saveGroupMessage(senderId, roomId, content);
            } else {
                chatService.savePrivateMessage(senderId, receiverId, content);
            }

            // 构建文件访问 URL（假设 /uploads 映射到 uploadDir）
            String fileUrl = "/uploads/" + filename;

            return ResponseEntity.ok(Map.of(
                    "status", "success",
                    "filename", filename,
                    "fileUrl", fileUrl
            ));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", e.getMessage()));
        }
    }

}
