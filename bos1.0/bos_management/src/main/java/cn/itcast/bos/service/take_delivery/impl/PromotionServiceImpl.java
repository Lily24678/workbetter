package cn.itcast.bos.service.take_delivery.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.take_delivery.PromotionRepository;
import cn.itcast.bos.domain.page.PageBean;
import cn.itcast.bos.domain.take_delivery.Promotion;
import cn.itcast.bos.service.take_delivery.PromotionService;

@Service
@Transactional
public class PromotionServiceImpl implements PromotionService {
	// 注入持久层
	@Autowired
	private PromotionRepository promotionRepository;

	@Override
	public void save(Promotion promotion) {
		promotionRepository.save(promotion);
	}

	@Override
	public Page<Promotion> pageQuery(Pageable pageable) {
		return promotionRepository.findAll(pageable);
	}

	@Override
	public PageBean<Promotion> pageBean(int page, int rows) {
		Pageable pageable = new PageRequest(page-1, rows);
		Page<Promotion> page2 = promotionRepository.findAll(pageable);
		PageBean<Promotion> pageBean = new PageBean<Promotion>();
		pageBean.setTotalCount(page2.getTotalElements());
		pageBean.setPageData(page2.getContent());
		return pageBean;
	}

	@Override
	public Promotion findById(Integer id) {
		return promotionRepository.findOne(id);
	}

	@Override
	public void updateStatus(Date date) {
		promotionRepository.updateStatus(date);
	}

	
}
