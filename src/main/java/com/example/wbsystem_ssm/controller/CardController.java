package com.example.wbsystem_ssm.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.wbsystem_ssm.entity.*;
import com.example.wbsystem_ssm.service.CardService;
import com.example.wbsystem_ssm.service.ConsumerService;
import com.example.wbsystem_ssm.service.RefundService;
import com.example.wbsystem_ssm.service.UserService;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.apache.commons.lang3.StringUtils;
import sun.util.resources.LocaleData;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@RestController
public class CardController {
    @Autowired
    private CardService cardService;

    @Autowired
    private RefundService refundService;

    @Autowired
    private UserService userService;

    @Autowired
    private ConsumerService consumerService;

    @GetMapping("/cardList")
    public IPage<CardDto> cardList(HttpServletRequest request, HttpServletResponse response) {
        String idCard2 = request.getParameter("idCard");
        String level2 = request.getParameter("level");
        String phone2 = request.getParameter("phone");
        String sex2 = request.getParameter("sex");
        String cardId = request.getParameter("cardId");
        String param = request.getParameter("current");
        System.out.println("cardId:"+cardId+"idCard2:"+idCard2 +"level2:" + level2 + "--phone:" + phone2 + "--sex:" + sex2);
        HttpSession session = request.getSession();
        User currentUser = JSON.parseObject((String)session.getAttribute("user"),User.class);

        Integer page = 1;
        if(param != null) page  = Integer.parseInt(param);
        Page pages = new Page<>(page, 5);

        IPage<CardDto> iPage = null;
        try {
            iPage = cardService.selectJoinListPage(pages, CardDto.class,
                    new MPJLambdaWrapper<Card>()
                            .selectAll(Card.class)
                            .select(User::getIdCard)
                            .select(User::getNickName)
                            .leftJoin(User.class, User::getUserId, Card::getUserId)
                            .eq(Card::getDeleteFlag, 0)
                            .eq(StringUtils.isNotEmpty(level2),Card::getLevel,level2)
                            .like(StringUtils.isNotEmpty(phone2),Card::getPhone,phone2)
                            .eq(StringUtils.isNotEmpty(sex2),Card::getSex,sex2)
                            .eq(StringUtils.isNotEmpty(cardId),Card::getCardId,cardId)
                            .eq(currentUser != null,User::getUserType,currentUser.getUserType() + 1)
                            .like(StringUtils.isNotEmpty(idCard2),User::getIdCard,idCard2)
            );

//            list = cardService.list(new QueryWrapper<Card>().eq("delete_flag", 0).eq(StringUtils.isNotEmpty(level2),"level",level2).like(StringUtils.isNotEmpty(phone2),"phone",phone2).eq(StringUtils.isNotEmpty(sex2),"sex",sex2).eq(StringUtils.isNotEmpty(cardId),"card_id",cardId));
//            for (Card card : list) {
//                User user = userService.getById(card.getUserId());
//                CardDto cardDto = new CardDto();
//                PropertyUtils.copyProperties(cardDto, card);
//                cardDto.setNickName(user.getNickName());
//                cardDto.setIdCard(user.getIdCard());
//                if( user.getUserType() == currentUser.getUserType() + 1 && (StringUtils.isEmpty(idCard2) || user.getIdCard().contains(idCard2)))
//                cardDtos.add(cardDto);
//            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return iPage;
        }
    }

    @GetMapping("/card/add")
    public ResultBean cardAdd(HttpServletRequest request, HttpServletResponse response) {

        Integer cardType = Integer.parseInt(request.getParameter("cardType"));
        String userId = request.getParameter("userId");
        String phone = request.getParameter("phone");
        Integer sex = Integer.parseInt(request.getParameter("sex"));
        Card card = new Card();
        card.setUserId(Integer.parseInt(userId));
        card.setCardType(cardType);
        card.setSex(sex);
        card.setPhone(phone);
        card.setCreateUser(Integer.parseInt(userId));
        card.setUpdateUser(Integer.parseInt(userId));
        ResultBean resultBean = null;
        try {
            cardService.save(card);
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

    @GetMapping("/card/update")
    public ResultBean cardUpdate(HttpServletRequest request, HttpServletResponse response) {

        Integer userId = Integer.parseInt(request.getParameter("userId"));
        Integer cardId = Integer.parseInt(request.getParameter("cardId"));
        String cardType = request.getParameter("cardType");
        Double amount = Double.parseDouble(request.getParameter("amount"));
        ResultBean resultBean = null;
        try {
            Card card = cardService.getById(cardId);
            card.setTotalMoney(card.getTotalMoney() + amount);
            cardService.updateById(card);
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

    @GetMapping("/card/del")
    public ResultBean del(HttpServletRequest request, HttpServletResponse response) {

        Integer cardId = Integer.parseInt(request.getParameter("cardId"));
        ResultBean resultBean = null;
        try {
            Card card = cardService.getById(cardId);
            card.setDeleteFlag(1);
            cardService.updateById(card);
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

    /*
     * 提交退费申请
     * */
    @PostMapping("/card/refund")
    public ResultBean refund(HttpServletRequest request, HttpServletResponse response) {

        Integer userId = Integer.parseInt(request.getParameter("userId"));
        Integer cardId = Integer.parseInt(request.getParameter("cardId"));
       ResultBean resultBean = null;
        try {
//            ClassLoader classLoader = MineExecutors.class.getClassLoader();


            //改变用户状态
            User user = userService.getById(userId);
            user.setState(1);
            userService.updateById(user);

            //退费
            Refund refund = new Refund();
            refund.setUserId(userId);
            refund.setCardId(cardId);
            refundService.save(refund);
            //清空用户余额
            Card card = cardService.getById(cardId);
            card.setTotalMoney(0.0);
            card.setSpendMoney(0.0);
            card.setLevel(0);
            cardService.updateById(card);
            shutDownALl();
//            Class aClass = classLoader.loadClass("com.example.wbsystem_ssm.executor.MineExecutors");

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
