package cn.guet.demo5;

import cn.guet.util.ConnectionUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 使用预处理来实现登录逻辑，验证是否能解决sql注入问题
 */

public class testLogin {
    public static void main(String[] args) {
        String username = "' or 1=1 or ''=''";
        boolean isok = login(username,"12345");
        System.out.println(isok);
    }

    public static boolean login(String username, String password) {
        // -----------区别1：sql 换成占位符 -----------
        String sql = "select * from account where accname=? and password=?";
        Connection conn = ConnectionUtils.getConn();

        try {
            // --------------区别2：提前去传入sql进行预处理，预编译------------------
            PreparedStatement pst = conn.prepareStatement(sql);
            //-------------------区别3：在执行前进行占位符赋值-----------------------
            pst.setString(1, username);
            pst.setString(2, password);

            //--------------区别4：执行时，不再传入sql------------------
            // 执行 返回结果集
            ResultSet resultSet = pst.executeQuery();
            if(resultSet.next()) {
                System.out.println("成功");
                return true;
            }

            ConnectionUtils.close(conn,pst,resultSet);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return false;
    }
}
