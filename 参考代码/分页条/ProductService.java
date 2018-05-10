package service;

import java.sql.SQLException;
import java.util.List;

import dao.ProductDao;
import domain.PageBean;
import domain.Product;

public class ProductService {
	private ProductDao productDao = new ProductDao();

	// 设置带回到页面的所有信息
	public PageBean queryPage(String currpage) throws SQLException {
		PageBean pageBean = new PageBean();
		// 设置当前页
		pageBean.setCurrPage(Integer.parseInt(currpage));
		// 设置分页条的长度
		pageBean.setSize(10);
		// 设置总记录数
		int totalCount = productDao.queryCount();
		pageBean.setTotalCount(totalCount);
		// 设置总页数
		int totalPage;
		int i = totalCount % pageBean.getSize();
		if (i == 0) {
			totalPage = totalCount / pageBean.getSize();
		} else {
			totalPage = totalCount / pageBean.getSize(); + 1;
		}
		pageBean.setTotalPage(totalPage);
		// 设置当前页显示的记录
		int begin = (Integer.parseInt(currpage) - 1) * pageBean.getSize();
		int count = pageBean.getSize();
		List<Product> list = productDao.queryLimit(begin, count);
		pageBean.setList(list);

		return pageBean;
	}

}
