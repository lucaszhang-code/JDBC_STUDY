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

public class TestLogin0 {
    public static void main(String[] args) {
        String username = "Lucas'";
       boolean isok = Login(username,"114514");
        System.out.println(isok);
    }

    /**
     * 验证用户名或密码是否匹配
     * @param username
     * @param password
     */
    public static boolean Login(String username, String password) {
        String sql = "select accid from account where accname= '"+username+"' and password='"+password+"'";
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
            if(resultSet.next()) {
                System.out.println("===成功的===");
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            ConnectionUtils.close(connection,statement,resultSet);
        }
        return false;
    }
}
