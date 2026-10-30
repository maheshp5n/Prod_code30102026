package com.pratikshat.exicom.repository;

import java.text.SimpleDateFormat;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;

import com.pratikshat.exicom.config.MiscellaneousUtils;
import com.pratikshat.exicom.entity.BtServiceAppoinmentProductConsumed;
import com.pratikshat.exicom.entity.BtServiceAppointment;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.interceptor.WorkflowExecuter;

public class BtServiceAppointmentProductConsumedCustomeRepoImpl implements BtServiceAppointmentProductConsumedCustomeRepo{

	@Autowired
	WorkflowExecuter workflow;
	
	@Autowired
	EntityManager em;
	
	@Override
	public BtServiceAppoinmentProductConsumed saveDetails(BtServiceAppoinmentProductConsumed appoinmentProductConsumed, LoginUser user)
			throws BadRequestException, UnauthoriseException {
	workflow.executeWorkflow(em, appoinmentProductConsumed,"INSERT","PRE", user);
	em.persist(appoinmentProductConsumed);
	SimpleDateFormat dateformatter = new SimpleDateFormat("yyyyMMdd");
	String manualId = null;
	manualId = "BT_SA_PC-" + dateformatter.format(new java.util.Date()) + "-"
	+ MiscellaneousUtils.fillZero(String.valueOf(appoinmentProductConsumed.getId()),5);
	appoinmentProductConsumed.setManualId(manualId);
	em.persist(appoinmentProductConsumed);
	workflow.executeWorkflow(em, appoinmentProductConsumed, "INSERT", "POST", user);
	return appoinmentProductConsumed;
	}

	@Override
	public BtServiceAppoinmentProductConsumed updateDetails(BtServiceAppoinmentProductConsumed t, LoginUser user)
			throws BadRequestException, UnauthoriseException {
	
		return null;
	}

	@Override
	public BtServiceAppoinmentProductConsumed deleteDetails(BtServiceAppoinmentProductConsumed t, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		
		return null;
	}

	@Override
	public BtServiceAppoinmentProductConsumed getproductConsumed(long id) throws BadRequestException {
		BtServiceAppoinmentProductConsumed appoinmentProductConsumed=null;
		try {
			appoinmentProductConsumed = em.createQuery(
					"select btpc from BtServiceAppoinmentProductConsumed btpc where btpc.id=?1",
					BtServiceAppoinmentProductConsumed.class).setParameter(1, id).getSingleResult();
		} catch (NoResultException ex) {
			throw new BadRequestException("Service Appointment does not exist");
		}
				return appoinmentProductConsumed;
	}
	

	@Override
	public void deleteItem(BtServiceAppoinmentProductConsumed id) throws BadRequestException {
	em.createQuery("delete from BtServiceAppoinmentProductConsumed items where items=?1").
	setParameter(1, id).executeUpdate();
	
	}

}
