package com.pratikshat.exicom.repository;


import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;

import org.hibernate.NonUniqueObjectException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pratikshat.exicom.config.FileStorageProperties;
import com.pratikshat.exicom.config.MiscellaneousUtils;
import com.pratikshat.exicom.config.WorkFlow;
import com.pratikshat.exicom.entity.BtFsr;
import com.pratikshat.exicom.entity.BtFsrBatteryCommunication;
import com.pratikshat.exicom.entity.BtServiceFSR;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.interceptor.WorkflowExecuter;
import com.pratikshat.exicom.utils.ClosedServiceAppointmentPDF;
import com.pratikshat.exicom.utils.ServiceFSRPDF;

import javassist.expr.NewArray;

public class BtServiceFSRRepositoryCustomImpl implements BtServiceFSRRepositoryCustom{
	
	
	@PersistenceContext
	EntityManager em;
	
	@Autowired
	WorkflowExecuter workflow;
	
	@Autowired
	FileStorageProperties fileStorageProperties;

	@Override
	public BtFsr saveDetails(BtFsr btFsr, LoginUser user) throws BadRequestException, UnauthoriseException {
		workflow.executeWorkflow(em, btFsr,"INSERT","PRE", user);
		em.persist(btFsr);
		SimpleDateFormat dateFormat =new SimpleDateFormat("yyyyMMdd");
		String manualId=null;
		manualId="FSR-CBMS-LI-"
				+MiscellaneousUtils.fillZero(String.valueOf(btFsr.getId()), 5);
		btFsr.setManualId(manualId);
		em.persist(btFsr);
		workflow.executeWorkflow(em, btFsr, "INSERT","POST", user);
		
		return null;
	}

	@Override
	public BtFsr updateDetails(BtFsr t, LoginUser user) throws BadRequestException, UnauthoriseException {
		workflow.executeWorkflow(em, t, "UPDATE", "PRE", user);
        em.merge(t);
        workflow.executeWorkflow(em, t, "UPDATE", "POST", user);
		return null;
	}

	@Override
	public BtFsr deleteDetails(BtFsr t, LoginUser user) throws BadRequestException, UnauthoriseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BtFsr getFsrWithId(long id) throws BadRequestException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ByteArrayInputStream generatePdf(LoginUser user, long id) throws Exception {
		ServiceFSRPDF pdf = new ServiceFSRPDF(em, fileStorageProperties);
		return pdf.createPdf(user, id);
	}

	

	
}
