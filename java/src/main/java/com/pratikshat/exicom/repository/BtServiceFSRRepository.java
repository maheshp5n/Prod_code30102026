package com.pratikshat.exicom.repository;

import java.util.Map;import javax.annotation.ParametersAreNonnullByDefault;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.BtAssetMasterUpdate;
import com.pratikshat.exicom.entity.BtCoolingMethod;
import com.pratikshat.exicom.entity.BtFaultyAssetParameters;
import com.pratikshat.exicom.entity.BtFsr;
import com.pratikshat.exicom.entity.BtPendingReasonCustomerBucket;
import com.pratikshat.exicom.entity.BtPowerPlantMake;
import com.pratikshat.exicom.entity.BtResolutionMethod;
import com.pratikshat.exicom.entity.BtServiceAppointment;
import com.pratikshat.exicom.entity.BtServiceFSR;
import com.pratikshat.exicom.entity.BtSiteEnvironmentalConditions;
import com.pratikshat.exicom.entity.BtSiteType;
import com.pratikshat.exicom.entity.BtSolutionImparted;
import com.pratikshat.exicom.model.response.BtServiceFSRResponse;

@Repository
public interface BtServiceFSRRepository extends JpaRepository<BtFsr, Long>,BtServiceFSRRepositoryCustom {
	
	

	
//	@Query(value = ""
//			+ "WHERE fsr.`id`=:id",nativeQuery = true)
//	Map<String,Object> getSingleServiceFSR(@Param("id") Long id);
	
	@Query("select new com.pratikshat.exicom.model.response.BtServiceFSRResponse(fsr) from BtFsr fsr left join fsr.btServiceAppointment sa left join fsr.btSiteType siteTpe"
			+ "left join fsr.btSiteEnvironmentalConditions ser left join fsr.btCoolingMethod cm left join fsr.btPowerPlantMake ppm left join fsr.btPendingReasonCustomerBucket prcb"
			+ "left join fsr.btSolutionImparted si where fsr.id=:id")
	BtServiceFSRResponse getByServiceFSRId(@Param("id") long id);
	
