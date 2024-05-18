package cn.guet.demo1;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;

/**
 * 目标：能够打通连接即可
 * 准备工作
 * 目标：创建连接
 * 四个参数：
 * 1.驱动 driver
 * 2.URL
 * 3.用户名
 * 4.密码
 * 五个步骤：
 * 1.加载驱动
 * 2.创建连接
 * 3.创建statement
 * 4.创建SQL
 * 5.关闭，释放资源
 */

public class TestConnection {
    public static void main(String[] args) {
        //mysql5 写法 com.mysql.jdbc.Driver
        String driver = "com.mysql.cj.jdbc.Driver"; //驱动就是一个类名
        String url = "jdbc:mysql://localhost:3306/bank";
        String user = "root";
        String password = "123456";

        //加载驱动
        try {
            //1.加载驱动
            Class.forName(driver);
            //2.创建连接
            Connection connection = DriverManager.getConnection(url, user, password);
            System.out.println("连接成功" + connection);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
