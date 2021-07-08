package com.example.springboot.demo.controller;

import com.example.springboot.demo.exception.UserNotExistException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@Controller
public class HelloController {

    /**
     * 注意 返回文件为webapp以跟目录的文件路径，spring.mvc.view.prefix=/ 配置路径不生效
     *
     * @return
     */
    @RequestMapping(value = "/a", method = RequestMethod.GET)
    public String a() {
        return "page/succcess";
    }

    /**
     * 返回string
     * @return
     */
    @ResponseBody
    @RequestMapping("/hello")
    public String hello() {
        return "hello world";
    }

    /**
     * thymeleaf 模板
     * @return
     */
    @RequestMapping("/success")
    public String success(Map<String,Object> map) {
        //classpath:/templates/success.html
        //hello的值会自动渲染到模板里
        map.put("hello","你好");
        return "success2";
    }

    /**
     * 测试抛自定义异常
     * @param user
     * @return
     */
    @ResponseBody
    @RequestMapping("/hello2")
    public String hello2(@RequestParam("user") String user) {
        if (user.equals("aaa")){
            throw new UserNotExistException();
        }
        return "hello world";
    }


}
