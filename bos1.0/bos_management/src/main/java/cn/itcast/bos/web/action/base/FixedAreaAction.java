package cn.itcast.bos.web.action.base;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.dom4j.CDATA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import cn.itcast.bos.domain.base.FixedArea;
import cn.itcast.bos.domain.base.SubArea;
import cn.itcast.bos.service.base.FixedAreaService;
import cn.itcast.crm.domain.Customer;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.util.ResolverUtil.NameEndsWith;

@Controller
@ParentPackage("json-default")
@Namespace("/")
@Actions
@Scope("prototype")
public class FixedAreaAction extends ActionSupport implements
		ModelDriven<FixedArea> {
	// 模型驱动，接收参数
	private FixedArea fixedArea = new FixedArea();

	@Override
	public FixedArea getModel() {
		return fixedArea;
	}

	// 注入业务层
	@Autowired
	private FixedAreaService fixedAreaService;

	/*
	 * 添加定区信息的方法
	 */
	@Action(value = "fixedArea_save", results = { @Result(name = "success", type = "redirect", location = "./pages/base/fixed_area.html") })
	public String save() {
		// 调用业务层
		fixedAreaService.save(fixedArea);
		return SUCCESS;

	}

	/*
	 * 条件分页显示定区信息
	 */
	private int page;
	private int rows;

	public void setPage(int page) {
		this.page = page;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	@Action(value = "fixedArea_pageQuery", results = { @Result(name = "success", type = "json") })
	public String pageQuery() {

		Specification<FixedArea> spec = new Specification<FixedArea>() {

			@Override
			public Predicate toPredicate(Root<FixedArea> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<Predicate>();
				if (StringUtils.isNotBlank(fixedArea.getId())) {
					Predicate p1 = cb.equal(root.get("id").as(String.class),
							fixedArea.getId());
					list.add(p1);
				}
				if (StringUtils.isNotBlank(fixedArea.getCompany())) {
					Predicate p2 = cb.like(
							root.get("company").as(String.class), "%"
									+ fixedArea.getCompany() + "%");
					list.add(p2);
				}

				return cb.and(list.toArray(new Predicate[0]));
			}
		};
		PageRequest pageRequest = new PageRequest(page - 1, rows);
		Page<FixedArea> pagedata = fixedAreaService
				.pageQuery(spec, pageRequest);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", pagedata.getTotalElements());
		map.put("rows", pagedata.getContent());
		ActionContext.getContext().getValueStack().push(map);
		return SUCCESS;
	}

	// 查询未关联到的客户
	@Action(value = "fixedArea_findCustomersNoAssociationFixedArea", results = { @Result(name = "success", type = "json") })
	public String findCustomersNoAssociationFixedArea() {
		Collection<? extends Customer> cutomerCollection = WebClient
				.create("http://localhost:9002/crm_management/services/customerService/noAssociationCustomer")
				.accept(MediaType.APPLICATION_JSON)
				.getCollection(Customer.class);
		ActionContext.getContext().getValueStack().push(cutomerCollection);
		return SUCCESS;
	}

	// 查询已经关联的客户
	@Action(value = "fixedArea_findCustomersAssociatonFixedArea", results = { @Result(name = "success", type = "json") })
	public String findCustomersAssociatonFixedArea() {
		Collection<? extends Customer> cutomerCollection = WebClient
				.create("http://localhost:9002/crm_management/services/customerService/associationCustomer/"
						+ fixedArea.getId()).accept(MediaType.APPLICATION_JSON)
				.getCollection(Customer.class);
		ActionContext.getContext().getValueStack().push(cutomerCollection);
		return SUCCESS;
	}

	// 将客户关联到指定的定区
	private String[] customerIds;

	public void setCustomerIds(String[] customerIds) {
		this.customerIds = customerIds;
	}

	@Action(value = "fixedArea_associatonCustomerstoFixedArea", results = { @Result(name = "success", type = "redirect", location = "./pages/base/fixed_area.html") })
	public String associatonCustomerstoFixedArea() {
		// 将客户的id使用“,”拼接
		String customerIdStr = StringUtils.join(customerIds, ",");
		WebClient
				.create("http://localhost:9002/crm_management/services/customerService/associatonCustomerstoFixedArea?customerIdStr="
						+ customerIdStr + "&fixedAreaId=" + fixedArea.getId())
				.type(MediaType.APPLICATION_JSON).put(null);
		return SUCCESS;
	}

	// 关联快递员
	private Integer courierId;
	private Integer takeTimeId;

	public void setCourierId(Integer courierId) {
		this.courierId = courierId;
	}

	public void setTakeTimeId(Integer takeTimeId) {
		this.takeTimeId = takeTimeId;
	}

	@Action(value = "fixedArea_associationCourierToFixedArea", results = { @Result(name = "success", type = "redirect", location = "./pages/base/fixed_area.html") })
	public String associationCourierToFixedArea() {
		fixedAreaService.associationCourierToFixedArea(fixedArea,courierId,takeTimeId);
		return SUCCESS;
	}
}
