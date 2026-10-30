package com.pratikshat.exicom.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.pratikshat.exicom.entity.BtVendorInvoiceSap;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.interceptor.WorkflowExecuter;

public class BtVendorInvoiceSapCustomeImpl implements BtVendorInvoiceSapCustome {

	@PersistenceContext
	EntityManager em;
	
	@Autowired
	WorkflowExecuter workflow;
	
	@Override
	public BtVendorInvoiceSap saveDetails(BtVendorInvoiceSap t, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		workflow.executeWorkflow(em, t, "INSERT","PRE", user);
		em.persist(t);
		workflow.executeWorkflow(em, t, "INSERT", "POST", user);
		
		return t;
	}

	@Override
	public BtVendorInvoiceSap updateDetails(BtVendorInvoiceSap t, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		
		workflow.executeWorkflow(em, t, "UPDATE","PRE", user);
		em.persist(t);
		workflow.executeWorkflow(em, t, "UPDATE", "POST", user);
		
		return t;
	}

	@Override
	public BtVendorInvoiceSap deleteDetails(BtVendorInvoiceSap t, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		
		return null;
	}

}
