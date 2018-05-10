package cn.itcast.bos.service.base.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.hibernate.mapping.Array;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.base.CourierRepository;
import cn.itcast.bos.dao.base.FixedAreaRepository;
import cn.itcast.bos.dao.base.TakeTimeRepository;
import cn.itcast.bos.domain.base.Courier;
import cn.itcast.bos.domain.base.FixedArea;
import cn.itcast.bos.domain.base.TakeTime;
import cn.itcast.bos.service.base.FixedAreaService;
@Service
@Transactional
public class FixedAreaServiceImpl implements FixedAreaService {
	//注入dao层
	@Autowired
	private FixedAreaRepository fixedAreaRepository;
	@Autowired
	private CourierRepository courierRepository;
	@Autowired
	private TakeTimeRepository takeTimeRepository;
	//定区的添加功能
	@Override
	public void save(FixedArea fixedArea) {
		fixedAreaRepository.save(fixedArea);
	}

	@Override
	public Page<FixedArea> pageQuery(Specification<FixedArea> spec,
			PageRequest pageRequest) {
		return fixedAreaRepository.findAll(spec, pageRequest);
	}

	@Override
	public void associationCourierToFixedArea(FixedArea fixedArea,
			Integer courierId, Integer takeTimeId) {
		//通过id找到持久层的FixedArea、Courier、Take
		FixedArea fixedArea2 = fixedAreaRepository.findOne(fixedArea.getId());
		Courier courier = courierRepository.findOne(courierId);
		TakeTime takeTime = takeTimeRepository.findOne(takeTimeId);
		//FixedArea关联客户
		fixedArea2.getCouriers().add(courier);
		//Courier关联时间
		courier.setTakeTime(takeTime);
	}


	


	


}
