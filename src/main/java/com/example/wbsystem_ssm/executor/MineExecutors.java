//package com.example.wbsystem_ssm.executor;
//
//import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//import com.example.wbsystem_ssm.entity.Card;
//import com.example.wbsystem_ssm.entity.Consumer;
//import com.example.wbsystem_ssm.entity.Equip;
//import com.example.wbsystem_ssm.entity.User;
//import com.example.wbsystem_ssm.service.CardService;
//import com.example.wbsystem_ssm.service.ConsumerService;
//import com.example.wbsystem_ssm.service.EquipService;
//import com.example.wbsystem_ssm.service.UserService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//import java.text.SimpleDateFormat;
//import java.time.LocalDateTime;
//import java.util.Arrays;
//import java.util.Date;
//import java.util.List;
//import java.util.concurrent.Executors;
//import java.util.concurrent.ScheduledExecutorService;
//import java.util.concurrent.TimeUnit;
//
//@Component
//@Slf4j
//public class MineExecutors {
//
//    private final static ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(5);
//
//    private final static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//
//    @Autowired
//    private UserService userService;
//    @Autowired
//    private CardService cardService;
//    @Autowired
//    private EquipService equipService;
//
//    @Autowired
//    private ConsumerService consumerService;
//
//    @PostConstruct
//    public void init() {
//        scheduler.scheduleAtFixedRate(() -> {
//            try {
//                List<Equip> list = equipService.list();
//                List<User> users = userService.list(new QueryWrapper<User>().eq("user_type", 2));
//                for (int i = 0; i < users.size(); i++) {
//                    User user = users.get(i);
//                    Card card = cardService.getOne(new QueryWrapper<Card>().eq("user_id", user.getUserId()).eq("state", 1));
//                    if (card != null) {
//                        if (card.getSpendMoney() < card.getTotalMoney()) {
//                            log.debug("开始扣钱");
//                            Equip equip = list.get(card.getConfigure() - 1);
//                            double spend;
//                            if (card.getCardType() == 1)
//                                spend = card.getSpendMoney() + 1.0 * equip.getVipPrice() / 60;
//                            else spend = card.getSpendMoney() + 1.0 * equip.getNoVipPrice() / 60;
//                            if (card.getTotalMoney() <= spend) card.setSpendMoney(card.getTotalMoney());
//                            else card.setSpendMoney(spend);
//                            System.out.println(card);
//                        }
//                        //余额不足，下机
//                        else {
//                            log.debug("余额不足，下机");
//                            card.setState(0);
//                            card.setConfigure(null);
//                            card.setPostion(null);
//                            List<Consumer> consumers = consumerService.list(new QueryWrapper<Consumer>().eq("user_id", card.getUserId()));
//                            Consumer consumer = consumers.get(consumers.size() - 1);
//                            consumer.setEndTime(LocalDateTime.now());
//                            consumerService.updateById(consumer);
//                        }
//                        cardService.updateById(card);
//                    }
//                }
//            } catch (Exception e) {
//                log.error("定时任务执行出错");
//            }
//        }, 0, 1 * 60, TimeUnit.SECONDS);
//        log.info("初始化成功 {}", format.format(new Date()));
//    }
//
//}
