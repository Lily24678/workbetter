package cn.itcast.bos.service.take_delivery.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

import javax.ws.rs.core.MediaType;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.base.AreaRepository;
import cn.itcast.bos.dao.base.FixedAreaRepository;
import cn.itcast.bos.dao.take_delivery.OrderRepository;
import cn.itcast.bos.dao.take_delivery.WorkBillRepository;
import cn.itcast.bos.domain.base.Area;
import cn.itcast.bos.domain.base.Courier;
import cn.itcast.bos.domain.base.FixedArea;
import cn.itcast.bos.domain.base.SubArea;
import cn.itcast.bos.domain.constant.Constant;
import cn.itcast.bos.domain.take_delivery.Order;
import cn.itcast.bos.domain.take_delivery.WayBill;
import cn.itcast.bos.domain.take_delivery.WorkBill;
import cn.itcast.bos.service.take_delivery.OrderService;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
	// 注入repository
	@Autowired
	private FixedAreaRepository fixedAreaRepository;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private AreaRepository areaRepository;

	@Autowired
	private WorkBillRepository workBillRepository;

	@Override
	public void save(Order order) {
		/*
		 * private String orderNum;// 订单号 private String status;// 订单状态 1 待取件 2
		 * 运输中 3 已签收 4 异常 private Date orderTime;// 下单时间 private Area sendArea;
		 * // 寄件人省市区信息 private Area recArea; // 收件人省市区信息
		 * 
		 * private String orderType;// 分单类型 1 自动分单 2 人工分单 private WayBill
		 * 
		 * wayBill;// 运单 private Set<WorkBill> workBills = new
		 * HashSet<WorkBill>(0);// 工单 private Courier courier;
		 */
		order.setOrderNum(UUID.randomUUID().toString());// 订单号
		order.setStatus("1");// 代取件
		order.setOrderTime(new Date());// 下单时间
		// 此时收件人所属区区域、寄件人所属区域都是瞬时态的
		Area sendArea = order.getSendArea();
		Area persistArea = areaRepository.findByProvinceAndCityAndDistrict(
				sendArea.getProvince(), sendArea.getCity(),
				sendArea.getDistrict());
		Area recArea = order.getRecArea();
		Area persisRecArea = areaRepository
				.findByProvinceAndCityAndDistrict(recArea.getProvince(),
						recArea.getCity(), recArea.getDistrict());
		order.setSendArea(persistArea);
		order.setRecArea(persisRecArea);

		// 第一种方式，根据详细地址去客户表中查客户关联的定区
		String fixedAreaId = WebClient
				.create(Constant.CRM_MANAGEMENT_URL
						+ "/services/customerService/customer/fixedAreaIdByAddress/"
						+ order.getSendAddress())
				.accept(MediaType.APPLICATION_JSON).get(String.class);
		if (fixedAreaId != null) {// 找到定区
			// 通过定区找到快递员,
			FixedArea fixedArea = fixedAreaRepository.findOne(fixedAreaId);
			Set<Courier> couriers = fixedArea.getCouriers();
			if (couriers != null) {
				Courier courier = couriers.iterator().next();
				// 自动分单成功，订单关联快递员,并保保存订单
				save(order, courier);

				// 生产工单，并发送短信
				saveWorkBillAndSms(order);

				return;
			}

		}

		// 第二种方式：根据分区的关键字、辅助关键关键字，查找定区，找到定区后找定区关联的快递员
		Set<SubArea> subareas = order.getSendArea().getSubareas();
		for (SubArea subArea : subareas) {
			if (order.getSendAddress().contains(subArea.getKeyWords()) || order.getSendAddress().contains(subArea.getAssistKeyWords()) ) {
				Set<Courier> couriers = subArea.getFixedArea().getCouriers();
				if (couriers != null) {
					Courier courier = couriers.iterator().next();
					// 自动分单成功，//自动分单成功，订单关联快递员,并保保存订单
					save(order, courier);
					// 生产工单，并发送短信

					return;
				}
			}
		}

		// 第三种方式：意思都不能分单成功
		order.setOrderType("2");// 人工分单
		orderRepository.save(order);

	}

	// 生产工单，并发送短信
	public void saveWorkBillAndSms(Order order) {
		WorkBill workBill = new WorkBill();
		/*
		 * private String type; // 工单类型 新,追,销.新单:没有确认货物状态的 已通知:自动下单下发短信
		 * 已确认:接到短信,回复收信确认信息 已取件:已经取件成功,发回确认信息 生成工作单已取消:销单 private String
		 * pickstate; // 取件状态 private Date buildtime; // 工单生成时间 private String
		 * remark; // 订单备注 private String smsNumber; // 短信序号 private Courier
		 * courier;// 快递员 private Order order; // 订单
		 */
		workBill.setType("新");
		workBill.setPickstate("新单");
		workBill.setBuildtime(new Date());
		workBill.setRemark(order.getRemark());
		workBill.setSmsNumber(RandomStringUtils.randomNumeric(4));
		workBill.setCourier(order.getCourier());
		workBill.setOrder(order);
		workBillRepository.save(workBill);
		// 短信发送
		
		
		
		
	}

	// 自动分单成功，订单关联快递员,并保保存订单
	public void save(Order order, Courier courier) {
		order.setOrderType("1");// 自动分单
		order.setCourier(courier);
		orderRepository.save(order);
	}

	@Override
	public Order findByOrderNum(String orderNum) {
		return orderRepository.findByOrderNum(orderNum);
	}

}
