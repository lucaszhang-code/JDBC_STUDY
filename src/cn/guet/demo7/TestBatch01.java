package cn.guet.demo7;

import cn.guet.util.ConnectionUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TestBatch01 {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis(); // 开始时间

        test2(100000);

        long endTime = System.currentTimeMillis(); // 结束时间

        System.out.println("耗时：" + (endTime - startTime) + "ms");
    }

    /**
     * count：插入的记录数
     * 普通的单条插入
     * 10万条数据插入耗时：476226ms
     */

    public static void test1(int count) {
        String sql = "insert into account(accname, password, balance, state, accdate) values(?,?,?,?,now())";
        Connection conn = ConnectionUtils.getConn();
        PreparedStatement pst = null;

        try {
            pst = conn.prepareStatement(sql);
            for (int i = 1; i <= count; i++) {
                pst.setString(1, i + "name");
                pst.setString(2, i + "pwd");
                pst.setFloat(3, 1000);
                pst.setInt(4, 1);

                //执行
                pst.executeUpdate();
            }

        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            ConnectionUtils.close(conn, pst, null);
        }
    }

    /**
     * count：插入的记录数
     * 使用批量插入
     * 10万条数据插入耗时：14939ms
     *
     * 注意：内存溢出问题
     */

    public static void test2(int count) {
        String sql = "insert into account(accname, password, balance, state, accdate) values(?,?,?,?,now())";
        Connection conn = ConnectionUtils.getConn();
        PreparedStatement pst = null;

        try {
            // 1.先关闭自动提交
            conn.setAutoCommit(false);


            pst = conn.prepareStatement(sql);
            for (int i = 1; i <= count; i++) {
                pst.setString(1, i + "name");
                pst.setString(2, i + "pwd");
                pst.setFloat(3, 1000);
                pst.setInt(4, 1);

                //执行
//                pst.executeUpdate();
                pst.addBatch(); // 2.把要添加的记录先放入缓存

                if((i + 1) % 6000 == 0){ // 每6000次 执行一次
                    pst.executeBatch();
                    conn.commit(); // 避免内存溢出
                }
            }

            // 3.批量的执行
            pst.executeBatch();


            // 4.提交事务
            conn.commit();

        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            ConnectionUtils.close(conn, pst, null);
        }
    }


}
