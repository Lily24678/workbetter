package cn.itcast.bos.service.system;

import java.util.List;

import cn.itcast.bos.domain.system.Menu;
import cn.itcast.bos.domain.system.User;

public interface MenuService {

	public abstract List<Menu> findALL();

	public abstract void save(Menu menu);

	public abstract List<Menu> findByUser(User user);
	

}
