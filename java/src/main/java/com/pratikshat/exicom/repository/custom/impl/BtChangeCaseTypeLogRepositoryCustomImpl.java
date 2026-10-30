package com.pratikshat.exicom.repository.custom.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.cloud.storage.Acl.Entity;
import com.pratikshat.exicom.entity.BtChangeCaseTypeLog;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.interceptor.WorkflowExecuter;
import com.pratikshat.exicom.repository.custom.BtChangeCaseTypeLogRepositoryCustom;

public class BtChangeCaseTypeLogRepositoryCustomImpl implements BtChangeCaseTypeLogRepositoryCustom{

	@Autowired
	WorkflowExecuter workflow;
	
	@PersistenceContext
	EntityManager em;
	
	@Override
	public BtChangeCaseTypeLog saveDetails(BtChangeCaseTypeLog t, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		workflow.executeWorkflow(em, t, "PRE", "INSERT", user);
		em.persist(t);
		workflow.executeWorkflow(em, t, "POST", "INSERT", user);
		return null;
	}

	@Override
	public BtChangeCaseTypeLog updateDetails(BtChangeCaseTypeLog t, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BtChangeCaseTypeLog deleteDetails(BtChangeCaseTypeLog t, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		// TODO Auto-generated method stub
		return null;
	}

}
