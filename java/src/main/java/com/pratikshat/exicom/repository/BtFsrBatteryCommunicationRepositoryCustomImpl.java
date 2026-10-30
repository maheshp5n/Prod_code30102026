package com.pratikshat.exicom.repository;

import java.text.SimpleDateFormat;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import com.pratikshat.exicom.entity.BtFsrBatteryCommunication;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.interceptor.WorkflowExecuter;

public class BtFsrBatteryCommunicationRepositoryCustomImpl implements BtFsrBatteryCommunicationRepositoryCustom{

	@PersistenceContext
	EntityManager em;
	@Autowired
	WorkflowExecuter workflow;
	
	@Override
	@Transactional
	public BtFsrBatteryCommunication saveDetails(BtFsrBatteryCommunication batteryCommunication, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		workflow.executeWorkflow(em, batteryCommunication, "INSERT", "PRE", user);
		em.persist(batteryCommunication);
		SimpleDateFormat dateformatter = new SimpleDateFormat("yyyyMMdd");
		String manualId = null;
		manualId = "FSR-COMM-BTY-" + dateformatter.format(new java.util.Date()) + "-"
		+ com.pratikshat.exicom.config.MiscellaneousUtils.fillZero(String.valueOf(batteryCommunication.getId()),5);
		batteryCommunication.setManualId(manualId);
		em.persist(batteryCommunication);
		workflow.executeWorkflow(em, batteryCommunication,"INSERT","POST",user);
		return null;
	}

	@Override
	@Transactional
	public BtFsrBatteryCommunication updateDetails(BtFsrBatteryCommunication batteryCommunication, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		BtFsrBatteryCommunication _check=null;
		try {
			_check=em.createQuery("select fsr from BtFsrBatteryCommunication fsr where fsr.id=?1",BtFsrBatteryCommunication.class)
					.setParameter(1, batteryCommunication.getId()).getSingleResult();	
		}catch (NoResultException e) {
			throw new BadRequestException("No result found with this ID");
		}
		_check.setBatterySNo1(batteryCommunication.getBatterySNo1());
		_check.setBatterySNo2(batteryCommunication.getBatterySNo2());
		_check.setBatterySNo3(batteryCommunication.getBatterySNo3());
		_check.setBatterySNo4(batteryCommunication.getBatterySNo4());
		_check.setBatterySNo5(batteryCommunication.getBatterySNo5());
		_check.setBatterySNo6(batteryCommunication.getBatterySNo6());
		_check.setBatterySNo7(batteryCommunication.getBatterySNo7());
		_check.setBatterySNo8(batteryCommunication.getBatterySNo8());
		_check.setBatterySoc1(batteryCommunication.getBatterySoc1());
		_check.setBatterySoc2(batteryCommunication.getBatterySoc2());
		_check.setBatterySoc3(batteryCommunication.getBatterySoc3());
		_check.setBatterySoc4(batteryCommunication.getBatterySoc4());
		_check.setBatterySoc5(batteryCommunication.getBatterySoc5());
		_check.setBatterySoc6(batteryCommunication.getBatterySoc6());
		_check.setBatterySoc7(batteryCommunication.getBatterySoc7());
		_check.setBatterySoc8(batteryCommunication.getBatterySoc8());
		_check.setDateOfSiteVisit(batteryCommunication.getDateOfSiteVisit());
		_check.setBtCase(batteryCommunication.getBtCase());
		_check.setServiceAppointment(batteryCommunication.getServiceAppointment());
		_check.setCbmsSerialNo(batteryCommunication.getCbmsSerialNo());
		_check.setWarehouse(batteryCommunication.getWarehouse());
		_check.setVoltageReading(batteryCommunication.getVoltageReading());
		_check.setVendorEngName(batteryCommunication.getVendorEngName());
		_check.setVendorEngContact(batteryCommunication.getVendorEngContact());
		_check.setSoftwareVersionOfBattery(batteryCommunication.getSoftwareVersionOfBattery());
		_check.setCurrentReading(batteryCommunication.getCurrentReading());
		_check.setCustomerMobileNo(batteryCommunication.getCustomerMobileNo());
		_check.setCustomerName(batteryCommunication.getCustomerName());
		_check.setOtherIssue(batteryCommunication.getOtherIssue());
		_check.setOtherSiteType(batteryCommunication.getOtherSiteType());
		_check.setAnyPhysicalDamage(batteryCommunication.getAnyPhysicalDamage());
		_check.setExicomIssues(batteryCommunication.getExicomIssues());
		_check.setSiteStatus(batteryCommunication.getSiteStatus());
		_check.setSiteType(batteryCommunication.getSiteType());
		_check.setOtherSiteType(batteryCommunication.getOtherSiteType());
		_check.setChargeVoltage(batteryCommunication.getChargeVoltage());
		_check.setFloatVoltage(batteryCommunication.getFloatVoltage());
		workflow.executeWorkflow(em, _check, "UPDATE", "PRE", user);
		em.persist(_check);
		workflow.executeWorkflow(em, _check, "UPDATE", "POST", user);
		
		return null;
	}

	@Override
	public BtFsrBatteryCommunication deleteDetails(BtFsrBatteryCommunication t, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BtFsrBatteryCommunication getFsrWithId(long id) throws BadRequestException {
		
		BtFsrBatteryCommunication batteryCommunication=null;
		try {
			batteryCommunication=em.createQuery("select fsr from BtFsrBatteryCommunication fsr left join fsr.serviceAppointment sr left join fsr.btCase ca"
					 +" left join fsr.warehouse wr left join fsr.siteType stype left join fsr.siteStatus stus where fsr.id=?1",BtFsrBatteryCommunication.class).setParameter(1, id).getSingleResult();
		}catch (NoResultException e) {
			throw new BadRequestException("No Fsr found with this ID");
		}
		
		return batteryCommunication;
	}

}
