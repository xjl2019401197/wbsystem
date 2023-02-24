package com.example.wbsystem_ssm.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.example.wbsystem_ssm.entity.*;
import com.example.wbsystem_ssm.executor.MyScheduledTask;
import com.example.wbsystem_ssm.service.CardService;
import com.example.wbsystem_ssm.service.ConsumerService;
import com.example.wbsystem_ssm.service.EquipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@RestController
public class EquipController {
    @Autowired
    private EquipService equipService;

    @Autowired
    private ConsumerService consumerService;

    @Autowired
    private CardService cardService;

    @Autowired
    private MyScheduledTask scheduledTask;

   @GetMapping("/equip/list")
    public List<Equip> list(){

       List<Equip> list = null;
       try{
           list = equipService.list(null);
       }catch (Exception e){
           e.printStackTrace();
       }finally {
           return list;
       }
   }
    @PostMapping("/equip/update")
    public ResultBean cardUpdate(HttpServletRequest request, HttpServletResponse response) {

        Integer equipId = Integer.parseInt(request.getParameter("equipId"));
        Integer number = Integer.parseInt(request.getParameter("number"));
        Integer vipPrice = Integer.parseInt(request.getParameter("vipPrice"));
        Integer noVipPrice = Integer.parseInt(request.getParameter("noVipPrice"));
        ResultBean resultBean = null;
        try {
            Equip equip = equipService.getById(equipId);
            equip.setNumber(number);
            equip.setVipPrice(vipPrice);
            equip.setNoVipPrice(noVipPrice);
            equipService.updateById(equip);
            shutDownALl();
            List<TaskEntity> list = null;
            if (CollectionUtils.isEmpty(list)) {
                // 这里模拟将要停止的cron可通过前端传来
                list = Constant.list;
            }
            scheduledTask.stop(list);
            scheduledTask.refresh(list);
            System.out.println("task任务:" + list.toString() + "已经重启");
            resultBean = new ResultBean();
            resultBean.setData(true);
        } catch (Exception e) {
            resultBean = new ResultBean(e);
            resultBean.setData("添加失败");
            e.printStackTrace();
        } finally {
            return resultBean;
        }
    }
    @GetMapping("/equip/useDetails")
    public List useDetails(){
       List equipList = null;
       equipList = new ArrayList<HashMap<String,Integer>>();
        List<Equip> list = equipService.list(null);
        for (Equip equip : list) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("name",equip.getName());
            map.put("total",equip.getNumber());
            map.put("reduce",equip.getNumber());
            equipList.add(map);
        }
        return equipList;
    }
    public void shutDownALl(){
        List<Card> list = cardService.list(new QueryWrapper<Card>().eq("state", 1));
        list.stream().forEach(card -> card.setState(0));
        cardService.updateBatchById(list);
        for (Card card : list) {
            List<Consumer> consumers = consumerService.list(new QueryWrapper<Consumer>().eq("card_id", card.getCardId()));
            consumers.get(consumers.size() - 1).setEndTime(LocalDateTime.now());
            consumerService.updateById(consumers.get(consumers.size() - 1));
        }
    }
}