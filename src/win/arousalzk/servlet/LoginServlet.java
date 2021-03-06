package win.arousalzk.servlet;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import win.arousalzk.domain.User;
import win.arousalzk.service.IUserService;
import win.arousalzk.service.impl.UserServiceImpl;


/**
 * LoginServlet.java
 * Servlet implementation class LoginServlet
 * 添加显示用户上次登录时间的功能
 * @author Arousalzk
 * @date 2017-11-15
 * @version 1.1
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private Properties properties;
    
    private static final String USER_LAST_LOGIN_TIME = "userLastLoginTime";
       
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
		
		String sessionCheckword = (String) request.getSession().getAttribute("checkword");
		String userCheckWord = request.getParameter("checkcode");
		if(null==sessionCheckword || !userCheckWord.equals(sessionCheckword)) {
            request.setAttribute("logininfo", "验证码错误");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return;
		}
		
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
                //没找到
//                response.getWriter().println("Login failed");
                request.setAttribute("logininfo", "用户名或密码错误");
                request.getRequestDispatcher("/login.jsp").forward(request, response);
                
            }else {
                
                //setContentType避免中文乱码
                response.setContentType("text/html; charset=UTF-8");
                //上一次登陆时间(如果不是第一次登录)
                Date date = null;
                //从ServletContext中获取当前登录总次数
                int count =(int) getServletContext().getAttribute("count");
                //从Cookie中获取当前用户登录的次数
                //1. 获取Cookies
                Cookie[] cookies = request.getCookies();
                //2. 拿到存储当前用户登录时间的cookie
                boolean bFirstLogin = true;
                for (Cookie cookie : cookies) {
                    if(USER_LAST_LOGIN_TIME.equals(cookie.getName())) {
                        //有存储上次登录时间对应的cookie
                        bFirstLogin = false;
                        String timeStr = cookie.getValue();
                        Long time = Long.valueOf(timeStr);
                        //获取上次登陆时间
                        date = new Date(time);
                    }
                }
                //不管是不是首次登录，保存新的登陆时间
                Cookie userLoginCookie = new Cookie(USER_LAST_LOGIN_TIME, System.currentTimeMillis()+"");
                userLoginCookie.setMaxAge(3600*24*30);
                response.addCookie(userLoginCookie);
                if(bFirstLogin) {
                    //当前用户是第一次登录，创建cookie，存储当前时间
                    response.getWriter().println("OK, " + currUser.getUsername() + "! 这是您注册后第一次登录...");
                } else {
                    //当前用户不是第一次登录
                    response.getWriter().println("OK, " + currUser.getUsername() + "! 您上次登录时间是：" + new SimpleDateFormat().format(date));
                }
                
                
                
                
                response.getWriter().println("是本服务器的第" + (++count) + "次登录请求!");
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
