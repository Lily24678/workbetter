package dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import domain.PageBean;
import domain.Product;
import utils.JdbcUtils;

public class ProductDao {
	private QueryRunner queryRunner = new QueryRunner(JdbcUtils.getDataSource());

	// 查询总记录数
	public int queryCount() throws SQLException {
		String sql = "SELECT COUNT(*) FROM product;";
		Long totalCount = (Long) queryRunner.query(sql, new ScalarHandler<>());
		return totalCount.intValue();
	}

	// 分页查询,该页面的记录数
	public List<Product> queryLimit(int begin, int count) throws SQLException {
		String sql = "SELECT * FROM product ORDER BY pdate DESC LIMIT ?,?;";
		List<Product> list = queryRunner.query(sql, new BeanListHandler<Product>(Product.class), begin, count);
		return list;
	}

}
