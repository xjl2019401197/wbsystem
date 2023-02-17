package com.example.wbsystem_ssm.controller;

import com.example.wbsystem_ssm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {
    @Autowired
    private UserService userService;
    @RequestMapping("/hello")
    @ResponseBody
    public String test(){
        System.out.println(userService.list(null));
        return "hello";
    }
}
