package com.pratikshat.exicom.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.BtProductRequest;
import com.pratikshat.exicom.entity.BtProductRequestItem;
import com.pratikshat.exicom.entity.BtProducts;
import com.pratikshat.exicom.repository.custom.BtProductRequestItemRepositoryCustome;

@Repository
public interface BtProductRequestItemRepository extends JpaRepository<BtProductRequestItem, Long>,BtProductRequestItemRepositoryCustome {

	BtProductRequestItem findByBtProductRequestAndBtProduct(BtProductRequest btProductRequest,BtProducts btProducts);
	List<BtProductRequestItem> findByBtProductRequest(BtProductRequest btProductRequest);

	@Query(value="SELECT \r\n" + 
			"item.id AS productRequestItemId,\r\n" + 
			"item.`created_date` AS productRequestItemCreatedDate,\r\n" + 
			"item.`last_modified_date` AS productRequestItemLastModifiedDate,\r\n" + 
			"item.`item_status` AS productRequestItemStatus,\r\n" + 
			"item.`description` AS productRequestItemDescription,\r\n" + 
			"item.`disabled_item` AS productRequestItemIsDisabled,\r\n" + 
			"item.`manual_id` AS productRequestItemManualId,\r\n" + 
			"item.`quantity` AS productRequestItemRequiredQuantity,\r\n" + 
			"item.`quantity_issued` AS productRequestItemIssuedQuantity,\r\n" + 
			"item.`quantity_received` AS productRequestItemReceivedQuantity,\r\n" + 
			"item.`synced` AS productRequestItemIsSynced,\r\n" + 
			"item.`created_by_email_id` AS productRequestItemCreatedByEmailId,\r\n" + 
			"item.`last_modified_by_email_id` AS productRequestItemLastModifiedByEmailId,\r\n" + 
			"item.`bt_product_id` AS productRequestItemProductId,\r\n" + 
			"item.`bt_product_request_id` AS productRequestId,\r\n" + 
			"prd.`manual_id` AS productRequestItemProductPartcode,\r\n" + 
			"prd.`product_price` AS productRequestItemProductPrice,\r\n" + 
			"prd.`product_type` AS productRequestItemProductType,\r\n" + 
			"prd.`product_description` AS productRequestItemProductDescription\r\n" + 
			"\r\n" + 
			"FROM `bt_product_request_item` item JOIN `bt_products` prd ON prd.id=item.bt_product_id where item.id=:id",nativeQuery=true)
	Map<String,Object> getById(@Param("id")Long id);
	
	@Query(value="SELECT \r\n" + 
			"item.id AS productRequestItemId,\r\n" + 
			"item.`created_date` AS productRequestItemCreatedDate,\r\n" + 
			"item.`last_modified_date` AS productRequestItemLastModifiedDate,\r\n" + 
			"item.`item_status` AS productRequestItemStatus,\r\n" + 
			"item.`description` AS productRequestItemDescription,\r\n" + 
			"item.`disabled_item` AS productRequestItemIsDisabled,\r\n" + 
			"item.`manual_id` AS productRequestItemManualId,\r\n" + 
			"item.`quantity` AS productRequestItemRequiredQuantity,\r\n" + 
			"item.`quantity_issued` AS productRequestItemIssuedQuantity,\r\n" + 
			"item.`quantity_received` AS productRequestItemReceivedQuantity,\r\n" + 
			"item.`synced` AS productRequestItemIsSynced,\r\n" + 
			"item.`created_by_email_id` AS productRequestItemCreatedByEmailId,\r\n" + 
			"item.`last_modified_by_email_id` AS productRequestItemLastModifiedByEmailId,\r\n" + 
			"item.`bt_product_id` AS productRequestItemProductId,\r\n" + 
			"item.`bt_product_request_id` AS productRequestId,\r\n" + 
			"prd.`manual_id` AS productRequestItemProductPartcode,\r\n" + 
			"prd.`product_price` AS productRequestItemProductPrice,\r\n" + 
			"prd.`product_type` AS productRequestItemProductType,\r\n" + 
			"prd.`product_description` AS productRequestItemProductDescription\r\n" + 
			"\r\n" + 
			"FROM `bt_product_request_item` item JOIN `bt_products` prd ON prd.id=item.bt_product_id JOIN `bt_product_request` req ON req.id=item.`bt_product_request_id` where item.id=:id and req.destination_location_location_id in :locationIds or req.source_location_location_id in :locationIds",nativeQuery=true)
	Map<String,Object> getById(@Param("id")Long id,@Param("locationIds")List<Long>locationIds);
	
