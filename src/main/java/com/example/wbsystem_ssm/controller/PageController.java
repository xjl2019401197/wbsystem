package com.example.wbsystem_ssm.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.wbsystem_ssm.entity.User;
import com.example.wbsystem_ssm.entity.UserInfo;
import com.example.wbsystem_ssm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class PageController {
    @Autowired
    private UserService userService;
    @RequestMapping("/toPage")
    public String toPage(HttpServletRequest request, HttpServletResponse response){
        String page = "login.html";
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if(authentication.getPrincipal() == null) throw new Exception();
            UserInfo userInfo = (UserInfo) authentication.getPrincipal();
            String username = userInfo.getUsername();
            User user = userService.getOne(new QueryWrapper<User>().eq("id_card", username));
            HttpSession session = request.getSession();
            session.setAttribute("user", JSON.toJSONString(user));
            if(user.getUserType() == 1)page = "index.html";
            else if(user.getUserType() == 0) page = "manager.html";
            else page = "index.jsp";
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return page;
        }
    }
}
