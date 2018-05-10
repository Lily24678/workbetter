package cn.itcast.bos.web.action.system;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

import cn.itcast.bos.domain.system.User;
import cn.itcast.bos.service.system.UserService;
import cn.itcast.bos.web.action.common.BaseAction;
@Actions
@Controller
@Namespace("/")
@ParentPackage("json-default")
@Scope("prototype")
public class UserAction extends BaseAction<User> {
	@Autowired
	private UserService userService;
	
	//添加用户管理
	private String[] roleIds;
	
	public void setRoleIds(String[] roleIds) {
		this.roleIds = roleIds;
	}

	@Action(value="user_save",results={@Result(name="success",type="redirect",location="pages/system/userlist.html")})
	public String save(){
		userService.save(model,roleIds);
		return SUCCESS;
		
	}
	
	//查询所有用户
	@Action(value="user_list",results={@Result(name="success",type="json")})
	public String list(){
		List<User> users = userService.findAll();
		ActionContext.getContext().getValueStack().push(users);
		return SUCCESS;
	}
	
	//用户退出登陆
	@Action(value="user_loginout",results={@Result(name="success",type="redirect",location="login.html")})
	public String loginout(){
		//基于shiro完成退出
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		return SUCCESS;
	}
	
	//用户登陆
	@Action(value="user_login",results={@Result(name="success",type="redirect",location="index.html"),@Result(name="login",type="redirect",location="login.html")})
	public String login(){
		//用户名和密码都存储在model中
		//基于shiro 实现登陆
		Subject subject = SecurityUtils.getSubject();
		//用户名和密码
		UsernamePasswordToken token = new UsernamePasswordToken(model.getUsername(), model.getPassword());
		try {
			subject.login(token);
			//登陆成功
			return SUCCESS;
		} catch (AuthenticationException e) {
			e.printStackTrace();
			return LOGIN;
		}
		
		
	}
}
