package cn.guet.demo9;

import cn.guet.util.ConnectionUtils;

import java.sql.*;

public class TestCallProcedure {
    public static void main(String[] args) {
//        callNoParamProcedure();
//        paramProcedure();
        paramsInOutProcedure();
//        paramsGetResult();
    }

    /**
     *调用存储过程，根据id号查询账户记录
     */
    public static void paramsGetResult() {
        try(Connection conn = ConnectionUtils.getConn()){
            CallableStatement cs = conn.prepareCall("call getInfo(?)");
            cs.setInt(1, 1);

            // 执行查询，返回结果集
            ResultSet resultSet = cs.executeQuery();
            if(resultSet.next()) {
                int accid = resultSet.getInt(1);
                String accname = resultSet.getString(2);
                float balance = resultSet.getFloat(3);
                System.out.println(accid + " " + accname + " " + balance);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * 通过存储过程实现转账，传入三个参数
     */
    public static void paramsInOutProcedure() {
        try(Connection conn = ConnectionUtils.getConn()){
            CallableStatement cs = conn.prepareCall("call transferOutParam(?,?,?,?)");
            cs.setFloat(1,500);
            cs.setInt(2,2);
            cs.setInt(3,1);
            // 注册out出参
            cs.registerOutParameter(4, Types.INTEGER);

            cs.executeUpdate();

            // 获取out参数结果
            int anInt = cs.getInt(4);

            System.out.println(anInt);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }


    /**
     * 调用存储过程实现转账
     * 传入三个参数
     *
     */

    public static void paramProcedure() {
        try(Connection conn = ConnectionUtils.getConn()){
            CallableStatement cs = conn.prepareCall("call transfer(?,?,?)");
            cs.setFloat(1,500);
            cs.setInt(2,1);
            cs.setInt(3,2);
            int i = cs.executeUpdate();
            System.out.println(i);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 调用无参数存储过程
     * 目标：创建和拷贝账户表
     * try-with-resources
     *
     * 第一步：先在MySQL端，创建存储过程，并且在数据库端进行测试
     * 第二步：使用JDBC执行过程
     */

    public static void callNoParamProcedure() {
       try(Connection conn = ConnectionUtils.getConn()){
           // 创建可以执行存储过程的声明对象
           CallableStatement cs = conn.prepareCall("call create_back");
           // 执行
           int i = cs.executeUpdate();

           System.out.println(i);
       }
        catch(Exception e){
           e.printStackTrace();
        }
    }

}
