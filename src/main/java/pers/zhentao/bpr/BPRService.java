package pers.zhentao.bpr;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alibaba.fastjson.JSONObject;

/**
 * service
 *
 * @author zhangzhentao1995@163.com 
 *         2017-11-28
 */
public class BPRService {

    private static final Logger logger = LogManager.getLogger(BPRService.class);

    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://";
    private static final String USER_NAME = "";
    private static final String PASSWORD = "";
    private static final String SQL_QUERY = "SELECT * FROM address_book";

    /**
     * 每次操作都新建数据库连接
     * 
     * @return 操作结果
     */
    public String getConnectEverytime() {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            logger.error("load driver failed.", e);
        }
        Connection conn;
        List<Object> list = new ArrayList<>();
        try {
            long t1 = System.currentTimeMillis();
            conn = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
            long t2 = System.currentTimeMillis();
            logger.info("get connection cost:" + (t2 - t1));
            logger.info("get connection success.");
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_QUERY);
            while (resultSet.next()) {
                Map<Integer, String> map = new HashMap<>();
                map.put(resultSet.getInt(1), resultSet.getString(2));
                list.add(map);
            }
            resultSet.close();
            statement.close();
            conn.close();
        } catch (Exception e) {
            logger.error(e);
        }
        return JSONObject.toJSONString(list);
    }

    /**
     * 第一次操作新建数据库连接
     * 
     * @return 操作结果
     */
    public String getConnectFirstTime() {
        Connection conn;
        List<Object> list = new ArrayList<>();
        try {
            long t1 = System.currentTimeMillis();
            conn = DBHelper.getInstance().getConnection();
            long t2 = System.currentTimeMillis();
            logger.info("get connection cost:" + (t2 - t1));
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_QUERY);
            while (resultSet.next()) {
                Map<Integer, String> map = new HashMap<>();
                map.put(resultSet.getInt(1), resultSet.getString(2));
                list.add(map);
            }
            resultSet.close();
            statement.close();
            conn = null;
        } catch (Exception e) {
            logger.error(e);
        }
        return JSONObject.toJSONString(list);
    }
}
