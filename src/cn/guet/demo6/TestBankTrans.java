package cn.guet.demo6;

import cn.guet.util.ConnectionUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 本类实现：模拟银行转账业务逻辑
 * 事务
 */

public class TestBankTrans {
    public static void main(String[] args) throws SQLException {
        String sql = "update account set balance = balance + ? where accname = ?";
        // 获取链接
        Connection conn = ConnectionUtils.getConn();
        // 创建预处理
        PreparedStatement pst = conn.prepareStatement(sql);
        // 给占位符赋值
        pst.setFloat(1, 500);
        pst.setString(2,"Lucas");
        //执行
        int rows = pst.executeUpdate();

        "".substring(12); // 主动抛出异常

        // 给Sam用户减500
        pst.setFloat(1,-500);
        pst.setString(2,"Sam");
        int  i = pst.executeUpdate();

        System.out.println(rows+"===" + i);
        ConnectionUtils.close(conn,pst,null);
    }

}
