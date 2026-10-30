package com.pratikshat.exicom.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.ServiceOrderItemReturn;

@Repository
public interface ServiceOrderItemReturnRepository extends JpaRepository<ServiceOrderItemReturn, Long>,ServiceOrderItemReturnRepositoryCustome {
	@Query(value="SELECT \r\n" + 
			"	jcard.`assigned_date` AS jobCardAssignedDate,\r\n" + 
			"	jcard.`id` AS jobCardId,\r\n" + 
			"	jcard.`item_id_item_id` AS jobCardReceiveId,\r\n" + 
			"	jcard.`manual_id` AS jobCardManualId,\r\n" + 
			"	jcard.`product_state_product_state_id` AS jobcardProductFinalStatus,\r\n" + 
			"	jcard.`repair_eng_service_resource_id` AS endResourceId,\r\n" + 
			"	jcard.`reparied` AS jobcardRepaired,\r\n" + 
			"	jcard.`returned` AS jobCardReturned,\r\n" + 
			"	rec.returned AS serviceOrderItemReturned,\r\n" + 
			"	item.`circle` AS productCircle,\r\n" + 
			"	item.`customer_inventory` AS isCustomerInventory,\r\n" + 
			"	item.`customer_name` AS customerName,\r\n" + 
			"	item.`customer_sap` AS customerSap,\r\n" + 
			"	item.`id` AS serviceOrderItemId,\r\n" + 
			"	item.`product_product_part_code` AS productPartCode,\r\n" + 
			"	item.`serial_number` AS productSerialNumber,\r\n" + 
			"	item.`service_order_id` AS serviceOrderId,\r\n" + 
			"	item.`state_product_state_id` AS previousProductStateId,\r\n" + 
			"	sord.`manual_id` AS serviceOrderManualId,\r\n" + 
			"	fstate.`product_state_name` AS finalProductStateName,\r\n" + 
			"	pstate.`product_state_name` AS previousProductStateName,\r\n" + 
			"	item.`remark` AS `remarks`,\r\n"+
			"	item.`lot_no` AS `lotNo`\r\n"+
			"	\r\n" + 
			" FROM `service_order_job_card` jcard\r\n" + 
			"	JOIN `service_order_item_receive` rec ON jcard.`item_id_item_id`=rec.`item_id`\r\n" + 
			"	JOIN `service_order_item` item ON rec.`item_id`=item.`id`\r\n" + 
			"	JOIN `service_order` sord ON item.`service_order_id`=sord.`id`\r\n" + 
			"	JOIN `location` loc ON sord.`repari_center_location_id`=loc.`location_id`\r\n" + 
			"	JOIN `product_line_item_product_state` fstate ON fstate.`product_state_id`=jcard.`product_state_product_state_id`\r\n" + 
			"	JOIN `product_line_item_product_state` pstate ON item.`state_product_state_id`=pstate.`product_state_id`\r\n" + 
			"  	JOIN `service_recource` sr on sr.location_location_id=loc.location_id\r\n"+
			"	\r\n" + 
			"	WHERE jcard.job_card_status <>'Archived' and jcard.`returned`=1 AND rec.returned=0 AND sr.`user_email_id`=:emailId",nativeQuery=true)
	List<Map<String,Object>> getPendingServiceOrderItemToReturn(@Param("emailId")String user);
	
