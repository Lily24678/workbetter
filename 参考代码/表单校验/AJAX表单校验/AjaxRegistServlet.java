package web.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.User;
import service.UserService;

/**
 * Servlet implementation class AjaxRegistServlet
 */
public class AjaxRegistServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			//接收参数
			request.setCharacterEncoding("utf-8");
			String username = request.getParameter("username");
			System.out.println(username);
			//调用service层进行处理
			User user = new	UserService().findUser(username);
			response.setContentType("text/html;charset=utf-8");
			if(user == null){
				
				response.getWriter().println("可用");
			}else{
				response.getWriter().println("不可用");
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}

}
