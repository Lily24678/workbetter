package cn.itcast.bos.web.action;

import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.bos.domain.base.Area;
import cn.itcast.bos.domain.constant.Constant;
import cn.itcast.bos.domain.take_delivery.Order;

@Controller
@Actions
@ParentPackage("json-default")
@Namespace("/")
@Scope("prototype")
public class OrderAction extends BaseAction<Order> {
	// 属性注入，进行数据封装
	private String sendAreaInfo;// 寄件人省市区（,页面提示不可为空）
	private String recAreaInfo;// 收件人省市区（,页面提示不可为空）

	public void setSendAreaInfo(String sendAreaInfo) {
		this.sendAreaInfo = sendAreaInfo;
	}

	public void setRecAreaInfo(String recAreaInfo) {
		this.recAreaInfo = recAreaInfo;
	}

	/* 订单的生成,订单生成成功后跳转到主页面 */
	@Action(value = "order_add", results = { @Result(name = "success", type = "redirect", location = "#/") })
	public String add() {
		//对寄件人所属区域进行封装
		Area sendArea = new Area();
		String[] sends = sendAreaInfo.split("/");
		sendArea.setProvince(sends[0]);
		sendArea.setCity(sends[1]);
		sendArea.setDistrict(sends[2]);
		
		//对收件人所属区域进行封装
		Area recArea = new Area();
		String[] recs = recAreaInfo.split("/");
		recArea.setProvince(recs[0]);
		recArea.setCity(recs[1]);
		recArea.setDistrict(recs[2]);
		
		//对客户输入的订单信息进行封装
		model.setSendArea(sendArea);
		model.setRecArea(recArea);
		
		// 调用后台管理服务（访问不同服务器WebService），将order保存到数据库中
		WebClient.create(Constant.BOS_MANAGEMENT_URL+"/services/orderService/order").type(MediaType.APPLICATION_JSON).post(model);
		return SUCCESS;
	}
}
