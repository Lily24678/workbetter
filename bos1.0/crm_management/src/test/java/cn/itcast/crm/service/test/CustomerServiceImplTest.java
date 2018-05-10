package cn.itcast.crm.service.test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.itcast.crm.domain.Customer;
import cn.itcast.crm.service.CustomerService;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext.xml")
public class CustomerServiceImplTest {
	//注入service层
	@Autowired
	public CustomerService customerService;

	@Test
	public void testFindCustomersNoAssociationFixedArea() {
		List<Customer> list = customerService.findCustomersNoAssociationFixedArea();	
		System.out.println(list);
	}

	@Test
	public void testFindCustomersAssociatonFixedArea() {
		List<Customer> list = customerService.findCustomersAssociatonFixedArea("pd001");
		System.out.println(list);
	}

	@Test
	public void testAssociatonCustomerstoFixedArea() {
		customerService.associatonCustomerstoFixedArea("1,2", "pd001");
	}

}