	@Query(value="SELECT \r\n" + 
			"	itemreturn.`id` AS reurnItemId,\r\n" + 
			"	itemreturn.`service_order_return_id` AS returnIdServiceOrder,\r\n" + 
			"	itemreturn.`state_product_state_id` AS productFinalStateId,\r\n" + 
			"	item.`circle` AS circleName,\r\n" + 
			"	item.`customer_inventory` AS isCustomerInventory,\r\n" + 
			"	item.`customer_name` AS customerName,\r\n" + 
			"	item.`customer_sap` AS customerSAP,\r\n" + 
			"	item.`product_product_part_code` AS productPartCode,\r\n" + 
			"	item.`serial_number` AS itemSerialNumber,\r\n" + 
			"	item.`service_order_id` AS serviceOrderId,\r\n" + 
			"	item.`state_product_state_id` AS previousStatteId,\r\n" + 
			"	stfinal.`product_state_name` AS finalProductStateName,\r\n" + 
			"	prestate.`product_state_name` AS previousProductFinalStateName,\r\n" + 
			"	sord.manual_id as serviceOrderManualId,\r\n"+
			"	item.`remark` AS `remarks`,\r\n"+
			"	item.`lot_no` AS `lotNo`\r\n"+
			"	\r\n" + 
			"	FROM `service_order_item_return` itemreturn\r\n" + 
			"	JOIN `service_order_item_receive` itemreceive ON itemreturn.`id`=itemreceive.`item_id`\r\n" + 
			"	JOIN `service_order_item` item ON item.`id`=itemreceive.`item_id`\r\n" + 
			"	JOIN `service_order` sord ON item.`service_order_id`=sord.`id`\r\n" + 
			"	JOIN `product_line_item_product_state` stfinal ON stfinal.`product_state_id`=itemreturn.`state_product_state_id`\r\n" + 
			"	JOIN `product_line_item_product_state` prestate ON prestate.`product_state_id`=item.`state_product_state_id`\r\n" + 
			"	JOIN `location` loc ON itemreturn.`return_warehouse_location_id`=loc.`location_id`\r\n" + 
			"  	JOIN `service_recource` sr on sr.location_location_id=loc.location_id\r\n"+
			"	WHERE sord.so_status <>'Archived' and itemreturn.`received`=0 AND sr.`user_email_id`=:emailid",nativeQuery=true)
	List<Map<String,Object>> getPendingToReceiveReturn(@Param("emailid")String user);
	
	
	@Query(value="SELECT \r\n" + 
			"sor.`id` AS `id`,\r\n" + 
			"sor.`manual_id` AS `manualId`,\r\n" + 
			"sor.`ship_to_name` AS `shipToName`,\r\n" + 
			"sor.`shipping_mode` AS `shippingMode`,\r\n" + 
			"sor.`tracking_number` AS `trackingNumber`,\r\n" + 
			"sor.`total_item` AS `returnedQty`,\r\n" + 
			"IFNULL(qty.`receivedQty`,0) AS `receivedQty`,\r\n" + 
			"(CASE\r\n" + 
			"	WHEN sor.`total_item`!=0 AND IFNULL(qty.`receivedQty`,0)!=0 AND sor.`total_item`=IFNULL(qty.`receivedQty`,0) THEN 'Received'\r\n" + 
			"	WHEN sor.`total_item`!=0 AND IFNULL(qty.`receivedQty`,0)!=0 AND sor.`total_item`>IFNULL(qty.`receivedQty`,0) THEN 'Partially Received'\r\n" + 
			"	WHEN IFNULL(qty.`receivedQty`,0)=0 THEN 'Pending to Receive'\r\n" + 
			"END) AS `status`,\r\n" + 
			"sor.`created_date` AS `createdDate`,\r\n" + 
			"sor.`modified_date` AS `modifiedDate`,\r\n" + 
			"sor.`created_by_email_id` AS `createdByEmailId`,\r\n" + 
			"CONCAT(CONCAT(cby.`first_name`,' '),IFNULL(CONCAT(cby.`middle_name`,' '),''),cby.`last_name`) AS `createdByName`,\r\n" + 
			"sor.`modified_by_email_id` AS `modifiedByEmailId`,\r\n" + 
			"CONCAT(CONCAT(mby.`first_name`,' '),IFNULL(CONCAT(mby.`middle_name`,' '),''),mby.`last_name`) AS `modifiedByName`,\r\n" + 
			"sor.`ownner_email_id` AS `ownerEmailId`,\r\n" + 
			"CONCAT(CONCAT(onr.`first_name`,' '),IFNULL(CONCAT(onr.`middle_name`,' '),''),onr.`last_name`) AS `ownerName`,\r\n" + 
			"sor.`destination_location_location_id` AS `sourceLocationId`,\r\n" + 
			"dl.`location_name` AS `destinationLocationName`,\r\n" + 
			"sor.`source_loccation_location_id` AS `destinationLocationId`,\r\n" + 
			"sl.`location_name` AS `sourceLocationName`\r\n" + 
			"FROM `service_order_return` sor\r\n" + 
			"JOIN `employee_details` cby ON cby.`email_id`=sor.`created_by_email_id`\r\n" + 
			"JOIN `employee_details` mby ON mby.`email_id`=sor.`modified_by_email_id`\r\n" + 
			"JOIN `employee_details` onr ON onr.`email_id`=sor.`ownner_email_id`\r\n" + 
			"JOIN `location` sl ON sl.`location_id`=sor.`source_loccation_location_id`\r\n" + 
			"JOIN `location` dl ON dl.`location_id`=sor.`destination_location_location_id`\r\n" + 
			"LEFT JOIN \r\n" + 
			"(SELECT\r\n" + 
			"	COUNT(1) AS `receivedQty`,\r\n" + 
			"	item.`service_order_return_id`\r\n" + 
			"	FROM `service_order_item_return` item\r\n" + 
			"	WHERE item.`received`=TRUE\r\n" + 
			"	GROUP BY  item.`service_order_return_id`\r\n" + 
			") AS `qty`\r\n" + 
			"ON qty.`service_order_return_id`=sor.`id`\r\n" +
			"WHERE sor.`total_item`!=0 AND IFNULL(qty.`receivedQty`,0)!=0 AND sor.`total_item`=IFNULL(qty.`receivedQty`,0) \r\n", 
			nativeQuery=true, 
			countQuery="SELECT \r\n" + 
					"COUNT(*) AS countQuery\r\n" +
					"FROM `service_order_return` sor\r\n" + 
					"JOIN `employee_details` cby ON cby.`email_id`=sor.`created_by_email_id`\r\n" + 
					"JOIN `employee_details` mby ON mby.`email_id`=sor.`modified_by_email_id`\r\n" + 
					"JOIN `employee_details` onr ON onr.`email_id`=sor.`ownner_email_id`\r\n" + 
					"JOIN `location` sl ON sl.`location_id`=sor.`source_loccation_location_id`\r\n" + 
					"JOIN `location` dl ON dl.`location_id`=sor.`destination_location_location_id`\r\n" + 
					"LEFT JOIN \r\n" + 
					"(SELECT\r\n" + 
					"	COUNT(1) AS `receivedQty`,\r\n" + 
					"	item.`service_order_return_id`\r\n" + 
					"	FROM `service_order_item_return` item\r\n" + 
					"	WHERE item.`received`=TRUE\r\n" + 
					"	GROUP BY  item.`service_order_return_id`\r\n" + 
					") AS `qty`\r\n" + 
					"ON qty.`service_order_return_id`=sor.`id`\r\n" +
					"WHERE sor.`total_item`!=0 AND IFNULL(qty.`receivedQty`,0)!=0 AND sor.`total_item`=IFNULL(qty.`receivedQty`,0) \r\n")
	Page<Map<String, Object>> getReceivedSOR(Pageable page);
	
	
	@Query(value="SELECT \r\n" + 
			"sor.`id` AS `id`,\r\n" + 
			"sor.`manual_id` AS `manualId`,\r\n" + 
			"sor.`ship_to_name` AS `shipToName`,\r\n" + 
			"sor.`shipping_mode` AS `shippingMode`,\r\n" + 
			"sor.`tracking_number` AS `trackingNumber`,\r\n" + 
			"sor.`total_item` AS `returnedQty`,\r\n" + 
			"IFNULL(qty.`receivedQty`,0) AS `receivedQty`,\r\n" + 
			"(CASE\r\n" + 
			"	WHEN sor.`total_item`!=0 AND IFNULL(qty.`receivedQty`,0)!=0 AND sor.`total_item`=IFNULL(qty.`receivedQty`,0) THEN 'Received'\r\n" + 
			"	WHEN sor.`total_item`!=0 AND IFNULL(qty.`receivedQty`,0)!=0 AND sor.`total_item`>IFNULL(qty.`receivedQty`,0) THEN 'Partially Received'\r\n" + 
			"	WHEN IFNULL(qty.`receivedQty`,0)=0 THEN 'Pending to Receive'\r\n" + 
			"END) AS `status`,\r\n" + 
			"sor.`created_date` AS `createdDate`,\r\n" + 
			"sor.`modified_date` AS `modifiedDate`,\r\n" + 
			"sor.`created_by_email_id` AS `createdByEmailId`,\r\n" + 
			"CONCAT(CONCAT(cby.`first_name`,' '),IFNULL(CONCAT(cby.`middle_name`,' '),''),cby.`last_name`) AS `createdByName`,\r\n" + 
			"sor.`modified_by_email_id` AS `modifiedByEmailId`,\r\n" + 
			"CONCAT(CONCAT(mby.`first_name`,' '),IFNULL(CONCAT(mby.`middle_name`,' '),''),mby.`last_name`) AS `modifiedByName`,\r\n" + 
			"sor.`ownner_email_id` AS `ownerEmailId`,\r\n" + 
			"CONCAT(CONCAT(onr.`first_name`,' '),IFNULL(CONCAT(onr.`middle_name`,' '),''),onr.`last_name`) AS `ownerName`,\r\n" + 
			"sor.`destination_location_location_id` AS `sourceLocationId`,\r\n" + 
			"dl.`location_name` AS `destinationLocationName`,\r\n" + 
			"sor.`source_loccation_location_id` AS `destinationLocationId`,\r\n" + 
			"sl.`location_name` AS `sourceLocationName`\r\n" + 
			"FROM `service_order_return` sor\r\n" + 
			"JOIN `employee_details` cby ON cby.`email_id`=sor.`created_by_email_id`\r\n" + 
			"JOIN `employee_details` mby ON mby.`email_id`=sor.`modified_by_email_id`\r\n" + 
			"JOIN `employee_details` onr ON onr.`email_id`=sor.`ownner_email_id`\r\n" + 
			"JOIN `location` sl ON sl.`location_id`=sor.`source_loccation_location_id`\r\n" + 
			"JOIN `location` dl ON dl.`location_id`=sor.`destination_location_location_id`\r\n" + 
			"LEFT JOIN \r\n" + 
			"(SELECT\r\n" + 
			"	COUNT(1) AS `receivedQty`,\r\n" + 
			"	item.`service_order_return_id`\r\n" + 
			"	FROM `service_order_item_return` item\r\n" + 
			"	WHERE item.`received`=TRUE\r\n" + 
			"	GROUP BY  item.`service_order_return_id`\r\n" + 
			") AS `qty`\r\n" + 
			"ON qty.`service_order_return_id`=sor.`id`\r\n" +
			"WHERE sor.`total_item`!=0 AND IFNULL(qty.`receivedQty`,0)!=0 AND sor.`total_item`>IFNULL(qty.`receivedQty`,0) \r\n", 
			nativeQuery=true, 
			countQuery="SELECT \r\n" + 
					"COUNT(*) AS countQuery\r\n" +
					"FROM `service_order_return` sor\r\n" + 
					"JOIN `employee_details` cby ON cby.`email_id`=sor.`created_by_email_id`\r\n" + 
					"JOIN `employee_details` mby ON mby.`email_id`=sor.`modified_by_email_id`\r\n" + 
					"JOIN `employee_details` onr ON onr.`email_id`=sor.`ownner_email_id`\r\n" + 
					"JOIN `location` sl ON sl.`location_id`=sor.`source_loccation_location_id`\r\n" + 
					"JOIN `location` dl ON dl.`location_id`=sor.`destination_location_location_id`\r\n" + 
					"LEFT JOIN \r\n" + 
					"(SELECT\r\n" + 
					"	COUNT(1) AS `receivedQty`,\r\n" + 
					"	item.`service_order_return_id`\r\n" + 
					"	FROM `service_order_item_return` item\r\n" + 
					"	WHERE item.`received`=TRUE\r\n" + 
					"	GROUP BY  item.`service_order_return_id`\r\n" + 
					") AS `qty`\r\n" + 
					"ON qty.`service_order_return_id`=sor.`id`\r\n" + 
					"WHERE sor.`total_item`!=0 AND IFNULL(qty.`receivedQty`,0)!=0 AND sor.`total_item`>IFNULL(qty.`receivedQty`,0) \r\n")
	Page<Map<String, Object>> getPartiallyReceivedSOR(Pageable page);
	
	
	@Query(value="SELECT \r\n" + 
			"sor.`id` AS `id`,\r\n" + 
			"sor.`manual_id` AS `manualId`,\r\n" + 
			"sor.`ship_to_name` AS `shipToName`,\r\n" + 
			"sor.`shipping_mode` AS `shippingMode`,\r\n" + 
			"sor.`tracking_number` AS `trackingNumber`,\r\n" + 
			"sor.`total_item` AS `returnedQty`,\r\n" + 
			"IFNULL(qty.`receivedQty`,0) AS `receivedQty`,\r\n" + 
			"(CASE\r\n" + 
			"	WHEN sor.`total_item`!=0 AND IFNULL(qty.`receivedQty`,0)!=0 AND sor.`total_item`=IFNULL(qty.`receivedQty`,0) THEN 'Received'\r\n" + 
			"	WHEN sor.`total_item`!=0 AND IFNULL(qty.`receivedQty`,0)!=0 AND sor.`total_item`>IFNULL(qty.`receivedQty`,0) THEN 'Partially Received'\r\n" + 
			"	WHEN IFNULL(qty.`receivedQty`,0)=0 THEN 'Pending to Receive'\r\n" + 
			"END) AS `status`,\r\n" + 
			"sor.`created_date` AS `createdDate`,\r\n" + 
			"sor.`modified_date` AS `modifiedDate`,\r\n" + 
			"sor.`created_by_email_id` AS `createdByEmailId`,\r\n" + 
			"CONCAT(CONCAT(cby.`first_name`,' '),IFNULL(CONCAT(cby.`middle_name`,' '),''),cby.`last_name`) AS `createdByName`,\r\n" + 
			"sor.`modified_by_email_id` AS `modifiedByEmailId`,\r\n" + 
			"CONCAT(CONCAT(mby.`first_name`,' '),IFNULL(CONCAT(mby.`middle_name`,' '),''),mby.`last_name`) AS `modifiedByName`,\r\n" + 
			"sor.`ownner_email_id` AS `ownerEmailId`,\r\n" + 
			"CONCAT(CONCAT(onr.`first_name`,' '),IFNULL(CONCAT(onr.`middle_name`,' '),''),onr.`last_name`) AS `ownerName`,\r\n" + 
			"sor.`destination_location_location_id` AS `sourceLocationId`,\r\n" + 
			"dl.`location_name` AS `destinationLocationName`,\r\n" + 
			"sor.`source_loccation_location_id` AS `destinationLocationId`,\r\n" + 
			"sl.`location_name` AS `sourceLocationName`\r\n" + 
			"FROM `service_order_return` sor\r\n" + 
			"JOIN `employee_details` cby ON cby.`email_id`=sor.`created_by_email_id`\r\n" + 
			"JOIN `employee_details` mby ON mby.`email_id`=sor.`modified_by_email_id`\r\n" + 
			"JOIN `employee_details` onr ON onr.`email_id`=sor.`ownner_email_id`\r\n" + 
			"JOIN `location` sl ON sl.`location_id`=sor.`source_loccation_location_id`\r\n" + 
			"JOIN `location` dl ON dl.`location_id`=sor.`destination_location_location_id`\r\n" + 
			"LEFT JOIN \r\n" + 
			"(SELECT\r\n" + 
			"	COUNT(1) AS `receivedQty`,\r\n" + 
			"	item.`service_order_return_id`\r\n" + 
			"	FROM `service_order_item_return` item\r\n" + 
			"	WHERE item.`received`=TRUE\r\n" + 
			"	GROUP BY  item.`service_order_return_id`\r\n" + 
			") AS `qty`\r\n" + 
			"ON qty.`service_order_return_id`=sor.`id`\r\n" + 
			"WHERE IFNULL(qty.`receivedQty`,0)=0 \r\n", 
			nativeQuery=true, 
			countQuery="SELECT \r\n" + 
					"COUNT(*) AS countQuery\r\n" +
					"FROM `service_order_return` sor\r\n" + 
					"JOIN `employee_details` cby ON cby.`email_id`=sor.`created_by_email_id`\r\n" + 
					"JOIN `employee_details` mby ON mby.`email_id`=sor.`modified_by_email_id`\r\n" + 
					"JOIN `employee_details` onr ON onr.`email_id`=sor.`ownner_email_id`\r\n" + 
					"JOIN `location` sl ON sl.`location_id`=sor.`source_loccation_location_id`\r\n" + 
					"JOIN `location` dl ON dl.`location_id`=sor.`destination_location_location_id`\r\n" + 
					"LEFT JOIN \r\n" + 
					"(SELECT\r\n" + 
					"	COUNT(1) AS `receivedQty`,\r\n" + 
					"	item.`service_order_return_id`\r\n" + 
					"	FROM `service_order_item_return` item\r\n" + 
					"	WHERE item.`received`=TRUE\r\n" + 
					"	GROUP BY  item.`service_order_return_id`\r\n" + 
					") AS `qty`\r\n" + 
					"ON qty.`service_order_return_id`=sor.`id`\r\n" +
					"WHERE IFNULL(qty.`receivedQty`,0)=0 \r\n")
	Page<Map<String, Object>> getPendingToReceiveSOR(Pageable page);
	
