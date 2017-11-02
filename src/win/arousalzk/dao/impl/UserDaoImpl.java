package win.arousalzk.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import win.arousalzk.dao.IUserDao;
import win.arousalzk.domain.User;
import win.arousalzk.utils.JdbcUtils;

/**
 * UserDaoImpl.java
 * DAO层
 * @author Arousalzk
 * @date 2017-11-02
 * @version 1.0
 */
public class UserDaoImpl implements IUserDao {

    @Override
    public User login(User user) throws SQLException {
        
        Connection conn = JdbcUtils.getJdbcUtils().getConnection();
        //添加binary让其大小写敏感
        String sql = "select * from userinfo where binary username=? and binary password=?;";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        //注意这里和Hibernate的Query对象不大一样，Query对象是从0开始。
        preparedStatement.setString(1, user.getUsername());
        preparedStatement.setString(2, user.getPassword());
        ResultSet rs = preparedStatement.executeQuery();
        if(rs.next()) {
            System.out.println(rs.getString("id"));
            System.out.println(rs.getString("password"));
            System.out.println(rs.getString("username"));
            return user;
        }
        System.out.println("........没这个用户");
        return null;
    }

}
