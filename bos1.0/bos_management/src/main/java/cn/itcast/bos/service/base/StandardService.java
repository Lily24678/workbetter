package cn.itcast.bos.service.base;

import java.util.List;

import org.springframework.data.domain.Page;

import cn.itcast.bos.domain.base.Standard;

public interface StandardService {

	public abstract void save(Standard standard);

	public abstract Page<Standard> pageQuery(int page, int rows);

	public abstract List<Standard> findALL();



}
