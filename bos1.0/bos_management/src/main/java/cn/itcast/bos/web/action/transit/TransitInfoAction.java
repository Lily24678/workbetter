package cn.itcast.bos.web.action.transit;

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
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

import cn.itcast.bos.domain.transit.TransitInfo;
import cn.itcast.bos.service.transit.TransitInfoService;
import cn.itcast.bos.web.action.common.BaseAction;

@Controller
@Actions
@Namespace("/")
@ParentPackage("json-default")
@Scope("prototype")
public class TransitInfoAction extends BaseAction<TransitInfo> {
	@Autowired
	private TransitInfoService transitInfoService;
	//分页查询
	@Action(value="transitInfo_pageQuery",results={@Result(name="success",type="json")})
	public String pageQuery(){
		//封装参数
		Pageable pageable = new PageRequest(page-1, rows);
		Page<TransitInfo> pageData = transitInfoService.pageQuery(pageable);
		pushPageDataToValueStack(pageData);
		return SUCCESS;
	}
	
	
	
	
	
	// 开启中转配送
	private String wayBillIds;

	public void setWayBillIds(String wayBillIds) {
		this.wayBillIds = wayBillIds;
	}

	@Action(value = "transit_create", results = { @Result(name = "success", type = "json") })
	public String create() {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			transitInfoService.create(wayBillIds);// 开启中转配送
			result.put("success", true);
			result.put("msg", "开启中转配送成功");
		} catch (Exception e) {
			result.put("success", false);
			result.put("msg", "开启中转配送失败:" + e.getMessage());
		}
		ActionContext.getContext().getValueStack().push(result);
		return SUCCESS;
	}

}
