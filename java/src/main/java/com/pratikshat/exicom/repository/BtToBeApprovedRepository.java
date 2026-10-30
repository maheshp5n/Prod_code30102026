package com.pratikshat.exicom.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.BtToBeApproved;

@Repository
public interface BtToBeApprovedRepository extends JpaRepository<BtToBeApproved, Long>,BtToBeApprovedRepositoryCustome {
	@Query(value="SELECT \r\n" + 
			"	btt.`id`,\r\n" + 
			"	btt.`created_date`,\r\n" + 
			"	btt.`last_modified_date`,\r\n" + 
			"	btt.`object_name`,\r\n" + 
			"	btt.`action`,\r\n" + 
			"	btt.`approved_date`,\r\n" + 
			"	btt.`assigned_date`,\r\n" + 
			"	btt.`assigned_to`,\r\n" + 
			"	btt.`object_id`,\r\n" + 
			"	btt.`object_manual_id`,\r\n" + 
			"	btt.`object_url`,\r\n" + 
			"	btt.`previous_remarks`,\r\n" + 
			"	btt.`remark`,\r\n" + 
			"	btt.`stage`,\r\n" + 
			"	btt.`status`,\r\n" + 
			"	btt.`created_by_email_id`,\r\n" + 
			"	btt.`last_modified_by_email_id`,\r\n" + 
			"	btt.`action_by_email_id`,\r\n" + 
			"	btt.`initiator_email_id`,\r\n" + 
			"	btt.`stapes_id`,\r\n" + 
			"	obj.`object_lable_name`\r\n" + 
			"	\r\n" + 
			" FROM `bt_to_be_approved` btt JOIN `object_definition` obj ON btt.`object_name`=obj.`object_name` WHERE btt.`status`=FALSE and (btt.assigned_to like :role or btt.assigned_to like :user)",countQuery = "SELECT \r\n" + 
					"count(*) as ct\r\n"+
					"	\r\n" + 
					" FROM `bt_to_be_approved` btt JOIN `object_definition` obj ON btt.`object_name`=obj.`object_name` WHERE btt.`status`=FALSE and (btt.assigned_to like :role or btt.assigned_to like :user)",nativeQuery=true)
	public Page<Map<String,Object>> getPendingPage(@Param("role")String role,@Param("user")String user,Pageable page);
	
	@Query(value="SELECT tobe.`previous_remarks` AS previousRemarks,   \r\n"
			+ "	\r\n"
			+ "			 			 tobe.`assigned_to` AS assignedTo,   \r\n"
			+ "			 			 	tobe.`stage` AS stage,   \r\n"
			+ "			 			 	IFNULL(tobe.`action`,'Submitted') AS 'action',   \r\n"
			+ "			 			 	tobe.`remark` AS remark,   \r\n"
			+ "			 			 \r\n"
			+ "			 			   			 	aps.`stape_name` AS stageName,   \r\n"
			+ "			  \r\n"
			+ "			 			   acemp.`first_name`AS actionByFirstName,   \r\n"
			+ "			 			 	acemp.`last_name` AS actionByLastName,   \r\n"
			+ "			 			 	acemp.`middle_name` AS actionByMiddleName,   \r\n"
			+ "			 			 	acemp.`email_id` AS actionByEmailId,  \r\n"
			+ "			 			 	  \r\n"
			+ "			 			 \r\n"
			+ "			 			 	tobe.`approved_date` AS approvedDate,  \r\n"
			+ "			 			 	tobe.`assigned_date` AS assignedDate   \r\n"
			+ "			 			 	FROM `bt_to_be_approved` tobe   \r\n"
			+ "			 			 	JOIN  `bt_approval_stapes` aps ON tobe.`stapes_id`=aps.`id`   \r\n"
			+ "			 			 	JOIN `bt_approval` apr ON apr.`id`=aps.`approval_id` AND tobe.`stage`=aps.`stape_number`   \r\n"
			+ "			 			 	LEFT JOIN `employee_details` acemp ON acemp.`email_id`= tobe.`action_by_email_id`  \r\n"
			+ "			 			   WHERE tobe.`object_name`=:objectname AND tobe.`object_id`=:objectId  ORDER BY tobe.`assigned_date` DESC",nativeQuery=true)
	public List<Map<String,Object>> getApprovalHistory(@Param("objectname")String objectName,@Param("objectId") long id);

	@Query(value="SELECT \r\n"
			+ "	btt.`id`,\r\n"
			+ "	btt.`created_date`,\r\n"
			+ "	btt.`last_modified_date`,\r\n"
			+ "	btt.`object_name`,\r\n"
			+ "	btt.`action`,\r\n"
			+ "	btt.`approved_date`,\r\n"
			+ "	btt.`assigned_date`,\r\n"
			+ "	btt.`assigned_to`,\r\n"
			+ "	btt.`object_id`,\r\n"
			+ "	btt.`object_manual_id`,\r\n"
			+ "	btt.`object_url`,\r\n"
			+ "	btt.`previous_remarks`,\r\n"
			+ "	btt.`remark`,\r\n"
			+ "	btt.`stage`,\r\n"
			+ "	btt.`status`,\r\n"
			+ "	btt.`created_by_email_id`,\r\n"
			+ "	btt.`last_modified_by_email_id`,\r\n"
			+ "	btt.`action_by_email_id`,\r\n"
			+ "	btt.`initiator_email_id`,\r\n"
			+ "	btt.`stapes_id`\r\n"
			+ "		FROM `bt_to_be_approved` btt\r\n"
			+ "        where btt.`status`=0 and btt.`object_id`=:objectId and btt.`object_name`=:objectname and (btt.assigned_to like :role or btt.assigned_to like :userName)",nativeQuery=true)
	public Map<String, Object> getApprovalOnIdAndName(@Param("objectId")long objectId,@Param("objectname") String objectName,@Param("role") String role,@Param("userName") String userName );

}
