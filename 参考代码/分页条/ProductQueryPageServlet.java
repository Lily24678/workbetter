package web.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.PageBean;
import domain.Product;
import service.ProductService;

public class ProductQueryPageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			// 接收参数
			String currpage = request.getParameter("currPage");
			// 调用service层的方法queryPage(String currpage);
			ProductService productService = new ProductService();
			PageBean pageBean = productService.queryPage(currpage);
			// 页面跳转
			request.setAttribute("pageBean", pageBean);
			request.getRequestDispatcher("/jsp/product_page.jsp").forward(request, response);

		} catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException();
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
