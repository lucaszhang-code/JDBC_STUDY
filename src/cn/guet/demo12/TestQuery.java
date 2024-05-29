package cn.guet.demo12;

import cn.guet.demo11.entity.Account;
import cn.guet.util.ConnectionUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.awt.image.ConvolveOp;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class TestQuery {
    public static void main(String[] args) {
//        queryOne();
        queryList();
    }

    public static void queryList(){
        Connection conn =  ConnectionUtils.getConn();
        QueryRunner queryRunner = new QueryRunner();
        String sql = "select accid,accname,password,balance,state from account";
        BeanListHandler<Account> handler = new BeanListHandler<>(Account.class);

        try {
            List<Account> list = queryRunner.query(conn, sql, handler);
            for(Account account:list){
                System.out.println(account);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionUtils.close(conn,null,null);
        }
    }

    public static void queryOne(){
        Connection conn =  ConnectionUtils.getConn();
        QueryRunner queryRunner = new QueryRunner();
        String sql = "select accid,accname,balance from account where accid = ?";
        // 实例化BeanHandler， 指定封装实体类
        BeanHandler<Account> rsh= new BeanHandler(Account.class);
        Object[] params = {1};
        try{
            Account account = queryRunner.query(conn, sql, rsh, params);
            System.out.println(account);
        }catch (SQLException throwables){
            throwables.printStackTrace();
        } finally {
            ConnectionUtils.close(conn,null,null);
        }

    }
}
