package cn.guet.demo11.entity;

import java.util.Date;

/**
 * JavaBean 实体类 表对应
 */

public class Account {
    private int accid;
    private String accname;
    private String password;
    private float balance;
    private int state;
    private Date accdate;

    public void setAccid(int accid) {
        this.accid = accid;
    }

    public void setAccname(String accname) {
        this.accname = accname;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public void setAccdate(Date accdate) {
        this.accdate = accdate;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getAccid() {
        return accid;
    }

    public String getAccname() {
        return accname;
    }

    public String getPassword() {
        return password;
    }

    public float getBalance() {
        return balance;
    }

    public int getState() {
        return state;
    }

    public Date getAccdate() {
        return accdate;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accid=" + accid +
                ", accname='" + accname + '\'' +
                ", password='" + password + '\'' +
                ", balance=" + balance +
                ", state=" + state +
                ", accdate=" + accdate +
                '}';
    }
}
