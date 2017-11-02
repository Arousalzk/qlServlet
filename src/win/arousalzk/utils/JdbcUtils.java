package win.arousalzk.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;


/**
 * JdbcUtils.java
 * 之前写的JDBCUtils实现获取和释放连接等简单操作
 * @author Arousalzk
 * @date 2017-11-02
 * @version 1.0
 */
public class JdbcUtils {

    
    private static Connection conn = null;
    
    private JdbcUtils() {
        //私有化构造方法
    }

    /**
     * JDBCUtils.java
     * 静态内部类
     * @author Arousalzk
     * @date 2017-11-02
     * @version 1.0
     */
    private static class Holder {
        private static final JdbcUtils SINSTANCE = new JdbcUtils();
    }
    
    public static JdbcUtils getJdbcUtils() {
        return Holder.SINSTANCE;
    }
    
    public Connection getConnection()  {
        
        if(null == conn) {
            try {
                initConnection();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return conn;
        
    }

    private void initConnection() throws SQLException, ClassNotFoundException {
        ResourceBundle rb = ResourceBundle.getBundle("db");
        String url = rb.getString("url");
        String user = rb.getString("username");
        String password = rb.getString("password");
        String driver = rb.getString("driver");
        Class.forName(driver);
        conn = DriverManager.getConnection(url, user, password);        
    }
    
    public void release(Connection conn, PreparedStatement pstmt, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (pstmt != null) {
            try {
                pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
}
