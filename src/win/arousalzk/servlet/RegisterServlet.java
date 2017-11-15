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
 * RegisterServlet.java
 * Servlet implementation class Register
 * @author Arousalzk
 * @date 2017-11-08
 * @version 1.0
 */
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    request.setCharacterEncoding("UTF-8");
	    //获取用户名密码
	    String userName = new String(request.getParameter("username").getBytes("ISO8859-1"));
	    String password = request.getParameter("userpassword");

	    User user = new User();
	    user.setUsername(userName);
	    user.setPassword(password);
	    
	    //调用用户服务，添加用户
	    IUserService userService = new UserServiceImpl();
	    boolean bStatus = false;
	    try {
            User resultUser = userService.register(user);
            if(null != resultUser) {
                bStatus = true;
            }
            System.out.println("添加成功");
        } catch (SQLException e) {
            e.printStackTrace();
        }
	    if(bStatus) {
	        //1.成功，稍后返回登录页面
	        response.sendRedirect(request.getContextPath() + "/login.jsp");
	    } else{
	        //2.失败，直接在原页面提示该用户名已被人注册
	        request.setAttribute("logininfo", "该用户名已被人注册");
	        request.getRequestDispatcher("/login.jsp").forward(request, response);
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
