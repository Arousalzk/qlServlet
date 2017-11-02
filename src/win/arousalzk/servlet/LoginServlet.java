package win.arousalzk.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import win.arousalzk.domain.User;
import win.arousalzk.service.IUserService;
import win.arousalzk.service.impl.UserServiceImpl;


/**
 * LoginServlet.java
 * Servlet implementation class LoginServlet
 * @author Arousalzk
 * @date 2017-11-01
 * @version 1.0
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
    }

    
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		response.getWriter().append("Served at: ").append(request.getContextPath());
	    //编码格式设定为utf-8,避免乱码问题；
		request.setCharacterEncoding("UTF-8");
		//1. 获取username和password数据
		String username = request.getParameter("username");
		String password = request.getParameter("userpassword");
	    //2. 调用Service进行登录；
		IUserService userServiceImpl = new UserServiceImpl();
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		response.setCharacterEncoding("UTF-8");
		try {
            User currUser = userServiceImpl.login(user);
            if(null == currUser) {
                response.getWriter().println("Login failed");
            }else {
                response.getWriter().println("OK, " + currUser.getUsername());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
	    
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
