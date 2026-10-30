package com.pratikshat.exicom.repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.entity.ToBeApproved;

@Repository
public interface ToBeApprovedRepository extends JpaRepository<ToBeApproved, Long>,ToBeApprovedRepositoryCustome {
	Page<ToBeApproved> findByAssignedToAndStatus(com.pratikshat.exicom.entity.EmployeeDetails user,boolean s,Pageable page);
	
	Page<ToBeApproved> findByStatusAndAssignedTo_emailIdIn(boolean s,List<String> user,Pageable page);
	
	
	
	@Query(value="SELECT tobe.`previous_remarks` AS previousRemarks,\r\n" + 
			"	emp.`first_name`AS assignedToFirstName,\r\n" + 
			"	emp.`last_name` AS assignedToLastName,\r\n" + 
			"	emp.`middle_name` AS assignedToMiddleName,\r\n" + 
			"	emp.`email_id` AS assignedToEmailId,\r\n" + 
			"	tobe.`stage` AS stage,\r\n" + 
			"	aps.`step_name` AS stageName,\r\n" + 
			"	ifnull(tobe.`action`,'Submitted') AS 'action',\r\n" + 
			"	tobe.`remark` AS remark,\r\n" + 
			
			"	acemp.`first_name`AS actionByFirstName,\r\n" + 
			"	acemp.`last_name` AS actionByLastName,\r\n" + 
			"	acemp.`middle_name` AS actionByMiddleName,\r\n" + 
			"	acemp.`email_id` AS actionByEmailId,\r\n" + 
			
			"	tobe.`approved_date` AS approvedDate,\r\n" +
			"	tobe.`assigned_date` AS assignedDate\r\n" + 
			"	FROM `to_be_approved` tobe\r\n" + 
			"	JOIN `approval` apr ON tobe.`approval_rule_id`=apr.`id`\r\n" + 
			"	JOIN `approval_steps` aps ON aps.`approval_id`=apr.`id` AND tobe.`stage`=aps.`approved_stage`\r\n" + 
			"	JOIN `employee_details` emp ON emp.`email_id`= tobe.`assigned_to_email_id`"+
			"	LEFT JOIN `employee_details` acemp ON acemp.`email_id`= tobe.`action_by_email_id`"+
			 " WHERE tobe.`object_name`=:objectName AND tobe.`object_id`=:objectId ORDER BY tobe.`assigned_date` DESC",nativeQuery=true)
	java.util.List<java.util.Map<String,Object>> findByObjectNameAndObjectId(@Param("objectName")String objectName,@Param("objectId")long objectId);
	
	@Query(value="SELECT id AS id,\r\n" + 
			"	approval_rule_id AS approvalRuleId,\r\n" + 
			"	object_name AS ObjectName,\r\n" + 
			"	object_id AS objectId,\r\n" + 
			"	object_manual_id AS objectManualId,\r\n" + 
			"	object_url AS objectUrl,\r\n" + 
			"	`previous_remarks` AS previousRemarks,\r\n" + 
			"	`assigned_to_email_id` AS assignedTo,\r\n" + 
			"	`status` AS `status`,\r\n" + 
			"	stage AS stage,\r\n" + 
			"	`action` AS `action`,\r\n" + 
			"	remark AS remark,\r\n" + 
			"	`approved_date` AS approvedDate,\r\n" + 
			"	`assigned_date` AS assignedDate\r\n" + 
			" FROM to_be_approved WHERE assigned_to_email_id in :emailId AND `status`=:status AND object_name=:objectName AND object_id=:objectId",nativeQuery=true)
	Map<String,Object> findByAssignedToAndStatusAndObjectNameAndObjectId(@Param("emailId")List<String> user,@Param("status")boolean status,@Param("objectName")String objectName,@Param("objectId")long objectId);
}
