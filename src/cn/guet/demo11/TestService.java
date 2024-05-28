package cn.guet.demo11;

import cn.guet.demo11.entity.Account;
import cn.guet.demo11.service.AccountService;

import java.util.Date;
import java.util.List;

public class TestService {
    public static void main(String[] args) {
        AccountService service = new AccountService();

        Account account = new Account();

//        account.setAccid(5);
//        account.setAccname("Lucas");
//        account.setPassword("123456");
////        account.setBalance(2000);
////        account.setState(1);
////        account.setAccdate(new Date());
//
////        service.register(account);
//
//        // 登录
//        boolean login = service.login(account);
//        System.out.println(login);

        // 账户列表
        List<Account> accounts = service.queryAll();
        for (Account account1 : accounts){
            System.out.println(account1);
        }
    }
}
