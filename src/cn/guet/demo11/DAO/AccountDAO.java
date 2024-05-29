package cn.guet.demo11.DAO;

import cn.guet.demo10.BaseDao;
import cn.guet.demo11.entity.Account;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 数据库访问层
 */

public class AccountDAO extends BaseDao {
    public void save(Account account) {
        String sql = "insert into account(accname,password,balance,state,accdate) values(?,?,?,?,?)";
        Object[] params = {account.getAccid(),account.getPassword(),account.getBalance(),
                account.getState(),account.getAccdate()};
        int rows = super.update(sql,params);
        System.out.println(rows);
    }

    public Account findAccountByAccname(String accname){
       String sql = "select accid,accname,password,accdate from account where accname=?";
       ResultSet rs = super.query(sql,1);
       try{
           if(rs.next()){
                Account account = new Account();
                String name = rs.getString("accname");
                String password = rs.getString("password");
//                String accdate = rs.getString("accdate");
                // 封装到实体类对象中
               account.setPassword(password);
               account.setAccname(name);
               return account;
           }

       }catch(Exception e){
           e.printStackTrace();
       }
       return null;
    }

    public List<Account> queryAll(){
        String sql = "select * from account";
//        return super.query(sql,Account.class);
        return null;
    }



    public List<Account> queryAllxx(){
        String sql = "select * from account";
        List<Account> list = new ArrayList<>();
        ResultSet rs = super.query(sql);
        try{
            while (rs.next()){
                // 把一条记录中所有列的值取出
                int id = rs.getInt("accid");
                String accname = rs.getString("accname");
                String password = rs.getString("password");
                String accdate = rs.getString("accdate");
                float accbalance = rs.getFloat("balance");
                Date date = rs.getDate("accdate");

                // 封装到实体对象中
                Account account = new Account();
                account.setAccid(id);
                account.setAccname(accname);
                account.setPassword(password);
                account.setBalance(accbalance);
                account.setAccdate(date);

                // 把实体对象添加到list列表
                list.add(account);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            super.close();
        }
        return list;
    }
}
