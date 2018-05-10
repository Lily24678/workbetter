package web.servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;

import domain.User;
import service.UserService;

/**
 *JQ AJAX注册用户名提示
 */
public class JQAjaxRegistServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;      

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  	
		try {
			//接收参数
	    	String username = new String(request.getParameter("username").getBytes("iso-8859-1"), "utf-8");
    		//调用业务层进行数据处理
			UserService userService = new UserService();
			User exsitUser = userService.findUser(username);
			if(exsitUser != null){
				response.getWriter().println(1);
			}else{
				response.getWriter().println(0);
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			throw new RuntimeException();
		}
 
 
	}

@Test
	private void jqAjax_load(HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException, IOException {
		try {
    		//接收参数
        	String username = new String(request.getParameter("username").getBytes("iso-8859-1"), "utf-8");
    	
//    		String username = request.getParameter("username");
    		//调用业务层进行数据处理
        	UserService userService = new UserService();
			User exsitUser = userService.findUser(username);
			response.setCharacterEncoding("utf-8");
		   	//根据业务处理的结果做出不同的响应
			if(exsitUser != null){//说明用户名已经存在
				
				response.getWriter().println("<font color='red'>用户名已经存在</font>");
			}else{
				response.getWriter().println("<font color='green'>用户名可以用存在</font>");
			}
			System.out.println("输入的用户名是:    " + username);
		} catch (SQLException e) {
			
			e.printStackTrace();
			throw new RuntimeException();
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
