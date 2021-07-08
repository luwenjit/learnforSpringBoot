package com.example.springboot.demo.config;

import com.example.springboot.demo.filter.MyFilter;
import com.example.springboot.demo.listener.MyListener;
import com.example.springboot.demo.servlet.MyServlet;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.Arrays;

@Configuration
public class MyServerConfig {

    /**
     * 访问：http://localhost:8080/myServlet
     * 得到 hello MyServlet 输出
     * @return
     */
    //注册三大组件
    @Bean
    public ServletRegistrationBean myServlet(){
        return new ServletRegistrationBean<>(new MyServlet(),"/myServlet");
    }

    @Bean
    public FilterRegistrationBean myFilter(){
        FilterRegistrationBean registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new MyFilter());
        registrationBean.setUrlPatterns(Arrays.asList("/hello","/myServlet"));
        return registrationBean;
    }

    @Bean
    public ServletListenerRegistrationBean myListener(){
        ServletListenerRegistrationBean<MyListener> registrationBean = new ServletListenerRegistrationBean<>(new MyListener());
        return registrationBean;
    }

}
