package com.example.system.demos.web.manageUser.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * 文件存储工具类
 * 封装了上传文件的保存逻辑，包括生成唯一文件名、保存到磁盘目录、返回路径等操作。
 */
public class FileStorageUtil {

    /**
     * 保存上传的文件到指定目录，并返回封装的保存结果对象。
     *
     * @param file      上传的文件对象（通常来自前端表单）
     * @param uploadDir 要保存的目标目录，建议从 application.properties 中注入
     * @return          FileSaveResult 包含生成的文件名和绝对路径
     * @throws IOException 文件为空、目录创建失败、保存失败等抛出异常
     */
    public static FileSaveResult saveFile(MultipartFile file, String uploadDir) throws IOException {
        // 1. 校验文件是否为空
        if (file.isEmpty()) {
            throw new IOException("上传的文件为空");
        }

        // 2. 获取原始文件名并验证合法性（必须包含扩展名）
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || !originalFilename.contains(".")) {
            throw new IOException("文件名无效，必须包含扩展名");
        }

        // 3. 提取文件扩展名（如 ".jpg"）
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));

        // 4. 使用 UUID 生成唯一文件名，防止命名冲突
        String filename = UUID.randomUUID().toString() + extension;

        // 5. 确保上传目录存在，如果不存在则尝试创建
        File directory = new File(uploadDir);
        if (!directory.exists() && !directory.mkdirs()) {
            throw new IOException("创建上传目录失败：" + uploadDir);
        }

        // 6. 构建目标文件路径对象（完整路径 = 目录 + 文件名）
        File dest = new File(directory, filename);

        // 7. 将上传的文件数据写入目标文件（保存到本地磁盘）
        file.transferTo(dest);

        // 8. 返回保存结果（封装了生成的文件名和绝对路径）
        return new FileSaveResult(filename, dest.getAbsolutePath());
    }

    /**
     * 文件保存结果封装类
     * 用于返回保存后的信息，如文件名、完整路径、相对访问路径等。
     */
    public static class FileSaveResult {
        private final String filename;    // 生成的唯一文件名
        private final String fullPath;    // 保存到磁盘上的绝对路径

        public FileSaveResult(String filename, String fullPath) {
            this.filename = filename;
            this.fullPath = fullPath;
        }

        public String getFilename() {
            return filename;
        }

        public String getFullPath() {
            return fullPath;
        }

        /**
         * 构建前端可访问的相对 URL 路径（通常配合 WebMvcConfigurer 配置）
         * 例如：urlPrefix="/uploads/"，filename="abc.jpg" → "/uploads/abc.jpg"
         */
        public String getRelativeUrl(String urlPrefix) {
            return urlPrefix + filename;
        }
    }
}
