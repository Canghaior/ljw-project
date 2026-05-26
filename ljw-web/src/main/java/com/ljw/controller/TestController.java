package com.ljw.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 简单测试接口。
 */
@RestController
public class TestController {

    /**
     * 用来验证项目是否正常启动。
     *
     * @return 测试字符串
     */
    @GetMapping("/test/hello")
    public String hello() {
        return "hello spring boot";
    }
}
