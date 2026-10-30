package com.pratikshat.exicom.repository;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.BtFsrOdcKitting;

@Repository
public interface BtFsrOdcKittingRepository extends JpaRepository<BtFsrOdcKitting, Long>,BtFsrOdcKittingRepositoryCustom{
	
	@Query(value="SELECT fsr.`id` AS id, fsr.`manual_id` AS manualId, fsr.`odc_serial_no` AS odcSerialNo , fsr.`odc_type` AS odcType, fsr.`smps_serial_no` AS smpsSerialNo,\r\n"
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
			+ "		FROM `bt_fsr_odc_kitting` fsr\r\n"
			+ "			JOIN `bt_service_appointment` serapp ON serapp.`service_appointment_id`=fsr.`bt_service_appointment_service_appointment_id`\r\n"
			+ "			JOIN `bt_case` ca ON ca.`id`=fsr.`bt_case_id`\r\n"
			+ "			JOIN `bt_site_details` site ON site.`id`=ca.`site_id`\r\n"
			+ "			JOIN `location` lo ON lo.`location_id`=fsr.`warehouse_location_id`"
			+ "\r\n"
			+" WHERE fsr.id=:id",nativeQuery = true)
	Map<String,Object> getSingleCase(@Param("id") Long id);
	
	@Query(value="SELECT fsr.`id` AS id, fsr.`manual_id` AS manualId, fsr.`odc_serial_no` AS odcSerialNo , fsr.`odc_type` AS odcType, fsr.`smps_serial_no` AS smpsSerialNo,\r\n"
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
			+ "	FROM `bt_fsr_odc_kitting` fsr\r\n"
			+ "			JOIN `bt_service_appointment` serapp ON serapp.`service_appointment_id`=fsr.`bt_service_appointment_service_appointment_id`\r\n"
			+ "			JOIN `bt_case` ca ON ca.`id`=fsr.`bt_case_id`\r\n"
			+ "			JOIN `bt_site_details` site ON site.`id`=ca.`site_id`\r\n"
			+ "			JOIN `location` lo ON lo.`location_id`=fsr.`warehouse_location_id`"
			,nativeQuery = true,countQuery = "SELECT COUNT(*) AS countNumber \r\n"
					+ "	FROM `bt_fsr_odc_kitting` fsr\r\n"
					+ "			JOIN `bt_service_appointment` serapp ON serapp.`service_appointment_id`=fsr.`bt_service_appointment_service_appointment_id`\r\n"
					+ "			JOIN `bt_case` ca ON ca.`id`=fsr.`bt_case_id`\r\n"
					+ "			JOIN `bt_site_details` site ON site.`id`=ca.`site_id`\r\n"
					+ "			JOIN `location` lo ON lo.`location_id`=fsr.`warehouse_location_id`")
	Page<Map<String,Object>> getPage(Pageable pageable);


}