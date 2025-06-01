package com.example.system.confug;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 * 配置文件
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    // 上传文件的目录
    @Value("${file.upload.dir}")
    private String uploadDir;

    /**
     * 配置文件访问
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:" + uploadDir + "/");
    }
}

