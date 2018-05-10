package cn.itcast.crm.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import cn.itcast.crm.domain.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
	public abstract List<Customer> findByfixedAreaIdIsNull();

	public abstract List<Customer> findByfixedAreaId(String fixedAreaId);

	@Query("update Customer set fixedAreaId=? where id=? ")
	@Modifying
	public abstract void associatonCustomerstoFixedArea(String fixedAreaId,
			Integer id);

	@Query("update Customer set fixedAreaId=null where fixedAreaId=? ")
	@Modifying
	public abstract void clearFixedAreaId(String fixedAreaId);

	public abstract Customer findBytelephone(String telephone);

	@Query("update Customer set type=1 where telephone=?")
	@Modifying
	public abstract void updateType(String telephone);
	
	public Customer findByTelephoneAndPassword(String telephone,String password);
	
	@Query("select fixedAreaId from Customer where address=?")
	public abstract String fixedAreaIdByAddress(String address);
}
