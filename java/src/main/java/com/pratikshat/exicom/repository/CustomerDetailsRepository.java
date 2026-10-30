package com.pratikshat.exicom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.CustomerDeatils;
@Repository
public interface CustomerDetailsRepository extends JpaRepository<com.pratikshat.exicom.entity.CustomerDeatils, Long> {
	int countByCustomerId(long customerId);
	com.pratikshat.exicom.entity.CustomerDeatils findByCustomerId(long customerId);
	@Query("select new com.pratikshat.exicom.model.response.CustomerForList(c.customerId,c.customerName) from CustomerDeatils c")
	java.util.List<com.pratikshat.exicom.model.response.CustomerForList> getByAll();
	
	@Query("select new com.pratikshat.exicom.model.response.CustomerForList(c.customerId,c.customerName) from CustomerDeatils c where c.customerId=:id")
	com.pratikshat.exicom.model.response.CustomerForList getById(@Param("id")long id);
	
	
	@Query("select c from CustomerDeatils c where c.customerName=:customerName")
	CustomerDeatils getByName(@Param("customerName")String customerName);
}
