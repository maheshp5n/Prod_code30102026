package com.pratikshat.exicom.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.BtCase;
import com.pratikshat.exicom.entity.BtCustomerWcc;
import com.pratikshat.exicom.entity.BtCustomerWccCase;
import com.pratikshat.exicom.entity.BtCustomerWccReject;

@Repository
public interface BtCustomerWccRepository extends JpaRepository<BtCustomerWcc, Long>,BtCustomerWccRepositoryCustom{

	BtCustomerWcc findById(long id);
	
	@Query(value = "SELECT wcc.`id` AS id, wcc.`manual_id` AS manualId,wcc.`created_date` AS createdDate, wcc.`last_modified_date` AS modifiedDate, \r\n"
			+ "			 				CONCAT(IFNULL(emp.first_name,'') ,' ', IFNULL(emp.middle_name, ' '),' ', IFNULL(emp.last_name,'') ) AS `createdBy`, emp.email_id AS `createdByEmailId`, \r\n"
			+ "			 				CONCAT(IFNULL(lemp.first_name,'') ,' ', IFNULL(lemp.middle_name, ' '),' ', IFNULL(lemp.last_name,'') ) AS `lastModifiedBy`, lemp.email_id AS `lastModifiedByEmailId`, \r\n"
			+ "			 						wcc.`customer` AS customer,wcc.`jms_no` AS JmsNo, wcc.submitted_by_sch_date, wcc.`circle_circle_name` AS circle,cr.`reason_id` AS reasonId, \r\n"
			+ "			 						cr.`case_reason_details_name` AS reasonName ,ct.`case_type_id` AS caseTypeId, ct.`case_types_name` AS caseTypeName,  \r\n"
			+ "								wcc.`grn_number` AS `grnNumber`, wcc.`grn_date` AS `grnDate`,\r\n"
			+ "			 					wcc.`approval_status` AS `approvalStatus`,wcc.approval_date AS `approvalDate`, \r\n"
			+ "			 					wcr.`wcc_customer_reject_list` AS `wccRejectReason`,wcr.`id` AS `wccRejectReasonId`,st.status AS `wccStatus`,st.id AS `wccStatusId`,wcc.`customer_po_number` AS `customerPoNumber`,wcc.`customer_po_date` AS `customerPoDate`, \r\n"
			+ "			 					wcc.`podnumber` AS `podnumber`,wcc.`poddate` AS `poddate`, wcc.`exicom_invoice_number`, wcc.`exicom_invoice_date`,wcc.`other_rejection_reason`,\r\n"
			+ "								wcc.`total_charges` AS `totalCharges`, wcc.`total_customer_spare_charges` AS `customerBillingRate`, \r\n"
			+ "								wcc.`total_site_visit_charge` AS `customerRate`, \r\n"
			+ "			 					wcc.`sapinvoice_reference_number`\r\n"
			+ "			 						 FROM `bt_customer_wcc` wcc  \r\n"
			+ "			 						LEFT JOIN `case_reason_details` cr ON cr.`reason_id` =wcc.`case_reason_reason_id` \r\n"
			+ "			 						LEFT JOIN `case_types` ct ON ct.`case_type_id`=wcc.`casetype_case_type_id` \r\n"
			+ "			 						LEFT JOIN `circle_details` cd ON cd.`circle_name`=wcc.`circle_circle_name`\r\n"
			+ "			 						LEFT JOIN `bt_customer_wcc_reject` wcr ON wcr.id=wcc.reject_reason_id \r\n"
			+ "			 						LEFT JOIN `bt_wcc_custom_status` st ON st.id=wcc.status_id \r\n"
			+ "			 						 JOIN `employee_details` emp ON emp.`email_id`=wcc.`created_by_email_id` \r\n"
			+ "			 				JOIN `employee_details` lemp ON lemp.`email_id`=wcc.`last_modified_by_email_id`",countQuery = "SELECT COUNT(*) \r\n"
					+ "			 						 FROM `bt_customer_wcc` wcc  \r\n"
					+ "			 						LEFT JOIN `case_reason_details` cr ON cr.`reason_id` =wcc.`case_reason_reason_id` \r\n"
					+ "			 						LEFT JOIN `case_types` ct ON ct.`case_type_id`=wcc.`casetype_case_type_id` \r\n"
					+ "			 						LEFT JOIN `circle_details` cd ON cd.`circle_name`=wcc.`circle_circle_name`\r\n"
					+ "			 						LEFT JOIN `bt_customer_wcc_reject` wcr ON wcr.id=wcc.reject_reason_id \r\n"
					+ "			 						LEFT JOIN `bt_wcc_custom_status` st ON st.id=wcc.status_id \r\n"
					+ "			 						 JOIN `employee_details` emp ON emp.`email_id`=wcc.`created_by_email_id` \r\n"
					+ "			 				JOIN `employee_details` lemp ON lemp.`email_id`=wcc.`last_modified_by_email_id`", nativeQuery=true)
	Page<Map<String, Object>> getWccCustomer(Pageable pageable);
	
