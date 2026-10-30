package com.pratikshat.exicom.repository;

import java.text.SimpleDateFormat;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;

import com.pratikshat.exicom.entity.BtFsrOdcKitting;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.interceptor.WorkflowExecuter;

public class BtFsrOdcKittingRepositoryCustomImpl implements BtFsrOdcKittingRepositoryCustom {
	
	    @PersistenceContext
	    EntityManager em;

	    @Autowired
	    WorkflowExecuter workflow;


	@Override
	@Transactional
	public BtFsrOdcKitting saveDetails(BtFsrOdcKitting btFsrOdcKitting, LoginUser user) throws BadRequestException, UnauthoriseException {
		workflow.executeWorkflow(em, btFsrOdcKitting, "INSERT", "PRE", user);
		em.persist(btFsrOdcKitting);
		SimpleDateFormat dateformatter = new SimpleDateFormat("yyyyMMdd");
		String manualId = null;
		manualId = "FSR-ODC-BTY-" + dateformatter.format(new java.util.Date()) + "-"
		+ com.pratikshat.exicom.config.MiscellaneousUtils.fillZero(String.valueOf(btFsrOdcKitting.getId()),5);
		btFsrOdcKitting.setManualId(manualId);
		em.persist(btFsrOdcKitting);
		workflow.executeWorkflow(em, btFsrOdcKitting,"INSERT","POST",user);
		return null;
	}
	@Override
	@Transactional
	public BtFsrOdcKitting updateDetails(BtFsrOdcKitting fsrOdcKitting, LoginUser user) throws BadRequestException, UnauthoriseException {
		BtFsrOdcKitting _check=null;
		try {
			_check=em.createQuery("select fsr from BtFsrOdcKitting fsr where fsr.id=?1",BtFsrOdcKitting.class)
					.setParameter(1, fsrOdcKitting.getId()).getSingleResult();
		}catch (NoResultException e) {
			throw new BadRequestException("No fsr Found with this id");
		}
		_check.setAnyPhysicalDamage(fsrOdcKitting.getAnyPhysicalDamage());
		_check.setBatterySNo1(fsrOdcKitting.getBatterySNo1());
		_check.setBatterySNo2(fsrOdcKitting.getBatterySNo2());
		_check.setBatterySNo3(fsrOdcKitting.getBatterySNo3());
		_check.setBatterySNo4(fsrOdcKitting.getBatterySNo4());
		_check.setBatterySoc1(fsrOdcKitting.getBatterySoc1());
		_check.setBatterySoc2(fsrOdcKitting.getBatterySoc2());
		_check.setBatterySoc3(fsrOdcKitting.getBatterySoc3());
		_check.setBatterySoc4(fsrOdcKitting.getBatterySoc4());
		_check.setBatteryType(fsrOdcKitting.getBatteryType());
		_check.setDateOfSiteVisit(fsrOdcKitting.getDateOfSiteVisit());
		_check.setBtCase(fsrOdcKitting.getBtCase());
		_check.setBtServiceAppointment(fsrOdcKitting.getBtServiceAppointment());
		_check.seteNodeBType(fsrOdcKitting.geteNodeBType());
		_check.setCbmsSerialNo(fsrOdcKitting.getCbmsSerialNo());
		_check.setWarehouse(fsrOdcKitting.getWarehouse());
		_check.setVoltageReading(fsrOdcKitting.getVoltageReading());
		_check.setVendorEngName(fsrOdcKitting.getVendorEngName());
		_check.setVendorEngContact(fsrOdcKitting.getVendorEngContact());
		_check.setSoftwareVersionOfBattery(fsrOdcKitting.getSoftwareVersionOfBattery());
		_check.setSmpsSerialNo(fsrOdcKitting.getSmpsSerialNo());
		_check.setSmpsType(fsrOdcKitting.getSmpsType());
		_check.setUnitType(fsrOdcKitting.getUnitType());
		_check.setMacID(fsrOdcKitting.getMacID());
		_check.setProjectType(fsrOdcKitting.getProjectType());
		_check.setRectifierRating(fsrOdcKitting.getRectifierRating());
		_check.setRjilSmpsMatcode(fsrOdcKitting.getRjilSmpsMatcode());
		_check.setOdcSerialNo(fsrOdcKitting.getOdcSerialNo());
		_check.setOdcType(fsrOdcKitting.getOdcType());
		_check.setCurrentReading(fsrOdcKitting.getCurrentReading());
		_check.setCustomerMobileNo(fsrOdcKitting.getCustomerMobileNo());
		_check.setCustomerName(fsrOdcKitting.getCustomerName());
		_check.setOtherIssue(fsrOdcKitting.getOtherIssue());
		_check.setOtherSiteType(fsrOdcKitting.getOtherSiteType());
		workflow.executeWorkflow(em, _check, "UPDATE", "PRE", user);
		em.persist(_check);
		workflow.executeWorkflow(em, _check, "UPDATE", "POST", user);
		return null;
	}
	

	@Override
	public BtFsrOdcKitting deleteDetails(BtFsrOdcKitting t, LoginUser user) throws BadRequestException, UnauthoriseException {
		return null;
	}
	@Override
	public BtFsrOdcKitting getFsrWithId(long id) throws BadRequestException {
		BtFsrOdcKitting btFsrOdcKitting=null;
		try {
			btFsrOdcKitting=em.createQuery("select fsr from BtFsrOdcKitting fsr left join fsr.btServiceAppointment sr left join fsr.btCase ca"
					+ " left join fsr.warehouse wr where fsr.id=?1",BtFsrOdcKitting.class).setParameter(1, id).getSingleResult();
		}catch (NoResultException e) {
		throw new BadRequestException("No result Foound");
		}
		return btFsrOdcKitting;
	}

}
