package com.pratikshat.exicom.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.ServiceOrderJobCard;
@Repository
public interface ServiceOrderJobCardRepository extends JpaRepository<ServiceOrderJobCard, Long>,ServiceOrderJobCardRepositoryCustome {
	
	//RETURN LIST OF JOB CARD ACCORDING TO USER LIST
	
	@Query(value="SELECT \r\n" + 
			"	jcard.`assigned_date` AS assignedDate,\r\n" + 
			"	jcard.`item_id_item_id` AS itemId,\r\n" + 
			"	jcard.`last_modified_by_email_id` AS lastMdifiedByEmailId,\r\n" + 
			"	jcard.`last_modified_date` AS lastModifiedDate,\r\n" + 
			"	jcard.`manual_id` AS jobCardManualId,\r\n" + 
			"	jcard.`id` AS jobCardId,\r\n" + 
			"	jcard.`owner_email_id` AS jobCardOwnerEmailId,\r\n" + 
			"	jcard.`repair_eng_service_resource_id` AS resourcId,\r\n" + 
			"	jcard.`reparied` AS reparied,\r\n" + 
			" 	jcard.`returned` as returned,\r\n"+
			"	item.`circle` AS itemCircle,\r\n" + 
			"	item.`customer_inventory` AS customerInventory,\r\n" + 
			"	item.`customer_name` AS customerName,\r\n" + 
			"	item.`customer_sap` AS customerSapName,\r\n" + 
			"	item.`id` AS serviceOrderItemId,\r\n" + 
			"	item.`product_product_part_code` AS partCode,\r\n" + 
			"	item.`serial_number` AS partCodeSerialNumber,\r\n" + 
			"	item.`service_order_id` AS serviceOrderId,\r\n" + 
			"	res.`service_resource_id` AS engId,\r\n" + 
			"	res.`name` AS engName,\r\n" + 
			"	prd.`pdoduct_description` as productDescription,\r\n"+
			"	prd.`product_type` as productType,\r\n"+
			"	pstate.`product_state_name` AS state,\r\n"+
			"	item.`remark` AS `remarks`,\r\n"+
			"	item.`lot_no` AS `lotNo`\r\n"+
			"\r\n" + 
			"	FROM `service_order_job_card` jcard\r\n" + 
			"	JOIN `service_order_item_receive` sitem ON sitem.`item_id`=jcard.`item_id_item_id`\r\n" + 
			"	JOIN `service_order_item` item ON item.`id`=sitem.`item_id`\r\n" + 
			"	JOIN `service_recource` res ON res.`service_resource_id`=jcard.`repair_eng_service_resource_id`\r\n" + 
			" 	JOIN `products` prd ON prd.`product_part_code`=item.`product_product_part_code`\r\n"+
			"	JOIN `product_line_item_product_state` pstate ON pstate.`product_state_id`=item.`state_product_state_id`"+
			"	\r\n" + 
			"	WHERE jcard.job_card_status <>'Archived' and jcard.`owner_email_id` IN (:emailUserList)\r\n" + 
			"	",nativeQuery=true,countQuery = "SELECT COUNT(*) AS countQuery\r\n" + 
					"	\r\n" + 
					"	FROM `service_order_job_card` jcard\r\n" + 
					"	JOIN `service_order_item_receive` sitem ON sitem.`item_id`=jcard.`item_id_item_id`\r\n" + 
					"	JOIN `service_order_item` item ON item.`id`=sitem.`item_id`\r\n" + 
					"	JOIN `service_recource` res ON res.`service_resource_id`=jcard.`repair_eng_service_resource_id`\r\n" + 
					" 	JOIN `products` prd ON prd.`product_part_code`=item.`product_product_part_code`\r\n"+
					"	JOIN `product_line_item_product_state` pstate ON pstate.`product_state_id`=item.`state_product_state_id`"+
					"	\r\n" + 
					"	WHERE  jcard.job_card_status <>'Archived' and jcard.`owner_email_id` IN (:emailUserList)")
	Page<Map<String,Object>> getJobCardByUserList(@Param("emailUserList")List<String>userlist,Pageable page);
	
	
	@Query(value="SELECT \r\n" + 
			"	jcard.`assigned_date` AS assignedDate,\r\n" + 
			"	jcard.`item_id_item_id` AS itemId,\r\n" + 
			"	jcard.`last_modified_by_email_id` AS lastMdifiedByEmailId,\r\n" + 
			"	jcard.`last_modified_date` AS lastModifiedDate,\r\n" + 
			"	jcard.`manual_id` AS jobCardManualId,\r\n" + 
			"	jcard.`id` AS jobCardId,\r\n" + 
			"	jcard.`owner_email_id` AS jobCardOwnerEmailId,\r\n" + 
			"	jcard.`repair_eng_service_resource_id` AS resourcId,\r\n" + 
			"	jcard.`reparied` AS reparied,\r\n" + 
			" 	jcard.`returned` as returned,\r\n"+
			"	item.`circle` AS itemCircle,\r\n" + 
			"	item.`customer_inventory` AS customerInventory,\r\n" + 
			"	item.`customer_name` AS customerName,\r\n" + 
			"	item.`customer_sap` AS customerSapName,\r\n" + 
			"	item.`id` AS serviceOrderItemId,\r\n" + 
			"	item.`product_product_part_code` AS partCode,\r\n" + 
			"	item.`serial_number` AS partCodeSerialNumber,\r\n" + 
			"	item.`service_order_id` AS serviceOrderId,\r\n" + 
			"	res.`service_resource_id` AS engId,\r\n" + 
			"	res.`name` AS engName,\r\n" + 
			"	prd.`pdoduct_description` as productDescription,\r\n"+
			"	prd.`product_type` as productType,\r\n"+
			"	pstate.`product_state_name` AS state,\r\n"+
			"	item.`remark` AS `remarks`,\r\n"+
			"	item.`lot_no` AS `lotNo`\r\n"+
			"\r\n" + 
			"	FROM `service_order_job_card` jcard\r\n" + 
			"	JOIN `service_order_item_receive` sitem ON sitem.`item_id`=jcard.`item_id_item_id`\r\n" + 
			"	JOIN `service_order_item` item ON item.`id`=sitem.`item_id`\r\n" + 
			"	JOIN `service_recource` res ON res.`service_resource_id`=jcard.`repair_eng_service_resource_id`\r\n" + 
			" 	JOIN `products` prd ON prd.`product_part_code`=item.`product_product_part_code`\r\n"+
			"	JOIN `product_line_item_product_state` pstate ON pstate.`product_state_id`=item.`state_product_state_id`"+
			"	\r\n" + 
			"	WHERE jcard.job_card_status <>'Archived' and jcard.`owner_email_id` IN (:emailUserList) and jcard.reparied=:repair\r\n" + 
			"	",nativeQuery=true,countQuery = "SELECT COUNT(*) AS countQuery\r\n" + 
					"	\r\n" + 
					"	FROM `service_order_job_card` jcard\r\n" + 
					"	JOIN `service_order_item_receive` sitem ON sitem.`item_id`=jcard.`item_id_item_id`\r\n" + 
					"	JOIN `service_order_item` item ON item.`id`=sitem.`item_id`\r\n" + 
					"	JOIN `service_recource` res ON res.`service_resource_id`=jcard.`repair_eng_service_resource_id`\r\n" + 
					" 	JOIN `products` prd ON prd.`product_part_code`=item.`product_product_part_code`\r\n"+
					"	JOIN `product_line_item_product_state` pstate ON pstate.`product_state_id`=item.`state_product_state_id`"+
					"	\r\n" + 
					"	WHERE jcard.`owner_email_id` IN (:emailUserList) and jcard.reparied=:repair")
	Page<Map<String,Object>> getJobCardByUserListByStatus(@Param("emailUserList")List<String>userlist,@Param("repair") boolean reparied,Pageable page);
	
