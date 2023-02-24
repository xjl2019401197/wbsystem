package com.example.wbsystem_ssm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.wbsystem_ssm.service.TaskService;
import com.example.wbsystem_ssm.entity.Card;
import com.example.wbsystem_ssm.entity.Consumer;
import com.example.wbsystem_ssm.entity.Equip;
import com.example.wbsystem_ssm.entity.User;
import com.example.wbsystem_ssm.service.CardService;
import com.example.wbsystem_ssm.service.ConsumerService;
import com.example.wbsystem_ssm.service.EquipService;
import com.example.wbsystem_ssm.service.UserService;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class TaskServiceJob1Impl implements TaskService {

    @Autowired
    private UserService userService;
    @Autowired
    private CardService cardService;
    @Autowired
    private EquipService equipService;
    @Autowired
    private ConsumerService consumerService;

    @Override
    public void HandlerJob() {
        System.out.println("------扣钱任务开始执行---------：" + new Date());

        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "  " + Thread.currentThread().getName() + "  任务一启动");
        try {
            List<Equip> list = equipService.list();
            List<Card> cards = cardService.selectJoinList(
                    Card.class, new MPJLambdaWrapper<Card>()
                            .selectAll(Card.class)
                            .leftJoin(User.class, User::getUserId, Card::getUserId)
                            .eq(User::getDeleteFlag, 0)
                            .eq(User::getState, 1)
                            .eq(User::getUserType, 2)
                            .eq(Card::getState, 1)
                            .eq(Card::getDeleteFlag, 0)
            );
            for (Card card : cards) {
                    if (card.getSpendMoney() < card.getTotalMoney()) {
                        log.debug("开始扣钱");
                        Equip equip = list.get(card.getConfigure() - 1);
                        double spend;
                        if (card.getCardType() == 1)
                            spend = card.getSpendMoney() + 1.0 * equip.getVipPrice() / 60;
                        else spend = card.getSpendMoney() + 1.0 * equip.getNoVipPrice() / 60;
                        if (card.getTotalMoney() <= spend) card.setSpendMoney(card.getTotalMoney());
                        else card.setSpendMoney(spend);
                        System.out.println(card);
                    }
                    //余额不足，下机
                    else {
                        log.debug("余额不足，下机");
                        card.setState(0);
                        card.setConfigure(null);
                        card.setPostion(null);
                        List<Consumer> consumers = consumerService.list(new QueryWrapper<Consumer>().eq("user_id", card.getUserId()));
                        Consumer consumer = consumers.get(consumers.size() - 1);
                        consumer.setEndTime(LocalDateTime.now());
                        consumerService.updateById(consumer);
                    }
            }
            cardService.updateBatchById(cards);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "  " + Thread.currentThread().getName() + "  结束");

    }

    @Override
    public Integer jobId() {
        return 1;
    }
}