	@Query(value = "SELECT wcc.`id` AS id, wcc.`manual_id` AS manualId,wcc.`created_date` AS createdDate, wcc.`last_modified_date` AS modifiedDate,  \r\n"
			+ "			 			 				CONCAT(IFNULL(emp.first_name,'') ,' ', IFNULL(emp.middle_name, ' '),' ', IFNULL(emp.last_name,'') ) AS `createdBy`, emp.email_id AS `createdByEmailId`,  \r\n"
			+ "			 			 				CONCAT(IFNULL(lemp.first_name,'') ,' ', IFNULL(lemp.middle_name, ' '),' ', IFNULL(lemp.last_name,'') ) AS `lastModifiedBy`, lemp.email_id AS `lastModifiedByEmailId`,  \r\n"
			+ "			 			 						wcc.`customer` AS customer,wcc.`jms_no` AS JmsNo, wcc.submitted_by_sch_date, wcc.`circle_circle_name` AS circle,cr.`reason_id` AS reasonId,  \r\n"
			+ "			 			 						cr.`case_reason_details_name` AS reasonName ,ct.`case_type_id` AS caseTypeId, ct.`case_types_name` AS caseTypeName,   \r\n"
			+ "			 								wcc.`grn_number` AS `grnNumber`, wcc.`grn_date` AS `grnDate`, \r\n"
			+ "			 			 					wcc.`approval_status` AS `approvalStatus`,wcc.approval_date AS `approvalDate`,  \r\n"
			+ "			 			 					wcr.`wcc_customer_reject_list` AS `wccRejectReason`,wcr.`id` AS `wccRejectReasonId`,st.status AS `wccStatus`,st.id AS `wccStatusId`,wcc.`customer_po_number` AS `customerPoNumber`,wcc.`customer_po_date` AS `customerPoDate`,  \r\n"
			+ "			 			 					wcc.`podnumber` AS `podnumber`,wcc.`poddate` AS `poddate`, wcc.`exicom_invoice_number`, wcc.`exicom_invoice_date`,wcc.`other_rejection_reason`, \r\n"
			+ "			 								wcc.`total_charges` AS `totalCharges`, wcc.`total_customer_spare_charges` AS `customerBillingRate`,  \r\n"
			+ "			 								wcc.`total_site_visit_charge` AS `customerRate`, lg.`legal_entity_name` AS `legalEntityName`, \r\n"
			+ "			 			 					wcc.`sapinvoice_reference_number` AS `sapInvoiceNumber` , wcc.`remarks` AS `remarks`,\r\n"
			+ "			 			 					wcc.`pod_shipping_expected_date` AS `podShippingExpectedDate`,wcc.`pod_shipping_mode` AS `podShippingMod`,wcc.`pod_shipping_to_name` AS `podShippingName`,\r\n"
			+ "			 			 					wcc.`pod_shipping_tracking_number` AS `podShippingTrackingNumber`, wcc.`service_period_from` AS `servicePeriodFrom`,\r\n"
			+ "			 			 					wcc.`service_period_to` AS `servicePeriodTo`,wcc.`submitted_by_sch_date` AS `submittedBySchDate`, wcc.`approved_by_rsh` AS `approvedByRsh`, wcc.`other_rejection_reason` AS `otherReason`\r\n"
			+ "			 			 						 FROM `bt_customer_wcc` wcc   \r\n"
			+ "			 			 						LEFT JOIN `case_reason_details` cr ON cr.`reason_id` =wcc.`case_reason_reason_id`  \r\n"
			+ "			 			 						LEFT JOIN `case_types` ct ON ct.`case_type_id`=wcc.`casetype_case_type_id`  \r\n"
			+ "           									LEFT JOIN `legal_entity` lg ON lg.`legal_entity_id` =wcc.`service_center_legal_entity_id` \r\n"
			+ "			 			 						LEFT JOIN `circle_details` cd ON cd.`circle_name`=wcc.`circle_circle_name` \r\n"
			+ "			 			 						LEFT JOIN `bt_customer_wcc_reject` wcr ON wcr.id=wcc.reject_reason_id  \r\n"
			+ "			 			 						LEFT JOIN `bt_wcc_custom_status` st ON st.id=wcc.status_id  \r\n"
			+ "			 			 						 JOIN `employee_details` emp ON emp.`email_id`=wcc.`created_by_email_id`  \r\n"
			+ "			 			 				JOIN `employee_details` lemp ON lemp.`email_id`=wcc.`last_modified_by_email_id` WHERE wcc.id=:id",nativeQuery = true)
	Map<String, Object> getWccCustomerSingle(@Param("id")Long id);

	
	@Query("SELECT st FROM BtCustomerWccReject st ORDER BY st.id ASC")
	List<BtCustomerWccReject> getReasonList();

	@Query("SELECT COUNT(*) FROM BtCustomerWccReject st WHERE st.id=:reasonId")
	int findByRejectionId(@Param("reasonId")int reasonId);
	
