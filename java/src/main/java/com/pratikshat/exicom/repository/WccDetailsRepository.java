package com.pratikshat.exicom.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.WccDetails;

@Repository
public interface WccDetailsRepository extends JpaRepository<WccDetails, Long>, WccDetailsRepositoryCustome {
	// for status dropdown
	@Query(value = "select * from wcc_status", nativeQuery = true)
	List<Map<String, Object>> getWccStatus();

	// for type dropdown
	@Query(value = "select id as id,wcc_type_name as wccTypeName from wcc_types", nativeQuery = true)
	List<Map<String, Object>> getTypeForDropdownList();

	@Query(value = "SELECT \r\n" +
			"	sa.`service_appointment_id`,\r\n" +
			"	sa.`appointment_number` AS appointmentNumber,\r\n" +
			"	act.`sap_customer_number` AS customerSapNumber,\r\n" +
			"	act.`name` AS customerName,\r\n" +
			"	cs.`manual_id` AS caseId,\r\n" +
			"	cty.`case_types_name` AS caseType,\r\n" +
			"	sa.`circle_circle_name` AS circleName,\r\n" +
			"	sr.`name` AS serviceResource,\r\n" +
			"	cr.`case_reason_details_name` AS caseReason,\r\n" +
			" 	cs.close_date as caseCloseDate\r\n" +
			"	\r\n" +
			" FROM `service_appointment` sa \r\n" +
			"	JOIN `case_details` cs ON sa.`case_details_case_id`=cs.`case_id`\r\n" +
			"	JOIN `site_details` st ON cs.`site_site_id`=st.`site_id`\r\n" +
			"	JOIN `accounts` act ON st.`account_sap_customer_number`=act.`sap_customer_number`\r\n" +
			"	JOIN `case_reason_details` cr ON cs.`case_reason_reason_id`=cr.`reason_id`\r\n" +
			"	JOIN `case_types` cty ON cs.`case_type_case_type_id`=cty.`case_type_id`\r\n" +
			"	JOIN `service_recource` sr ON sr.`service_resource_id`=sa.`service_resource_service_resource_id`\r\n" +
			"	JOIN `service_appointment_status` srst ON srst.`status_id`=sa.`status_status_id`\r\n" +
			"	WHERE sa.wcc=FALSE AND sr.`user_email_id`=:username AND cr.`wcc_type_id`=:wccTypeId and sa.`claim_status_claim_status_id`=3 and date(cs.close_date) between date(:datefrom) and date(:dateto)", nativeQuery = true)
	List<Map<String, Object>> getPendingServiceAppointment(@Param("username") String userEmailId,
			@Param("wccTypeId") long wccTypeId, @Param("datefrom") java.util.Date datefrom,
			@Param("dateto") java.util.Date dateto);

