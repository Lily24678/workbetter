package cn.itcast.bos.service.transit;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import cn.itcast.bos.domain.transit.TransitInfo;

public interface TransitInfoService {

	public abstract void create(String wayBillIds);

	public abstract Page<TransitInfo> pageQuery(Pageable pageable);

}
