package com.pratikshat.exicom.repository;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.pratikshat.exicom.entity.BtFsrBatteryCommunication;

@Repository
public interface BtFsrBatteryCommunicationRepository extends JpaRepository<BtFsrBatteryCommunication, Long>,BtFsrBatteryCommunicationRepositoryCustom{

	@Query(value="SELECT fsr.`id` AS id,fsr.`manuald` AS manualId,  fsr.`batterysno1` AS batterySno_1, fsr.`batterysno2` AS batterySno_2 ,fsr.`batterysno3` AS batterySno_3,\r\n"
			+ "       fsr.`batterysno4` AS batterySno_4,fsr.`batterysno5` AS batterySno_5, fsr.`batterysno6` AS batterySno_6,\r\n"
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
			+ "FROM `bt_fsr_battery_communication` fsr\r\n"
			+ "JOIN `bt_service_appointment` serapp ON serapp.`service_appointment_id`=fsr.`service_appointment_service_appointment_id`\r\n"
			+ "JOIN `bt_site_status` sta ON sta.`id` = fsr.`site_status_id`\r\n"
			+ "JOIN `bt_site_type` st ON st.`id`= fsr.`site_type_id`\r\n"
			+ "JOIN `location` war ON war.`location_id`=fsr.`warehouse_location_id`\r\n"
			+ "JOIN `bt_case` ca ON ca.`id`=fsr.`bt_case_id`"
			+" WHERE fsr.id=:id",nativeQuery = true)
	Map<String,Object> getSingleFsr(@Param("id") Long id);
	
	@Query(value="SELECT fsr.`id` AS id,fsr.`manuald` AS manualId,  fsr.`batterysno1` AS batterySno_1, fsr.`batterysno2` AS batterySno_2 ,fsr.`batterysno3` AS batterySno_3,\r\n"
			+ "       fsr.`batterysno4` AS batterySno_4,fsr.`batterysno5` AS batterySno_5, fsr.`batterysno6` AS batterySno_6,\r\n"
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
			+ "JOIN `bt_case` ca ON ca.`id`=fsr.`bt_case_id`"
			,nativeQuery = true,countQuery = "SELECT COUNT(*) AS countNumber \r\n"
					+ "FROM `bt_fsr_battery_communication` fsr\r\n"
					+ "JOIN `bt_service_appointment` serapp ON serapp.`service_appointment_id`=fsr.`service_appointment_service_appointment_id`\r\n"
					+ "JOIN `bt_site_status` sta ON sta.`id` = fsr.`site_status_id`\r\n"
					+ "JOIN `bt_site_type` st ON st.`id`= fsr.`site_type_id`\r\n"
					+ "JOIN `location` war ON war.`location_id`=fsr.`warehouse_location_id`\r\n"
					+ "JOIN `bt_case` ca ON ca.`id`=fsr.`bt_case_id`")
	Page<Map<String,Object>> getPage(Pageable pageable);
}