	@Query(value = "SELECT 	w.`manual_id` AS manulId,\r\n" +
			"	w.`id` AS id,\r\n" +
			" 	w.wcc_amount as wccAmount,\r\n" +
			"   w.wcc_tax_amount as wccTaxAmount,\r\n" +
			"	w.wcc_total_amount as wccTotalAmount,\r\n" +
			"	act.`name` AS vendorName,\r\n" +
			"	act.`sap_customer_number` AS vendorSapNumber,\r\n" +
			"	cod.`first_name` AS vendorUserFirstName,\r\n" +
			"	cod.`last_name` AS vendorUserLastName,\r\n" +
			"	cod.`middle_name` AS vendorUserMiddleName,\r\n" +
			"	cod.`employee_code` AS vendorUserCode,\r\n" +
			"	cod.`contact_id` as vendorContactId,\r\n" +
			"	w.`circle_circle_name` AS circleName,\r\n" +
			"	cod.`contact_id` as vendorContactId,\r\n" +
			"	lg.`legal_entity_name` AS serviceCenter,\r\n" +
			"	wt.`wcc_type_name` AS wccType,\r\n" +
			"	ct.`case_types_name` AS caseType,\r\n" +
			"	cr.`case_reason_details_name` AS caseReasonDetails,\r\n" +
			"	ws.`name` AS wccStatus,\r\n" +
			"	w.`created_date` AS createdDate,\r\n" +
			"	w.`last_modified_by_email_id` AS lastModifiedByEmailId,\r\n" +
			"	w.`last_modified_date` AS lastModifiedDate,\r\n" +
			"crd.`first_name` AS createdByFirstName,\r\n" +
			"			 	crd.`last_name` AS createdByLastname,\r\n" +
			"			 	crd.`middle_name` AS createdbyMiddleName,\r\n" +
			"			 	mrd.`first_name` AS modifiedByFirstName,\r\n" +
			"			 	mrd.`last_name` AS modifiedByLastName,\r\n" +
			"			 	mrd.`middle_name` AS modifiedByMiddleName,\r\n" +
			"			 	crd.`email_id` AS createdByEmailId,\r\n" +
			"			 	mrd.`email_id` AS modifiedByEmailId,\r\n" +
			"	w.from_date as fromDate,\r\n" +
			"	w.to_date as toDate,\r\n" +
			" 	w.wcc_type_id as wccTypeId,\r\n" +
			"	tobe.remark AS approvalRemark,\r\n" +
			"	tobe.approved_date AS approvalDate,\r\n" +
			"(SELECT approved_date FROM to_be_approved WHERE `object_name`='WccDetails' AND `object_id`=w.`id` ORDER BY id DESC LIMIT 1) AS lastActionDate\r\n"
			+
			"	\r\n" +
			"	FROM `wcc_details` w\r\n" +
			"		JOIN `wcc_status` ws ON ws.`id`=w.`wcc_status_id`\r\n" +
			"		JOIN `wcc_types` wt ON wt.`id`=w.`wcc_type_id`\r\n" +
			"		JOIN `legal_entity` lg ON w.`service_center_legal_entity_id`=lg.`legal_entity_id`\r\n" +
			"		LEFT JOIN `case_types` ct ON ct.`case_type_id`=w.`case_type_case_type_id`\r\n" +
			"		LEFT JOIN `case_reason_details` cr ON cr.`reason_id`=w.`case_reason_reason_id`\r\n" +
			"		JOIN `service_recource` sr ON sr.`user_email_id`=w.`owner_email_id`\r\n" +
			"		JOIN `contact_details` cod ON sr.`contact_contact_id`=cod.`contact_id`\r\n" +
			"		JOIN `accounts` act ON cod.`account_id`=act.`sap_customer_number`\r\n" +
			"		JOIN `employee_details` crd ON w.`created_by_email_id`=crd.`email_id`\r\n" +
			"			 	JOIN `employee_details` mrd ON w.`last_modified_by_email_id`=mrd.`email_id`\r\n" +
			"	LEFT JOIN `to_be_approved` tobe ON tobe.`object_name`='WccDetails' AND tobe.`object_id`=w.`id` AND (tobe.`action`='Approved' AND tobe.`action`='Rejected') AND tobe.`status`=1\r\n"
			+
			"		WHERE sr.`user_email_id`=:userEmailId\r\n" +
			"		", countQuery = "SELECT 	COUNT(*)\r\n" +
					"	FROM `wcc_details` w\r\n" +
					"		JOIN `wcc_status` ws ON ws.`id`=w.`wcc_status_id`\r\n" +
					"		JOIN `wcc_types` wt ON wt.`id`=w.`wcc_type_id`\r\n" +
					"		JOIN `legal_entity` lg ON w.`service_center_legal_entity_id`=lg.`legal_entity_id`\r\n" +
					"		LEFT JOIN `case_types` ct ON ct.`case_type_id`=w.`case_type_case_type_id`\r\n" +
					"		LEFT JOIN `case_reason_details` cr ON cr.`reason_id`=w.`case_reason_reason_id`\r\n" +
					"		JOIN `service_recource` sr ON sr.`user_email_id`=w.`owner_email_id`\r\n" +
					"		JOIN `contact_details` cod ON sr.`contact_contact_id`=cod.`contact_id`\r\n" +
					"		JOIN `accounts` act ON cod.`account_id`=act.`sap_customer_number`\r\n" +
					"		JOIN `employee_details` crd ON w.`created_by_email_id`=crd.`email_id`\r\n" +
					"			 	JOIN `employee_details` mrd ON w.`last_modified_by_email_id`=mrd.`email_id`\r\n" +
					"	LEFT JOIN `to_be_approved` tobe ON tobe.`object_name`='WccDetails' AND tobe.`object_id`=w.`id` AND (tobe.`action`='Approved' AND tobe.`action`='Rejected') AND tobe.`status`=1\r\n"
					+
					"		WHERE sr.`user_email_id`=:userEmailId\r\n" +
					"		", nativeQuery = true)
	Page<Map<String, Object>> getAllWccByUserId(@Param("userEmailId") String userEmailId, Pageable page);

