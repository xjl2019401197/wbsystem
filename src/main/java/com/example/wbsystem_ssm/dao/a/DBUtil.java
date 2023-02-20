package com.example.wbsystem_ssm.dao.a;

import java.sql.*;

public class DBUtil {

    //设置数据库和连接池变量
    final String driver = "com.mysql.jdbc.Driver";
    final String url = "jdbc:mysql://124.223.182.14/test";
    final String user = "root";
    final String password = "xjl011025";

    /**
     * 专门负责加载数据库驱动的方法
     */
    public void load() {
        try {
            Class.forName(driver);
            System.out.println("数据库驱动[ " + driver + "] 加载成功.");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("数据库驱动[ " + driver + "] 加载失败");
        }
    }

    /**
     * 专门负责获得数据库连接的方法
     *
     * @return
     */
    public Connection getConnection() {

        load();

        Connection conn = null;

        try {
            conn = DriverManager.getConnection(url, user, password);
            return conn;
        } catch (SQLException e) {
            throw new RuntimeException("连接数据库失败", e);
        }

    }

    //关闭连接
    public void closeConnection(Connection con, PreparedStatement ps, ResultSet rs) throws SQLException {
        if (rs != null) {
            rs.close();
        }
        if (ps != null) {
            ps.close();
        }
        if (con != null) {
            con.close();
        }
    }
}
