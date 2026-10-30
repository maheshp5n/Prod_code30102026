package com.pratikshat.exicom.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import com.pratikshat.exicom.entity.BtServiceAppointmentExp;

@Repository
public interface BtServiceAppointmentExpRepository extends JpaRepository<BtServiceAppointmentExp, Long> {

	
	@Query(value ="SELECT * from FROM bt_service_appointment_exp sae left Join bt_service_appointment sa on sae.bt_service_appointment_service_appointment_id=sa.service_appoinment_id left join bt_expense_management em on em.id=sae.bt_expense_management_id where sae.is_deleted=false and em.is_deleted=false and em.id=26",nativeQuery = true)
	List<Map<String,Object>> getServiceAppointmentData();
	
	
	
	@Query(value ="SELECT sae.`id` AS id,sae.`cost_center` AS costCenter, sae.`claim_amount` AS claimAmount,sae.`created_date` AS createdDate,sae.`last_modified_date` AS lastModifiedDate,sae.`remark` AS remark,CONCAT(IFNULL(ed.`first_name`,' '),IFNULL(ed.`middle_name`,' '),IFNULL(ed.`last_name`,''))AS `createdByName`,sae.`created_by_email_id` AS createdByEmailId,CONCAT(IFNULL(ed.`first_name`,' '),IFNULL(ed.`middle_name`,' '),IFNULL(ed.`last_name`,''))AS `modifiedByName`,sae.`last_modified_by_email_id` AS lastModifiedByEmailId,sae.`bt_expense_management_id` AS btExpenseManagementId,em.`manual_id` as btExpenseManagementManualId,bd.`business_domain_id` as businessDomainId,bd.`business_domain_name` as businessDomainName,sae.`owner_email_id` AS ownerEmailId,bsa.`service_appointment_id` AS btServiceAppointmentId,bsa.`manual_id` AS btServiceAppointmentManualId,site.`id` AS siteId,site.`site_name` AS siteName,cd.`circle_name` AS circleName,ca.`id` AS caseId,ca.`manual_id` AS caseManualId FROM bt_service_appointment_exp sae LEFT JOIN employee_details ed on ed.email_id=sae.owner_email_id  LEFT JOIN bt_service_appointment bsa ON sae.bt_service_appointment_service_appointment_id=bsa.service_appointment_id LEFT JOIN `bt_case` ca ON ca.`id`=bsa.`case_details_id` JOIN `bt_site_details` site ON site.`id` =ca.`site_id` JOIN `circle_details` cd ON cd.`circle_name` =bsa.`circle_circle_name` LEFT JOIN bt_expense_management em ON em.id=sae.bt_expense_management_id LEFT JOIN business_domain bd on em.`business_domain_business_domain_id`=bd.`business_domain_id` WHERE sae.is_deleted=FALSE AND sae.id=:id",nativeQuery = true)
	Map<String,Object> getById(@RequestParam("id") long id);
	
	
	
	
	@Query(value ="SELECT sae.`id` AS id,sae.`cost_center` AS costCenter, sae.`claim_amount` AS claimAmount,sae.`created_date` AS createdDate,sae.`last_modified_date` AS lastModifiedDate,sae.`remark` AS remark,CONCAT(IFNULL(ed.`first_name`,' '),IFNULL(ed.`middle_name`,' '),IFNULL(ed.`last_name`,''))AS `createdByName`,sae.`created_by_email_id` AS createdByEmailId,CONCAT(IFNULL(ed.`first_name`,' '),IFNULL(ed.`middle_name`,' '),IFNULL(ed.`last_name`,''))AS `modifiedByName`,sae.`last_modified_by_email_id` AS lastModifiedByEmailId,sae.`bt_expense_management_id` AS btExpenseManagementId,em.`manual_id` as btExpenseManagementManualId,bd.`business_domain_id` as businessDomainId,bd.`business_domain_name` as businessDomainName,sae.`owner_email_id` AS ownerEmailId,bsa.`service_appointment_id` AS btServiceAppointmentId,bsa.`manual_id` AS btServiceAppointmentManualId,site.`id` AS siteId,site.`site_name` AS siteName,cd.`circle_name` AS circleName,ca.`id` AS caseId,ca.`manual_id` AS caseManualId FROM bt_service_appointment_exp sae LEFT JOIN employee_details ed on ed.email_id=sae.owner_email_id  LEFT JOIN bt_service_appointment bsa ON sae.bt_service_appointment_service_appointment_id=bsa.service_appointment_id LEFT JOIN `bt_case` ca ON ca.`id`=bsa.`case_details_id` JOIN `bt_site_details` site ON site.`id` =ca.`site_id` JOIN `circle_details` cd ON cd.`circle_name` =bsa.`circle_circle_name` LEFT JOIN bt_expense_management em ON em.id=sae.bt_expense_management_id LEFT JOIN business_domain bd on em.`business_domain_business_domain_id`=bd.`business_domain_id` WHERE sae.is_deleted=FALSE AND sae.`bt_expense_management_id`=:btExpId",nativeQuery = true,countQuery ="SELECT count(*) FROM bt_service_appointment_exp sae LEFT JOIN employee_details ed on ed.email_id=sae.owner_email_id  LEFT JOIN bt_service_appointment bsa ON sae.bt_service_appointment_service_appointment_id=bsa.service_appointment_id LEFT JOIN `bt_case` ca ON ca.`id`=bsa.`case_details_id` JOIN `bt_site_details` site ON site.`id` =ca.`site_id` JOIN `circle_details` cd ON cd.`circle_name` =bsa.`circle_circle_name` LEFT JOIN bt_expense_management em ON em.id=sae.bt_expense_management_id LEFT JOIN business_domain bd on em.`business_domain_business_domain_id`=bd.`business_domain_id` WHERE sae.is_deleted=FALSE and sae.`bt_expense_management_id`=:btExpId")
	Page<Map<String, Object>> getAll(@RequestParam("btExpId") Long btExpId,Pageable page);
	
//	SELECT bsa.`service_appointment_id` AS btServiceAppointmentId,bsa.`manual_id` AS btServiceAppointmentManualId,site.`id` AS siteId,site.`site_name` AS siteName,cl.`circle_name` AS circleName,ca.`id` AS caseId,ca.`manual_id` AS caseManualId FROM bt_service_appointment bsa LEFT JOIN `bt_case` ca ON ca.`id`=bsa.`case_details_id` JOIN `bt_site_details` site ON site.`id` =ca.`site_id` JOIN `circle_details` cl ON cl.`circle_name` =bsa.`circle_circle_name` WHERE bsa.`service_appointment_expense`=FALSE
	
//	@Query(value ="SELECT  appointment.`service_appointment_id` AS btServiceAppointmentId,\r\n" + 
//			"	appointment.`manual_id` AS btServiceAppointmentManualId,\r\n" + 
//			"	site.`id` AS siteId,site.`site_name` AS siteName,cd.`circle_name` AS circleName,ca.`id` AS caseId,ca.`manual_id` AS caseManualId\r\n" + 
//			"	FROM `bt_service_appointment` appointment  \r\n" + 
//			"	JOIN `bt_case` ca ON ca.`id`=appointment.`case_details_id` \r\n" + 
//			"	JOIN `service_recource` serRes ON serRes.`service_resource_id` =appointment.`service_resource_service_resource_id` \r\n" + 
//			"	JOIN `bt_site_details` site ON site.`id` =ca.`site_id` \r\n" + 
//			"	JOIN `service_appointment_status` ser ON ser.`status_id`=`status_status_id` \r\n" + 
//			"	JOIN `circle_details` cd ON cd.`circle_name` =appointment.`circle_circle_name` \r\n" + 
//			"	JOIN `claim_status` cstatus ON cstatus.`claim_status_id`=appointment.`claim_status_claim_status_id` \r\n" + 
//			"	LEFT JOIN `bt_service_payout` sp ON sp.`id`=appointment.`bt_service_payout_id`\r\n" + 
//			"	WHERE serRes.`user_email_id`=:userEmailId AND appointment.`service_appointment_expense`=FALSE AND claim_status_id IN(3,7) AND appointment.status_status_id=4",nativeQuery = true)
//	List<Map<String,Object>> getBtServiceAppointmentDetailByExpId(@RequestParam("userEmailId") String userEmailId);


	
	@Query(value ="SELECT  appointment.`service_appointment_id` AS btServiceAppointmentId,\r\n" + 
			"	appointment.`manual_id` AS btServiceAppointmentManualId,\r\n" + 
			"	site.`id` AS siteId,site.`site_name` AS siteName,cd.`circle_name` AS circleName,ca.`id` AS caseId,ca.`manual_id` AS caseManualId\r\n" + 
			"	FROM `bt_service_appointment` appointment  \r\n" + 
			"	JOIN `bt_case` ca ON ca.`id`=appointment.`case_details_id` \r\n" + 
			"	JOIN `service_recource` serRes ON serRes.`service_resource_id` =appointment.`service_resource_service_resource_id` \r\n" + 
			"	JOIN `bt_site_details` site ON site.`id` =ca.`site_id` \r\n" + 
			"	JOIN `service_appointment_status` ser ON ser.`status_id`=`status_status_id` \r\n" + 
			"	JOIN `circle_details` cd ON cd.`circle_name` =appointment.`circle_circle_name` \r\n" + 
			"	JOIN `claim_status` cstatus ON cstatus.`claim_status_id`=appointment.`claim_status_claim_status_id` \r\n" + 
			"	LEFT JOIN `bt_service_payout` sp ON sp.`id`=appointment.`bt_service_payout_id`\r\n" + 
			"	WHERE serRes.`user_email_id`=:userEmailId AND appointment.`service_appointment_expense`=FALSE AND claim_status_id IN(3,7) AND appointment.status_status_id=4 AND appointment.`billing_required`=TRUE",nativeQuery = true)
	List<Map<String,Object>> getBtServiceAppointmentDetailByExpId(@RequestParam("userEmailId") String userEmailId);
}
