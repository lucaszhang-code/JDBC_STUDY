package cn.guet.demo11.DAO;

import cn.guet.demo11.entity.Account;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * DAO:Database Access Object 数据库访问对象
 *
 *
 *
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


    /**
     * 自动封装：把ResultSet结果集中的记录自动提取并封装，放入list
     * @param sql
     * @param cls
     * @param params
     * @return
     * @param <T>
     */
    public <T> List<T> query(String sql,Class<T> cls,Object... params){
        rs = query(sql, params);

        List<T> list = new ArrayList();

        try{
            // 元数据
            ResultSetMetaData metaData = rs.getMetaData();
            // 列数
            int columnCount = metaData.getColumnCount();
            while(rs.next()){
                // 实例化实体类 Account account = new Account()
                T obj = cls.getDeclaredConstructor(String.class).newInstance(rs.getString(1));
                for(int i = 0;i<columnCount;i++){
                    // 取到列名
                    String columnName = metaData.getColumnLabel(i+1);
                    // 根据列名取出当前列的值
                    Object val = rs.getObject(columnName);
                    // 赋值 account.setAccname(accname)
                    // 使用属性名去拼接处对象的setter方法的名字
                    String setterName = "set"+columnName.substring(0,1).toUpperCase()+columnName.substring(1);

                    // 反射获取属性对象
                    Field prop = cls.getDeclaredField(columnName);
                    Class<?> type = prop.getType();
                    // 通过反射对应的方法对象(提供方法名，方法参数类型)
                    Method setterMethod = cls.getDeclaredMethod(setterName, type);

                    // 执行方法 account.setAccname("")
                    setterMethod.invoke(obj, val);
                }

                // 把封装好的对象放入list
                list.add(obj);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            close();
        }
        return list;
    }
}