	@Query(value = "SELECT 	w.`manual_id` AS manulId,\r\n" +
			"	w.`id` AS id,\r\n" +
			"	w.invoiced as invoiced,\r\n" +
			" 	w.wcc_amount as wccAmount,\r\n" +
			"   w.wcc_tax_amount as wccTaxAmount,\r\n" +
			"	w.wcc_total_amount as wccTotalAmount,\r\n" +
			"	act.`name` AS vendorName,\r\n" +
			"	act.`sap_customer_number` AS vendorSapNumber,\r\n" +
			"	cod.`first_name` AS vendorUserFirstName,\r\n" +
			"	cod.`last_name` AS vendorUserLastName,\r\n" +
			"	cod.`middle_name` AS vendorUserMiddleName,\r\n" +
			"	cod.`employee_code` AS vendorUserCode,\r\n" +
			"	cod.`contact_id` as vendorContactId,\r\n" +
			"	w.`circle_circle_name` AS circleName,\r\n" +
			"	lg.`legal_entity_name` AS serviceCenter,\r\n" +
			"	wt.`wcc_type_name` AS wccType,\r\n" +
			"	ct.`case_types_name` AS caseType,\r\n" +
			"	cr.`case_reason_details_name` AS caseReasonDetails,\r\n" +
			"	ws.`name` AS wccStatus,\r\n" +
			"	w.`created_date` AS createdDate,\r\n" +
			"	w.`last_modified_by_email_id` AS lastModifiedByEmailId,\r\n" +
			"	w.`last_modified_date` AS lastModifiedDate,\r\n" +
			"	crd.`first_name` AS createdByFirstName,\r\n" +
			"			 	crd.`last_name` AS createdByLastname,\r\n" +
			"			 	crd.`middle_name` AS createdbyMiddleName,\r\n" +
			"			 	mrd.`first_name` AS modifiedByFirstName,\r\n" +
			"			 	mrd.`last_name` AS modifiedByLastName,\r\n" +
			"			 	mrd.`middle_name` AS modifiedByMiddleName,\r\n" +
			"			 	crd.`email_id` AS createdByEmailId,\r\n" +
			"			 	mrd.`email_id` AS modifiedByEmailId,\r\n" +
			"	w.from_date as fromDate,\r\n" +
			"	w.to_date as toDate,\r\n" +
			" 	w.wcc_type_id as wccTypeId,\r\n" +
			"	tobe.remark AS approvalRemark,\r\n" +
			"	tobe.approved_date AS approvalDate,\r\n" +
			"(SELECT approved_date FROM to_be_approved WHERE `object_name`='WccDetails' AND `object_id`=w.`id` ORDER BY id DESC LIMIT 1) AS lastActionDate\r\n"
			+
			"	\r\n" +
			"	FROM `wcc_details` w\r\n" +
			"		JOIN `wcc_status` ws ON ws.`id`=w.`wcc_status_id`\r\n" +
			"		JOIN `wcc_types` wt ON wt.`id`=w.`wcc_type_id`\r\n" +
			"		JOIN `legal_entity` lg ON w.`service_center_legal_entity_id`=lg.`legal_entity_id`\r\n" +
			"		left JOIN `case_types` ct ON ct.`case_type_id`=w.`case_type_case_type_id`\r\n" +
			"		left JOIN `case_reason_details` cr ON cr.`reason_id`=w.`case_reason_reason_id`\r\n" +
			"		JOIN `service_recource` sr ON sr.`user_email_id`=w.`owner_email_id`\r\n" +
			"		JOIN `contact_details` cod ON sr.`contact_contact_id`=cod.`contact_id`\r\n" +
			"		JOIN `accounts` act ON cod.`account_id`=act.`sap_customer_number`\r\n" +
			"		JOIN `employee_details` crd ON w.`created_by_email_id`=crd.`email_id`\r\n" +
			"		JOIN `employee_details` mrd ON w.`last_modified_by_email_id`=mrd.`email_id`" +
			"	LEFT JOIN `to_be_approved` tobe ON tobe.`object_name`='WccDetails' AND tobe.`object_id`=w.`id` AND (tobe.`action`='Approved' AND tobe.`action`='Rejected') AND tobe.`status`=1\r\n"
			+
			"		", countQuery = "SELECT 	COUNT(*)\r\n" +
					"	FROM `wcc_details` w\r\n" +
					"		JOIN `wcc_status` ws ON ws.`id`=w.`wcc_status_id`\r\n" +
					"		JOIN `wcc_types` wt ON wt.`id`=w.`wcc_type_id`\r\n" +
					"		JOIN `legal_entity` lg ON w.`service_center_legal_entity_id`=lg.`legal_entity_id`\r\n" +
					"		left JOIN `case_types` ct ON ct.`case_type_id`=w.`case_type_case_type_id`\r\n" +
					"		left JOIN `case_reason_details` cr ON cr.`reason_id`=w.`case_reason_reason_id`\r\n" +
					"		JOIN `service_recource` sr ON sr.`user_email_id`=w.`owner_email_id`\r\n" +
					"		JOIN `contact_details` cod ON sr.`contact_contact_id`=cod.`contact_id`\r\n" +
					"		JOIN `accounts` act ON cod.`account_id`=act.`sap_customer_number`\r\n" +
					"		JOIN `employee_details` crd ON w.`created_by_email_id`=crd.`email_id`\r\n" +
					"		JOIN `employee_details` mrd ON w.`last_modified_by_email_id`=mrd.`email_id`" +
					"	LEFT JOIN `to_be_approved` tobe ON tobe.`object_name`='WccDetails' AND tobe.`object_id`=w.`id` AND (tobe.`action`='Approved' AND tobe.`action`='Rejected') AND tobe.`status`=1\r\n"
					+
					"		", nativeQuery = true)
	Page<Map<String, Object>> getAll(Pageable page);

