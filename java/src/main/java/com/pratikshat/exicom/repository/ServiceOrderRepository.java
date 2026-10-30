package com.pratikshat.exicom.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.ServiceOrder;

@Repository
public interface ServiceOrderRepository extends JpaRepository<ServiceOrder, Long>,ServiceOrderRepositoryCustome {
	@Query(value="SELECT \r\n" + 
			"	sord.`id` AS id,\r\n" + 
			"	sord.`manual_id` AS manualId,\r\n" + 
			"	sord.`created_date` AS createdDate,\r\n" + 
			"	sord.`last_modified_date` AS lastModifiedDate,\r\n" + 
			"	sord.`order_status_id` AS orderStatusId,\r\n" + 
			"	sos.`name` AS orderStatusName,\r\n" + 
			"	sord.`repari_center_location_id` AS repairCenterLocationId,\r\n" + 
			"	dloc.`location_name` AS repariCenterLocationName,\r\n" + 
			"	sloc.`location_name` AS warehouseName,\r\n" + 
			"	sord.`ware_house_location_id` AS wareHouseId,\r\n" + 
			"	sord.`total_item` AS totalNumberOfItem,\r\n" + 
			"	cemp.`email_id` AS createdByEmailId,\r\n" + 
			"	cemp.`first_name` AS createdByFirstName,\r\n" + 
			"	cemp.`middle_name` AS createdByMiddleName,\r\n" + 
			"	cemp.`last_name` AS createdByLastName,\r\n" + 
			"	memp.`email_id` AS lastModifiedByEmailD,\r\n" + 
			"	memp.`first_name` AS lastModifiedByFirstName,\r\n" + 
			"	memp.`middle_name` AS lastModifiedByMiddleName,\r\n" + 
			"	memp.`last_name` AS lastModifiedByLastName\r\n" + 
			"	\r\n" + 
			"	\r\n" + 
			" FROM service_order sord \r\n" + 
			"	JOIN `location` sloc ON sloc.`location_id`=sord.`ware_house_location_id`\r\n" + 
			"	JOIN `location` dloc ON dloc.`location_id`=sord.`repari_center_location_id`\r\n" + 
			"	JOIN `service_order_status` sos ON sos.`id`=sord.`order_status_id`\r\n" + 
			"	JOIN `employee_details` cemp ON cemp.`email_id`=sord.`created_by_email_id`\r\n" + 
			"	JOIN `employee_details` memp ON memp.`email_id`=sord.`modified_by_email_id`\r\n" + 
			"	WHERE sord.so_status <>'Archived' and (sloc.`location_id` IN (:ids) OR (dloc.`location_id` IN (:ids) AND sos.`id`!=1)) and sord.`order_status_id` in (:statusids)",nativeQuery=true,countQuery = "SELECT \r\n" + 
					"count(*) \r\n"+
					" FROM service_order sord \r\n" + 
					"	JOIN `location` sloc ON sloc.`location_id`=sord.`ware_house_location_id`\r\n" + 
					"	JOIN `location` dloc ON dloc.`location_id`=sord.`repari_center_location_id`\r\n" + 
					"	JOIN `service_order_status` sos ON sos.`id`=sord.`order_status_id`\r\n" + 
					"	JOIN `employee_details` cemp ON cemp.`email_id`=sord.`created_by_email_id`\r\n" + 
					"	JOIN `employee_details` memp ON memp.`email_id`=sord.`modified_by_email_id`\r\n" + 
					"	WHERE sord.so_status <>'Archived' and (sloc.`location_id` IN (:ids) OR (dloc.`location_id` IN (:ids) AND sos.`id`!=1)) and sord.`order_status_id` in (:statusids)")
	public Page<Map<String,Object>> getServiceOrders(@Param("ids") List<Long>ids,@Param("statusids")List<Integer>statusIds,Pageable page);
	
	
	//QUERY FOR VIEW ALL AUTHORITIES
	@Query(value="SELECT \r\n" + 
			"	sord.`id` AS id,\r\n" + 
			"	sord.`manual_id` AS manualId,\r\n" + 
			"	sord.`created_date` AS createdDate,\r\n" + 
			"	sord.`last_modified_date` AS lastModifiedDate,\r\n" + 
			"	sord.`order_status_id` AS orderStatusId,\r\n" + 
			"	sos.`name` AS orderStatusName,\r\n" + 
			"	sord.`repari_center_location_id` AS repairCenterLocationId,\r\n" + 
			"	dloc.`location_name` AS repariCenterLocationName,\r\n" + 
			"	sloc.`location_name` AS warehouseName,\r\n" + 
			"	sord.`ware_house_location_id` AS wareHouseId,\r\n" + 
			"	sord.`total_item` AS totalNumberOfItem,\r\n" + 
			"	cemp.`email_id` AS createdByEmailId,\r\n" + 
			"	cemp.`first_name` AS createdByFirstName,\r\n" + 
			"	cemp.`middle_name` AS createdByMiddleName,\r\n" + 
			"	cemp.`last_name` AS createdByLastName,\r\n" + 
			"	memp.`email_id` AS lastModifiedByEmailD,\r\n" + 
			"	memp.`first_name` AS lastModifiedByFirstName,\r\n" + 
			"	memp.`middle_name` AS lastModifiedByMiddleName,\r\n" + 
			"	memp.`last_name` AS lastModifiedByLastName\r\n" + 
			"	\r\n" + 
			"	\r\n" + 
			" FROM service_order sord \r\n" + 
			"	JOIN `location` sloc ON sloc.`location_id`=sord.`ware_house_location_id`\r\n" + 
			"	JOIN `location` dloc ON dloc.`location_id`=sord.`repari_center_location_id`\r\n" + 
			"	JOIN `service_order_status` sos ON sos.`id`=sord.`order_status_id`\r\n" + 
			"	JOIN `employee_details` cemp ON cemp.`email_id`=sord.`created_by_email_id`\r\n" + 
			"	JOIN `employee_details` memp ON memp.`email_id`=sord.`modified_by_email_id`\r\n" + 
			"	WHERE sord.so_status <>'Archived' and sord.`order_status_id` IN (:statusids)",nativeQuery=true,countQuery = "SELECT \r\n" + 
					"count(*)\r\n"+ 
					" FROM service_order sord \r\n" + 
					"	JOIN `location` sloc ON sloc.`location_id`=sord.`ware_house_location_id`\r\n" + 
					"	JOIN `location` dloc ON dloc.`location_id`=sord.`repari_center_location_id`\r\n" + 
					"	JOIN `service_order_status` sos ON sos.`id`=sord.`order_status_id`\r\n" + 
					"	JOIN `employee_details` cemp ON cemp.`email_id`=sord.`created_by_email_id`\r\n" + 
					"	JOIN `employee_details` memp ON memp.`email_id`=sord.`modified_by_email_id`\r\n" + 
					"	WHERE sord.so_status <>'Archived' and sord.`order_status_id` IN (:statusids)")
	public Page<Map<String,Object>> getServiceOrders(@Param("statusids")List<Integer>statisIds,Pageable page);
	
