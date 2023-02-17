package com.example.wbsystem_ssm.controller;

import com.example.wbsystem_ssm.dao.a.DBUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


@WebServlet(name = "userAdd", value = "/userUpdate")
public class UserUpdateController extends HttpServlet {
    private String message;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 返回参数
        // 获取内容格式
        String contentType = request.getContentType();
        if (contentType != null && !contentType.equals("")) {
            contentType = contentType.split(";")[0];
        }

        Integer userId = Integer.parseInt(request.getParameter("userId"));
        String userName = request.getParameter("userName");
        String pwd = request.getParameter("pwd");
        String phone = request.getParameter("phone");
        String qq = request.getParameter("qq");
        String wxchat = request.getParameter("wxchat");
        String email = request.getParameter("email");


        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            DBUtil dbUtil = new DBUtil();
            con = dbUtil.getConnection();

            String strSql = "update `user` set user_name = '" + userName + "'," +
                    "  pwd = '" + pwd + "'," +
                    "  phone = '" + phone + "'," +
                    "  qq = '" + qq + "'," +
                    "  wxchat = '" + wxchat + "'," +
                    "  email = '" + email + "'" +
                    " WHERE user_id = " + userId;
            stmt = con.createStatement();
            stmt.execute(strSql);
        } catch (Exception e) {
            e.printStackTrace();

            response.setContentType("text/html");
            response.setCharacterEncoding("utf-8");
            PrintWriter out = response.getWriter();
            out.println("添加失败!");
            out.close();
            return;
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        OutputStream os = response.getOutputStream();
        String resMsgOut = "true";
        os.write(resMsgOut.getBytes("GBK"));
    }
}
