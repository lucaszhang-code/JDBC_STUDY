package cn.guet.demo10;

import cn.guet.util.ConnectionUtils;

import java.sql.*;

public class TestResultSet {
    public static void main(String[] args) {
        String sql = "select accid,accname name,password from account";
        query(sql);
    }

    public static void query(String sql){
        Connection conn = ConnectionUtils.getConn();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
           // 获取元数据对象
            ResultSetMetaData metaData = rs.getMetaData();
            // 获取列数
            int columnCount = metaData.getColumnCount();
            System.out.println(columnCount);

            for (int i = 1; i <= columnCount; i++) {
                String columnName = metaData.getColumnName(i); // 从一开始的
//                int columnType = metaData.getColumnType(i);
                String labelName = metaData.getColumnLabel(i);
                System.out.println(columnName + " " + labelName);
            }

            // 获取表的名字
            String tableName = metaData.getTableName(1);
            System.out.println("表名：" + tableName);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            ConnectionUtils.close(conn,ps,rs);
        }

    }
}
