package com.pratikshat.exicom.repository;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pratikshat.exicom.entity.BatteryComplaintStatus;
import com.pratikshat.exicom.entity.BatteryFSR;
import com.pratikshat.exicom.entity.BatterySiteType;
import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.PendingResponsibility;
import com.pratikshat.exicom.entity.ServiceAppointment;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.request.BatteryFSRRequest;

@Repository
public class BatteryFSRCustomRepositoryImpl implements BatteryFSRCustomRepository {

	@PersistenceContext
	EntityManager entityManager;
	@Autowired
	com.pratikshat.exicom.interceptor.WorkflowExecuter workflow;
	
	@Transactional(isolation = Isolation.READ_UNCOMMITTED, rollbackFor = { Exception.class,RuntimeException.class,BadRequestException.class,UnauthoriseException.class, }, propagation = Propagation.REQUIRES_NEW)
	@Override
	public BatteryFSRRequest saveBatteryFSR(BatteryFSRRequest request, LoginUser user) throws Exception {
		
		ServiceAppointment serviceAppointment = null;
		try {
			Query selectServiceAppointmentQuery = entityManager.createQuery("select sa from ServiceAppointment sa where sa.serviceAppointmentId=?1",ServiceAppointment.class);
			selectServiceAppointmentQuery.setParameter(1, request.getServiceAppointmentId());
			serviceAppointment = (ServiceAppointment) selectServiceAppointmentQuery.getSingleResult();
		} catch (NoResultException ex) {
			
		}
		
		if(serviceAppointment==null) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Service Appointment does not exist");
		}
		
		BatteryFSR batteryFSR = new BatteryFSR();
		batteryFSR.setAmbientTemp(request.getAmbientTemp());
		batteryFSR.setBatteryVoltageAfterRepair(request.getBatteryVoltageAfterRepair());
		batteryFSR.setBatteryVoltageBeforRepair(request.getBatteryVoltageBeforRepair());
		if(request.getComplaintStatusId()!=0) {
			batteryFSR.setComplaintStatus(new BatteryComplaintStatus(request.getComplaintStatusId()));
		}else {
			batteryFSR.setComplaintStatus(null);
		}
		batteryFSR.setCorrectiveAction(request.getCorrectiveAction());
		batteryFSR.setCreatedBy(new EmployeeDetails(user.getUsername()));
		batteryFSR.setCreatedDate(new Date());
		
		batteryFSR.setCustomerOrSiteProblem(request.getCustomerOrSiteProblem());
		
		batteryFSR.setDateOfVisit(request.getDateOfVisit());
		batteryFSR.setDCLoad(request.getDCLoad());
		batteryFSR.setDGRating(request.getDGRating());
		batteryFSR.setDGRun(request.getDGRun());
		batteryFSR.setEarthingStatus(request.getEarthingStatus());
		batteryFSR.setEBAvailability(request.getEBAvailability());
		batteryFSR.setFaultObservedByEngineer(request.getFaultObservedByEngineer());
		batteryFSR.setFaultReportedByCustomer(request.getFaultReportedByCustomer());
		batteryFSR.setMdifiedBy(new EmployeeDetails(user.getUsername()));
		batteryFSR.setModifiedDate(new Date());
		batteryFSR.setOtherPendingIssue(request.getOtherPendingIssue());
		if(request.getPendingResponsibilityId()!=0) {
			batteryFSR.setPendingResponsibility(new PendingResponsibility(request.getPendingResponsibilityId()));	
		}else {
			batteryFSR.setPendingResponsibility(null);	
		}
		
		batteryFSR.setPowerPlantMakeAndCapacity(request.getPowerPlantMakeAndCapacity());
		batteryFSR.setProblemCustomerOrExicom(request.getProblemCustomerOrExicom());
		batteryFSR.setRemark(request.getRemark());
		batteryFSR.setServiceAppointment(serviceAppointment);
		batteryFSR.setSiteEnvCond(request.getSiteEnvCond());
		if(request.getSiteTypeId()!=0) {
			batteryFSR.setSiteType(new BatterySiteType(request.getSiteTypeId()));	
		}else {
			batteryFSR.setSiteType(null);	
		}
		
		
		batteryFSR.setSoftVersionCBMS(request.getSoftVersionCBMS());
		
		batteryFSR.setSparePartReplaced(request.getSparePartReplaced());
		
		batteryFSR.setVendorEngContact(request.getVendorEngContact());
		batteryFSR.setVendorEngName(request.getVendorEngName());
		workflow.executeWorkflow(entityManager, batteryFSR, "INSERT", "PRE", user);
		entityManager.persist(batteryFSR);
		workflow.executeWorkflow(entityManager, batteryFSR, "INSERT", "POST", user);

		
		Query updateManualId=entityManager.createQuery("update BatteryFSR fsr set fsr.manualId=?1 where fsr.id=?2");
		SimpleDateFormat dateformatter=new SimpleDateFormat("yyyyMMdd");
		String manualId= "FSR-CBMS-LI-"+dateformatter.format(new java.util.Date())+"-"+com.pratikshat.exicom.config.MiscellaneousUtils.fillZero(String.valueOf(batteryFSR.getId()),5);
		updateManualId.setParameter(1,manualId);
		updateManualId.setParameter(2, batteryFSR.getId());
		updateManualId.executeUpdate();
		
