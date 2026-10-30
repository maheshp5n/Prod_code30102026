package com.pratikshat.exicom.repository;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.pratikshat.exicom.config.ApprovalWorkFlowAction;
import com.pratikshat.exicom.config.CustomeClassLoader;
import com.pratikshat.exicom.entity.BtApprovalStapes;
import com.pratikshat.exicom.entity.BtToBeApproved;
import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;

public class BtToBeApprovedRepositoryCustomeImpl implements BtToBeApprovedRepositoryCustome {

	@PersistenceContext
	EntityManager em;
	
	@Autowired
	CustomeClassLoader loader;
	
	@Override
	public BtToBeApproved saveDetails(BtToBeApproved t, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		em.persist(t);
		return null;
	}

	@Override
	public BtToBeApproved updateDetails(BtToBeApproved t, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		BtApprovalStapes stapes=null;
		try {
			stapes=em.createQuery("select stp from BtApprovalStapes stp join stp.approval apr where stp=?1",BtApprovalStapes.class).setParameter(1,t.getStapes()).getSingleResult();
			try {
				performeAction(stapes,t,em,user);
			} catch (MalformedURLException | ClassNotFoundException | InstantiationException | IllegalAccessException
					| BadRequestException | UnauthoriseException e) {
						e.printStackTrace();
				throw new BadRequestException(e.getMessage());
				
			}
			t.setActionBy(new EmployeeDetails(user.getUsername()));
			t.setStatus(true);
			em.persist(t);
			if(stapes.getStapeNumber()<stapes.getApproval().getTotalStapes()) {
				BtApprovalStapes nextStape=null;
				try {
					int stage=(stapes.getStapeNumber()+1);
					nextStape=em.createQuery("select stp from BtApprovalStapes stp where stp.stapeNumber=?1 and stp.approval=?2",BtApprovalStapes.class)
							.setParameter(1,stage)
							.setParameter(2, stapes.getApproval())
							.getSingleResult();
					if(t.getAction().equalsIgnoreCase("Approved")||t.getAction().equalsIgnoreCase("Approve")){
						BtToBeApproved nextTobe=new BtToBeApproved();
						nextTobe.setAssignedDate(new Date());
						nextTobe.setAssignedTo(nextStape.getAssignedTo());

						nextTobe.setInitiator(t.getInitiator());

						nextTobe.setManualId(t.getManualId());
						nextTobe.setObjectId(t.getObjectId());
						nextTobe.setObjectManualId(t.getObjectManualId());
						nextTobe.setObjectName(t.getObjectName());
						nextTobe.setObjectUrl(t.getObjectUrl());
						nextTobe.setOwner(new EmployeeDetails(user.getUsername()));
						nextTobe.setPreviousRemarks(t.getRemark());
						nextTobe.setStage(stage);
						nextTobe.setStapes(nextStape);
						nextTobe.setStatus(false);
						em.persist(nextTobe);
					}
					
				}catch(NoResultException ex) {
					throw new BadRequestException("Next stape not found for approval");
				}catch(NonUniqueResultException ex) {
					throw new BadRequestException("Multiple next stape found for approval");
				}
			}
			
			
			
			
		}catch(NoResultException ex) {
			throw new BadRequestException("Stapes not found");
		}	
		
		
		
		return null;
	}

	@Override
	public BtToBeApproved deleteDetails(BtToBeApproved t, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		// TODO Auto-generated method stub
		return null;
	}
	
	private void performeAction(BtApprovalStapes stapes,BtToBeApproved tobeApproved,EntityManager em,LoginUser user) throws MalformedURLException, ClassNotFoundException, InstantiationException, IllegalAccessException, BadRequestException, UnauthoriseException {
		//URLClassLoader classLoader=URLClassLoader.newInstance(new URL[] {new File(stapes.getClassLoaderUrl()).toURI().toURL()},this.getClass().getClassLoader());
		//Class clz= classLoader.loadClass(stapes.getActionClassName());
		
		ApprovalWorkFlowAction action=(ApprovalWorkFlowAction)loader.getClass(stapes.getActionClassName()).newInstance(); // (ApprovalWorkFlowAction)clz.newInstance();
		
		
		if(tobeApproved.getAction().equalsIgnoreCase("Approved")||tobeApproved.getAction().equalsIgnoreCase("Approve")) {
			action.onApproval(stapes, tobeApproved.getObjectId(), tobeApproved.getAction(), user, em);
		}
		if(tobeApproved.getAction().equalsIgnoreCase("Rejected")||tobeApproved.getAction().equalsIgnoreCase("Reject")) {
			action.onRejected(stapes, tobeApproved.getObjectId(), tobeApproved.getAction(), user, em);
		}
		
		
	}

}