	@Query(value="SELECT \r\n" + 
			"sor.`id` AS `id`,\r\n" + 
			"sor.`manual_id` AS `manualId`,\r\n" + 
			"sor.`ship_to_name` AS `shipToName`,\r\n" + 
			"sor.`shipping_mode` AS `shippingMode`,\r\n" + 
			"sor.`tracking_number` AS `trackingNumber`,\r\n" + 
			"sor.`total_item` AS `returnedQty`,\r\n" + 
			"IFNULL(qty.`receivedQty`,0) AS `receivedQty`,\r\n" + 
			"(CASE\r\n" + 
			"	WHEN sor.`total_item`!=0 AND IFNULL(qty.`receivedQty`,0)!=0 AND sor.`total_item`=IFNULL(qty.`receivedQty`,0) THEN 'Received'\r\n" + 
			"	WHEN sor.`total_item`!=0 AND IFNULL(qty.`receivedQty`,0)!=0 AND sor.`total_item`>IFNULL(qty.`receivedQty`,0) THEN 'Partially Received'\r\n" + 
			"	WHEN IFNULL(qty.`receivedQty`,0)=0 THEN 'Pending to Receive'\r\n" + 
			"END) AS `status`,\r\n" + 
			"sor.`created_date` AS `createdDate`,\r\n" + 
			"sor.`modified_date` AS `modifiedDate`,\r\n" + 
			"sor.`created_by_email_id` AS `createdByEmailId`,\r\n" + 
			"CONCAT(CONCAT(cby.`first_name`,' '),IFNULL(CONCAT(cby.`middle_name`,' '),''),cby.`last_name`) AS `createdByName`,\r\n" + 
			"sor.`modified_by_email_id` AS `modifiedByEmailId`,\r\n" + 
			"CONCAT(CONCAT(mby.`first_name`,' '),IFNULL(CONCAT(mby.`middle_name`,' '),''),mby.`last_name`) AS `modifiedByName`,\r\n" + 
			"sor.`ownner_email_id` AS `ownerEmailId`,\r\n" + 
			"CONCAT(CONCAT(onr.`first_name`,' '),IFNULL(CONCAT(onr.`middle_name`,' '),''),onr.`last_name`) AS `ownerName`,\r\n" + 
			"sor.`destination_location_location_id` AS `sourceLocationId`,\r\n" + 
			"dl.`location_name` AS `destinationLocationName`,\r\n" + 
			"sor.`source_loccation_location_id` AS `destinationLocationId`,\r\n" + 
			"sl.`location_name` AS `sourceLocationName`\r\n" + 
			"FROM `service_order_return` sor\r\n" + 
			"JOIN `employee_details` cby ON cby.`email_id`=sor.`created_by_email_id`\r\n" + 
			"JOIN `employee_details` mby ON mby.`email_id`=sor.`modified_by_email_id`\r\n" + 
			"JOIN `employee_details` onr ON onr.`email_id`=sor.`ownner_email_id`\r\n" + 
			"JOIN `location` sl ON sl.`location_id`=sor.`source_loccation_location_id`\r\n" + 
			"JOIN `location` dl ON dl.`location_id`=sor.`destination_location_location_id`\r\n" + 
			"LEFT JOIN \r\n" + 
			"(SELECT\r\n" + 
			"	COUNT(1) AS `receivedQty`,\r\n" + 
			"	item.`service_order_return_id`\r\n" + 
			"	FROM `service_order_item_return` item\r\n" + 
			"	WHERE item.`received`=TRUE\r\n" + 
			"	GROUP BY  item.`service_order_return_id`\r\n" + 
			") AS `qty`\r\n" + 
			"ON qty.`service_order_return_id`=sor.`id`\r\n",
			nativeQuery=true, 
			countQuery="SELECT \r\n" + 
					"COUNT(*) AS countQuery\r\n" +
					"FROM `service_order_return` sor\r\n" + 
					"JOIN `employee_details` cby ON cby.`email_id`=sor.`created_by_email_id`\r\n" + 
					"JOIN `employee_details` mby ON mby.`email_id`=sor.`modified_by_email_id`\r\n" + 
					"JOIN `employee_details` onr ON onr.`email_id`=sor.`ownner_email_id`\r\n" + 
					"JOIN `location` sl ON sl.`location_id`=sor.`source_loccation_location_id`\r\n" + 
					"JOIN `location` dl ON dl.`location_id`=sor.`destination_location_location_id`\r\n" + 
					"LEFT JOIN \r\n" + 
					"(SELECT\r\n" + 
					"	COUNT(1) AS `receivedQty`,\r\n" + 
					"	item.`service_order_return_id`\r\n" + 
					"	FROM `service_order_item_return` item\r\n" + 
					"	WHERE item.`received`=TRUE\r\n" + 
					"	GROUP BY  item.`service_order_return_id`\r\n" + 
					") AS `qty`\r\n" + 
					"ON qty.`service_order_return_id`=sor.`id`\r\n")
	Page<Map<String, Object>> getSOR(Pageable page);
	
