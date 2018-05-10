package cn.itcast.bos.service.take_delivery;

import java.util.Date;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import cn.itcast.bos.domain.page.PageBean;
import cn.itcast.bos.domain.take_delivery.Promotion;

public interface PromotionService {

	public abstract void save(Promotion promotion);

	public abstract Page<Promotion> pageQuery(Pageable pageable);

	@Path("/pageBean")
	@GET
	@Produces({"application/xml","application/json"})
	public abstract PageBean<Promotion> pageBean(@QueryParam("page")int page,@QueryParam("rows")int rows);
	
	
	//根据id查询
	@Path("promotion/findById/{id}")
	@GET
	@Produces({"application/xml","application/json"})
	public abstract Promotion findById(@PathParam("id")Integer id);

	public abstract void updateStatus(Date date);
	
}