	@Query(value = "SELECT 	w.`manual_id` AS manulId,\r\n" +
			"	w.`id` AS id,\r\n" +
			"	w.invoiced as invoiced,\r\n" +
			" 	w.wcc_amount as wccAmount,\r\n" +
			"   w.wcc_tax_amount as wccTaxAmount,\r\n" +
			"	w.wcc_total_amount as wccTotalAmount,\r\n" +
			"	act.`name` AS vendorName,\r\n" +
			"	act.`sap_customer_number` AS vendorSapNumber,\r\n" +
			"	cod.`first_name` AS vendorUserFirstName,\r\n" +
			"	cod.`last_name` AS vendorUserLastName,\r\n" +
			"	cod.`middle_name` AS vendorUserMiddleName,\r\n" +
			"	cod.`employee_code` AS vendorUserCode,\r\n" +
			"	cod.`contact_id` as vendorContactId,\r\n" +
			"	w.`circle_circle_name` AS circleName,\r\n" +
			"	lg.`legal_entity_name` AS serviceCenter,\r\n" +
			"	wt.`wcc_type_name` AS wccType,\r\n" +
			"	ct.`case_types_name` AS caseType,\r\n" +
			"	cr.`case_reason_details_name` AS caseReasonDetails,\r\n" +
			"	ws.`name` AS wccStatus,\r\n" +
			"	w.`created_date` AS createdDate,\r\n" +
			"	w.`last_modified_by_email_id` AS lastModifiedByEmailId,\r\n" +
			"	w.`last_modified_date` AS lastModifiedDate,\r\n" +
			"	crd.`first_name` AS createdByFirstName,\r\n" +
			"			 	crd.`last_name` AS createdByLastname,\r\n" +
			"			 	crd.`middle_name` AS createdbyMiddleName,\r\n" +
			"			 	mrd.`first_name` AS modifiedByFirstName,\r\n" +
			"			 	mrd.`last_name` AS modifiedByLastName,\r\n" +
			"			 	mrd.`middle_name` AS modifiedByMiddleName,\r\n" +
			"			 	crd.`email_id` AS createdByEmailId,\r\n" +
			"			 	mrd.`email_id` AS modifiedByEmailId,\r\n" +
			"	w.from_date as fromDate,\r\n" +
			"	w.to_date as toDate,\r\n" +
			" 	w.wcc_type_id as wccTypeId,\r\n" +
			"	tobe.remark AS approvalRemark,\r\n" +
			"	tobe.approved_date AS approvalDate,\r\n" +
			"(SELECT approved_date FROM to_be_approved WHERE `object_name`='WccDetails' AND `object_id`=w.`id` ORDER BY id DESC LIMIT 1) AS lastActionDate\r\n"
			+
			"	\r\n" +
			"	FROM `wcc_details` w\r\n" +
			"		JOIN `wcc_status` ws ON ws.`id`=w.`wcc_status_id`\r\n" +
			"		JOIN `wcc_types` wt ON wt.`id`=w.`wcc_type_id`\r\n" +
			"		JOIN `legal_entity` lg ON w.`service_center_legal_entity_id`=lg.`legal_entity_id`\r\n" +
			"		left JOIN `case_types` ct ON ct.`case_type_id`=w.`case_type_case_type_id`\r\n" +
			"		left JOIN `case_reason_details` cr ON cr.`reason_id`=w.`case_reason_reason_id`\r\n" +
			"		JOIN `service_recource` sr ON sr.`user_email_id`=w.`owner_email_id`\r\n" +
			"		JOIN `contact_details` cod ON sr.`contact_contact_id`=cod.`contact_id`\r\n" +
			"		JOIN `accounts` act ON cod.`account_id`=act.`sap_customer_number`\r\n" +
			"		JOIN `employee_details` crd ON w.`created_by_email_id`=crd.`email_id`\r\n" +
			"		JOIN `employee_details` mrd ON w.`last_modified_by_email_id`=mrd.`email_id`" +
			"	LEFT JOIN `to_be_approved` tobe ON tobe.`object_name`='WccDetails' AND tobe.`object_id`=w.`id` AND (tobe.`action`='Approved' AND tobe.`action`='Rejected') AND tobe.`status`=1 where w.`wcc_status_id`=:statusId\r\n"
			+
			"		", countQuery = "SELECT 	COUNT(*)\r\n" +
					"	FROM `wcc_details` w\r\n" +
					"		JOIN `wcc_status` ws ON ws.`id`=w.`wcc_status_id`\r\n" +
					"		JOIN `wcc_types` wt ON wt.`id`=w.`wcc_type_id`\r\n" +
					"		JOIN `legal_entity` lg ON w.`service_center_legal_entity_id`=lg.`legal_entity_id`\r\n" +
					"		left JOIN `case_types` ct ON ct.`case_type_id`=w.`case_type_case_type_id`\r\n" +
					"		left JOIN `case_reason_details` cr ON cr.`reason_id`=w.`case_reason_reason_id`\r\n" +
					"		JOIN `service_recource` sr ON sr.`user_email_id`=w.`owner_email_id`\r\n" +
					"		JOIN `contact_details` cod ON sr.`contact_contact_id`=cod.`contact_id`\r\n" +
					"		JOIN `accounts` act ON cod.`account_id`=act.`sap_customer_number`\r\n" +
					"		JOIN `employee_details` crd ON w.`created_by_email_id`=crd.`email_id`\r\n" +
					"		JOIN `employee_details` mrd ON w.`last_modified_by_email_id`=mrd.`email_id`" +
					"	LEFT JOIN `to_be_approved` tobe ON tobe.`object_name`='WccDetails' AND tobe.`object_id`=w.`id` AND (tobe.`action`='Approved' AND tobe.`action`='Rejected') AND tobe.`status`=1 where w.`wcc_status_id`=:statusId\r\n"
					+
					"		", nativeQuery = true)
	Page<Map<String, Object>> getAll(@Param("statusId") int statusId, Pageable page);