	@Query(value="SELECT \r\n" + 
			"	sord.`id` AS id,\r\n" + 
			"	sord.`manual_id` AS manualId,\r\n" + 
			"	sord.`created_date` AS createdDate,\r\n" + 
			"	sord.`last_modified_date` AS lastModifiedDate,\r\n" + 
			"	sord.`order_status_id` AS orderStatusId,\r\n" + 
			"	sos.`name` AS orderStatusName,\r\n" + 
			"	sord.`repari_center_location_id` AS repairCenterLocationId,\r\n" + 
			"	dloc.`location_name` AS repariCenterLocationName,\r\n" + 
			"	sloc.`location_name` AS warehouseName,\r\n" + 
			"	sord.`ware_house_location_id` AS wareHouseId,\r\n" + 
			"	sord.`total_item` AS totalNumberOfItem,\r\n" + 
			"	cemp.`email_id` AS createdByEmailId,\r\n" + 
			"	cemp.`first_name` AS createdByFirstName,\r\n" + 
			"	cemp.`middle_name` AS createdByMiddleName,\r\n" + 
			"	cemp.`last_name` AS createdByLastName,\r\n" + 
			"	memp.`email_id` AS lastModifiedByEmailD,\r\n" + 
			"	memp.`first_name` AS lastModifiedByFirstName,\r\n" + 
			"	memp.`middle_name` AS lastModifiedByMiddleName,\r\n" + 
			"	memp.`last_name` AS lastModifiedByLastName,\r\n" + 
			"	sord.total_received AS totalReceived,\r\n"+
			" 	sord.ship_to_name AS shipName,\r\n"+
			" 	sord.shipping_mode AS shippingMode,\r\n"+
			"	sord.tracking_number AS trackingNumber\r\n"+
			"	\r\n" + 
			"	\r\n" + 
			" FROM service_order sord \r\n" + 
			"	JOIN `location` sloc ON sloc.`location_id`=sord.`ware_house_location_id`\r\n" + 
			"	JOIN `location` dloc ON dloc.`location_id`=sord.`repari_center_location_id`\r\n" + 
			"	JOIN `service_order_status` sos ON sos.`id`=sord.`order_status_id`\r\n" + 
			"	JOIN `employee_details` cemp ON cemp.`email_id`=sord.`created_by_email_id`\r\n" + 
			"	JOIN `employee_details` memp ON memp.`email_id`=sord.`modified_by_email_id`\r\n" + 
			"	WHERE sord.so_status <>'Archived' and sord.`id`=:id AND (sloc.`location_id` IN (:ids) OR (dloc.`location_id` IN (:ids) AND sos.`id`!=1))",nativeQuery=true)
	public Map<String,Object> getServiceOrder(@Param("id")Long id,@Param("ids")List<Long>ids);
	
