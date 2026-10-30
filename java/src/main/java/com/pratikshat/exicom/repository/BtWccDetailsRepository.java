package com.pratikshat.exicom.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.BtWccDetails;
import com.pratikshat.exicom.entity.LoginUser;

@Repository
public interface BtWccDetailsRepository extends JpaRepository<BtWccDetails, Long>,BtWccDetailsRepositoryCustom {
		

	@Query(value="select id as id,wcc_type_name as wccTypeName, vendor_po_glcode as vendorPoGlcode from wcc_types",nativeQuery=true)
	List<Map<String,Object>> getTypeForDropdownList();
	
	@Query(value="select * from wcc_status",nativeQuery=true)
	List<Map<String,Object>> getWccStatus();
	
	@Query(value="SELECT wcc.`id` AS wccId,\r\n" + 
			"	wcc.`manual_id` AS wccManualId,\r\n" + 
			"	wcc.`wcc_type_id` AS wccTypeId\r\n" + 
			"	\r\n" + 
			"	 FROM bt_wcc_details wcc WHERE wcc.invoiced=0 AND wcc.owner_email_id=:ownerEmailId AND wcc.wcc_status_id=2",nativeQuery=true)
	List<Map<String,Object>> getPendingToInvoiceWccByUser(@Param("ownerEmailId")String userEmailId);
	
	@Query(value = "SELECT \r\n"
			+ "				wcc.`id` AS id,wcc.`manual_id` AS manulId,wcc.invoiced AS invoiced,wcc.wcc_amount AS wccAmount,wcc.wcc_tax_amount AS wccTaxAmount,\r\n"
			+ "				wcc.wcc_total_amount AS wccTotalAmount,wcc.`circle_circle_name` AS circleName,\r\n"
			+ "				act.`name` AS vendorName,act.`sap_customer_number` AS vendorSapNumber,\r\n"
			+ "				cod.`first_name` AS vendorUserFirstName,cod.`last_name` AS vendorUserLastName,\r\n"
			+ "				cod.`middle_name` AS vendorUserMiddleName,cod.`employee_code` AS vendorUserCode,\r\n"
			+ "				cod.`contact_id` AS vendorContactId,\r\n"
			+ "				lg.`legal_entity_name` AS serviceCenter,ty.`wcc_type_name` AS wccType,ty.`vendor_po_glcode` AS vendorPoGlcode,\r\n"
			+ "				ct.`case_types_name` AS caseType,cr.`case_reason_details_name` AS caseReasonDetails,\r\n"
			+ "				st.`name` AS wccStatus,wcc.`created_date` AS createdDate,cby.`email_id` AS createdByEmailId, cby.`first_name` AS createdByFirstName,\r\n"
			+ "				cby.`middle_name` AS createdByMiddleName,cby.`last_name`AS createdByLastName,\r\n"
			+ "				mby.`email_id` AS modifiedByEmailId, mby.`first_name` AS modifiedByFirstName,\r\n"
			+ "				mby.`middle_name` AS modifiedByMiddleName,mby.`last_name`AS modifiedByLastName,wcc.to_date AS toDate,wcc.from_date AS fromDate,\r\n"
			+ "				wcc.wcc_type_id AS wccTypeId,\r\n"
			+ "			(SELECT approved_date FROM bt_to_be_approved WHERE `object_name`='BtWccDetails' AND `object_id`=wcc.`id` ORDER BY id DESC LIMIT 1) AS lastActionDate\r\n"
			+ "			FROM `bt_wcc_details` wcc\r\n"
			+ "			JOIN `wcc_status` st ON st.`id`=wcc.`wcc_status_id`\r\n"
			+ "			JOIN `wcc_types` ty ON ty.`id`=wcc.`wcc_type_id`\r\n"
			+ "			JOIN `legal_entity` lg ON lg.`legal_entity_id`=wcc.`service_center_legal_entity_id`\r\n"
			+ "			LEFT JOIN `case_reason_details` cr ON cr.`reason_id` = wcc.`case_reason_reason_id`\r\n"
			+ "			LEFT JOIN `case_types` ct ON ct.`case_type_id` =wcc.`case_type_case_type_id`\r\n"
			+ "			JOIN `service_recource` sr ON sr.`user_email_id`= wcc.`owner_email_id`\r\n"
			+ "			JOIN `contact_details` cod ON sr.`contact_contact_id`=cod.`contact_id`\r\n"
			+ "			JOIN `accounts` act ON cod.`account_id`=act.`sap_customer_number`\r\n"
			+ "			JOIN `employee_details` cby ON cby.`email_id`= wcc.`created_by_email_id`\r\n"
			+ "			JOIN `employee_details` mby ON mby.`email_id`=wcc.`last_modified_by_email_id`\r\n"
			+ "			WHERE wcc.`id`=:id",nativeQuery = true)
//		LEFT JOIN `bt_to_be_approved` tobe ON tobe.`object_name`='BtWccDetails' AND tobe.`object_id`=wcc.`id` AND tobe.stage=2 AND tobe.`action`='Approved' AND tobe.`status`=1
	Map<String,Object> getSingleWcc(@Param("id") Long id);
	