	@Query(value = "SELECT 	w.`manual_id` AS manulId,\r\n" +
			"	w.`id` AS id,\r\n" +
			" 	w.wcc_amount as wccAmount,\r\n" +
			"   w.wcc_tax_amount as wccTaxAmount,\r\n" +
			"	w.wcc_total_amount as wccTotalAmount,\r\n" +
			"	act.`name` AS vendorName,\r\n" +
			"	act.`sap_customer_number` AS vendorSapNumber,\r\n" +
			"	cod.`first_name` AS vendorUserFirstName,\r\n" +
			"	cod.`last_name` AS vendorUserLastName,\r\n" +
			"	cod.`middle_name` AS vendorUserMiddleName,\r\n" +
			"	cod.`employee_code` AS vendorUserCode,\r\n" +
			"	cod.`contact_id` as vendorContactId,\r\n" +
			"	w.`circle_circle_name` AS circleName,\r\n" +
			"	lg.`legal_entity_name` AS serviceCenter,\r\n" +
			"	wt.`wcc_type_name` AS wccType,\r\n" +
			"	ct.`case_types_name` AS caseType,\r\n" +
			"	cr.`case_reason_details_name` AS caseReasonDetails,\r\n" +
			"	ws.`name` AS wccStatus,\r\n" +
			"	w.`created_date` AS createdDate,\r\n" +
			"	w.`last_modified_by_email_id` AS lastModifiedByEmailId,\r\n" +
			"	w.`last_modified_date` AS lastModifiedDate,\r\n" +
			"	crd.`first_name` AS createdByFirstName,\r\n" +
			"	crd.`last_name` AS createdByLastname,\r\n" +
			"	crd.`middle_name` AS createdbyMiddleName,\r\n" +
			"	mrd.`first_name` AS modifiedByFirstName,\r\n" +
			"	mrd.`last_name` AS modifiedByLastName,\r\n" +
			"	mrd.`middle_name` AS modifiedByMiddleName,\r\n" +
			"	crd.`email_id` AS createdByEmailId,\r\n" +
			"	mrd.`email_id` AS modifiedByEmailId,\r\n" +
			"	w.from_date as fromDate,\r\n" +
			"	w.to_date as toDate,\r\n" +
			" 	w.wcc_type_id as wccTypeId,\r\n" +
			"	tobe.remark AS approvalRemark,\r\n" +
			"	tobe.approved_date AS approvalDate,\r\n" +
			"(SELECT approved_date FROM to_be_approved WHERE `object_name`='WccDetails' AND `object_id`=w.`id` ORDER BY id DESC LIMIT 1) AS lastActionDate\r\n"
			+
			"	\r\n" +
			"	FROM `wcc_details` w\r\n" +
			"		JOIN `wcc_status` ws ON ws.`id`=w.`wcc_status_id`\r\n" +
			"		JOIN `wcc_types` wt ON wt.`id`=w.`wcc_type_id`\r\n" +
			"		JOIN `legal_entity` lg ON w.`service_center_legal_entity_id`=lg.`legal_entity_id`\r\n" +
			"		LEFT JOIN `case_types` ct ON ct.`case_type_id`=w.`case_type_case_type_id`\r\n" +
			"		LEFT JOIN `case_reason_details` cr ON cr.`reason_id`=w.`case_reason_reason_id`\r\n" +
			"		JOIN `service_recource` sr ON sr.`user_email_id`=w.`owner_email_id`\r\n" +
			"		JOIN `contact_details` cod ON sr.`contact_contact_id`=cod.`contact_id`\r\n" +
			"		JOIN `accounts` act ON cod.`account_id`=act.`sap_customer_number`\r\n" +
			"		JOIN `employee_details` crd ON w.`created_by_email_id`=crd.`email_id`\r\n" +
			"		JOIN `employee_details` mrd ON w.`last_modified_by_email_id`=mrd.`email_id`\r\n" +
			"	LEFT JOIN (SELECT * FROM to_be_approved WHERE `object_name`='WccDetails' AND `object_id`=:wccid LIMIT 1) as tobe ON tobe.`object_name`='WccDetails' AND tobe.`object_id`=w.`id` AND tobe.`action`='Approved' AND tobe.`status`=1\r\n"
			+
			"		WHERE w.`id`=:wccid\r\n" +
			"", nativeQuery = true)
	Map<String, Object> getWccById(@Param("wccid") Long id);

