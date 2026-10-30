package com.pratikshat.exicom.repository;

import java.text.SimpleDateFormat;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import com.pratikshat.exicom.config.MiscellaneousUtils;
import com.pratikshat.exicom.entity.BtWccDetails;
import com.pratikshat.exicom.entity.GlobalSearchIndex;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.WccStatus;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.interceptor.WorkflowExecuter;

public class BtWccDetailsRepositoryCustomImpl implements BtWccDetailsRepositoryCustom {

	@PersistenceContext
	EntityManager em;
	@Autowired
	WorkflowExecuter workflow;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyymmdd");

	@Override
	@Transactional
	public BtWccDetails saveDetails(BtWccDetails btWccDetails, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		workflow.executeWorkflow(em, btWccDetails, "INSERT", "PRE", user);
		em.persist(btWccDetails);
		String manualId = null;
		manualId = String.format("WCC-BT-%s",MiscellaneousUtils.fillZero(String.valueOf(btWccDetails.getId()), 5));
		btWccDetails.setManualId(manualId);
		em.merge(btWccDetails);
		workflow.executeWorkflow(em, btWccDetails, "INSERT", "POST", user);
		try {
			GlobalSearchIndex search = new GlobalSearchIndex();
			search.setKeyName("manualId");
			search.setObjectDetailsUrl("bat_wcc_customer/" + btWccDetails.getId());
			search.setObjectIdName("id");
			search.setObjectIdValue("" + btWccDetails.getId());
			search.setObjectName("BtWccDetails");
			search.setSearchString(manualId);
			em.persist(search);
		} catch (Exception ex) {
		}
		return null;
	}

	@Override
	@Transactional
	public BtWccDetails updateDetails(BtWccDetails btWccDetails, LoginUser user) throws BadRequestException, UnauthoriseException {
		BtWccDetails _check= null;
		try {
			_check = em.createQuery("select wc from BtWccDetails wc where wc.id=?1",BtWccDetails.class).
					setParameter(1, btWccDetails.getId()).getSingleResult();	
		}catch(NoResultException ex) {
			throw new BadRequestException("No result Found With this id");
		}
		_check.setToDate(btWccDetails.getToDate());
		_check.setCaseReason(btWccDetails.getCaseReason());
		_check.setCaseType(btWccDetails.getCaseType());
		_check.setFromDate(btWccDetails.getFromDate());
		_check.setServiceCenter(btWccDetails.getServiceCenter());
		_check.setSynked(false);
		_check.setWccStatus(btWccDetails.getWccStatus());
		_check.setWccType(btWccDetails.getWccType());
		_check.setWccAmount(btWccDetails.getWccAmount());
		_check.setWccTaxAmount(btWccDetails.getWccTaxAmount());
		_check.setWccTotalAmount(btWccDetails.getWccTotalAmount());
		workflow.executeWorkflow(em, _check, "UPDATE", "PRE", user);
		em.persist(_check);
		workflow.executeWorkflow(em, _check, "UPDATE", "POST", user);
		return null;
	}

	@Override
	public BtWccDetails deleteDetails(BtWccDetails t, LoginUser user) throws BadRequestException, UnauthoriseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BtWccDetails getBtWccDetailsWithID(long id) throws BadRequestException {
		BtWccDetails btWccDetails=null;
		try {
			btWccDetails=em.createQuery("select wc from BtWccDetails wc where wc.id=?1",BtWccDetails.class).
					setParameter(1,id).getSingleResult();	
		}catch(NoResultException ex) {
			throw new BadRequestException("Wcc Details not found");
		}catch(NonUniqueResultException ex) {
			throw new BadRequestException("Multiple Record found");
		}
		return btWccDetails;
	}

	@Override
	@Transactional
	public String submitforApproval(long wccId, LoginUser user) throws BadRequestException, UnauthoriseException {
		BtWccDetails _wcc=null;
		try
		{
		_wcc = em.createQuery("select w from BtWccDetails w where w.id=?1", BtWccDetails.class).setParameter(1, wccId).getSingleResult();
		}catch(Exception ex)
		{
			throw new BadRequestException("Invalid Id");
		}
		
		if(!_wcc.getOwner().getEmailId().equalsIgnoreCase(user.getUsername())) throw new UnauthoriseException("You Are Not Authorized");
		if(_wcc.getWccStatus().getId()==1 || _wcc.getWccStatus().getId()==2) throw new BadRequestException("Already Submitted Or Approved");
		
		workflow.executeWorkflow(em, _wcc, "UPDATE", "PRE", user);

		_wcc.setWccStatus(new WccStatus(1));
		
		em.persist(_wcc);
		workflow.executeWorkflow(em, _wcc, "UPDATE", "POST", user);
		return "Submitted Sucessfully";
	}

}
