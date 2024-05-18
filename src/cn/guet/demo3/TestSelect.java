package cn.guet.demo3;

import java.sql.*;

/**
 * 目标：查询账户中所有记录并打印输出
 * 结果类
 */

public class TestSelect {
    public static void main(String[] args) {
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/bank";
        String user = "root";
        String password = "123456";

        //1.加载驱动
        try {
            //1.加载驱动
            Class.forName(driver);
            //2.创建连接
            Connection connection = DriverManager.getConnection(url,user,password);
            //3.创建statement
            Statement statement = connection.createStatement();
            //4.执行SQL 返回一个结果集 ResultSet
            String sql = "select * from account";
            ResultSet resultSet = statement.executeQuery(sql);
            //5.遍历result结果集 默认游标指向第一行之前
            while (resultSet.next()) {
                //获取当前游指向的记录数据
                int accid= resultSet.getInt(1); //1表示列的位置，从1开始
                String accname = resultSet.getString(2);
                String accpass = resultSet.getString(3);
                Float balance = resultSet.getFloat(4);
                int state= resultSet.getInt(5);
                Date date = resultSet.getDate(6);

                System.out.println(accid + "===" + accname + "===" + accpass + "===" + balance + "===" + state);
            }

            //6.关闭 释放资源
            resultSet.close();
            statement.close();
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
