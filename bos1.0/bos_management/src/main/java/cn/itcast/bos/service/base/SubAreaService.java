package cn.itcast.bos.service.base;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import cn.itcast.bos.domain.base.SubArea;


public interface SubAreaService {

	public abstract void save(List<SubArea> list);

	public abstract Page<SubArea> findAll(Pageable pageable);

	
}