	@Query(value="SELECT \r\n" + 
			"	sord.`id` AS id,\r\n" + 
			"	sord.`manual_id` AS manualId,\r\n" + 
			"	sord.`created_date` AS createdDate,\r\n" + 
			"	sord.`last_modified_date` AS lastModifiedDate,\r\n" + 
			"	sord.`order_status_id` AS orderStatusId,\r\n" + 
			"	sos.`name` AS orderStatusName,\r\n" + 
			"	sord.`repari_center_location_id` AS repairCenterLocationId,\r\n" + 
			"	dloc.`location_name` AS repariCenterLocationName,\r\n" + 
			"	sloc.`location_name` AS warehouseName,\r\n" + 
			"	sord.`ware_house_location_id` AS wareHouseId,\r\n" + 
			"	sord.`total_item` AS totalNumberOfItem,\r\n" + 
			"	cemp.`email_id` AS createdByEmailId,\r\n" + 
			"	cemp.`first_name` AS createdByFirstName,\r\n" + 
			"	cemp.`middle_name` AS createdByMiddleName,\r\n" + 
			"	cemp.`last_name` AS createdByLastName,\r\n" + 
			"	memp.`email_id` AS lastModifiedByEmailD,\r\n" + 
			"	memp.`first_name` AS lastModifiedByFirstName,\r\n" + 
			"	memp.`middle_name` AS lastModifiedByMiddleName,\r\n" + 
			"	memp.`last_name` AS lastModifiedByLastName,\r\n" + 
			"	sord.total_received AS totalReceived,\r\n"+
			" 	sord.ship_to_name AS shipName,\r\n"+
			" 	sord.shipping_mode AS shippingMode,\r\n"+
			"	sord.tracking_number AS trackingNumber\r\n"+
			"	\r\n" + 
			"	\r\n" + 
			" FROM service_order sord \r\n" + 
			"	JOIN `location` sloc ON sloc.`location_id`=sord.`ware_house_location_id`\r\n" + 
			"	JOIN `location` dloc ON dloc.`location_id`=sord.`repari_center_location_id`\r\n" + 
			"	JOIN `service_order_status` sos ON sos.`id`=sord.`order_status_id`\r\n" + 
			"	JOIN `employee_details` cemp ON cemp.`email_id`=sord.`created_by_email_id`\r\n" + 
			"	JOIN `employee_details` memp ON memp.`email_id`=sord.`modified_by_email_id`\r\n" + 
			"	WHERE sord.so_status <>'Archived' and sord.`id`=:id",nativeQuery=true)
	public Map<String,Object> getServiceOrder(@Param("id")Long id);
	
