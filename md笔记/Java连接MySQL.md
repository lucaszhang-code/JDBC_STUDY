# JDBC

## 准备工作

##### 1.下载连接驱动 https://dev.mysql.com/downloads/file/?id=527658

##### 2.把.jar文件引入Java工程文件，并作为库

##### 3.创建一个数据库

```sql
-- 创建bank数据库
create database bank;
use bank;
```

## 连接数据库

##### 加载驱动

##### `mysql5` 写法 `com.mysql.jdbc.Driver`;

##### `mysql8`写法`com.mysql.cj.jdbc.Driver`

##### `url`地址一般是`jdbc:mysql://localhost:3306`,后面跟上数据库名

```java
public class TestConnection {
    public static void main(String[] args) {
        //mysql5 写法 com.mysql.jdbc.Driver
        String driver = "com.mysql.cj.jdbc.Driver"; //驱动就是一个类名
        String url = "jdbc:mysql://localhost:3306/bank";
        //用户名
        String user = "root";
        //密码
        String password = "123456";

        try {
            //1.加载驱动
            Class.forName(driver);
            //2.创建连接
            Connection connection = DriverManager.getConnection(url, user, password);
            //检查是否连接成功
            System.out.println("连接成功" + connection);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

##### 看见以下输出代表连接成功

![输出图片](./assets/输出结果.png)

## 插入数据

#### 数据库创建表

```sql
-- 创建表
create table account(
    accid int primary key auto_increment,
    accname varchar(30),
    password varchar(30),
    balance float,
    state int(1),
    accdata datetime
);
```

#### Java程序

##### 返回的是影响的列数，也就是有几条数据受到影响

##### 记得关闭`connection`和`statement`，后创建的先关闭；先创建的`connection`，后创建的`statement`;先删除`statement.close();`,再删除`connection.close();`

##### `sql`语句如果拿不准可以先在数据库查询控制台验证一下

##### `sql`插入语句本体

```sql
insert into account (accid, accname, password, balance, state, accdate)
values (1,'Lucas','123456',1000,1,now());
```

```java
package cn.guet.demo2;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * 目标：记录的插入，入库
 * 准备：先在MySQL创建表
 *
 * 步骤
 * 1.加载驱动
 * 2.创建连接
 * 3.创建statement
 * 4.执行SQL
 * 5.关闭
 */

