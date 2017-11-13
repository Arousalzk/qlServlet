package win.arousalzk.service;

import java.sql.SQLException;

import win.arousalzk.domain.User;

/**
 * IUserService.java
 * 用户服务业务层接口
 * @author Arousalzk
 * @date 2017-11-02
 * @version 1.0
 */
public interface IUserService {
    /**
     * 用户登录
     * @param user 用户实体
     * @return 查询之后的结果，如果不存在为空。
     * @throws SQLException 
     */
    User login(User user) throws SQLException;
    
    /**
     * 用户注册
     * @param user 要注册的用户实体
     * @return 返回注册成功的实体，如果已经存在返回null
     * @throws SQLException 
     */
    User register(User user) throws SQLException;
}
