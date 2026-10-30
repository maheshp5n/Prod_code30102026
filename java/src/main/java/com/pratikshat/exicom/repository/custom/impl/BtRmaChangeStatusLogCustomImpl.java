package com.pratikshat.exicom.repository.custom.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import com.pratikshat.exicom.entity.BtRmaChangeStatusLog;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.UploadedFiles;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.interceptor.WorkflowExecuter;
import com.pratikshat.exicom.repository.custom.BtRmaChangeStatusLogCustom;

public class BtRmaChangeStatusLogCustomImpl implements BtRmaChangeStatusLogCustom {

	@PersistenceContext
	EntityManager em;
	
	@Autowired
	WorkflowExecuter workflow;
	
	@Override
	public BtRmaChangeStatusLog saveDetails(BtRmaChangeStatusLog btRmaChangeStatusLog, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		workflow.executeWorkflow(em, btRmaChangeStatusLog, "INSERT", "PRE", user);
		em.persist(btRmaChangeStatusLog);
		workflow.executeWorkflow(em, btRmaChangeStatusLog, "INSERT", "POST", user);
		return null;
	}

	@Override
	public BtRmaChangeStatusLog updateDetails(BtRmaChangeStatusLog t, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BtRmaChangeStatusLog deleteDetails(BtRmaChangeStatusLog t, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BtRmaChangeStatusLog saveDetails(BtRmaChangeStatusLog btRmaChangeStatusLog, UploadedFiles uploadedFile, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		workflow.executeWorkflow(em, btRmaChangeStatusLog, "INSERT", "PRE", user);
		em.persist(uploadedFile);
		em.persist(btRmaChangeStatusLog);
		workflow.executeWorkflow(em, btRmaChangeStatusLog, "INSERT", "POST", user);
		return null;
	}

}
