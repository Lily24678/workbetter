package com.heima.filter;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.heima.domain.User;
import com.heima.service.UserService;
import com.itheima.utils.CookieUtils;

/**
 * Servlet Filter implementation class AutoLoginFilter
 */
public class AutoLoginFilter implements Filter {
	
	public void destroy() {
		System.out.println("我被销毁了");
	}


	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest hrep = (HttpServletRequest) request;
		HttpSession session = hrep.getSession();
		User exsitUser = (User) session.getAttribute("exsitUser");
		if(exsitUser != null){
			chain.doFilter(request, response);
		}else{
			Cookie[] cookies = hrep.getCookies();
			Cookie cookie = CookieUtils.findCookie(cookies, "usermessage");
			if(cookie == null){
				chain.doFilter(request, response);
			}else{
				String[] strs = cookie.getValue().split("-");
				User user = new User();
				user.setUsername(strs[0]);
				user.setPassword(strs[1]);
				User exsituser;
				try {
					exsituser = new UserService().findUser(user);
					if(exsituser==null){
						chain.doFilter(request, response);
					}else{
						session.setAttribute("exsitUser", exsituser);
						chain.doFilter(request, response);
					}
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
				
			}
			
		}
		
		
	}


	public void init(FilterConfig fConfig) throws ServletException {
		System.out.println("我创建啦");
	}

}
