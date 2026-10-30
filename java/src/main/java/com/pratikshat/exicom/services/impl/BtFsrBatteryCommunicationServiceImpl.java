package com.pratikshat.exicom.services.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import com.pratikshat.exicom.entity.BtCase;
import com.pratikshat.exicom.entity.BtFsrBatteryCommunication;
import com.pratikshat.exicom.entity.BtServiceAppointment;
import com.pratikshat.exicom.entity.BtSiteStatus;
import com.pratikshat.exicom.entity.BtSiteType;
import com.pratikshat.exicom.entity.CommissioningScope;
import com.pratikshat.exicom.entity.Location;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.repository.BtCaseRepository;
import com.pratikshat.exicom.repository.BtCommissioningScopeRepository;
import com.pratikshat.exicom.repository.BtFsrBatteryCommunicationRepository;
import com.pratikshat.exicom.repository.BtServiceAppointmentRepository;
import com.pratikshat.exicom.repository.BtSiteStatusRepository;
import com.pratikshat.exicom.repository.BtSiteTypeRepository;
import com.pratikshat.exicom.repository.LocationRepository;
import com.pratikshat.exicom.repository.UserProfileRepository;
import com.pratikshat.exicom.services.BtFsrBatteryCommunicationService;

@Service
public class BtFsrBatteryCommunicationServiceImpl implements BtFsrBatteryCommunicationService {

	@Autowired
	UserProfileRepository dbprofile;
	@Autowired
	BtServiceAppointmentRepository dbService;
	@Autowired
	BtCaseRepository dbCase;
	@Autowired
	LocationRepository dbwarehouse;
	@Autowired
	BtSiteStatusRepository dbStatus;
	@Autowired
	BtSiteTypeRepository dbSiteType;
	@Autowired
	BtFsrBatteryCommunicationRepository dbFsr;
	@Autowired
	JdbcTemplate jdbc;
	@Autowired
	BtCommissioningScopeRepository dbCommissioningScope;
	
	
	SimpleDateFormat dformatter = new SimpleDateFormat("yyyy-mm-dd");
	@Override
	public Map<String, Object> getSingle(Object id, LoginUser user) throws BadRequestException, UnauthoriseException {
		boolean view = dbprofile.hasObjectAccess(user.getUsername(),BtFsrBatteryCommunication.class, UserProfileRepository.VIEW);
		boolean viewAll = dbprofile.hasObjectAccess(user.getUsername(),BtFsrBatteryCommunication.class, UserProfileRepository.VIEW_ALL);
		if(view || viewAll){
			long _id=Long.parseLong(id.toString());
			return dbFsr.getSingleFsr(_id);
			
		}else {
			throw new UnauthoriseException("You are not Authorized");
		}
		
	}

	@Override
	public Page<Map<String, Object>> getPage(Pageable page, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		boolean view = dbprofile.hasObjectAccess(user.getUsername(),BtFsrBatteryCommunication.class, UserProfileRepository.VIEW);
		boolean viewAll = dbprofile.hasObjectAccess(user.getUsername(),BtFsrBatteryCommunication.class, UserProfileRepository.VIEW_ALL);
		if(view || viewAll){
			return dbFsr.getPage(page);
			
		}else {
			throw new UnauthoriseException("You are not Authorized");
		}
	}