	//FOR VIEW ALL 
	//RETURN ALL JOB CARD LIST
	
	@Query(value="SELECT \r\n" + 
			"	jcard.`assigned_date` AS assignedDate,\r\n" + 
			"	jcard.`item_id_item_id` AS itemId,\r\n" + 
			"	jcard.`last_modified_by_email_id` AS lastMdifiedByEmailId,\r\n" + 
			"	jcard.`last_modified_date` AS lastModifiedDate,\r\n" + 
			"	jcard.`manual_id` AS jobCardManualId,\r\n" + 
			"	jcard.`id` AS jobCardId,\r\n" + 
			"	jcard.`owner_email_id` AS jobCardOwnerEmailId,\r\n" + 
			"	jcard.`repair_eng_service_resource_id` AS resourcId,\r\n" + 
			"	jcard.`reparied` AS reparied,\r\n" + 
			" 	jcard.`returned` as returned,\r\n"+
			"	item.`circle` AS itemCircle,\r\n" + 
			"	item.`customer_inventory` AS customerInventory,\r\n" + 
			"	item.`customer_name` AS customerName,\r\n" + 
			"	item.`customer_sap` AS customerSapName,\r\n" + 
			"	item.`id` AS serviceOrderItemId,\r\n" + 
			"	item.`product_product_part_code` AS partCode,\r\n" + 
			"	item.`serial_number` AS partCodeSerialNumber,\r\n" + 
			"	item.`service_order_id` AS serviceOrderId,\r\n" + 
			"	res.`service_resource_id` AS engId,\r\n" + 
			"	res.`name` AS engName,\r\n" + 
			"	prd.`pdoduct_description` as productDescription,\r\n"+
			"	prd.`product_type` as productType,\r\n"+
			"	pstate.`product_state_name` AS state,\r\n"+
			"	item.`remark` AS `remarks`,\r\n"+
			"	item.`lot_no` AS `lotNo`\r\n"+
			"\r\n" + 
			"	FROM `service_order_job_card` jcard\r\n" + 
			"	JOIN `service_order_item_receive` sitem ON sitem.`item_id`=jcard.`item_id_item_id`\r\n" + 
			"	JOIN `service_order_item` item ON item.`id`=sitem.`item_id`\r\n" + 
			"	JOIN `service_recource` res ON res.`service_resource_id`=jcard.`repair_eng_service_resource_id`\r\n" +
			" 	JOIN `products` prd ON prd.`product_part_code`=item.`product_product_part_code`\r\n"+
			"	JOIN `product_line_item_product_state` pstate ON pstate.`product_state_id`=item.`state_product_state_id` where jcard.job_card_status <>'Archived'"
			,nativeQuery=true,countQuery = "SELECT COUNT(*) AS countQuery\r\n" + 
					"	\r\n" + 
					"	FROM `service_order_job_card` jcard\r\n" + 
					"	JOIN `service_order_item_receive` sitem ON sitem.`item_id`=jcard.`item_id_item_id`\r\n" + 
					"	JOIN `service_order_item` item ON item.`id`=sitem.`item_id`\r\n" + 
					" 	JOIN `products` prd ON prd.`product_part_code`=item.`product_product_part_code`\r\n"+
					"	JOIN `product_line_item_product_state` pstate ON pstate.`product_state_id`=item.`state_product_state_id`\r\n"+
					"	JOIN `service_recource` res ON res.`service_resource_id`=jcard.`repair_eng_service_resource_id` where jcard.job_card_status <>'Archived'\r\n")
	Page<Map<String,Object>> getJobCard(Pageable page);
	
