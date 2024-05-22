package cn.guet.demo8;

import cn.guet.util.ConnectionUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * 通过读取配置文件获取JDBC参数
 *1.配置资源文件 db.properties
 * 2.加载配置文件，读取配置信息
 *
 * 注意：修改的是ConnectionUtils
 */

public class TestProperties {
    public static void main(String[] args) {
        // try-with-resources 自动在finally中关闭
        try(
                Connection conn = ConnectionUtils.getConn();
                PreparedStatement ps = conn.prepareStatement("select * from account");
        ){
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                System.out.println("读取成功");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
