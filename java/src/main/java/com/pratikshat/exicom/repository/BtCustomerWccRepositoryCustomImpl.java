package com.pratikshat.exicom.repository;

import java.text.SimpleDateFormat;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import com.pratikshat.exicom.entity.BtCustomerWcc;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.UploadedFiles;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.interceptor.WorkflowExecuter;

public class BtCustomerWccRepositoryCustomImpl implements BtCustomerWccRepositoryCustom
{
	
	@PersistenceContext
	EntityManager em;
	
	@Autowired
	WorkflowExecuter executer;

	
	@Override
	public BtCustomerWcc saveDetails(BtCustomerWcc btCustomerWcc, LoginUser user) throws BadRequestException, UnauthoriseException {
		executer.executeWorkflow(em, btCustomerWcc, "INSERT", "PRE", user);
		em.persist(btCustomerWcc);
		SimpleDateFormat dateformatter = new SimpleDateFormat("yyyyMMdd");
		String manualId = null;
		manualId = "CU-WCC-"
				+ com.pratikshat.exicom.config.MiscellaneousUtils.fillZero(String.valueOf(btCustomerWcc.getId()), 5);
		btCustomerWcc.setManualId(manualId);
		em.persist(btCustomerWcc);
		executer.executeWorkflow(em, btCustomerWcc, "INSERT", "POST", user);
		return null;
	}

	@Override
	public BtCustomerWcc updateDetails(BtCustomerWcc t, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		
		BtCustomerWcc btCustomerWcc=null;
		try {
			btCustomerWcc=em.createQuery("select wa from BtCustomerWcc wa where wa.id=?1",BtCustomerWcc.class).setParameter(1, t.getId()).getSingleResult();
		}catch (NoResultException e) {
			// TODO: handle exception
		}
//		btCustomerWcc.setBtCase(t.getBtCase());
		btCustomerWcc.setApprovalDate(t.getApprovalDate());
		btCustomerWcc.setIsWccApprove(t.getIsWccApprove());
		btCustomerWcc.setApprovalStatus(t.getApprovalStatus());
//		btCustomerWcc.setRemarks(t.getRemarks());
		btCustomerWcc.setStatus(t.getStatus());
//		btCustomerWcc.setRejectReason(t.getRejectReason());
		executer.executeWorkflow(em, t, "UPDATE", "PRE", user);
		em.persist(btCustomerWcc);
		executer.executeWorkflow(em, t, "UPDATE", "POST", user);
		return t;
	}

	@Override
	public BtCustomerWcc deleteDetails(BtCustomerWcc t, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void wccfileUpload(UploadedFiles globalFile, MultipartFile wccfile, LoginUser user) throws BadRequestException, UnauthoriseException {
		executer.executeWorkflow(em, globalFile, "INSERT", "PRE", user);
		em.persist(globalFile);
		executer.executeWorkflow(em, globalFile, "INSERT", "POST", user);
		
	}
}
