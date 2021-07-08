package com.example.springboot.demo.config;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;
import java.util.Map;

/**
 * 自己组装json返回需要的数据
 */
@Component
public class MyErrorAttribute extends DefaultErrorAttributes {

    /**
     * 自定义ErrorAttributes，改变输出内容
     * @param webRequest
     * @param options
     * @return
     */
    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {
        Map<String,Object> map = super.getErrorAttributes(webRequest, options);
        map.put("company","atguigu");

        //取出request里的内容 传入0，从request获取
        Map<String,Object> ext = (Map<String, Object>) webRequest.getAttribute("ext",0);
        map.put("ext",ext);

        return map;
    }

    /**
     * 请求：http://localhost:8080/hello2?user=aaa  get
     * {
     *     "timestamp": "2021-07-06T06:23:10.323+00:00",
     *     "status": 500,
     *     "error": "Internal Server Error",
     *     "exception": "com.example.springboot.demo.exception.UserNotExistException",
     *     "message": "用户不存在",
     *     "path": "/hello2",
     *     "company": "atguigu",
     *     "ext": {
     *         "code": "user.notexist",
     *         "message": "用户不存在"
     *     }
     * }
     */
}