	@Query(value="SELECT \r\n" + 
			"item.id AS productRequestItemId,\r\n" + 
			"item.`created_date` AS productRequestItemCreatedDate,\r\n" + 
			"item.`last_modified_date` AS productRequestItemLastModifiedDate,\r\n" + 
			"item.`item_status` AS productRequestItemStatus,\r\n" + 
			"item.`description` AS productRequestItemDescription,\r\n" + 
			"item.`disabled_item` AS productRequestItemIsDisabled,\r\n" + 
			"item.`manual_id` AS productRequestItemManualId,\r\n" + 
			"item.`quantity` AS productRequestItemRequiredQuantity,\r\n" + 
			"item.`quantity_issued` AS productRequestItemIssuedQuantity,\r\n" + 
			"item.`quantity_received` AS productRequestItemReceivedQuantity,\r\n" + 
			"item.`synced` AS productRequestItemIsSynced,\r\n" + 
			"item.`created_by_email_id` AS productRequestItemCreatedByEmailId,\r\n" + 
			"item.`last_modified_by_email_id` AS productRequestItemLastModifiedByEmailId,\r\n" + 
			"item.`bt_product_id` AS productRequestItemProductId,\r\n" + 
			"item.`bt_product_request_id` AS productRequestId,\r\n" + 
			"prd.`manual_id` AS productRequestItemProductPartcode,\r\n" + 
			"prd.`product_price` AS productRequestItemProductPrice,\r\n" + 
			"prd.`product_type` AS productRequestItemProductType,\r\n" + 
			"prd.`product_description` AS productRequestItemProductDescription\r\n" + 
			"\r\n" + 
			"FROM `bt_product_request_item` item JOIN `bt_products` prd ON prd.id=item.bt_product_id where item.bt_product_request_id=:id",nativeQuery=true,countQuery="SELECT count(*) as ct" + 
					"FROM `bt_product_request_item` item JOIN `bt_products` prd ON prd.id=item.bt_product_id where item.bt_product_request_id=:id")
	Page<Map<String,Object>> getPageBy(@Param("id")Long productRequestId,Pageable page);
	
	@Query(value="SELECT \r\n" + 
			"item.id AS productRequestItemId,\r\n" + 
			"item.`created_date` AS productRequestItemCreatedDate,\r\n" + 
			"item.`last_modified_date` AS productRequestItemLastModifiedDate,\r\n" + 
			"item.`item_status` AS productRequestItemStatus,\r\n" + 
			"item.`description` AS productRequestItemDescription,\r\n" + 
			"item.`disabled_item` AS productRequestItemIsDisabled,\r\n" + 
			"item.`manual_id` AS productRequestItemManualId,\r\n" + 
			"item.`quantity` AS productRequestItemRequiredQuantity,\r\n" + 
			"item.`quantity_issued` AS productRequestItemIssuedQuantity,\r\n" + 
			"item.`quantity_received` AS productRequestItemReceivedQuantity,\r\n" + 
			"item.`synced` AS productRequestItemIsSynced,\r\n" + 
			"item.`created_by_email_id` AS productRequestItemCreatedByEmailId,\r\n" + 
			"item.`last_modified_by_email_id` AS productRequestItemLastModifiedByEmailId,\r\n" + 
			"item.`bt_product_id` AS productRequestItemProductId,\r\n" + 
			"item.`bt_product_request_id` AS productRequestId,\r\n" + 
			"prd.`manual_id` AS productRequestItemProductPartcode,\r\n" + 
			"prd.`product_price` AS productRequestItemProductPrice,\r\n" + 
			"prd.`product_type` AS productRequestItemProductType,\r\n" + 
			"prd.`product_description` AS productRequestItemProductDescription\r\n" + 
			"\r\n" + 
			"FROM `bt_product_request_item` item JOIN `bt_products` prd ON prd.id=item.bt_product_id  JOIN `bt_product_request` req ON req.id=item.`bt_product_request_id` where item.bt_product_request_id=:id and (req.source_location_location_id in :locationIds or req.destination_location_location_id in :locationIds)",nativeQuery=true,countQuery="SELECT count(*) as ct" + 
					"FROM `bt_product_request_item` item JOIN `bt_products` prd ON prd.id=item.bt_product_id JOIN `bt_product_request` req ON req.id=item.`bt_product_request_id` where item.bt_product_request_id=:id and (req.source_location_location_id in :locationIds or req.destination_location_location_id in :locationIds")
	Page<Map<String,Object>> getPageBy(@Param("id")Long productRequestId,@Param("locationIds")List<Long>locationIds,Pageable page);
	
