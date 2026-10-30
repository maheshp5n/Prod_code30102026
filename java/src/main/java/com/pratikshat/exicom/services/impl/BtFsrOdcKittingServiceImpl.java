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
import com.pratikshat.exicom.entity.BtFsrOdcKitting;
import com.pratikshat.exicom.entity.BtServiceAppointment;
import com.pratikshat.exicom.entity.BtSiteType;
import com.pratikshat.exicom.entity.Location;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.repository.BtCaseRepository;
import com.pratikshat.exicom.repository.BtFsrOdcKittingRepository;
import com.pratikshat.exicom.repository.BtServiceAppointmentRepository;
import com.pratikshat.exicom.repository.BtSiteTypeRepository;
import com.pratikshat.exicom.repository.LocationRepository;
import com.pratikshat.exicom.repository.UserProfileRepository;
import com.pratikshat.exicom.services.BtFsrOdcKittingService;

@Service
public class BtFsrOdcKittingServiceImpl implements BtFsrOdcKittingService {

	@Autowired
	UserProfileRepository dbProfile;
	@Autowired
	BtServiceAppointmentRepository dbServiceAppointment;
	@Autowired
	BtCaseRepository dbCase;
	@Autowired
	LocationRepository dbwarehouse;
	@Autowired
	BtFsrOdcKittingRepository dbFsr;
	@Autowired
	JdbcTemplate jdbc;
	@Autowired
	BtSiteTypeRepository dbSiteType;
	SimpleDateFormat dformatter = new SimpleDateFormat("yyyy-mm-dd");

	@Override
	public Map<String, Object> getSingle(Object id, LoginUser user) throws BadRequestException, UnauthoriseException {
		boolean view = dbProfile.hasObjectAccess(user.getUsername(), BtFsrOdcKitting.class, UserProfileRepository.VIEW);
		boolean viewAll = dbProfile.hasObjectAccess(user.getUsername(), BtFsrOdcKitting.class, UserProfileRepository.VIEW_ALL);
		if(view || viewAll) {
			Long _id = Long.parseLong(id.toString());
			return dbFsr.getSingleCase(_id);
		}
		else {
			throw new UnauthoriseException("you are not authorize");
		}
		
	}

	@Override
	public Page<Map<String, Object>> getPage(Pageable page, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		boolean view = dbProfile.hasObjectAccess(user.getUsername(), BtFsrOdcKitting.class, UserProfileRepository.VIEW);
		boolean viewAll = dbProfile.hasObjectAccess(user.getUsername(), BtFsrOdcKitting.class, UserProfileRepository.VIEW_ALL);
		if(view || viewAll) {
			return dbFsr.getPage(page);
		}
		else {
			throw new UnauthoriseException("you are not authorize");
		}
	}

