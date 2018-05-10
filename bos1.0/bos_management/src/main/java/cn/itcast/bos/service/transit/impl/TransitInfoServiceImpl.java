package cn.itcast.bos.service.transit.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.take_delivery.WayBillRepository;
import cn.itcast.bos.dao.transit.TransitInfoRepository;
import cn.itcast.bos.domain.take_delivery.WayBill;
import cn.itcast.bos.domain.transit.TransitInfo;
import cn.itcast.bos.index.WayBillIndexRepository;
import cn.itcast.bos.service.take_delivery.WayBillService;
import cn.itcast.bos.service.transit.TransitInfoService;

@Service
@Transactional
public class TransitInfoServiceImpl implements TransitInfoService {
	@Autowired
	private TransitInfoRepository transitRepository;
	@Autowired
	private WayBillRepository wayBillRepository;
	@Autowired
	private WayBillIndexRepository wayBillIndexRepository;
	
	@Override
	public void create(String wayBillIds) {
		String[] ids = wayBillIds.split(",");
		for (String id : ids) {
			WayBill wayBill = wayBillRepository.findOne(Integer.parseInt(id));
			if (wayBill.getSignStatus() == 1) {// 签收状态 1 待发货、 2 派送中、3 已签收、4 异常
				// 待发货，开启中转配送
				TransitInfo transitInfo = new TransitInfo();
				transitInfo.setStatus("出入库中转");// 出入库中转、到达网点、开始配送、正常签收、异常
				transitInfo.setWayBill(wayBill);
				transitRepository.save(transitInfo);
				wayBill.setSignStatus(2);// 2 派送中
				wayBillIndexRepository.save(wayBill);
			}
		}
	}

	@Override
	public Page<TransitInfo> pageQuery(Pageable pageable) {
		return transitRepository.findAll(pageable);
	}

}
