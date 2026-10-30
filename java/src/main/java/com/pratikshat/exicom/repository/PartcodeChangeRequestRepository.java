package com.pratikshat.exicom.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.PartcodeChangeRequest;

@Repository
public interface PartcodeChangeRequestRepository extends JpaRepository<PartcodeChangeRequest, Long> {
	
	final static String FLOW_TYPE_SA="FLOW_TYPE_SA";
	final static String FLOW_TYPE_NON_SA="FLOW_TYPE_NON_SA";
	
	@Query("select rq from PartcodeChangeRequest rq left join rq.partcodeChangeSa sa where rq.id=:id")
	Optional<PartcodeChangeRequest> findById(@Param("id")Long id);
	
	List<PartcodeChangeRequest> findByIdIn(List<Long>ids);
	
	@Query(value="SELECT \r\n" + 
			"	req.`id`,\r\n" + 
			"	req.manual_id,\r\n"+
			"	req.`approved_status`,\r\n" + 
			"	req.`assigned_to`,\r\n" + 
			"	req.`circle`,\r\n" + 
			"	req.`consumed_product_serial_number`,\r\n" + 
			"	req.`create_date`,\r\n" + 
			"	req.`engineer_name`,\r\n" + 
			"	req.`faulty_product_serial_number`,\r\n" + 
			"	req.`modified_date`,\r\n" + 
			"	req.`price_variance`,\r\n" + 
			"	req.`request_date`,\r\n" + 
			"	req.`sa_id`,\r\n" + 
			"	req.`sa_manual_id`,\r\n" + 
			"	req.`location_name`,\r\n"+
			" 	req.`legal_entity`,\r\n"+
			"	req.`consumed_product_product_part_code`,\r\n" + 
			"	cprd.`pdoduct_description` AS consumed_product_description,\r\n" + 
			"	cprd.`product_price` AS consumed_product_price,\r\n" + 
			"	req.`created_by_email_id`,\r\n" + 
			"	req.`faulty_product_product_part_code`,\r\n" + 
			"	fprd.`pdoduct_description` AS faulty_product_description,\r\n" + 
			"	fprd.`product_price` AS faulty_product_price,\r\n" + 
			"	req.`flow_type`,\r\n"+
			"	req.`initiated_by_email_id`,\r\n" + 
			"	req.`modified_by_email_id`\r\n" + 
			"	\r\n" + 
			" FROM `partcode_change_request` req\r\n" + 
			"	left JOIN `products` cprd ON cprd.`product_part_code`=req.`consumed_product_product_part_code`\r\n" + 
			"	JOIN `products` fprd ON fprd.`product_part_code`=req.`faulty_product_product_part_code` WHERE req.`approved_status`=FALSE AND (req.`assigned_to` LIKE :rolename or req.`assigned_to` like :useremail or req.`initiated_by_email_id`=:initieator) order by req.`modified_date` desc",nativeQuery=true)
	List<Map<String,Object>> findPendingRequest(@Param("rolename")String rolename,@Param("useremail")String useremail,@Param("initieator")String initiatorEmailId);
	
	@Query(value="SELECT \r\n" + 
			"	req.`id`,\r\n" + 
			"	req.manual_id,\r\n"+
			"	req.`approved_status`,\r\n" + 
			"	req.`assigned_to`,\r\n" + 
			"	req.`circle`,\r\n" + 
			"	req.`consumed_product_serial_number`,\r\n" + 
			"	req.`create_date`,\r\n" + 
			"	req.`engineer_name`,\r\n" + 
			"	req.`faulty_product_serial_number`,\r\n" + 
			"	req.`modified_date`,\r\n" + 
			"	req.`price_variance`,\r\n" + 
			"	req.`request_date`,\r\n" + 
			"	req.`sa_id`,\r\n" + 
			"	req.`sa_manual_id`,\r\n" + 
			"	req.`location_name`,\r\n"+
			" 	req.`legal_entity`,\r\n"+
			"	req.`consumed_product_product_part_code`,\r\n" + 
			"	cprd.`pdoduct_description` AS consumed_product_description,\r\n" + 
			"	cprd.`product_price` AS consumed_product_price,\r\n" + 
			"	req.`created_by_email_id`,\r\n" + 
			"	req.`faulty_product_product_part_code`,\r\n" + 
			"	fprd.`pdoduct_description` AS faulty_product_description,\r\n" + 
			"	fprd.`product_price` AS faulty_product_price,\r\n" + 
			"	req.`flow_type`,\r\n"+
			"	req.`initiated_by_email_id`,\r\n" + 
			"	req.`modified_by_email_id`\r\n" + 
			"	\r\n" + 
			" FROM `partcode_change_request` req\r\n" + 
			"	left JOIN `products` cprd ON cprd.`product_part_code`=req.`consumed_product_product_part_code`\r\n" + 
			"	JOIN `products` fprd ON fprd.`product_part_code`=req.`faulty_product_product_part_code` WHERE req.`approved_status`=FALSE AND req.id=:id",nativeQuery=true)
	Map<String,Object> findPendingRequestById(@Param("id")Long id);
	