	@Query(value = "SELECT \r\n"
			+ "				wcc.`id` AS id,wcc.`manual_id` AS manulId,wcc.invoiced AS invoiced,wcc.wcc_amount AS wccAmount,wcc.wcc_tax_amount AS wccTaxAmount,\r\n"
			+ "				wcc.wcc_total_amount AS wccTotalAmount,wcc.`circle_circle_name` AS circleName,\r\n"
			+ "				act.`name` AS vendorName,act.`sap_customer_number` AS vendorSapNumber,\r\n"
			+ "				cod.`first_name` AS vendorUserFirstName,cod.`last_name` AS vendorUserLastName,\r\n"
			+ "				cod.`middle_name` AS vendorUserMiddleName,cod.`employee_code` AS vendorUserCode,\r\n"
			+ "				cod.`contact_id` AS vendorContactId,\r\n"
			+ "				lg.`legal_entity_name` AS serviceCenter,ty.`wcc_type_name` AS wccType,\r\n"
			+ "				ct.`case_types_name` AS caseType,cr.`case_reason_details_name` AS caseReasonDetails,\r\n"
			+ "				st.`name` AS wccStatus,wcc.`created_date` AS createdDate,cby.`email_id` AS createdByEmailId, cby.`first_name` AS createdByFirstName,\r\n"
			+ "				cby.`middle_name` AS createdByMiddleName,cby.`last_name`AS createdByLastName,\r\n"
			+ "				mby.`email_id` AS modifiedByEmailId, mby.`first_name` AS modifiedByFirstName,\r\n"
			+ "				mby.`middle_name` AS modifiedByMiddleName,mby.`last_name`AS modifiedByLastName,wcc.to_date AS toDate,wcc.from_date AS fromDate,\r\n"
			+ "				wcc.wcc_type_id AS wccTypeId,\r\n"
			+ "			(SELECT approved_date FROM bt_to_be_approved WHERE `object_name`='BtWccDetails' AND `object_id`=wcc.`id` ORDER BY id DESC LIMIT 1) AS lastActionDate\r\n"
			+ "			FROM `bt_wcc_details` wcc\r\n"
			+ "			JOIN `wcc_status` st ON st.`id`=wcc.`wcc_status_id`\r\n"
			+ "			JOIN `wcc_types` ty ON ty.`id`=wcc.`wcc_type_id`\r\n"
			+ "			JOIN `legal_entity` lg ON lg.`legal_entity_id`=wcc.`service_center_legal_entity_id`\r\n"
			+ "			LEFT JOIN `case_reason_details` cr ON cr.`reason_id` = wcc.`case_reason_reason_id`\r\n"
			+ "			LEFT JOIN `case_types` ct ON ct.`case_type_id` =wcc.`case_type_case_type_id`\r\n"
			+ "			JOIN `service_recource` sr ON sr.`user_email_id`= wcc.`owner_email_id`\r\n"
			+ "			JOIN `contact_details` cod ON sr.`contact_contact_id`=cod.`contact_id`\r\n"
			+ "			JOIN `accounts` act ON cod.`account_id`=act.`sap_customer_number`\r\n"
			+ "			JOIN `employee_details` cby ON cby.`email_id`= wcc.`created_by_email_id`\r\n"
			+ "			JOIN `employee_details` mby ON mby.`email_id`=wcc.`last_modified_by_email_id`\r\n",nativeQuery = true,
			countQuery ="SELECT COUNT(*) AS countNumber FROM `bt_wcc_details` wcc\r\n"
					+ "JOIN `wcc_status` st ON st.`id`=wcc.`wcc_status_id`\r\n"
					+ "JOIN `wcc_types` ty ON ty.`id`=wcc.`wcc_type_id`\r\n"
					+ "JOIN `legal_entity` lg ON lg.`legal_entity_id`=wcc.`service_center_legal_entity_id`\r\n"
					+ "LEFT JOIN `case_reason_details` cr ON cr.`reason_id` = wcc.`case_reason_reason_id`\r\n"
					+ "LEFT JOIN `case_types` ct ON ct.`case_type_id` =wcc.`case_type_case_type_id`\r\n"
					+ "JOIN `service_recource` sr ON sr.`user_email_id`= wcc.`owner_email_id`\r\n"
					+ "JOIN `contact_details` cod ON sr.`contact_contact_id`=cod.`contact_id`\r\n"
					+ "JOIN `accounts` act ON cod.`account_id`=act.`sap_customer_number`\r\n"
					+ "JOIN `employee_details` cby ON cby.`email_id`= wcc.`created_by_email_id`\r\n"
					+ "JOIN `employee_details` mby ON mby.`email_id`=wcc.`last_modified_by_email_id`")
	Page<Map<String,Object>> getPage(Pageable pageable);
	