	@Query(value = "SELECT \r\n" +
			"	sa.`service_appointment_id`,\r\n" +
			"	sa.`appointment_number` AS appointmentNumber,\r\n" +
			"	act.`sap_customer_number` AS customerSapNumber,\r\n" +
			"	act.`name` AS customerName,\r\n" +
			"	cs.`manual_id` AS caseId,\r\n" +
			"	cty.`case_types_name` AS caseType,\r\n" +
			"	sa.`circle_circle_name` AS circleName,\r\n" +
			"	sr.`name` AS serviceResource,\r\n" +
			"	cr.`case_reason_details_name` AS caseReason,\r\n" +
			"	cs.`case_type_case_type_id`,\r\n" +
			"	po.id as poDetailsId,\r\n" +
			"	po.activity_proce_list_description as activityProceListDescription,\r\n" +
			" 	po.po_number as poNumber,\r\n" +
			" 	cs.close_date as caseCloseDate,\r\n" +
			" 	wcs.amount as amount,\r\n" +
			"	wcs.tax_amount as taxAmount,\r\n" +
			" 	wcs.tax_rate as taxRate,\r\n" +
			"  	wcs.total_amount as totalAmount\r\n" +
			"	\r\n" +
			" FROM `service_appointment` sa \r\n" +
			"	JOIN `case_details` cs ON sa.`case_details_case_id`=cs.`case_id`\r\n" +
			"	JOIN `site_details` st ON cs.`site_site_id`=st.`site_id`\r\n" +
			"	JOIN `accounts` act ON st.`account_sap_customer_number`=act.`sap_customer_number`\r\n" +
			"	JOIN `case_reason_details` cr ON cs.`case_reason_reason_id`=cr.`reason_id`\r\n" +
			"	JOIN `case_types` cty ON cs.`case_type_case_type_id`=cty.`case_type_id`\r\n" +
			"	JOIN `service_recource` sr ON sr.`service_resource_id`=sa.`service_resource_service_resource_id`\r\n" +
			"	JOIN `service_appointment_status` srst ON srst.`status_id`=sa.`status_status_id`\r\n" +
			"	JOIN `wcc_service_appointment` wcs ON wcs.`service_appointment_service_appointment_id`=sa.`service_appointment_id`\r\n"
			+
			"	JOIN `vendor_po_details` po on wcs.po_details_id=po.id \r\n" +
			"	WHERE wcs.`wcc_id`=:wccid", nativeQuery = true)
	List<Map<String, Object>> getServiceAppointmentByWccId(@Param("wccid") Long id);

	// GET PENDING WCC FOR INVOICE BY USER ID
	@Query(value = "SELECT wcc.`id` AS wccId,\r\n" +
			"	wcc.`manual_id` AS wccManualId,\r\n" +
			"	wcc.`wcc_type_id` AS wccTypeId\r\n" +
			"	\r\n" +
			"	 FROM wcc_details wcc WHERE wcc.invoiced=0 AND wcc.owner_email_id=:ownerEmailId AND wcc.wcc_status_id=2", nativeQuery = true)
	List<Map<String, Object>> getPendingToInvoiceWccByUser(@Param("ownerEmailId") String userEmailId);

