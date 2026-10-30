package com.pratikshat.exicom.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pratikshat.exicom.entity.Approval;

@Repository
public interface ApprovalRepository extends JpaRepository<Approval,Long>, ApprovalRepositoryCustomeRepository{
	@Transactional(readOnly = true)
	@Query("select new com.pratikshat.exicom.model.response.ApprovalResponse(a) from Approval a left join a.businessDomain bd")
	Page<com.pratikshat.exicom.model.response.ApprovalResponse> getAll(Pageable page);
	
	@Transactional(readOnly = true)
	@Query("select new com.pratikshat.exicom.model.response.ApprovalResponse(a) from Approval a left join a.businessDomain bd where a.id=:approvalId")
	com.pratikshat.exicom.model.response.ApprovalResponse getByApprovalId(@Param("approvalId") Long approvalId);
}