	@Query(value="SELECT \r\n" + 
			"	jcard.`assigned_date` AS assignedDate,\r\n" + 
			"	jcard.`item_id_item_id` AS itemId,\r\n" + 
			"	jcard.`last_modified_by_email_id` AS lastMdifiedByEmailId,\r\n" + 
			"	jcard.`last_modified_date` AS lastModifiedDate,\r\n" + 
			"	jcard.`manual_id` AS jobCardManualId,\r\n" + 
			"	jcard.`id` AS jobCardId,\r\n" + 
			"	jcard.`owner_email_id` AS jobCardOwnerEmailId,\r\n" + 
			"	jcard.`repair_eng_service_resource_id` AS resourcId,\r\n" + 
			"	jcard.`reparied` AS reparied,\r\n" + 
			" 	jcard.`returned` as returned,\r\n"+
			"	item.`circle` AS itemCircle,\r\n" + 
			"	item.`customer_inventory` AS customerInventory,\r\n" + 
			"	item.`customer_name` AS customerName,\r\n" + 
			"	item.`customer_sap` AS customerSapName,\r\n" + 
			"	item.`id` AS serviceOrderItemId,\r\n" + 
			"	item.`product_product_part_code` AS partCode,\r\n" + 
			"	item.`serial_number` AS partCodeSerialNumber,\r\n" + 
			"	item.`service_order_id` AS serviceOrderId,\r\n" + 
			"	res.`service_resource_id` AS engId,\r\n" + 
			"	res.`name` AS engName,\r\n" + 
			"	prd.`pdoduct_description` as productDescription,\r\n"+
			"	prd.`product_type` as productType,\r\n"+
			"	pstate.`product_state_name` AS state,\r\n"+
			"	item.`remark` AS `remarks`,\r\n"+
			"	item.`lot_no` AS `lotNo`\r\n"+
			"\r\n" + 
			"	FROM `service_order_job_card` jcard\r\n" + 
			"	JOIN `service_order_item_receive` sitem ON sitem.`item_id`=jcard.`item_id_item_id`\r\n" + 
			"	JOIN `service_order_item` item ON item.`id`=sitem.`item_id`\r\n" + 
			"	JOIN `service_recource` res ON res.`service_resource_id`=jcard.`repair_eng_service_resource_id`\r\n" +
			" 	JOIN `products` prd ON prd.`product_part_code`=item.`product_product_part_code`\r\n"+
			"	JOIN `product_line_item_product_state` pstate ON pstate.`product_state_id`=item.`state_product_state_id` where jcard.job_card_status <>'Archived' and jcard.reparied=:repair"
			,nativeQuery=true,countQuery = "SELECT COUNT(*) AS countQuery\r\n" + 
					"	\r\n" + 
					"	FROM `service_order_job_card` jcard\r\n" + 
					"	JOIN `service_order_item_receive` sitem ON sitem.`item_id`=jcard.`item_id_item_id`\r\n" + 
					"	JOIN `service_order_item` item ON item.`id`=sitem.`item_id`\r\n" + 
					" 	JOIN `products` prd ON prd.`product_part_code`=item.`product_product_part_code`\r\n"+
					"	JOIN `product_line_item_product_state` pstate ON pstate.`product_state_id`=item.`state_product_state_id`\r\n"+
					"	JOIN `service_recource` res ON res.`service_resource_id`=jcard.`repair_eng_service_resource_id` where jcard.job_card_status <>'Archived' and jcard.reparied=:repair\r\n")
	Page<Map<String,Object>> getJobCard(Pageable page,@Param("repair")boolean reparied);
	
