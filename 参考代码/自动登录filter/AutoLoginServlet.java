package com.heima.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.heima.domain.User;
import com.heima.service.UserService;

public class AutoLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			request.setCharacterEncoding("utf-8");
			Map<String, String[]> map = request.getParameterMap();
			User user = new User();
			BeanUtils.populate(user, map);
			User exsitUser = new UserService().findUser(user);
			response.setContentType("text/html;charset=utf-8");
			if(exsitUser == null){
				request.setAttribute("meg", "用户名或者密码错误");
				request.getRequestDispatcher("/jsp/login.jsp").forward(request, response);
			}else{
				String parameter = request.getParameter("autologin");
				if("true".equals(parameter)){
					Cookie cookie = new Cookie("usermessage", exsitUser.getUsername()+"-"+exsitUser.getPassword());
					cookie.setMaxAge(60*60*24*7);
					cookie.setPath(request.getContextPath());
					response.addCookie(cookie);
				}
				request.getSession().setAttribute("exsitUser", exsitUser);
				response.sendRedirect(request.getContextPath() + "/jsp/index.jsp");
			}
		} catch (Exception e) {
			
			e.printStackTrace();
			throw new RuntimeException();
		} 
	}

}
