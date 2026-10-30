package com.pratikshat.exicom.repository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import com.pratikshat.exicom.config.MiscellaneousUtils;
import com.pratikshat.exicom.entity.BtPreRegistrationCases;
import com.pratikshat.exicom.entity.BtSiteDetails;
import com.pratikshat.exicom.entity.BusinessDomain;
import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.entity.GlobalSearchIndex;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.interceptor.WorkflowExecuter;

public class BtPreRegistrationCasesRepositoryCustomImpl implements BtPreRegistrationCasesRepositoryCustom {
	
	@PersistenceContext
	EntityManager em;
	
	@Autowired
	WorkflowExecuter workflow;
	 @Autowired
		BusinessDomainRepository dbDomain;
		@Autowired
		BtSiteDetailsRepository dbSite;

	@Override
	@Transactional
	public BtPreRegistrationCases saveDetails(BtPreRegistrationCases btPreRegistrationCases, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		workflow.executeWorkflow(em, btPreRegistrationCases, "INSERT", "PRE", user);
		em.persist(btPreRegistrationCases);
		String manualId=null;
		SimpleDateFormat dateformatter = new SimpleDateFormat("yyyyMMdd");
		manualId = "PRE-CS-BT-"	+ MiscellaneousUtils.fillZero(String.valueOf(btPreRegistrationCases.getId()),5);
		btPreRegistrationCases.setManualId(manualId);
		BusinessDomain buisnessModule = dbDomain.getDomainWithId(btPreRegistrationCases.getBusinessDomain().getBusinessDomainId());
		BtSiteDetails btSiteDetails = dbSite.getSiteWithId(btPreRegistrationCases.getSite().getId());
		String emailId = null;
		if (btSiteDetails != null) {
			if (btSiteDetails.getCircle() != null) {
				if (buisnessModule.getBusinessDomainId() == 1) { // Battery
					if (btSiteDetails.getCircle() != null) {
						if (btSiteDetails.getCircle().getCircleManagerBattry() != null) {
							emailId = btSiteDetails.getCircle().getCircleManagerBattry().getEmailId();
						}
					}
				}
			} else {
				throw new com.pratikshat.exicom.exception.BadRequestException("Circle does not exist");
			}
		} else {
			throw new com.pratikshat.exicom.exception.BadRequestException("Site does not exist");
		}

		List<Integer> statusList = new ArrayList<>();
		statusList.add(1); // New
		statusList.add(2); // Closed
		statusList.add(4); // Rejected

		if (statusList.contains(btPreRegistrationCases.getStatus().getId())) {
			btPreRegistrationCases.setOwner(new EmployeeDetails(user.getUsername()));

		} else {
			if (emailId != null) {
				btPreRegistrationCases.setOwnerDetails(new com.pratikshat.exicom.entity.EmployeeDetails(emailId));
			} else {
				throw new com.pratikshat.exicom.exception.BadRequestException("Circle Manager Does not exist");
			}
		}
		em.persist(btPreRegistrationCases);
		workflow.executeWorkflow(em, btPreRegistrationCases, "INSERT", "POST", user);
		try {
			GlobalSearchIndex _search = new GlobalSearchIndex();
			_search.setKeyName("manualId");
			_search.setObjectDetailsUrl("bt/prc/" + btPreRegistrationCases.getId());
			_search.setObjectIdName("id");
			_search.setObjectIdValue("" + btPreRegistrationCases.getId());
			_search.setObjectName("BtPreRegistrationCases");
			_search.setSearchString(manualId);
			em.persist(_search);
		} catch (Exception e) {
		}
		return null;
	}
	

	@Override
	public BtPreRegistrationCases updateDetails(BtPreRegistrationCases cases, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		BtPreRegistrationCases _exits=null;
		try{
			_exits=em.createQuery("select ca from BtPreRegistrationCases ca where ca.id=?1",BtPreRegistrationCases.class).
					setParameter(1,cases.getId()).getSingleResult();
		}catch (NoResultException ex) {
			throw new BadRequestException("No Cases Found with this id..");
		}
		_exits.setPendingResponsibility(cases.getPendingResponsibility());//
		_exits.setRemarks(cases.getRemarks());//
		_exits.setInternalRemark(cases.getInternalRemark());//
		_exits.setInputFromEng(cases.getInputFromEng());//
		_exits.setAssets(cases.getAssets());//
		_exits.setCaseReason(cases.getCaseReason());//
		_exits.setCaseType(cases.getCaseType());//
		_exits.setPODate(cases.getPODate());//
		_exits.setPONumber(cases.getPONumber());//
		_exits.setAssignByAssignmentRule(cases.isAssignByAssignmentRule());//
		_exits.setPriority(cases.getPriority());//
		_exits.setStage(cases.getStage());//
		_exits.setMissingAssets(cases.getMissingAssets());//
		_exits.setNoOfsmr(cases.getNoOfsmr());//
		_exits.setOrigin(cases.getOrigin());//
		_exits.setSite(cases.getSite());//
		_exits.setContactPerson(cases.getContactPerson());//
		_exits.setEngineer(cases.getEngineer());//
		_exits.setStatus(cases.getStatus());//
		_exits.setRJILTT01No(cases.getRJILTT01No());//
		_exits.setServiceCenter(cases.getServiceCenter());//
		_exits.setOtherProblemReported(cases.getOtherProblemReported());///
		_exits.setCloseDate(cases.getCloseDate());//
		_exits.setClosedBy(cases.getClosedBy());//
		_exits.setLastAction(cases.getLastAction());
		_exits.setLastActionComment(cases.getLastActionComment());
		_exits.setAssetsTypeChoice(cases.getAssetsTypeChoice());
		_exits.setBtProblemMaster(cases.getBtProblemMaster());
		workflow.executeWorkflow(em,_exits,"UPDATE","PRE",user);
		em.persist(_exits);
		workflow.executeWorkflow(em,_exits,"UPDATE","POST",user);
		return null;
	}

	@Override
	public BtPreRegistrationCases deleteDetails(BtPreRegistrationCases t, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BtPreRegistrationCases getCasesWithId(long id) throws BadRequestException {
		BtPreRegistrationCases btPreRegistrationCases=null;
		try {
			btPreRegistrationCases=em.createQuery("select ca from BtPreRegistrationCases ca left join ca.status cs left join ca.assets ast left join ast.account acn left join ca.site st left join ca.engineer eng left join eng.contact econ left join ca.serviceCenter scr where ca.id=?1",
					BtPreRegistrationCases.class).setParameter(1, id).getSingleResult();
		}catch (NoResultException e) {
			throw new BadRequestException("No result found with this id");
		}
		return btPreRegistrationCases;
	}

}