	@Query(value = "SELECT \r\n"
			+ "				btsa.`service_appointment_id` AS id,\r\n"
			+ "				btsa.`manual_id` AS appointmentNumber,\r\n"
			+ "				act.`sap_customer_number` AS customerSapNumber,\r\n"
			+ "				act.`name` AS customerName,\r\n"
			+ "				btc.`manual_id` AS caseId, \r\n"
			+ "				cty.`case_types_name` AS caseType,\r\n"
			+ "				btsa.`circle_circle_name` AS circleName,\r\n"
			+ "				sr.`name` AS serviceResource,\r\n"
			+ "				cr.`case_reason_details_name` AS caseReason,\r\n"
			+ "				btc.`case_type_case_type_id`,\r\n"
			+ "				po.id AS poDetailsId,\r\n"
			+ "				po.activity_proce_list_description AS activityProceListDescription,\r\n"
			+ "			 	po.po_number AS poNumber,\r\n"
			+ "			 	btc.close_date AS caseCloseDate,\r\n"
			+ "			 	wcs.amount AS amount,\r\n"
			+ "				wcs.tax_amount AS taxAmount,\r\n"
			+ "			 	wcs.tax_rate AS taxRate,\r\n"
			+ "			  	wcs.total_amount AS totalAmount\r\n"
			+ "	FROM `bt_service_appointment` btsa \r\n"
			+ "	JOIN `bt_case` btc ON btsa.`case_details_id`=btc.`id`\r\n"
			+ "	JOIN `bt_site_details` btsd ON btc.`site_id` = btsd.`id`\r\n"
			+ "	JOIN `accounts` act ON btsd.`account_sap_customer_number`=act.`sap_customer_number`\r\n"
			+ "	JOIN `case_reason_details` cr ON btc.`case_reason_reason_id`=cr.`reason_id`\r\n"
			+ "	JOIN `case_types` cty ON btc.`case_type_case_type_id`=cty.`case_type_id`\r\n"
			+ "	JOIN `service_recource` sr ON sr.`service_resource_id`=btsa.`service_resource_service_resource_id`\r\n"
			+ "	JOIN `service_appointment_status` srst ON srst.`status_id`=btsa.`status_status_id`\r\n"
			+ "	JOIN `bt_wcc_service_appointment` wcs ON wcs.`service_appointment_service_appointment_id`=btsa.`service_appointment_id`\r\n"
			+ "	JOIN `bt_vendor_po_details` po ON wcs.`po_details_id`=po.`id`"
			+ "WHERE wcs.`wcc_id`=:id",nativeQuery = true)
	List<Map<String,Object>> getServiceAppointmentByWccId(@Param("id") Long id);
	
	
	@Query(value="SELECT	wcc.`id` AS id,wcc.`manual_id` AS manulId,wcc.invoiced AS invoiced,wcc.`wcc_amount` AS wccAmount,wcc.`wcc_tax_amount` AS wccTaxAmount,\r\n"
			+ "				wcc.`wcc_total_amount` AS wccTotalAmount,wcc.`circle_circle_name` AS circleName,\r\n"
			+ "				act.`name` AS vendorName,act.`sap_customer_number` AS vendorSapNumber,\r\n"
			+ "				cod.`first_name` AS vendorUserFirstName,cod.`last_name` AS vendorUserLastName,\r\n"
			+ "				cod.`middle_name` AS vendorUserMiddleName,cod.`employee_code` AS vendorUserCode,\r\n"
			+ "				cod.`contact_id` AS vendorContactId,\r\n"
			+ "				lg.`legal_entity_name` AS serviceCenter,ty.`wcc_type_name` AS wccType,\r\n"
			+ "				ct.`case_types_name` AS caseType,cr.`case_reason_details_name` AS caseReasonDetails,\r\n"
			+ "				st.`name` AS wccStatus,wcc.`created_date` AS createdDate,cby.`email_id` AS createdByEmailId, cby.`first_name` AS createdByFirstName,\r\n"
			+ "				cby.`middle_name` AS createdByMiddleName,cby.`last_name`AS createdByLastName,\r\n"
			+ "				mby.`email_id` AS modifiedByEmailId, mby.`first_name` AS modifiedByFirstName,\r\n"
			+ "				mby.`middle_name` AS modifiedByMiddleName,mby.`last_name`AS modifiedByLastName,wcc.to_date AS toDate,wcc.from_date AS fromDate,\r\n"
			+ "				wcc.wcc_type_id AS wccTypeId,\r\n"
			+ "				(SELECT approved_date FROM bt_to_be_approved WHERE `object_name`='BtWccDetails' AND `object_id`=wcc.`id` ORDER BY id DESC LIMIT 1) AS lastActionDate\r\n"
			+ "				\r\n"
			+ "			FROM `bt_wcc_details` wcc\r\n"
			+ "			JOIN `wcc_status` st ON st.`id`=wcc.`wcc_status_id`\r\n"
			+ "			JOIN `wcc_types` ty ON ty.`id`=wcc.`wcc_type_id`\r\n"
			+ "			JOIN `legal_entity` lg ON lg.`legal_entity_id`=wcc.`service_center_legal_entity_id`\r\n"
			+ "			LEFT JOIN `case_reason_details` cr ON cr.`reason_id` = wcc.`case_reason_reason_id`\r\n"
			+ "			LEFT JOIN `case_types` ct ON ct.`case_type_id` =wcc.`case_type_case_type_id`\r\n"
			+ "			JOIN `service_recource` sr ON sr.`user_email_id`= wcc.`owner_email_id`\r\n"
			+ "			JOIN `contact_details` cod ON sr.`contact_contact_id`=cod.`contact_id`\r\n"
			+ "			JOIN `accounts` act ON cod.`account_id`=act.`sap_customer_number`\r\n"
			+ "			JOIN `employee_details` cby ON cby.`email_id`= wcc.`created_by_email_id`\r\n"
			+ "			JOIN `employee_details` mby ON mby.`email_id`=wcc.`last_modified_by_email_id`\r\n"
			+ "         WHERE  wcc.`wcc_status_id`=:statusId "
						,countQuery = "SELECT 	COUNT(*)\r\n" + 
					"	FROM `bt_wcc_details` wcc\r\n"
					+ "			JOIN `wcc_status` st ON st.`id`=wcc.`wcc_status_id`\r\n"
					+ "			JOIN `wcc_types` ty ON ty.`id`=wcc.`wcc_type_id`\r\n"
					+ "			JOIN `legal_entity` lg ON lg.`legal_entity_id`=wcc.`service_center_legal_entity_id`\r\n"
					+ "			LEFT JOIN `case_reason_details` cr ON cr.`reason_id` = wcc.`case_reason_reason_id`\r\n"
					+ "			LEFT JOIN `case_types` ct ON ct.`case_type_id` =wcc.`case_type_case_type_id`\r\n"
					+ "			JOIN `service_recource` sr ON sr.`user_email_id`= wcc.`owner_email_id`\r\n"
					+ "			JOIN `contact_details` cod ON sr.`contact_contact_id`=cod.`contact_id`\r\n"
					+ "			JOIN `accounts` act ON cod.`account_id`=act.`sap_customer_number`\r\n"
					+ "			JOIN `employee_details` cby ON cby.`email_id`= wcc.`created_by_email_id`\r\n"
					+ "			JOIN `employee_details` mby ON mby.`email_id`=wcc.`last_modified_by_email_id`\r\n"
					+ "         WHERE  wcc.`wcc_status_id`=:statusId ",nativeQuery=true)
	Page<Map<String,Object>> getAll(@Param("statusId")int statusId,Pageable page);
	
	
	@Query(value = "SELECT	wcc.`id` AS id,wcc.`manual_id` AS manulId,wcc.invoiced AS invoiced,wcc.`wcc_amount` AS wccAmount,wcc.`wcc_tax_amount` AS wccTaxAmount,\r\n"
			+ "				wcc.`wcc_total_amount` AS wccTotalAmount,wcc.`circle_circle_name` AS circleName,\r\n"
			+ "				act.`name` AS vendorName,act.`sap_customer_number` AS vendorSapNumber,\r\n"
			+ "				cod.`first_name` AS vendorUserFirstName,cod.`last_name` AS vendorUserLastName,\r\n"
			+ "				cod.`middle_name` AS vendorUserMiddleName,cod.`employee_code` AS vendorUserCode,\r\n"
			+ "				cod.`contact_id` AS vendorContactId,\r\n"
			+ "				lg.`legal_entity_name` AS serviceCenter,ty.`wcc_type_name` AS wccType,\r\n"
			+ "				ct.`case_types_name` AS caseType,cr.`case_reason_details_name` AS caseReasonDetails,\r\n"
			+ "				st.`name` AS wccStatus,wcc.`created_date` AS createdDate,cby.`email_id` AS createdByEmailId, cby.`first_name` AS createdByFirstName,\r\n"
			+ "				cby.`middle_name` AS createdByMiddleName,cby.`last_name`AS createdByLastName,\r\n"
			+ "				mby.`email_id` AS modifiedByEmailId, mby.`first_name` AS modifiedByFirstName,\r\n"
			+ "				mby.`middle_name` AS modifiedByMiddleName,mby.`last_name`AS modifiedByLastName,wcc.to_date AS toDate,wcc.from_date AS fromDate,\r\n"
			+ "				wcc.wcc_type_id AS wccTypeId,\r\n"
			+ "				(SELECT approved_date FROM bt_to_be_approved WHERE `object_name`='BtWccDetails' AND `object_id`=wcc.`id` ORDER BY id DESC LIMIT 1) AS lastActionDate\r\n"
			+ "				\r\n"
			+ "			FROM `bt_wcc_details` wcc\r\n"
			+ "			JOIN `wcc_status` st ON st.`id`=wcc.`wcc_status_id`\r\n"
			+ "			JOIN `wcc_types` ty ON ty.`id`=wcc.`wcc_type_id`\r\n"
			+ "			JOIN `legal_entity` lg ON lg.`legal_entity_id`=wcc.`service_center_legal_entity_id`\r\n"
			+ "			LEFT JOIN `case_reason_details` cr ON cr.`reason_id` = wcc.`case_reason_reason_id`\r\n"
			+ "			LEFT JOIN `case_types` ct ON ct.`case_type_id` =wcc.`case_type_case_type_id`\r\n"
			+ "			JOIN `service_recource` sr ON sr.`user_email_id`= wcc.`owner_email_id`\r\n"
			+ "			JOIN `contact_details` cod ON sr.`contact_contact_id`=cod.`contact_id`\r\n"
			+ "			JOIN `accounts` act ON cod.`account_id`=act.`sap_customer_number`\r\n"
			+ "			JOIN `employee_details` cby ON cby.`email_id`= wcc.`created_by_email_id`\r\n"
			+ "			JOIN `employee_details` mby ON mby.`email_id`=wcc.`last_modified_by_email_id`\r\n"
//			+ "			LEFT JOIN `bt_to_be_approved` tobe ON tobe.`object_name`='BtWccDetails' AND tobe.`object_id`=wcc.`id` AND tobe.`action`='Approved' AND tobe.`status`=1"
			+ "         WHERE sr.`user_email_id` in (:userEmailId) and wcc.`wcc_status_id`=:statusId",nativeQuery = true,
					countQuery = "SELECT COUNT(*) FROM `bt_wcc_details` wcc\r\n"
					+ "			JOIN `wcc_status` st ON st.`id`=wcc.`wcc_status_id`\r\n"
					+ "			JOIN `wcc_types` ty ON ty.`id`=wcc.`wcc_type_id`\r\n"
					+ "			JOIN `legal_entity` lg ON lg.`legal_entity_id`=wcc.`service_center_legal_entity_id`\r\n"
					+ "			LEFT JOIN `case_reason_details` cr ON cr.`reason_id` = wcc.`case_reason_reason_id`\r\n"
					+ "			LEFT JOIN `case_types` ct ON ct.`case_type_id` =wcc.`case_type_case_type_id`\r\n"
					+ "			JOIN `service_recource` sr ON sr.`user_email_id`= wcc.`owner_email_id`\r\n"
					+ "			JOIN `contact_details` cod ON sr.`contact_contact_id`=cod.`contact_id`\r\n"
					+ "			JOIN `accounts` act ON cod.`account_id`=act.`sap_customer_number`\r\n"
					+ "			JOIN `employee_details` cby ON cby.`email_id`= wcc.`created_by_email_id`\r\n"
					+ "			JOIN `employee_details` mby ON mby.`email_id`=wcc.`last_modified_by_email_id`\r\n"
//					+ "			LEFT JOIN `bt_to_be_approved` tobe ON tobe.`object_name`='BtWccDetails' AND tobe.`object_id`=wcc.`id` AND tobe.`action`='Approved' AND tobe.`status`=1"
					+ "         WHERE sr.`user_email_id` in (:userEmailId) and wcc.`wcc_status_id`=:statusId")
	Page<Map<String,Object>> getAllWccByUserId(@Param("userEmailId")List<String> userEmailId,@Param("statusId")int statusId,Pageable page);

	
	@Query(value = "SELECT	wcc.`id` AS id,wcc.`manual_id` AS manulId,wcc.invoiced AS invoiced,wcc.`wcc_amount` AS wccAmount,wcc.`wcc_tax_amount` AS wccTaxAmount,\r\n"
			+ "				wcc.`wcc_total_amount` AS wccTotalAmount,wcc.`circle_circle_name` AS circleName,\r\n"
			+ "				act.`name` AS vendorName,act.`sap_customer_number` AS vendorSapNumber,\r\n"
			+ "				cod.`first_name` AS vendorUserFirstName,cod.`last_name` AS vendorUserLastName,\r\n"
			+ "				cod.`middle_name` AS vendorUserMiddleName,cod.`employee_code` AS vendorUserCode,\r\n"
			+ "				cod.`contact_id` AS vendorContactId,\r\n"
			+ "				lg.`legal_entity_name` AS serviceCenter,ty.`wcc_type_name` AS wccType,\r\n"
			+ "				ct.`case_types_name` AS caseType,cr.`case_reason_details_name` AS caseReasonDetails,\r\n"
			+ "				st.`name` AS wccStatus,wcc.`created_date` AS createdDate,cby.`email_id` AS createdByEmailId, cby.`first_name` AS createdByFirstName,\r\n"
			+ "				cby.`middle_name` AS createdByMiddleName,cby.`last_name`AS createdByLastName,\r\n"
			+ "				mby.`email_id` AS modifiedByEmailId, mby.`first_name` AS modifiedByFirstName,\r\n"
			+ "				mby.`middle_name` AS modifiedByMiddleName,mby.`last_name`AS modifiedByLastName,wcc.to_date AS toDate,wcc.from_date AS fromDate,\r\n"
			+ "				wcc.wcc_type_id AS wccTypeId,\r\n"
			+ "				(SELECT approved_date FROM bt_to_be_approved WHERE `object_name`='BtWccDetails' AND `object_id`=wcc.`id` ORDER BY id DESC LIMIT 1) AS lastActionDate\r\n"
			+ "				\r\n"
			+ "			FROM `bt_wcc_details` wcc\r\n"
			+ "			JOIN `wcc_status` st ON st.`id`=wcc.`wcc_status_id`\r\n"
			+ "			JOIN `wcc_types` ty ON ty.`id`=wcc.`wcc_type_id`\r\n"
			+ "			JOIN `legal_entity` lg ON lg.`legal_entity_id`=wcc.`service_center_legal_entity_id`\r\n"
			+ "			LEFT JOIN `case_reason_details` cr ON cr.`reason_id` = wcc.`case_reason_reason_id`\r\n"
			+ "			LEFT JOIN `case_types` ct ON ct.`case_type_id` =wcc.`case_type_case_type_id`\r\n"
			+ "			JOIN `service_recource` sr ON sr.`user_email_id`= wcc.`owner_email_id`\r\n"
			+ "			JOIN `contact_details` cod ON sr.`contact_contact_id`=cod.`contact_id`\r\n"
			+ "			JOIN `accounts` act ON cod.`account_id`=act.`sap_customer_number`\r\n"
			+ "			JOIN `employee_details` cby ON cby.`email_id`= wcc.`created_by_email_id`\r\n"
			+ "			JOIN `employee_details` mby ON mby.`email_id`=wcc.`last_modified_by_email_id`\r\n"
			+ "         WHERE sr.`user_email_id` IN (:userEmailId)",nativeQuery = true,
					countQuery = "SELECT COUNT(*) FROM `bt_wcc_details` wcc\r\n"
					+ "			JOIN `wcc_status` st ON st.`id`=wcc.`wcc_status_id`\r\n"
					+ "			JOIN `wcc_types` ty ON ty.`id`=wcc.`wcc_type_id`\r\n"
					+ "			JOIN `legal_entity` lg ON lg.`legal_entity_id`=wcc.`service_center_legal_entity_id`\r\n"
					+ "			LEFT JOIN `case_reason_details` cr ON cr.`reason_id` = wcc.`case_reason_reason_id`\r\n"
					+ "			LEFT JOIN `case_types` ct ON ct.`case_type_id` =wcc.`case_type_case_type_id`\r\n"
					+ "			JOIN `service_recource` sr ON sr.`user_email_id`= wcc.`owner_email_id`\r\n"
					+ "			JOIN `contact_details` cod ON sr.`contact_contact_id`=cod.`contact_id`\r\n"
					+ "			JOIN `accounts` act ON cod.`account_id`=act.`sap_customer_number`\r\n"
					+ "			JOIN `employee_details` cby ON cby.`email_id`= wcc.`created_by_email_id`\r\n"
					+ "			JOIN `employee_details` mby ON mby.`email_id`=wcc.`last_modified_by_email_id`\r\n"
					+ "         WHERE sr.`user_email_id` IN (:userEmailId)")
	Page<Map<String,Object>> getAllWccByUserId(@Param("userEmailId")List<String> userEmailId,Pageable page);
	