	//GET JOB CARD BY ID AND USER LIST
	@Query(value="SELECT \r\n" + 
			"	jcard.`assigned_date` AS assignedDate,\r\n" + 
			"	jcard.`item_id_item_id` AS itemId,\r\n" + 
			"	jcard.`last_modified_by_email_id` AS lastMdifiedByEmailId,\r\n" + 
			"	jcard.`last_modified_date` AS lastModifiedDate,\r\n" + 
			"	jcard.`manual_id` AS jobCardManualId,\r\n" + 
			"	jcard.`id` AS jobCardId,\r\n" + 
			"	jcard.`owner_email_id` AS jobCardOwnerEmailId,\r\n" + 
			"	jcard.`repair_eng_service_resource_id` AS resourcId,\r\n" + 
			"	jcard.`reparied` AS reparied,\r\n" + 
			" 	jcard.`returned` as returned,\r\n"+
			"	item.`circle` AS itemCircle,\r\n" + 
			"	item.`customer_inventory` AS customerInventory,\r\n" + 
			"	item.`customer_name` AS customerName,\r\n" + 
			"	item.`customer_sap` AS customerSapName,\r\n" + 
			"	item.`id` AS serviceOrderItemId,\r\n" + 
			"	item.`product_product_part_code` AS partCode,\r\n" + 
			"	item.`serial_number` AS partCodeSerialNumber,\r\n" + 
			"	item.`service_order_id` AS serviceOrderId,\r\n" + 
			"	res.`service_resource_id` AS engId,\r\n" + 
			"	res.`name` AS engName,\r\n" + 
			"	prd.`pdoduct_description` as productDescription,\r\n"+
			"	prd.`product_type` as productType,\r\n"+
			"	pstate.`product_state_name` AS state,\r\n"+
			"	item.`remark` AS `remarks`,\r\n"+
			"	item.`lot_no` AS `lotNo`\r\n"+
			"\r\n" + 
			"	FROM `service_order_job_card` jcard\r\n" + 
			"	JOIN `service_order_item_receive` sitem ON sitem.`item_id`=jcard.`item_id_item_id`\r\n" + 
			"	JOIN `service_order_item` item ON item.`id`=sitem.`item_id`\r\n" + 
			"	JOIN `service_recource` res ON res.`service_resource_id`=jcard.`repair_eng_service_resource_id`\r\n" + 
			" 	JOIN `products` prd ON prd.`product_part_code`=item.`product_product_part_code`\r\n"+
			"	JOIN `product_line_item_product_state` pstate ON pstate.`product_state_id`=item.`state_product_state_id`"+
			"	\r\n" + 
			"	WHERE jcard.job_card_status <>'Archived' and jcard.`owner_email_id` IN (:emailUserList) and jcard.id=:id\r\n",nativeQuery=true)
	Map<String,Object> getJobCardByUserListAndId(@Param("emailUserList")List<String>emailUserList,@Param("id")Long id);
	//GET JOB CARD BY ID FOR VIEW ALL
	