	@Query(value="SELECT \r\n" + 
			"sor.`id` AS `id`,\r\n" + 
			"sor.`manual_id` AS `manualId`,\r\n" + 
			"sor.`ship_to_name` AS `shipToName`,\r\n" + 
			"sor.`shipping_mode` AS `shippingMode`,\r\n" + 
			"sor.`tracking_number` AS `trackingNumber`,\r\n" + 
			"sor.`total_item` AS `returnedQty`,\r\n" + 
			"IFNULL(qty.`receivedQty`,0) AS `receivedQty`,\r\n" + 
			"(CASE\r\n" + 
			"	WHEN sor.`total_item`!=0 AND IFNULL(qty.`receivedQty`,0)!=0 AND sor.`total_item`=IFNULL(qty.`receivedQty`,0) THEN 'Received'\r\n" + 
			"	WHEN sor.`total_item`!=0 AND IFNULL(qty.`receivedQty`,0)!=0 AND sor.`total_item`>IFNULL(qty.`receivedQty`,0) THEN 'Partially Received'\r\n" + 
			"	WHEN IFNULL(qty.`receivedQty`,0)=0 THEN 'Pending to Receive'\r\n" + 
			"END) AS `status`,\r\n" + 
			"sor.`created_date` AS `createdDate`,\r\n" + 
			"sor.`modified_date` AS `modifiedDate`,\r\n" + 
			"sor.`created_by_email_id` AS `createdByEmailId`,\r\n" + 
			"CONCAT(CONCAT(cby.`first_name`,' '),IFNULL(CONCAT(cby.`middle_name`,' '),''),cby.`last_name`) AS `createdByName`,\r\n" + 
			"sor.`modified_by_email_id` AS `modifiedByEmailId`,\r\n" + 
			"CONCAT(CONCAT(mby.`first_name`,' '),IFNULL(CONCAT(mby.`middle_name`,' '),''),mby.`last_name`) AS `modifiedByName`,\r\n" + 
			"sor.`ownner_email_id` AS `ownerEmailId`,\r\n" + 
			"CONCAT(CONCAT(onr.`first_name`,' '),IFNULL(CONCAT(onr.`middle_name`,' '),''),onr.`last_name`) AS `ownerName`,\r\n" + 
			"sor.`destination_location_location_id` AS `sourceLocationId`,\r\n" + 
			"dl.`location_name` AS `destinationLocationName`,\r\n" + 
			"sor.`source_loccation_location_id` AS `destinationLocationId`,\r\n" + 
			"sl.`location_name` AS `sourceLocationName`\r\n" + 
			"FROM `service_order_return` sor\r\n" + 
			"JOIN `employee_details` cby ON cby.`email_id`=sor.`created_by_email_id`\r\n" + 
			"JOIN `employee_details` mby ON mby.`email_id`=sor.`modified_by_email_id`\r\n" + 
			"JOIN `employee_details` onr ON onr.`email_id`=sor.`ownner_email_id`\r\n" + 
			"JOIN `location` sl ON sl.`location_id`=sor.`source_loccation_location_id`\r\n" + 
			"JOIN `location` dl ON dl.`location_id`=sor.`destination_location_location_id`\r\n" + 
			"LEFT JOIN \r\n" + 
			"(SELECT\r\n" + 
			"	COUNT(1) AS `receivedQty`,\r\n" + 
			"	item.`service_order_return_id`\r\n" + 
			"	FROM `service_order_item_return` item\r\n" + 
			"	WHERE item.`received`=TRUE\r\n" + 
			"	GROUP BY  item.`service_order_return_id`\r\n" + 
			") AS `qty`\r\n" + 
			"ON qty.`service_order_return_id`=sor.`id`\r\n" +
			"WHERE sor.`total_item`!=0 AND IFNULL(qty.`receivedQty`,0)!=0 AND sor.`total_item`=IFNULL(qty.`receivedQty`,0) \r\n"+
			"AND (sl.`location_id` IN (:ids) OR dl.`location_id` IN (:ids)) \r\n", 
			nativeQuery=true, 
			countQuery="SELECT \r\n" + 
					"COUNT(*) AS countQuery\r\n" +
					"FROM `service_order_return` sor\r\n" + 
					"JOIN `employee_details` cby ON cby.`email_id`=sor.`created_by_email_id`\r\n" + 
					"JOIN `employee_details` mby ON mby.`email_id`=sor.`modified_by_email_id`\r\n" + 
					"JOIN `employee_details` onr ON onr.`email_id`=sor.`ownner_email_id`\r\n" + 
					"JOIN `location` sl ON sl.`location_id`=sor.`source_loccation_location_id`\r\n" + 
					"JOIN `location` dl ON dl.`location_id`=sor.`destination_location_location_id`\r\n" + 
					"LEFT JOIN \r\n" + 
					"(SELECT\r\n" + 
					"	COUNT(1) AS `receivedQty`,\r\n" + 
					"	item.`service_order_return_id`\r\n" + 
					"	FROM `service_order_item_return` item\r\n" + 
					"	WHERE item.`received`=TRUE\r\n" + 
					"	GROUP BY  item.`service_order_return_id`\r\n" + 
					") AS `qty`\r\n" + 
					"ON qty.`service_order_return_id`=sor.`id`\r\n" +
					"WHERE sor.`total_item`!=0 AND IFNULL(qty.`receivedQty`,0)!=0 AND sor.`total_item`=IFNULL(qty.`receivedQty`,0) \r\n"+
					"AND (sl.`location_id` IN (:ids) OR dl.`location_id` IN (:ids)) \r\n")
	Page<Map<String, Object>> getReceivedSOR(@Param("ids")java.util.List<Long> ids, Pageable page);
	
	
	@Query(value="SELECT \r\n" + 
			"sor.`id` AS `id`,\r\n" + 
			"sor.`manual_id` AS `manualId`,\r\n" + 
			"sor.`ship_to_name` AS `shipToName`,\r\n" + 
			"sor.`shipping_mode` AS `shippingMode`,\r\n" + 
			"sor.`tracking_number` AS `trackingNumber`,\r\n" + 
			"sor.`total_item` AS `returnedQty`,\r\n" + 
			"IFNULL(qty.`receivedQty`,0) AS `receivedQty`,\r\n" + 
			"(CASE\r\n" + 
			"	WHEN sor.`total_item`!=0 AND IFNULL(qty.`receivedQty`,0)!=0 AND sor.`total_item`=IFNULL(qty.`receivedQty`,0) THEN 'Received'\r\n" + 
			"	WHEN sor.`total_item`!=0 AND IFNULL(qty.`receivedQty`,0)!=0 AND sor.`total_item`>IFNULL(qty.`receivedQty`,0) THEN 'Partially Received'\r\n" + 
			"	WHEN IFNULL(qty.`receivedQty`,0)=0 THEN 'Pending to Receive'\r\n" + 
			"END) AS `status`,\r\n" + 
			"sor.`created_date` AS `createdDate`,\r\n" + 
			"sor.`modified_date` AS `modifiedDate`,\r\n" + 
			"sor.`created_by_email_id` AS `createdByEmailId`,\r\n" + 
			"CONCAT(CONCAT(cby.`first_name`,' '),IFNULL(CONCAT(cby.`middle_name`,' '),''),cby.`last_name`) AS `createdByName`,\r\n" + 
			"sor.`modified_by_email_id` AS `modifiedByEmailId`,\r\n" + 
			"CONCAT(CONCAT(mby.`first_name`,' '),IFNULL(CONCAT(mby.`middle_name`,' '),''),mby.`last_name`) AS `modifiedByName`,\r\n" + 
			"sor.`ownner_email_id` AS `ownerEmailId`,\r\n" + 
			"CONCAT(CONCAT(onr.`first_name`,' '),IFNULL(CONCAT(onr.`middle_name`,' '),''),onr.`last_name`) AS `ownerName`,\r\n" + 
			"sor.`destination_location_location_id` AS `sourceLocationId`,\r\n" + 
			"dl.`location_name` AS `destinationLocationName`,\r\n" + 
			"sor.`source_loccation_location_id` AS `destinationLocationId`,\r\n" + 
			"sl.`location_name` AS `sourceLocationName`\r\n" + 
			"FROM `service_order_return` sor\r\n" + 
			"JOIN `employee_details` cby ON cby.`email_id`=sor.`created_by_email_id`\r\n" + 
			"JOIN `employee_details` mby ON mby.`email_id`=sor.`modified_by_email_id`\r\n" + 
			"JOIN `employee_details` onr ON onr.`email_id`=sor.`ownner_email_id`\r\n" + 
			"JOIN `location` sl ON sl.`location_id`=sor.`source_loccation_location_id`\r\n" + 
			"JOIN `location` dl ON dl.`location_id`=sor.`destination_location_location_id`\r\n" + 
			"LEFT JOIN \r\n" + 
			"(SELECT\r\n" + 
			"	COUNT(1) AS `receivedQty`,\r\n" + 
			"	item.`service_order_return_id`\r\n" + 
			"	FROM `service_order_item_return` item\r\n" + 
			"	WHERE item.`received`=TRUE\r\n" + 
			"	GROUP BY  item.`service_order_return_id`\r\n" + 
			") AS `qty`\r\n" + 
			"ON qty.`service_order_return_id`=sor.`id`\r\n" +
			"WHERE sor.`total_item`!=0 AND IFNULL(qty.`receivedQty`,0)!=0 AND sor.`total_item`>IFNULL(qty.`receivedQty`,0) \r\n"+
			"AND (sl.`location_id` IN (:ids) OR dl.`location_id` IN (:ids)) \r\n", 
			nativeQuery=true, 
			countQuery="SELECT \r\n" + 
					"COUNT(*) AS countQuery\r\n" +
					"FROM `service_order_return` sor\r\n" + 
					"JOIN `employee_details` cby ON cby.`email_id`=sor.`created_by_email_id`\r\n" + 
					"JOIN `employee_details` mby ON mby.`email_id`=sor.`modified_by_email_id`\r\n" + 
					"JOIN `employee_details` onr ON onr.`email_id`=sor.`ownner_email_id`\r\n" + 
					"JOIN `location` sl ON sl.`location_id`=sor.`source_loccation_location_id`\r\n" + 
					"JOIN `location` dl ON dl.`location_id`=sor.`destination_location_location_id`\r\n" + 
					"LEFT JOIN \r\n" + 
					"(SELECT\r\n" + 
					"	COUNT(1) AS `receivedQty`,\r\n" + 
					"	item.`service_order_return_id`\r\n" + 
					"	FROM `service_order_item_return` item\r\n" + 
					"	WHERE item.`received`=TRUE\r\n" + 
					"	GROUP BY  item.`service_order_return_id`\r\n" + 
					") AS `qty`\r\n" + 
					"ON qty.`service_order_return_id`=sor.`id`\r\n" +
					"WHERE sor.`total_item`!=0 AND IFNULL(qty.`receivedQty`,0)!=0 AND sor.`total_item`>IFNULL(qty.`receivedQty`,0) \r\n"+
					"AND (sl.`location_id` IN (:ids) OR dl.`location_id` IN (:ids)) \r\n")
	Page<Map<String, Object>> getPartiallyReceivedSOR(@Param("ids")java.util.List<Long> ids, Pageable page);
	
	
	@Query(value="SELECT \r\n" + 
			"sor.`id` AS `id`,\r\n" + 
			"sor.`manual_id` AS `manualId`,\r\n" + 
			"sor.`ship_to_name` AS `shipToName`,\r\n" + 
			"sor.`shipping_mode` AS `shippingMode`,\r\n" + 
			"sor.`tracking_number` AS `trackingNumber`,\r\n" + 
			"sor.`total_item` AS `returnedQty`,\r\n" + 
			"IFNULL(qty.`receivedQty`,0) AS `receivedQty`,\r\n" + 
			"(CASE\r\n" + 
			"	WHEN sor.`total_item`!=0 AND IFNULL(qty.`receivedQty`,0)!=0 AND sor.`total_item`=IFNULL(qty.`receivedQty`,0) THEN 'Received'\r\n" + 
			"	WHEN sor.`total_item`!=0 AND IFNULL(qty.`receivedQty`,0)!=0 AND sor.`total_item`>IFNULL(qty.`receivedQty`,0) THEN 'Partially Received'\r\n" + 
			"	WHEN IFNULL(qty.`receivedQty`,0)=0 THEN 'Pending to Receive'\r\n" + 
			"END) AS `status`,\r\n" + 
			"sor.`created_date` AS `createdDate`,\r\n" + 
			"sor.`modified_date` AS `modifiedDate`,\r\n" + 
			"sor.`created_by_email_id` AS `createdByEmailId`,\r\n" + 
			"CONCAT(CONCAT(cby.`first_name`,' '),IFNULL(CONCAT(cby.`middle_name`,' '),''),cby.`last_name`) AS `createdByName`,\r\n" + 
			"sor.`modified_by_email_id` AS `modifiedByEmailId`,\r\n" + 
			"CONCAT(CONCAT(mby.`first_name`,' '),IFNULL(CONCAT(mby.`middle_name`,' '),''),mby.`last_name`) AS `modifiedByName`,\r\n" + 
			"sor.`ownner_email_id` AS `ownerEmailId`,\r\n" + 
			"CONCAT(CONCAT(onr.`first_name`,' '),IFNULL(CONCAT(onr.`middle_name`,' '),''),onr.`last_name`) AS `ownerName`,\r\n" + 
			"sor.`destination_location_location_id` AS `sourceLocationId`,\r\n" + 
			"dl.`location_name` AS `destinationLocationName`,\r\n" + 
			"sor.`source_loccation_location_id` AS `destinationLocationId`,\r\n" + 
			"sl.`location_name` AS `sourceLocationName`\r\n" + 
			"FROM `service_order_return` sor\r\n" + 
			"JOIN `employee_details` cby ON cby.`email_id`=sor.`created_by_email_id`\r\n" + 
			"JOIN `employee_details` mby ON mby.`email_id`=sor.`modified_by_email_id`\r\n" + 
			"JOIN `employee_details` onr ON onr.`email_id`=sor.`ownner_email_id`\r\n" + 
			"JOIN `location` sl ON sl.`location_id`=sor.`source_loccation_location_id`\r\n" + 
			"JOIN `location` dl ON dl.`location_id`=sor.`destination_location_location_id`\r\n" + 
			"LEFT JOIN \r\n" + 
			"(SELECT\r\n" + 
			"	COUNT(1) AS `receivedQty`,\r\n" + 
			"	item.`service_order_return_id`\r\n" + 
			"	FROM `service_order_item_return` item\r\n" + 
			"	WHERE item.`received`=TRUE\r\n" + 
			"	GROUP BY  item.`service_order_return_id`\r\n" + 
			") AS `qty`\r\n" + 
			"ON qty.`service_order_return_id`=sor.`id`\r\n" + 
			"WHERE IFNULL(qty.`receivedQty`,0)=0 \r\n"+
			"AND (sl.`location_id` IN (:ids) OR dl.`location_id` IN (:ids)) \r\n", 
			nativeQuery=true, 
			countQuery="SELECT \r\n" + 
					"COUNT(*) AS countQuery\r\n" +
					"FROM `service_order_return` sor\r\n" + 
					"JOIN `employee_details` cby ON cby.`email_id`=sor.`created_by_email_id`\r\n" + 
					"JOIN `employee_details` mby ON mby.`email_id`=sor.`modified_by_email_id`\r\n" + 
					"JOIN `employee_details` onr ON onr.`email_id`=sor.`ownner_email_id`\r\n" + 
					"JOIN `location` sl ON sl.`location_id`=sor.`source_loccation_location_id`\r\n" + 
					"JOIN `location` dl ON dl.`location_id`=sor.`destination_location_location_id`\r\n" + 
					"LEFT JOIN \r\n" + 
					"(SELECT\r\n" + 
					"	COUNT(1) AS `receivedQty`,\r\n" + 
					"	item.`service_order_return_id`\r\n" + 
					"	FROM `service_order_item_return` item\r\n" + 
					"	WHERE item.`received`=TRUE\r\n" + 
					"	GROUP BY  item.`service_order_return_id`\r\n" + 
					") AS `qty`\r\n" + 
					"ON qty.`service_order_return_id`=sor.`id`\r\n" +
					"WHERE IFNULL(qty.`receivedQty`,0)=0 \r\n"+
					"AND (sl.`location_id` IN (:ids) OR dl.`location_id` IN (:ids)) \r\n")
	Page<Map<String, Object>> getPendingToReceiveSOR(@Param("ids")java.util.List<Long> ids, Pageable page);
	