	@Query(value="SELECT \r\n" + 
			"lineitem.`id` AS lineItemId,\r\n" + 
			"lineitem.`customer_inventory` AS isCustomerInventory,\r\n" + 
			"lineitem.`customer_name` AS customerName,\r\n" + 
			"lineitem.`customer_sap_number` AS customerSapNumber,\r\n" + 
			"lineitem.`item_serial_number` AS itemSerialNumber,\r\n" + 
			"item.`product_product_part_code` AS partCode,\r\n" + 
			"lstate.product_state_name AS productStatus,\r\n" + 
			"prod.`pdoduct_description` AS partCodeDescription,\r\n" +
			"lineitem.`remarks` AS `remarks`,\r\n"+
			"lineitem.`lot_no` AS `lotNo`,\r\n"+
			"lineitem.`modified_date` AS `modifiedDate`"+
			"FROM `location_product_line_items` lineitem\r\n" + 
			"JOIN location_product_item item ON item.`id`=lineitem.`location_product_item_id`\r\n" + 
			"JOIN product_line_item_product_state lstate ON lstate.product_state_id=lineitem.location_proeuct_line_item_state_product_state_id\r\n" + 
			"JOIN location loc ON item.`location_location_id`=loc.`location_id`\r\n" + 
			"JOIN products prod ON prod.`product_part_code`=item.`product_product_part_code`\r\n" + 
			"JOIN service_recource sr ON sr.location_location_id=loc.location_id\r\n" + 
			"WHERE lineitem.`location_proeuct_line_item_state_product_state_id`=2 AND sr.`user_email_id`=:userEmailId",nativeQuery=true)
	public List<Map<String,Object>> getNotOkeItems(@Param("userEmailId")String emailId);
	
	@Query(value="SELECT   \r\n"
			+ "	loc.`location_id` AS locationId,  \r\n"
			+ "	loc.`location_name` AS warehouseName,  \r\n"
			+ "	llg.`legal_entity_name`  \r\n"
			+ "		FROM location loc   \r\n"
			+ "		JOIN `legal_entity` llg ON loc.`legal_entity_legal_entity_id`=llg.`legal_entity_id`   \r\n"
			+ "		WHERE llg.`legal_entity_type_type_id`!=3 and loc.business_domain_business_domain_id in (2,3)",nativeQuery=true)
	public List<Map<String,Object>> getRepariCenter();
	
	@Query(value="SELECT item.`circle` AS circleName,\r\n" + 
			"	item.`customer_inventory` AS isCustomerInventory,\r\n" + 
			"	item.`customer_name` AS customerName,\r\n" + 
			"	item.`customer_sap` AS customerSAP,\r\n" + 
			"	item.`id` AS serviceOrderItemId,\r\n" + 
			"	item.`product_product_part_code` AS partCode,\r\n" + 
			"	item.`received` AS isReceived,\r\n" + 
			"	item.`serial_number` AS itemSerialNumber,\r\n" + 
			"	item.`service_order_id` AS serviceOrderId,\r\n" + 
			"	item.`state_product_state_id` AS stateId,\r\n" + 
			"	st.`product_state_name` AS state,\r\n" + 
			"	prod.`pdoduct_description` AS productDescription,\r\n" + 
			"	srr.service_resource_id as assignedResourceId,\r\n"+
			"	srr.name as assignedToEng,\r\n"+
			"	item.`remark` AS `remarks`,\r\n"+
			"	item.`lot_no` AS `lotNo`\r\n"+
			"\r\n" + 
			"	FROM service_order_item item \r\n" + 
			"	JOIN products prod ON prod.`product_part_code`=item.`product_product_part_code`\r\n" + 
			"	JOIN `product_line_item_product_state` st ON st.`product_state_id`=item.`state_product_state_id` \r\n" + 
			"	LEFT JOIN service_order_item_receive sritem on sritem.item_id=item.id\r\n"+
			"	LEFT JOIN service_recource srr on srr.service_resource_id=sritem.assigned_to_service_resource_id\r\n"+
			"	WHERE item.`service_order_id`=:id\r\n" + 
			"",nativeQuery=true)
	public List<Map<String,Object>> getServiceOrderItems(@Param("id")Long id);
	
	@Query(value="SELECT 	item.`id` AS itemId,\r\n" + 
			"	item.`circle` AS circleName,\r\n" + 
			"	item.`customer_inventory` AS isCustomerInventory,\r\n" + 
			"	item.`customer_name` AS customerName,\r\n" + 
			"	item.`customer_sap` AS customerSapNumber,\r\n" + 
			"	item.`product_product_part_code` AS productPartCode,\r\n" + 
			"	item.`received` AS isReceived,\r\n" + 
			"	item.`serial_number` AS itemSerialNumber,\r\n" + 
			"	item.`state_product_state_id` AS productStateCode,\r\n" + 
			"	prd.`pdoduct_description` AS productDescription,\r\n" + 
			"	ordd.`id` AS serviceOrderId,\r\n" + 
			"	ordd.`manual_id` AS serviceOrderManualId,\r\n" + 
			"	ordd.`created_date` AS orderPlacedDate,\r\n" + 
			"	ware.`location_name` AS wareHouseName,\r\n" + 
			"	ware.`location_id` AS warehouseId,\r\n" + 
			"	item.`remark` AS `remarks`,\r\n"+
			"	item.`lot_no` AS `lotNo`\r\n"+
			"\r\n" + 
			"	FROM service_order ordd\r\n" + 
			"	JOIN service_order_item item ON ordd.`id`=item.`service_order_id`\r\n" + 
			"	JOIN products prd ON prd.`product_part_code`=item.`product_product_part_code`\r\n" + 
			"	JOIN location loc ON loc.`location_id`=ordd.`repari_center_location_id`\r\n" + 
			"	JOIN location ware ON ware.`location_id`=ordd.`ware_house_location_id`\r\n" + 
			"	JOIN service_recource sr on sr.location_location_id=loc.location_id\r\n"+
			"	WHERE ordd.so_status <>'Archived' and item.`received`=0 AND sr.`user_email_id`=:userEmailId",nativeQuery=true)
	public List<Map<String,Object>> getPendingToReceiveByUserId(@Param("userEmailId")String emailId);
	
