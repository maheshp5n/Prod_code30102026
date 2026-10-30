package com.pratikshat.exicom.repository;

import java.util.List;
import java.util.Map;

import com.pratikshat.exicom.entity.BtServiceOrderItemReturn;
import com.pratikshat.exicom.repository.custom.BtServiceOrderItemReturnRepositoryCustom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BtServiceOrderItemReturnRepository extends JpaRepository<BtServiceOrderItemReturn,Long>,BtServiceOrderItemReturnRepositoryCustom {
    @Query(value = "SELECT "
    +"	rtn.`id` AS returnId, "
	+"	rtn.`created_date` AS returnCreatedDate, "
	+"	rtn.`last_modified_date` AS returnLastModifiedDate, "
	+"	rtn.`manual_id` AS returnManualId, "
	+"	rtn.`received` AS returnIsReceived, "
	+"	rtn.`created_by_email_id` AS returnCreatedByEMailId, "
	+"	rtn.`last_modified_by_email_id` AS returnLastModifiedByEmailId, "
	+"	rtn.`item_receive_id` AS soItemReceivedId, "
	+"	rtn.`owner_email_id` AS returnOwnerEmailId, "
	+"	rtn.`so_return_id` AS serviceOrderReturnId, "
	+"	item.`a_status` AS arrivedStatus, "
	+"	item.`r_status` AS soReturnedStatus, "
	+"	item.`manual_id` AS soItemManuId, "
	+"	item.`serial_number` AS itemSerialNumber, "
	+"	item.`customer_inventory` AS isCustomerInventory, "
	+"	item.`in_customer_inventory_id` AS brcCustomerNumber, "
	+"	prd.`manual_id` AS productPartcode, "
	+"	prd.`product_type` AS productType, "
	+"	prd.`product_description` AS productDescription "
    +"	FROM `bt_service_order_item_return` rtn "
	+"	JOIN `bt_service_order_item` item ON rtn.id=item.id "
	+"	JOIN `bt_products` prd ON item.`product_id`=prd.id WHERE rtn.`so_return_id`=:id",nativeQuery = true)
    List<Map<String,Object>> getListbysoreturnId(@Param("id") Long id);

    @Query(value = "SELECT "
    +"	rtn.`id` AS returnId, "
	+"	rtn.`created_date` AS returnCreatedDate, "
	+"	rtn.`last_modified_date` AS returnLastModifiedDate, "
	+"	rtn.`manual_id` AS returnManualId, "
	+"	rtn.`received` AS returnIsReceived, "
	+"	rtn.`created_by_email_id` AS returnCreatedByEMailId, "
	+"	rtn.`last_modified_by_email_id` AS returnLastModifiedByEmailId, "
	+"	rtn.`item_receive_id` AS soItemReceivedId, "
	+"	rtn.`owner_email_id` AS returnOwnerEmailId, "
	+"	rtn.`so_return_id` AS serviceOrderReturnId, "
	+"	item.`a_status` AS arrivedStatus, "
	+"	item.`r_status` AS soReturnedStatus, "
	+"	item.`manual_id` AS soItemManuId, "
	+"	item.`serial_number` AS itemSerialNumber, "
	+"	item.`customer_inventory` AS isCustomerInventory, "
	+"	item.`in_customer_inventory_id` AS brcCustomerNumber, "
	+"	prd.id as productId, "
	+"	prd.`manual_id` AS productPartcode, "
	+"	prd.`product_type` AS productType, "
	+"	prd.`product_description` AS productDescription "
    +"	FROM `bt_service_order_item_return` rtn "
	+"	JOIN `bt_service_order_item` item ON rtn.id=item.id "
	+"	JOIN `bt_products` prd ON item.`product_id`=prd.id WHERE rtn.`so_return_id`=:id",nativeQuery = true,countQuery = "SELECT count(*) as ct "
    +"	FROM `bt_service_order_item_return` rtn "
	+"	JOIN `bt_service_order_item` item ON rtn.id=item.id "
	+"	JOIN `bt_products` prd ON item.`product_id`=prd.id WHERE rtn.`so_return_id`=:id")
    Page<Map<String,Object>> getPagebysoreturnId(@Param("id") Long id,Pageable page);


	@Query(value = "SELECT "
	+" item.`serial_number` AS itemSerialNumber, "
	+" item.`product_id` AS productId, "
	+" prd.`manual_id` AS productPartcode, "
	+" prd.`product_type` AS productType, "
	+" prd.`product_description` AS productDescription, "
	+" sord.`manual_id` AS serviceOrderManualId, "
	+" sord.`id` AS serviceOrderId, "
	+" item.`r_status` AS returnStatus, "
	+" reo.`id` AS itemReturnId, "
	+" rnt.`tracking_number` AS trackingNumber, "
	+" rnt.`shipping_to` AS shippTo, "
	+" rnt.`shippint_mode` AS shippintMode, "
	+" rnt.`id` as serviceOrderReturnId, "
	+" rnt.`manual_id` as serviceOrderReturnManualId, "
	+" dloc.location_id AS destinationLocationid, "
	+" sloc.location_id AS sourceLocationId, "
	+" dloc.location_name AS destinationLocationName, "
	+" sloc.location_name AS sourceLocationName "

    +" FROM `bt_service_order_item_return` reo  "
	+" JOIN bt_service_order_return rnt ON rnt.id=reo.`so_return_id` "
	+" JOIN `bt_service_order_item` item ON item.id=reo.id "
	+" JOIN `bt_products` prd ON prd.id=item.`product_id` "
	+" JOIN `bt_service_order` sord ON sord.id=item.`service_order_id` "
	+" JOIN location sloc ON sloc.location_id=rnt.`source_location_location_id` "
	+" JOIN location dloc ON dloc.location_id=rnt.`destination_location_location_id` WHERE dloc.location_id=:locationId AND reo.`received`=FALSE "
	+" ",nativeQuery = true)
    public List<Map<String,Object>> getPendingItemToreceive(@Param("locationId") Long locationId);
    
}
