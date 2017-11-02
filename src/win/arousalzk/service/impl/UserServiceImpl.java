package win.arousalzk.service.impl;

import java.sql.SQLException;

import win.arousalzk.dao.IUserDao;
import win.arousalzk.dao.impl.UserDaoImpl;
import win.arousalzk.domain.User;
import win.arousalzk.service.IUserService;

/**
 * UserServiceImpl.java
 * 用户服务业务层实现类，操作用户的一些动作
 * @author Arousalzk
 * @date 2017-11-02
 * @version 1.0
 */
public class UserServiceImpl implements IUserService {

    @Override
    public User login(User user) throws SQLException {
        IUserDao userDaoImpl = new UserDaoImpl();
        return userDaoImpl.login(user);
    }

}
