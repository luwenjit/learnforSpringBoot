package com.example.springboot.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class LoginController {

    /**
     * 登录
     * @param username
     * @param password
     * @param map
     * @return
     */
    @PostMapping(value = "/user/login")
    public String login(@RequestParam("username") String username
        , @RequestParam("password") String password
        , Map<String,Object> map
        , HttpSession session){
        if (StringUtils.isEmpty(username) || "123456".equals(password)){
            // 登录成功
            // 重定向，避免F5时重新提交
            // 登录信息进入session，通过preHandle检查
            session.setAttribute("loginUser",username);

            return "redirect:/main.html";
        }
        map.put("msg","用户名密码错误");
        // 登录失败
        return "login";
    }

}
