package web.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Word;
import service.UserService;


public class BaiduSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//接收参数
		request.setCharacterEncoding("utf-8");
		String word = request.getParameter("word");
		//调用业务层
		UserService userService = new UserService();		
		try {
			List<Word> 	list = userService.findWords(word);
			request.setAttribute("list", list);
			request.getRequestDispatcher("/jq-Ajax_baiduSearch/info.jsp").forward(request, response);
		} catch (SQLException e) {
			
			e.printStackTrace();
			throw new RuntimeException();
		}
		
		
	}

}
