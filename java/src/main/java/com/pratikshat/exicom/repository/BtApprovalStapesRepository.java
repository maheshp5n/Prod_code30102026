package com.pratikshat.exicom.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.BtApprovalStapes;

@Repository
public interface BtApprovalStapesRepository extends JpaRepository<BtApprovalStapes, Long>,BtApprovalStapesRepositoryCustome {
	
	
	@Query(value="select *  from `bt_approval_stapes` where id=:id",nativeQuery=true)
	public Map<String,Object> getById(@Param("id")Long id);
	
	@Query(value="select *  from `bt_approval_stapes`",nativeQuery=true,countQuery="select count(*) as ct from `bt_approval_stapes`")
	public Page<Map<String,Object>> getPage(Pageable page);
	
	@Query(value="select *  from `bt_approval_stapes` where approval_id=:approvalId",nativeQuery=true)
	public List<Map<String,Object>> getListByApprovalId(@Param("approvalId")Long approvalId);
	
	@Query(value="select * from `bt_approval_stapes` where approval_id=:approvalId and id=:approvalStapeId",nativeQuery = true)
	public Map<String,Object> getObjectByApprovalIdAndStapeId(@Param("approvalId")Long approvalId,@Param("approvalStapeId")Long approvalStapesId);
	
}
