package cn.guet.demo5;

import cn.guet.util.ConnectionUtils;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 本类演示PreparedStatement的用法
 * 预处理
 * 1.防止sql注入
 * 2.对于特殊字符处理
 * 3.提高执行的效率
 */

public class TestPreparedStatement {
    public static void main(String[] args) {
        testDelete();
    }

    /**
     * 使用预处理插入记录
     * 占位符
     * 在执行sql前给占位符赋值
     */

    public static void testInsert(){
        //?表示占位符
        String sql = "insert into account ( accname, password, balance, state, accdate)"+
                "values (?,?,?,?,?)";

        //创建连接
        Connection conn = ConnectionUtils.getConn();
        //创建PreparedStatement === 注意：需要提前传入sql
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql); //预编译处理
            //在执行之前，先要给占位符赋值
            pst.setString(1,"曹操");
            pst.setString(2,"123456");
            pst.setFloat(3,2000);
            pst.setInt(4,1);
            pst.setDate(5,new Date(System.currentTimeMillis()));

            int rows = pst.executeUpdate();

            System.out.println(rows);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            ConnectionUtils.close(conn,pst,null);
        }
    }

    public static void testUpdate(){
        //?表示占位符
        String sql = "update account set password=? where accid=?";

        //创建连接
        Connection conn = ConnectionUtils.getConn();
        //创建PreparedStatement === 注意：需要提前传入sql
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql); //预编译处理
            //在执行之前，先要给占位符赋值
            pst.setString(1,"114514");
            pst.setInt(2,1);

            int rows = pst.executeUpdate();

            System.out.println(rows);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            ConnectionUtils.close(conn,pst,null);
        }
    }

    public static void testDelete(){
        //?表示占位符
        String sql = "delete from account where accid=?";

        //创建连接
        Connection conn = ConnectionUtils.getConn();
        //创建PreparedStatement === 注意：需要提前传入sql
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql); //预编译处理
            //在执行之前，先要给占位符赋值
            pst.setInt(1,6);

            int rows = pst.executeUpdate();

            System.out.println(rows);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            ConnectionUtils.close(conn,pst,null);
        }
    }
}
