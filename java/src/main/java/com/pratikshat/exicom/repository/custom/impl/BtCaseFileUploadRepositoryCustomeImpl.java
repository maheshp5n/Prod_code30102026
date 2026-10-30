package com.pratikshat.exicom.repository.custom.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import com.pratikshat.exicom.config.MiscellaneousUtils;
import com.pratikshat.exicom.entity.BtCase;
import com.pratikshat.exicom.entity.BtCaseFileUpload;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.interceptor.WorkflowExecuter;
import com.pratikshat.exicom.repository.custom.BtCaseFileUploadRepositoryCustome;

public class BtCaseFileUploadRepositoryCustomeImpl implements BtCaseFileUploadRepositoryCustome {

	@PersistenceContext EntityManager em;
	@Autowired WorkflowExecuter workFlow; 
	
	
	
	@Override
	public BtCaseFileUpload saveDetails(BtCaseFileUpload t, LoginUser user)
			throws BadRequestException, UnauthoriseException {
			workFlow.executeWorkflow(em, t, "INSERT", "PRE", user);
			em.persist(t);
			String maunualId="BT-CS-FILE-"+MiscellaneousUtils.fillZero(String.valueOf(t.getId()), 5);
			t.setManualId(maunualId);
			em.merge(t);
			workFlow.executeWorkflow(em, t, "INSERT","POST", user);
			
		return t;
	}

	@Override
	public BtCaseFileUpload updateDetails(BtCaseFileUpload t, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		workFlow.executeWorkflow(em, t, "UPDATE", "PRE", user);
		em.merge(t);
		workFlow.executeWorkflow(em, t, "UPDATE","POST", user);
		return t;
	}

	@Override
	public BtCaseFileUpload deleteDetails(BtCaseFileUpload t, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		em.remove(t);
		return t;
	}

}
