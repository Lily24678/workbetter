package cn.itcast.bos.service.base;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.domain.base.Area;


public interface AreaService {

	public abstract void save(List<Area> list);

	public abstract Page<Area> pageQuery(Pageable pageable, Specification<Area> spec);

	public abstract Area findOne(Area area);

}
