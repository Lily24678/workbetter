package cn.itcast.bos.web.action.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.hibernate.boot.registry.StandardServiceInitiator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;

import cn.itcast.bos.domain.base.Standard;
import cn.itcast.bos.service.base.StandardService;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

@Controller
@ParentPackage("json-default")
@Namespace("/")
@Actions
public class StandardAction extends ActionSupport implements
		ModelDriven<Standard> {
	// 模型驱动接收参数
	private Standard standard = new Standard();

	@Override
	public Standard getModel() {
		return standard; 
	}

	// 业务层注入
	@Autowired
	private StandardService standardService;

	// 收派标准信息添加
	@Action(value = "standard_save", results = { @Result(name = "success", type = "redirect", location = "./pages/base/standard.html") })
	public String save() {
		standardService.save(standard);
		return SUCCESS;
	}

	// 分页查询收派标准信息
	private int page;
	private int rows;

	public void setPage(int page) {
		this.page = page;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	@Action(value = "standard_pageQuery",
			results = { @Result(name = "success", type = "json") })
	public String pageQuery() {
		Page<Standard> pageData = standardService.pageQuery(page, rows);
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("total", pageData.getTotalElements());
		map.put("rows", pageData.getContent());
		ActionContext.getContext().getValueStack().push(map);
		return SUCCESS;
	}
	
	//直接查询快递员
	@Action(value="standard_findAll",results={@Result(name="success",type="json")})
	public String findAll(){
		List<Standard> list = standardService.findALL();
		ActionContext.getContext().getValueStack().push(list);
		return SUCCESS;
	}
	
	
}