	@Query(value="SELECT \r\n" + 
			"	jcard.`assigned_date` AS assignedDate,\r\n" + 
			"	jcard.`item_id_item_id` AS itemId,\r\n" + 
			"	jcard.`last_modified_by_email_id` AS lastMdifiedByEmailId,\r\n" + 
			"	jcard.`last_modified_date` AS lastModifiedDate,\r\n" + 
			"	jcard.`manual_id` AS jobCardManualId,\r\n" + 
			"	jcard.`id` AS jobCardId,\r\n" + 
			"	jcard.`owner_email_id` AS jobCardOwnerEmailId,\r\n" + 
			"	jcard.`repair_eng_service_resource_id` AS resourcId,\r\n" + 
			"	jcard.`reparied` AS reparied,\r\n" + 
			" 	jcard.`returned` as returned,\r\n"+
			"	item.`circle` AS itemCircle,\r\n" + 
			"	item.`customer_inventory` AS customerInventory,\r\n" + 
			"	item.`customer_name` AS customerName,\r\n" + 
			"	item.`customer_sap` AS customerSapName,\r\n" + 
			"	item.`id` AS serviceOrderItemId,\r\n" + 
			"	item.`product_product_part_code` AS partCode,\r\n" + 
			"	item.`serial_number` AS partCodeSerialNumber,\r\n" + 
			"	item.`service_order_id` AS serviceOrderId,\r\n" + 
			"	res.`service_resource_id` AS engId,\r\n" + 
			"	res.`name` AS engName,\r\n" + 
			"	prd.`pdoduct_description` as productDescription,\r\n"+
			"	prd.`product_type` as productType,\r\n"+
			"	pstate.`product_state_name` AS state,\r\n"+
			"	item.`remark` AS `remarks`,\r\n"+
			"	item.`lot_no` AS `lotNo`\r\n"+
			"\r\n" + 
			"	FROM `service_order_job_card` jcard\r\n" + 
			"	JOIN `service_order_item_receive` sitem ON sitem.`item_id`=jcard.`item_id_item_id`\r\n" + 
			"	JOIN `service_order_item` item ON item.`id`=sitem.`item_id`\r\n" + 
			"	JOIN `service_recource` res ON res.`service_resource_id`=jcard.`repair_eng_service_resource_id`\r\n" + 
			" 	JOIN `products` prd ON prd.`product_part_code`=item.`product_product_part_code`\r\n"+
			"	JOIN `product_line_item_product_state` pstate ON pstate.`product_state_id`=item.`state_product_state_id`"+
			"	\r\n" + 
			"	WHERE jcard.job_card_status <>'Archived' and jcard.id=:id\r\n",nativeQuery=true)
	Map<String,Object> getJobCardId(@Param("id")Long id);
	
