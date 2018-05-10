package cn.itcast.bos.dao.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.base.StandardRepository;
import cn.itcast.bos.domain.base.Standard;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext.xml")
public class StandardRepositoryTest {

	@Autowired
	private StandardRepository stardandRepository;
	@Test
	public void testSave() {
		Standard standard = new Standard();
		standard.setName("6-10");
		stardandRepository.save(standard);
	}
	@Test
	@Transactional
	@Rollback(false)
	public void testDelete() {
		Standard standard = new Standard();
		standard.setId(7);
		standard.setName("6-10");
		stardandRepository.delete(8);;
	}

}
