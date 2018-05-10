package cn.itcast.bos.web.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.TimeUnit;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

import cn.itcast.bos.domain.constant.Constant;
import cn.itcast.bos.utils.MailUtils;
import cn.itcast.bos.utils.SmsUtils;
import cn.itcast.crm.domain.Customer;

@Controller
@Actions
@ParentPackage("struts-default")
@Namespace("/")
@Scope("prototype")
public class CustomerAction extends BaseAction<Customer> {
	@Autowired
	@Qualifier("jmsQueueTemplate")
	private JmsTemplate jmsTemplate;

	// 发送验证码
	@Action(value = "customer_sendSms")
	public String sendSms() throws Exception {
		// 生成随机验
		String random = RandomStringUtils.randomNumeric(2);
		ServletActionContext.getRequest().getSession()
				.setAttribute(model.getTelephone(), random);
		System.out.println(random);
		// 编辑网络信息
		final String msg = "尊敬的顾客您好，验证码是" + random;
		//调用MQ服务，发送一条消息
		jmsTemplate.send("bos_sms", new MessageCreator() {
			
			@Override
			public Message createMessage(Session session) throws JMSException {
				MapMessage mapMessage = session.createMapMessage();
				mapMessage.setString("telephone", model.getTelephone());
				mapMessage.setString("msg", msg);
				return mapMessage;
			}
		});
		return NONE;
		

	}

	/* 用户注册 */
	// 属性注入接收验证码
	private String checkCode;

	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}

	// 注入RedisTemplate

	private RedisTemplate<String, String> redisTemplate;

	public void setRedisTemplate(RedisTemplate<String, String> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	@Action(value = "customer_regist", results = {
			@Result(name = "success", type = "redirect", location = "signup-success.html"),
			@Result(name = "input", type = "redirect", location = "signup.html") })
	public String regist() {
		// 校验验证码
		String checkcodeSession = (String) ServletActionContext.getRequest()
				.getSession().getAttribute(model.getTelephone());
		if (checkcodeSession == null || !checkcodeSession.equals(checkCode)) {
			// 验证码不正确
			System.out.println("验证码错误");
			return INPUT;
		}

		// 将用户填的信息保存在CRM-Management数据库中
		WebClient
				.create("http://localhost:9002/crm_management/services/customerService/customer")
				.type(MediaType.APPLICATION_JSON).post(model);

		/* //邮件发送激活码 */
		String activeCode = RandomStringUtils.randomNumeric(6);

		/* //将激活码存入Redis数据库中、 */

		redisTemplate.opsForValue().set(model.getTelephone(), activeCode, 24,
				TimeUnit.HOURS);

		String contend = "<h1>尊敬的客户您好，请于24小时内点击下面的连接完成绑定：<br/><a href='"
				+ MailUtils.activeUrl + "?telephone=" + model.getTelephone()
				+ "&activeCode=" + activeCode + " '>速运快递用户激活</a></h1>";
		MailUtils.sendMail("bos用户注册激活码", contend, model.getEmail());

		return SUCCESS;
	}

	// 邮件激活
	private String activeCode;

	public void setActiveCode(String activeCode) {
		this.activeCode = activeCode;
	}

	@Action(value = "customer_activeMail")
	public String activeMail() throws IOException {
		ServletActionContext.getResponse().setCharacterEncoding("utf-8");
		String activeCodeRedis = redisTemplate.opsForValue().get(
				model.getTelephone());
		if (activeCodeRedis == null || !activeCodeRedis.equals(activeCode)) {
			// 邮件未激活
			ServletActionContext.getResponse().getWriter()
					.println("激活码已经过期，请登陆系统重新绑定邮箱");
		} else {
			// 查询顾客系统，防止电话重复绑定
			Customer customer = WebClient
					.create("http://localhost:9002/crm_management/services/customerService/customer/telephone/"
							+ model.getTelephone())
					.accept(MediaType.APPLICATION_JSON).get(Customer.class);
			if (customer.getType() == null || customer.getType() != 1) {
				// 没有激活过
				WebClient
						.create("http://localhost:9002/crm_management/services/customerService/customer/updateType/"
								+ model.getTelephone())
						.type(MediaType.APPLICATION_JSON).put(null);
				ServletActionContext.getResponse().getWriter()
						.println("邮箱绑定成功");
			} else {
				ServletActionContext.getResponse().getWriter()
						.println("该邮箱已经绑定，无须重复绑定");
			}

			redisTemplate.delete(model.getTelephone());

		}

		return NONE;
	}

	// 用户登陆
	@Action(value = "customer_login", results = {
			@Result(name = "success", type = "redirect", location = "index.html#/myhome"),
			@Result(name = "login", type = "redirect", location = "login.html") })
	public String login() {
		Customer customer = WebClient
				.create(Constant.CRM_MANAGEMENT_URL
						+ "/services/customerService/customer/login?telephone="
						+ model.getTelephone() + "&password="
						+ model.getPassword())
				.accept(MediaType.APPLICATION_JSON).get(Customer.class);
		if (customer == null) {
			// 登陆失败
			return LOGIN;
		}

		ServletActionContext.getRequest().getSession()
				.setAttribute("customer", customer);
		return SUCCESS;
	}
}