	@Query(value="SELECT \r\n" + 
			"sor.`id` AS `id`,\r\n" + 
			"sor.`manual_id` AS `manualId`,\r\n" + 
			"sor.`ship_to_name` AS `shipToName`,\r\n" + 
			"sor.`shipping_mode` AS `shippingMode`,\r\n" + 
			"sor.`tracking_number` AS `trackingNumber`,\r\n" + 
			"sor.`total_item` AS `returnedQty`,\r\n" + 
			"IFNULL(qty.`receivedQty`,0) AS `receivedQty`,\r\n" + 
			"(CASE\r\n" + 
			"	WHEN sor.`total_item`!=0 AND IFNULL(qty.`receivedQty`,0)!=0 AND sor.`total_item`=IFNULL(qty.`receivedQty`,0) THEN 'Received'\r\n" + 
			"	WHEN sor.`total_item`!=0 AND IFNULL(qty.`receivedQty`,0)!=0 AND sor.`total_item`>IFNULL(qty.`receivedQty`,0) THEN 'Partially Received'\r\n" + 
			"	WHEN IFNULL(qty.`receivedQty`,0)=0 THEN 'Pending to Receive'\r\n" + 
			"END) AS `status`,\r\n" + 
			"sor.`created_date` AS `createdDate`,\r\n" + 
			"sor.`modified_date` AS `modifiedDate`,\r\n" + 
			"sor.`created_by_email_id` AS `createdByEmailId`,\r\n" + 
			"CONCAT(CONCAT(cby.`first_name`,' '),IFNULL(CONCAT(cby.`middle_name`,' '),''),cby.`last_name`) AS `createdByName`,\r\n" + 
			"sor.`modified_by_email_id` AS `modifiedByEmailId`,\r\n" + 
			"CONCAT(CONCAT(mby.`first_name`,' '),IFNULL(CONCAT(mby.`middle_name`,' '),''),mby.`last_name`) AS `modifiedByName`,\r\n" + 
			"sor.`ownner_email_id` AS `ownerEmailId`,\r\n" + 
			"CONCAT(CONCAT(onr.`first_name`,' '),IFNULL(CONCAT(onr.`middle_name`,' '),''),onr.`last_name`) AS `ownerName`,\r\n" + 
			"sor.`destination_location_location_id` AS `sourceLocationId`,\r\n" + 
			"dl.`location_name` AS `destinationLocationName`,\r\n" + 
			"sor.`source_loccation_location_id` AS `destinationLocationId`,\r\n" + 
			"sl.`location_name` AS `sourceLocationName`\r\n" + 
			"FROM `service_order_return` sor\r\n" + 
			"JOIN `employee_details` cby ON cby.`email_id`=sor.`created_by_email_id`\r\n" + 
			"JOIN `employee_details` mby ON mby.`email_id`=sor.`modified_by_email_id`\r\n" + 
			"JOIN `employee_details` onr ON onr.`email_id`=sor.`ownner_email_id`\r\n" + 
			"JOIN `location` sl ON sl.`location_id`=sor.`source_loccation_location_id`\r\n" + 
			"JOIN `location` dl ON dl.`location_id`=sor.`destination_location_location_id`\r\n" + 
			"LEFT JOIN \r\n" + 
			"(SELECT\r\n" + 
			"	COUNT(1) AS `receivedQty`,\r\n" + 
			"	item.`service_order_return_id`\r\n" + 
			"	FROM `service_order_item_return` item\r\n" + 
			"	WHERE item.`received`=TRUE\r\n" + 
			"	GROUP BY  item.`service_order_return_id`\r\n" + 
			") AS `qty`\r\n" + 
			"ON qty.`service_order_return_id`=sor.`id`\r\n"+
			"WHERE sl.`location_id` IN (:ids) OR dl.`location_id` IN (:ids) \r\n",
			nativeQuery=true, 
			countQuery="SELECT \r\n" + 
					"COUNT(*) AS countQuery\r\n" +
					"FROM `service_order_return` sor\r\n" + 
					"JOIN `employee_details` cby ON cby.`email_id`=sor.`created_by_email_id`\r\n" + 
					"JOIN `employee_details` mby ON mby.`email_id`=sor.`modified_by_email_id`\r\n" + 
					"JOIN `employee_details` onr ON onr.`email_id`=sor.`ownner_email_id`\r\n" + 
					"JOIN `location` sl ON sl.`location_id`=sor.`source_loccation_location_id`\r\n" + 
					"JOIN `location` dl ON dl.`location_id`=sor.`destination_location_location_id`\r\n" + 
					"LEFT JOIN \r\n" + 
					"(SELECT\r\n" + 
					"	COUNT(1) AS `receivedQty`,\r\n" + 
					"	item.`service_order_return_id`\r\n" + 
					"	FROM `service_order_item_return` item\r\n" + 
					"	WHERE item.`received`=TRUE\r\n" + 
					"	GROUP BY  item.`service_order_return_id`\r\n" + 
					") AS `qty`\r\n" + 
					"ON qty.`service_order_return_id`=sor.`id`\r\n"+
					"WHERE sl.`location_id` IN (:ids) OR dl.`location_id` IN (:ids) \r\n")
	Page<Map<String, Object>> getSOR(@Param("ids")java.util.List<Long> ids, Pageable page);
	
	
	@Query(value="SELECT \r\n" + 
			"sor.`id` AS `id`,\r\n" + 
			"sor.`manual_id` AS `manualId`,\r\n" + 
			"sor.`ship_to_name` AS `shipToName`,\r\n" + 
			"sor.`shipping_mode` AS `shippingMode`,\r\n" + 
			"sor.`tracking_number` AS `trackingNumber`,\r\n" + 
			"sor.`total_item` AS `returnedQty`,\r\n" + 
			"IFNULL(qty.`receivedQty`,0) AS `receivedQty`,\r\n" + 
			"(CASE\r\n" + 
			"	WHEN sor.`total_item`!=0 AND IFNULL(qty.`receivedQty`,0)!=0 AND sor.`total_item`=IFNULL(qty.`receivedQty`,0) THEN 'Received'\r\n" + 
			"	WHEN sor.`total_item`!=0 AND IFNULL(qty.`receivedQty`,0)!=0 AND sor.`total_item`>IFNULL(qty.`receivedQty`,0) THEN 'Partially Received'\r\n" + 
			"	WHEN IFNULL(qty.`receivedQty`,0)=0 THEN 'Pending to Receive'\r\n" + 
			"END) AS `status`,\r\n" + 
			"sor.`created_date` AS `createdDate`,\r\n" + 
			"sor.`modified_date` AS `modifiedDate`,\r\n" + 
			"sor.`created_by_email_id` AS `createdByEmailId`,\r\n" + 
			"CONCAT(CONCAT(cby.`first_name`,' '),IFNULL(CONCAT(cby.`middle_name`,' '),''),cby.`last_name`) AS `createdByName`,\r\n" + 
			"sor.`modified_by_email_id` AS `modifiedByEmailId`,\r\n" + 
			"CONCAT(CONCAT(mby.`first_name`,' '),IFNULL(CONCAT(mby.`middle_name`,' '),''),mby.`last_name`) AS `modifiedByName`,\r\n" + 
			"sor.`ownner_email_id` AS `ownerEmailId`,\r\n" + 
			"CONCAT(CONCAT(onr.`first_name`,' '),IFNULL(CONCAT(onr.`middle_name`,' '),''),onr.`last_name`) AS `ownerName`,\r\n" + 
			"sor.`destination_location_location_id` AS `sourceLocationId`,\r\n" + 
			"dl.`location_name` AS `destinationLocationName`,\r\n" + 
			"sor.`source_loccation_location_id` AS `destinationLocationId`,\r\n" + 
			"sl.`location_name` AS `sourceLocationName`\r\n" + 
			"FROM `service_order_return` sor\r\n" + 
			"JOIN `employee_details` cby ON cby.`email_id`=sor.`created_by_email_id`\r\n" + 
			"JOIN `employee_details` mby ON mby.`email_id`=sor.`modified_by_email_id`\r\n" + 
			"JOIN `employee_details` onr ON onr.`email_id`=sor.`ownner_email_id`\r\n" + 
			"JOIN `location` sl ON sl.`location_id`=sor.`source_loccation_location_id`\r\n" + 
			"JOIN `location` dl ON dl.`location_id`=sor.`destination_location_location_id`\r\n" + 
			"LEFT JOIN \r\n" + 
			"(SELECT\r\n" + 
			"	COUNT(1) AS `receivedQty`,\r\n" + 
			"	item.`service_order_return_id`\r\n" + 
			"	FROM `service_order_item_return` item\r\n" + 
			"	WHERE item.`received`=TRUE\r\n" + 
			"	GROUP BY  item.`service_order_return_id`\r\n" + 
			") AS `qty`\r\n" + 
			"ON qty.`service_order_return_id`=sor.`id`\r\n"+
			"WHERE sor.`id`=:serivceOrderReturnId",
			nativeQuery=true)
	Map<String, Object> getSORById(@Param("serivceOrderReturnId")long serivceOrderReturnId);
	
	
	
