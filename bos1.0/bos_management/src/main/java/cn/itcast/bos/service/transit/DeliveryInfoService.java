package cn.itcast.bos.service.transit;

import cn.itcast.bos.domain.transit.DeliveryInfo;

public interface DeliveryInfoService {

	public abstract void save(String transitInfoId, DeliveryInfo deliveryInfo);

}
