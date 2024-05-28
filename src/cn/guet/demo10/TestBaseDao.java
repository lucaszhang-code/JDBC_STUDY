package cn.guet.demo10;


import java.sql.ResultSet;
import java.util.Date;

/**
 *
 */

public class TestBaseDao {
    public static void main(String[] args) {
        String sql = "select * from account where accid = ?";


        BaseDao dao = new BaseDao();
        ResultSet rs = dao.query(sql,1);

        try{
            while (rs.next()){
                String id = rs.getString("accid");
                String name = rs.getString("accname");
                String pwd = rs.getString("password");
                String balance = rs.getString("balance");
                String state = rs.getString("state");
                String time = rs.getString("accdate");
                System.out.println(id + " " + name + " " + pwd + " " + balance + " " + state + " " + time + " ");
            }
        } catch(Exception e){
            e.printStackTrace();
        } finally {
            dao.close();
        }

    }

    public static void test(String[] args) {
        String sql = "insert into account (accname,password,balance,state,accdate) value(?,?,?,?,?)";

        Object[] params = {"Andy","123456789",1200,1,new Date()};
        BaseDao dao = new BaseDao();
        dao.setAutoCommit(false);
        int update = dao.update(sql,params);

        // 提交
        dao.commit();
        System.out.println(update);
    }
}
