package com.zzz.springboot.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 孟享广
 * @date 2020-12-10 11:30 上午
 * @description
 */
@Configuration
public class DruidConfig {

    /**
     * 需要配置这个，以使initialSize等属性注入datasource中
     * @return
     */
    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean
    public DataSource druid() {
        return new DruidDataSource();
    }

    //配置Druid的监控
    //1、配置一个管理后台的Servlet
    @Bean
    public ServletRegistrationBean statViewServlet() {
        /**
         * 注入servlet，参考之前的项目，手写myservlet,继承HttpServlet，然后通过ServletRegistrationBean注入该servlet
         * 而这里是通过ServletRegistrationBean注入StatViewServlet【其父类依然是HttpServlet】
         */
        ServletRegistrationBean bean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
        Map<String,String> initParams = new HashMap<>();

        initParams.put("loginUsername","admin");
        initParams.put("loginPassword","admin");
        initParams.put("allow","");//默认就是允许所有访问
        initParams.put("deny","10.10.214.212");//拒绝谁访问

        bean.setInitParameters(initParams);
        return bean;
    }

    //2、配置一个web监控的filter
    @Bean
    public FilterRegistrationBean webStatFilter(){
        /**
         * 注入filter
         * 通过FilterRegistrationBean注入WebStatFilter
         */
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new WebStatFilter());

        Map<String,String> initParams = new HashMap<>();
        initParams.put("exclusions","*.js,*.css,/druid/*");//不拦截

        bean.setInitParameters(initParams);

        // 拦截所有请求
        bean.setUrlPatterns(Arrays.asList("/*"));

        return  bean;
    }
}
