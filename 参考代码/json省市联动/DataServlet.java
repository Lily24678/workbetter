package web.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.City;
import domain.Province;
import net.sf.json.JSONArray;
import service.DataService;

/**
 * Servlet implementation class DataServlet
 */
public class DataServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//接收参数
		String method = request.getParameter("method");
		if("queryProvince".equals(method)){
			queryProvince(request, response);
		}else if("queryCity".equals(method)){
			queryCityByPid(request, response);
		}
	}


	public void queryCityByPid(HttpServletRequest request, HttpServletResponse response) {
		try {
			String pid = request.getParameter("pid");
			List<City> list = new DataService().queryCityByPid(pid);
			String jsondata = JSONArray.fromObject(list).toString();
			System.out.println(jsondata);
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().println(jsondata);
		} catch (Exception e) {
			
			e.printStackTrace();
			throw new RuntimeException();
		}

		
	}


	public void queryProvince(HttpServletRequest request, HttpServletResponse response){
		
		try {
			List<Province> list = new DataService().queryProvince();
			String jsondata = JSONArray.fromObject(list).toString();
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().println(jsondata);
		} catch (Exception e) {
			
			e.printStackTrace();
			throw new RuntimeException();
		}

	}


	


	

}
