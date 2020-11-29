package com.qf.elasticsearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @Author chenzhongjun
 * @Date 2020/11/24
 */
@SpringBootApplication(scanBasePackages = "com.qf.elasticsearch")
public class ElasticSearchApplication extends SpringBootServletInitializer {

    public static void main(String[] args){
        SpringApplication.run(ElasticSearchApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(ElasticSearchApplication.class);
    }
}