	@Query(value="SELECT \r\n" + 
			"	itemreturn.`id` AS reurnItemId,\r\n" + 
			"	itemreturn.`service_order_return_id` AS returnIdServiceOrder,\r\n" + 
			"	itemreturn.`state_product_state_id` AS productFinalStateId,\r\n" + 
			"	item.`circle` AS circleName,\r\n" + 
			"	item.`customer_inventory` AS isCustomerInventory,\r\n" + 
			"	item.`customer_name` AS customerName,\r\n" + 
			"	item.`customer_sap` AS customerSAP,\r\n" + 
			"	item.`product_product_part_code` AS productPartCode,\r\n" + 
			"	prd.`pdoduct_description` AS productDescription,\r\n"+
			"	item.`serial_number` AS itemSerialNumber,\r\n" + 
			"	item.`service_order_id` AS serviceOrderId,\r\n" + 
			"	item.`state_product_state_id` AS previousStatteId,\r\n" +
			"	item.`received` AS received,\r\n"+
			"	stfinal.`product_state_name` AS finalProductStateName,\r\n" + 
			"	prestate.`product_state_name` AS previousProductFinalStateName,\r\n" + 
			"	sord.manual_id as serviceOrderManualId,\r\n"+
			"	item.`remark` AS `remarks`,\r\n"+
			"	item.`lot_no` AS `lotNo`\r\n"+
			"	\r\n" + 
			"	FROM `service_order_item_return` itemreturn\r\n" + 
			"	JOIN `service_order_item_receive` itemreceive ON itemreturn.`id`=itemreceive.`item_id`\r\n" + 
			"	JOIN `service_order_item` item ON item.`id`=itemreceive.`item_id`\r\n" + 
			"	JOIN `service_order` sord ON item.`service_order_id`=sord.`id`\r\n" + 
			"	JOIN `products` prd ON prd.`product_part_code`=item.`product_product_part_code`\r\n"+
			"	JOIN `product_line_item_product_state` stfinal ON stfinal.`product_state_id`=itemreturn.`state_product_state_id`\r\n" + 
			"	JOIN `product_line_item_product_state` prestate ON prestate.`product_state_id`=item.`state_product_state_id`\r\n" + 
			"	WHERE itemreturn.`service_order_return_id`=:serivceOrderReturnId",
			nativeQuery=true,
			countQuery="SELECT \r\n" + 
					"	COUNT(*) AS `countQuery`\r\n"+
					"	FROM `service_order_item_return` itemreturn\r\n" + 
					"	JOIN `service_order_item_receive` itemreceive ON itemreturn.`id`=itemreceive.`item_id`\r\n" + 
					"	JOIN `service_order_item` item ON item.`id`=itemreceive.`item_id`\r\n" + 
					"	JOIN `service_order` sord ON item.`service_order_id`=sord.`id`\r\n" + 
					"	JOIN `products` prd ON prd.`product_part_code`=item.`product_product_part_code`\r\n"+
					"	JOIN `product_line_item_product_state` stfinal ON stfinal.`product_state_id`=itemreturn.`state_product_state_id`\r\n" + 
					"	JOIN `product_line_item_product_state` prestate ON prestate.`product_state_id`=item.`state_product_state_id`\r\n" + 
					"	WHERE itemreturn.`service_order_return_id`=:serivceOrderReturnId")
	Page<Map<String, Object>> getSORItemSORById(@Param("serivceOrderReturnId")long serivceOrderReturnId, Pageable page);
}
