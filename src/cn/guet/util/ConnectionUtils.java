package cn.guet.util;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.*;
import java.util.Properties;

/**
 * 专门用于创建链接和关闭链接
 */

public class ConnectionUtils {
    private static String user = "rootxx";
    private static String password = "123456xx";
    private static String driver = "com.mysql.cj.jdbc.Driverxx";
    private static String url = "jdbc:mysql://localhost:3306/bankxx";

    static {
        InputStream is = ConnectionUtils.class.getResourceAsStream("/db.properties");
        Properties prop = new Properties();
        try {
            prop.load(is);
            System.out.println(">>>>>>" + is);
            user = prop.getProperty("user");
            password = prop.getProperty("password");
            driver = prop.getProperty("driver");
            url = prop.getProperty("url");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取连接对象
     * @return connection
     */
    public static Connection getConn() {
        Connection connection = null;
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * 关闭链接 释放资源
     * @param conn
     * @param st
     * @param rs
     */
    public static void close(Connection conn, Statement st, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if (st != null) {
            try {
                st.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        if(conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
