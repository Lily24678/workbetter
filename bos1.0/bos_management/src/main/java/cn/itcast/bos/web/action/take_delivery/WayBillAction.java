package cn.itcast.bos.web.action.take_delivery;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.sun.istack.logging.Logger;

import cn.itcast.bos.domain.take_delivery.WayBill;
import cn.itcast.bos.service.take_delivery.WayBillService;
import cn.itcast.bos.web.action.common.BaseAction;

@Controller
@Actions
@Namespace("/")
@ParentPackage("json-default")
@Scope("prototype")
public class WayBillAction extends BaseAction<WayBill> {
	private static final Logger LOGGER = Logger.getLogger(WayBill.class);
	@Autowired
	private WayBillService wayBillService;
	
	
	
	
	
	//根据运单编号查询运单
	@Action(value="wayBill_findByWayBillNum",results={@Result(name="success",type="json")})
	public String findByWayBillNum(){
		WayBill wayBill = wayBillService.findByWayBillNum(model.getWayBillNum());
		Map<String, Object> result = new HashMap<String, Object>();
		if (wayBill==null) {
			result.put("success", false);
		}else {
			result.put("success", true);
			result.put("wayBillData", wayBill);
			ActionContext.getContext().getValueStack().push(result);
		}
		return SUCCESS;
	}

	

	@Action(value = "wayBill_save", results = { @Result(name = "success", type = "json") })
	public String save() {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			//去除没有id的Order
			if (model.getOrder() != null && (model.getOrder().getId()==null || model.getOrder().getId() == 0 )) {
				model.setOrder(null);
			}
			wayBillService.save(model);
			
			// 保存成功
			result.put("success", true);
			result.put("msg", "运单保存成功");
			LOGGER.info("保存运单成功，运单号： " + model.getWayBillNum());
		} catch (Exception e) {
			e.printStackTrace();
			// 保存失败
			result.put("success", false);
			result.put("msg", "运单保存失败");
			LOGGER.info("保存运单失败，运单号： " + model.getWayBillNum());
		}
		ActionContext.getContext().getValueStack().push(result);
		return SUCCESS;

	}

	// 分页查询
	@Action(value = "wayBill_pageQuery", results = { @Result(name = "success", type = "json") })
	public String pageQuery() {
		Pageable pageable = new PageRequest(page-1, rows, new Sort(
				new Sort.Order(Sort.Direction.DESC, "id")));
		Page<WayBill> pageData = wayBillService.pageQuery(pageable,model);
		pushPageDataToValueStack(pageData);
		return SUCCESS;
	}

}
