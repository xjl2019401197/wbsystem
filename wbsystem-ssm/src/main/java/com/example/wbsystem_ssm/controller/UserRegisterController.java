package com.example.wbsystem_ssm.controller;

import com.alibaba.fastjson.JSON;
import com.example.wbsystem_ssm.redis.JedisUtil;
import com.example.wbsystem_ssm.entity.Card;
import com.example.wbsystem_ssm.entity.ResultBean;
import com.example.wbsystem_ssm.entity.User;
import com.example.wbsystem_ssm.service.CardService;
import com.example.wbsystem_ssm.service.UserService;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class UserRegisterController {
    @Autowired
    private UserService userService;
    @Autowired
    private CardService cardService;

    @GetMapping("/userRegister")
    public ResultBean userRegister(HttpServletRequest request, HttpServletResponse response) {

        String idCard = request.getParameter("idCard");
        String phone = request.getParameter("phone");
        Integer sex = Integer.valueOf(request.getParameter("sex"));
        Integer type = Integer.valueOf(request.getParameter("type"));
        User  cach= JSON.parseObject(JedisUtil.getJedisCon().get("user"),User.class);
        User user = new User();
        user.setNickName("用户001");
        user.setPwd("123456");
        user.setIdCard(idCard);
        user.setPhone(phone);
        user.setCreateUser(cach.getUserId());
        user.setType(type);
        user.setSex(sex);
        Card card = new Card();

        ResultBean resultBean = null;
        try {
            userService.save(user);
            PropertyUtils.copyProperties(card, user);
            card.setCardType(type);
            cardService.save(card);
            resultBean = new ResultBean();
            resultBean.setData(true);
        }catch (Exception e){
            resultBean = new ResultBean(e);
            resultBean.setData( "添加失败");
            e.printStackTrace();
        }finally {
            return resultBean;
        }
    }

}