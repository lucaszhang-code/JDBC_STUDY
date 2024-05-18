package cn.guet.demo2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * DMl:insert update delete 更新操作 --- excuteUpdate()
 * DQL:select               查询操作 --- excuteQuery()
 * Statement
 */

public class Testupdate {
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
            //4.执行SQL
            String sql = "update account set accname = 'Sam',password = '66666' where accid = 3";

            //执行SQL返回影响的行数
            int rows = statement.executeUpdate(sql);
            System.out.println(">>>>rows" + rows);

            //关闭 释放资源
            statement.close();
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
