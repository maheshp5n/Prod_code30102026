package com.pratikshat.exicom.repository;

import java.util.List;
import java.util.Map;

import com.pratikshat.exicom.entity.BtProductReturnOrder;
import com.pratikshat.exicom.entity.BtProductReturnOrderItem;
import com.pratikshat.exicom.repository.custom.BtProductReturnOrderItemRepositoryCustom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BtProductReturnOrderItemRepository extends JpaRepository<BtProductReturnOrderItem,Long>,BtProductReturnOrderItemRepositoryCustom{
    @Query("select item from BtProductReturnOrderItem item where item.returnOrder=:returnOrder")
    List<BtProductReturnOrderItem> getReturnOrderItemByReturnOrder(@Param("returnOrder")BtProductReturnOrder returnOrder);

	

    @Query(value = "SELECT "
	+" item.`id` AS returnOrderItemId, "
	+" item.`created_date` AS returnOrderItemCreatedDate, "
	+" item.`last_modified_date` AS returnOrderItemModifiedDate, "
	+" item.`manual_id` AS returnOrderItemManualId, "
	+" item.`quantity_received` AS returnOrderItemQuantityReceived, "
	+" item.`quantity_returned` AS returnOrderItemQuantityReturned, "
	+" item.`created_by_email_id` AS returnOrderItemCreatedByEmailId, "
	+" item.`last_modified_by_email_id` AS returnOrderItemLastModifiedByEmailId, "
	+" item.`owner_email_id` AS returnOrderItemOwnerEmailId, "
	+" prd.id AS productId, "
	+" prd.manual_id AS productManualId, "
	+" item.`customer_quantity_received` AS returnOrderItemCustomerQuantityReceived, "
	+" item.`customer_quantity_returned` AS returnOrderItemCustomerQuantityReturned, "
	+" ro.id AS returnOrderId, "
	+" ro.manual_id AS returnOrderManualId "
	
	+" FROM `bt_product_return_order_item` item "
	+" JOIN `bt_product_return_order` ro ON ro.id=item.`return_order_id` "
	+" JOIN `bt_products` prd ON prd.id=item.`products_id` where ro.id=:returnOrderId",nativeQuery = true,countQuery = "SELECT count(*) as ct"
	
	+" FROM `bt_product_return_order_item` item "
	+" JOIN `bt_product_return_order` ro ON ro.id=item.`return_order_id` "
	+" JOIN `bt_products` prd ON prd.id=item.`products_id` where ro.id=:returnOrderId")
    Page<Map<String,Object>> getPageReturnOrderItemsByReturnOrderId(@Param("returnOrderId")Long returnOrderId,Pageable page);

    @Query(value = "SELECT "
	+" item.`id` AS returnOrderItemId, "
	+" item.`created_date` AS returnOrderItemCreatedDate, "
	+" item.`last_modified_date` AS returnOrderItemModifiedDate, "
	+" item.`manual_id` AS returnOrderItemManualId, "
	+" item.`quantity_received` AS returnOrderItemQuantityReceived, "
	+" item.`quantity_returned` AS returnOrderItemQuantityReturned, "
	+" item.`created_by_email_id` AS returnOrderItemCreatedByEmailId, "
	+" item.`last_modified_by_email_id` AS returnOrderItemLastModifiedByEmailId, "
	+" item.`owner_email_id` AS returnOrderItemOwnerEmailId, "
	+" prd.id AS productId, "
	+" prd.manual_id AS productManualId, "
	+" item.`customer_quantity_received` AS returnOrderItemCustomerQuantityReceived, "
	+" item.`customer_quantity_returned` AS returnOrderItemCustomerQuantityReturned, "
	+" ro.id AS returnOrderId, "
	+" ro.manual_id AS returnOrderManualId "
	
	+" FROM `bt_product_return_order_item` item "
	+" JOIN `bt_product_return_order` ro ON ro.id=item.`return_order_id` "
	+" JOIN `bt_products` prd ON prd.id=item.`products_id` where ro.id=:returnOrderId and  (ro.destination_location_id in :locationIds or ro.source_location_id in :locationIds)",nativeQuery = true,countQuery = "SELECT count(*) as ct"
	
	+" FROM `bt_product_return_order_item` item "
	+" JOIN `bt_product_return_order` ro ON ro.id=item.`return_order_id` "
	+" JOIN `bt_products` prd ON prd.id=item.`products_id` where ro.id=:returnOrderId and  (ro.destination_location_id in :locationIds or ro.source_location_id in :locationIds)")
    Page<Map<String,Object>> getPageReturnOrderItemsByReturnOrderId(@Param("returnOrderId")Long returnOrderId,@Param("locationIds")List<Long> locationIds,Pageable page);

    @Query(value = "SELECT "
	+" item.`id` AS returnOrderItemId, "
	+" item.`created_date` AS returnOrderItemCreatedDate, "
	+" item.`last_modified_date` AS returnOrderItemModifiedDate, "
	+" item.`manual_id` AS returnOrderItemManualId, "
	+" item.`quantity_received` AS returnOrderItemQuantityReceived, "
	+" item.`quantity_returned` AS returnOrderItemQuantityReturned, "
	+" item.`created_by_email_id` AS returnOrderItemCreatedByEmailId, "
	+" item.`last_modified_by_email_id` AS returnOrderItemLastModifiedByEmailId, "
	+" item.`owner_email_id` AS returnOrderItemOwnerEmailId, "
	+" prd.id AS productId, "
	+" prd.manual_id AS productManualId, "
	+" prd.product_type as productType, "
	+" prd.product_description as productDescription, "
	+" item.`customer_quantity_received` AS returnOrderItemCustomerQuantityReceived, "
	+" item.`customer_quantity_returned` AS returnOrderItemCustomerQuantityReturned, "
	+" ro.id AS returnOrderId, "
	+" ro.manual_id AS returnOrderManualId "
	
	+" FROM `bt_product_return_order_item` item "
	+" JOIN `bt_product_return_order` ro ON ro.id=item.`return_order_id` "
	+" JOIN `bt_products` prd ON prd.id=item.`products_id` where ro.id=:returnOrderId",nativeQuery = true)
    List<Map<String,Object>> getListReturnOrderItemsByReturnOrderId(@Param("returnOrderId")Long returnOrderId);

    @Query(value = "SELECT "
	+" item.`id` AS returnOrderItemId, "
	+" item.`created_date` AS returnOrderItemCreatedDate, "
	+" item.`last_modified_date` AS returnOrderItemModifiedDate, "
	+" item.`manual_id` AS returnOrderItemManualId, "
	+" item.`quantity_received` AS returnOrderItemQuantityReceived, "
	+" item.`quantity_returned` AS returnOrderItemQuantityReturned, "
	+" item.`created_by_email_id` AS returnOrderItemCreatedByEmailId, "
	+" item.`last_modified_by_email_id` AS returnOrderItemLastModifiedByEmailId, "
	+" item.`owner_email_id` AS returnOrderItemOwnerEmailId, "
	+" prd.id AS productId, "
	+" prd.manual_id AS productManualId, "
	+" item.`customer_quantity_received` AS returnOrderItemCustomerQuantityReceived, "
	+" item.`customer_quantity_returned` AS returnOrderItemCustomerQuantityReturned, "
	+" ro.id AS returnOrderId, "
	+" ro.manual_id AS returnOrderManualId "
	
	+" FROM `bt_product_return_order_item` item "
	+" JOIN `bt_product_return_order` ro ON ro.id=item.`return_order_id` "
	+" JOIN `bt_products` prd ON prd.id=item.`products_id` where ro.id=:returnOrderId and  (ro.destination_location_id in :locationIds or ro.source_location_id in :locationIds)",nativeQuery = true)
    List<Map<String,Object>> getListReturnOrderItemsByReturnOrderId(@Param("returnOrderId")Long returnOrderId,@Param("locationIds")List<Long> locationIds);


	@Query(value="SELECT "
	+" item.`id` AS returnOrderItemId, "
	+" item.`created_date` AS returnOrderItemCreatedDate, "
	+" item.`last_modified_date` AS returnOrderItemModifiedDate, "
	+" item.`manual_id` AS returnOrderItemManualId, "
	+" item.`quantity_received` AS returnOrderItemQuantityReceived, "
	+" item.`quantity_returned` AS returnOrderItemQuantityReturned, "
	+" item.`created_by_email_id` AS returnOrderItemCreatedByEmailId, "
	+" item.`last_modified_by_email_id` AS returnOrderItemLastModifiedByEmailId, "
	+" item.`owner_email_id` AS returnOrderItemOwnerEmailId, "
	+" prd.id AS productId, "
	+" prd.manual_id AS productManualId, "
	+" item.`customer_quantity_received` AS returnOrderItemCustomerQuantityReceived, "
	+" item.`customer_quantity_returned` AS returnOrderItemCustomerQuantityReturned, "
	+" ro.id AS returnOrderId, "
	+" ro.manual_id AS returnOrderManualId, "
	+" ro.ship_to_name as returnOrderShipToName, "
	+" ro.shipping_mode as returnOrderShippingMode, "
	+" ro.tracking_number as returnOrderTrackingNumber, "
	+" dloc.location_id as returnOrderDestinationLocationId, "
	+" dloc.location_name as returnOrderDestinationLocationName, "
	+" sloc.location_id as returnOrderSourceLocationId, "
	+" sloc.location_name as returnOrderSourceLocationName "

	+" FROM `bt_product_return_order_item` item "
	+" JOIN `bt_product_return_order` ro ON ro.id=item.`return_order_id` "
	+" JOIN location dloc on dloc.location_id =ro.destination_location_id "
	+" JOIN location sloc on sloc.location_id =ro.source_location_id"
	+" JOIN `bt_products` prd ON prd.id=item.`products_id` where dloc.location_id=:locationId and ro.return_order_status !='Draft' and (item.quantity_returned>item.quantity_received or item.customer_quantity_returned>customer_quantity_received)",nativeQuery = true)
	public List<Map<String,Object>> getPendingReturnOrderItemToReceive(@Param("locationId")Long locationId);

	@Query(value="SELECT "
	+" item.`id` AS returnOrderItemId, "
	+" item.`created_date` AS returnOrderItemCreatedDate, "
	+" item.`last_modified_date` AS returnOrderItemModifiedDate, "
	+" item.`manual_id` AS returnOrderItemManualId, "
	+" item.`quantity_received` AS returnOrderItemQuantityReceived, "
	+" item.`quantity_returned` AS returnOrderItemQuantityReturned, "
	+" item.`created_by_email_id` AS returnOrderItemCreatedByEmailId, "
	+" item.`last_modified_by_email_id` AS returnOrderItemLastModifiedByEmailId, "
	+" item.`owner_email_id` AS returnOrderItemOwnerEmailId, "
	+" prd.id AS productId, "
	+" prd.manual_id AS productManualId, "
	+" item.`customer_quantity_received` AS returnOrderItemCustomerQuantityReceived, "
	+" item.`customer_quantity_returned` AS returnOrderItemCustomerQuantityReturned, "
	+" ro.id AS returnOrderId, "
	+" ro.manual_id AS returnOrderManualId, "
	+" ro.ship_to_name as returnOrderShipToName, "
	+" ro.shipping_mode as returnOrderShippingMode, "
	+" ro.tracking_number as returnOrderTrackingNumber, "
	+" dloc.location_id as returnOrderDestinationLocationId, "
	+" dloc.location_name as returnOrderDestinationLocationName, "
	+" sloc.location_id as returnOrderSourceLocationId, "
	+" sloc.location_name as returnOrderSourceLocationName "

	+" FROM `bt_product_return_order_item` item "
	+" JOIN `bt_product_return_order` ro ON ro.id=item.`return_order_id` "
	+" JOIN location dloc on dloc.location_id =ro.destination_location_id "
	+" JOIN location sloc on sloc.location_id =ro.source_location_id"
	+" JOIN `bt_products` prd ON prd.id=item.`products_id` where dloc.location_id=:locationId and ro.id=:returnOrderId and ro.return_order_status !='Draft' and (item.quantity_returned>item.quantity_received or item.customer_quantity_returned>customer_quantity_received)",nativeQuery = true)
	public List<Map<String,Object>> getPendingReturnOrderItemToReceive(@Param("locationId")Long locationId,@Param("returnOrderId")Long returnOrderId);

	



}
