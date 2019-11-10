package com.jiankang;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import org.springframework.transaction.annotation.EnableTransactionManagement;

//注意取消自动Multipart配置，否则可能在上传接口中拿不到file的值
@MapperScan("com.jiankang.mapper")
@SpringBootApplication
@EnableTransactionManagement
public class JiankangApplication extends SpringBootServletInitializer {


    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(JiankangApplication.class);

    }

    public static void main(String[] args) {

        SpringApplication.run(JiankangApplication.class, args);
    }

}
