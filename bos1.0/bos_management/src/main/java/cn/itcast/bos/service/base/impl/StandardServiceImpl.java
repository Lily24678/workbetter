package cn.itcast.bos.service.base.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.base.StandardRepository;
import cn.itcast.bos.domain.base.Standard;
import cn.itcast.bos.service.base.StandardService;

@Service
@Transactional
public class StandardServiceImpl implements StandardService {
	// 注入dao层、
	@Autowired
	private StandardRepository standardRepository;

	// 添加信息
	@Override
	@CacheEvict(value="standard",allEntries=true)//将standard缓存清空
	public void save(Standard standard) {
		standardRepository.save(standard);
	}

	// 分页查询
	@Override
	@Cacheable(value="standard",key="#page+'-'+#rows")//SpEL
	public Page<Standard> pageQuery(int page, int rows) {
		Pageable pageable = new PageRequest(page - 1, rows);
		return standardRepository.findAll(pageable);

	}

	@Override
	@Cacheable("standard")//自定义缓存区
	public List<Standard> findALL() {
		return standardRepository.findAll();
	}

}
