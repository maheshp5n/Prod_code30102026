package com.pratikshat.exicom.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.pratikshat.exicom.config.MiscellaneousUtils;
import com.pratikshat.exicom.entity.BtAnotherServiceAppointment;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.interceptor.WorkflowExecuter;

public class BtAnotherServiceAppointmentCustomRepositoryImpl implements BtAnotherServiceAppointmentCustomRepository{

	
	@PersistenceContext
	EntityManager em;
	
	@Autowired
	WorkflowExecuter workflow;
	
	@Override
	public BtAnotherServiceAppointment saveDetails(BtAnotherServiceAppointment t, LoginUser user)
			throws BadRequestException, UnauthoriseException {
	workflow.executeWorkflow(em, t, "INSERT", "PRE", user);
	em.persist(t);
	
	String manualId="BT-A-SA-"+MiscellaneousUtils.fillZero(""+t.getId(), 5);
	t.setManualId(manualId);
	em.merge(t);
	
	workflow.executeWorkflow(em, t, "INSERT", "POST", user);
		return t;
	}

	@Override
	public BtAnotherServiceAppointment updateDetails(BtAnotherServiceAppointment t, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		workflow.executeWorkflow(em, t, "UPDATE", "PRE", user);
		em.merge(t);
		workflow.executeWorkflow(em, t, "UPDATE", "POST", user);
		return t;
	}

	@Override
	public BtAnotherServiceAppointment deleteDetails(BtAnotherServiceAppointment t, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		// TODO Auto-generated method stub
		return null;
	}

}