	@Query("select wc from BtCustomerWcc wc where wc.id=:id")
	com.pratikshat.exicom.entity.BtCustomerWcc getById(long id);

	
	@Query(value="SELECT wcc.`id` AS id, wcc.`manual_id` AS manualId,wcc.`created_date` AS createdDate, wcc.`last_modified_date` AS modifiedDate, \r\n"
			+ "			 				CONCAT(IFNULL(emp.first_name,'') ,' ', IFNULL(emp.middle_name, ' '),' ', IFNULL(emp.last_name,'') ) AS `createdBy`, emp.email_id AS `createdByEmailId`, \r\n"
			+ "			 				CONCAT(IFNULL(lemp.first_name,'') ,' ', IFNULL(lemp.middle_name, ' '),' ', IFNULL(lemp.last_name,'') ) AS `lastModifiedBy`, lemp.email_id AS `lastModifiedByEmailId`, \r\n"
			+ "			 						wcc.`customer` AS customer,wcc.`jms_no` AS JmsNo, wcc.submitted_by_sch_date, wcc.`circle_circle_name` AS circle,cr.`reason_id` AS reasonId, \r\n"
			+ "			 						cr.`case_reason_details_name` AS reasonName ,ct.`case_type_id` AS caseTypeId, ct.`case_types_name` AS caseTypeName,  \r\n"
			+ "								wcc.`grn_number` AS `grnNumber`, wcc.`grn_date` AS `grnDate`,\r\n"
			+ "			 					wcc.`approval_status` AS `approvalStatus`,wcc.approval_date AS `approvalDate`, \r\n"
			+ "			 					wcr.`wcc_customer_reject_list` AS `wccRejectReason`,wcr.`id` AS `wccRejectReasonId`,st.status AS `wccStatus`,st.id AS `wccStatusId`,wcc.`customer_po_number` AS `customerPoNumber`,wcc.`customer_po_date` AS `customerPoDate`, \r\n"
			+ "			 					wcc.`podnumber` AS `podnumber`,wcc.`poddate` AS `poddate`, wcc.`exicom_invoice_number`, wcc.`exicom_invoice_date`,wcc.`other_rejection_reason`,\r\n"
			+ "								wcc.`total_charges` AS `totalCharges`, wcc.`total_customer_spare_charges` AS `customerBillingRate`, \r\n"
			+ "								wcc.`total_site_visit_charge` AS `customerRate`, \r\n"
			+ "			 					wcc.`sapinvoice_reference_number`\r\n"
			+ "			 						 FROM `bt_customer_wcc` wcc  \r\n"
			+ "			 						LEFT JOIN `case_reason_details` cr ON cr.`reason_id` =wcc.`case_reason_reason_id` \r\n"
			+ "			 						LEFT JOIN `case_types` ct ON ct.`case_type_id`=wcc.`casetype_case_type_id` \r\n"
			+ "			 						LEFT JOIN `circle_details` cd ON cd.`circle_name`=wcc.`circle_circle_name`\r\n"
			+ "			 						LEFT JOIN `bt_customer_wcc_reject` wcr ON wcr.id=wcc.reject_reason_id \r\n"
			+ "			 						LEFT JOIN `bt_wcc_custom_status` st ON st.id=wcc.status_id \r\n"
			+ "			 						 JOIN `employee_details` emp ON emp.`email_id`=wcc.`created_by_email_id` \r\n"
			+ "			 				JOIN `employee_details` lemp ON lemp.`email_id`=wcc.`last_modified_by_email_id` \r\n"
			+ "			 				WHERE wcc.created_by_email_id IN :user", countQuery="SELECT COUNT(*) \r\n"
					+ "			 						 FROM `bt_customer_wcc` wcc  \r\n"
					+ "			 						LEFT JOIN `case_reason_details` cr ON cr.`reason_id` =wcc.`case_reason_reason_id` \r\n"
					+ "			 						LEFT JOIN `case_types` ct ON ct.`case_type_id`=wcc.`casetype_case_type_id` \r\n"
					+ "			 						LEFT JOIN `circle_details` cd ON cd.`circle_name`=wcc.`circle_circle_name`\r\n"
					+ "			 						LEFT JOIN `bt_customer_wcc_reject` wcr ON wcr.id=wcc.reject_reason_id \r\n"
					+ "			 						LEFT JOIN `bt_wcc_custom_status` st ON st.id=wcc.status_id \r\n"
					+ "			 						 JOIN `employee_details` emp ON emp.`email_id`=wcc.`created_by_email_id` \r\n"
					+ "			 				JOIN `employee_details` lemp ON lemp.`email_id`=wcc.`last_modified_by_email_id` \r\n"
					+ "			 				WHERE wcc.created_by_email_id IN :user", nativeQuery=true)
	Page<Map<String, Object>> getWccCustomerByUser(@Param("user")List<String> urlist, Pageable page);
	
	
	
	
	///////////////////////////////////////Customer WCC Customise////////////////////////////////////
	
	
	@Query(value="SELECT sch.`customer_rate` AS siteVisitCharge ,sch.`customer_billing_rate` AS  `customerSpareCharges`\r\n"
			+ "										 FROM `bt_case` cs LEFT JOIN \r\n"
			+ "			 			 						`bt_service_appointment` sa ON sa.case_details_id=cs.id  \r\n"
			+ "			 			 				LEFT JOIN `bt_sch_use_only_details` sch ON sch.`bt_service_appointment_service_appointment_id`=sa.service_appointment_id  \r\n"
			+ "			 			 					WHERE sa.`status_status_id`IN(4) AND `cs`.id=:id", nativeQuery=true)
	List<Map<String,Object>> getTotalSACharges(@Param("id")long id);

	
	
