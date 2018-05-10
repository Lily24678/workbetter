package cn.itcast.bos.web.action.system;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

import cn.itcast.bos.domain.system.Menu;
import cn.itcast.bos.domain.system.User;
import cn.itcast.bos.service.system.MenuService;
import cn.itcast.bos.web.action.common.BaseAction;

@Actions
@Controller
@Namespace("/")
@ParentPackage("json-default")
@Scope("prototype")
public class MenuAction extends BaseAction<Menu> {
	@Autowired
	private MenuService menuService;
	
	//根据不同用户显示不同菜单
	@Action(value="menu_showMenu",results={@Result(name="success",type="json")})
	public String showMenu(){
		Subject subject = SecurityUtils.getSubject();
		User user = (User) subject.getPrincipal(); 
		List<Menu> menus = menuService.findByUser(user);
		ActionContext.getContext().getValueStack().push(menus);
		return SUCCESS;
	}
	
	//添加方法
	@Action(value="menu_save",results={@Result(name="success",type="redirect",location="pages/system/menu.html")})
	public String save(){
		menuService.save(model);
		return SUCCESS;
	}
	
	
	// 查询所有menu的数据
	@Action(value = "menu_findAll", results = { @Result(name = "success", type = "json") })
	public String findAll() {
		List<Menu> menus = menuService.findALL();
		ActionContext.getContext().getValueStack().push(menus);
		return SUCCESS;
	}
}
