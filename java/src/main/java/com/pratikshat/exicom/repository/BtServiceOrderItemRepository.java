package com.pratikshat.exicom.repository;

import java.util.List;
import java.util.Map;

import com.pratikshat.exicom.entity.BtServiceOrderItem;
import com.pratikshat.exicom.repository.custom.BtServiceOrderItemRepositoryCustom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BtServiceOrderItemRepository extends JpaRepository<BtServiceOrderItem,Long>,BtServiceOrderItemRepositoryCustom {

    @Query(value = "SELECT "
	+" bts.`id` AS serviceOrderId, "
	+" bts.`manual_id` AS serviceOrderManualId, "
	+" bts.`shipping_mode` AS serviceOrderShippingMode, "
	+" bts.`shipping_to` AS serviceOrderShippingTo, "
	+" bts.`tracking_number` AS serviceOrderTrackingNumber, "
	+" bts.`source_location_location_id` AS serviceOrderSourceLocationid, "
	+" sloc.location_name AS serviceOrderLocationName, "
	+" +" +" prd.`manual_id` AS productPartcode, "
	+" prd.`id` AS productId, "
	+" prd.`product_type` AS productType, "
	+" prd.`product_description` AS productDescription, "
	+" titem.`id` AS serviceOrderItemId, "
	+" titem.`manual_id` AS serviceOrderItemManualId, "
	+" titem.`customer_inventory` AS isCustomerInventory, "
	+" titem.`serial_number` serviceOrderItemSerialNumber "
 
    +" FROM `bt_service_order` bts  "
	+" JOIN `bt_service_order_item` titem ON titem.`service_order_id`=bts.id  "
	+" JOIN bt_products prd ON prd.id=titem.product_id "
	+" JOIN location sloc ON sloc.location_id = bts.`source_location_location_id` WHERE titem.`received`=FALSE AND bts.`destination_location_location_id`=:locationId",nativeQuery = true)
    List<Map<String,Object>> getPendingitemToReceive(@Param("locationId") Long locationId); 

	@Query(value = "SELECT "
	+" bts.`id` AS serviceOrderId, "
	+" bts.`manual_id` AS serviceOrderManualId, "
	+" bts.`shipping_mode` AS serviceOrderShippingMode, "
	+" bts.`shipping_to` AS serviceOrderShippingTo, "
	+" bts.`tracking_number` AS serviceOrderTrackingNumber, "
	+" bts.`source_location_location_id` AS serviceOrderSourceLocationid, "
	+" sloc.location_name AS serviceOrderLocationName, "
	+" +" +" prd.`manual_id` AS productPartcode, "
	+" prd.`id` AS productId, "
	+" prd.`product_type` AS productType, "
	+" prd.`product_description` AS productDescription, "
	+" titem.`id` AS serviceOrderItemId, "
	+" titem.`manual_id` AS serviceOrderItemManualId, "
	+" titem.`customer_inventory` AS isCustomerInventory, "
	+" titem.`serial_number` serviceOrderItemSerialNumber "
 
    +" FROM `bt_service_order` bts  "
	+" JOIN `bt_service_order_item` titem ON titem.`service_order_id`=bts.id  "
	+" JOIN bt_products prd ON prd.id=titem.product_id "
	+" JOIN location sloc ON sloc.location_id = bts.`source_location_location_id` WHERE  bts.`id` = :serviceOrderId and titem.`received`=FALSE AND bts.`destination_location_location_id`=:locationId",nativeQuery = true)
    List<Map<String,Object>> getPendingitemToReceive(@Param("serviceOrderId")Long serviceOrderId,@Param("locationId") Long locationId); 

    @Query(value = "SELECT "
	+" bts.`id` AS serviceOrderId, "
	+" bts.`manual_id` AS serviceOrderManualId, "
	+" bts.`shipping_mode` AS serviceOrderShippingMode, "
	+" bts.`shipping_to` AS serviceOrderShippingTo, "
	+" bts.`tracking_number` AS serviceOrderTrackingNumber, "
	+" bts.`source_location_location_id` AS serviceOrderSourceLocationid, "
	+" sloc.location_name AS serviceOrderLocationName, "
	+" +" +" prd.`manual_id` AS productPartcode, "
	+" prd.`id` AS productId, "
	+" prd.`product_type` AS productType, "
	+" prd.`product_description` AS productDescription, "
	+" titem.`id` AS serviceOrderItemId, "
	+" titem.`manual_id` AS serviceOrderItemManualId, "
	+" titem.`customer_inventory` AS isCustomerInventory, "
	+" titem.`serial_number` serviceOrderItemSerialNumber "
 
    +" FROM `bt_service_order` bts  "
	+" JOIN `bt_service_order_item` titem ON titem.`service_order_id`=bts.id  "
	+" JOIN bt_products prd ON prd.id=titem.product_id "
	+" JOIN location sloc ON sloc.location_id = bts.`source_location_location_id` WHERE titem.`received`=true and titem.`assigned`=false AND bts.`destination_location_location_id`=:locationId",nativeQuery = true)
    List<Map<String,Object>> getPendingItemToAssign(@Param("locationId")Long locationId);


	@Query(value = "SELECT "
	+" bts.`id` AS serviceOrderId, "
	+" bts.`manual_id` AS serviceOrderManualId, "
	+" bts.`shipping_mode` AS serviceOrderShippingMode, "
	+" bts.`shipping_to` AS serviceOrderShippingTo, "
	+" bts.`tracking_number` AS serviceOrderTrackingNumber, "
	+" bts.`source_location_location_id` AS serviceOrderSourceLocationid, "
	+" sloc.location_name AS serviceOrderLocationName, "
	+" +" +" prd.`manual_id` AS productPartcode, "
	+" prd.`id` AS productId, "
	+" prd.`product_type` AS productType, "
	+" prd.`product_description` AS productDescription, "
	+" titem.`id` AS serviceOrderItemId, "
	+" titem.`manual_id` AS serviceOrderItemManualId, "
	+" titem.`customer_inventory` AS isCustomerInventory, "
	+" titem.`serial_number` serviceOrderItemSerialNumber "
 
    +" FROM `bt_service_order` bts  "
	+" JOIN `bt_service_order_item` titem ON titem.`service_order_id`=bts.id  "
	+" JOIN bt_products prd ON prd.id=titem.product_id "
	+" JOIN location sloc ON sloc.location_id = bts.`source_location_location_id` WHERE bts.`id`=:serviceOrderId and titem.`received`=true and titem.`assigned`=false AND bts.`destination_location_location_id`=:locationId",nativeQuery = true)
    List<Map<String,Object>> getPendingItemToAssign(@Param("serviceOrderId")Long serviceOrderId,@Param("locationId")Long locationId);

    @Query(value = "SELECT "
	+" bts.`id` AS serviceOrderId, "
	+" bts.`manual_id` AS serviceOrderManualId, "
	
	+" prd.`manual_id` AS productPartcode, "
	+" prd.`id` AS productId, "
	+" prd.`product_type` AS productType, "
	+" prd.`product_description` AS productDescription, "
	+" titem.`id` AS serviceOrderItemId, "
	+" titem.`manual_id` AS serviceOrderItemManualId, "
	+" titem.`customer_inventory` AS isCustomerInventory, "
	+" titem.`serial_number` serviceOrderItemSerialNumber, "
	+" titem.`assigned` AS isAssigned, "
	+" titem.`assigned_returned` AS isAssignedReturned, "
	+" titem.`r_status` AS returnedStatus, "
	+" titem.`a_status` AS arriableStatus, "
	+" titem.`received` AS isReceived, "
	+" titem.`returned_to_source` AS isReturnedToSource, "
	+" titem.`assigned_to_email_id` AS assignedToEmailId, "

    +" aemp.`first_name` AS assignedToFirstName, "
	+" aemp.`middle_name` AS assignedToMiddleName, "
	+" aemp.`last_name` AS assignedToLastName, "
	+" btin.`id` AS brcCaseId, "
	+" btin.`manual_id` AS brcCaseManualId, "
	+" btin.`account_name` AS accountName, "
	+" btin.`account_sap_number` AS accountSapNumber "
	 
	+" FROM `bt_service_order` bts  "
	+" JOIN `bt_service_order_item` titem ON titem.`service_order_id`=bts.id  "
	+" JOIN bt_products prd ON prd.id=titem.product_id "
	+" JOIN location sloc ON sloc.location_id = bts.`source_location_location_id`  "
	+" LEFT JOIN employee_details aemp ON aemp.email_id=titem.`assigned_to_email_id` "
    +" LEFT JOIN `bt_customer_inventory_in` btin ON btin.id=titem.`in_customer_inventory_id`"
    +" where bts.id=:serviceOrderId",nativeQuery = true)
    List<Map<String,Object>> getItemByServiceOrderId(@Param("serviceOrderId")Long serviceOrderId);

    @Query(value = "SELECT "
	+" bts.`id` AS serviceOrderId, "
	+" bts.`manual_id` AS serviceOrderManualId, "
	
	+" prd.`manual_id` AS productPartcode, "
	+" prd.`id` AS productId, "
	+" prd.`product_type` AS productType, "
	+" prd.`product_description` AS productDescription, "
	+" titem.`id` AS serviceOrderItemId, "
	+" titem.`manual_id` AS serviceOrderItemManualId, "
	+" titem.`customer_inventory` AS isCustomerInventory, "
	+" titem.`serial_number` serviceOrderItemSerialNumber, "
	+" titem.`assigned` AS isAssigned, "
	+" titem.`assigned_returned` AS isAssignedReturned, "
	+" titem.`r_status` AS returnedStatus, "
	+" titem.`a_status` AS arriableStatus, "
	+" titem.`received` AS isReceived, "
	+" titem.`returned_to_source` AS isReturnedToSource, "
	+" titem.`assigned_to_email_id` AS assignedToEmailId, "

    +" aemp.`first_name` AS assignedToFirstName, "
	+" aemp.`middle_name` AS assignedToMiddleName, "
	+" aemp.`last_name` AS assignedToLastName, "
	+" btin.`id` AS brcCaseId, "
	+" btin.`manual_id` AS brcCaseManualId, "
	+" btin.`account_name` AS accountName, "
	+" btin.`account_sap_number` AS accountSapNumber "
	 
	+" FROM `bt_service_order` bts  "
	+" JOIN `bt_service_order_item` titem ON titem.`service_order_id`=bts.id  "
	+" JOIN bt_products prd ON prd.id=titem.product_id "
	+" JOIN location sloc ON sloc.location_id = bts.`source_location_location_id`  "
	+" LEFT JOIN employee_details aemp ON aemp.email_id=titem.`assigned_to_email_id` "
    +" LEFT JOIN `bt_customer_inventory_in` btin ON btin.id=titem.`in_customer_inventory_id`"
    +" where bts.id=:serviceOrderId",nativeQuery = true,countQuery = "SELECT count(*) as ct"
	 
	+" FROM `bt_service_order` bts  "
	+" JOIN `bt_service_order_item` titem ON titem.`service_order_id`=bts.id  "
	+" JOIN bt_products prd ON prd.id=titem.product_id "
	+" JOIN location sloc ON sloc.location_id = bts.`source_location_location_id`  "
	+" LEFT JOIN employee_details aemp ON aemp.email_id=titem.`assigned_to_email_id` "
    +" LEFT JOIN `bt_customer_inventory_in` btin ON btin.id=titem.`in_customer_inventory_id`"
    +" where bts.id=:serviceOrderId")
    Page<Map<String,Object>> getItemPageByServiceOrderId(@Param("serviceOrderId")Long serviceOrderId,Pageable page);


	@Query(value = "SELECT "
	+" item.`id` AS serviceOrderItemId, "
	+" item.`created_date` AS serviceOrderItemCreatedDate, "
	+" item.`last_modified_date` AS serviceOrderItemLastModifiedDate, "
	+" item.`a_status` AS serviceOrderItemAStatus, "
	+" item.`customer_inventory`  AS serviceOrderIsCustomerInventory, "
	+" item.`manual_id` AS serviceOrderItemManualId, "
	+" item.`r_status` AS serviceOrderItemReturnStatus, "
	+" item.`serial_number` AS serviceOrderItemSerialnumber, "
	+" item.`created_by_email_id` AS serviceOrderItemCreatedByEmailId, "
	+" item.`assigned_to_email_id` AS serviceOrderItemAssignedToEmailId, "
	+" item.`in_customer_inventory_id` serviceOrderItemBrcCaseId, "
	+" item.`product_id` AS productId, "
	+" prd.manual_id AS productPartcode, "
	+" prd.`product_type` AS productType, "
	+" prd.`product_description` AS productDescription, "
	+" so.`id` AS serviceOrderId, "
	+" so.`manual_id` AS serviceOrderManualId "
	

	+" FROM `bt_service_order_item` item "
	+" JOIN bt_products prd ON item.product_id=prd.id  "
	+" JOIN bt_service_order so ON so.id=item.service_order_id "
	
	+" WHERE item.`returned_to_source`=FALSE AND item.`assigned_returned`=TRUE AND so.destination_location_location_id=:sourceLocationId AND so.`source_location_location_id`=:destinationLocationId",nativeQuery = true)
	List<Map<String,Object>> getPendingItemToReturnToSource(@Param("sourceLocationId")Long sourceLocationId,@Param("destinationLocationId") Long destinationLocationId);

	@Query(value = "SELECT distinct "
	+" loc.location_id AS locationId, "
	+" loc.location_name AS locationName, "
	+" loc.plant AS locationPlant "

	+" FROM `bt_service_order_item` item "
	+" JOIN bt_service_order so ON so.id=item.service_order_id "
	+" JOIN location loc ON loc.location_id=so.`source_location_location_id` "
	+" WHERE item.`returned_to_source`=FALSE AND item.`assigned_returned`=TRUE AND so.`destination_location_location_id`=:sourceLocationId",nativeQuery = true)
	List<Map<String,Object>> getPendingLocation(@Param("sourceLocationId")Long sourceLocationId);
}
