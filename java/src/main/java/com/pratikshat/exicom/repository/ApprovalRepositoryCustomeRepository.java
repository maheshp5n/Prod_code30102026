package com.pratikshat.exicom.repository;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.model.request.ApprovalRuleRequest;
import com.pratikshat.exicom.model.request.ApprovalStepRequest;

@Repository
public interface ApprovalRepositoryCustomeRepository {

	ApprovalRuleRequest createApprovalRule(ApprovalRuleRequest approvalRuleRequest, LoginUser user) throws Exception;
	
	ApprovalRuleRequest updateApprovalRule(ApprovalRuleRequest approvalRuleRequest, LoginUser user) throws Exception;
	
	ApprovalStepRequest createApprovalStep(ApprovalStepRequest approvalStepRequest, LoginUser user) throws Exception;
	
	ApprovalStepRequest updateApprovalStep(ApprovalStepRequest approvalStepRequest, LoginUser user) throws Exception;
	
	public void submitForApproval(EntityManager em,Class cls,String id,LoginUser user);
	public void submitForApproval(EntityManager em,Class cls,long id,LoginUser user);
	public void submitForApproval(EntityManager em,Class cls,Long id,LoginUser user);
}
