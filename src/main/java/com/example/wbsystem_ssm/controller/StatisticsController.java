package com.example.wbsystem_ssm.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.wbsystem_ssm.dao.a.DateUtil;
import com.example.wbsystem_ssm.entity.*;
import com.example.wbsystem_ssm.dao.a.DBUtil;
import com.example.wbsystem_ssm.service.CardService;
import com.example.wbsystem_ssm.service.TopUpService;
import com.example.wbsystem_ssm.service.UserService;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
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
    //清单列表
    @GetMapping("/statistics")
    public IPage<TopUpDto> statistics(HttpServletRequest request, HttpServletResponse response){
        //获取req的参数
        String type = request.getParameter("type");
        String phone = request.getParameter("phone2");
        String idCard = request.getParameter("idCard2");
        String way = request.getParameter("way2");
        String param = request.getParameter("current");

        //对比参数是日收入清单还是月收入清单
        String day = DateUtil.getNowday();
        String nowMonth = DateUtil.getNowMonth();
        Integer page = 1;
        if(param != null) page  = Integer.parseInt(param);
        Page pages = new Page<>(page, 5);
        IPage<TopUpDto> iPage = null;
        try {
            iPage = topUpService.selectJoinListPage(pages, TopUpDto.class,
                    new MPJLambdaWrapper<TopUp>()
                            .selectAll(TopUp.class)
                            .selectAs( User::getNickName,TopUpDto::getCreateUserName)//别名 t.address AS userAddress

                            .select(User::getNickName)
                            .select(User::getIdCard)
                            .select(User::getPhone)
                            .leftJoin(User.class, User::getUserId, TopUp::getUserId)
                            .like(type.equals("day"),TopUp::getCreateTime, day)
                            .like(type.equals("month"),TopUp::getCreateTime, nowMonth)
                            .like(StringUtils.isNotEmpty(idCard),User::getIdCard, idCard)
                            .like(StringUtils.isNotEmpty(phone),User::getPhone,phone)
            );
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            return iPage;
        }


    }
}
