package cn.itcast.bos.service.take_delivery;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import cn.itcast.bos.domain.take_delivery.WayBill;

public interface WayBillService {

	public abstract void save(WayBill wayBill);

	public abstract Page<WayBill> pageQuery(Pageable pageable, WayBill wayBill);

	public abstract WayBill findByWayBillNum(String wayBillNum);

}
