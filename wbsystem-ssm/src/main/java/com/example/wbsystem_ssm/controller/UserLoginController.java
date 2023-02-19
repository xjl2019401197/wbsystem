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
            System.out.println("authentication:"+authentication);
            User user = userService.getOne(new QueryWrapper<User>().eq("id_card", idCard));
            HttpSession session = request.getSession();
            session.setAttribute("user", JSON.toJSONString(user));
//            session.setAttribute("user", user);
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
//@WebServlet(name = "userLogin", value = "/userLogin")
//public class UserLoginController extends HttpServlet {
//    private String message;
//
////    @Autowired
////    private RedisTemplate redisTemplate;
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
//        String userName = request.getParameter("userName");
//        String pwd = request.getParameter("pwd");
//
//        Connection con = null;
//        Statement stmt = null;
//        ResultSet rs = null;
//        boolean flag = false;
//        User user = new User();
//
//        try {
//            DBUtil dbUtil = new DBUtil();
//            con = dbUtil.getConnection();
//
//            // 编写sql语句
//            String strSql = "SELECT * FROM `user` WHERE user_name = '" + userName + "' and pwd = '" + pwd + "'";
//
//            // 创建一个 PreparedStatement 对象，初始化sql语句
//            PreparedStatement ps = con.prepareStatement(strSql);
//            // 获取执行sql语句后的结果集
//            rs = ps.executeQuery();
//
//            // 遍历结果集，添加到list中
//            while (rs.next()) {
//                flag = true;
//
//                user.setUserId(rs.getInt("user_id"));
//                user.setUserType(rs.getInt("user_type"));
//                user.setUserName(rs.getString("user_name"));
//                user.setNickname(rs.getString("nickname"));
//                user.setPwd(rs.getString("pwd"));
//                user.setPhone(rs.getString("phone"));
//                user.setQq(rs.getString("qq"));
//                user.setWxchat(rs.getString("wxchat"));
//                user.setEmail(rs.getString("email"));
//                user.setSex(rs.getInt("sex"));
//            }
//
//
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
//
//        if (flag) {
//            HttpSession session = request.getSession();
//            session.setAttribute("user", JSON.toJSONString(user));
//            Jedis jedis = JedisUtill.getJedisCon();
//            System.out.println(JSON.toJSONString(user));
//            jedis.set("user", JSON.toJSONString(user));
//            String resMsgOut = "2";
//            os.write(resMsgOut.getBytes("GBK"));
//        } else {
//            String resMsgOut = "1";
//            os.write(resMsgOut.getBytes("GBK"));
//        }
//    }
//
//    public void destroy() {
//    }
//}
