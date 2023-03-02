package com.example.wbsystem_ssm.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.wbsystem_ssm.entity.UserInfo;
import com.example.wbsystem_ssm.redis.JedisUtil;
import com.example.wbsystem_ssm.entity.ResultBean;
import com.example.wbsystem_ssm.entity.User;
import com.example.wbsystem_ssm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
@CrossOrigin
public class UserLoginController {
    @Autowired
    private UserService userService;


    @Resource
    private AuthenticationManager authenticationManager;

    @GetMapping("/userLogin")
    public ResultBean userLogin(HttpServletRequest request, HttpServletResponse response) {
        String idCard = request.getParameter("account");
        String pwd = request.getParameter("pwd");
        ResultBean resultBean = null;
        Authentication authentication = null;
        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(idCard, pwd);
            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
            authentication = authenticationManager.authenticate(
                    authenticationToken);
            UserInfo userInfo = (UserInfo) authentication.getPrincipal();
            System.out.println(userInfo);
            if (userInfo == null) {
                throw new Exception("用户不存在");
            }
            authentication = SecurityContextHolder.getContext().getAuthentication();
            System.out.println("authentication1:"+authentication);
            User user = userService.getOne(new QueryWrapper<User>().eq("id_card", idCard));
            HttpSession session = request.getSession();
            session.setAttribute("user", JSON.toJSONString(user));
            System.out.println("session:"+session.getAttribute("user"));
            Jedis jedis = JedisUtil.getJedisCon();
            jedis.set("user", JSON.toJSONString(user));
            resultBean = new ResultBean();
            resultBean.setData("2");

        } catch (Exception e) {
            System.out.println(e);
            System.out.println(e.getMessage());
            e.printStackTrace();
            resultBean = new ResultBean(e);
            if (e.getMessage().equals("用户不存在"))
                resultBean.setData("0");
            else if (e.getMessage().equals("密码不正确"))
                resultBean.setData("1");
            else resultBean.setData("3");
        } finally {
            System.out.println(resultBean);
            return resultBean;
        }

    }
}
