package com.example.wbsystem_ssm.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.wbsystem_ssm.dao.a.DateUtil;
import com.example.wbsystem_ssm.entity.*;
import com.example.wbsystem_ssm.dao.a.DBUtil;
import com.example.wbsystem_ssm.service.CardService;
import com.example.wbsystem_ssm.service.TopUpService;
import com.example.wbsystem_ssm.service.UserService;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class StatisticsController {
    @Autowired
    private TopUpService topUpService;
    @Autowired
    private UserService userService;
    @Autowired
    private CardService cardService;
    @GetMapping("/statistics")
    public List<TopUpDto> statistics(HttpServletRequest request, HttpServletResponse response){

        String type = request.getParameter("type");
        String phone = request.getParameter("phone2");
        String idCard = request.getParameter("idCard2");
        String way = request.getParameter("way2");
        System.out.println("idCard:"+idCard+"  "+phone+"  "+way);
        String day = DateUtil.getNowday();
        String tomorrow = DateUtil.getTomorrow();
        String nowMonth = DateUtil.getNowMonth();
        String nextMonth = DateUtil.getNextMonth();
        List<TopUp> list = null;
        ArrayList<TopUpDto> topUpDtos = new ArrayList<TopUpDto>();
        try {
            if (type.equals("day")){
                //日收入
                list = topUpService.list(new QueryWrapper<TopUp>().like("create_time", day).eq(StringUtils.isNotEmpty(way),"way",way));
            }else {
                //月收入
                list = topUpService.list(new QueryWrapper<TopUp>().like("create_time", nowMonth).eq(StringUtils.isNotEmpty(way),"way",way));
            }
            for (TopUp topUp : list) {
                boolean flag = true;
                User user = userService.getById(topUp.getUserId());
                TopUpDto topUpDto = new TopUpDto();
                PropertyUtils.copyProperties(topUpDto, topUp);
                topUpDto.setCreateUserName(user.getNickName());
                topUpDto.setIdCard(user.getIdCard());
                topUpDto.setPhone(user.getPhone());
                if(StringUtils.isNotEmpty(idCard)&& !topUpDto.getIdCard().contains(idCard))flag = false;
                if(StringUtils.isNotEmpty(phone) && !topUpDto.getPhone().contains(phone))flag = false;
                if(flag)
                topUpDtos.add(topUpDto);
            }
            System.out.println("topUpDto:"+topUpDtos);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            return topUpDtos;
        }


    }
}
