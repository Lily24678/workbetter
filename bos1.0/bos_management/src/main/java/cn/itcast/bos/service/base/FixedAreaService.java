package cn.itcast.bos.service.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import cn.itcast.bos.domain.base.FixedArea;

public interface FixedAreaService {

	public abstract void save(FixedArea fixedArea);

	public abstract Page<FixedArea> pageQuery(Specification<FixedArea> spec,
			PageRequest pageRequest);

	public abstract void associationCourierToFixedArea(FixedArea fixedArea,
			Integer courierId, Integer takeTimeId);

	

	
}
