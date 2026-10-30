package com.pratikshat.exicom.repository.custom.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.pratikshat.exicom.entity.BtPreRegisterReport;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.interceptor.WorkflowExecuter;
import com.pratikshat.exicom.repository.custom.BtPreRegisterReportRepositoryCustom;

public class BtPreRegisterReportRepositoryCustomImpl implements BtPreRegisterReportRepositoryCustom{

	@Autowired
	WorkflowExecuter workflow;
	@PersistenceContext
	EntityManager em;
	
	@Override
	public BtPreRegisterReport saveDetails(BtPreRegisterReport t, LoginUser user)
			throws BadRequestException, UnauthoriseException {
	workflow.executeWorkflow(em, t, "INSERT", "PRE", user);
	em.persist(t);
	workflow.executeWorkflow(em, t, "INSERT", "POST", user);
	return null;
	}

	@Override
	public BtPreRegisterReport updateDetails(BtPreRegisterReport t, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BtPreRegisterReport deleteDetails(BtPreRegisterReport t, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		// TODO Auto-generated method stub
		return null;
	}

}
