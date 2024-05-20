package cn.guet.demo6;

import cn.guet.util.ConnectionUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TestTransaction {
    public static void main(String[] args) {
        String sql = "update account set balance = balance + ? where accname = ?";
        // 获取链接
        Connection conn = ConnectionUtils.getConn();
        PreparedStatement pst = null;
        try {
            // 关闭自动提交 手动提交
            conn.setAutoCommit(false);

            // 创建预处理
            pst = conn.prepareStatement(sql);
            // 给占位符赋值
            pst.setFloat(1, 500);
            pst.setString(2, "Lucas");
            //执行
            int rows = pst.executeUpdate(); // -------不再自动提交事务--------

//            "".substring(12); // 主动抛出异常

            // 给Sam用户减500
            pst.setFloat(1, -500);
            pst.setString(2, "Sam");
            int i = pst.executeUpdate();

            // 提交事务
            conn.commit(); // --------手动提交事务--------

            System.out.println(rows + "===" + i);
        }
        catch (Exception e) {
            try {
                // 事务回滚
                conn.rollback(); // --------事务回滚，恢复到初始状态--------
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
        finally {
            ConnectionUtils.close(conn, pst, null);
        }
    }
}