	@Query(value=" SELECT \r\n" + 
			"	jcard.`id` AS jcardId,\r\n" + 
			"	jcard.`manual_id` AS jcardManualId,\r\n" + 
			"	jcard.`reparied` AS repaired,\r\n" + 
			"	jcard.`assigned_date` AS assignedDate,\r\n" + 
			" 	jcard.`returned` as returned,\r\n"+
			"	item.`circle` AS circle,\r\n" + 
			"	item.`customer_inventory` AS isCustomerInventory,\r\n" + 
			"	item.`customer_name` AS customerName,\r\n" + 
			"	item.`customer_sap` AS customerSap,\r\n" + 
			"	item.`product_product_part_code` AS partCode,\r\n" + 
			"	item.`serial_number` AS serialNumber,\r\n" + 
			"	prd.`pdoduct_description` AS productDescription,\r\n" + 
			"	item.`remark` AS `remarks`,\r\n"+
			"	item.`lot_no` AS `lotNo`\r\n"+
			"	\r\n" + 
			"  FROM `service_order_job_card` jcard\r\n" + 
			"	JOIN `service_order_item_receive` ir ON ir.`item_id`=jcard.`item_id_item_id`\r\n" + 
			"	JOIN `service_order_item` item ON item.`id`=ir.`item_id`\r\n" + 
			"	JOIN `products` prd ON prd.`product_part_code`=item.`product_product_part_code`\r\n" + 
			"	WHERE jcard.job_card_status <>'Archived' and jcard.returned=0 AND jcard.`owner_email_id` =:emailId",nativeQuery=true)
	List<Map<String,Object>> getPendingJobCardToReturn(@Param("emailId")String emailId);
	
	
	@Query(value="SELECT \r\n" + 
			"ct.`email_id` AS emailId,\r\n" + 
			"ct.`first_name` AS firstName,\r\n" + 
			"ct.`middle_name` AS middleName,\r\n" + 
			"ct.`last_name` AS lastName \r\n" + 
			"FROM `contact_details` ct  \r\n" + 
			"JOIN `legal_entity` lg ON ct.`legal_entity_id`=lg.`legal_entity_id` \r\n" + 
			"JOIN `employee_details` emp ON emp.email_id=ct.`email_id`\r\n" + 
			"JOIN `login_user_roles` lur ON lur.`login_username`=ct.`email_id`\r\n" + 
			"WHERE lg.`legal_entity_type_type_id`=2 \r\n" + 
			"AND lg.`legal_entity_id` IN (SELECT lgc.`legal_entity_id` \r\n" + 
			"FROM legal_entity lgc \r\n" + 
			"JOIN `contact_details` ctc ON ctc.`legal_entity_id`=lgc.legal_entity_id  \r\n" + 
			"WHERE ctc.`email_id`=:useremailid)\r\n" + 
			"AND lur.`authority` IN :userRole",nativeQuery=true)
	public List<Map<String,Object>> getRepariEngineerUser(@Param("useremailid")String useremailid,@Param("userRole")List<String> userRole);
}
