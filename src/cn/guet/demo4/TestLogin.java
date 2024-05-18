package cn.guet.demo4;

import cn.guet.util.ConnectionUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * JDBC 应用案例
 * sql注入攻击
 * 1.实现非法登录，绕过验证
 * 2.特殊字符处理
 */

public class TestLogin {
    public static void main(String[] args) {
        String username = "' or 1=1 or ''=''"; //现在会报错了
       boolean isok = Login(username,"346534");
        System.out.println(isok);
    }

    /**
     * 验证用户名或密码是否匹配
     * @param username
     * @param password
     * 第一种解决方案：
     * 先通过用户名去查密码，然后和传入的密码匹配
     * 第二种解决方案
     * 使用预处理
     */
    public static boolean Login(String username, String password) {
        //先通过用户名来查密码,然后再匹配密码
        String sql = "select password from account where accname= '"+username+"'";
        System.out.println(">>>sql:"+sql);


        //获取连接
        Connection connection = ConnectionUtils.getConn();
        //创建Statement
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            //判断是否有记录
            if(resultSet.next()) { //用户名存在
                //取出密码
                String pass = resultSet.getString("password");
                if(password.equals(pass)) {
                    System.out.println("===成功的===");
                    return true;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            ConnectionUtils.close(connection,statement,resultSet);
        }
        return false;
    }
}
