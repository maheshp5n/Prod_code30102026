package com.pratikshat.exicom.repository;

import java.util.List;
import java.util.Map;
//
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.BtProductTransfer;
import com.pratikshat.exicom.repository.custom.BtProductTransferRepositoryCustom;

@Repository
public interface BtProductTransferRepository
		extends JpaRepository<BtProductTransfer, Long>, BtProductTransferRepositoryCustom {
	
	@Query("select trn from BtProductTransfer trn join trn.destinationLocation dloc join trn.sourceLocation sloc join dloc.legalEntity dlg join sloc.legalEntity slg where trn.id=:id")
	BtProductTransfer getTransferById(Long id);

	@Query(value = "SELECT \r\n" + "	trn.`id` AS productTrnsferId,\r\n"
			+ "	trn.`created_date` AS productTransferCreatedDate,\r\n"
			+ "	trn.`last_modified_date` AS productTransferLastModifiedDate,\r\n"
			+ "	trn.`deliv` AS productTransferSapDeliv,\r\n"
			+ "	trn.`invoice` AS productTransferSapInvocieNumber,\r\n"
			+ "	trn.`mblnr_g` AS productTransferSapMblnrg,\r\n" + "	trn.`mjahr_g` AS productTransferSapMjahrG,\r\n"
			+ "	trn.`remark_d` AS productTransferRemarkD,\r\n" + "	trn.`remark_g` AS productTraansferRemarkG,\r\n"
			+ "	trn.`remark_i` AS productTransferRemarkI,\r\n"
			+ "	trn.`actual_delivery_date` AS productTransferActualDeliveryDate,\r\n"
			+ "	trn.`manual_id` AS productTransferManualId,\r\n"
			+ "	trn.`received_date` AS productTransferReceivedDate,\r\n"
			+ "	trn.`shipping_mode` AS productTransferShippingMode,\r\n"
			+ "	trn.`shipping_to_name` AS productTransferShippingToName,\r\n"
			+ "	trn.`tracking_number` AS productTranferTrackingNumber,\r\n"
			+ "	trn.`created_by_email_id` AS productTransferCreatedByEmaiilId,\r\n"
			+ "	trn.`last_modified_by_email_id` AS productTransferLastModifiedByEmailId,\r\n"
			+ "	trn.transfer_status AS productTransferStatus,"
			+ " trn.`total_quantity_issued` AS productTransferTotalQuantityIssued,"
			+ "	trn.`total_quantity_received` AS productTransferTotalQuantityReceived, "
			+ "	cemp.first_name AS productTransferCreatedByFirstName,"
			+ "	cemp.`middle_name` AS productTransferCreatedByMiddleName,"
			+ "	cemp.`last_name` AS productTransferCreatedByLastName," +

			"	lemp.first_name AS productTransferLastModifiedByFirstName,"
			+ "	lemp.`middle_name` AS productTransferLastModifiedByMiddleName,"
			+ "	lemp.`last_name` AS productTransferLastModifiedByLastName," +

			"	oemp.first_name AS productTransferOwnerFirstName,"
			+ "	oemp.`middle_name` AS productTransferOwnerMiddleName,"
			+ "	oemp.`last_name` AS productTransferOwnerLastName," +

			"	sloc.location_name AS productRequestSourceLocationName,\r\n"
			+ "	dloc.location_name AS productRequestDestinationLocationName,\r\n"
			+ "	sloc.location_id AS productRequestSourceLocationId,\r\n"
			+ "	dloc.location_id AS productRequestDestinationLocationId\r\n" + " FROM `bt_product_transfer` trn \r\n" +

			"	JOIN `location` sloc ON sloc.location_id=trn.`source_location_location_id`\r\n"
			+ "	JOIN location dloc ON dloc.location_id=trn.`destination_location_location_id` "
			+ "	JOIN employee_details cemp ON cemp.email_id=trn.`created_by_email_id` "
			+ "	JOIN employee_details lemp ON lemp.email_id=trn.`last_modified_by_email_id` "
			+ "	JOIN employee_details oemp ON oemp.email_id=trn.`owner_email_id`", nativeQuery = true, countQuery = "SELECT count(*) as ct\r\n"
					+ " FROM `bt_product_transfer` trn \r\n"
					+ "	JOIN `location` sloc ON sloc.location_id=trn.`source_location_location_id`\r\n"
					+ "	JOIN location dloc ON dloc.location_id=trn.`destination_location_location_id`"
					+ "	JOIN employee_details cemp ON cemp.email_id=trn.`created_by_email_id` "
					+ "	JOIN employee_details lemp ON lemp.email_id=trn.`last_modified_by_email_id` "
					+ "	JOIN employee_details oemp ON oemp.email_id=trn.`owner_email_id`")
					
	public Page<Map<String, Object>> getPage(Pageable page);

	@Query(value = "SELECT \r\n" + "	trn.`id` AS productTrnsferId,\r\n"
			+ "	trn.`created_date` AS productTransferCreatedDate,\r\n"
			+ "	trn.`last_modified_date` AS productTransferLastModifiedDate,\r\n"
			+ "	trn.`deliv` AS productTransferSapDeliv,\r\n"
			+ "	trn.`invoice` AS productTransferSapInvocieNumber,\r\n"
			+ "	trn.`mblnr_g` AS productTransferSapMblnrg,\r\n" + "	trn.`mjahr_g` AS productTransferSapMjahrG,\r\n"
			+ "	trn.`remark_d` AS productTransferRemarkD,\r\n" + "	trn.`remark_g` AS productTraansferRemarkG,\r\n"
			+ "	trn.`remark_i` AS productTransferRemarkI,\r\n"
			+ "	trn.`actual_delivery_date` AS productTransferActualDeliveryDate,\r\n"
			+ "	trn.`manual_id` AS productTransferManualId,\r\n"
			+ "	trn.`received_date` AS productTransferReceivedDate,\r\n"
			+ "	trn.`shipping_mode` AS productTransferShippingMode,\r\n"
			+ "	trn.`shipping_to_name` AS productTransferShippingToName,\r\n"
			+ "	trn.`tracking_number` AS productTranferTrackingNumber,\r\n"
			+ "	trn.`created_by_email_id` AS productTransferCreatedByEmaiilId,\r\n"
			+ "	trn.`last_modified_by_email_id` AS productTransferLastModifiedByEmailId,\r\n"
			+ "	trn.transfer_status AS productTransferStatus,"
			+ " trn.`total_quantity_issued` AS productTransferTotalQuantityIssued,"
			+ "	trn.`total_quantity_received` AS productTransferTotalQuantityReceived, "
			+ "	cemp.first_name AS productTransferCreatedByFirstName,"
			+ "	cemp.`middle_name` AS productTransferCreatedByMiddleName,"
			+ "	cemp.`last_name` AS productTransferCreatedByLastName,"
			+ "	lemp.first_name AS productTransferLastModifiedByFirstName,"
			+ "	lemp.`middle_name` AS productTransferLastModifiedByMiddleName,"
			+ "	lemp.`last_name` AS productTransferLastModifiedByLastName," +

			"	oemp.first_name AS productTransferOwnerFirstName,"
			+ "	oemp.`middle_name` AS productTransferOwnerMiddleName,"
			+ "	oemp.`last_name` AS productTransferOwnerLastName," +

			"	sloc.location_name AS productRequestSourceLocationName,\r\n"
			+ "	dloc.location_name AS productRequestDestinationLocationName,\r\n"
			+ "	sloc.location_id AS productRequestSourceLocationId,\r\n"
			+ "	dloc.location_id AS productRequestDestinationLocationId\r\n" + " FROM `bt_product_transfer` trn \r\n"
			+ "	JOIN employee_details cemp ON cemp.email_id=trn.`created_by_email_id` "
			+ "	JOIN employee_details lemp ON lemp.email_id=trn.`last_modified_by_email_id` "
			+ "	JOIN employee_details oemp ON oemp.email_id=trn.`owner_email_id` "
			+ "	JOIN `location` sloc ON sloc.location_id=trn.`source_location_location_id`\r\n"
			+ "	JOIN location dloc ON dloc.location_id=trn.`destination_location_location_id` where dloc.location_id in :locationIds or sloc.location_id in :locationIds", nativeQuery = true, countQuery = "SELECT count(*) as ct\r\n"
					+

					" FROM `bt_product_transfer` trn \r\n"
					+ "	JOIN employee_details cemp ON cemp.email_id=trn.`created_by_email_id` "
					+ "	JOIN employee_details lemp ON lemp.email_id=trn.`last_modified_by_email_id` "
					+ "	JOIN employee_details oemp ON oemp.email_id=trn.`owner_email_id` "
					+ "	JOIN `location` sloc ON sloc.location_id=trn.`source_location_location_id`\r\n"
					+ "	JOIN location dloc ON dloc.location_id=trn.`destination_location_location_id` where dloc.location_id in :locationIds or sloc.location_id in :locationIds")
	public Page<Map<String, Object>> getPage(@Param("locationIds") List<Long> locationIds, Pageable page);

	@Query(value = "SELECT \r\n" + "	trn.`id` AS productTrnsferId,\r\n"
			+ "	trn.`created_date` AS productTransferCreatedDate,\r\n"
			+ "	trn.`last_modified_date` AS productTransferLastModifiedDate,\r\n"
			+ "	trn.`deliv` AS productTransferSapDeliv,\r\n"
			+ "	trn.`invoice` AS productTransferSapInvocieNumber,\r\n"
			+ "	trn.`mblnr_g` AS productTransferSapMblnrg,\r\n" + "	trn.`mjahr_g` AS productTransferSapMjahrG,\r\n"
			+ "	trn.`remark_d` AS productTransferRemarkD,\r\n" + "	trn.`remark_g` AS productTraansferRemarkG,\r\n"
			+ "	trn.`remark_i` AS productTransferRemarkI,\r\n"
			+ "	trn.`actual_delivery_date` AS productTransferActualDeliveryDate,\r\n"
			+ "	trn.`manual_id` AS productTransferManualId,\r\n"
			+ "	trn.`received_date` AS productTransferReceivedDate,\r\n"
			+ "	trn.`shipping_mode` AS productTransferShippingMode,\r\n"
			+ "	trn.`shipping_to_name` AS productTransferShippingToName,\r\n"
			+ "	trn.`tracking_number` AS productTranferTrackingNumber,\r\n"
			+ "	trn.`created_by_email_id` AS productTransferCreatedByEmaiilId,\r\n"
			+ "	trn.`last_modified_by_email_id` AS productTransferLastModifiedByEmailId,\r\n"
			+ "	trn.transfer_status AS productTransferStatus,"
			+ " trn.`total_quantity_issued` AS productTransferTotalQuantityIssued,"
			+ "	trn.`total_quantity_received` AS productTransferTotalQuantityReceived, "
			+ "	cemp.first_name AS productTransferCreatedByFirstName,"
			+ "	cemp.`middle_name` AS productTransferCreatedByMiddleName,"
			+ "	cemp.`last_name` AS productTransferCreatedByLastName,"
			+ "	lemp.first_name AS productTransferLastModifiedByFirstName,"
			+ "	lemp.`middle_name` AS productTransferLastModifiedByMiddleName,"
			+ "	lemp.`last_name` AS productTransferLastModifiedByLastName," +

			"	oemp.first_name AS productTransferOwnerFirstName,"
			+ "	oemp.`middle_name` AS productTransferOwnerMiddleName,"
			+ "	oemp.`last_name` AS productTransferOwnerLastName," +

			"	sloc.location_name AS productRequestSourceLocationName,\r\n"
			+ "	dloc.location_name AS productRequestDestinationLocationName,\r\n"
			+ "	sloc.location_id AS productRequestSourceLocationId,\r\n"
			+ "	dloc.location_id AS productRequestDestinationLocationId\r\n" + " FROM `bt_product_transfer` trn \r\n"
			+ "	JOIN employee_details cemp ON cemp.email_id=trn.`created_by_email_id` "
			+ "	JOIN employee_details lemp ON lemp.email_id=trn.`last_modified_by_email_id` "
			+ "	JOIN employee_details oemp ON oemp.email_id=trn.`owner_email_id` "
			+ "	JOIN `bt_product_request` req ON trn.`bt_product_request_id`=req.`id`\r\n"
			+ "	JOIN `location` sloc ON sloc.location_id=req.`source_location_location_id`\r\n"
			+ "	JOIN location dloc ON dloc.location_id=req.`destination_location_location_id` where trn.transfer_status in :tStatus", nativeQuery = true, countQuery = "SELECT count(*) as ct \r\n"
					+

					" FROM `bt_product_transfer` trn \r\n"
					+ "	JOIN employee_details cemp ON cemp.email_id=trn.`created_by_email_id` "
					+ "	JOIN employee_details lemp ON lemp.email_id=trn.`last_modified_by_email_id` "
					+ "	JOIN employee_details oemp ON oemp.email_id=trn.`owner_email_id` "
					+ "	JOIN `bt_product_request` req ON trn.`bt_product_request_id`=req.`id`\r\n"
					+ "	JOIN `location` sloc ON sloc.location_id=req.`source_location_location_id`\r\n"
					+ "	JOIN location dloc ON dloc.location_id=req.`destination_location_location_id`  where trn.transfer_status in :tStatus")
	public Page<Map<String, Object>> getByStatus(@Param("tStatus") List<String> tStatus, Pageable page);

	@Query(value = "SELECT \r\n" + "	trn.`id` AS productTrnsferId,\r\n"
			+ "	trn.`created_date` AS productTransferCreatedDate,\r\n"
			+ "	trn.`last_modified_date` AS productTransferLastModifiedDate,\r\n"
			+ "	trn.`deliv` AS productTransferSapDeliv,\r\n"
			+ "	trn.`invoice` AS productTransferSapInvocieNumber,\r\n"
			+ "	trn.`mblnr_g` AS productTransferSapMblnrg,\r\n" + "	trn.`mjahr_g` AS productTransferSapMjahrG,\r\n"
			+ "	trn.`remark_d` AS productTransferRemarkD,\r\n" + "	trn.`remark_g` AS productTraansferRemarkG,\r\n"
			+ "	trn.`remark_i` AS productTransferRemarkI,\r\n"
			+ "	trn.`actual_delivery_date` AS productTransferActualDeliveryDate,\r\n"
			+ "	trn.`manual_id` AS productTransferManualId,\r\n"
			+ "	trn.`received_date` AS productTransferReceivedDate,\r\n"
			+ "	trn.`shipping_mode` AS productTransferShippingMode,\r\n"
			+ "	trn.`shipping_to_name` AS productTransferShippingToName,\r\n"
			+ "	trn.`tracking_number` AS productTranferTrackingNumber,\r\n"
			+ "	trn.`created_by_email_id` AS productTransferCreatedByEmaiilId,\r\n"
			+ "	trn.`last_modified_by_email_id` AS productTransferLastModifiedByEmailId,\r\n"
			+ "	trn.transfer_status AS productTransferStatus,"
			+ " trn.`total_quantity_issued` AS productTransferTotalQuantityIssued,"
			+ "	trn.`total_quantity_received` AS productTransferTotalQuantityReceived, "
			+ "	cemp.first_name AS productTransferCreatedByFirstName,"
			+ "	cemp.`middle_name` AS productTransferCreatedByMiddleName,"
			+ "	cemp.`last_name` AS productTransferCreatedByLastName,"
			+ "	lemp.first_name AS productTransferLastModifiedByFirstName,"
			+ "	lemp.`middle_name` AS productTransferLastModifiedByMiddleName,"
			+ "	lemp.`last_name` AS productTransferLastModifiedByLastName," +

			"	oemp.first_name AS productTransferOwnerFirstName,"
			+ "	oemp.`middle_name` AS productTransferOwnerMiddleName,"
			+ "	oemp.`last_name` AS productTransferOwnerLastName," +

			"	sloc.location_name AS productRequestSourceLocationName,\r\n"
			+ "	dloc.location_name AS productRequestDestinationLocationName,\r\n"
			+ "	sloc.location_id AS productRequestSourceLocationId,\r\n"
			+ "	dloc.location_id AS productRequestDestinationLocationId\r\n" + " FROM `bt_product_transfer` trn \r\n"
			+ "	JOIN employee_details cemp ON cemp.email_id=trn.`created_by_email_id` "
			+ "	JOIN employee_details lemp ON lemp.email_id=trn.`last_modified_by_email_id` "
			+ "	JOIN employee_details oemp ON oemp.email_id=trn.`owner_email_id` "
			+ "	JOIN `location` sloc ON sloc.location_id=trn.`source_location_location_id`\r\n"
			+ "	JOIN location dloc ON dloc.location_id=trn.`destination_location_location_id` where trn.transfer_status in :tStatus and (dloc.location_id in :locatinIds or sloc.location_id in :locatinIds)", nativeQuery = true, countQuery = "SELECT count(*) as ct \r\n"
					+

					" FROM `bt_product_transfer` trn \r\n"
					+ "	JOIN employee_details cemp ON cemp.email_id=trn.`created_by_email_id` "
					+ "	JOIN employee_details lemp ON lemp.email_id=trn.`last_modified_by_email_id` "
					+ "	JOIN employee_details oemp ON oemp.email_id=trn.`owner_email_id` "
					+ "	JOIN `location` sloc ON sloc.location_id=trn.`source_location_location_id`\r\n"
					+ "	JOIN location dloc ON dloc.location_id=trn.`destination_location_location_id` where  trn.transfer_status in :tStatus and (dloc.location_id in :locatinIds or sloc.location_id in :locatinIds)")
	public Page<Map<String, Object>> getByStatus(@Param("tStatus") List<String> tStatus,
			@Param("locatinIds") List<Long> locationIds, Pageable page);

	@Query(value = "SELECT distinct \r\n" + " trn.`id` AS productTrnsferId,\r\n"
			+ " trn.`created_date` AS productTransferCreatedDate,\r\n"
			+ " trn.`last_modified_date` AS productTransferLastModifiedDate,\r\n"
			+ " trn.`deliv` AS productTransferSapDeliv,\r\n" + " trn.`invoice` AS productTransferSapInvocieNumber,\r\n"
			+ " trn.`mblnr_g` AS productTransferSapMblnrg,\r\n" + " trn.`mjahr_g` AS productTransferSapMjahrG,\r\n"
			+ " trn.`remark_d` AS productTransferRemarkD,\r\n" + " trn.`remark_g` AS productTraansferRemarkG,\r\n"
			+ " trn.`remark_i` AS productTransferRemarkI,\r\n"
			+ " trn.`actual_delivery_date` AS productTransferActualDeliveryDate,\r\n"
			+ " trn.`manual_id` AS productTransferManualId,\r\n"
			+ " trn.`received_date` AS productTransferReceivedDate,\r\n"
			+ " trn.`shipping_mode` AS productTransferShippingMode,\r\n"
			+ " trn.`shipping_to_name` AS productTransferShippingToName,\r\n"
			+ " trn.`tracking_number` AS productTranferTrackingNumber,\r\n"
			+ " trn.`created_by_email_id` AS productTransferCreatedByEmaiilId,\r\n"
			+ " trn.`last_modified_by_email_id` AS productTransferLastModifiedByEmailId,\r\n"
			+ "	trn.transfer_status AS productTransferStatus,"
			+ " trn.`total_quantity_issued` AS productTransferTotalQuantityIssued,"
			+ "	trn.`total_quantity_received` AS productTransferTotalQuantityReceived, "
			+ "	cemp.first_name AS productTransferCreatedByFirstName,"
			+ "	cemp.`middle_name` AS productTransferCreatedByMiddleName,"
			+ "	cemp.`last_name` AS productTransferCreatedByLastName,"
			+ "	lemp.first_name AS productTransferLastModifiedByFirstName,"
			+ "	lemp.`middle_name` AS productTransferLastModifiedByMiddleName,"
			+ " btp.`manual_id` AS productPartcode, "
			+ " btp.`product_type` AS productType, "
			+ " btp.`product_description` AS productDescription, "
			+ "	lemp.`last_name` AS productTransferLastModifiedByLastName," +

			"	oemp.first_name AS productTransferOwnerFirstName,"
			+ "	oemp.`middle_name` AS productTransferOwnerMiddleName,"
			+ "	oemp.`last_name` AS productTransferOwnerLastName," +

			" req.`id` AS productRequestId,\r\n" + " req.`manual_id` AS productRequestManualId,\r\n"
			+ " sloc.location_name AS productRequestSourceLocationName,\r\n"
			+ " dloc.location_name AS productRequestDestinationLocationName,\r\n"
			+ " sloc.location_id AS productRequestSourceLocationId,\r\n"
			+ " dloc.location_id AS productRequestDestinationLocationId\r\n" + " FROM `bt_product_transfer` trn \r\n"
			+ " JOIN bt_product_transfer_item trn_item on trn.id=trn_item.btp_transfer_id\r\n"
			+ " JOIN bt_product_request_item req_item on req_item.id=trn_item.btp_request_item_id\r\n"
			+ " JOIN `bt_product_request` req ON req_item.`bt_product_request_id`=req.`id`\r\n"
			+ " JOIN `location` sloc ON sloc.location_id=trn.`source_location_location_id`\r\n"
			+ "	JOIN employee_details cemp ON cemp.email_id=trn.`created_by_email_id` "
			+ "	JOIN employee_details lemp ON lemp.email_id=trn.`last_modified_by_email_id` "
			+ "	JOIN employee_details oemp ON oemp.email_id=trn.`owner_email_id` "
			+ " JOIN bt_products btp ON btp.id=req_item.`bt_product_id` "
			+ " JOIN location dloc ON dloc.location_id=trn.`destination_location_location_id` where req.id=:productRequestId", nativeQuery = true)
	public List<Map<String, Object>> getProductTransferByProductRequestId(
			@Param("productRequestId") Long productRequestId);

			@Query(value = "SELECT distinct \r\n" + " trn.`id` AS productTrnsferId,\r\n"
			+ " trn.`created_date` AS productTransferCreatedDate,\r\n"
			+ " trn.`last_modified_date` AS productTransferLastModifiedDate,\r\n"
			+ " trn.`deliv` AS productTransferSapDeliv,\r\n" + " trn.`invoice` AS productTransferSapInvocieNumber,\r\n"
			+ " trn.`mblnr_g` AS productTransferSapMblnrg,\r\n" + " trn.`mjahr_g` AS productTransferSapMjahrG,\r\n"
			+ " trn.`remark_d` AS productTransferRemarkD,\r\n" + " trn.`remark_g` AS productTraansferRemarkG,\r\n"
			+ " trn.`remark_i` AS productTransferRemarkI,\r\n"
			+ " trn.`actual_delivery_date` AS productTransferActualDeliveryDate,\r\n"
			+ " trn.`manual_id` AS productTransferManualId,\r\n"
			+ " trn.`received_date` AS productTransferReceivedDate,\r\n"
			+ " trn.`shipping_mode` AS productTransferShippingMode,\r\n"
			+ " trn.`shipping_to_name` AS productTransferShippingToName,\r\n"
			+ " trn.`tracking_number` AS productTranferTrackingNumber,\r\n"
			+ " trn.`created_by_email_id` AS productTransferCreatedByEmaiilId,\r\n"
			+ " trn.`last_modified_by_email_id` AS productTransferLastModifiedByEmailId,\r\n"
			+ "	trn.transfer_status AS productTransferStatus,"
			+ " trn.`total_quantity_issued` AS productTransferTotalQuantityIssued,"
			+ "	trn.`total_quantity_received` AS productTransferTotalQuantityReceived, "
			+ "	cemp.first_name AS productTransferCreatedByFirstName,"
			+ "	cemp.`middle_name` AS productTransferCreatedByMiddleName,"
			+ "	cemp.`last_name` AS productTransferCreatedByLastName,"
			+ "	lemp.first_name AS productTransferLastModifiedByFirstName,"
			+ "	lemp.`middle_name` AS productTransferLastModifiedByMiddleName,"
			+ " btp.`manual_id` AS productPartcode, "
			+ " btp.`product_type` AS productType, "
			+ " btp.`product_description` AS productDescription, "
			+ "	lemp.`last_name` AS productTransferLastModifiedByLastName," +

			"	oemp.first_name AS productTransferOwnerFirstName,"
			+ "	oemp.`middle_name` AS productTransferOwnerMiddleName,"
			+ "	oemp.`last_name` AS productTransferOwnerLastName," +

			" req.`id` AS productRequestId,\r\n" + " req.`manual_id` AS productRequestManualId,\r\n"
			+ " sloc.location_name AS productRequestSourceLocationName,\r\n"
			+ " dloc.location_name AS productRequestDestinationLocationName,\r\n"
			+ " sloc.location_id AS productRequestSourceLocationId,\r\n"
			+ " dloc.location_id AS productRequestDestinationLocationId\r\n" + " FROM `bt_product_transfer` trn \r\n"
			+ " JOIN bt_product_transfer_item trn_item on trn.id=trn_item.btp_transfer_id\r\n"
			+ " JOIN bt_product_request_item req_item on req_item.id=trn_item.btp_request_item_id\r\n"
			+ " JOIN `bt_product_request` req ON req_item.`bt_product_request_id`=req.`id`\r\n"
			+ " JOIN `location` sloc ON sloc.location_id=trn.`source_location_location_id`\r\n"
			+ "	JOIN employee_details cemp ON cemp.email_id=trn.`created_by_email_id` "
			+ "	JOIN employee_details lemp ON lemp.email_id=trn.`last_modified_by_email_id` "
			+ "	JOIN employee_details oemp ON oemp.email_id=trn.`owner_email_id` "
			+ " JOIN bt_products btp ON btp.id=req_item.`bt_product_id` "
			+ " JOIN location dloc ON dloc.location_id=trn.`destination_location_location_id` where req.id=:productRequestId", nativeQuery = true,countQuery = "SELECT distinct count(*) as ct FROM `bt_product_transfer` trn \r\n"
			+ " JOIN bt_product_transfer_item trn_item on trn.id=trn_item.btp_transfer_id\r\n"
			+ " JOIN bt_product_request_item req_item on req_item.id=trn_item.btp_request_item_id\r\n"
			+ " JOIN `bt_product_request` req ON req_item.`bt_product_request_id`=req.`id`\r\n"
			+ " JOIN `location` sloc ON sloc.location_id=trn.`source_location_location_id`\r\n"
			+ "	JOIN employee_details cemp ON cemp.email_id=trn.`created_by_email_id` "
			+ "	JOIN employee_details lemp ON lemp.email_id=trn.`last_modified_by_email_id` "
			+ "	JOIN employee_details oemp ON oemp.email_id=trn.`owner_email_id` "
			+ " JOIN bt_products btp ON btp.id=req_item.`bt_product_id` "
			+ " JOIN location dloc ON dloc.location_id=trn.`destination_location_location_id` where req.id=:productRequestId")
	public Page<Map<String, Object>> getProductTransferByProductRequestId(
			@Param("productRequestId") Long productRequestId,Pageable page);

	@Query(value = "SELECT distinct \r\n" + " trn.`id` AS productTrnsferId,\r\n"
			+ " trn.`created_date` AS productTransferCreatedDate,\r\n"
			+ " trn.`last_modified_date` AS productTransferLastModifiedDate,\r\n"
			+ " trn.`deliv` AS productTransferSapDeliv,\r\n" + " trn.`invoice` AS productTransferSapInvocieNumber,\r\n"
			+ " trn.`mblnr_g` AS productTransferSapMblnrg,\r\n" + " trn.`mjahr_g` AS productTransferSapMjahrG,\r\n"
			+ " trn.`remark_d` AS productTransferRemarkD,\r\n" + " trn.`remark_g` AS productTraansferRemarkG,\r\n"
			+ " trn.`remark_i` AS productTransferRemarkI,\r\n"
			+ " trn.`actual_delivery_date` AS productTransferActualDeliveryDate,\r\n"
			+ " trn.`manual_id` AS productTransferManualId,\r\n"
			+ " trn.`received_date` AS productTransferReceivedDate,\r\n"
			+ " trn.`shipping_mode` AS productTransferShippingMode,\r\n"
			+ " trn.`shipping_to_name` AS productTransferShippingToName,\r\n"
			+ " trn.`tracking_number` AS productTranferTrackingNumber,\r\n"
			+ " trn.`created_by_email_id` AS productTransferCreatedByEmaiilId,\r\n"
			+ " trn.`last_modified_by_email_id` AS productTransferLastModifiedByEmailId,\r\n"
			+ "	trn.transfer_status AS productTransferStatus,"
			+ " trn.`total_quantity_issued` AS productTransferTotalQuantityIssued,"
			+ "	trn.`total_quantity_received` AS productTransferTotalQuantityReceived, "
			+ "	cemp.first_name AS productTransferCreatedByFirstName,"
			+ "	cemp.`middle_name` AS productTransferCreatedByMiddleName,"
			+ "	cemp.`last_name` AS productTransferCreatedByLastName,"
			+ "	lemp.first_name AS productTransferLastModifiedByFirstName,"
			+ "	lemp.`middle_name` AS productTransferLastModifiedByMiddleName,"
			+ "	lemp.`last_name` AS productTransferLastModifiedByLastName," 
			+ " oemp.first_name AS productTransferOwnerFirstName,"
			+ "	oemp.`middle_name` AS productTransferOwnerMiddleName,"
			+ "	oemp.`last_name` AS productTransferOwnerLastName," 
			+"	btp.`manual_id` AS productPartcode, "
			+"	btp.`product_type` AS productType, "
			+"	btp.`product_description` AS productDescription, "

			+" req.`id` AS productRequestId,\r\n" + " req.`manual_id` AS productRequestManualId,\r\n"
			+ " sloc.location_name AS productRequestSourceLocationName,\r\n"
			+ " dloc.location_name AS productRequestDestinationLocationName,\r\n"
			+ " sloc.location_id AS productRequestSourceLocationId,\r\n"
			+ " dloc.location_id AS productRequestDestinationLocationId\r\n" + " FROM `bt_product_transfer` trn \r\n"
			+ " JOIN bt_product_transfer_item trn_item on trn.id=trn_item.btp_transfer_id\r\n"
			+ " JOIN bt_product_request_item req_item on req_item.id=trn_item.btp_request_item_id\r\n"
			+ " JOIN `bt_product_request` req ON req_item.`bt_product_request_id`=req.`id`\r\n"
			+ " JOIN `location` sloc ON sloc.location_id=trn.`source_location_location_id`\r\n"
			+ "	JOIN employee_details cemp ON cemp.email_id=trn.`created_by_email_id` "
			+ "	JOIN employee_details lemp ON lemp.email_id=trn.`last_modified_by_email_id` "
			+ "	JOIN employee_details oemp ON oemp.email_id=trn.`owner_email_id` "
			+ "JOIN bt_products btp ON btp.id=req_item.`bt_product_id` "
		
			+ " JOIN location dloc ON dloc.location_id=trn.`destination_location_location_id` where req.id=:productRequestId and (dloc.location_id in :locationIds or sloc.location_id in :locationIds)", nativeQuery = true)
	public List<Map<String, Object>> getProductTransferByProductRequestId(
			@Param("productRequestId") Long productRequestId, @Param("locationIds") List<Long> locationIds);

			@Query(value = "SELECT distinct \r\n" + " trn.`id` AS productTrnsferId,\r\n"
			+ " trn.`created_date` AS productTransferCreatedDate,\r\n"
			+ " trn.`last_modified_date` AS productTransferLastModifiedDate,\r\n"
			+ " trn.`deliv` AS productTransferSapDeliv,\r\n" + " trn.`invoice` AS productTransferSapInvocieNumber,\r\n"
			+ " trn.`mblnr_g` AS productTransferSapMblnrg,\r\n" + " trn.`mjahr_g` AS productTransferSapMjahrG,\r\n"
			+ " trn.`remark_d` AS productTransferRemarkD,\r\n" + " trn.`remark_g` AS productTraansferRemarkG,\r\n"
			+ " trn.`remark_i` AS productTransferRemarkI,\r\n"
			+ " trn.`actual_delivery_date` AS productTransferActualDeliveryDate,\r\n"
			+ " trn.`manual_id` AS productTransferManualId,\r\n"
			+ " trn.`received_date` AS productTransferReceivedDate,\r\n"
			+ " trn.`shipping_mode` AS productTransferShippingMode,\r\n"
			+ " trn.`shipping_to_name` AS productTransferShippingToName,\r\n"
			+ " trn.`tracking_number` AS productTranferTrackingNumber,\r\n"
			+ " trn.`created_by_email_id` AS productTransferCreatedByEmaiilId,\r\n"
			+ " trn.`last_modified_by_email_id` AS productTransferLastModifiedByEmailId,\r\n"
			+ "	trn.transfer_status AS productTransferStatus,"
			+ " trn.`total_quantity_issued` AS productTransferTotalQuantityIssued,"
			+ "	trn.`total_quantity_received` AS productTransferTotalQuantityReceived, "
			+ "	cemp.first_name AS productTransferCreatedByFirstName,"
			+ "	cemp.`middle_name` AS productTransferCreatedByMiddleName,"
			+ "	cemp.`last_name` AS productTransferCreatedByLastName,"
			+ "	lemp.first_name AS productTransferLastModifiedByFirstName,"
			+ "	lemp.`middle_name` AS productTransferLastModifiedByMiddleName,"
			+ "	lemp.`last_name` AS productTransferLastModifiedByLastName," 
			+ " oemp.first_name AS productTransferOwnerFirstName,"
			+ "	oemp.`middle_name` AS productTransferOwnerMiddleName,"
			+ "	oemp.`last_name` AS productTransferOwnerLastName," 
			+"	btp.`manual_id` AS productPartcode, "
			+"	btp.`product_type` AS productType, "
			+"	btp.`product_description` AS productDescription, "

			+" req.`id` AS productRequestId,\r\n" + " req.`manual_id` AS productRequestManualId,\r\n"
			+ " sloc.location_name AS productRequestSourceLocationName,\r\n"
			+ " dloc.location_name AS productRequestDestinationLocationName,\r\n"
			+ " sloc.location_id AS productRequestSourceLocationId,\r\n"
			+ " dloc.location_id AS productRequestDestinationLocationId\r\n" + " FROM `bt_product_transfer` trn \r\n"
			+ " JOIN bt_product_transfer_item trn_item on trn.id=trn_item.btp_transfer_id\r\n"
			+ " JOIN bt_product_request_item req_item on req_item.id=trn_item.btp_request_item_id\r\n"
			+ " JOIN `bt_product_request` req ON req_item.`bt_product_request_id`=req.`id`\r\n"
			+ " JOIN `location` sloc ON sloc.location_id=trn.`source_location_location_id`\r\n"
			+ "	JOIN employee_details cemp ON cemp.email_id=trn.`created_by_email_id` "
			+ "	JOIN employee_details lemp ON lemp.email_id=trn.`last_modified_by_email_id` "
			+ "	JOIN employee_details oemp ON oemp.email_id=trn.`owner_email_id` "
			+ "JOIN bt_products btp ON btp.id=req_item.`bt_product_id` "
		
			+ " JOIN location dloc ON dloc.location_id=trn.`destination_location_location_id` where req.id=:productRequestId and (dloc.location_id in :locationIds or sloc.location_id in :locationIds)", nativeQuery = true,countQuery = "SELECT distinct count(*) as ct FROM `bt_product_transfer` trn \r\n"
			+ " JOIN bt_product_transfer_item trn_item on trn.id=trn_item.btp_transfer_id\r\n"
			+ " JOIN bt_product_request_item req_item on req_item.id=trn_item.btp_request_item_id\r\n"
			+ " JOIN `bt_product_request` req ON req_item.`bt_product_request_id`=req.`id`\r\n"
			+ " JOIN `location` sloc ON sloc.location_id=trn.`source_location_location_id`\r\n"
			+ "	JOIN employee_details cemp ON cemp.email_id=trn.`created_by_email_id` "
			+ "	JOIN employee_details lemp ON lemp.email_id=trn.`last_modified_by_email_id` "
			+ "	JOIN employee_details oemp ON oemp.email_id=trn.`owner_email_id` "
			+ "JOIN bt_products btp ON btp.id=req_item.`bt_product_id` "
		
			+ " JOIN location dloc ON dloc.location_id=trn.`destination_location_location_id` where req.id=:productRequestId and (dloc.location_id in :locationIds or sloc.location_id in :locationIds)")
	public Page<Map<String, Object>> getProductTransferByProductRequestId(
			@Param("productRequestId") Long productRequestId, @Param("locationIds") List<Long> locationIds,Pageable page);

	@Query(value = "SELECT 	req.id AS productRequestId,\r\n" + " req.manual_id AS productRequestManualId,\r\n"
			+ " item.id AS productRequestItemId,\r\n" + " item.manual_id AS productRequestItemManualId,\r\n"
			+ " item.`quantity` AS productRequestItemRequiredQty,\r\n"
			+ " item.`quantity_issued` AS productRequestItemIssuedQty,\r\n" + " prd.`manual_id` AS productManualId,\r\n"
			+ " prd.id AS productId,\r\n" + " prd.`product_type`\r\n" +

			" FROM `bt_product_request_item` item \r\n"
			+ " JOIN `bt_product_request` req ON req.id=item.`bt_product_request_id`\r\n"
			+ " JOIN bt_products prd ON prd.id=item.`bt_product_id` \r\n"
			+ " WHERE item.quantity_issued<item.quantity \r\n"
			+ " AND item.`item_status` NOT IN ('Draft','Short_Closed','Short Closed') \r\n"
			+ " AND req.`bt_product_request_status` NOT IN ('Draft','Short_Closed','Short Closed')\r\n"
			+ " AND req.`destination_location_location_id`=:destinationLocationId\r\n"
			+ " AND req.`source_location_location_id`=:sourceLocationId", nativeQuery = true)
	public List<Map<String, Object>> getPendingProductRequestForTransfer(
			@Param("sourceLocationId") Long sourceLocationId,
			@Param("destinationLocationId") Long destinationLocationId);

	@Query(value = "SELECT  \r\n" + "trn.`id` AS productTrnsferId,   \r\n"
			+ "trn.`created_date` AS productTransferCreatedDate,  \r\n"
			+ "trn.`last_modified_date` AS productTransferLastModifiedDate,   \r\n"
			+ "trn.`deliv` AS productTransferSapDeliv,   \r\n"
			+ "trn.`invoice` AS productTransferSapInvocieNumber,  \r\n"
			+ "trn.`mblnr_g` AS productTransferSapMblnrg,   \r\n" + "trn.`mjahr_g` AS productTransferSapMjahrG,   \r\n"
			+ "trn.`remark_d` AS productTransferRemarkD,   \r\n" + "trn.`remark_g` AS productTraansferRemarkG,   \r\n"
			+ "trn.`remark_i` AS productTransferRemarkI,   \r\n"
			+ "trn.`actual_delivery_date` AS productTransferActualDeliveryDate,   \r\n"
			+ "trn.`manual_id` AS productTransferManualId,   \r\n"
			+ "trn.`received_date` AS productTransferReceivedDate,  \r\n"
			+ "trn.`shipping_mode` AS productTransferShippingMode,   \r\n"
			+ "trn.`shipping_to_name` AS productTransferShippingToName,  \r\n"
			+ "trn.`tracking_number` AS productTranferTrackingNumber,   \r\n"
			+ "trn.`created_by_email_id` AS productTransferCreatedByEmaiilId,  \r\n"
			+ "trn.`last_modified_by_email_id` AS productTransferLastModifiedByEmailId, \r\n"
			+ "	trn.transfer_status AS productTransferStatus,"
			+ " trn.`total_quantity_issued` AS productTransferTotalQuantityIssued,"
			+ "	trn.`total_quantity_received` AS productTransferTotalQuantityReceived, "
			+ "	cemp.first_name AS productTransferCreatedByFirstName,"
			+ "	cemp.`middle_name` AS productTransferCreatedByMiddleName,"
			+ "	cemp.`last_name` AS productTransferCreatedByLastName,"
			+ "	lemp.first_name AS productTransferLastModifiedByFirstName,"
			+ "	lemp.`middle_name` AS productTransferLastModifiedByMiddleName,"
			+ "	lemp.`last_name` AS productTransferLastModifiedByLastName," +

			"	oemp.first_name AS productTransferOwnerFirstName,"
			+ "	oemp.`middle_name` AS productTransferOwnerMiddleName,"
			+ "	oemp.`last_name` AS productTransferOwnerLastName," +

			"sloc.location_name AS productRequestSourceLocationName,   \r\n"
			+ "dloc.location_name AS productRequestDestinationLocationName, \r\n"
			+ "sloc.location_id AS productRequestSourceLocationId,   \r\n"
			+ "dloc.location_id AS productRequestDestinationLocationId   \r\n"
			+ "FROM `bt_product_transfer` trn    \r\n"
			+ "	JOIN employee_details cemp ON cemp.email_id=trn.`created_by_email_id` "
			+ "	JOIN employee_details lemp ON lemp.email_id=trn.`last_modified_by_email_id` "
			+ "	JOIN employee_details oemp ON oemp.email_id=trn.`owner_email_id` "
			+ "JOIN `location` sloc ON sloc.location_id=trn.`source_location_location_id`   \r\n"
			+ " JOIN location dloc ON dloc.location_id=trn.`destination_location_location_id` where trn.id=:productTransferId", nativeQuery = true)
	public Map<String, Object> getById(@Param("productTransferId") Long productTransferId);

	@Query(value = "SELECT  \r\n" + "trn.`id` AS productTrnsferId,   \r\n"
			+ "trn.`created_date` AS productTransferCreatedDate,  \r\n"
			+ "trn.`last_modified_date` AS productTransferLastModifiedDate,   \r\n"
			+ "trn.`deliv` AS productTransferSapDeliv,   \r\n"
			+ "trn.`invoice` AS productTransferSapInvocieNumber,  \r\n"
			+ "trn.`mblnr_g` AS productTransferSapMblnrg,   \r\n" + "trn.`mjahr_g` AS productTransferSapMjahrG,   \r\n"
			+ "trn.`remark_d` AS productTransferRemarkD,   \r\n" + "trn.`remark_g` AS productTraansferRemarkG,   \r\n"
			+ "trn.`remark_i` AS productTransferRemarkI,   \r\n"
			+ "trn.`actual_delivery_date` AS productTransferActualDeliveryDate,   \r\n"
			+ "trn.`manual_id` AS productTransferManualId,   \r\n"
			+ "trn.`received_date` AS productTransferReceivedDate,  \r\n"
			+ "trn.`shipping_mode` AS productTransferShippingMode,   \r\n"
			+ "trn.`shipping_to_name` AS productTransferShippingToName,  \r\n"
			+ "trn.`tracking_number` AS productTranferTrackingNumber,   \r\n"
			+ "trn.`created_by_email_id` AS productTransferCreatedByEmaiilId,  \r\n"
			+ "trn.`last_modified_by_email_id` AS productTransferLastModifiedByEmailId, \r\n"
			+ "	trn.transfer_status AS productTransferStatus,"
			+ " trn.`total_quantity_issued` AS productTransferTotalQuantityIssued,"
			+ "	trn.`total_quantity_received` AS productTransferTotalQuantityReceived, "
			+ "	cemp.first_name AS productTransferCreatedByFirstName,"
			+ "	cemp.`middle_name` AS productTransferCreatedByMiddleName,"
			+ "	cemp.`last_name` AS productTransferCreatedByLastName,"
			+ "	lemp.first_name AS productTransferLastModifiedByFirstName,"
			+ "	lemp.`middle_name` AS productTransferLastModifiedByMiddleName,"
			+ "	lemp.`last_name` AS productTransferLastModifiedByLastName," +

			"	oemp.first_name AS productTransferOwnerFirstName,"
			+ "	oemp.`middle_name` AS productTransferOwnerMiddleName,"
			+ "	oemp.`last_name` AS productTransferOwnerLastName," +

			"sloc.location_name AS productRequestSourceLocationName,   \r\n"
			+ "dloc.location_name AS productRequestDestinationLocationName, \r\n"
			+ "sloc.location_id AS productRequestSourceLocationId,   \r\n"
			+ "dloc.location_id AS productRequestDestinationLocationId   \r\n"
			+ "FROM `bt_product_transfer` trn    \r\n"
			+ "	JOIN employee_details cemp ON cemp.email_id=trn.`created_by_email_id` "
			+ "	JOIN employee_details lemp ON lemp.email_id=trn.`last_modified_by_email_id` "
			+ "	JOIN employee_details oemp ON oemp.email_id=trn.`owner_email_id` "
			+ "JOIN `location` sloc ON sloc.location_id=trn.`source_location_location_id`   \r\n"
			+ " JOIN location dloc ON dloc.location_id=trn.`destination_location_location_id` where trn.id=:productTransferId and (dloc.location_id in :locationIds or sloc.location_id in :locationIds)", nativeQuery = true)
	public Map<String, Object> getById(@Param("productTransferId") Long productTransferId,
			@Param("locationIds") List<Long> locationIds);

	@Query(value = "SELECT  \r\n" + "trn.`id` AS productTrnsferId,   \r\n"
			+ "trn.`created_date` AS productTransferCreatedDate,  \r\n"
			+ "trn.`last_modified_date` AS productTransferLastModifiedDate,   \r\n"
			+ "trn.`deliv` AS productTransferSapDeliv,   \r\n"
			+ "trn.`invoice` AS productTransferSapInvocieNumber,  \r\n"
			+ "trn.`mblnr_g` AS productTransferSapMblnrg,   \r\n" + "trn.`mjahr_g` AS productTransferSapMjahrG,   \r\n"
			+ "trn.`remark_d` AS productTransferRemarkD,   \r\n" + "trn.`remark_g` AS productTraansferRemarkG,   \r\n"
			+ "trn.`remark_i` AS productTransferRemarkI,   \r\n"
			+ "trn.`actual_delivery_date` AS productTransferActualDeliveryDate,   \r\n"
			+ "trn.`manual_id` AS productTransferManualId,   \r\n"
			+ "trn.`received_date` AS productTransferReceivedDate,  \r\n"
			+ "trn.`shipping_mode` AS productTransferShippingMode,   \r\n"
			+ "trn.`shipping_to_name` AS productTransferShippingToName,  \r\n"
			+ "trn.`tracking_number` AS productTranferTrackingNumber,   \r\n"
			+ "trn.`created_by_email_id` AS productTransferCreatedByEmaiilId,  \r\n"
			+ "trn.`last_modified_by_email_id` AS productTransferLastModifiedByEmailId, \r\n"
			+ "	trn.transfer_status AS productTransferStatus,"
			+ " trn.`total_quantity_issued` AS productTransferTotalQuantityIssued,"
			+ "	trn.`total_quantity_received` AS productTransferTotalQuantityReceived, "
			+ "	cemp.first_name AS productTransferCreatedByFirstName,"
			+ "	cemp.`middle_name` AS productTransferCreatedByMiddleName,"
			+ "	cemp.`last_name` AS productTransferCreatedByLastName,"
			+ "	lemp.first_name AS productTransferLastModifiedByFirstName,"
			+ "	lemp.`middle_name` AS productTransferLastModifiedByMiddleName,"
			+ "	lemp.`last_name` AS productTransferLastModifiedByLastName," +

			"	oemp.first_name AS productTransferOwnerFirstName,"
			+ "	oemp.`middle_name` AS productTransferOwnerMiddleName,"
			+ "	oemp.`last_name` AS productTransferOwnerLastName," +

			"sloc.location_name AS productRequestSourceLocationName,   \r\n"
			+ "dloc.location_name AS productRequestDestinationLocationName, \r\n"
			+ "sloc.location_id AS productRequestSourceLocationId,   \r\n"
			+ "dloc.location_id AS productRequestDestinationLocationId   \r\n"
			+ "FROM `bt_product_transfer` trn    \r\n"
			+ "	JOIN employee_details cemp ON cemp.email_id=trn.`created_by_email_id` "
			+ "	JOIN employee_details lemp ON lemp.email_id=trn.`last_modified_by_email_id` "
			+ "	JOIN employee_details oemp ON oemp.email_id=trn.`owner_email_id` "
			+ "JOIN `location` sloc ON sloc.location_id=trn.`source_location_location_id`   \r\n"
			+ " JOIN location dloc ON dloc.location_id=trn.`destination_location_location_id` where  (trn.manual_id like :searchkey or trn.invoice :searchkey or trn.shipping_mode like :searchkey or trn.shipping_to_name like :searchkey or trn.tracking_number like :searchkey or sloc.location_name like :searchkey or dloc.location_name like :searchkey)", nativeQuery = true, countQuery = "SELECT count(*) as ct \r\n"
					+

					"FROM `bt_product_transfer` trn    \r\n"
					+ "	JOIN employee_details cemp ON cemp.email_id=trn.`created_by_email_id` "
					+ "	JOIN employee_details lemp ON lemp.email_id=trn.`last_modified_by_email_id` "
					+ "	JOIN employee_details oemp ON oemp.email_id=trn.`owner_email_id` "
					+ "JOIN `location` sloc ON sloc.location_id=trn.`source_location_location_id`   \r\n"
					+ " JOIN location dloc ON dloc.location_id=trn.`destination_location_location_id` where  (trn.manual_id like :searchkey or trn.invoice :searchkey or trn.shipping_mode like :searchkey or trn.shipping_to_name like :searchkey or trn.tracking_number like :searchkey or sloc.location_name like :searchkey or dloc.location_name like :searchkey)")
	public Page<Map<String, Object>> searchProductTransferForList(@Param("searchkey") String searchkey, Pageable page);

	@Query(value = "SELECT  \r\n" + "trn.`id` AS productTrnsferId,   \r\n"
			+ "trn.`created_date` AS productTransferCreatedDate,  \r\n"
			+ "trn.`last_modified_date` AS productTransferLastModifiedDate,   \r\n"
			+ "trn.`deliv` AS productTransferSapDeliv,   \r\n"
			+ "trn.`invoice` AS productTransferSapInvocieNumber,  \r\n"
			+ "trn.`mblnr_g` AS productTransferSapMblnrg,   \r\n" + "trn.`mjahr_g` AS productTransferSapMjahrG,   \r\n"
			+ "trn.`remark_d` AS productTransferRemarkD,   \r\n" + "trn.`remark_g` AS productTraansferRemarkG,   \r\n"
			+ "trn.`remark_i` AS productTransferRemarkI,   \r\n"
			+ "trn.`actual_delivery_date` AS productTransferActualDeliveryDate,   \r\n"
			+ "trn.`manual_id` AS productTransferManualId,   \r\n"
			+ "trn.`received_date` AS productTransferReceivedDate,  \r\n"
			+ "trn.`shipping_mode` AS productTransferShippingMode,   \r\n"
			+ "trn.`shipping_to_name` AS productTransferShippingToName,  \r\n"
			+ "trn.`tracking_number` AS productTranferTrackingNumber,   \r\n"
			+ "trn.`created_by_email_id` AS productTransferCreatedByEmaiilId,  \r\n"
			+ "trn.`last_modified_by_email_id` AS productTransferLastModifiedByEmailId, \r\n"
			+ "	trn.transfer_status AS productTransferStatus,"
			+ " trn.`total_quantity_issued` AS productTransferTotalQuantityIssued,"
			+ "	trn.`total_quantity_received` AS productTransferTotalQuantityReceived, "
			+ "	cemp.first_name AS productTransferCreatedByFirstName,"
			+ "	cemp.`middle_name` AS productTransferCreatedByMiddleName,"
			+ "	cemp.`last_name` AS productTransferCreatedByLastName,"
			+ "	lemp.first_name AS productTransferLastModifiedByFirstName,"
			+ "	lemp.`middle_name` AS productTransferLastModifiedByMiddleName,"
			+ "	lemp.`last_name` AS productTransferLastModifiedByLastName," +

			"	oemp.first_name AS productTransferOwnerFirstName,"
			+ "	oemp.`middle_name` AS productTransferOwnerMiddleName,"
			+ "	oemp.`last_name` AS productTransferOwnerLastName," +

			"sloc.location_name AS productRequestSourceLocationName,   \r\n"
			+ "dloc.location_name AS productRequestDestinationLocationName, \r\n"
			+ "sloc.location_id AS productRequestSourceLocationId,   \r\n"
			+ "dloc.location_id AS productRequestDestinationLocationId   \r\n"
			+ "FROM `bt_product_transfer` trn    \r\n"
			+ "	JOIN employee_details cemp ON cemp.email_id=trn.`created_by_email_id` "
			+ "	JOIN employee_details lemp ON lemp.email_id=trn.`last_modified_by_email_id` "
			+ "	JOIN employee_details oemp ON oemp.email_id=trn.`owner_email_id` "
			+ "JOIN `location` sloc ON sloc.location_id=trn.`source_location_location_id`   \r\n"
			+ " JOIN location dloc ON dloc.location_id=trn.`destination_location_location_id` where  (trn.manual_id like :searchkey or trn.invoice :searchkey or trn.shipping_mode like :searchkey or trn.shipping_to_name like :searchkey or trn.tracking_number like :searchkey or sloc.location_name like :searchkey or dloc.location_name like :searchkey)", nativeQuery = true)
	public List<Map<String, Object>> searchProductTransferForList(@Param("searchkey") String searchkey);

	@Query(value = "SELECT  \r\n" + "trn.`id` AS productTrnsferId,   \r\n"
			+ "trn.`created_date` AS productTransferCreatedDate,  \r\n"
			+ "trn.`last_modified_date` AS productTransferLastModifiedDate,   \r\n"
			+ "trn.`deliv` AS productTransferSapDeliv,   \r\n"
			+ "trn.`invoice` AS productTransferSapInvocieNumber,  \r\n"
			+ "trn.`mblnr_g` AS productTransferSapMblnrg,   \r\n" + "trn.`mjahr_g` AS productTransferSapMjahrG,   \r\n"
			+ "trn.`remark_d` AS productTransferRemarkD,   \r\n" + "trn.`remark_g` AS productTraansferRemarkG,   \r\n"
			+ "trn.`remark_i` AS productTransferRemarkI,   \r\n"
			+ "trn.`actual_delivery_date` AS productTransferActualDeliveryDate,   \r\n"
			+ "trn.`manual_id` AS productTransferManualId,   \r\n"
			+ "trn.`received_date` AS productTransferReceivedDate,  \r\n"
			+ "trn.`shipping_mode` AS productTransferShippingMode,   \r\n"
			+ "trn.`shipping_to_name` AS productTransferShippingToName,  \r\n"
			+ "trn.`tracking_number` AS productTranferTrackingNumber,   \r\n"
			+ "trn.`created_by_email_id` AS productTransferCreatedByEmaiilId,  \r\n"
			+ "trn.`last_modified_by_email_id` AS productTransferLastModifiedByEmailId, \r\n"
			+ "	trn.transfer_status AS productTransferStatus,"
			+ " trn.`total_quantity_issued` AS productTransferTotalQuantityIssued,"
			+ "	trn.`total_quantity_received` AS productTransferTotalQuantityReceived, "
			+ "	cemp.first_name AS productTransferCreatedByFirstName,"
			+ "	cemp.`middle_name` AS productTransferCreatedByMiddleName,"
			+ "	cemp.`last_name` AS productTransferCreatedByLastName,"
			+ "	lemp.first_name AS productTransferLastModifiedByFirstName,"
			+ "	lemp.`middle_name` AS productTransferLastModifiedByMiddleName,"
			+ "	lemp.`last_name` AS productTransferLastModifiedByLastName," +

			"	oemp.first_name AS productTransferOwnerFirstName,"
			+ "	oemp.`middle_name` AS productTransferOwnerMiddleName,"
			+ "	oemp.`last_name` AS productTransferOwnerLastName," +

			"sloc.location_name AS productRequestSourceLocationName,   \r\n"
			+ "dloc.location_name AS productRequestDestinationLocationName, \r\n"
			+ "sloc.location_id AS productRequestSourceLocationId,   \r\n"
			+ "dloc.location_id AS productRequestDestinationLocationId   \r\n"
			+ "FROM `bt_product_transfer` trn    \r\n"
			+ "	JOIN employee_details cemp ON cemp.email_id=trn.`created_by_email_id` "
			+ "	JOIN employee_details lemp ON lemp.email_id=trn.`last_modified_by_email_id` "
			+ "	JOIN employee_details oemp ON oemp.email_id=trn.`owner_email_id` "
			+ "JOIN `location` sloc ON sloc.location_id=trn.`source_location_location_id`   \r\n"
			+ " JOIN location dloc ON dloc.location_id=trn.`destination_location_location_id` where (dloc.location_id in :locationIds or sloc.location_id in :locationIds) and (trn.manual_id like :searchkey or trn.invoice :searchkey or trn.shipping_mode like :searchkey or trn.shipping_to_name like :searchkey or trn.tracking_number like :searchkey or sloc.location_name like :searchkey or dloc.location_name like :searchkey)", nativeQuery = true, countQuery = "SELECT  count(*) as ct \r\n"
					+

					"FROM `bt_product_transfer` trn    \r\n"
					+ "	JOIN employee_details cemp ON cemp.email_id=trn.`created_by_email_id` "
					+ "	JOIN employee_details lemp ON lemp.email_id=trn.`last_modified_by_email_id` "
					+ "	JOIN employee_details oemp ON oemp.email_id=trn.`owner_email_id` "
					+ "JOIN `location` sloc ON sloc.location_id=trn.`source_location_location_id`   \r\n"
					+ " JOIN location dloc ON dloc.location_id=trn.`destination_location_location_id` where (dloc.location_id in :locationIds or sloc.location_id in :locationIds) and (trn.manual_id like :searchkey or trn.invoice :searchkey or trn.shipping_mode like :searchkey or trn.shipping_to_name like :searchkey or trn.tracking_number like :searchkey or sloc.location_name like :searchkey or dloc.location_name like :searchkey)")
	public Page<Map<String, Object>> searchProductTransferForList(@Param("locationIds") List<Long> locationIds,
			@Param("searchkey") String searchkey, Pageable page);

	@Query(value = "SELECT  \r\n" + "trn.`id` AS productTrnsferId,   \r\n"
			+ "trn.`created_date` AS productTransferCreatedDate,  \r\n"
			+ "trn.`last_modified_date` AS productTransferLastModifiedDate,   \r\n"
			+ "trn.`deliv` AS productTransferSapDeliv,   \r\n"
			+ "trn.`invoice` AS productTransferSapInvocieNumber,  \r\n"
			+ "trn.`mblnr_g` AS productTransferSapMblnrg,   \r\n" + "trn.`mjahr_g` AS productTransferSapMjahrG,   \r\n"
			+ "trn.`remark_d` AS productTransferRemarkD,   \r\n" + "trn.`remark_g` AS productTraansferRemarkG,   \r\n"
			+ "trn.`remark_i` AS productTransferRemarkI,   \r\n"
			+ "trn.`actual_delivery_date` AS productTransferActualDeliveryDate,   \r\n"
			+ "trn.`manual_id` AS productTransferManualId,   \r\n"
			+ "trn.`received_date` AS productTransferReceivedDate,  \r\n"
			+ "trn.`shipping_mode` AS productTransferShippingMode,   \r\n"
			+ "trn.`shipping_to_name` AS productTransferShippingToName,  \r\n"
			+ "trn.`tracking_number` AS productTranferTrackingNumber,   \r\n"
			+ "trn.`created_by_email_id` AS productTransferCreatedByEmaiilId,  \r\n"
			+ "trn.`last_modified_by_email_id` AS productTransferLastModifiedByEmailId, \r\n"
			+ "	trn.transfer_status AS productTransferStatus,"
			+ " trn.`total_quantity_issued` AS productTransferTotalQuantityIssued,"
			+ "	trn.`total_quantity_received` AS productTransferTotalQuantityReceived, "
			+ "	cemp.first_name AS productTransferCreatedByFirstName,"
			+ "	cemp.`middle_name` AS productTransferCreatedByMiddleName,"
			+ "	cemp.`last_name` AS productTransferCreatedByLastName,"
			+ "	lemp.first_name AS productTransferLastModifiedByFirstName,"
			+ "	lemp.`middle_name` AS productTransferLastModifiedByMiddleName,"
			+ "	lemp.`last_name` AS productTransferLastModifiedByLastName," +

			"	oemp.first_name AS productTransferOwnerFirstName,"
			+ "	oemp.`middle_name` AS productTransferOwnerMiddleName,"
			+ "	oemp.`last_name` AS productTransferOwnerLastName,"
			+ "sloc.location_name AS productRequestSourceLocationName,   \r\n"
			+ "dloc.location_name AS productRequestDestinationLocationName, \r\n"
			+ "sloc.location_id AS productRequestSourceLocationId,   \r\n"
			+ "dloc.location_id AS productRequestDestinationLocationId   \r\n"
			+ "FROM `bt_product_transfer` trn    \r\n"
			+ "	JOIN employee_details cemp ON cemp.email_id=trn.`created_by_email_id` "
			+ "	JOIN employee_details lemp ON lemp.email_id=trn.`last_modified_by_email_id` "
			+ "	JOIN employee_details oemp ON oemp.email_id=trn.`owner_email_id` "
			+ "JOIN `location` sloc ON sloc.location_id=trn.`source_location_location_id`   \r\n"
			+ " JOIN location dloc ON dloc.location_id=trn.`destination_location_location_id` where (dloc.location_id in :locationIds or sloc.location_id in :locationIds) and (trn.manual_id like :searchkey or trn.invoice :searchkey or trn.shipping_mode like :searchkey or trn.shipping_to_name like :searchkey or trn.tracking_number like :searchkey or sloc.location_name like :searchkey or dloc.location_name like :searchkey)", nativeQuery = true)
	public List<Map<String, Object>> searchProductTransferForList(@Param("locationIds") List<Long> locationIds,
			@Param("searchkey") String searchkey);

	@Query(value = "SELECT DISTINCT loc.location_id AS locationId,loc.location_name AS locationName, loc.plant AS plantCode FROM `bt_product_request` req JOIN location loc ON req.`destination_location_location_id`=loc.location_id WHERE req.`total_quantity_required`>req.`total_quantity_issued` AND req.`bt_product_request_status` NOT IN ('Draft','Archived','Short Closed','Short_Closed') and req.source_location_location_id=:sourceLocationId", nativeQuery = true)
	List<Map<String, Object>> getPndingLocationListForTransfer(@Param("sourceLocationId") Long sourceLocationId);
}
