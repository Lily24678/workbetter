package cn.itcast.bos.service.base.impl;

import java.util.List;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.base.CourierRepository;
import cn.itcast.bos.domain.base.Courier;
import cn.itcast.bos.service.base.CourierService;

@Service
@Transactional
public class CourierServceImpl implements CourierService {
	// 注入dao层
	@Autowired
	private CourierRepository courierRepository;

	@Override
	public Page<Courier> pageQuery(Specification<Courier> specification,
			PageRequest pageRequest) {
		return courierRepository.findAll(specification, pageRequest);
	}

	@Override
	@RequiresPermissions("courier:add")
	public void save(Courier model) {
		courierRepository.save(model);
	}

	@Override
	public List<Courier> findnoassociation() {
		Specification<Courier> specification = new Specification<Courier>() {

			@Override
			public Predicate toPredicate(Root<Courier> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate p1 = cb.isEmpty(root.get("fixedAreas").as(Set.class));
				
				return p1;
			}
		};
		return courierRepository.findAll(specification);
	}

}
