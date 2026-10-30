package com.pratikshat.exicom.repository;

import java.util.List;
import java.util.Map;

import com.pratikshat.exicom.entity.BtProductTransferItem;
import com.pratikshat.exicom.repository.custom.BtProductTransferItemRepositoryCustom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BtProductTransferItemRepository extends JpaRepository<BtProductTransferItem,Long>,BtProductTransferItemRepositoryCustom{
    @Query(value = "SELECT titem.`manual_id` AS transferItemManualId,"
	+" titem.`transfer_quantity` AS transferQuantity,"
	+" titem.`received_quantity` AS transferReceivedQuantity,"
	
	+" btp.`manual_id` AS productPartcode,"
	+" btp.`id` AS productId,"
	+" btp.`product_type` AS productType,"
	+" btp.`product_description` AS productDescription,"
	
	+" reqitem.`manual_id` AS productRequestItemManualId,"
	+" reqitem.id AS productRequestItemId,"
	+" req.`id` AS productRequestId,"
	+" req.`manual_id` AS productRequestManualId"
	
	+" FROM `bt_product_transfer_item` titem "
	+" JOIN `bt_product_transfer` trn ON trn.id=titem.`btp_transfer_id`"
	+" JOIN `bt_product_request_item` reqitem ON reqitem.id=titem.`btp_request_item_id`"
	+" JOIN `bt_products` btp ON btp.`id`=reqitem.`bt_product_id`"
	+" JOIN `bt_product_request` req ON req.id=reqitem.`bt_product_request_id` WHERE trn.id=:trnid",nativeQuery = true,countQuery = "SELECT count(*) as ct"
	
	+" FROM `bt_product_transfer_item` titem "
	+" JOIN `bt_product_transfer` trn ON trn.id=titem.`btp_transfer_id`"
	+" JOIN `bt_product_request_item` reqitem ON reqitem.id=titem.`btp_request_item_id`"
	+" JOIN `bt_products` btp ON btp.`id`=reqitem.`bt_product_id`"
	+" JOIN `bt_product_request` req ON req.id=reqitem.`bt_product_request_id` WHERE trn.id=:trnid")
    Page<Map<String,Object>> getItems(@Param("trnid")Long transferId,Pageable page);

    @Query(value = "SELECT titem.`manual_id` AS transferItemManualId,"
	+" titem.`transfer_quantity` AS transferQuantity,"
	+" titem.`received_quantity` AS transferReceivedQuantity,"
	
	+" btp.`manual_id` AS productPartcode,"
	+" btp.`id` AS productId,"
	+" btp.`product_type` AS productType,"
	+" btp.`product_description` AS productDescription,"
	
	+" reqitem.`manual_id` AS productRequestItemManualId,"
	+" reqitem.id AS productRequestItemId,"
	+" req.`id` AS productRequestId,"
	+" req.`manual_id` AS productRequestManualId"
	
	+" FROM `bt_product_transfer_item` titem "
	+" JOIN `bt_product_transfer` trn ON trn.id=titem.`btp_transfer_id`"
	+" JOIN `bt_product_request_item` reqitem ON reqitem.id=titem.`btp_request_item_id`"
	+" JOIN `bt_products` btp ON btp.`id`=reqitem.`bt_product_id`"
	+" JOIN `bt_product_request` req ON req.id=reqitem.`bt_product_request_id` WHERE trn.id=:trnid",nativeQuery = true)
    List<Map<String,Object>> getItems(@Param("trnid")Long transferId);


	@Query(value="SELECT distinct \r\n" + 
			" trn.`id` AS productTrnsferId,\r\n" + 
			" trn.`created_date` AS productTransferCreatedDate,\r\n" + 
			" trn.`last_modified_date` AS productTransferLastModifiedDate,\r\n" + 
			" trn.`deliv` AS productTransferSapDeliv,\r\n" + 
			" trn.`invoice` AS productTransferSapInvocieNumber,\r\n" + 
			" trn.`mblnr_g` AS productTransferSapMblnrg,\r\n" + 
			" trn.`mjahr_g` AS productTransferSapMjahrG,\r\n" + 
			" trn.`remark_d` AS productTransferRemarkD,\r\n" + 
			" trn.`remark_g` AS productTraansferRemarkG,\r\n" + 
			" trn.`remark_i` AS productTransferRemarkI,\r\n" + 
			" trn.`actual_delivery_date` AS productTransferActualDeliveryDate,\r\n" + 
			" trn.`manual_id` AS productTransferManualId,\r\n" + 
			" trn.`received_date` AS productTransferReceivedDate,\r\n" + 
			" trn.`shipping_mode` AS productTransferShippingMode,\r\n" + 
			" trn.`shipping_to_name` AS productTransferShippingToName,\r\n" + 
			" trn.`tracking_number` AS productTranferTrackingNumber,\r\n" + 
			" trn.`created_by_email_id` AS productTransferCreatedByEmaiilId,\r\n" + 
			" trn.`last_modified_by_email_id` AS productTransferLastModifiedByEmailId,\r\n" + 
			" req.`id` AS productRequestId,\r\n" + 
			" req.`manual_id` AS productRequestManualId,\r\n" + 
			" sloc.location_name AS productRequestSourceLocationName,\r\n" + 
			" dloc.location_name AS productRequestDestinationLocationName,\r\n" + 
			" sloc.location_id AS productRequestSourceLocationId,\r\n" + 
			" dloc.location_id AS productRequestDestinationLocationId\r\n" + 
			" FROM `bt_product_transfer` trn \r\n" + 
			" JOIN bt_product_transfer_item trn_item on trn.id=trn_item.btp_transfer_id\r\n"+
			" JOIN bt_product_request_item req_item on req_item.id=trn_item.btp_request_item_id\r\n"+
			" JOIN `bt_product_request` req ON req_item.bt_product_request_id`=req.`id`\r\n" + 
			" JOIN `location` sloc ON sloc.location_id=trn.`source_location_location_id`\r\n" + 
			" JOIN location dloc ON dloc.location_id=trn.`destination_location_location_id` where req.id=:productRequestId and req_item.id=:productRequestItemId",nativeQuery=true)
	public List<Map<String,Object>> getProductTransferByProductRequestIdAndRequestItemId(@Param("productRequestId")Long productRequestId,@Param("productRequestItemId")Long productRequestItemId);
	
	@Query(value="SELECT distinct \r\n" + 
			" trn.`id` AS productTrnsferId,\r\n" + 
			" trn.`created_date` AS productTransferCreatedDate,\r\n" + 
			" trn.`last_modified_date` AS productTransferLastModifiedDate,\r\n" + 
			" trn.`deliv` AS productTransferSapDeliv,\r\n" + 
			" trn.`invoice` AS productTransferSapInvocieNumber,\r\n" + 
			" trn.`mblnr_g` AS productTransferSapMblnrg,\r\n" + 
			" trn.`mjahr_g` AS productTransferSapMjahrG,\r\n" + 
			" trn.`remark_d` AS productTransferRemarkD,\r\n" + 
			" trn.`remark_g` AS productTraansferRemarkG,\r\n" + 
			" trn.`remark_i` AS productTransferRemarkI,\r\n" + 
			" trn.`actual_delivery_date` AS productTransferActualDeliveryDate,\r\n" + 
			" trn.`manual_id` AS productTransferManualId,\r\n" + 
			" trn.`received_date` AS productTransferReceivedDate,\r\n" + 
			" trn.`shipping_mode` AS productTransferShippingMode,\r\n" + 
			" trn.`shipping_to_name` AS productTransferShippingToName,\r\n" + 
			" trn.`tracking_number` AS productTranferTrackingNumber,\r\n" + 
			" trn.`created_by_email_id` AS productTransferCreatedByEmaiilId,\r\n" + 
			" trn.`last_modified_by_email_id` AS productTransferLastModifiedByEmailId,\r\n" + 
			" req.`id` AS productRequestId,\r\n" + 
			" req.`manual_id` AS productRequestManualId,\r\n" + 
			" sloc.location_name AS productRequestSourceLocationName,\r\n" + 
			" dloc.location_name AS productRequestDestinationLocationName,\r\n" + 
			" sloc.location_id AS productRequestSourceLocationId,\r\n" + 
			" dloc.location_id AS productRequestDestinationLocationId\r\n" + 
			" FROM `bt_product_transfer` trn \r\n" + 
			" JOIN bt_product_transfer_item trn_item on trn.id=trn_item.btp_transfer_id\r\n"+
			" JOIN bt_product_request_item req_item on req_item.id=trn_item.btp_request_item_id\r\n"+
			" JOIN `bt_product_request` req ON req_item.bt_product_request_id`=req.`id`\r\n" + 
			" JOIN `location` sloc ON sloc.location_id=trn.`source_location_location_id`\r\n" + 
			" JOIN location dloc ON dloc.location_id=trn.`destination_location_location_id` where req.id=:productRequestId and (dloc.location_id in :locationIds or sloc.location_id in locationIds) and req_item.id=:productRequestItemId",nativeQuery=true)
	public List<Map<String,Object>> getProductTransferByProductRequestIdAndRequestItemId(@Param("productRequestId")Long productRequestId,@Param("locationIds")List<Long> locationIds,@Param("productRequestItemId")Long productRequestItemId);

	@Query(value="SELECT "
		+" titem.`id` AS transferItemId, "
		+" titem.`created_date` AS transferItemCreatedDate, "
		+" titem.`last_modified_date` AS transferItemLastModifiedDate, "
		+" titem.`manual_id` AS transferItemManualId, "
		+" titem.`received_quantity` AS transferItemReceivedQuantity, "
		+" titem.`transfer_quantity` AS transferItemTransferedQuantity, "
		+" titem.`created_by_email_id` AS transferItemCreatedByEmailId, "
		+" titem.`last_modified_by_email_id` AS transferItemModifiedByEmailId, "
		+" titem.`btp_request_item_id` AS productRequestItemId, "
		+" tran.`shipping_mode` AS transferShippingMode, "
		+" tran.`shipping_to_name` AS transferShippingToName, "
		+" tran.`tracking_number` AS trackingNumber, "
		+" tran.`manual_id` AS transferManualId, "
		+" sloc.location_id AS transferSourceLocationId, "
		+" sloc.location_name AS transferSourceLocationName, "
		+" dloc.location_id AS transferDestinationLocationId, "
		+" dloc.location_name AS transferDestinationLocationName, "
		+" prd.id AS productId, "
		+" prd.`manual_id` AS productPartcode, "
		+" prd.`product_type` AS productType, "
		+" prd.`product_description` AS productDescription "
		
		+" FROM `bt_product_transfer_item` titem  "
		+" JOIN `bt_product_transfer` tran ON tran.id=titem.`btp_transfer_id` "
		+" JOIN `bt_product_request_item` rqitem ON rqitem.id= titem.`btp_request_item_id` "
		+" JOIN `bt_products` prd ON prd.id=rqitem.`bt_product_id` "
		+" JOIN location sloc ON sloc.location_id=tran.`source_location_location_id` "
		+" JOIN location dloc ON dloc.location_id=tran.`destination_location_location_id` WHERE titem.received_quantity<titem.transfer_quantity AND dloc.location_id=:locationId",nativeQuery = true)
	public List<Map<String,Object>> getPendingTransferItemToReceive(@Param("locationId")Long destinationLocationId);


}
