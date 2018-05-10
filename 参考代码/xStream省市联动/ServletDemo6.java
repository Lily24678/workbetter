package com.itheima.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.domain.City;
import com.itheima.service.ProvinceService;
import com.thoughtworks.xstream.XStream;

/**
 * 根据省份的ID查询市的信息的Servlet
 */
public class ServletDemo6 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			// 接收数据:
			String pid = request.getParameter("pid");
			// 调用业务层查询所有的市的信息:
			ProvinceService provinceService = new ProvinceService();
			List<City> list = provinceService.findByPid(pid);
			// 将List集合生成XML:
			
			XStream xStream = new XStream();
			
			// 修改标签名:
			xStream.alias("city", City.class);
			// 将类中属性作为 标签的属性
			xStream.useAttributeFor(City.class, "cid");
			xStream.useAttributeFor(City.class,"cname");
			xStream.useAttributeFor(City.class,"pid");
			
			String xmlStr = xStream.toXML(list);
			
			// System.out.println(xmlStr);
			response.setContentType("text/xml;charset=UTF-8");
			response.getWriter().println(xmlStr);
			/**
			 * <list>
			 * 	<city>
			 * 		<cid>1</cid>
			 *      <cname>哈尔滨</cname>
			 *      <pid>4</pid>
			 *  </city>
			 *  <city>
			 * 		<cid>2</cid>
			 *      <cname>齐齐哈尔</cname>
			 *      <pid>4</pid>
			 *  </city>
			 * </list>
			 */
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
