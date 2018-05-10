package cn.itcast.crm.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.crm.dao.CustomerRepository;
import cn.itcast.crm.domain.Customer;
import cn.itcast.crm.service.CustomerService;
@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {
	// 注入dao
	@Autowired
	private CustomerRepository customerRepository;

	@Override
	public List<Customer> findCustomersNoAssociationFixedArea() {

		return customerRepository.findByfixedAreaIdIsNull();
	}

	@Override
	public List<Customer> findCustomersAssociatonFixedArea(String fixedAreaId) {
		return customerRepository.findByfixedAreaId(fixedAreaId);
	}

	@Override
	public void associatonCustomerstoFixedArea(String customerIdStr,
			String fixedAreaId) {
		customerRepository.clearFixedAreaId(fixedAreaId);
		if (StringUtils.isBlank(customerIdStr) || customerIdStr.equals("null")) {
			return;
		}
		//将用户id的字符串切割
		String[] customerids = customerIdStr.split(",");
		for (String idStr : customerids) {
			int id = Integer.parseInt(idStr);
			customerRepository.associatonCustomerstoFixedArea(fixedAreaId, id);
		}

	}

	@Override
	public void regist(Customer customer) {
		customerRepository.save(customer);
	}

	@Override
	public Customer findCustomerByTelephone(String telephone) {
		return customerRepository.findBytelephone(telephone);
	}

	@Override
	public void updateType(String telephone) {
		customerRepository.updateType(telephone);
	}

	@Override
	public Customer login(String telephone, String password) {
		return customerRepository.findByTelephoneAndPassword(telephone, password);
	}

	@Override
	public String fixedAreaIdByAddress(String address) {
		return customerRepository.fixedAreaIdByAddress(address);
	}

	

}
