package cn.itcast.bos.service.system;

import java.util.List;

import cn.itcast.bos.domain.system.Permission;
import cn.itcast.bos.domain.system.User;

public interface PermissionService {

	public abstract List<Permission> findAll();

	public abstract void save(Permission permission);

	public abstract List<Permission> findByUser(User user);

}