	@Override
	@Transactional
	public Map<String, Object> saveObject(Map<String, Object> request, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		boolean create = dbProfile.hasObjectAccess(user.getUsername(), BtFsrOdcKitting.class,
				UserProfileRepository.CREATE);
		BtFsrOdcKitting btFsrOdcKitting = new BtFsrOdcKitting();
		if (create) {
			if (request.get("serviceAppointmentId") == null
					&& request.get("serviceAppointmentId").toString().equalsIgnoreCase("")) {
				throw new BadRequestException("No service Appointment Inserted");
			}
			BtServiceAppointment btServiceAppointment = dbServiceAppointment
					.getServiceAppointment(Long.valueOf(request.get("serviceAppointmentId").toString()));
			BtCase btCase = dbCase.getCaseWihtId(btServiceAppointment.getCaseDetails().getId());
			btFsrOdcKitting.setBtCase(btCase);
			btFsrOdcKitting.setVendorEngName(btServiceAppointment.getServiceResource().getName());
			btFsrOdcKitting.setVendorEngContact(btServiceAppointment.getServiceResource().getContact().getMobile());
			btFsrOdcKitting.setBtServiceAppointment(btServiceAppointment);

			Location warehouse = null;
			try {
				warehouse = dbwarehouse.getLocationWithidAndType(Long.valueOf(request.get("warehouseId").toString()),
						1);
			} catch (NoResultException e) {
				throw new BadRequestException("Not find any Warehouse");
			}
			btFsrOdcKitting.setWarehouse(warehouse);
			BtSiteType siteType=dbSiteType.getBySitetypeWithId((int) request.get("siteType"));
			btFsrOdcKitting.setSiteType(siteType);
			if(request.get("otherSiteType")!=null) {
				btFsrOdcKitting.setOtherSiteType(request.get("otherSiteType").toString());
			}
			try {
				btFsrOdcKitting.setDateOfSiteVisit(dformatter.parse(request.get("visitDate").toString()));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (request.get("cbmsSerialNo") != null) {
				btFsrOdcKitting.setCbmsSerialNo(request.get("cbmsSerialNo").toString());
			}
			if (request.get("softwareVersionOfBattery") != null) {
				btFsrOdcKitting.setSoftwareVersionOfBattery(request.get("softwareVersionOfBattery").toString());
			}
			if (request.get("currentReading") != null) {
				btFsrOdcKitting.setCurrentReading(request.get("currentReading").toString());
			}
			if (request.get("voltageReading") != null) {
				btFsrOdcKitting.setVoltageReading(request.get("voltageReading").toString());
			}
			if (request.get("macId") != null) {
				btFsrOdcKitting.setMacID(request.get("macId").toString());
			}
			if (request.get("smpsSerialNo") != null) {
				btFsrOdcKitting.setSmpsSerialNo(request.get("smpsSerialNo").toString());
			}
			if (request.get("odcType") != null) {
				btFsrOdcKitting.setOdcType(request.get("odcType").toString());
			}
			if (request.get("odcSerialNo") != null) {
				btFsrOdcKitting.setOdcSerialNo(request.get("odcSerialNo").toString());
			}
			if (request.get("smpsType") != null) {
				btFsrOdcKitting.setSmpsType(request.get("smpsType").toString());
			}
			if (request.get("eNodeBType") != null) {
				btFsrOdcKitting.seteNodeBType(request.get("eNodeBType").toString());
			}
			if (request.get("projectType") != null) {
				btFsrOdcKitting.setProjectType(request.get("projectType").toString());
			}
			if (request.get("unitType") != null) {
				btFsrOdcKitting.setUnitType(request.get("unitType").toString());
			}
			if (request.get("rectifierRating") != null) {
				btFsrOdcKitting.setRectifierRating(request.get("rectifierRating").toString());
			}
			if (request.get("RjilSmpsMatcode") != null) {
				btFsrOdcKitting.setRjilSmpsMatcode(Long.valueOf(request.get("RjilSmpsMatcode").toString()));
			}
			if (request.get("batterySNo1") != null) {
				btFsrOdcKitting.setBatterySNo1(Long.valueOf(request.get("batterySNo1").toString()));
			}
			if (request.get("batterySNo2") != null) {
				btFsrOdcKitting.setBatterySNo2(Long.valueOf(request.get("batterySNo2").toString()));
			}
			if (request.get("batterySNo3") != null) {
				btFsrOdcKitting.setBatterySNo3(Long.valueOf(request.get("batterySNo3").toString()));
			}
			if (request.get("batterySNo4") != null) {
				btFsrOdcKitting.setBatterySNo4(Long.valueOf(request.get("batterySNo4").toString()));
			}
			if (request.get("batterySoc1") != null) {
				btFsrOdcKitting.setBatterySoc1(Long.valueOf(request.get("batterySoc1").toString()));
			}
			if (request.get("batterySoc2") != null) {
				btFsrOdcKitting.setBatterySoc2(Long.valueOf(request.get("batterySoc2").toString()));
			}
			if (request.get("batterySoc3") != null) {
				btFsrOdcKitting.setBatterySoc3(Long.valueOf(request.get("batterySoc3").toString()));
			}
			if (request.get("batterySoc4") != null) {
				btFsrOdcKitting.setBatterySoc4(Long.valueOf(request.get("batterySoc4").toString()));
			}
			if (request.get("batteryType") != null) {
				btFsrOdcKitting.setBatteryType(request.get("batteryType").toString());
			}
			if (request.get("anyPhysicalDamage") != null) {
				btFsrOdcKitting.setAnyPhysicalDamage(request.get("anyPhysicalDamage").toString());
			}
			if (request.get("otherIssue") != null) {
				btFsrOdcKitting.setOtherIssue(request.get("otherIssue").toString());
			}
			if (request.get("customerMobileNo") != null) {
				btFsrOdcKitting.setCustomerMobileNo(request.get("customerMobileNo").toString());
			}
			if (request.get("customerName") != null) {
				btFsrOdcKitting.setCustomerName(request.get("customerName").toString());
			}
			dbFsr.saveDetails(btFsrOdcKitting, user);
		} else {
			throw new UnauthoriseException("You are not Authorized");
		}
		return dbFsr.getSingleCase(btFsrOdcKitting.getId());
	}

	@Override
	@Transactional
	public Map<String, Object> updateObject(Map<String, Object> request, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		boolean update = dbProfile.hasObjectAccess(user.getUsername(), BtFsrOdcKitting.class,
				UserProfileRepository.EDIT);
		BtFsrOdcKitting btFsr = null;
		if (update) {
			btFsr = dbFsr.getFsrWithId(Long.valueOf(request.get("fsrId").toString()));

			if (request.get("serviceAppointmentId") != null) {
				BtServiceAppointment btServiceAppointment = dbServiceAppointment
						.getServiceAppointment(Long.valueOf(request.get("serviceAppointmentId").toString()));
				BtCase btCase = dbCase.getCaseWihtId(btServiceAppointment.getCaseDetails().getId());
				btFsr.setBtServiceAppointment(btServiceAppointment);
				btFsr.setVendorEngName(btServiceAppointment.getServiceResource().getName());
				btFsr.setVendorEngContact(btServiceAppointment.getServiceResource().getContact().getMobile());
				btFsr.setBtCase(btCase);
			}
			if (request.get("siteType") != null) {
				BtSiteType siteType=dbSiteType.getBySitetypeWithId((int) request.get("siteType"));
				btFsr.setSiteType(siteType);
			}
			if (request.get("otherSiteType") != null) {
				btFsr.setOtherSiteType(request.get("otherSiteType").toString());
			}
			if (request.get("warehouseId") != null) {
				Location warehouse = null;
				try {
					warehouse = dbwarehouse
							.getLocationWithidAndType(Long.valueOf(request.get("warehouseId").toString()), 1);
				} catch (NoResultException e) {
					throw new BadRequestException("Not find any Warehouse");
				}
				btFsr.setWarehouse(warehouse);
			}
			if(request.get("visitDate")!=null) {
				try {
					btFsr.setDateOfSiteVisit(dformatter.parse(request.get("visitDate").toString()));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			
			if (request.get("cbmsSerialNo") != null) {
				btFsr.setCbmsSerialNo(request.get("cbmsSerialNo").toString());
			}
			if (request.get("softwareVersionOfBattery") != null) {
				btFsr.setSoftwareVersionOfBattery(request.get("softwareVersionOfBattery").toString());
			}
			if (request.get("currentReading") != null) {
				btFsr.setCurrentReading(request.get("currentReading").toString());
			}
			if (request.get("voltageReading") != null) {
				btFsr.setVoltageReading(request.get("voltageReading").toString());
			}
			if (request.get("macId") != null) {
				btFsr.setMacID(request.get("macId").toString());
			}
			if (request.get("smpsSerialNo") != null) {
				btFsr.setSmpsSerialNo(request.get("smpsSerialNo").toString());
			}
			if (request.get("odcType") != null) {
				btFsr.setOdcType(request.get("odcType").toString());
			}
			if (request.get("odcSerialNo") != null) {
				btFsr.setOdcSerialNo(request.get("odcSerialNo").toString());
			}
			if (request.get("smpsType") != null) {
				btFsr.setSmpsType(request.get("smpsType").toString());
			}
			if (request.get("eNodeBType") != null) {
				btFsr.seteNodeBType(request.get("eNodeBType").toString());
			}
			if (request.get("projectType") != null) {
				btFsr.setProjectType(request.get("projectType").toString());
			}
			if (request.get("unitType") != null) {
				btFsr.setUnitType(request.get("unitType").toString());
			}
			if (request.get("rectifierRating") != null) {
				btFsr.setRectifierRating(request.get("rectifierRating").toString());
			}
			if (request.get("RjilSmpsMatcode") != null) {
				btFsr.setRjilSmpsMatcode(Long.valueOf(request.get("RjilSmpsMatcode").toString()));
			}
			if (request.get("batterySNo1") != null) {
				btFsr.setBatterySNo1(Long.valueOf(request.get("batterySNo1").toString()));
			}
			if (request.get("batterySNo2") != null) {
				btFsr.setBatterySNo2(Long.valueOf(request.get("batterySNo2").toString()));
			}
			if (request.get("batterySNo3") != null) {
				btFsr.setBatterySNo3(Long.valueOf(request.get("batterySNo3").toString()));
			}
			if (request.get("batterySNo4") != null) {
				btFsr.setBatterySNo4(Long.valueOf(request.get("batterySNo4").toString()));
			}
			if (request.get("batterySoc1") != null) {
				btFsr.setBatterySoc1(Long.valueOf(request.get("batterySoc1").toString()));
			}
			if (request.get("batterySoc2") != null) {
				btFsr.setBatterySoc2(Long.valueOf(request.get("batterySoc2").toString()));
			}
			if (request.get("batterySoc3") != null) {
				btFsr.setBatterySoc3(Long.valueOf(request.get("batterySoc3").toString()));
			}
			if (request.get("batterySoc4") != null) {
				btFsr.setBatterySoc4(Long.valueOf(request.get("batterySoc4").toString()));
			}
			if (request.get("batteryType") != null) {
				btFsr.setBatteryType(request.get("batteryType").toString());
			}
			if (request.get("anyPhysicalDamage") != null) {
				btFsr.setAnyPhysicalDamage(request.get("anyPhysicalDamage").toString());
			}
			if (request.get("otherIssue") != null) {
				btFsr.setOtherIssue(request.get("otherIssue").toString());
			}
			if (request.get("customerMobileNo") != null) {
				btFsr.setCustomerMobileNo(request.get("customerMobileNo").toString());
			}
			if (request.get("customerName") != null) {
				btFsr.setCustomerName(request.get("customerName").toString());
			}
			dbFsr.updateDetails(btFsr, user);
		} else {
			throw new UnauthoriseException("you are not Authorized");
		}
		return dbFsr.getSingleCase(btFsr.getId());
	}

	@Override
	public List<Map<String, Object>> searchForList(String search, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		if (search != null && !search.equalsIgnoreCase("")) {
			List<Object> value = new ArrayList<>();
			String _query = "SELECT fsr.`id` AS id, fsr.`manual_id` AS manualId,\r\n"
					+ "	serapp.`manual_id` AS serviceManualId\r\n"
					+ " FROM `bt_fsr_odc_kitting` fsr JOIN `bt_service_appointment` serapp ON serapp.`service_appointment_id`=fsr.`bt_service_appointment_service_appointment_id`\r\n"
					+ "	WHERE";
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append(_query);
			stringBuilder.append(" fsr.`manual_id` like ?");
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
		String _query = "SELECT fsr.`id` AS id, fsr.`manual_id` AS manualId, fsr.`odc_serial_no` AS odcSerialNo , fsr.`odc_type` AS odcType, fsr.`smps_serial_no` AS smpsSerialNo,\r\n"
				+ "			       fsr.`smps_type` AS smpsType,fsr.`batterysno1` AS batterySno_1, fsr.`batterysno2` AS batterySno_2 ,fsr.`batterysno3` AS batterySno_3,\r\n"
				+ "			       fsr.`batterysno4` AS batterySno_4, fsr.`battery_soc1` AS batterySoc_1, fsr.`battery_soc2` AS batterySoc_2, fsr.`battery_soc3` AS batterySoc_3,\r\n"
				+ "			       fsr.`battery_soc4` AS batterySoc_4,fsr.`rjil_smps_matcode` AS rjilSmpsMatcode, fsr.`rectifier_rating` AS rectifierRating,\r\n"
				+ "			       fsr.`unit_type` AS unitTtpe,fsr.`vendor_eng_contact` AS vendorEngName, fsr.`vendor_eng_name` AS vendorEnfContact,\r\n"
				+ "			       fsr.`voltage_reading` AS voltageReading, fsr.`other_issue`  AS otherIssue, fsr.`any_physical_damage` AS nayPhysicalDamage,\r\n"
				+ "			       fsr.`customer_name` AS customerName, fsr.`customer_mobile_no` AS customerMobileNo,fsr.`cbms_serial_no` AS \r\n"
				+ "			       cbmsSerialNo, fsr.`current_reading` AS currentReading , fsr.`e_nodebtype` AS eNodeType, fsr.`macid` AS macId ,\r\n"
				+ "			       fsr.`project_type` AS projectType, fsr.`software_version_of_battery` AS softwareVersion,\r\n"
				+ "			       serapp.`manual_id` AS serviceManualId ,serapp.`visit_date` AS dateOfVisit, ca.`manual_id` AS casManualId,lo.`location_name` AS warehouseName,\r\n"
				+ "			       site.`site_name` AS siteName, site.`manual_id` AS siteManuald,fsr.`other_site_type` AS otherSiteType\r\n"
				+ "			\r\n"
				+ "			\r\n"
				+ "			FROM `bt_fsr_odc_kitting` fsr\r\n"
				+ "			JOIN `bt_service_appointment` serapp ON serapp.`service_appointment_id`=fsr.`bt_service_appointment_service_appointment_id`\r\n"
				+ "			JOIN `bt_case` ca ON ca.`id`=fsr.`bt_case_id`\r\n"
				+ "			JOIN `bt_site_details` site ON site.`id`=ca.`site_id`\r\n"
				+ "			JOIN `location` lo ON lo.`location_id`=fsr.`warehouse_location_id`"
				+ "WHERE 1=1";
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append(_query);
		if (searchKeys.get("manualId") != null && !searchKeys.get("manualId").equals("")) {
			queryBuilder.append(" and fsr.`manual_id` like ?");
			value.add("%" + searchKeys.get("manualId") + "%");
		}
		if (searchKeys.get("serviceManualId") != null && !searchKeys.get("serviceManualId").equals("")) {
			queryBuilder.append(" and serapp.`manual_id` like ?");
			value.add("%" + searchKeys.get("serviceManualId") + "%");
		}
		if (searchKeys.get("odcType") != null && !searchKeys.get("odcType").equals("")) {
			queryBuilder.append(" and fsr.`odc_type` like ?");
			value.add("%" + searchKeys.get("odcType") + "%");
		}
		return jdbc.queryForList(queryBuilder.toString(), value.toArray());

	}

}