package com.example.wbsystem_ssm.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.wbsystem_ssm.entity.*;
import com.example.wbsystem_ssm.service.UserService;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@CrossOrigin
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/user/getUserBySession")
    public User getUserBySession(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        Object principal = null;
        User user = null;
        JSONObject jsonObjects = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        System.out.println("authenticationSession:"+authentication);
        try{
            user = JSON.parseObject((String) session.getAttribute("user"),User.class);
            System.out.println(user);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return user;
        }
    }



    //用户列表的获取
    @GetMapping("/userList")
    public IPage<User> userList(HttpServletRequest request, HttpServletResponse response) {
        //获取req的请求参数
        String idCard = request.getParameter("idCard2");
        String phone = request.getParameter("phone2");
        String sex = request.getParameter("sex2");
        String userId = request.getParameter("userId");
        //获取当前登录用户的信息
        HttpSession session = request.getSession();
        User user = JSON.parseObject((String)session.getAttribute("user"),User.class);
        String param = request.getParameter("current");
        //mybatisplus提供的分页查和讯

        Integer page = 1;
        if(param != null) page  = Integer.parseInt(param);
        Page pages = new Page<>(page, 5);
        IPage<User> iPage = null;
        try {
            iPage = userService.selectJoinListPage(pages, User.class,
                    new MPJLambdaWrapper<User>()
                            .selectAll(User.class)
                            .eq(User::getDeleteFlag, 0)
                            .like(StringUtils.isNotEmpty(idCard),User::getIdCard,idCard)
                            .like(StringUtils.isNotEmpty(phone),User::getPhone,phone)
                            .eq(StringUtils.isNotEmpty(sex),User::getSex,sex)
                            .eq(StringUtils.isNotEmpty(userId),User::getUserId,userId)
                            .eq(user != null,User::getUserType,user != null ?user.getUserType()+1:0));
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            return iPage;
        }
    }
    @PostMapping("/user/checkAccount")
    public ResultBean<Boolean> checkAccount(HttpServletRequest request, HttpServletResponse response) {
        String idCard = request.getParameter("idCard");
        User user = null;
        ResultBean<Boolean> resultBean = new ResultBean<>(true);
        try {
            user = userService.getOne(new QueryWrapper<User>().eq("id_card",idCard));
            if(user == null)resultBean.setData(false);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            return resultBean;
        }
    }
    @PostMapping("/user/del")
    public ResultBean del(HttpServletRequest request, HttpServletResponse response) {
        Integer userId = Integer.parseInt(request.getParameter("id"));
        ResultBean resultBean = null;
        try {
            User user = userService.getById(userId);
            user.setDeleteFlag(1);
            userService.updateById(user);
            resultBean = new ResultBean();
            resultBean.setData(true);
        } catch (Exception e) {
            resultBean = new ResultBean(e);
            resultBean.setData("删除失败");
            e.printStackTrace();
        } finally {
            return resultBean;
        }
    }
    @PostMapping("/user/change")
    public ResultBean change(HttpServletRequest request, HttpServletResponse response) {
        Integer userId = Integer.parseInt(request.getParameter("id"));
        ResultBean resultBean = null;
        try {
            User user = userService.getById(userId);
            user.setState((user.getState()+1)%2);
            userService.updateById(user);
            resultBean = new ResultBean();
            resultBean.setData(true);
        } catch (Exception e) {
            resultBean = new ResultBean(e);
            resultBean.setData("禁用失败");
            e.printStackTrace();
        } finally {
            return resultBean;
        }
    }
    @PostMapping("/user/update")
    public ResultBean update(HttpServletRequest request, HttpServletResponse response) {
        Integer userId = Integer.parseInt(request.getParameter("userId"));
        String  nickName =request.getParameter("nickName");
        String pwd = request.getParameter("pwd");
        String phone = request.getParameter("phone");
        ResultBean resultBean = null;
        try {
            User user = userService.getById(userId);
            user.setNickName(nickName);
            user.setPwd(pwd);
            user.setPhone(phone);
            userService.updateById(user);
            resultBean = new ResultBean();
            resultBean.setData(true);
        } catch (Exception e) {
            resultBean = new ResultBean(e);
            resultBean.setData("更新失败");
            e.printStackTrace();
        } finally {
            return resultBean;
        }
    }

}
