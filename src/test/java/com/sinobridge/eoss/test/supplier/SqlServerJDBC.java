/*
 * FileName: SqlServerJDBC.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.test.supplier;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * <code>SqlServerJDBC</code>
 * 
 * @version  1.0
 * @author  guokemenng
 * @since 1.0  2014年3月31日
 */
public class SqlServerJDBC {

    public static Connection getConnect() {

        String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver"; // 加载JDBC驱动
        String dbURL = "jdbc:sqlserver://10.2.2.7:1433;DatabaseName=SinoERP61";
        String userName = "sa"; // 用户名
        String userPwd = "sa"; // 密码
        Connection connect = null;
        try {
            // 加载SQLSERVER JDBC驱动程序
            Class.forName(driverName);
            //建立数据库连接
            connect = DriverManager.getConnection(dbURL, userName, userPwd);
        } catch (ClassNotFoundException e) {
            System.out.print("Error loading SQLServer Driver!");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.print("get data error!");
            e.printStackTrace();
        } 

        return connect;
    }

}