		request.setId(batteryFSR.getId());
		
		//search index entry
		try {
			com.pratikshat.exicom.entity.GlobalSearchIndex _search=new com.pratikshat.exicom.entity.GlobalSearchIndex();
			_search.setKeyName("manualId");
			_search.setObjectDetailsUrl("Batteryfsr/"+batteryFSR.getId());
			_search.setObjectIdName("id");
			_search.setObjectIdValue(""+batteryFSR.getId());
			_search.setObjectName("BatteryFSR");
			_search.setSearchString(manualId);
			entityManager.persist(_search);
			
		}catch(Exception e) {}
		
		entityManager.flush();
		entityManager.clear();
		
		return request;
	}

	@Transactional(isolation = Isolation.READ_UNCOMMITTED, rollbackFor = {BadRequestException.class,UnauthoriseException.class, Exception.class,RuntimeException.class }, propagation = Propagation.REQUIRES_NEW)
	@Override
	public BatteryFSRRequest updateBatteryFSR(BatteryFSRRequest request, LoginUser user) throws Exception {
		
		ServiceAppointment serviceAppointment = null;
		try {
			Query selectServiceAppointmentQuery = entityManager.createQuery("select sa from ServiceAppointment sa join sa.status st where sa.serviceAppointmentId=?1",ServiceAppointment.class);
			selectServiceAppointmentQuery.setParameter(1, request.getServiceAppointmentId());
			serviceAppointment = (ServiceAppointment) selectServiceAppointmentQuery.getSingleResult();
		} catch (NoResultException ex) {
			
		}
		if(serviceAppointment==null) {
			throw new com.pratikshat.exicom.exception.BadRequestException("ServiceAppointment does not exist");
		}
		
		
		BatteryFSR batteryFSR = null;
		try {
			Query selectBatteryFSRQuery = entityManager.createQuery("select fsr from BatteryFSR fsr where fsr.id=?1",BatteryFSR.class);
			selectBatteryFSRQuery.setParameter(1, request.getId());
			batteryFSR = (BatteryFSR) selectBatteryFSRQuery.getSingleResult();
		} catch (NoResultException ex) {
		}
		
		if(batteryFSR==null) {
			throw new com.pratikshat.exicom.exception.BadRequestException("BatteryFSR does not exist");
		}
		
		batteryFSR.setAmbientTemp(request.getAmbientTemp());
		batteryFSR.setBatteryVoltageAfterRepair(request.getBatteryVoltageAfterRepair());
		batteryFSR.setBatteryVoltageBeforRepair(request.getBatteryVoltageBeforRepair());
		if(request.getComplaintStatusId()!=0) {
			batteryFSR.setComplaintStatus(new BatteryComplaintStatus(request.getComplaintStatusId()));
		}else {
			batteryFSR.setComplaintStatus(null);
		}
		batteryFSR.setCorrectiveAction(request.getCorrectiveAction());
		batteryFSR.setCreatedBy(new EmployeeDetails(user.getUsername()));
		batteryFSR.setCreatedDate(new Date());
		
		batteryFSR.setCustomerOrSiteProblem(request.getCustomerOrSiteProblem());
		
		batteryFSR.setDateOfVisit(request.getDateOfVisit());
		batteryFSR.setDCLoad(request.getDCLoad());
		batteryFSR.setDGRating(request.getDGRating());
		batteryFSR.setDGRun(request.getDGRun());
		batteryFSR.setEarthingStatus(request.getEarthingStatus());
		batteryFSR.setEBAvailability(request.getEBAvailability());
		batteryFSR.setFaultObservedByEngineer(request.getFaultObservedByEngineer());
		batteryFSR.setFaultReportedByCustomer(request.getFaultReportedByCustomer());
		batteryFSR.setMdifiedBy(new EmployeeDetails(user.getUsername()));
		batteryFSR.setModifiedDate(new Date());
		batteryFSR.setOtherPendingIssue(request.getOtherPendingIssue());
		if(request.getPendingResponsibilityId()!=0) {
			batteryFSR.setPendingResponsibility(new PendingResponsibility(request.getPendingResponsibilityId()));	
		}else {
			batteryFSR.setPendingResponsibility(null);	
		}
		
		batteryFSR.setPowerPlantMakeAndCapacity(request.getPowerPlantMakeAndCapacity());
		batteryFSR.setProblemCustomerOrExicom(request.getProblemCustomerOrExicom());
		batteryFSR.setRemark(request.getRemark());
		batteryFSR.setSiteEnvCond(request.getSiteEnvCond());
		if(request.getSiteTypeId()!=0) {
			batteryFSR.setSiteType(new BatterySiteType(request.getSiteTypeId()));	
		}else {
			batteryFSR.setSiteType(null);	
		}
		
		
		batteryFSR.setSoftVersionCBMS(request.getSoftVersionCBMS());
		
		batteryFSR.setSparePartReplaced(request.getSparePartReplaced());
		
		batteryFSR.setVendorEngContact(request.getVendorEngContact());
		batteryFSR.setVendorEngName(request.getVendorEngName());
		workflow.executeWorkflow(entityManager, batteryFSR, "UPDATE", "PRE", user);
		entityManager.persist(batteryFSR);
		workflow.executeWorkflow(entityManager, batteryFSR, "UPDATE", "POST", user);

		
		return request;
	}

}
