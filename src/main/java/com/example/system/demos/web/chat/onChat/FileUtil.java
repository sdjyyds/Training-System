package com.example.system.demos.web.chat.onChat;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 */

public class FileUtil {
    private static final String BASE_DIR = "D:/chat-uploads/";

    // 保存 base64 文件并返回文件 URL
    public static String saveBase64File(String fileName, String base64) throws IOException {
        File dir = new File(BASE_DIR);
        if (!dir.exists()) dir.mkdirs();

        String savePath = BASE_DIR + System.currentTimeMillis() + "_" + fileName;
        byte[] fileBytes = Base64.getDecoder().decode(base64);
        try (FileOutputStream fos = new FileOutputStream(savePath)) {
            fos.write(fileBytes);
        }

        // 假设文件可通过此地址访问，建议通过 Controller 或静态资源映射
        return "/files/" + new File(savePath).getName();
    }
}