	@Query(value="SELECT  \r\n"
			+ "			 wcc.`manual_id` AS `wccNumber`, \r\n"
			+ "			 wcc.`created_date` AS `wccCreationDate`, \r\n"
			+ "			 wcc.`approved_by_rsh` AS `wccApprovalDate`, \r\n"
			+ "			 wcc.`jms_no` AS `jmsNumber`, \r\n"
			+ "			 wcc.`customer` AS `customer`, \r\n"
			+ "			 cd.`zone_zone_name` AS `zone`, \r\n"
			+ "			 wcc.`circle_circle_name` AS `circle`, \r\n"
			+ "			 cs.`manual_id` AS `caseNo`, \r\n"
			+ "			 (SELECT sa.`manual_id` FROM `bt_service_appointment` sa LEFT JOIN `bt_case` bcs ON bcs.id=sa.`case_details_id` WHERE sa.`status_status_id`=4 AND bcs.id=cs.id) AS `SaNo`, \r\n"
			+ "			  cr.`case_reason_details_name` AS `caseReason`, \r\n"
			+ "			 ct.`case_types_name` AS `caseType`, \r\n"
			+ "			  wcc.`customer_po_number` AS `poNo`, \r\n"
			+ "			 wcc.`customer_po_date` AS `poDate`, \r\n"
			+ "			 wcc.`service_period_from` AS `servicePeriodFrom`, \r\n"
			+ "			 wcc.`service_period_to` AS `servicePeriodTo` \r\n"
			+ "			 FROM `bt_customer_wcc` AS wcc \r\n"
			+ "			 LEFT JOIN `case_reason_details` cr ON cr.`reason_id` =wcc.`case_reason_reason_id`  \r\n"
			+ "			 LEFT JOIN `case_types` ct ON ct.`case_type_id`=wcc.`casetype_case_type_id`   \r\n"
			+ "			 LEFT JOIN `circle_details` cd ON cd.`circle_name`=wcc.`circle_circle_name`  \r\n"
			+ "			 LEFT JOIN `bt_customer_wcc_case` wcase ON wcase.`wcc_id`=wcc.id \r\n"
			+ "			 LEFT JOIN `bt_case` cs ON cs.id= wcase.`btcase_id`			\r\n"
			+ "			 LEFT JOIN `bt_customer_wcc_reject` wcr ON wcr.id=wcc.reject_reason_id   \r\n"
			+ "			 LEFT JOIN `bt_wcc_custom_status` st ON st.id=wcc.status_id   \r\n"
			+ "			 JOIN `employee_details` emp ON emp.`email_id`=wcc.`created_by_email_id`   \r\n"
			+ "			 JOIN `employee_details` lemp ON lemp.`email_id`=wcc.`last_modified_by_email_id` WHERE wcc.id=:wccid", nativeQuery=true)
	List<Map<String, Object>> getTemplateData(@Param("wccid") long wccid);

	@Query(value="SELECT cs.`manual_id` AS `caseManualId` , cs.id AS `caseId`, \r\n"
			+ "wcs.`customer_spare_charges` AS `sapareCharges` , wcs.`site_visit_charge` AS `siteCharge`, wcs.`total_charges` AS `totalCharge`\r\n"
			+ "FROM `bt_customer_wcc_case` wcs\r\n"
			+ "LEFT JOIN `bt_customer_wcc` wcc ON wcc.id=wcs.`wcc_id`\r\n"
			+ "LEFT JOIN `bt_case` cs ON cs.id=wcs.`btcase_id`\r\n"
			+ " WHERE wcc.id=:wccid",countQuery="SELECT COUNT(*) \r\n"
					+ "FROM `bt_customer_wcc_case` wcs\r\n"
					+ "LEFT JOIN `bt_customer_wcc` wcc ON wcc.id=wcs.`wcc_id`\r\n"
					+ "LEFT JOIN `bt_case` cs ON cs.id=wcs.`btcase_id`\r\n"
					+ " WHERE wcc.id=:wccid \r\n", nativeQuery=true)
	List<Map<String, Object>> getAllwccCase(@Param("wccid") long id);

	
	@Query(value="select cs from BtCustomerWccCase wcs left join wcs.btcase AS cs left join wcs.wcc AS wc where wc.id=:id")
	List<BtCase> getCaseByWccId(@Param("id") long id);
	

