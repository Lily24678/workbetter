package cn.itcast.bos.web.action.base;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

import cn.itcast.bos.domain.base.Courier;
import cn.itcast.bos.domain.base.Standard;
import cn.itcast.bos.service.base.CourierService;
import cn.itcast.bos.web.action.common.BaseAction;

@Controller
@Actions
@ParentPackage("json-default")
@Namespace("/")
@Scope("prototype")
public class CourierAction extends BaseAction<Courier> {
	//注入业务层
	@Autowired
	private CourierService courierService;
	//分页查询
	@Action(value="courier_pageQuery",results={@Result(name="success",type="json")})
	public String pageQuery(){
		//封装page、rows
		PageRequest pageRequest = new PageRequest(page-1, rows);
		//封装条件对象Predicate
		Specification<Courier> specification = new Specification<Courier>() {			
			@Override
			public Predicate toPredicate(Root<Courier> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<Predicate>();
				if (StringUtils.isNotBlank(model.getCourierNum())) {
					Predicate p1 = cb.equal(root.get("courierNum"), model.getCourierNum());
					list.add(p1);
				}
				Join<Courier, Standard> standardRoot = root.join("standard",JoinType.INNER);
				if (model.getStandard() !=null && StringUtils.isNotBlank(model.getStandard().getName())) {
					Predicate p2 = cb.like(standardRoot.get("name").as(String.class), "%" + model.getStandard().getName() + "%");
					list.add(p2);
				}
				if (StringUtils.isNotBlank(model.getCompany())) {
					Predicate p3 = cb.like(root.get("company").as(String.class), "%"+model.getCompany()+"%");
					list.add(p3);
				}
				if (StringUtils.isNotBlank(model.getType())) {
					Predicate p4 = cb.equal(root.get("type"), model.getType());
					list.add(p4);
				}
				return cb.and(list.toArray(new Predicate[0]));
			}
		};
		//调用业务层
		Page<Courier> pagedata =  courierService.pageQuery(specification,pageRequest);
		pushPageDataToValueStack(pagedata);
		return SUCCESS;
	}
	
	//添加业务员
	@Action(value="courier_save",results={@Result(name="success",type="redirect",location="./pages/base/courier.html")})
	public String save(){
		courierService.save(model);
		return SUCCESS;
	}
	
	//查询未与定区关联的业务员
	@Action(value="courier_findnoassociation",results={@Result(name="success",type="json")})
	public String findnoassociation(){
	    List<Courier> list = courierService.findnoassociation();
	    ActionContext.getContext().getValueStack().push(list);
		return SUCCESS;
		
	}
	
}