	@Override
	@Transactional
	public Map<String, Object> saveObject(Map<String, Object> request, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		boolean create = dbprofile.hasObjectAccess(user.getUsername(), this.getClass(), UserProfileRepository.CREATE);
		BtFsrBatteryCommunication batteryCommunication = new BtFsrBatteryCommunication();
		if (create) {
		if (request.get("serviceAppointmentId") == null
				&& request.get("serviceAppointmentId").toString().equalsIgnoreCase("")) {
			throw new BadRequestException("No service Appointment Inserted");
		}

		BtServiceAppointment appointment = dbService
				.getServiceAppointment(Long.valueOf(request.get("serviceAppointmentId").toString()));
		System.out.println("service Appoint id is " + appointment.getId() + " and the request is "
				+ Long.valueOf(request.get("serviceAppointmentId").toString()));
		batteryCommunication.setServiceAppointment(appointment);
		BtCase btcase = dbCase.getCaseWihtId(appointment.getCaseDetails().getId());
		batteryCommunication.setBtCase(btcase);
		System.out.println("Case id is " + btcase.getId());
		batteryCommunication.setVendorEngName(appointment.getServiceResource().getName());
		batteryCommunication.setVendorEngContact(appointment.getServiceResource().getContact().getMobile());

		Location warehouse = null;
		try {
			warehouse = dbwarehouse.getLocationWithidAndType(Long.valueOf(request.get("warehouseId").toString()), 1);
		} catch (NoResultException e) {
			throw new BadRequestException("Not find any Warehouse");
		}
		batteryCommunication.setWarehouse(warehouse);
		BtSiteStatus status = null;
		try {
			status = dbStatus.getBySiteStatusWithId((int) request.get("siteStatus"));
			batteryCommunication.setSiteStatus(status);
		} catch (NoResultException e) {
			throw new BadRequestException("No Data Found");
		}
		BtSiteType siteType = null;
		try {
			siteType = dbSiteType.getBySitetypeWithId((int) request.get("siteType"));
			batteryCommunication.setSiteType(siteType);
		} catch (NoResultException e) {
			throw new BadRequestException("No Data found");
		}

		if (request.get("otherSiteType") != null) {
			
			batteryCommunication.setOtherSiteType(request.get("otherSiteType").toString());
		}
		try {
			batteryCommunication.setDateOfSiteVisit(dformatter.parse(request.get("visitDate").toString()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (request.get("batterySNo1") != null) {
			batteryCommunication.setBatterySNo1(Long.valueOf(request.get("batterySNo1").toString()));
		}
		if (request.get("batterySNo2") != null) {
			batteryCommunication.setBatterySNo2(Long.valueOf(request.get("batterySNo2").toString()));
		}
		if (request.get("batterySNo3") != null) {
			batteryCommunication.setBatterySNo3(Long.valueOf(request.get("batterySNo3").toString()));
		}
		if (request.get("batterySNo4") != null) {
			batteryCommunication.setBatterySNo4(Long.valueOf(request.get("batterySNo4").toString()));
		}
		if (request.get("batterySNo5") != null) {
			batteryCommunication.setBatterySNo5(Long.valueOf(request.get("batterySNo5").toString()));
		}
		if (request.get("batterySNo6") != null) {
			batteryCommunication.setBatterySNo6(Long.valueOf(request.get("batterySNo6").toString()));
		}
		if (request.get("batterySNo7") != null) {
			batteryCommunication.setBatterySNo7(Long.valueOf(request.get("batterySNo7").toString()));
		}
		if (request.get("batterySNo8") != null) {
			batteryCommunication.setBatterySNo8(Long.valueOf(request.get("batterySNo8").toString()));
		}
		if (request.get("batterySoc1") != null) {
			batteryCommunication.setBatterySoc1(Long.valueOf(request.get("batterySoc1").toString()));
		}
		if (request.get("batterySoc2") != null) {
			batteryCommunication.setBatterySoc2(Long.valueOf(request.get("batterySoc2").toString()));
		}
		if (request.get("batterySoc3") != null) {
			batteryCommunication.setBatterySoc3(Long.valueOf(request.get("batterySoc3").toString()));
		}
		if (request.get("batterySoc4") != null) {
			batteryCommunication.setBatterySoc4(Long.valueOf(request.get("batterySoc4").toString()));
		}
		if (request.get("batterySoc5") != null) {
			batteryCommunication.setBatterySoc5(Long.valueOf(request.get("batterySoc5").toString()));
		}
		if (request.get("batterySoc6") != null) {
			batteryCommunication.setBatterySoc6(Long.valueOf(request.get("batterySoc6").toString()));
		}
		if (request.get("batterySoc7") != null) {
			batteryCommunication.setBatterySoc7(Long.valueOf(request.get("batterySoc7").toString()));
		}
		if (request.get("batterySoc8") != null) {
			batteryCommunication.setBatterySoc8(Long.valueOf(request.get("batterySoc8").toString()));
		}
		if (request.get("anyPhysicalDamage") != null) {
			batteryCommunication.setAnyPhysicalDamage(request.get("anyPhysicalDamage").toString());
		}
		if (request.get("otherIssue") != null) {
			batteryCommunication.setOtherIssue(request.get("otherIssue").toString());
		}
		if (request.get("excomIssue") != null) {
			batteryCommunication.setExicomIssues(request.get("excomIssue").toString());
		}
		if (request.get("customerMobileNo") != null) {
			batteryCommunication.setCustomerMobileNo(request.get("customerMobileNo").toString());
		}
		if (request.get("customerName") != null) {
			batteryCommunication.setCustomerName(request.get("customerName").toString());
		}
		if (request.get("currentReading") != null) {
			batteryCommunication.setCurrentReading(request.get("currentReading").toString());
		}
		if (request.get("voltageReading") != null) {
			batteryCommunication.setVoltageReading(request.get("voltageReading").toString());
		}
		if (request.get("cbmsSerialNo") != null) {
			batteryCommunication.setCbmsSerialNo(request.get("cbmsSerialNo").toString());
		}
		if (request.get("softwareVersionOfBattery") != null) {
			batteryCommunication.setSoftwareVersionOfBattery(request.get("softwareVersionOfBattery").toString());
		}
		if (request.get("chargeVoltage") != null) {
			batteryCommunication.setChargeVoltage(request.get("chargeVoltage").toString());
		}
		if (request.get("floatVoltage") != null) {
			batteryCommunication.setFloatVoltage(request.get("floatVoltage").toString());
		}
		if (request.get("commissioningScope") != null) {
			CommissioningScope commissioningScope=dbCommissioningScope.findById((int)request.get("commissioningScope"));
			batteryCommunication.setCommissioningScope(commissioningScope);
		}
		dbFsr.saveDetails(batteryCommunication, user);

		} else {
			throw new BadRequestException("You are not Authorize");
		}
		return null;
	}

	@Override
	@Transactional
	public Map<String, Object> updateObject(Map<String, Object> request, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		boolean update = dbprofile.hasObjectAccess(user.getUsername(), BtFsrBatteryCommunication.class,
				UserProfileRepository.EDIT);
		BtFsrBatteryCommunication batteryCommunication = null;
		if (update) {
		batteryCommunication = dbFsr.getFsrWithId(Long.valueOf(request.get("fsrId").toString()));

		BtServiceAppointment appointment = null;
		if (request.get("serviceAppointmentId") != null) {

			appointment = dbService.getServiceAppointment(Long.valueOf(request.get("serviceAppointmentId").toString()));
			batteryCommunication.setServiceAppointment(appointment);
			BtCase btcase = dbCase.getCaseWihtId(appointment.getCaseDetails().getId());
			batteryCommunication.setBtCase(btcase);
			batteryCommunication.setVendorEngName(appointment.getServiceResource().getName());
			batteryCommunication.setVendorEngContact(appointment.getServiceResource().getContact().getMobile());

		}

		if (request.get("warehouseId") != null) {
			Location warehouse = null;
			try {
				warehouse = dbwarehouse.getLocationWithidAndType(Long.valueOf(request.get("warehouseId").toString()),
						1);
			} catch (NoResultException e) {
				throw new BadRequestException("Not find any Warehouse");
			}
			batteryCommunication.setWarehouse(warehouse);
		}
		if (request.get("siteStatus") != null) {
			BtSiteStatus status = null;
			try {
				status = dbStatus.getBySiteStatusWithId((int) request.get("siteStatus"));
				batteryCommunication.setSiteStatus(status);
			} catch (NoResultException e) {
				throw new BadRequestException("No Data Found");
			}
		}
		if (request.get("siteType") != null) {
			BtSiteType siteType = null;
			try {
				siteType = dbSiteType.getBySitetypeWithId((int) request.get("siteType"));
				batteryCommunication.setSiteType(siteType);
			} catch (NoResultException e) {
				throw new BadRequestException("No Data found");
			}
		}
		if (request.get("otherSiteType") != null) {
		
			batteryCommunication.setOtherSiteType(request.get("otherSiteType").toString());
		}
		if(request.get("visitDate")!=null) {
			try {
				batteryCommunication.setDateOfSiteVisit(dformatter.parse(request.get("visitDate").toString()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if (request.get("batterySNo1") != null) {
			batteryCommunication.setBatterySNo1(Long.valueOf(request.get("batterySNo1").toString()));
		}
		if (request.get("batterySNo2") != null) {
			batteryCommunication.setBatterySNo2(Long.valueOf(request.get("batterySNo2").toString()));
		}
		if (request.get("batterySNo3") != null) {
			batteryCommunication.setBatterySNo3(Long.valueOf(request.get("batterySNo3").toString()));
		}
		if (request.get("batterySNo4") != null) {
			batteryCommunication.setBatterySNo4(Long.valueOf(request.get("batterySNo4").toString()));
		}
		if (request.get("batterySNo5") != null) {
			batteryCommunication.setBatterySNo5(Long.valueOf(request.get("batterySNo5").toString()));
		}
		if (request.get("batterySNo6") != null) {
			batteryCommunication.setBatterySNo6(Long.valueOf(request.get("batterySNo6").toString()));
		}
		if (request.get("batterySNo7") != null) {
			batteryCommunication.setBatterySNo7(Long.valueOf(request.get("batterySNo7").toString()));
		}
		if (request.get("batterySNo8") != null) {
			batteryCommunication.setBatterySNo8(Long.valueOf(request.get("batterySNo8").toString()));
		}
		if (request.get("batterySoc1") != null) {
			batteryCommunication.setBatterySoc1(Long.valueOf(request.get("batterySoc1").toString()));
		}
		if (request.get("batterySoc2") != null) {
			batteryCommunication.setBatterySoc2(Long.valueOf(request.get("batterySoc2").toString()));
		}
		if (request.get("batterySoc3") != null) {
			batteryCommunication.setBatterySoc3(Long.valueOf(request.get("batterySoc3").toString()));
		}
		if (request.get("batterySoc4") != null) {
			batteryCommunication.setBatterySoc4(Long.valueOf(request.get("batterySoc4").toString()));
		}
		if (request.get("batterySoc5") != null) {
			batteryCommunication.setBatterySoc5(Long.valueOf(request.get("batterySoc5").toString()));
		}
		if (request.get("batterySoc6") != null) {
			batteryCommunication.setBatterySoc6(Long.valueOf(request.get("batterySoc6").toString()));
		}
		if (request.get("batterySoc7") != null) {
			batteryCommunication.setBatterySoc7(Long.valueOf(request.get("batterySoc7").toString()));
		}
		if (request.get("batterySoc8") != null) {
			batteryCommunication.setBatterySoc8(Long.valueOf(request.get("batterySoc8").toString()));
		}
		if (request.get("anyPhysicalDamage") != null) {
			batteryCommunication.setAnyPhysicalDamage(request.get("anyPhysicalDamage").toString());
		}
		if (request.get("otherIssue") != null) {
			batteryCommunication.setOtherIssue(request.get("otherIssue").toString());
		}
		if (request.get("excomIssue") != null) {
			batteryCommunication.setExicomIssues(request.get("excomIssue").toString());
		}
		if (request.get("customerMobileNo") != null) {
			batteryCommunication.setCustomerMobileNo(request.get("customerMobileNo").toString());
		}
		if (request.get("customerName") != null) {
			batteryCommunication.setCustomerName(request.get("customerName").toString());
		}
		if (request.get("currentReading") != null) {
			batteryCommunication.setCurrentReading(request.get("currentReading").toString());
		}
		if (request.get("voltageReading") != null) {
			batteryCommunication.setVoltageReading(request.get("voltageReading").toString());
		}
		if (request.get("cbmsSerialNo") != null) {
			batteryCommunication.setCbmsSerialNo(request.get("cbmsSerialNo").toString());
		}
		if (request.get("softwareVersionOfBattery") != null) {
			batteryCommunication.setSoftwareVersionOfBattery(request.get("softwareVersionOfBattery").toString());
		}
		if (request.get("chargeVoltage") != null) {
			batteryCommunication.setChargeVoltage(request.get("chargeVoltage").toString());
		}
		if (request.get("floatVoltage") != null) {
			batteryCommunication.setFloatVoltage(request.get("floatVoltage").toString());
		}
		if (request.get("commissioningScope") != null) {
			CommissioningScope commissioningScope= dbCommissioningScope.findById((int)request.get("commissioningScope"));
			batteryCommunication.setCommissioningScope(commissioningScope);
		}
		dbFsr.updateDetails(batteryCommunication, user);

		} else {
			throw new UnauthoriseException("You are not authorized");
		}
		return null;
	}

	@Override
	public List<Map<String, Object>> searchForList(String search, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		if (search != null && !search.equalsIgnoreCase("")) {
			List<Object> value = new ArrayList<>();
			String _query = "SELECT fsr.`id` AS id, fsr.`manuald` AS manualId, serapp.`manual_id` AS serviceManualId\r\n"
					+ "FROM `bt_fsr_battery_communication` fsr\r\n"
					+ "JOIN `bt_service_appointment` serapp ON serapp.`service_appointment_id`=fsr.`service_appointment_service_appointment_id`\r\n"
					+ "	WHERE";
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append(_query);
			stringBuilder.append(" fsr.`manuald` like ?");
			value.add("%" + search + "%");
			stringBuilder.append(" or serapp.`manual_id` like ?");
			value.add("%" + search + "%");
			return jdbc.queryForList(stringBuilder.toString(), value.toArray());
		}
		return new ArrayList<Map<String, Object>>();
	}

	@Override
	public List<Map<String, Object>> advanceSearch(Map<String, Object> searchKeys, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		
		List<Object> value = new ArrayList<>();
		String _query = "SELECT fsr.`id` AS id,fsr.`manuald` AS manualId,  fsr.`batterysno1` AS batterySno_1, fsr.`batterysno2` AS batterySno_2 ,fsr.`batterysno3` AS batterySno_3,\r\n"
				+ "       fsr.`batterysno4` AS batterySno_4,fsr.`batterysno5` AS batterySno_5, fsr.`batterysno6` AS batterySno_5,\r\n"
				+ "       fsr.`batterysno7` AS batterySno_7 ,fsr.`batterysno8` AS batterySno_8,\r\n"
				+ "       fsr.`battery_soc1` AS batterySoc_1, fsr.`battery_soc2` AS batterySoc_2, fsr.`battery_soc3` AS batterySoc_3,\r\n"
				+ "       fsr.`battery_soc4` AS batterySoc_4,fsr.`battery_soc5` AS batterySoc_5, fsr.`battery_soc6` AS batterySoc_6,\r\n"
				+ "       fsr.`battery_soc7` AS batterySoc_7, fsr.`battery_soc8` AS batterySoc_8,fsr.`commissioning_scope` AS commissinoningScope,\r\n"
				+ "       fsr.`float_voltage` AS floatvoltage, fsr.`charge_voltage` AS chargeVoltage,\r\n"
				+ "       fsr.`vendor_eng_name` AS vendorEngName, fsr.`vendor_eng_contact` AS vendorEngContact,\r\n"
				+ "       fsr.`voltage_reading` AS voltageReading, fsr.`other_issue`  AS otherIssue, fsr.`any_physical_damage` AS anyPhysicalDamage,\r\n"
				+ "       fsr.`customer_name` AS customerName, fsr.`customer_mobile_no` AS customerMobileNo,fsr.`cbms_serial_no` AS \r\n"
				+ "       cbmsSerialNo, fsr.`current_reading` AS currentReading ,  fsr.`software_version_of_battery` AS softwareVersion,\r\n"
				+ "       fsr.`exicom_issues` AS exicomIssue, \r\n"
				+ "       war.`location_name` AS warehouse, serapp.`manual_id` AS serviceManuaId, serapp.`visit_date` AS dateOfSiteVisit,\r\n"
				+ "       sta.`name` AS siteStatus , st.`type_name` AS siteType, ca.`manual_id` AS caseManualId\r\n"
				+ "       \r\n"
				+ "\r\n"
				+ "FROM `bt_fsr_battery_communication` fsr\r\n"
				+ "JOIN `bt_service_appointment` serapp ON serapp.`service_appointment_id`=fsr.`service_appointment_service_appointment_id`\r\n"
				+ "JOIN `bt_site_status` sta ON sta.`id` = fsr.`site_status_id`\r\n"
				+ "JOIN `bt_site_type` st ON st.`id`= fsr.`site_type_id`\r\n"
				+ "JOIN `location` war ON war.`location_id`=fsr.`warehouse_location_id`\r\n"
				+ "JOIN `bt_case` ca ON ca.`id`=fsr.`bt_case_id`\r\n"

				+ "\r\n"
				+ "WHERE 1=1";
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append(_query);
		if (searchKeys.get("manualId") != null && !searchKeys.get("manualId").equals("")) {
			queryBuilder.append(" and fsr.`manuald` like ?");
			value.add("%" + searchKeys.get("manualId") + "%");
		}
		if (searchKeys.get("serviceManualId") != null && !searchKeys.get("serviceManualId").equals("")) {
			queryBuilder.append(" and serapp.`manual_id` like ?");
			value.add("%" + searchKeys.get("serviceManualId") + "%");
		}
		if (searchKeys.get("siteStatus") != null && !searchKeys.get("siteStatus").equals("")) {
			queryBuilder.append(" and sta.`name` like ?");
			value.add("%" + searchKeys.get("siteStatus") + "%");
		}
		return jdbc.queryForList(queryBuilder.toString(), value.toArray());

	}

}