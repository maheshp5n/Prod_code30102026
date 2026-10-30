package com.pratikshat.exicom.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pratikshat.exicom.entity.ApprovalSteps;

@Repository
public interface ApprovalStepsRepository extends JpaRepository<ApprovalSteps, Long> {

	@Transactional(readOnly = true)
	@Query("select new com.pratikshat.exicom.model.response.ApprovalStepsResponse(ast) from ApprovalSteps ast join ast.approval apr where apr.id=:approvalId")
	List<com.pratikshat.exicom.model.response.ApprovalStepsResponse> getApprovalStepsByApprovalId(@Param("approvalId") Long approvalId);
	
	@Transactional(readOnly = true)
	@Query("select new com.pratikshat.exicom.model.response.ApprovalStepsResponse(ast) from ApprovalSteps ast join ast.approval apr where ast.id=:approvalStepId")
	com.pratikshat.exicom.model.response.ApprovalStepsResponse getApprovalStepsById(@Param("approvalStepId") Long approvalStepId);
	
	ApprovalSteps findById(long id);
}
