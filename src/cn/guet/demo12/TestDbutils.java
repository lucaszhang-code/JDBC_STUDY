package cn.guet.demo12;

import cn.guet.util.ConnectionUtils;
import com.mysql.cj.x.protobuf.MysqlxDatatypes;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.sql.Timestamp;

/**
 * dbutils
 * 开源组件，Apache 下的轻量级的工具包(组件)
 * 核心功能：查询结果集的封装，自动的封装成指定的JavaBean
 *
 */

public class TestDbutils {
    public static void main(String[] args) {
//        update();
        insert();
    }

    /**
     * 执行插入操作后，可以获取自动增长列的主键
     */
    public static void insert(){
        Connection conn = ConnectionUtils.getConn();
        QueryRunner qr = new QueryRunner();
        String sql = "insert into account(accname, password, balance, state, accdate) values(?, ?, ?, ?, ?)";
        Object[] params = {"张飞","6669999",10000,1,new Date()};
        try{
            ScalarHandler<BigInteger> handler = new ScalarHandler<>();

            BigInteger id = qr.insert(conn, sql, handler, params);
            System.out.println("id:" + id);
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }finally {
            ConnectionUtils.close(conn,null,null);
        }
    }

    /**
     * dbUtils执行的更新操作
     * ArrayHandler：把数据放进数组
     * ListHandler
     * BeanHandler
     * BeanListHandler
     * ScalarHandler：某一条记录中的某一个值
     */
    public static void update() {
        Connection conn = ConnectionUtils.getConn();

        QueryRunner queryRunner = new QueryRunner();

        // sql
//        String sql = "insert into account(accname, password, balance, state, accdate) values(?, ?, ?, ?, ?)";
        String sql1 = "delete from account where accid = ?";
//        String sql2 = "insert into account(accname, password, balance, state, accdate) values(?, ?, ?, ?, ?)";
        Object[] params = {11};
        try {
            int rows = queryRunner.update(conn, sql1, params);
            System.out.println(rows);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            ConnectionUtils.close(conn, null, null);
        }
    }
}