	@Query(value="SELECT 	item.`id` AS itemId,\r\n" + 
			"	item.`circle` AS circleName,\r\n" + 
			"	item.`customer_inventory` AS isCustomerInventory,\r\n" + 
			"	item.`customer_name` AS customerName,\r\n" + 
			"	item.`customer_sap` AS customerSapNumber,\r\n" + 
			"	item.`product_product_part_code` AS productPartCode,\r\n" + 
			"	item.`received` AS isReceived,\r\n" + 
			"	item.`serial_number` AS itemSerialNumber,\r\n" + 
			"	item.`state_product_state_id` AS productStateCode,\r\n" + 
			"	prd.`pdoduct_description` AS productDescription,\r\n" + 
			"	ordd.`id` AS serviceOrderId,\r\n" + 
			"	ordd.`manual_id` AS serviceOrderManualId,\r\n" + 
			"	ordd.`created_date` AS orderPlacedDate,\r\n" + 
			"	ware.`location_name` AS wareHouseName,\r\n" + 
			"	ware.`location_id` AS warehouseId,\r\n" +
			"	item.`remark` AS `remarks`,\r\n"+
			"	item.`lot_no` AS `lotNo`\r\n"+
			"\r\n" + 
			"	FROM service_order ordd\r\n" + 
			"	JOIN service_order_item item ON ordd.`id`=item.`service_order_id`\r\n" + 
			"	JOIN products prd ON prd.`product_part_code`=item.`product_product_part_code`\r\n" + 
			"	JOIN location loc ON loc.`location_id`=ordd.`repari_center_location_id`\r\n" + 
			"	JOIN location ware ON ware.`location_id`=ordd.`ware_house_location_id`\r\n" + 
			"	JOIN service_recource sr on sr.location_location_id=loc.location_id\r\n"+
			"	WHERE ordd.so_status <>'Archived' and item.`received`=0 AND sr.`user_email_id`=:userEmailid AND ordd.id=:id",nativeQuery=true)
	public List<Map<String,Object>> getPendingToReceiveByUserIdAndOrderId(@Param("userEmailid")String emailId,@Param("id")Long id);
	
	@Query(value="SELECT \r\n" + 
			"	sre.`service_resource_id` AS resourceId,\r\n" + 
			"	sre.`is_active` AS isActive,\r\n" + 
			"	sre.`name` AS serviceResourceName\r\n" + 
			"	FROM `service_recource` sre\r\n" + 
			"	JOIN `location` loc ON loc.`location_id`=sre.`location_location_id`\r\n" + 
			"	JOIN `location` ploc ON ploc.`location_id`=loc.`parent_location_location_id`\r\n" + 
			"	JOIN service_recource sr on sr.location_location_id=ploc.location_id\r\n"+
			"	WHERE sre.`is_active`=TRUE AND sr.`user_email_id`=:userEmailId",nativeQuery=true)
	public List<Map<String,Object>> getRepairEngUnder(@Param("userEmailId")String userEmailId);
	
	
	@Query(value="SELECT ct.`email_id` AS emailId,ct.`first_name` AS firstName,ct.`middle_name` AS middleName,ct.`last_name` AS lastName FROM `contact_details` ct\r\n" + 
			"	JOIN `legal_entity` lg ON ct.`legal_entity_id`=lg.`legal_entity_id`\r\n" + 
			"	WHERE lg.`legal_entity_type_type_id`=2",nativeQuery=true)
	public List<Map<String,Object>> getRepariEngineerUser();
}
