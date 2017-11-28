package pers.zhentao.bpr;

import java.sql.Connection;
import java.sql.DriverManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 数据库连接管理类，单例（静态内部类模式）
 *
 * @author zhangzhentao1995@163.com 
 *         2017-11-28
 */
public class DBHelper {

    private static final Logger logger = LogManager.getLogger(DBHelper.class);

    private Connection connection;

    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://";
    private static final String USER_NAME = "";
    private static final String PASSWORD = "";

    private static class LazyHolder {
        private static final DBHelper helper = new DBHelper();
    }

    private DBHelper() {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            logger.error("load driver failed.", e);
        }
        try {
            connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
            logger.info("get connection success.");
        } catch (Exception e) {
            logger.error("get connection failed.", e);
        }
    }

    public static final DBHelper getInstance() {
        return LazyHolder.helper;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
