package cn.itcast.bos.service.system.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.system.MenuRepository;
import cn.itcast.bos.dao.system.PermissionRepository;
import cn.itcast.bos.dao.system.RoleRepository;
import cn.itcast.bos.domain.system.Menu;
import cn.itcast.bos.domain.system.Permission;
import cn.itcast.bos.domain.system.Role;
import cn.itcast.bos.domain.system.User;
import cn.itcast.bos.service.system.RoleService;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private PermissionRepository permissionRepository;
	@Autowired
	private MenuRepository menuRepository;

	@Override
	public List<Role> findAll() {
		return roleRepository.findAll();
	}

	@Override
	public void save(Role role, String[] permissionIds, String menuIds) {
		roleRepository.save(role);
		if (permissionIds != null) {
			for (String permissionId : permissionIds) {
				Permission permission = permissionRepository.findOne(Integer
						.parseInt(permissionId));
				role.getPermissions().add(permission);
			}
		}

		if (StringUtils.isNotBlank(menuIds)) {
			String[] split = menuIds.split(",");
			for (String menuId : split) {
				Menu menu = menuRepository.findOne(Integer.parseInt(menuId));
				role.getMenus().add(menu);
			}
		}

	}

	@Override
	public List<Role> findByUser(User user) {
		if (user.getUsername().equals("admin")) {
			return roleRepository.findAll();
		}else {
			return roleRepository.findByUser(user.getId());
		}
		
	}

}
