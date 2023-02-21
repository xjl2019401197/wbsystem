package com.example.wbsystem_ssm.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.wbsystem_ssm.entity.*;
import com.example.wbsystem_ssm.service.CardService;
import com.example.wbsystem_ssm.service.ConsumerService;
import com.example.wbsystem_ssm.service.EquipService;
import org.mybatis.logging.Logger;
import org.mybatis.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@RestController
public class ConsumerController {
    @Autowired
    private ConsumerService consumerService;
    @Autowired
    private CardService cardService;
    @Autowired
    private EquipService equipService;
//    private static Logger log = LoggerFactory.getLogger(ConsumerController.class);

    @GetMapping("/startUp")
    public ResultBean startUp(HttpServletRequest request, HttpServletResponse response) {
        User user = JSON.parseObject(String.valueOf(request.getSession().getAttribute("user")),User.class) ;
        Integer userId = Integer.parseInt(request.getParameter("userId"));
        Integer cardId = Integer.parseInt(request.getParameter("cardId"));
        Integer configure = Integer.parseInt(request.getParameter("configure"));
        ResultBean resultBean = null;

        try {

            Consumer consumer = new Consumer();
            consumer.setUserId(userId);
            consumer.setCardId(cardId);

            consumerService.save(consumer);

            Card card = cardService.getById(cardId);
            card.setState(1);
            card.setConfigure(configure);
            Equip equip = equipService.getById(configure);
            equip.setReduce(equip.getReduce() - 1);
            equipService.updateById(equip);
            card.setPostion(new Random().nextInt(equip.getNumber()) + 1);
            cardService.updateById(card);

            resultBean = new ResultBean();
            resultBean.setData(true);
        } catch (Exception e) {
            resultBean = new ResultBean(e);
            resultBean.setData("操作失败");
            e.printStackTrace();

        } finally {
            return resultBean;
        }
    }

    @GetMapping("/shutDown")
    public ResultBean shutDown(HttpServletRequest request, HttpServletResponse response) {

        Integer userId = Integer.parseInt(request.getParameter("userId"));
        Integer cardId = Integer.parseInt(request.getParameter("cardId"));
        System.out.println("consumer");
        ResultBean resultBean = null;
        try {
            shut(userId, cardId);
            resultBean = new ResultBean();
            resultBean.setData(true);
        } catch (Exception e) {
            resultBean = new ResultBean(e);
            resultBean.setData("操作失败");
            e.printStackTrace();

        } finally {
            return resultBean;

        }
    }

    public void shut(int userId, int cardId) {
        Card card = cardService.getById(cardId);

        Equip equip = equipService.getById(card.getConfigure());
        equip.setReduce(equip.getReduce() + 1);
        equipService.updateById(equip);

        card.setState(0);
        card.setConfigure(null);
        card.setPostion(null);
        System.out.println("card:" + card);
        cardService.updateById(card);

        List<Consumer> consumers = consumerService.list(new QueryWrapper<Consumer>().eq("user_id", userId));
        Consumer consumer = consumers.get(consumers.size() - 1);
        consumer.setEndTime(LocalDateTime.now());
        consumerService.updateById(consumer);

    }
}
