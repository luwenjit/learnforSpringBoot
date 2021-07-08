package com.example.springboot.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

import java.util.Locale;

@SpringBootApplication
public class SpringbootWebJsp2Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootWebJsp2Application.class, args);
    }


    /**
     * 获取视图解析器，查看源码是否加载该视图解析器
     *  源码搜索 dispatchservlet
     * @return
     */
    @Bean
    public ViewResolver myViewResolver(){
        return new MyViewResolver();
    }

    private static class MyViewResolver implements ViewResolver{

        @Override
        public View resolveViewName(String s, Locale locale) throws Exception {
            return null;
        }
    }
}
