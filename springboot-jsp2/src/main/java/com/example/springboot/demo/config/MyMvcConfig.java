package com.example.springboot.demo.config;

import org.springframework.boot.autoconfigure.web.embedded.EmbeddedWebServerFactoryCustomizerAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 新版配置类是 WebMvcConfigurationSupport
 */
//使用WebMvcConfigurerAdapter可以来扩展SpringMVC的功能
//@EnableWebMvc   不要接管SpringMVC,所有springmvc自动配置失效
@Configuration
public class MyMvcConfig extends WebMvcConfigurerAdapter {

    /**
     * 重写视图映射规则
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry){
//        super.addViewControllers(registry);
        // 浏览器发送atguigu 请求来到success页面
        registry.addViewController("/atguigu").setViewName("success");
    }

    /**
     * 配置视图解析器，解决静态资源存在多个index.html，这里项目是模板引擎，需要访问/templates下的index.html
     * @return
     */
    //所有的webMvcConfigureAdapter组件都将一起起作用，所以这里自己定义的解析器，需要放入容器才生效
    @Bean //放入容器以生效
    public WebMvcConfigurerAdapter webMvcConfigurerAdapter(){
        WebMvcConfigurerAdapter adapter = new WebMvcConfigurerAdapter(){
            @Override
            public void addViewControllers(ViewControllerRegistry registry){
                registry.addViewController("/").setViewName("login");
                registry.addViewController("/index.html").setViewName("login");
                // 后台到dashboard.html加个中间页，防止登录时重复提交表单
                // 问题：重定向的话，存在直接访问/main.html 就跳过login.html 直接进应用；解决办法：增加拦截器登录检查
                registry.addViewController("/main.html").setViewName("dashboard");
            }

            //注册拦截器
            // 让/**请求走登录拦截，并且排除/index.html等请求
            // 如请求不存在的路径/aaa 会定向到登录页面，而不是默认error页面
            // 默认error

            /**
             * 原理：参照ErrorMvcAutoConfiguration
             * 定制错误页面：
             *  1、有模板引擎，error/状态码 [将错误页面命名为错误状态码.html，放在模板引擎里的error文件夹里]
             *      可以使用4xxh和5xx，作为文件名匹配，精确优先【优先寻找状态码.html】
             *  2、没有模板引擎，也可以放到静态资源文件夹static里，但是无法获取注入的status、error等modelview
             *  3、以上都没有，就来到spring boot默认的错误提示页面
             * 定制错误json数据
             *  1、MyExceptionHander
             * @param registry
             */
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
//                super.addInterceptors(registry);
//                registry.addInterceptor(new LoginHandlerInterceptor()).addPathPatterns("/**")
//                        .excludePathPatterns("/index.html","/","/user/login");
            }
        };
        return adapter;
    }

    /**
     * 配置自己编写的区域解析器
     * @return
     */
    @Bean
    public LocaleResolver localeResolver(){
        return new MyLocaleResolver();
    }




}