	@Query(value = "SELECT 	w.`manual_id` AS manulId,\r\n" +
			"	w.invoiced as invoiced,\r\n" +
			"	w.`id` AS id,\r\n" +
			" 	w.wcc_amount as wccAmount,\r\n" +
			"   w.wcc_tax_amount as wccTaxAmount,\r\n" +
			"	w.wcc_total_amount as wccTotalAmount,\r\n" +
			"	act.`name` AS vendorName,\r\n" +
			"	act.`sap_customer_number` AS vendorSapNumber,\r\n" +
			"	cod.`first_name` AS vendorUserFirstName,\r\n" +
			"	cod.`last_name` AS vendorUserLastName,\r\n" +
			"	cod.`middle_name` AS vendorUserMiddleName,\r\n" +
			"	cod.`employee_code` AS vendorUserCode,\r\n" +
			"	cod.`contact_id` as vendorContactId,\r\n" +
			"	w.`circle_circle_name` AS circleName,\r\n" +
			"	lg.`legal_entity_name` AS serviceCenter,\r\n" +
			"	wt.`wcc_type_name` AS wccType,\r\n" +
			"	ct.`case_types_name` AS caseType,\r\n" +
			"	cr.`case_reason_details_name` AS caseReasonDetails,\r\n" +
			"	ws.`name` AS wccStatus,\r\n" +
			"	w.`created_date` AS createdDate,\r\n" +
			"	w.`last_modified_by_email_id` AS lastModifiedByEmailId,\r\n" +
			"	w.`last_modified_date` AS lastModifiedDate,\r\n" +
			"crd.`first_name` AS createdByFirstName,\r\n" +
			"			 	crd.`last_name` AS createdByLastname,\r\n" +
			"			 	crd.`middle_name` AS createdbyMiddleName,\r\n" +
			"			 	mrd.`first_name` AS modifiedByFirstName,\r\n" +
			"			 	mrd.`last_name` AS modifiedByLastName,\r\n" +
			"			 	mrd.`middle_name` AS modifiedByMiddleName,\r\n" +
			"			 	crd.`email_id` AS createdByEmailId,\r\n" +
			"			 	mrd.`email_id` AS modifiedByEmailId,\r\n" +
			"	w.to_date as toDAte,\r\n" +
			"	w.from_date as fromDate,\r\n" +
			" 	w.wcc_type_id as wccTypeId,\r\n" +
			"	tobe.remark AS approvalRemark,\r\n" +
			"	tobe.approved_date AS approvalDate,\r\n" +
			"(SELECT approved_date FROM to_be_approved WHERE `object_name`='WccDetails' AND `object_id`=w.`id` ORDER BY id DESC LIMIT 1) AS lastActionDate\r\n"
			+
			"	\r\n" +
			"	FROM `wcc_details` w\r\n" +
			"		JOIN `wcc_status` ws ON ws.`id`=w.`wcc_status_id`\r\n" +
			"		JOIN `wcc_types` wt ON wt.`id`=w.`wcc_type_id`\r\n" +
			"		JOIN `legal_entity` lg ON w.`service_center_legal_entity_id`=lg.`legal_entity_id`\r\n" +
			"		LEFT JOIN `case_types` ct ON ct.`case_type_id`=w.`case_type_case_type_id`\r\n" +
			"		LEFT JOIN `case_reason_details` cr ON cr.`reason_id`=w.`case_reason_reason_id`\r\n" +
			"		JOIN `service_recource` sr ON sr.`user_email_id`=w.`owner_email_id`\r\n" +
			"		JOIN `contact_details` cod ON sr.`contact_contact_id`=cod.`contact_id`\r\n" +
			"		JOIN `accounts` act ON cod.`account_id`=act.`sap_customer_number`\r\n" +
			"		JOIN `employee_details` crd ON w.`created_by_email_id`=crd.`email_id`\r\n" +
			"			 	JOIN `employee_details` mrd ON w.`last_modified_by_email_id`=mrd.`email_id`\r\n" +
			"	LEFT JOIN `to_be_approved` tobe ON tobe.`object_name`='WccDetails' AND tobe.`object_id`=w.`id` AND (tobe.`action`='Approved' AND tobe.`action`='Rejected') AND tobe.`status`=1\r\n"
			+
			"		WHERE sr.`user_email_id` in (:userEmailId)\r\n" +
			"		", countQuery = "SELECT 	COUNT(*)\r\n" +
					"	FROM `wcc_details` w\r\n" +
					"		JOIN `wcc_status` ws ON ws.`id`=w.`wcc_status_id`\r\n" +
					"		JOIN `wcc_types` wt ON wt.`id`=w.`wcc_type_id`\r\n" +
					"		JOIN `legal_entity` lg ON w.`service_center_legal_entity_id`=lg.`legal_entity_id`\r\n" +
					"		LEFT JOIN `case_types` ct ON ct.`case_type_id`=w.`case_type_case_type_id`\r\n" +
					"		LEFT JOIN `case_reason_details` cr ON cr.`reason_id`=w.`case_reason_reason_id`\r\n" +
					"		JOIN `service_recource` sr ON sr.`user_email_id`=w.`owner_email_id`\r\n" +
					"		JOIN `contact_details` cod ON sr.`contact_contact_id`=cod.`contact_id`\r\n" +
					"		JOIN `accounts` act ON cod.`account_id`=act.`sap_customer_number`\r\n" +
					"		JOIN `employee_details` crd ON w.`created_by_email_id`=crd.`email_id`\r\n" +
					"			 	JOIN `employee_details` mrd ON w.`last_modified_by_email_id`=mrd.`email_id`\r\n" +
					"	LEFT JOIN `to_be_approved` tobe ON tobe.`object_name`='WccDetails' AND tobe.`object_id`=w.`id` AND (tobe.`action`='Approved' AND tobe.`action`='Rejected') AND tobe.`status`=1\r\n"
					+
					"		WHERE sr.`user_email_id` in (:userEmailId)\r\n" +
					"		", nativeQuery = true)
	Page<Map<String, Object>> getAllWccByUserId(@Param("userEmailId") List<String> userEmailId, Pageable page);

