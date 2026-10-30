package com.pratikshat.exicom.repository;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.BtApproval;
@Repository
public interface BtApprovalRepository extends JpaRepository<BtApproval, Long>,BtApprovalRepositoryCustome {
	
	@Query(value="select * from bt_approval where id=:id",nativeQuery=true)
	Map<String,Object> getById(@Param("id")Long id);
	
	@Query(value="select * from bt_approval",nativeQuery = true,countQuery = "select count(*) as ct from bt_approval")
	Page<Map<String,Object>> getPage(Pageable page);
	
	
}
