package com.example.wbsystem_ssm.controller;

import com.alibaba.fastjson.JSON;
import com.example.wbsystem_ssm.redis.JedisUtil;
import com.example.wbsystem_ssm.entity.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/*

@WebServlet(name = "userInfoServlet", value = "/cybercafe/userInfo")
public class UserInfoController extends HttpServlet {
    private String message;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doPost(request,response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        System.out.println(1);
//        User user = (User) request.getSession().getAttribute("user");
        Jedis jedis = JedisUtill.getJedisCon();
        System.out.println(JSON.parseObject(jedis.get("user")));

        User user = JSON.parseObject(jedis.get("user"), User.class);
        OutputStream os = response.getOutputStream();
//        PrintWriter writer = response.getWriter();
        os.write(user.toString().getBytes(StandardCharsets.UTF_8));
    }

    public void destroy() {
    }
}*/
@RestController
public class UserInfoController{
    @GetMapping("/userInfo")
    public User userInfo(HttpServletRequest request,HttpServletResponse response){
        User user = null;
        try{
            HttpSession session = request.getSession();
            user = JSON.parseObject((String) session.getAttribute("user"),User.class);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return user;
        }
    }

}