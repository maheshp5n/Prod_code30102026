package com.pratikshat.exicom.repository.custom.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.pratikshat.exicom.entity.BtFsrFileUpload;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.interceptor.WorkflowExecuter;
import com.pratikshat.exicom.repository.custom.BtFsrFileUploadCustom;

public class BtFsrFileUploadCustomImpl implements BtFsrFileUploadCustom {
	
	@PersistenceContext
	EntityManager em;
	
	@Autowired
	WorkflowExecuter workflow;
	
	@Override
	public BtFsrFileUpload saveDetails(BtFsrFileUpload t, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		workflow.executeWorkflow(em, t, "PRE","INSERT", user);
		em.persist(t);
		workflow.executeWorkflow(em, t, "POST", "INSERT", user);
		return null;
	}

	@Override
	public BtFsrFileUpload updateDetails(BtFsrFileUpload t, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BtFsrFileUpload deleteDetails(BtFsrFileUpload t, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		// TODO Auto-generated method stub
		return null;
	}

}