	@Query(value="	SELECT \r\n"
			+ "		sa.`service_appointment_id`,\r\n"
			+ "		sa.`manual_id` AS appointmentNumber,\r\n"
			+ "		act.`sap_customer_number` AS customerSapNumber,\r\n"
			+ "		act.`name` AS customerName,\r\n"
			+ "		cs.`manual_id` AS caseId,\r\n"
			+ "		cty.`case_types_name` AS caseType,\r\n"
			+ "		sa.`circle_circle_name` AS circleName,\r\n"
			+ "		sr.`name` AS serviceResource,\r\n"
			+ "		cr.`case_reason_details_name` AS caseReason,\r\n"
			+ "		cs.`close_date` AS caseCloseDate\r\n"
			+ "				\r\n"
			+ "		FROM `bt_service_appointment` sa \r\n"
			+ "		JOIN `bt_case` cs ON sa.`case_details_id`=cs.`id`\r\n"
			+ "		JOIN `bt_site_details` st ON cs.`site_id`=st.`id`\r\n"
			+ "		JOIN `accounts` act ON st.`account_sap_customer_number`=act.`sap_customer_number`\r\n"
			+ "		JOIN `case_reason_details` cr ON cs.`case_reason_reason_id`=cr.`reason_id`\r\n"
			+ "		JOIN `case_types` cty ON cs.`case_type_case_type_id`=cty.`case_type_id`\r\n"
			+ "		JOIN `service_recource` sr ON sr.`service_resource_id`=sa.`service_resource_service_resource_id`\r\n"
			+ "		JOIN `service_appointment_status` srst ON srst.`status_id`=sa.`status_status_id`" + 
			"	WHERE sa.wcc=FALSE AND sr.`user_email_id`=:username AND cr.`wcc_type_id`=:wccTypeId and sa.`claim_status_claim_status_id`=3 and date(cs.close_date) between date(:datefrom) and date(:dateto)",nativeQuery=true)
	List<Map<String,Object>> getPendingServiceAppointment(@Param("username")String userEmailId,@Param("wccTypeId")long wccTypeId,@Param("datefrom")java.util.Date datefrom,@Param("dateto")java.util.Date dateto);

	
	

}