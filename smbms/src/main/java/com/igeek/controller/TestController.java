package com.igeek.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("test")
public class TestController {
    @ResponseBody
    @RequestMapping("theText")
    public String text(){
        return "hello world";
    }
}
