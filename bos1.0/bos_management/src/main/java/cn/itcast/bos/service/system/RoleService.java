package cn.itcast.bos.service.system;

import java.util.List;

import cn.itcast.bos.domain.system.Role;
import cn.itcast.bos.domain.system.User;

public interface RoleService {

	public abstract List<Role> findAll();

	public abstract void save(Role role, String[] permissionIds, String menuIds);

	public abstract List<Role> findByUser(User user);

}
