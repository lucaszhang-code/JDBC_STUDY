package cn.guet.demo11.service;
import cn.guet.demo11.DAO.AccountDAO;
import cn.guet.demo11.entity.Account;


import java.util.Date;
import java.util.List;

/**
 * 账户业务
 */

public class AccountService {
private AccountDAO accountDAO = new AccountDAO();
    /**
     * 登录业务
     * @param account
     * @return
     */
    public boolean login(Account account){
        Account dbaccount = accountDAO.findAccountByAccname(account.getAccname());
        if(dbaccount==null){ // 用户名有效
            // 在匹配密码
            if(dbaccount.getPassword() != null && dbaccount.getPassword().equals(account.getPassword())){
                // 密码匹配成功 说明是合法用户
                return true;
            }
        }
        return false;
    }

    /**
     * 注册业务
     * @param
     * @return
     */
    public void register(Account account){
        accountDAO.save(account);
    }

    /**
     * 查询列表
     */
    public List<Account> queryAll(){
        return accountDAO.queryAll();
    }
}