	@Query(value = "SELECT fsr.`id` AS id,fsr.`customer_name` AS customerName,fsr.`cmp` AS CMP,fsr.`date_of_sitevisit` AS dateOfSiteVisit,\r\n"
			+ "	ca.`manual_id` AS caseNo,ca.`created_date` AS dateOfCaseGenerated,\r\n"
			+ "	ser.`manual_id` AS serviceAppointmentId,\r\n"
			+ "	site.`site_name` AS siteName,site.`manual_id` AS siteId,\r\n"
			+ "	circle.`circle_name` AS circleName,\r\n"
			+ "	cty.`email_id` AS caseLoggedBy,contact.`mobile` AS contactNo,\r\n"
			+ "	fsr.`fault_reported_by_customer` AS faultReportedByCustomer,\r\n"
			+ "	siteType.`type_name` AS siteTye, fsr.`earthing_status` AS earthingStatus,fsr.`ambient_temperature`\r\n"
			+ "	AS ambientTemperature, fsr.`total_equipment_dc_load` AS totalEquipmentDcLoad,fsr.`smps_to_cbms_vol_gap` AS smpsToCbmsVolGap,\r\n"
			+ "	fsr.`site_environment_condition` AS siteEnvironmentCondition, fsr.`cooling_method` AS coolingMethod,\r\n"
			+ "	fsr.`power_plant_make` AS powerPlantmake, fsr.`active_rectifier` AS activeRectifier, fsr.`cbms_to_bat_vol_gap` AS cbmsToBatVolGap,\r\n"
			+ "	fsr.`cbmssno` AS cbmsSNo, fsr.`batterysno1` AS batterySno_1,fsr.`batterysno2` AS batterySno_2,\r\n"
			+ "	fsr.`batterysno3` AS batterySno_3,fsr.`batterysno4` AS batterySno_4,\r\n"
			+ "	fsr.`model_no` AS modelNo,fsr.`equipment_sr_no` AS equipmentSNo, fsr.`software_version` AS softwareversion,\r\n"
			+ "	fsr.`warrenty_status` AS warrentyStatus,fsr.`installation_date` AS installtionDate,fsr.`current_condition` AS currentCondition,\r\n"
			+ "	fsr.`vol` AS vol, fsr.`soc` AS soc, fsr.`soh`AS soh,fsr.`active_alarm` AS activeAlarm, fsr.`problem_since` AS problemSince,\r\n"
			+ "	fsr.`min_cell_voltage` AS minCellVoltage, fsr.`max_cell_voltage` AS maxCellVoltage,fsr.`vol_gap` AS volGap,\r\n"
			+ "	fsr.`battery_temp` AS batteryTemp, fsr.`last_cycle_del` AS lastCycleDel, fsr.`log_status` AS logStatus,\r\n"
			+ "	fsr.`battery_requrid_at_brc` AS batteryRequidAtBrc,\r\n"
			+ "	fsr.`fault_observed_by_exicom_engineer` AS faultObservedByExicomEngineer, fsr.`corrective_actions_in_detail` AS correctiveActions,\r\n"
			+ "	fsr.`resolution_method` AS resolutionMethod, fsr.`solution_imparted` AS solutionImparted,\r\n"
			+ "	caseStatus.`case_status_name` AS caseStatus, fsr.`pending_bucket` AS pendingBucket, fsr.`pending_reason` AS pendingReason,\r\n"
			+ "	sr.`name` AS engName ,src.`mobile` AS engMobileNo,createBy.`email_id` AS createdBy,mBy.`email_id` AS modifiedBy,\r\n"
			+ "	fsr.`created_date` AS createdDate, fsr.`last_modified_date` AS modifiedDate\r\n"
			+ "	\r\n"
			+ "	\r\n"
			+ "\r\n"
			+ "FROM `bt_servicefsr` fsr\r\n"
			+ "JOIN `bt_service_appointment` ser ON ser.`service_appointment_id`= fsr.`bt_service_appointment_service_appointment_id`\r\n"
			+ "JOIN `bt_case` ca ON ca.`id`=fsr.`bt_case_id`\r\n"
			+ "JOIN`bt_site_details` site ON site.`id`= fsr.`bt_site_id`\r\n"
			+ "LEFT JOIN `circle_details` circle ON circle.`circle_name`=site.`circle_circle_name`\r\n"
			+ "JOIN `employee_details` cty ON cty.`email_id` = ca.`created_by_email_id`\r\n"
			+ "JOIN `contact_details` contact ON contact.`contact_id`=cty.`contact_contact_id`\r\n"
			+ "JOIN `bt_site_type` siteType ON siteType.`id`=fsr.`site_type_id`\r\n"
			+ "JOIN `case_status_details` caseStatus ON caseStatus.`id` =ca.`status_id`\r\n"
			+ "JOIN `service_recource` sr ON sr.`service_resource_id` =fsr.`engineer_service_resource_id`\r\n"
			+ "JOIN `contact_details` src ON src.`contact_id` =sr.`contact_contact_id`\r\n"
			+ "JOIN `employee_details` createBy ON createBy.`email_id` = fsr.`created_by_email_id`\r\n"
			+ "JOIN `employee_details` mBy ON mBy.`email_id` = fsr.`last_modified_by_email_id`\r\n"
			+ "",nativeQuery = true,
			countQuery ="SELECT COUNT(*) AS countNumber FROM `bt_servicefsr` fsr\r\n"
					+ "JOIN `bt_service_appointment` ser ON ser.`service_appointment_id`= fsr.`bt_service_appointment_service_appointment_id`\r\n"
					+ "JOIN `bt_case` ca ON ca.`id`=fsr.`bt_case_id`\r\n"
					+ "JOIN`bt_site_details` site ON site.`id`= fsr.`bt_site_id`\r\n"
					+ "LEFT JOIN `circle_details` circle ON circle.`circle_name`=site.`circle_circle_name`\r\n"
					+ "JOIN `employee_details` cty ON cty.`email_id` = ca.`created_by_email_id`\r\n"
					+ "JOIN `contact_details` contact ON contact.`contact_id`=cty.`contact_contact_id`\r\n"
					+ "JOIN `bt_site_type` siteType ON siteType.`id`=fsr.`site_type_id`\r\n"
					+ "JOIN `case_status_details` caseStatus ON caseStatus.`id` =ca.`status_id`\r\n"
					+ "JOIN `service_recource` sr ON sr.`service_resource_id` =fsr.`engineer_service_resource_id`\r\n"
					+ "JOIN `contact_details` src ON src.`contact_id` =sr.`contact_contact_id`\r\n"
					+ "JOIN `employee_details` createBy ON createBy.`email_id` = fsr.`created_by_email_id`\r\n"
					+ "JOIN `employee_details` mBy ON mBy.`email_id` = fsr.`last_modified_by_email_id`")
	Page<Map<String,Object>> getPage(Pageable pageable);
	
	Long findByBtServiceAppointment(BtServiceAppointment id);
	

}