	@Query(value="SELECT \r\n" + 
			"item.id AS productRequestItemId,\r\n" + 
			"item.`created_date` AS productRequestItemCreatedDate,\r\n" + 
			"item.`last_modified_date` AS productRequestItemLastModifiedDate,\r\n" + 
			"item.`item_status` AS productRequestItemStatus,\r\n" + 
			"item.`description` AS productRequestItemDescription,\r\n" + 
			"item.`disabled_item` AS productRequestItemIsDisabled,\r\n" + 
			"item.`manual_id` AS productRequestItemManualId,\r\n" + 
			"item.`quantity` AS productRequestItemRequiredQuantity,\r\n" + 
			"item.`quantity_issued` AS productRequestItemIssuedQuantity,\r\n" + 
			"item.`quantity_received` AS productRequestItemReceivedQuantity,\r\n" + 
			"item.`synced` AS productRequestItemIsSynced,\r\n" + 
			"item.`created_by_email_id` AS productRequestItemCreatedByEmailId,\r\n" + 
			"item.`last_modified_by_email_id` AS productRequestItemLastModifiedByEmailId,\r\n" + 
			"item.`bt_product_id` AS productRequestItemProductId,\r\n" + 
			"item.`bt_product_request_id` AS productRequestId,\r\n" + 
			"prd.`manual_id` AS productRequestItemProductPartcode,\r\n" + 
			"prd.`product_price` AS productRequestItemProductPrice,\r\n" + 
			"prd.`product_type` AS productRequestItemProductType,\r\n" + 
			"prd.`product_description` AS productRequestItemProductDescription\r\n" + 
			"\r\n" + 
			"FROM `bt_product_request_item` item JOIN `bt_products` prd ON prd.id=item.id where item.bt_product_request_id=:id  and (item.description like :searchkey or item.manual_id like :searchkey or prd.manual_id like :searchkey or prd.product_description like :searchkey or prd.product_description like :searchkey)",nativeQuery=true)
	public List<Map<String,Object>> getSearchList(@Param("id")Long productRequestId,@Param("searchkey")String searchKey);

	@Query(value="SELECT \r\n" + 
			"item.id AS productRequestItemId,\r\n" + 
			"item.`created_date` AS productRequestItemCreatedDate,\r\n" + 
			"item.`last_modified_date` AS productRequestItemLastModifiedDate,\r\n" + 
			"item.`item_status` AS productRequestItemStatus,\r\n" + 
			"item.`description` AS productRequestItemDescription,\r\n" + 
			"item.`disabled_item` AS productRequestItemIsDisabled,\r\n" + 
			"item.`manual_id` AS productRequestItemManualId,\r\n" + 
			"item.`quantity` AS productRequestItemRequiredQuantity,\r\n" + 
			"item.`quantity_issued` AS productRequestItemIssuedQuantity,\r\n" + 
			"item.`quantity_received` AS productRequestItemReceivedQuantity,\r\n" + 
			"item.`synced` AS productRequestItemIsSynced,\r\n" + 
			"item.`created_by_email_id` AS productRequestItemCreatedByEmailId,\r\n" + 
			"item.`last_modified_by_email_id` AS productRequestItemLastModifiedByEmailId,\r\n" + 
			"item.`bt_product_id` AS productRequestItemProductId,\r\n" + 
			"item.`bt_product_request_id` AS productRequestId,\r\n" + 
			"prd.`manual_id` AS productRequestItemProductPartcode,\r\n" + 
			"prd.`product_price` AS productRequestItemProductPrice,\r\n" + 
			"prd.`product_type` AS productRequestItemProductType,\r\n" + 
			"prd.`product_description` AS productRequestItemProductDescription\r\n" + 
			"\r\n" + 
			"FROM `bt_product_request_item` item JOIN `bt_products` prd ON prd.id=item.bt_product_id where item.bt_product_request_id=:id",nativeQuery=true)
	List<Map<String,Object>> getListBy(@Param("id")Long productRequestId);
	
	@Query(value="SELECT \r\n" + 
			"item.id AS productRequestItemId,\r\n" + 
			"item.`created_date` AS productRequestItemCreatedDate,\r\n" + 
			"item.`last_modified_date` AS productRequestItemLastModifiedDate,\r\n" + 
			"item.`item_status` AS productRequestItemStatus,\r\n" + 
			"item.`description` AS productRequestItemDescription,\r\n" + 
			"item.`disabled_item` AS productRequestItemIsDisabled,\r\n" + 
			"item.`manual_id` AS productRequestItemManualId,\r\n" + 
			"item.`quantity` AS productRequestItemRequiredQuantity,\r\n" + 
			"item.`quantity_issued` AS productRequestItemIssuedQuantity,\r\n" + 
			"item.`quantity_received` AS productRequestItemReceivedQuantity,\r\n" + 
			"item.`synced` AS productRequestItemIsSynced,\r\n" + 
			"item.`created_by_email_id` AS productRequestItemCreatedByEmailId,\r\n" + 
			"item.`last_modified_by_email_id` AS productRequestItemLastModifiedByEmailId,\r\n" + 
			"item.`bt_product_id` AS productRequestItemProductId,\r\n" + 
			"item.`bt_product_request_id` AS productRequestId,\r\n" + 
			"prd.`manual_id` AS productRequestItemProductPartcode,\r\n" + 
			"prd.`product_price` AS productRequestItemProductPrice,\r\n" + 
			"prd.`product_type` AS productRequestItemProductType,\r\n" + 
			"prd.`product_description` AS productRequestItemProductDescription\r\n" + 
			"\r\n" + 
			"FROM `bt_product_request_item` item JOIN `bt_products` prd ON prd.id=item.bt_product_id  JOIN `bt_product_request` req ON req.id=item.`bt_product_request_id` where item.bt_product_request_id=:id and (req.source_location_location_id in :locationIds or req.destination_location_location_id in :locationIds)",nativeQuery=true)
	List<Map<String,Object>> getListBy(@Param("id")Long productRequestId,@Param("locationIds")List<Long>locationIds);
	
	
	

}
