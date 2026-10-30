package com.pratikshat.exicom.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import com.pratikshat.exicom.entity.BtPreRegistrationCaseActionLog;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.interceptor.WorkflowExecuter;

public class BtPreRegistrationCaseActionLogRepositoryCustomImpl implements BtPreRegistrationCaseActionLogRepositoryCustom{
	
	@PersistenceContext
	EntityManager em;
	
	@Autowired
	WorkflowExecuter workflow;
	
	@Override
	@Transactional
	public BtPreRegistrationCaseActionLog saveDetails(BtPreRegistrationCaseActionLog actionLog, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		workflow.executeWorkflow(em, actionLog, "INSERT", "PRE", user);
		em.persist(actionLog);
		workflow.executeWorkflow(em, actionLog, "INSERT", "POST", user);
		return null;
	}

	@Override
	public BtPreRegistrationCaseActionLog updateDetails(BtPreRegistrationCaseActionLog t, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BtPreRegistrationCaseActionLog deleteDetails(BtPreRegistrationCaseActionLog t, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		// TODO Auto-generated method stub
		return null;
	}

}
