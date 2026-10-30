package com.pratikshat.exicom.repository.custom.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.pratikshat.exicom.entity.BtRmaRejectLog;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.UploadedFiles;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.interceptor.WorkflowExecuter;
import com.pratikshat.exicom.repository.custom.BtRmaRejectLogCustom;

public class BtRmaRejectLogCustomImpl implements BtRmaRejectLogCustom {
	
	@PersistenceContext
	EntityManager em;
	
	@Autowired
	WorkflowExecuter workflow;

	@Override
	public BtRmaRejectLog saveDetails(BtRmaRejectLog btRmaRejectLog, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		workflow.executeWorkflow(em, btRmaRejectLog, "INSERT", "PRE", user);
		em.persist(btRmaRejectLog);
		workflow.executeWorkflow(em, btRmaRejectLog, "INSERT", "POST", user);
		return null;
	}

	@Override
	public BtRmaRejectLog updateDetails(BtRmaRejectLog t, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BtRmaRejectLog deleteDetails(BtRmaRejectLog t, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BtRmaRejectLog saveDetails(BtRmaRejectLog btRmaRejectLog, UploadedFiles uploadedFile, LoginUser user)throws BadRequestException, UnauthoriseException  {
		workflow.executeWorkflow(em, btRmaRejectLog, "INSERT", "PRE", user);
		em.persist(uploadedFile);
		em.persist(btRmaRejectLog);
		workflow.executeWorkflow(em, btRmaRejectLog, "INSERT", "POST", user);
		return null;
	}

}
