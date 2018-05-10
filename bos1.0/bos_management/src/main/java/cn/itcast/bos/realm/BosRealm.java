package cn.itcast.bos.realm;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.itcast.bos.domain.system.Permission;
import cn.itcast.bos.domain.system.Role;
import cn.itcast.bos.domain.system.User;
import cn.itcast.bos.service.system.PermissionService;
import cn.itcast.bos.service.system.RoleService;
import cn.itcast.bos.service.system.UserService;
//@Service("bosRealm")
public class BosRealm extends AuthorizingRealm {
	//注入userService
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private PermissionService permissionService;

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection paramPrincipalCollection) {
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		//根据当前登陆用户 查询角色和权限
		Subject subject = SecurityUtils.getSubject();
		User user = (User) subject.getPrincipal();
		List<Role> roles = roleService.findByUser(user);
		for (Role role : roles) {
			authorizationInfo.addRole(role.getKeyword());
		}
		List<Permission> permissions = permissionService.findByUser(user);
		for (Permission permission : permissions) {
			authorizationInfo.addStringPermission(permission.getKeyword());
		}
		return authorizationInfo;
	}

	@Override
	//认证...
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken paramAuthenticationToken)
			throws AuthenticationException {
		UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) paramAuthenticationToken;		
		User user = userService.findByUsername(usernamePasswordToken.getUsername());
		if(user == null){
			return null;
		}else {
			//参数1：期望在登陆后保存在Subject中的信息 		参数2：如果返回为null,则说明用户名不存在		参数3：realm的名称
			//当返回用户密码时，securityManager自动比较返回的密码与用户输入的密码是否一致，如果一致登陆成功，如果不一致包密码错误异常
			return new SimpleAuthenticationInfo(user, user.getPassword(), user.getUsername()); 
		}
		
	}

}
