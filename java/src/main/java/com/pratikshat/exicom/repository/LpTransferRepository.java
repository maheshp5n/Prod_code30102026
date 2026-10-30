package com.pratikshat.exicom.repository;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LpTransferRepository extends JpaRepository<com.pratikshat.exicom.entity.LPTrasfer, Long>, LpTransferRepositoryCustome {
	@Query("select new com.pratikshat.exicom.model.response.LocationProductTransferItemResponse(item) from LPTransferItem item join item.product prd join item.lpTranfer trn join trn.createdBy crt join trn.locationProductRequest preq join preq.sourceLocation sl join preq.destinationLocation dl join item.lpRequestLineItem ritem where item.lpTransferItemId=:transferItemId")
	com.pratikshat.exicom.model.response.LocationProductTransferItemResponse getTransferItemByTransferItemId(@Param("transferItemId")long transferItemId);
	
	@Query("select new com.pratikshat.exicom.model.response.LocationProductTransferItemResponse(item) from LPTransferItem item join item.product prd join item.lpTranfer trn join trn.owner onr join trn.createdBy crt join trn.modifiedBy mdf join trn.locationProductRequest preq join preq.sourceLocation sl join preq.destinationLocation dl join item.lpRequestLineItem ritem")
	Page<com.pratikshat.exicom.model.response.LocationProductTransferItemResponse> getTransferItems(Pageable page);
	
//	@Query("select new com.pratikshat.exicom.model.response.LocationProductTransferItemResponse(item) from LPTransferItem item join item.product prd join item.lpTranfer trn join trn.owner onr join trn.createdBy crt join trn.modifiedBy mdf join trn.locationProductRequest preq join preq.sourceLocation sl join preq.destinationLocation dl join item.lpRequestLineItem ritem where sl.owner in :userlist or dl.owner in :userlist")
//	Page<com.pratikshat.exicom.model.response.LocationProductTransferItemResponse> getTransferItems(@Param("userlist")java.util.List<com.pratikshat.exicom.entity.EmployeeDetails> userList ,Pageable page);
	
	@Query("select new com.pratikshat.exicom.model.response.LocationProductTransferItemResponse(item) from LPTransferItem item join item.product prd join item.lpTranfer trn join trn.owner onr join trn.createdBy crt join trn.modifiedBy mdf join trn.locationProductRequest preq join preq.sourceLocation sl join preq.destinationLocation dl join item.lpRequestLineItem ritem where sl.locationId in :ids or dl.locationId in :ids")
	Page<com.pratikshat.exicom.model.response.LocationProductTransferItemResponse> getTransferItems(@Param("ids")java.util.List<Long> ids ,Pageable page);
	
	@Query("select new com.pratikshat.exicom.model.response.LocationProductTransferItemResponse(item) from LPTransferItem item join item.product prd join item.lpTranfer trn join trn.owner onr join trn.createdBy crt join trn.modifiedBy mdf join trn.locationProductRequest preq join preq.sourceLocation sl join sl.owner slo join preq.destinationLocation dl join item.lpRequestLineItem ritem join ServiceRecource sr on sr.location=sl where sr.user=:owner")
	Page<com.pratikshat.exicom.model.response.LocationProductTransferItemResponse> getMyTransferItems(@Param("owner")com.pratikshat.exicom.entity.EmployeeDetails user,Pageable page);
	
	@Query("select new com.pratikshat.exicom.model.response.LocationProductTransferItemResponse(item) from LPTransferItem item join item.product prd join item.lpTranfer trn join trn.owner onr join trn.createdBy crt join trn.modifiedBy mdf join trn.locationProductRequest preq join preq.sourceLocation sl join preq.destinationLocation dl join dl.owner dlo join item.lpRequestLineItem ritem join ServiceRecource sr on sr.location=dl where sr.user=:owner")
	Page<com.pratikshat.exicom.model.response.LocationProductTransferItemResponse> getForMeTransferItems(@Param("owner")com.pratikshat.exicom.entity.EmployeeDetails user,Pageable page);
	
	@Query("select new com.pratikshat.exicom.model.response.LocationProductTransferLineItemResponse(item.lpTransferItemId,lineItem.lpTransferLineitemId,lineItem.lpTransferLineitemIdManualId,prd.productPartCode,prd.pdoductDescription,prd.productType,lineItem.productSerialNumber,lineItem.received) from LPTransferLineItem lineItem join lineItem.transferItem item join item.product prd where item.lpTransferItemId=:itemTransferId")
	java.util.List<com.pratikshat.exicom.model.response.LocationProductTransferLineItemResponse> getLineItemsByTransferItemId(@Param("itemTransferId")long itemTransferId);

	//  shagufta
	@Query("select new com.pratikshat.exicom.model.response.LocationProductTransferLineItemResponse(item.lpTransferItemId,lineItem.lpTransferLineitemId,lineItem.lpTransferLineitemIdManualId,prd.productPartCode,prd.pdoductDescription,prd.productType,lineItem.productSerialNumber,lineItem.received, cby,lineItem.createdDate, mby, lineItem.modifiedDate) from LPTransferLineItem lineItem join lineItem.transferItem item join item.product prd left join lineItem.createdBy cby left join lineItem.modifiedBy mby where lineItem.lpTransferLineitemId=:transferLineItemId")
	com.pratikshat.exicom.model.response.LocationProductTransferLineItemResponse getProductTransferItemsById(@Param("transferLineItemId")long transferLineItemId);
	
	
	@Query("select trf from LPTrasfer trf join trf.locationProductRequest pr join pr.sourceLocation srl join pr.destinationLocation dsl where trf.locationProductTransferId=:transferId")
	com.pratikshat.exicom.entity.LPTrasfer getProductTransferById(@Param("transferId")long transferId);
	
	@Query(value="SELECT \r\n" + 
			"trf.`location_product_transfer_id` AS `id`,\r\n" + 
			"trf.`location_product_transfer_manual_id` AS `manualId`,\r\n" + 
			"sl.`location_id` AS `sourceLocationId`,\r\n" + 
			"sl.`location_name` AS `sourceLocationName`,\r\n" + 
			"dl.`location_id` AS `destinationLocationId`,\r\n" + 
			"dl.`location_name` AS `destinationLocationName`,\r\n" + 
			"trf.`actual_delivery_date` AS `deliveryDate`,\r\n" + 
			"trf.`shipping_mode` AS `shippingMode`,\r\n" + 
			"trf.`shipping_to_name` AS `shippingToName`,\r\n" + 
			"trf.`tracking_number` AS `trackingNumber`,\r\n" + 
			"trf.`received_date` AS `receivedDate`,\r\n" + 
			"trf.`location_product_request_location_product_request_id` AS `productRequestId`,\r\n" + 
			"pr.`location_product_request_manual_id` AS `productRequestManualId`,\r\n" + 
			"qty.transferQty,\r\n" + 
			"qty.receivedQty,\r\n" + 
			"(CASE\r\n" + 
			"	WHEN qty.transferQty!=0 AND qty.receivedQty!=0 AND qty.transferQty=qty.receivedQty THEN 'Received'\r\n" + 
			"	WHEN qty.transferQty!=0 AND qty.receivedQty!=0 AND qty.transferQty>qty.receivedQty THEN 'Partially Received'\r\n" + 
			"	WHEN qty.receivedQty=0 THEN 'Pendig to Received'\r\n" + 
			"END) AS `status`,\r\n" + 
			"trf.`created_date` AS `createdDate`,\r\n" + 
			"trf.`modified_date` AS `modifiedDate`,\r\n" + 
			"trf.`created_by_email_id` AS `createdByEmailId`,\r\n" + 
			"CONCAT(CONCAT(cby.`first_name`,' '),IFNULL(CONCAT(cby.`middle_name`,' '),''),cby.`last_name`) AS `createdByName`,\r\n" + 
			"trf.`modified_by_email_id` AS `modifiedByEmailId`,\r\n" + 
			"CONCAT(CONCAT(mby.`first_name`,' '),IFNULL(CONCAT(mby.`middle_name`,' '),''),mby.`last_name`) AS `modifiedByName`,\r\n" + 
			"trf.`owner_email_id` AS `ownerEmailId`,\r\n" + 
			"CONCAT(CONCAT(onr.`first_name`,' '),IFNULL(CONCAT(onr.`middle_name`,' '),''),onr.`last_name`) AS `ownerName`,\r\n" + 
			"trf.`deliv`,\r\n" + 
			"trf.`invoice`,\r\n" + 
			"trf.`mblnr_g`,\r\n" + 
			"trf.`mjahr_g`,\r\n" + 
			"trf.`remark_d`,\r\n" + 
			"trf.`remark_g`,\r\n" + 
			"trf.`remark_i`\r\n" + 
			"FROM `lptrasfer` trf\r\n" + 
			"JOIN `lproduct_request` pr ON pr.`location_product_request_id`=trf.`location_product_request_location_product_request_id`\r\n" + 
			"JOIN `employee_details` cby ON cby.`email_id`=trf.`created_by_email_id`\r\n" + 
			"JOIN `employee_details` mby ON mby.`email_id`=trf.`modified_by_email_id`\r\n" + 
			"JOIN `employee_details` onr ON onr.`email_id`=trf.`owner_email_id`\r\n" + 
			"JOIN `location` sl ON sl.`location_id`=pr.`source_location_location_id`\r\n" + 
			"JOIN `location` dl ON dl.`location_id`=pr.`destination_location_location_id`\r\n" + 
			"JOIN `service_recource` sr ON sr.`location_location_id`=sl.`location_id`\r\n" + 
			"LEFT JOIN \r\n" + 
			"(SELECT\r\n" + 
			"	SUM(item.`transfer_quantity`) AS `transferQty`,\r\n" + 
			"	SUM(item.`received_quantity`) AS `receivedQty`,\r\n" + 
			"	item.`lp_tranfer_location_product_transfer_id`\r\n" + 
			"	FROM `lptransfer_item` item\r\n" + 
			"	GROUP BY  item.`lp_tranfer_location_product_transfer_id`\r\n" + 
			") AS `qty`\r\n" + 
			"ON qty.`lp_tranfer_location_product_transfer_id`=trf.`location_product_transfer_id`\r\n" + 
			"WHERE pr.`location_product_request_status`!='Archived'\r\n" + 
			"AND sr.`user_email_id`=:owner",nativeQuery=true,countQuery="SELECT \r\n" +
					"COUNT(*) AS countQuery\r\n" + 
					"FROM `lptrasfer` trf\r\n" + 
					"JOIN `lproduct_request` pr ON pr.`location_product_request_id`=trf.`location_product_request_location_product_request_id`\r\n" + 
					"JOIN `employee_details` cby ON cby.`email_id`=trf.`created_by_email_id`\r\n" + 
					"JOIN `employee_details` mby ON mby.`email_id`=trf.`modified_by_email_id`\r\n" + 
					"JOIN `employee_details` onr ON onr.`email_id`=trf.`owner_email_id`\r\n" + 
					"JOIN `location` sl ON sl.`location_id`=pr.`source_location_location_id`\r\n" + 
					"JOIN `location` dl ON dl.`location_id`=pr.`destination_location_location_id`\r\n" + 
					"JOIN `service_recource` sr ON sr.`location_location_id`=sl.`location_id`\r\n" + 
					"LEFT JOIN \r\n" + 
					"(SELECT\r\n" + 
					"	SUM(item.`transfer_quantity`) AS `transferQty`,\r\n" + 
					"	SUM(item.`received_quantity`) AS `receivedQty`,\r\n" + 
					"	item.`lp_tranfer_location_product_transfer_id`\r\n" + 
					"	FROM `lptransfer_item` item\r\n" + 
					"	GROUP BY  item.`lp_tranfer_location_product_transfer_id`\r\n" + 
					") AS `qty`\r\n" + 
					"ON qty.`lp_tranfer_location_product_transfer_id`=trf.`location_product_transfer_id`\r\n" + 
					"WHERE pr.`location_product_request_status`!='Archived'\r\n" + 
					"AND sr.`user_email_id`=:owner")
	Page<Map<String,Object>> getMyProductTransfer(@Param("owner")com.pratikshat.exicom.entity.EmployeeDetails user,Pageable page);
	
	
	
	
	@Query(value="SELECT \r\n" + 
			"trf.`location_product_transfer_id` AS `id`,\r\n" + 
			"trf.`location_product_transfer_manual_id` AS `manualId`,\r\n" + 
			"sl.`location_id` AS `sourceLocationId`,\r\n" + 
			"sl.`location_name` AS `sourceLocationName`,\r\n" + 
			"dl.`location_id` AS `destinationLocationId`,\r\n" + 
			"dl.`location_name` AS `destinationLocationName`,\r\n" + 
			"trf.`actual_delivery_date` AS `deliveryDate`,\r\n" + 
			"trf.`shipping_mode` AS `shippingMode`,\r\n" + 
			"trf.`shipping_to_name` AS `shippingToName`,\r\n" + 
			"trf.`tracking_number` AS `trackingNumber`,\r\n" + 
			"trf.`received_date` AS `receivedDate`,\r\n" + 
			"trf.`location_product_request_location_product_request_id` AS `productRequestId`,\r\n" + 
			"pr.`location_product_request_manual_id` AS `productRequestManualId`,\r\n" + 
			"qty.transferQty,\r\n" + 
			"qty.receivedQty,\r\n" + 
			"(CASE\r\n" + 
			"	WHEN qty.transferQty!=0 AND qty.receivedQty!=0 AND qty.transferQty=qty.receivedQty THEN 'Received'\r\n" + 
			"	WHEN qty.transferQty!=0 AND qty.receivedQty!=0 AND qty.transferQty>qty.receivedQty THEN 'Partially Received'\r\n" + 
			"	WHEN qty.receivedQty=0 THEN 'Pendig to Received'\r\n" + 
			"END) AS `status`,\r\n" + 
			"trf.`created_date` AS `createdDate`,\r\n" + 
			"trf.`modified_date` AS `modifiedDate`,\r\n" + 
			"trf.`created_by_email_id` AS `createdByEmailId`,\r\n" + 
			"CONCAT(CONCAT(cby.`first_name`,' '),IFNULL(CONCAT(cby.`middle_name`,' '),''),cby.`last_name`) AS `createdByName`,\r\n" + 
			"trf.`modified_by_email_id` AS `modifiedByEmailId`,\r\n" + 
			"CONCAT(CONCAT(mby.`first_name`,' '),IFNULL(CONCAT(mby.`middle_name`,' '),''),mby.`last_name`) AS `modifiedByName`,\r\n" + 
			"trf.`owner_email_id` AS `ownerEmailId`,\r\n" + 
			"CONCAT(CONCAT(onr.`first_name`,' '),IFNULL(CONCAT(onr.`middle_name`,' '),''),onr.`last_name`) AS `ownerName`,\r\n" + 
			"trf.`deliv`,\r\n" + 
			"trf.`invoice`,\r\n" + 
			"trf.`mblnr_g`,\r\n" + 
			"trf.`mjahr_g`,\r\n" + 
			"trf.`remark_d`,\r\n" + 
			"trf.`remark_g`,\r\n" + 
			"trf.`remark_i`\r\n" + 
			"FROM `lptrasfer` trf\r\n" + 
			"JOIN `lproduct_request` pr ON pr.`location_product_request_id`=trf.`location_product_request_location_product_request_id`\r\n" + 
			"JOIN `employee_details` cby ON cby.`email_id`=trf.`created_by_email_id`\r\n" + 
			"JOIN `employee_details` mby ON mby.`email_id`=trf.`modified_by_email_id`\r\n" + 
			"JOIN `employee_details` onr ON onr.`email_id`=trf.`owner_email_id`\r\n" + 
			"JOIN `location` sl ON sl.`location_id`=pr.`source_location_location_id`\r\n" + 
			"JOIN `location` dl ON dl.`location_id`=pr.`destination_location_location_id`\r\n" + 
			"JOIN `service_recource` sr ON sr.`location_location_id`=dl.`location_id`\r\n" + 
			"LEFT JOIN \r\n" + 
			"(SELECT\r\n" + 
			"	SUM(item.`transfer_quantity`) AS `transferQty`,\r\n" + 
			"	SUM(item.`received_quantity`) AS `receivedQty`,\r\n" + 
			"	item.`lp_tranfer_location_product_transfer_id`\r\n" + 
			"	FROM `lptransfer_item` item\r\n" + 
			"	GROUP BY  item.`lp_tranfer_location_product_transfer_id`\r\n" + 
			") AS `qty`\r\n" + 
			"ON qty.`lp_tranfer_location_product_transfer_id`=trf.`location_product_transfer_id`\r\n" + 
			"WHERE pr.`location_product_request_status`!='Archived'\r\n" + 
			"AND sr.`user_email_id`=:owner", nativeQuery=true, countQuery="SELECT \r\n" +
					"COUNT(*) AS countQuery\r\n" +
					"FROM `lptrasfer` trf\r\n" + 
					"JOIN `lproduct_request` pr ON pr.`location_product_request_id`=trf.`location_product_request_location_product_request_id`\r\n" + 
					"JOIN `employee_details` cby ON cby.`email_id`=trf.`created_by_email_id`\r\n" + 
					"JOIN `employee_details` mby ON mby.`email_id`=trf.`modified_by_email_id`\r\n" + 
					"JOIN `employee_details` onr ON onr.`email_id`=trf.`owner_email_id`\r\n" + 
					"JOIN `location` sl ON sl.`location_id`=pr.`source_location_location_id`\r\n" + 
					"JOIN `location` dl ON dl.`location_id`=pr.`destination_location_location_id`\r\n" + 
					"JOIN `service_recource` sr ON sr.`location_location_id`=dl.`location_id`\r\n" + 
					"LEFT JOIN \r\n" + 
					"(SELECT\r\n" + 
					"	SUM(item.`transfer_quantity`) AS `transferQty`,\r\n" + 
					"	SUM(item.`received_quantity`) AS `receivedQty`,\r\n" + 
					"	item.`lp_tranfer_location_product_transfer_id`\r\n" + 
					"	FROM `lptransfer_item` item\r\n" + 
					"	GROUP BY  item.`lp_tranfer_location_product_transfer_id`\r\n" + 
					") AS `qty`\r\n" + 
					"ON qty.`lp_tranfer_location_product_transfer_id`=trf.`location_product_transfer_id`\r\n" + 
					"WHERE pr.`location_product_request_status`!='Archived'\r\n" + 
					"AND sr.`user_email_id`=:owner")
	Page<Map<String,Object>> getForMeProductTransfer(@Param("owner")com.pratikshat.exicom.entity.EmployeeDetails user,Pageable page);
	
	
	@Query(value="SELECT \r\n" + 
			"trf.`location_product_transfer_id` AS `id`,\r\n" + 
			"trf.`location_product_transfer_manual_id` AS `manualId`,\r\n" + 
			"sl.`location_id` AS `sourceLocationId`,\r\n" + 
			"sl.`location_name` AS `sourceLocationName`,\r\n" + 
			"dl.`location_id` AS `destinationLocationId`,\r\n" + 
			"dl.`location_name` AS `destinationLocationName`,\r\n" + 
			"trf.`actual_delivery_date` AS `deliveryDate`,\r\n" + 
			"trf.`shipping_mode` AS `shippingMode`,\r\n" + 
			"trf.`shipping_to_name` AS `shippingToName`,\r\n" + 
			"trf.`tracking_number` AS `trackingNumber`,\r\n" + 
			"trf.`received_date` AS `receivedDate`,\r\n" + 
			"trf.`location_product_request_location_product_request_id` AS `productRequestId`,\r\n" + 
			"pr.`location_product_request_manual_id` AS `productRequestManualId`,\r\n" + 
			"qty.transferQty,\r\n" + 
			"qty.receivedQty,\r\n" + 
			"(CASE\r\n" + 
			"	WHEN qty.transferQty!=0 AND qty.receivedQty!=0 AND qty.transferQty=qty.receivedQty THEN 'Received'\r\n" + 
			"	WHEN qty.transferQty!=0 AND qty.receivedQty!=0 AND qty.transferQty>qty.receivedQty THEN 'Partially Received'\r\n" + 
			"	WHEN qty.receivedQty=0 THEN 'Pendig to Received'\r\n" + 
			"END) AS `status`,\r\n" + 
			"trf.`created_date` AS `createdDate`,\r\n" + 
			"trf.`modified_date` AS `modifiedDate`,\r\n" + 
			"trf.`created_by_email_id` AS `createdByEmailId`,\r\n" + 
			"CONCAT(CONCAT(cby.`first_name`,' '),IFNULL(CONCAT(cby.`middle_name`,' '),''),cby.`last_name`) AS `createdByName`,\r\n" + 
			"trf.`modified_by_email_id` AS `modifiedByEmailId`,\r\n" + 
			"CONCAT(CONCAT(mby.`first_name`,' '),IFNULL(CONCAT(mby.`middle_name`,' '),''),mby.`last_name`) AS `modifiedByName`,\r\n" + 
			"trf.`owner_email_id` AS `ownerEmailId`,\r\n" + 
			"CONCAT(CONCAT(onr.`first_name`,' '),IFNULL(CONCAT(onr.`middle_name`,' '),''),onr.`last_name`) AS `ownerName`,\r\n" + 
			"trf.`deliv`,\r\n" + 
			"trf.`invoice`,\r\n" + 
			"trf.`mblnr_g`,\r\n" + 
			"trf.`mjahr_g`,\r\n" + 
			"trf.`remark_d`,\r\n" + 
			"trf.`remark_g`,\r\n" + 
			"trf.`remark_i`\r\n" + 
			"FROM `lptrasfer` trf\r\n" + 
			"JOIN `lproduct_request` pr ON pr.`location_product_request_id`=trf.`location_product_request_location_product_request_id`\r\n" + 
			"JOIN `employee_details` cby ON cby.`email_id`=trf.`created_by_email_id`\r\n" + 
			"JOIN `employee_details` mby ON mby.`email_id`=trf.`modified_by_email_id`\r\n" + 
			"JOIN `employee_details` onr ON onr.`email_id`=trf.`owner_email_id`\r\n" + 
			"JOIN `location` sl ON sl.`location_id`=pr.`source_location_location_id`\r\n" + 
			"JOIN `location` dl ON dl.`location_id`=pr.`destination_location_location_id`\r\n" + 
			"LEFT JOIN \r\n" + 
			"(SELECT\r\n" + 
			"	SUM(item.`transfer_quantity`) AS `transferQty`,\r\n" + 
			"	SUM(item.`received_quantity`) AS `receivedQty`,\r\n" + 
			"	item.`lp_tranfer_location_product_transfer_id`\r\n" + 
			"	FROM `lptransfer_item` item\r\n" + 
			"	GROUP BY  item.`lp_tranfer_location_product_transfer_id`\r\n" + 
			") AS `qty`\r\n" + 
			"ON qty.`lp_tranfer_location_product_transfer_id`=trf.`location_product_transfer_id`\r\n" + 
			"WHERE pr.`location_product_request_status`!='Archived'\r\n" + 
			"AND trf.`location_product_transfer_id`=:transferId", nativeQuery=true)
	Map<String,Object> getTransferById(@Param("transferId")long transferId);
	
	
	@Query(value="SELECT \r\n" + 
			"trf.`location_product_transfer_id` AS `id`,\r\n" + 
			"trf.`location_product_transfer_manual_id` AS `manualId`,\r\n" + 
			"sl.`location_id` AS `sourceLocationId`,\r\n" + 
			"sl.`location_name` AS `sourceLocationName`,\r\n" + 
			"dl.`location_id` AS `destinationLocationId`,\r\n" + 
			"dl.`location_name` AS `destinationLocationName`,\r\n" + 
			"trf.`actual_delivery_date` AS `deliveryDate`,\r\n" + 
			"trf.`shipping_mode` AS `shippingMode`,\r\n" + 
			"trf.`shipping_to_name` AS `shippingToName`,\r\n" + 
			"trf.`tracking_number` AS `trackingNumber`,\r\n" + 
			"trf.`received_date` AS `receivedDate`,\r\n" + 
			"trf.`location_product_request_location_product_request_id` AS `productRequestId`,\r\n" + 
			"pr.`location_product_request_manual_id` AS `productRequestManualId`,\r\n" + 
			"qty.transferQty,\r\n" + 
			"qty.receivedQty,\r\n" + 
			"(CASE\r\n" + 
			"	WHEN qty.transferQty!=0 AND qty.receivedQty!=0 AND qty.transferQty=qty.receivedQty THEN 'Received'\r\n" + 
			"	WHEN qty.transferQty!=0 AND qty.receivedQty!=0 AND qty.transferQty>qty.receivedQty THEN 'Partially Received'\r\n" + 
			"	WHEN qty.receivedQty=0 THEN 'Pendig to Received'\r\n" + 
			"END) AS `status`,\r\n" + 
			"trf.`created_date` AS `createdDate`,\r\n" + 
			"trf.`modified_date` AS `modifiedDate`,\r\n" + 
			"trf.`created_by_email_id` AS `createdByEmailId`,\r\n" + 
			"CONCAT(CONCAT(cby.`first_name`,' '),IFNULL(CONCAT(cby.`middle_name`,' '),''),cby.`last_name`) AS `createdByName`,\r\n" + 
			"trf.`modified_by_email_id` AS `modifiedByEmailId`,\r\n" + 
			"CONCAT(CONCAT(mby.`first_name`,' '),IFNULL(CONCAT(mby.`middle_name`,' '),''),mby.`last_name`) AS `modifiedByName`,\r\n" + 
			"trf.`owner_email_id` AS `ownerEmailId`,\r\n" + 
			"CONCAT(CONCAT(onr.`first_name`,' '),IFNULL(CONCAT(onr.`middle_name`,' '),''),onr.`last_name`) AS `ownerName`,\r\n" + 
			"trf.`deliv`,\r\n" + 
			"trf.`invoice`,\r\n" + 
			"trf.`mblnr_g`,\r\n" + 
			"trf.`mjahr_g`,\r\n" + 
			"trf.`remark_d`,\r\n" + 
			"trf.`remark_g`,\r\n" + 
			"trf.`remark_i`\r\n" + 
			"FROM `lptrasfer` trf\r\n" + 
			"JOIN `lproduct_request` pr ON pr.`location_product_request_id`=trf.`location_product_request_location_product_request_id`\r\n" + 
			"JOIN `employee_details` cby ON cby.`email_id`=trf.`created_by_email_id`\r\n" + 
			"JOIN `employee_details` mby ON mby.`email_id`=trf.`modified_by_email_id`\r\n" + 
			"JOIN `employee_details` onr ON onr.`email_id`=trf.`owner_email_id`\r\n" + 
			"JOIN `location` sl ON sl.`location_id`=pr.`source_location_location_id`\r\n" + 
			"JOIN `location` dl ON dl.`location_id`=pr.`destination_location_location_id`\r\n" +
			"LEFT JOIN \r\n" + 
			"(SELECT\r\n" + 
			"	SUM(item.`transfer_quantity`) AS `transferQty`,\r\n" + 
			"	SUM(item.`received_quantity`) AS `receivedQty`,\r\n" + 
			"	item.`lp_tranfer_location_product_transfer_id`\r\n" + 
			"	FROM `lptransfer_item` item\r\n" + 
			"	GROUP BY  item.`lp_tranfer_location_product_transfer_id`\r\n" + 
			") AS `qty`\r\n" + 
			"ON qty.`lp_tranfer_location_product_transfer_id`=trf.`location_product_transfer_id`\r\n" + 
			"WHERE pr.`location_product_request_status`!='Archived'\r\n" + 
			"AND (sl.`location_id` IN (:ids) OR dl.`location_id` IN (:ids))", nativeQuery=true, countQuery="SELECT \r\n" + 
					"COUNT(*) AS countQuery\r\n" +
					"FROM `lptrasfer` trf\r\n" + 
					"JOIN `lproduct_request` pr ON pr.`location_product_request_id`=trf.`location_product_request_location_product_request_id`\r\n" + 
					"JOIN `employee_details` cby ON cby.`email_id`=trf.`created_by_email_id`\r\n" + 
					"JOIN `employee_details` mby ON mby.`email_id`=trf.`modified_by_email_id`\r\n" + 
					"JOIN `employee_details` onr ON onr.`email_id`=trf.`owner_email_id`\r\n" + 
					"JOIN `location` sl ON sl.`location_id`=pr.`source_location_location_id`\r\n" + 
					"JOIN `location` dl ON dl.`location_id`=pr.`destination_location_location_id`\r\n" + 
					"LEFT JOIN \r\n" + 
					"(SELECT\r\n" + 
					"	SUM(item.`transfer_quantity`) AS `transferQty`,\r\n" + 
					"	SUM(item.`received_quantity`) AS `receivedQty`,\r\n" + 
					"	item.`lp_tranfer_location_product_transfer_id`\r\n" + 
					"	FROM `lptransfer_item` item\r\n" + 
					"	GROUP BY  item.`lp_tranfer_location_product_transfer_id`\r\n" + 
					") AS `qty`\r\n" + 
					"ON qty.`lp_tranfer_location_product_transfer_id`=trf.`location_product_transfer_id`\r\n" + 
					"WHERE pr.`location_product_request_status`!='Archived'\r\n" + 
					"AND (sl.`location_id` IN (:ids) OR dl.`location_id` IN (:ids))")
	Page<Map<String,Object>> getProductTransfers(@Param("ids")java.util.List<Long> ids ,Pageable page);
	
	
	@Query(value="SELECT \r\n" + 
			"trf.`location_product_transfer_id` AS `id`,\r\n" + 
			"trf.`location_product_transfer_manual_id` AS `manualId`,\r\n" + 
			"sl.`location_id` AS `sourceLocationId`,\r\n" + 
			"sl.`location_name` AS `sourceLocationName`,\r\n" + 
			"dl.`location_id` AS `destinationLocationId`,\r\n" + 
			"dl.`location_name` AS `destinationLocationName`,\r\n" + 
			"trf.`actual_delivery_date` AS `deliveryDate`,\r\n" + 
			"trf.`shipping_mode` AS `shippingMode`,\r\n" + 
			"trf.`shipping_to_name` AS `shippingToName`,\r\n" + 
			"trf.`tracking_number` AS `trackingNumber`,\r\n" + 
			"trf.`received_date` AS `receivedDate`,\r\n" + 
			"trf.`location_product_request_location_product_request_id` AS `productRequestId`,\r\n" + 
			"pr.`location_product_request_manual_id` AS `productRequestManualId`,\r\n" + 
			"qty.transferQty,\r\n" + 
			"qty.receivedQty,\r\n" + 
			"(CASE\r\n" + 
			"	WHEN qty.transferQty!=0 AND qty.receivedQty!=0 AND qty.transferQty=qty.receivedQty THEN 'Received'\r\n" + 
			"	WHEN qty.transferQty!=0 AND qty.receivedQty!=0 AND qty.transferQty>qty.receivedQty THEN 'Partially Received'\r\n" + 
			"	WHEN qty.receivedQty=0 THEN 'Pendig to Received'\r\n" + 
			"END) AS `status`,\r\n" + 
			"trf.`created_date` AS `createdDate`,\r\n" + 
			"trf.`modified_date` AS `modifiedDate`,\r\n" + 
			"trf.`created_by_email_id` AS `createdByEmailId`,\r\n" + 
			"CONCAT(CONCAT(cby.`first_name`,' '),IFNULL(CONCAT(cby.`middle_name`,' '),''),cby.`last_name`) AS `createdByName`,\r\n" + 
			"trf.`modified_by_email_id` AS `modifiedByEmailId`,\r\n" + 
			"CONCAT(CONCAT(mby.`first_name`,' '),IFNULL(CONCAT(mby.`middle_name`,' '),''),mby.`last_name`) AS `modifiedByName`,\r\n" + 
			"trf.`owner_email_id` AS `ownerEmailId`,\r\n" + 
			"CONCAT(CONCAT(onr.`first_name`,' '),IFNULL(CONCAT(onr.`middle_name`,' '),''),onr.`last_name`) AS `ownerName`,\r\n" + 
			"trf.`deliv`,\r\n" + 
			"trf.`invoice`,\r\n" + 
			"trf.`mblnr_g`,\r\n" + 
			"trf.`mjahr_g`,\r\n" + 
			"trf.`remark_d`,\r\n" + 
			"trf.`remark_g`,\r\n" + 
			"trf.`remark_i`\r\n" + 
			"FROM `lptrasfer` trf\r\n" + 
			"JOIN `lproduct_request` pr ON pr.`location_product_request_id`=trf.`location_product_request_location_product_request_id`\r\n" + 
			"JOIN `employee_details` cby ON cby.`email_id`=trf.`created_by_email_id`\r\n" + 
			"JOIN `employee_details` mby ON mby.`email_id`=trf.`modified_by_email_id`\r\n" + 
			"JOIN `employee_details` onr ON onr.`email_id`=trf.`owner_email_id`\r\n" + 
			"JOIN `location` sl ON sl.`location_id`=pr.`source_location_location_id`\r\n" + 
			"JOIN `location` dl ON dl.`location_id`=pr.`destination_location_location_id`\r\n" + 
			"LEFT JOIN \r\n" + 
			"(SELECT\r\n" + 
			"	SUM(item.`transfer_quantity`) AS `transferQty`,\r\n" + 
			"	SUM(item.`received_quantity`) AS `receivedQty`,\r\n" + 
			"	item.`lp_tranfer_location_product_transfer_id`\r\n" + 
			"	FROM `lptransfer_item` item\r\n" + 
			"	GROUP BY  item.`lp_tranfer_location_product_transfer_id`\r\n" + 
			") AS `qty`\r\n" + 
			"ON qty.`lp_tranfer_location_product_transfer_id`=trf.`location_product_transfer_id`\r\n" + 
			"WHERE pr.`location_product_request_status`!='Archived'\r\n" + 
			"AND (sl.`location_id` IN (:ids) OR dl.`location_id` IN (:ids))", nativeQuery=true, countQuery="SELECT \r\n" + 
					"COUNT(*) AS countQuery\r\n" +
					"FROM `lptrasfer` trf\r\n" + 
					"JOIN `lproduct_request` pr ON pr.`location_product_request_id`=trf.`location_product_request_location_product_request_id`\r\n" + 
					"JOIN `employee_details` cby ON cby.`email_id`=trf.`created_by_email_id`\r\n" + 
					"JOIN `employee_details` mby ON mby.`email_id`=trf.`modified_by_email_id`\r\n" + 
					"JOIN `employee_details` onr ON onr.`email_id`=trf.`owner_email_id`\r\n" + 
					"JOIN `location` sl ON sl.`location_id`=pr.`source_location_location_id`\r\n" + 
					"JOIN `location` dl ON dl.`location_id`=pr.`destination_location_location_id`\r\n" + 
					"LEFT JOIN \r\n" + 
					"(SELECT\r\n" + 
					"	SUM(item.`transfer_quantity`) AS `transferQty`,\r\n" + 
					"	SUM(item.`received_quantity`) AS `receivedQty`,\r\n" + 
					"	item.`lp_tranfer_location_product_transfer_id`\r\n" + 
					"	FROM `lptransfer_item` item\r\n" + 
					"	GROUP BY  item.`lp_tranfer_location_product_transfer_id`\r\n" + 
					") AS `qty`\r\n" + 
					"ON qty.`lp_tranfer_location_product_transfer_id`=trf.`location_product_transfer_id`\r\n" + 
					"WHERE pr.`location_product_request_status`!='Archived'\r\n")
	Page<Map<String,Object>> getProductTransfers(Pageable page);
	
	
	@Query("select new com.pratikshat.exicom.model.response.LocationProductTransferItemResponse(item) from LPTransferItem item join item.product prd join item.lpTranfer trn join trn.owner onr join trn.createdBy crt join trn.modifiedBy mdf join trn.locationProductRequest preq join preq.sourceLocation sl join preq.destinationLocation dl join item.lpRequestLineItem ritem where trn.locationProductTransferId=:transferId")
	Page<com.pratikshat.exicom.model.response.LocationProductTransferItemResponse> getTransferItemsByTrnasferId(@Param("transferId")long transferId ,Pageable page);
}
