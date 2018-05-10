package cn.itcast.bos.service.base.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.base.SubAreaRepository;
import cn.itcast.bos.domain.base.SubArea;
import cn.itcast.bos.service.base.SubAreaService;
@Service
@Transactional
public class SubAreaServiceImpl implements SubAreaService {
	//持久层注入
	@Autowired
	private SubAreaRepository subAreaRepository;

	@Override
	public void save(List<SubArea> list) {
		subAreaRepository.save(list);
	}

	@Override
	public Page<SubArea> findAll(Pageable pageable) {
		return subAreaRepository.findAll(pageable);
	}

	



}
