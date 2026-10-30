package com.pratikshat.exicom.repository.custom.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.PODFileUpload;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.interceptor.WorkflowExecuter;
import com.pratikshat.exicom.repository.custom.PODFileUploadRepositoryCustomer;

public class PODFileUploadRepositoryCustomerImpl implements PODFileUploadRepositoryCustomer{

	@PersistenceContext
	EntityManager entityManager;
	
	@Autowired
	WorkflowExecuter executer;
	
	@Override
	public PODFileUpload saveDetails(PODFileUpload t, LoginUser user) throws BadRequestException, UnauthoriseException {
	executer.executeWorkflow(entityManager, t, "INSERT", "PRE", user);
	entityManager.persist(t);
	executer.executeWorkflow(entityManager, t, "INSERT", "POST", user);
		return null;
	}

	@Override
	public PODFileUpload updateDetails(PODFileUpload t, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PODFileUpload deleteDetails(PODFileUpload t, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		// TODO Auto-generated method stub
		return null;
	}

}
