package com.jiankang.config;


import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.MultipartConfigElement;

@Configuration
public class FileUpLoad {


        @Bean
        public MultipartConfigElement multipartConfigElement() {
            MultipartConfigFactory factory = new MultipartConfigFactory();
            //  单个数据大小  测试后从配置取值。
            factory.setMaxFileSize("1024000KB"); // KB,MB
            /// 总上传数据大小
            factory.setMaxRequestSize("102400000KB");
            return factory.createMultipartConfig();
        }
}