	@Query(value = "SELECT 	w.`manual_id` AS manulId,\r\n" +
			"	w.invoiced as invoiced,\r\n" +
			"	w.`id` AS id,\r\n" +
			" 	w.wcc_amount as wccAmount,\r\n" +
			"   w.wcc_tax_amount as wccTaxAmount,\r\n" +
			"	w.wcc_total_amount as wccTotalAmount,\r\n" +
			"	act.`name` AS vendorName,\r\n" +
			"	act.`sap_customer_number` AS vendorSapNumber,\r\n" +
			"	cod.`first_name` AS vendorUserFirstName,\r\n" +
			"	cod.`last_name` AS vendorUserLastName,\r\n" +
			"	cod.`middle_name` AS vendorUserMiddleName,\r\n" +
			"	cod.`employee_code` AS vendorUserCode,\r\n" +
			"	cod.`contact_id` as vendorContactId,\r\n" +
			"	w.`circle_circle_name` AS circleName,\r\n" +
			"	lg.`legal_entity_name` AS serviceCenter,\r\n" +
			"	wt.`wcc_type_name` AS wccType,\r\n" +
			"	ct.`case_types_name` AS caseType,\r\n" +
			"	cr.`case_reason_details_name` AS caseReasonDetails,\r\n" +
			"	ws.`name` AS wccStatus,\r\n" +
			"	w.`created_date` AS createdDate,\r\n" +
			"	w.`last_modified_by_email_id` AS lastModifiedByEmailId,\r\n" +
			"	w.`last_modified_date` AS lastModifiedDate,\r\n" +
			"crd.`first_name` AS createdByFirstName,\r\n" +
			"			 	crd.`last_name` AS createdByLastname,\r\n" +
			"			 	crd.`middle_name` AS createdbyMiddleName,\r\n" +
			"			 	mrd.`first_name` AS modifiedByFirstName,\r\n" +
			"			 	mrd.`last_name` AS modifiedByLastName,\r\n" +
			"			 	mrd.`middle_name` AS modifiedByMiddleName,\r\n" +
			"			 	crd.`email_id` AS createdByEmailId,\r\n" +
			"			 	mrd.`email_id` AS modifiedByEmailId,\r\n" +
			"	w.to_date as toDAte,\r\n" +
			"	w.from_date as fromDate,\r\n" +
			" 	w.wcc_type_id as wccTypeId,\r\n" +
			"	tobe.remark AS approvalRemark,\r\n" +
			"	tobe.approved_date AS approvalDate,\r\n" +
			"(SELECT approved_date FROM to_be_approved WHERE `object_name`='WccDetails' AND `object_id`=w.`id` ORDER BY id DESC LIMIT 1) AS lastActionDate\r\n"
			+
			"	\r\n" +
			"	FROM `wcc_details` w\r\n" +
			"		JOIN `wcc_status` ws ON ws.`id`=w.`wcc_status_id`\r\n" +
			"		JOIN `wcc_types` wt ON wt.`id`=w.`wcc_type_id`\r\n" +
			"		JOIN `legal_entity` lg ON w.`service_center_legal_entity_id`=lg.`legal_entity_id`\r\n" +
			"		LEFT JOIN `case_types` ct ON ct.`case_type_id`=w.`case_type_case_type_id`\r\n" +
			"		LEFT JOIN `case_reason_details` cr ON cr.`reason_id`=w.`case_reason_reason_id`\r\n" +
			"		JOIN `service_recource` sr ON sr.`user_email_id`=w.`owner_email_id`\r\n" +
			"		JOIN `contact_details` cod ON sr.`contact_contact_id`=cod.`contact_id`\r\n" +
			"		JOIN `accounts` act ON cod.`account_id`=act.`sap_customer_number`\r\n" +
			"		JOIN `employee_details` crd ON w.`created_by_email_id`=crd.`email_id`\r\n" +
			"			 	JOIN `employee_details` mrd ON w.`last_modified_by_email_id`=mrd.`email_id`\r\n" +
			"	LEFT JOIN `to_be_approved` tobe ON tobe.`object_name`='WccDetails' AND tobe.`object_id`=w.`id` AND (tobe.`action`='Approved' AND tobe.`action`='Rejected') AND tobe.`status`=1\r\n"
			+
			"		WHERE sr.`user_email_id` in (:userEmailId) and w.`wcc_status_id`=:statusId\r\n" +
			"		", countQuery = "SELECT 	COUNT(*)\r\n" +
					"	FROM `wcc_details` w\r\n" +
					"		JOIN `wcc_status` ws ON ws.`id`=w.`wcc_status_id`\r\n" +
					"		JOIN `wcc_types` wt ON wt.`id`=w.`wcc_type_id`\r\n" +
					"		JOIN `legal_entity` lg ON w.`service_center_legal_entity_id`=lg.`legal_entity_id`\r\n" +
					"		LEFT JOIN `case_types` ct ON ct.`case_type_id`=w.`case_type_case_type_id`\r\n" +
					"		LEFT JOIN `case_reason_details` cr ON cr.`reason_id`=w.`case_reason_reason_id`\r\n" +
					"		JOIN `service_recource` sr ON sr.`user_email_id`=w.`owner_email_id`\r\n" +
					"		JOIN `contact_details` cod ON sr.`contact_contact_id`=cod.`contact_id`\r\n" +
					"		JOIN `accounts` act ON cod.`account_id`=act.`sap_customer_number`\r\n" +
					"		JOIN `employee_details` crd ON w.`created_by_email_id`=crd.`email_id`\r\n" +
					"			 	JOIN `employee_details` mrd ON w.`last_modified_by_email_id`=mrd.`email_id`\r\n" +
					"	LEFT JOIN `to_be_approved` tobe ON tobe.`object_name`='WccDetails' AND tobe.`object_id`=w.`id` AND (tobe.`action`='Approved' AND tobe.`action`='Rejected') AND tobe.`status`=1\r\n"
					+
					"		WHERE sr.`user_email_id` in (:userEmailId) and w.`wcc_status_id`=:statusId\r\n" +
					"		", nativeQuery = true)
	Page<Map<String, Object>> getAllWccByUserId(@Param("userEmailId") List<String> userEmailId,
			@Param("statusId") int statusId, Pageable page);

}
