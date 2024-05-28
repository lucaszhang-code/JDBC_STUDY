package cn.guet.demo10;

import cn.guet.util.ConnectionUtils;

import java.sql.*;
import java.util.Arrays;

/**
 * DAO:Database Access Object 数据库访问对象
 * 操作数据库的底层类
 * 两大类：
 * 1.更新操作 insert update delete
 * 2.查询操作 select
 */

public class BaseDao {
    private String username = "root";
    private String password = "123456";
    private String url = "jdbc:mysql://127.0.0.1:3306/bank";
    private String driver = "com.mysql.cj.jdbc.Driver";

    private Connection conn ;
    private PreparedStatement ps ;
    private ResultSet rs ;

    private boolean isAutoCommit = true;

    /**
     * 设置是否自动提交
     * @param isAutoCommit
     */
    public void setAutoCommit(boolean isAutoCommit) {
        isAutoCommit = isAutoCommit;
    }

    /**
     * 单独封装
     */
    public void commit(){
        if(isAutoCommit && conn != null){
            try {
                conn.commit();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    /**
     * 连接函数
     * @return
     */
    public Connection getConnection() {
        try {
            if (conn == null || conn.isClosed()) {
                Class.forName(driver);
                conn = DriverManager.getConnection(url, username, password);
            }
            return conn;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public int update(String sql, Object... params) {
        System.out.println("SQL:" + sql);
        System.out.println("Params:" + Arrays.toString(params));

        // 连接
        conn = getConnection();
        // 声明 PreparedStatement
        try{
            // 设置是否自动提交
            conn.setAutoCommit(isAutoCommit);

            ps = conn.prepareStatement(sql);
            // 给占位符赋值
            if(params != null) {
                for(int i = 0;i<params.length;i++){
                    ps.setObject(i+1, params[i]);
                }
            }

            int i = ps.executeUpdate();
            return i;
        }
        catch(Exception e){
            if(!isAutoCommit){
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
            e.printStackTrace();
        }
        finally {
            close();
        }
        // 执行sql
        return 1;
    }

    public ResultSet query(String sql, Object... params) {
        System.out.println("SQL:" + sql);
        System.out.println("Params:" + Arrays.toString(params));
        // 连接
        conn = getConnection();
        // 声明 PreparedStatement
        try {
            ps = conn.prepareStatement(sql);
            // 先给占位符赋值
            if(params != null) {
                for(int i = 0;i<params.length;i++){
                    ps.setObject(i+1, params[i]);
                }
            }
            // 执行sql
            rs = ps.executeQuery();
            return rs;

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            // close();
        }
    }

    /**
     * 关闭连接
     */

    public  void close(){
        try{
            if(rs != null){
                rs.close();
                rs = null;
            }
            if(ps != null){
                ps.close();
                ps = null;
            }
            if(conn != null){
                conn.close();
                conn = null;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
