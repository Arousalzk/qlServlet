package win.arousalzk.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.Properties;
import java.util.ResourceBundle;

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
    private Properties properties;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
    }

    @Override
    public void init() throws ServletException {
        //读取配置文件中的count数
        super.init();
        
        properties = new Properties();
        try {
            properties.load(getServletContext().getResourceAsStream("WEB-INF/classes/app.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        int count = Integer.valueOf(properties.getProperty("count"));
//        System.out.println(properties.getProperty("count"));
        getServletContext().setAttribute("count", count);
    }
    
    @Override
    public void destroy() {
        super.destroy();
        int count = (int) getServletContext().getAttribute("count");
        System.out.println(count);
        properties.setProperty("count",String.valueOf(count));
        try {
            //在该Servlet销毁时候，记录登录次数，并存入配置文件
            FileOutputStream oFile = new FileOutputStream(getServletContext().getRealPath("WEB-INF/classes/app.properties"));
            properties.store(oFile, "no comments");
        } catch (IOException e) {
            e.printStackTrace();
        }
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
		//测试通过ServletContext获取src下的文件绝对路径
		/*
		ServletContext servletContext = this.getServletContext();
		String realPath = servletContext.getRealPath("WEB-INF/classes/111.txt");
		String path = LoginServlet.class.getClassLoader().getResource("111.txt").getPath();
		System.out.println(realPath);
		System.out.println(path);
		*/
		try {
            User currUser = userServiceImpl.login(user);
            //测试向ServletContext对象中存取数据
            /*
            servletContext.setAttribute("currUser", user);
            */
            if(null == currUser) {
                response.getWriter().println("Login failed");
            }else {
                int count =(int) getServletContext().getAttribute("count");
                //setContentType避免中文乱码
                response.setContentType("text/html; charset=UTF-8");
                response.getWriter().println("OK, " + currUser.getUsername() + "! 这是本服务器的第" + (++count) + "次登录请求!");
                getServletContext().setAttribute("count", count);
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
