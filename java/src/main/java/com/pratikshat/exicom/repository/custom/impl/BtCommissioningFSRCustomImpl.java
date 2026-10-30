package com.pratikshat.exicom.repository.custom.impl;

import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.protobuf.StringValue;
import com.pratikshat.exicom.config.FileStorageProperties;
import com.pratikshat.exicom.config.MiscellaneousUtils;
import com.pratikshat.exicom.entity.BtCommissioningFSR;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.interceptor.WorkflowExecuter;
import com.pratikshat.exicom.repository.custom.BtCommissioningFSRCustom;
import com.pratikshat.exicom.utils.CommissioningFSRPdf;

public class BtCommissioningFSRCustomImpl implements BtCommissioningFSRCustom {
	
	@PersistenceContext
	EntityManager em;
	
	@Autowired
	WorkflowExecuter workflow;
	
	@Autowired
	FileStorageProperties fileStorageProperties;

	@Override
	public BtCommissioningFSR saveDetails(BtCommissioningFSR btCommFSR, LoginUser user) throws BadRequestException, UnauthoriseException {
		workflow.executeWorkflow(em, btCommFSR, "INSERT", "PRE", user);
		em.persist(btCommFSR);
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyyMMdd");
		String manualId=null;
		manualId="FSR-I&C-"+MiscellaneousUtils.fillZero(String.valueOf(btCommFSR.getId()), 4);
		btCommFSR.setManualId(manualId);
		em.persist(btCommFSR);
		workflow.executeWorkflow(em, btCommFSR, "INSERT","POST", user);
		return null;
	}

	@Override
	public BtCommissioningFSR updateDetails(BtCommissioningFSR t, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		workflow.executeWorkflow(em, t, "UPDATE", "PRE", user);
        em.merge(t);
        workflow.executeWorkflow(em, t, "UPDATE", "POST", user);
		return null;
	}

	@Override
	public BtCommissioningFSR deleteDetails(BtCommissioningFSR t, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ByteArrayInputStream generatePdf(LoginUser user, long id) throws Exception {
		CommissioningFSRPdf pdf=new CommissioningFSRPdf(em,fileStorageProperties);
		return pdf.createPdf(user,id);
	}

}
