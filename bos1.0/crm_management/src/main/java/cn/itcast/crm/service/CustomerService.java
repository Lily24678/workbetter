package cn.itcast.crm.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.validator.annotations.CustomValidator;

import cn.itcast.crm.domain.Customer;

public interface CustomerService {
	// 查询所有未联定区的客户
	@GET
	@Path("/noAssociationCustomer")
	@Produces({ "application/xml,application/json" })
	public List<Customer> findCustomersNoAssociationFixedArea();

	// 查询所有已关联某个定区的客户
	@GET
	@Path("/associationCustomer/{fixedAreaId}")
	@Produces({ "application/xml,application/json" })
	public List<Customer> findCustomersAssociatonFixedArea(
			@PathParam("fixedAreaId") String fixedAreaId);

	// 将客户关联到某个定区,将所有客户的id拼成字符串1，2，3
	@PUT
	@Path("/associatonCustomerstoFixedArea")
	@Consumes({ "application/xml,application/json" })
	public void associatonCustomerstoFixedArea(
			@QueryParam("customerIdStr") String customerIdStr,
			@QueryParam("fixedAreaId") String fixedAreaId);

	// 用户注册
	@POST
	@Path("/customer")
	@Consumes({ "application/xml,application/json" })
	public void regist(Customer customer);

	// 通过电话号码查找用户
	@Path("customer/telephone/{telephone}")
	@GET
	@Consumes({ "application/xml,application/json" })
	@Produces({ "application/xml,application/json" })
	public Customer findCustomerByTelephone(
			@PathParam("telephone") String telephone);

	// 将type设置为1
	@Path("/customer/updateType/{telephone}")
	@PUT
	@Consumes({ "application/xml,application/json" })
	public void updateType(@PathParam("telephone") String telephone);

	// 用户登陆
	@Path("/customer/login")
	@GET
	@Produces({ "application/xml,application/json" })
	public Customer login(@QueryParam("telephone") String telephone,
			@QueryParam("password") String password);
	
	//根据地址找定区编码fixedAreaId
	@Path("/customer/fixedAreaIdByAddress/{address}")
	@GET
	@Produces({ "application/xml,application/json" })
	public abstract String fixedAreaIdByAddress(@PathParam("address") String address);

}