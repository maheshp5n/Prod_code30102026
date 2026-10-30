package com.pratikshat.exicom.repository.custom.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.pratikshat.exicom.entity.BtCaseRejectLog;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.interceptor.WorkflowExecuter;
import com.pratikshat.exicom.repository.custom.BtCaseRejectLogRepositoryCustom;

public class BtCaseRejectLogRepositoryCustomImpl implements BtCaseRejectLogRepositoryCustom{

	@Autowired
	WorkflowExecuter workflow;
	
	@PersistenceContext
	EntityManager em;
	
	@Override
	public BtCaseRejectLog saveDetails(BtCaseRejectLog t, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		workflow.executeWorkflow(em, t, "INSERT", "PRE", user);
		em.persist(t);
		workflow.executeWorkflow(em, t, "INSERT", "POST", user);
		return null;
	}

	@Override
	public BtCaseRejectLog updateDetails(BtCaseRejectLog t, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BtCaseRejectLog deleteDetails(BtCaseRejectLog t, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		// TODO Auto-generated method stub
		return null;
	}

}