	@Query(value="SELECT   \r\n" + 
			"				req.`id`,  \r\n" + 
			"				req.manual_id,\r\n"+
			"				req.`approved_status`,  \r\n" + 
			"				req.`assigned_to`,  \r\n" + 
			"				req.`circle`,  \r\n" + 
			"				req.`consumed_product_serial_number`,  \r\n" + 
			"				req.`create_date`,  \r\n" + 
			"				req.`engineer_name`,  \r\n" + 
			"				req.`faulty_product_serial_number`,  \r\n" + 
			"				req.`modified_date`,  \r\n" + 
			"				req.`price_variance`,  \r\n" + 
			"				req.`request_date`,  \r\n" + 
			"				req.`sa_id`,  \r\n" + 
			"				req.`sa_manual_id`,  \r\n" + 
			"				req.`location_name`,\r\n"+
			" 				req.`legal_entity`,\r\n"+
			"				req.`consumed_product_product_part_code`,  \r\n" + 
			"				cprd.`pdoduct_description` AS consumed_product_description,  \r\n" + 
			"				cprd.`product_price` AS consumed_product_price,  \r\n" + 
			"				req.`created_by_email_id`,  \r\n" + 
			"				req.`faulty_product_product_part_code`,  \r\n" + 
			"				fprd.`pdoduct_description` AS faulty_product_description,  \r\n" + 
			"				fprd.`product_price` AS faulty_product_price,  \r\n" + 
			"				req.`initiated_by_email_id`,  \r\n" + 
			"				req.`modified_by_email_id`,  \r\n" + 
			"				apr.`approval_date`,  \r\n" + 
			"				apr.`new_part_code`,  \r\n" + 
			"				apr.`new_serialnumber`,  \r\n" + 
			"				apr.`old_partcode`,  \r\n" + 
			"				apr.`old_serial_number`,  \r\n" + 
			"				req.`flow_type`,\r\n" + 
			"				apr.`created_by_email_id` as `approved_by_email_id`,  \r\n" + 
			"				(select `pdoduct_description` from `products` where product_part_code=apr.`old_partcode`) AS `old_product_description`,  \r\n" + 
			"				(select `pdoduct_description` from `products` where product_part_code=apr.`new_part_code`) AS `new_product_description`,  \r\n" + 
			"				apr.mblnr,\r\n" + 
			"				apr.mblnr_c,\r\n" + 
			"				apr.mblnr_u,\r\n" + 
			"				apr.mjahr,\r\n" + 
			"				apr.mjahr_c,\r\n" + 
			"				apr.mjahr_u,\r\n" + 
			"				apr.remark_c,\r\n" + 
			"				apr.remark_u\r\n" + 
			"			 FROM `partcode_change_request` req  \r\n" + 
			"				left JOIN `products` cprd ON cprd.`product_part_code`=req.`consumed_product_product_part_code`  \r\n" + 
			"				JOIN `products` fprd ON fprd.`product_part_code`=req.`faulty_product_product_part_code`   \r\n" + 
			"				JOIN `partcode_change_request_approval` apr ON apr.id=req.id  \r\n" + 
			"				WHERE req.`assigned_to` LIKE :rolename or req.`assigned_to` like :username or req.initiated_by_email_id=:initiar order by req.`modified_date`",nativeQuery=true)
	List<Map<String,Object>>  findApprovedAequestBy(@Param("rolename")String roleName,@Param("username")String username,@Param("initiar")String initiarEmailId);
	
