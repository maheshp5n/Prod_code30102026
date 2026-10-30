package com.pratikshat.exicom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.BusinessDomain;

@Repository
public interface BusinessDomainRepository extends JpaRepository<BusinessDomain, Integer>{
		
	@Query(value ="SELECT * from business_domain where business_domain_id=:id",nativeQuery = true)
	BusinessDomain getByDomainId(@Param("id")int id);
	
	List<BusinessDomain> findAll();
	
	@Query("select bs from BusinessDomain bs where bs.businessDomainId=:id")
	BusinessDomain getDomainWithId(int id);
	
}
