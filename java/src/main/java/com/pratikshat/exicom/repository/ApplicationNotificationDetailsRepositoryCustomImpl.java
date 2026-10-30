package com.pratikshat.exicom.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.pratikshat.exicom.entity.ApplicationNotificationDetails;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.interceptor.WorkflowExecuter;

public class ApplicationNotificationDetailsRepositoryCustomImpl implements ApplicationNotificationDetailsRepositoryCustom{

	@PersistenceContext
	EntityManager em;
	
	@Autowired
	WorkflowExecuter workflow;
	
	@Override
	public ApplicationNotificationDetails saveDetails(ApplicationNotificationDetails t, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		workflow.executeWorkflow(em, t, "INSERT", "PRE", user);
		em.persist(t);
		workflow.executeWorkflow(em, t, "INSERT", "POST", user);
		return null;
	}

	@Override
	public ApplicationNotificationDetails updateDetails(ApplicationNotificationDetails t, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ApplicationNotificationDetails deleteDetails(ApplicationNotificationDetails t, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		// TODO Auto-generated method stub
		return null;
	}

}