	// BtCustomerWcc findById(long id);
	
	
	// @Query(value = "SELECT DISTINCT wcc.`id` AS id,\r\n"
	// 		+ "wcc.`manual_id` AS manualId,\r\n"
	// 		+ "wcc.`created_date` AS createdDate, \r\n"
	// 		+ "wcc.`last_modified_date` AS modifiedDate,  \r\n"
	// 		+ "CONCAT(IFNULL(emp.first_name,'') , IFNULL(emp.middle_name, ' '), IFNULL(emp.last_name,'') ) AS `createdBy`,\r\n"
	// 		+ "emp.email_id AS `createdByEmailId`,  \r\n"
	// 		+ "CONCAT(IFNULL(lemp.first_name,'') , IFNULL(lemp.middle_name, ' '), IFNULL(lemp.last_name,'') ) AS `lastModifiedBy`,\r\n"
	// 		+ "lemp.email_id AS `lastModifiedByEmailId`,  \r\n"
	// 		+ "cases.`jms_no` AS JmsNo, \r\n"
	// 		+ "wcc.submitted_by_sch_date, \r\n"
	// 		+ "site.`circle_circle_name` AS circle,\r\n"
	// 		+ "cr.`reason_id` AS reasonId,  \r\n"
	// 		+ "cr.`case_reason_details_name` AS reasonName, \r\n"
	// 		+ "ct.`case_type_id` AS caseTypeId, \r\n"
	// 		+ "ct.`case_types_name` AS caseTypeName,   \r\n"
	// 		+ "le.`legal_entity_name` AS serviceCenter, \r\n"
	// 		+ "sch.`rate` AS `totalSiteVisitCharge`,  \r\n"
	// 		+ "sch.`billing_rate` AS  `totalCustomerSpareCharges`,\r\n"
	// 		+ "wcc.`approval_status` AS `approvalStatus`,\r\n"
	// 		+ "wcc.approval_date AS `approvalDate`,\r\n"
	// 		+ "wcc.remarks AS `rejectRemarks`,  \r\n"
	// 		+ "wcr.`wcc_customer_reject_list` AS `wccRejectReason`,\r\n"
	// 		+ "wcr.`id` AS `wccRejectReasonId`,\r\n"
	// 		+ "st.status AS `wccStatus`,\r\n"
	// 		+ "st.id AS `wccStatusId`,\r\n"
	// 		+ "cases.ponumber,\r\n"
	// 		+ "cases.podate,  \r\n"
	// 		+ "cases.invoice_number,\r\n"
	// 		+ "cases.invoice_date  \r\n"
	// 		+ "FROM `bt_customer_wcc` wcc   \r\n"
	// 		+ "LEFT JOIN `bt_case` cases ON cases.`customer_wcc_id`=wcc.`id`  \r\n"
	// 		+ "LEFT JOIN(SELECT wcc.`id`,IFNULL(SUM( sch.`customer_rate`),0) AS `rate`,\r\n"
	// 		+ "IFNULL(SUM(sch.`customer_billing_rate`),0) AS `billing_rate` FROM `bt_sch_use_only_details` sch \r\n"
	// 		+ " JOIN `bt_service_appointment` sa ON sa.service_appointment_id=sch.`bt_service_appointment_service_appointment_id`\r\n"
	// 		+ "JOIN `bt_case` cases ON cases.`id`=sa.case_details_id LEFT JOIN  `bt_customer_wcc` wcc ON wcc.`id`=cases.`customer_wcc_id`  \r\n"
	// 		+ "GROUP BY wcc.`id`)sch ON sch.`id`=wcc.id LEFT JOIN `bt_site_details` site ON site.`id`=cases.`site_id`  \r\n"
	// 		+ "LEFT JOIN `case_reason_details` cr ON cr.`reason_id` =cases.`case_reason_reason_id`  \r\n"
	// 		+ "LEFT JOIN `case_types` ct ON ct.`case_type_id`=cases.`case_type_case_type_id`  \r\n"
	// 		+ "LEFT JOIN `legal_entity` le ON le.`legal_entity_id`=cases.service_center_legal_entity_id  \r\n"
	// 		+ "LEFT JOIN `bt_customer_wcc_reject` wcr ON wcr.id=wcc.reject_reason_id  \r\n"
	// 		+ "LEFT JOIN `bt_wcc_custom_status` st ON st.id=wcc.status_id  \r\n"
	// 		+ "JOIN `employee_details` emp ON emp.`email_id`=wcc.`created_by_email_id`  \r\n"
	// 		+ "JOIN `employee_details` lemp ON lemp.`email_id`=wcc.`last_modified_by_email_id`",countQuery = "SELECT COUNT(*) \r\n"
	// 		+ "			 FROM `bt_customer_wcc` wcc \r\n"
	// 		+ "			LEFT JOIN `bt_customer_wcc_reject` wcr ON wcr.id=wcc.reject_reason_id\r\n"
	// 		+ "			LEFT JOIN `bt_wcc_custom_status` st ON st.id=wcc.status_id\r\n"
	// 		+ "			 JOIN `employee_details` emp ON emp.`email_id`=wcc.`created_by_email_id`\r\n"
	// 		+ "			 JOIN `employee_details` lemp ON lemp.`email_id`=wcc.`last_modified_by_email_id` \r\n", nativeQuery=true)
	// Page<Map<String, Object>> getWccCustomer(Pageable pageable);
	

	
	// @Query(value = "SELECT DISTINCT wcc.`id` AS id,\r\n"
	// 		+ "wcc.`manual_id` AS manualId,\r\n"
	// 		+ "wcc.`created_date` AS createdDate, \r\n"
	// 		+ "wcc.`last_modified_date` AS modifiedDate,  \r\n"
	// 		+ "CONCAT(IFNULL(emp.first_name,'') , IFNULL(emp.middle_name, ' '), IFNULL(emp.last_name,'') ) AS `createdBy`,\r\n"
	// 		+ "emp.email_id AS `createdByEmailId`,  \r\n"
	// 		+ "CONCAT(IFNULL(lemp.first_name,'') , IFNULL(lemp.middle_name, ' '), IFNULL(lemp.last_name,'') ) AS `lastModifiedBy`,\r\n"
	// 		+ "lemp.email_id AS `lastModifiedByEmailId`,  \r\n"
	// 		+ "cases.`jms_no` AS JmsNo, \r\n"
	// 		+ "wcc.submitted_by_sch_date, \r\n"
	// 		+ "site.`circle_circle_name` AS circle,\r\n"
	// 		+ "cr.`reason_id` AS reasonId,  \r\n"
	// 		+ "cr.`case_reason_details_name` AS reasonName, \r\n"
	// 		+ "ct.`case_type_id` AS caseTypeId, \r\n"
	// 		+ "ct.`case_types_name` AS caseTypeName,   \r\n"
	// 		+ "le.`legal_entity_name` AS serviceCenter, \r\n"
	// 		+ "sch.`rate` AS `totalSiteVisitCharge`,  \r\n"
	// 		+ "sch.`billing_rate` AS  `totalCustomerSpareCharges`,\r\n"
	// 		+ "wcc.`approval_status` AS `approvalStatus`,\r\n"
	// 		+ "wcc.approval_date AS `approvalDate`,\r\n"
	// 		+ "wcc.remarks AS `rejectRemarks`,  \r\n"
	// 		+ "wcr.`wcc_customer_reject_list` AS `wccRejectReason`,\r\n"
	// 		+ "wcr.`id` AS `wccRejectReasonId`,\r\n"
	// 		+ "st.status AS `wccStatus`,\r\n"
	// 		+ "st.id AS `wccStatusId`,\r\n"
	// 		+ "cases.ponumber,\r\n"
	// 		+ "cases.podate,  \r\n"
	// 		+ "cases.invoice_number,\r\n"
	// 		+ "cases.invoice_date  \r\n"
	// 		+ "FROM `bt_customer_wcc` wcc   \r\n"
	// 		+ "LEFT JOIN `bt_case` cases ON cases.`customer_wcc_id`=wcc.`id`  \r\n"
	// 		+ "LEFT JOIN(SELECT wcc.`id`,IFNULL(SUM( sch.`customer_rate`),0) AS `rate`,\r\n"
	// 		+ "IFNULL(SUM(sch.`customer_billing_rate`),0) AS `billing_rate` FROM `bt_sch_use_only_details` sch \r\n"
	// 		+ " JOIN `bt_service_appointment` sa ON sa.service_appointment_id=sch.`bt_service_appointment_service_appointment_id`\r\n"
	// 		+ "JOIN `bt_case` cases ON cases.`id`=sa.case_details_id LEFT JOIN  `bt_customer_wcc` wcc ON wcc.`id`=cases.`customer_wcc_id`  \r\n"
	// 		+ "GROUP BY wcc.`id`)sch ON sch.`id`=wcc.id LEFT JOIN `bt_site_details` site ON site.`id`=cases.`site_id`  \r\n"
	// 		+ "LEFT JOIN `case_reason_details` cr ON cr.`reason_id` =cases.`case_reason_reason_id`  \r\n"
	// 		+ "LEFT JOIN `case_types` ct ON ct.`case_type_id`=cases.`case_type_case_type_id`  \r\n"
	// 		+ "LEFT JOIN `legal_entity` le ON le.`legal_entity_id`=cases.service_center_legal_entity_id  \r\n"
	// 		+ "LEFT JOIN `bt_customer_wcc_reject` wcr ON wcr.id=wcc.reject_reason_id  \r\n"
	// 		+ "LEFT JOIN `bt_wcc_custom_status` st ON st.id=wcc.status_id  \r\n"
	// 		+ "JOIN `employee_details` emp ON emp.`email_id`=wcc.`created_by_email_id`  \r\n"
	// 		+ "JOIN `employee_details` lemp ON lemp.`email_id`=wcc.`last_modified_by_email_id` WHERE wcc.id=:id",nativeQuery = true)
	// Map<String, Object> getWccCustomerSingle(@Param("id")Long id);

	
	// @Query("SELECT st FROM BtCustomerWccReject st ORDER BY st.id ASC")
	// List<BtCustomerWccReject> getReasonList();

