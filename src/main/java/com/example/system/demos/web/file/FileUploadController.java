package com.example.system.demos.web.file;

import com.example.system.demos.web.manageUser.util.FileStorageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 */
@RestController
@RequestMapping("/file")
@Slf4j
public class FileUploadController {

    @Value("${file.upload.dir}") // 你可以在 application.yml 或 .properties 中配置
    private String uploadDir;

    /**
     * 文件上传接口
     */
    @PostMapping("/upload")
    public ResponseEntity<Map<String, Object>> uploadAvatar(@RequestParam("file") MultipartFile file) {
        log.info("开始上传文件：{}", file.getOriginalFilename());
        Map<String, Object> result = new HashMap<>();
        try {
            // 保存文件到本地
            FileStorageUtil.FileSaveResult saveResult = FileStorageUtil.saveFile(file, uploadDir);
            String filename = saveResult.getFilename();
            String relativeUrl = saveResult.getRelativeUrl("/uploads/"); // 映射目录为 /uploads/

            log.info("文件上传成功：{}", filename);
            result.put("status", "success");
            result.put("filename", filename);
            result.put("fileUrl", relativeUrl); // 返回给前端的相对地址
            return ResponseEntity.ok(result);

        } catch (IOException e) {
            log.error("文件保存失败：{}", e.getMessage());
            result.put("status", "fail");
            result.put("message", "文件保存失败：" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
    }
}