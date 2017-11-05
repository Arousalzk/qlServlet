package win.arousalzk.servlet;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sun.misc.BASE64Encoder;


/**
 * DownloadServlet.java
 * Servlet implementation class DownloadServlet
 * @author Arousalzk
 * @date 2017-11-05
 * @version 1.0
 */
public class DownloadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String BROWSER_IE = "MSIE";
	private static final String BROWSER_FIREFOX = "Firefox";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DownloadServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    
	    
	    //获取文件名
	    String fileName = new String(request.getParameter("filename").getBytes("ISO8859-1"), "UTF-8");
	    //通过User-Agent获取浏览器类型
	    String agent = request.getHeader("User-Agent");
	    String filenameEncoder = "";
	    //根据浏览器类型拼接文件名
        if (agent.contains(BROWSER_IE)) {
            // IE浏览器
            filenameEncoder = URLEncoder.encode(fileName, "utf-8");
            filenameEncoder = filenameEncoder.replace("+", " ");
        } else if (agent.contains(BROWSER_FIREFOX)) {
            // 火狐浏览器
            BASE64Encoder base64Encoder = new BASE64Encoder();
            filenameEncoder = "=?utf-8?B?"
                    + base64Encoder.encode(fileName.getBytes("utf-8")) + "?=";
        } else {
            // 其它浏览器
            filenameEncoder = URLEncoder.encode(fileName, "utf-8");             
        }
        
        //设置内容类型
        response.setHeader("Content-Type", getServletContext().getMimeType(fileName));
//        response.setContentType("application/x-download");
        //设置Content-Disposition头.....注意千万别拼错，下载的文件没文件名，找了半天原因
        response.addHeader("Content-Disposition", "attachment;filename=" + filenameEncoder);  
	    
	    //获取要下载文件的真实路径
	    String realPath = getServletContext().getRealPath("download/" + fileName);
	    //IO代码模板
	    FileInputStream fis = new FileInputStream(realPath);
	    OutputStream os = response.getOutputStream();
	    
	    byte[] buff = new byte[4096];
	    int len = 0;
	    while((len = fis.read(buff)) != -1) {
	        os.write(buff, 0, len);
	    }
	    fis.close();
	    //os是response管理，不用这里关闭
//	    os.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