	// @Query("SELECT COUNT(*) FROM BtCustomerWccReject st WHERE st.id=:reasonId")
	// int findByRejectionId(@Param("reasonId")int reasonId);
	
	// @Query("select wc from BtCustomerWcc wc where wc.id=:id")
	// com.pratikshat.exicom.entity.BtCustomerWcc getById(long id);

	

	// @Query(value=" SELECT DISTINCT wcc.`id` AS id,\r\n"
	// 		+ "wcc.`manual_id` AS manualId,\r\n"
	// 		+ "wcc.`created_date` AS createdDate, \r\n"
	// 		+ "wcc.`last_modified_date` AS modifiedDate,  \r\n"
	// 		+ "CONCAT(IFNULL(emp.first_name,'') , IFNULL(emp.middle_name, ' '), IFNULL(emp.last_name,'') ) AS `createdBy`,\r\n"
	// 		+ "emp.email_id AS `createdByEmailId`,  \r\n"
	// 		+ "CONCAT(IFNULL(lemp.first_name,'') , IFNULL(lemp.middle_name, ' '), IFNULL(lemp.last_name,'') ) AS `lastModifiedBy`,\r\n"
	// 		+ "lemp.email_id AS `lastModifiedByEmailId`,  \r\n"
	// 		+ "cases.`jms_no` AS JmsNo, \r\n"
	// 		+ "wcc.submitted_by_sch_date, \r\n"
	// 		+ "site.`circle_circle_name` AS circle,\r\n"
	// 		+ "cr.`reason_id` AS reasonId,  \r\n"
	// 		+ "cr.`case_reason_details_name` AS reasonName, \r\n"
	// 		+ "ct.`case_type_id` AS caseTypeId, \r\n"
	// 		+ "ct.`case_types_name` AS caseTypeName,   \r\n"
	// 		+ "le.`legal_entity_name` AS serviceCenter, \r\n"
	// 		+ "sch.`rate` AS `totalSiteVisitCharge`,  \r\n"
	// 		+ "sch.`billing_rate` AS  `totalCustomerSpareCharges`,\r\n"
	// 		+ "wcc.`approval_status` AS `approvalStatus`,\r\n"
	// 		+ "wcc.approval_date AS `approvalDate`,\r\n"
	// 		+ "wcc.remarks AS `rejectRemarks`,  \r\n"
	// 		+ "wcr.`wcc_customer_reject_list` AS `wccRejectReason`,\r\n"
	// 		+ "wcr.`id` AS `wccRejectReasonId`,\r\n"
	// 		+ "st.status AS `wccStatus`,\r\n"
	// 		+ "st.id AS `wccStatusId`,\r\n"
	// 		+ "cases.ponumber,\r\n"
	// 		+ "cases.podate,  \r\n"
	// 		+ "cases.invoice_number,\r\n"
	// 		+ "cases.invoice_date  \r\n"
	// 		+ "FROM `bt_customer_wcc` wcc   \r\n"
	// 		+ "LEFT JOIN `bt_case` cases ON cases.`customer_wcc_id`=wcc.`id`  \r\n"
	// 		+ "LEFT JOIN(SELECT wcc.`id`,IFNULL(SUM( sch.`customer_rate`),0) AS `rate`,\r\n"
	// 		+ "IFNULL(SUM(sch.`customer_billing_rate`),0) AS `billing_rate` FROM `bt_sch_use_only_details` sch \r\n"
	// 		+ " JOIN `bt_service_appointment` sa ON sa.service_appointment_id=sch.`bt_service_appointment_service_appointment_id`\r\n"
	// 		+ "JOIN `bt_case` cases ON cases.`id`=sa.case_details_id LEFT JOIN  `bt_customer_wcc` wcc ON wcc.`id`=cases.`customer_wcc_id`  \r\n"
	// 		+ "GROUP BY wcc.`id`)sch ON sch.`id`=wcc.id LEFT JOIN `bt_site_details` site ON site.`id`=cases.`site_id`  \r\n"
	// 		+ "LEFT JOIN `case_reason_details` cr ON cr.`reason_id` =cases.`case_reason_reason_id`  \r\n"
	// 		+ "LEFT JOIN `case_types` ct ON ct.`case_type_id`=cases.`case_type_case_type_id`  \r\n"
	// 		+ "LEFT JOIN `legal_entity` le ON le.`legal_entity_id`=cases.service_center_legal_entity_id  \r\n"
	// 		+ "LEFT JOIN `bt_customer_wcc_reject` wcr ON wcr.id=wcc.reject_reason_id  \r\n"
	// 		+ "LEFT JOIN `bt_wcc_custom_status` st ON st.id=wcc.status_id  \r\n"
	// 		+ "JOIN `employee_details` emp ON emp.`email_id`=wcc.`created_by_email_id`  \r\n"
	// 		+ "JOIN `employee_details` lemp ON lemp.`email_id`=wcc.`last_modified_by_email_id` WHERE wcc.created_by_email_id IN :user", countQuery="SELECT COUNT(*) \r\n"
	// 		+ "			 						 FROM `bt_customer_wcc` wcc  \r\n"
	// 		+ "			 						LEFT JOIN `bt_customer_wcc_reject` wcr ON wcr.id=wcc.reject_reason_id \r\n"
	// 		+ "			 						LEFT JOIN `bt_wcc_custom_status` st ON st.id=wcc.status_id \r\n"
	// 		+ "			 						LEFT JOIN `bt_service_appointment` sa ON sa.case_details_id=cases.id \r\n"
	// 		+ "			 						 JOIN `employee_details` emp ON emp.`email_id`=wcc.`created_by_email_id` \r\n"
	// 		+ "			 				JOIN `employee_details` lemp ON lemp.`email_id`=wcc.`last_modified_by_email_id`\r\n"
	// 		+ "			 				WHERE wcc.created_by_email_id IN :user", nativeQuery=true)
	// Page<Map<String, Object>> getWccCustomerByUser(@Param("user")List<String> urlist, Pageable page);
	
	
	// @Query(value=" SELECT cases.`customer` AS customer,cases.`jms_no` AS JmsNo, site.`circle_circle_name` AS circle,cr.`reason_id` AS reasonId,  \r\n"
	// 		+ " 			 			 						cr.`case_reason_details_name` AS reasonName ,ct.`case_type_id` AS caseTypeId, ct.`case_types_name` AS caseTypeName,   \r\n"
	// 		+ " 			 			 						le.`legal_entity_name` AS serviceCenter,cases.`id` AS caseid, cases.`manual_id` AS `caseManualId`,  \r\n"
	// 		+ " 			 			 						IFNULL((SELECT SUM(sch.`customer_rate`) AS customerRate FROM `bt_case` cs  \r\n"
	// 		+ " 			 			 					LEFT JOIN `bt_service_appointment` sa ON sa.case_details_id=cs.id  \r\n"
	// 		+ " 			 			 				LEFT JOIN `bt_sch_use_only_details` sch ON sch.`bt_service_appointment_service_appointment_id`=sa.service_appointment_id  \r\n"
	// 		+ " 			 			 					WHERE `cs`.id=cases.`id`),0) AS `totalSiteVisitCharge`,  \r\n"
	// 		+ " 			 			 					IFNULL((SELECT SUM(sch.`customer_billing_rate`) AS customerBillingRate FROM `bt_case` cs\r\n"
	// 		+ " 			 			 					 LEFT JOIN `bt_service_appointment` sa ON sa.case_details_id=cs.id  \r\n"
	// 		+ " 			 			 				LEFT JOIN `bt_sch_use_only_details` sch ON sch.`bt_service_appointment_service_appointment_id`=sa.service_appointment_id  \r\n"
	// 		+ " 			 			 					WHERE `cs`.id=cases.`id` ),0) AS  `totalCustomerSpareCharges`,  \r\n"
	// 		+ " 			 			 \r\n"
	// 		+ " 			 			 					cases.ponumber,cases.podate,  \r\n"
	// 		+ " 			 			 					cases.invoice_number,cases.invoice_date  \r\n"
	// 		+ " 			 			 						 FROM  `bt_case` cases    \r\n"
	// 		+ " 			 			 						 	LEFT JOIN `bt_site_details` site ON site.`id`=cases.`site_id`\r\n"
	// 		+ " 			 			 						LEFT JOIN `case_reason_details` cr ON cr.`reason_id` =cases.`case_reason_reason_id`  \r\n"
	// 		+ " 			 			 						LEFT JOIN `case_types` ct ON ct.`case_type_id`=cases.`case_type_case_type_id`  \r\n"
	// 		+ " 			 			 						LEFT JOIN `legal_entity` le ON le.`legal_entity_id`=cases.service_center_legal_entity_id  \r\n"
	// 		+ " 			 			 						 \r\n"
	// 		+ " 			 			 					  WHERE cases.`customer_wcc_id`=:id", countQuery ="select COUNT(*) FROM  `bt_case` cases    \r\n"
	// 				+ " 			 			 						 	LEFT JOIN `bt_site_details` site ON site.`id`=cases.`site_id`\r\n"
	// 				+ " 			 			 						LEFT JOIN `case_reason_details` cr ON cr.`reason_id` =cases.`case_reason_reason_id`  \r\n"
	// 				+ " 			 			 						LEFT JOIN `case_types` ct ON ct.`case_type_id`=cases.`case_type_case_type_id`  \r\n"
	// 				+ " 			 			 						LEFT JOIN `legal_entity` le ON le.`legal_entity_id`=cases.service_center_legal_entity_id  \r\n"
	// 				+ " 			 			 					  WHERE cases.`customer_wcc_id`=:id", nativeQuery=true)
	// Page<Map<String, Object>> getCaseByWccId(@Param("id")Long id , Pageable page);
	
}
