package com.example.wbsystem_ssm.controller;

import com.alibaba.fastjson.JSON;
import com.example.wbsystem_ssm.redis.JedisUtil;
import com.example.wbsystem_ssm.entity.Card;
import com.example.wbsystem_ssm.entity.ResultBean;
import com.example.wbsystem_ssm.entity.TopUp;
import com.example.wbsystem_ssm.entity.User;
import com.example.wbsystem_ssm.service.CardService;
import com.example.wbsystem_ssm.service.TopUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class TopUpAddController{
    @Autowired
    private TopUpService topUpService;

    @Autowired
    private CardService cardService;

    @GetMapping("/TopUpAdd")
    public ResultBean topUpAdd(HttpServletRequest request, HttpServletResponse response){
        Double money = Double.parseDouble(request.getParameter("money"));
        String way = request.getParameter("way");
        Integer cardId = Integer.parseInt(request.getParameter("cardId"));
        Integer userId = Integer.parseInt(request.getParameter("userId"));
        Jedis jedis = JedisUtil.getJedisCon();
        User user = JSON.parseObject(jedis.get("user").toString(),User.class);
        TopUp topUp = new TopUp();
        topUp.setCardId(cardId);
        topUp.setUserId(userId);
        topUp.setMoney(money);
        topUp.setWay(way);
        topUp.setCreateUser(user.getUserId());
        topUp.setUpdateUser(user.getUserId());

        ResultBean resultBean = null;
        try {
            topUpService.save(topUp);
            Card card = cardService.getById(cardId);
            card.setTotalMoney(card.getTotalMoney() + money);
            if(card.getTotalMoney() >= 100)
                card.setLevel((int)(card.getTotalMoney() /100));
            cardService.updateById(card);
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


//@WebServlet(name = "topUpAdd", value = "/TopUpAdd")
//public class TopUpAddController extends HttpServlet {
//    private String message;
//
//    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        doPost(request, response);
//    }
//
//    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        // 返回参数
//        // 获取内容格式
//        String contentType = request.getContentType();
//        if (contentType != null && !contentType.equals("")) {
//            contentType = contentType.split(";")[0];
//        }
//
//        Double money = Double.parseDouble(request.getParameter("money"));
//        String way = request.getParameter("way");
//        String cardId = request.getParameter("cardId");
//
//
//        Connection con = null;
//        Statement stmt = null;
//        ResultSet rs = null;
//
//        try {
//            DBUtil dbUtil = new DBUtil();
//            con = dbUtil.getConnection();
//
//            String strSql = "insert into `top_up`(money,way,card_id) "
//                    + "values(" + money + "," + way + "," + cardId + ") ";
//            stmt = con.createStatement();
//            stmt.execute(strSql);
//        } catch (Exception e) {
//            e.printStackTrace();
//
//            response.setContentType("text/html");
//            response.setCharacterEncoding("utf-8");
//            PrintWriter out = response.getWriter();
//            out.println("添加失败!");
//            out.close();
//            return;
//        } finally {
//            try {
//                con.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//
//
//        //修改卡总金额
//        try {
//            DBUtil dbUtil = new DBUtil();
//            con = dbUtil.getConnection();
//
//            String strSql = "update card set total_money = total_money + " + money +
//                    " , residue_money = residue_money + " + money +
//                    " WHERE card_id = " + cardId;
//            stmt = con.createStatement();
//            stmt.execute(strSql);
//        } catch (Exception e) {
//            e.printStackTrace();
//
//            response.setContentType("text/html");
//            response.setCharacterEncoding("utf-8");
//            PrintWriter out = response.getWriter();
//            out.println("添加失败!");
//            out.close();
//            return;
//        } finally {
//            try {
//                con.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//
//        OutputStream os = response.getOutputStream();
//        String resMsgOut = "true";
//        os.write(resMsgOut.getBytes("GBK"));
//    }
//}
