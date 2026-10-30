package com.pratikshat.exicom.repository;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.pratikshat.exicom.entity.BtVendorPoDetails;
import com.pratikshat.exicom.entity.BtWccDetails;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.interceptor.WorkflowExecuter;

public class BtVendorPoDetailsRepositoryCustomImpl implements BtVendorPoDetailsRepositoryCustom{

	@Autowired
	WorkflowExecuter workflow;
	@PersistenceContext
	EntityManager em;
	
	@Override
	public BtVendorPoDetails saveDetails(BtVendorPoDetails t, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		workflow.executeWorkflow(em, t, "INSERT", "PRE", user);
		em.persist(t);
		workflow.executeWorkflow(em, t, "INSERT", "POST", user);
		return null;
	}

	@Override
	public BtVendorPoDetails updateDetails(BtVendorPoDetails details, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		BtVendorPoDetails _check=null;
		
		try {
			_check = em.createQuery("select wc from BtVendorPoDetails wc where wc.id=?1",BtVendorPoDetails.class).
					setParameter(1, details.getId()).getSingleResult();	
		}catch(NoResultException ex) {
			throw new BadRequestException("No result Found With this id");
		}
		return null;
	}

	@Override
	public BtVendorPoDetails deleteDetails(BtVendorPoDetails t, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		// TODO Auto-generated method stub
		return null;
	}

}