	@Query(value="SELECT   \r\n" + 
			"				req.`id`,  \r\n" + 
			"				req.manual_id,\r\n"+
			"				req.`approved_status`,  \r\n" + 
			"				req.`assigned_to`,  \r\n" + 
			"				req.`circle`,  \r\n" + 
			"				req.`consumed_product_serial_number`,  \r\n" + 
			"				req.`create_date`,  \r\n" + 
			"				req.`engineer_name`,  \r\n" + 
			"				req.`faulty_product_serial_number`,  \r\n" + 
			"				req.`modified_date`,  \r\n" + 
			"				req.`price_variance`,  \r\n" + 
			"				req.`request_date`,  \r\n" + 
			"				req.`sa_id`,  \r\n" + 
			"				req.`sa_manual_id`,  \r\n" + 
			"				req.`location_name`,\r\n"+
			" 				req.`legal_entity`,\r\n"+
			"				req.`consumed_product_product_part_code`,  \r\n" + 
			"				cprd.`pdoduct_description` AS consumed_product_description,  \r\n" + 
			"				cprd.`product_price` AS consumed_product_price,  \r\n" + 
			"				req.`created_by_email_id`,  \r\n" + 
			"				req.`faulty_product_product_part_code`,  \r\n" + 
			"				fprd.`pdoduct_description` AS faulty_product_description,  \r\n" + 
			"				fprd.`product_price` AS faulty_product_price,  \r\n" + 
			"				req.`initiated_by_email_id`,  \r\n" + 
			"				req.`modified_by_email_id`,  \r\n" + 
			"				apr.`approval_date`,  \r\n" + 
			"				apr.`new_part_code`,  \r\n" + 
			"				apr.`new_serialnumber`,  \r\n" + 
			"				apr.`old_partcode`,  \r\n" + 
			"				apr.`old_serial_number`,  \r\n" + 
			"				req.`flow_type`,\r\n" + 
			"				apr.`created_by_email_id` as `approved_by_email_id`,  \r\n" + 
			"				(select `pdoduct_description` from `products` where product_part_code=apr.`old_partcode`) AS `old_product_description`,  \r\n" + 
			"				(select `pdoduct_description` from `products` where product_part_code=apr.`new_part_code`) AS `new_product_description`,  \r\n" + 
			"				apr.mblnr,\r\n" + 
			"				apr.mblnr_c,\r\n" + 
			"				apr.mblnr_u,\r\n" + 
			"				apr.mjahr,\r\n" + 
			"				apr.mjahr_c,\r\n" + 
			"				apr.mjahr_u,\r\n" + 
			"				apr.remark_c,\r\n" + 
			"				apr.remark_u\r\n" + 
			"			 FROM `partcode_change_request` req  \r\n" + 
			"				left JOIN `products` cprd ON cprd.`product_part_code`=req.`consumed_product_product_part_code`  \r\n" + 
			"				JOIN `products` fprd ON fprd.`product_part_code`=req.`faulty_product_product_part_code`   \r\n" + 
			"				JOIN `partcode_change_request_approval` apr ON apr.id=req.id  \r\n" + 
			"				WHERE req.`assigned_to` LIKE :rolename or req.`assigned_to` like :username or req.initiated_by_email_id=:initiar order by req.`modified_date`",nativeQuery=true,countQuery = "SELECT  count(*) as ctn \r\n"+
					"			 FROM `partcode_change_request` req  \r\n" + 
					"				left JOIN `products` cprd ON cprd.`product_part_code`=req.`consumed_product_product_part_code`  \r\n" + 
					"				JOIN `products` fprd ON fprd.`product_part_code`=req.`faulty_product_product_part_code`   \r\n" + 
					"				JOIN `partcode_change_request_approval` apr ON apr.id=req.id  \r\n" + 
					"				WHERE req.`assigned_to` LIKE :rolename or req.`assigned_to` like :username or req.initiated_by_email_id=:initiar")
	Page<Map<String,Object>>  findApprovedAequestBy(@Param("rolename")String roleName,@Param("username")String username,@Param("initiar")String initiarEmailId,Pageable page);
	
	
}
