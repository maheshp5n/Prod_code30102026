package com.pratikshat.exicom.repository;

import java.text.SimpleDateFormat;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import com.pratikshat.exicom.config.MiscellaneousUtils;
import com.pratikshat.exicom.entity.BtCase;
import com.pratikshat.exicom.entity.BtRMADetails;
import com.pratikshat.exicom.entity.BtSiteDetails;
import com.pratikshat.exicom.entity.CircleDetails;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.interceptor.WorkflowExecuter;

public class BtRmaDetailsRepositoryCustomeImpl implements BtRmaDetailsRepositoryCustome {

	@PersistenceContext
	EntityManager em;
	
	@Autowired
	WorkflowExecuter workflow;
	
	@Override
	public BtRMADetails saveDetails(BtRMADetails btRmaDetails, LoginUser user) throws BadRequestException, UnauthoriseException {
		workflow.executeWorkflow(em, btRmaDetails, "INSERT", "PRE", user);
		em.persist(btRmaDetails);
		SimpleDateFormat dateformatter = new SimpleDateFormat("ddMMyy");
		String manualId = null;
		BtCase _bcase=em.find(BtCase.class, btRmaDetails.getCaseDetails().getId());
		BtSiteDetails _site=em.find(BtSiteDetails.class, _bcase.getSite().getId());
		CircleDetails circle=em.find(CircleDetails.class, _site.getCircle().getCircleName());
		String key=null;
		if(circle.getSapCode().equalsIgnoreCase("UPE"))
			key="UE";
		else if(circle.getSapCode().equalsIgnoreCase("UPW"))
			key="UW";
		else 
			key=circle.getSapCode();

		manualId = "PRP-" + dateformatter.format(new java.util.Date())+"-"+key+ "-" +MiscellaneousUtils.fillZero(String.valueOf(btRmaDetails.getId()),6);
		btRmaDetails.setManualId(manualId);
		em.persist(btRmaDetails);
		workflow.executeWorkflow(em, btRmaDetails, "INSERT", "POST", user);
		return btRmaDetails;
	}

	@Override
	public BtRMADetails updateDetails(BtRMADetails t, LoginUser user) throws BadRequestException, UnauthoriseException {
		
		workflow.executeWorkflow(em, t, "UPDATE", "PRE", user);
		em.persist(t);
		workflow.executeWorkflow(em, t, "UPDATE", "POST", user);
		return t;
	}

	@Override
	public BtRMADetails deleteDetails(BtRMADetails t, LoginUser user) throws BadRequestException, UnauthoriseException {
		// TODO Auto-generated method stub
		return null;
	}

}
