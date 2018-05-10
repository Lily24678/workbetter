package cn.itcast.bos.service.transit.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.transit.InOutStorageInfoRepository;
import cn.itcast.bos.dao.transit.TransitInfoRepository;
import cn.itcast.bos.domain.transit.InOutStorageInfo;
import cn.itcast.bos.domain.transit.TransitInfo;
import cn.itcast.bos.service.transit.InOutStorageInfoService;
@Service
@Transactional
public class InOutStorageInfoServiceImpl implements InOutStorageInfoService {
	@Autowired
	private InOutStorageInfoRepository inOutStorageInfoRepository;
	@Autowired
	private TransitInfoRepository transitInfoRepository;

	@Override
	public void save(String transitInfoId, InOutStorageInfo inOutStorageInfo) {
		inOutStorageInfoRepository.save(inOutStorageInfo);
		TransitInfo transitInfo = transitInfoRepository.findOne(Integer.parseInt(transitInfoId));
	
			transitInfo.getInOutStorageInfos().add(inOutStorageInfo);
			if (inOutStorageInfo.getOperation().equals("到达网点")) {
				transitInfo.setStatus("到达网点");
				//更新网店地址
				transitInfo.setOutletAddress(inOutStorageInfo.getAddress());
			}
		}
	

}
