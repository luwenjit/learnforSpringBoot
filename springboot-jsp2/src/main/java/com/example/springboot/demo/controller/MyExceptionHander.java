package com.example.springboot.demo.controller;

import com.example.springboot.demo.exception.UserNotExistException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 自定义异常json返回
 */
@ControllerAdvice
public class MyExceptionHander {

    /**
     * 浏览器/客户端 返回的都是 json
     * @param e
     * @return
     */
//    @ResponseBody
//    @ExceptionHandler(UserNotExistException.class)
//    public Map<String, Object> handlerException(Exception e) {
//
//        Map<String, Object> map = new HashMap<>();
//        map.put("code", "user.notexist");
//        map.put("message", e.getMessage());
//        return map;
//    }


    /**
     * 自适应返回html、json
     *   转发到error，通过BasicErrorController 自匹配浏览器返回html，postman返回json
     *   但是无法定制写的这些json【因为返回的status=200，并没有返回实际的状态码】
     *
     * @param e
     * @return
     */
    @ExceptionHandler(UserNotExistException.class)
    public String handlerException(Exception e, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        // 传入我们自己的错误状态码。4xx,5xx
        request.setAttribute("javax.servlet.error.status_code",500);
        // 自定义的code,message并没有传入返回
        /**
         * 出现错误以后，会来到error请求，会被BasicErrorController处理，相应出去可以获取的数据
         * 是由getErrorAttributes得到的【是AbstractErrorController（ErrorController）规定的方法】
         * 1、完全编写一个ErrorController类处理规则，编写AbstractErrorController的子类
         * 2、页面上能用的数据，或者json返回能用的数据，都是通过errorAttributes.getErrorAttributes得到的
         */
        map.put("code", "user.notexist");
        map.put("message", e.getMessage());

        //这里吧需要放入errorAttributes的内容放到request里，然后到MyErrorAttribute取出
        request.setAttribute("ext",map);

        return "forward:/error";
    }

}