public class TestInsert {
    public static void main(String[] args) {
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/bank";
        String user = "root";
        String password = "123456";

        //1.加载驱动
        try {
            //1.加载驱动
            Class.forName(driver);
            //2.创建连接
            Connection connection = DriverManager.getConnection(url,user,password);
            //3.创建statement
            Statement statement = connection.createStatement();
            //4.执行SQL
            String sql = "insert into account ( accname, password, balance, state, accdate)"+
                    "values ('Tom','45678',2000,0,now())";

            //执行SQL
            int rows = statement.executeUpdate(sql);
            //返回影响的行数
            System.out.println(">>>>rows" + rows);

            //关闭 释放资源
            statement.close();
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

```

## 修改数据

##### 只用修改sql语句就可以了

```java
package cn.guet.demo2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * DMl:insert update delete 更新操作 --- excuteUpdate()
 * DQL:select               查询操作 --- excuteQuery()
 * Statement
 */

public class Testupdate {
    public static void main(String[] args) {
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/bank";
        String user = "root";
        String password = "123456";

        //1.加载驱动
        try {
            //1.加载驱动
            Class.forName(driver);
            //2.创建连接
            Connection connection = DriverManager.getConnection(url,user,password);
            //3.创建statement
            Statement statement = connection.createStatement();
            //4.执行SQL
            String sql = "update account set accname = 'Sam',password = '66666' where accid = 3";

            //执行SQL返回影响的行数
            int rows = statement.executeUpdate(sql);
            System.out.println(">>>>rows" + rows);

            //关闭 释放资源
            statement.close();
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

```

## 删除数据

```java
package cn.guet.demo2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * 删除操作
 */

public class TestDelete {
    public static void main(String[] args) {
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/bank";
        String user = "root";
        String password = "123456";

        //1.加载驱动
        try {
            //1.加载驱动
            Class.forName(driver);
            //2.创建连接
            Connection connection = DriverManager.getConnection(url,user,password);
            //3.创建statement
            Statement statement = connection.createStatement();
            //4.执行SQL
            String sql = "delete from account where accid = 2";

            //执行SQL返回影响的行数
            int rows = statement.executeUpdate(sql);
            System.out.println(">>>>rows" + rows);

            //关闭 释放资源
            statement.close();
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

```

## 查询数据

##### 查询语句是`DQL`语句，不同于此前的`DML`语句

1. DMl:insert update delete    更新操作 --- excuteUpdate()
2. DQL:select              	       查询操作 --- excuteQuery()
##### excuteQuery()返回的是结果集(`ResultSet`)，需要使用`while`读取信息

##### `resultSet.next()`函数是查询下一列是否有数据，如果有数据返回`true`，没有返回`false`; 注意！默认游标指向第一行之前，也就是说从第一行之前开始遍历

##### `resultSet`也要记得关闭

```java
package cn.guet.demo3;

import java.sql.*;

/**
 * 目标：查询账户中所有记录并打印输出
 * 结果类
 */

public class TestSelect {
    public static void main(String[] args) {
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/bank";
        String user = "root";
        String password = "123456";

        //1.加载驱动
        try {
            //1.加载驱动
            Class.forName(driver);
            //2.创建连接
            Connection connection = DriverManager.getConnection(url,user,password);
            //3.创建statement
            Statement statement = connection.createStatement();
            //4.执行SQL 返回一个结果集 ResultSet
            String sql = "select * from account";
            ResultSet resultSet = statement.executeQuery(sql);
            //5.遍历result结果集 默认游标指向第一行之前
            while (resultSet.next()) {
                //获取当前游指向的记录数据
                //什么类型的数据用什么类型的变量接收
                int accid= resultSet.getInt(1); //1表示列的位置，从1开始
                String accname = resultSet.getString(2);
                String accpass = resultSet.getString(3);
                Float balance = resultSet.getFloat(4);
                int state= resultSet.getInt(5);
                Date date = resultSet.getDate(6);

                System.out.println(accid + "===" + accname + "===" + accpass + "===" + balance + "===" + state);
            }

            //6.关闭 释放资源
            resultSet.close();
            statement.close();
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

```

##### 运行结果

![select输出结果](./assets/select输出结果.png)

## 登录验证案例

##### 我们的目的是验证用户输入的用户名和密码是否相等，所以对应的sql语句是

```sql
select accid from account where accname= '"+username+"' and password='"+password+"'
```

##### 其中因为`sql`语句在`Java`中是字符串，我们传入数据需要通过`"+参数+"`的形式

### 封装连接语句和关闭语句

##### 在之前的代码中，我们多次连接数据库，和关闭数据库，其实可以把这部分的代码进行封装

- 1.单独创建`utils`文件夹
- 2.创建类

```java
package cn.guet.util;
import java.sql.*;

/**
 * 专门用于创建链接和关闭链接
 */

public class ConnectionUtils {
    private static String user = "root";
    private static String password = "123456";
    private static String driver = "com.mysql.cj.jdbc.Driver";
    private static String url = "jdbc:mysql://localhost:3306/bank";

    /**
     * 获取连接对象
     * @return connection
     */
    public static Connection getConn() {
        Connection connection = null;
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * 关闭链接 释放资源
     * @param conn
     * @param st
     * @param rs
     */
    public static void close(Connection conn, Statement st, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if (st != null) {
            try {
                st.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        if(conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}


```

### 实现验证类

##### 我们让用户输入`username`和`password`,通过`sql`语句返回对应的accid，使用`resultSet.next()`判断是否有返回的resultSet数据集，如果有就会进入if判断，返回true，如果没有则不会进入if判断，返回false

##### 你可能会注意到一点，我们把`Statement statement = null; ResultSet resultSet = null;`放在`try`语句外面先声明，目的是如果他们在try语句里面，`finally`语句是无法找到`statement`和`resultSet`的，因为他们不在同一作用域，所以我们要把他放在try外面先声明

```java
package cn.guet.demo4;

import cn.guet.util.ConnectionUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * JDBC 应用案例
 * sql注入攻击
 * 1.实现非法登录，绕过验证
 * 2.特殊字符处理
 */

public class TestLogin0 {
    public static void main(String[] args) {
        String username = "Lucas"; 
       boolean isok = Login(username,"114514");
        System.out.println(isok);
    }

    /**
     * 验证用户名或密码是否匹配
     * @param username
     * @param password
     */
    public static boolean Login(String username, String password) {
        String sql = "select accid from account where accname= '"+username+"' and password='"+password+"'";
        System.out.println(">>>sql:"+sql);


        //获取连接
        Connection connection = ConnectionUtils.getConn();
        //创建Statement
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            //判断是否有记录
            if(resultSet.next()) {
                System.out.println("===成功的===");
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            ConnectionUtils.close(connection,statement,resultSet);
        }
        return false;
    }
}
```

## sql注入

##### 这种方式从逻辑上并没有不妥，但是验证用户名密码是否正确本质上还是通过`sql`语句进行验证的，而恰恰用户输入的`username`和`password`是直接放入`sql`语句去执行的，这可能会导致用户输入特殊的用户名和密码去改变`sql`语句的正确性，比如如果用户名输入`' or 1=1 or ''=''`这个用户名，密码随意，那么它的`sql`语句就是`select accid from account where accname= '' or 1=1 or ''=''' and password='123456'`,在sql语句中or的优先级是要大于and的，1=1是true，'='也是true，password是false，最终sql得出的结果是true，也就是说虽然密码并不正确，但是sql语句还是得出了true的结果，从而得到所有的accid,其实具体是哪个accid并不重要，但是我们只是判断有没有accid从而得出用户名密码是否正确的结论，所以程序会执行true

##### 我们再想象一种情况，如果用户输入的用户名中包含特殊字符如单引号，程序是会抛出异常的，如果用户一直频繁登录，程序一直抛出异常，这对服务器的压力很大，严重的可能会使服务器宕机

##### 用户输入的用户名带单引号

```java
 public static void main(String[] args) {
        String username = "Lucas'";
       boolean isok = Login(username,"114514");
        System.out.println(isok);
    }
```

##### 程序会抛出异常

![单引号抛出异常](./assets/单引号异常.png)

### sql注入类型

- 1.实现非法登录，绕过验证
- 2.特殊字符处理

### 解决方法

- 1.先通过用户名去查密码，然后和传入的密码匹配
- 2.使用预处理

### 先通过用户名去查密码，然后和传入的密码匹配

##### 在这里我们没有去使用and语句查询accid了，而是通过用户名去查密码，然后将数据库里的密码与用户输入的密码进行比对，从而判断用户名密码是否正确

```java
 	public static boolean Login(String username, String password) {
        //先通过用户名来查密码,然后再匹配密码
        String sql = "select password from account where accname= '"+username+"'";
        System.out.println(">>>sql:"+sql);


        //获取连接
        Connection connection = ConnectionUtils.getConn();
        //创建Statement
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            //判断是否有记录
            if(resultSet.next()) { //用户名存在
                //取出密码
                String pass = resultSet.getString("password");
                if(password.equals(pass)) {
                    System.out.println("===成功的===");
                    return true;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            ConnectionUtils.close(connection,statement,resultSet);
        }
        return false;
    }
```

### 预处理

##### 预处理是应对sql注入使用最普遍的方式，他有以下优点

* 1.防止sql注入
* 2.对于特殊字符处理
* 3.提高执行的效率

#### 插入数据

##### 我们先将sql语句写在外面，对于要写入的数据我们可以使用英文的问号?作为占位符，并且在外面先声明`PreparedStatement`

##### 如果我们多次插入相似的语句，预处理只要检查一次sql语句的语法完整性，后面只是往里面填入数据，这可以提升程序的运行效率

##### `setString`，`setInt`语句传入两个值，第一个值表示给第几个参数传值，这对应sql语句的顺序；第二个值表示传入的数据，数据类型必须对应参数要求的数据类型

```java
	public static void testInsert(){
        //?表示占位符
        String sql = "insert into account ( accname, password, balance, state, accdate)"+
                "values (?,?,?,?,?)";

        //创建连接
        Connection conn = ConnectionUtils.getConn();
        //创建PreparedStatement === 注意：需要提前传入sql
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql); //预编译处理
            //在执行之前，先要给占位符赋值
            pst.setString(1,"曹操");
            pst.setString(2,"123456");
            pst.setFloat(3,2000);
            pst.setInt(4,1);
            pst.setDate(5,new Date(System.currentTimeMillis()));

            int rows = pst.executeUpdate();

            System.out.println(rows);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            ConnectionUtils.close(conn,pst,null);
        }
    }
```

#### 修改数据

```java
	public static void testUpdate(){
        //?表示占位符
        String sql = "update account set password=? where accid=?";

        //创建连接
        Connection conn = ConnectionUtils.getConn();
        //创建PreparedStatement === 注意：需要提前传入sql
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql); //预编译处理
            //在执行之前，先要给占位符赋值
            pst.setString(1,"114514");
            pst.setInt(2,1);

            int rows = pst.executeUpdate();

            System.out.println(rows);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            ConnectionUtils.close(conn,pst,null);
        }
    }
```

#### 删除数据

```java
    public static void testDelete(){
        //?表示占位符
        String sql = "delete from account where accid=?";

        //创建连接
        Connection conn = ConnectionUtils.getConn();
        //创建PreparedStatement === 注意：需要提前传入sql
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql); //预编译处理
            //在执行之前，先要给占位符赋值
            pst.setInt(1,6);

            int rows = pst.executeUpdate();

            System.out.println(rows);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            ConnectionUtils.close(conn,pst,null);
        }
    }
```

#### 预处理的应用

```java
package cn.guet.demo5;

import cn.guet.util.ConnectionUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 使用预处理来实现登录逻辑，验证是否能解决sql注入问题
 */

public class testLogin {
    public static void main(String[] args) {
        String username = "' or 1=1 or ''=''";
        boolean isok = login(username,"12345");
        System.out.println(isok);
    }

    public static boolean login(String username, String password) {
        // -----------区别1：sql 换成占位符 -----------
        String sql = "select * from account where accname=? and password=?";
        Connection conn = ConnectionUtils.getConn();

        try {
            // --------------区别2：提前去传入sql进行预处理，预编译------------------
            PreparedStatement pst = conn.prepareStatement(sql);
            //-------------------区别3：在执行前进行占位符赋值-----------------------
            pst.setString(1, username);
            pst.setString(2, password);

            //--------------区别4：执行时，不再传入sql------------------
            // 执行 返回结果集
            ResultSet resultSet = pst.executeQuery();
            if(resultSet.next()) {
                System.out.println("成功");
                return true;
            }

            ConnectionUtils.close(conn,pst,resultSet);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return false;
    }
}

```

## 银行转账案例

##### 我们使用预处理进行两个用户的转账操作，比如Sam用户向Lucas用户转入500元，他们对应`sql`操作是

```sql
update account set balance = balance + 500 where accname = "Lucas";
```

```sql
update account set balance = balance - 500 where accname = "Sam"
```

##### 我们可以发现，他们的sql语句很相似，只有`balance`和`accname`不一样，这是使用预处理的前提

##### 所以预处理的代码是

```java
 String sql = "update account set balance = balance + ? where accname = ?";
```

```java
package cn.guet.demo6;

import cn.guet.util.ConnectionUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 本类实现：模拟银行转账业务逻辑
 * 事务
 */

public class TestBankTrans {
    public static void main(String[] args) throws SQLException {
        String sql = "update account set balance = balance + ? where accname = ?";
        // 获取链接
        Connection conn = ConnectionUtils.getConn();
        // 创建预处理
        PreparedStatement pst = conn.prepareStatement(sql);
        // 给占位符赋值
        pst.setFloat(1, 500);
        pst.setString(2,"Lucas");
        //执行
        int rows = pst.executeUpdate();
        
        // 给Sam用户减500
        pst.setFloat(1,-500);
        pst.setString(2,"Sam");
        int  i = pst.executeUpdate();

        System.out.println(rows+"===" + i);
        ConnectionUtils.close(conn,pst,null);
    }

}
```

##### 代码整体上很好理解，我们利用`balance+-金额`实现金额的改变，先让Lucas加500元，再让Sam减500元，并返回数据库改变的列数

##### 从逻辑上当然没什么问题，但是对于多并发的场景如果中途程序出现异常是会造成麻烦的

##### 比如，如图我们在第一个转账操作执行后手动抛出异常，那么`"".substring(12)`下面的代码是不会执行的，但是Lucas的转账操作已经执行了，但Sam的转账操作不会执行，因此程序出现bug

```java
 pst.setFloat(1, 500);
        pst.setString(2,"Lucas");
        //执行
        int rows = pst.executeUpdate();

        "".substring(12); // 主动抛出异常

        // 给Sam用户减500
        pst.setFloat(1,-500);
        pst.setString(2,"Sam");
        int  i = pst.executeUpdate();
```

### 解决方法

##### 我们的想法是转账操作要么都完成，要么都不完成，如果只有一方完成了，另一方未完成，需要进行回滚，回到初始状态；这里通过`事务`来解决

#### 事务的四大特性

- 原子性：指事务是数据库的逻辑工作单位，事务中包括的操作要么都做，要么都不做，不可分割。这确保了事务中包含的程序要么全部执行，要么完全不执行，保持了操作的原子性。
- 一致性：指事务执行的结果必须是使数据库从一个一致性状态变到另一个一致性状态。这保证了事务在完成时，必须使所有的数据保持一致状态，即数据的完整性和一致性得到维护。
- 隔离性：指并发的事务是互相隔离的，一个事务的执行不能被其他事务干扰。这确保了即使多个事务并发执行，它们之间也是相互独立的，每个事务都有各自完整的数据空间，保证了并发执行的事务之间不能互相干扰。
- 持久性：指一旦事务提交后，它对数据库中的数据改变就应该是永久的。即使系统或介质发生故障时，已提交事务的更新不会丢失，保证了数据的持久性和稳定性。

##### 我们使用`conn.setAutoCommit(false)`关闭自动提交事务，也就是只有我们允许提交事务时，才会进行数据库的数据更改，当所有的操作都完成后，再通过`conn.commit()`手动提交事务，在下方`catch`语句使用`conn.rollback()`进行数据回滚，如果转账操作发生异常抛出错误，就执行回滚操作
```java
package cn.guet.demo6;

import cn.guet.util.ConnectionUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TestTransaction {
    public static void main(String[] args) {
        String sql = "update account set balance = balance + ? where accname = ?";
        // 获取链接
        Connection conn = ConnectionUtils.getConn();
        PreparedStatement pst = null;
        try {
            // 关闭自动提交 手动提交
            conn.setAutoCommit(false);

            // 创建预处理
            pst = conn.prepareStatement(sql);
            // 给占位符赋值
            pst.setFloat(1, 500);
            pst.setString(2, "Lucas");
            //执行
            int rows = pst.executeUpdate(); // -------不再自动提交事务--------

//            "".substring(12); // 主动抛出异常

            // 给Sam用户减500
            pst.setFloat(1, -500);
            pst.setString(2, "Sam");
            int i = pst.executeUpdate();

            // 提交事务
            conn.commit(); // --------手动提交事务--------

            System.out.println(rows + "===" + i);
        }
        catch (Exception e) {
            try {
                // 事务回滚
                conn.rollback(); // --------事务回滚，恢复到初始状态--------
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
        finally {
            ConnectionUtils.close(conn, pst, null);
        }
    }
}

```

