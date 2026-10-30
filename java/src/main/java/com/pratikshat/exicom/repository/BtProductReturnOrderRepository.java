package com.pratikshat.exicom.repository;

import java.util.List;
import java.util.Map;

import com.pratikshat.exicom.entity.BtProductReturnOrder;
import com.pratikshat.exicom.repository.custom.BtProductReturnOrderRepositoryCustom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BtProductReturnOrderRepository extends JpaRepository<BtProductReturnOrder,Long>,BtProductReturnOrderRepositoryCustom {

	@Query("select rot from BtProductReturnOrder rot join rot.source sloc join sloc.legalEntity slg join rot.destination dloc join dloc.legalEntity dlg where rot.id=:id")
	BtProductReturnOrder getById(@Param("id") Long id);
    
    @Query(value="SELECT "
	+" ro.`id` AS id, "
	+" ro.`created_date` AS createdDate, "
	+" ro.`last_modified_date` AS returnOrderLastModifiedDate, "
	+" ro.`actual_send_date` AS returnOrderActualSendDate, "
	+" ro.`manual_id` AS returnOrderManualId, "
	+" ro.`return_order_status` AS returnOrderStatus, "
	+" ro.`ship_to_name` AS returnOrderShipToName, "
	+" ro.`shipping_mode` AS returnOrderShippingName, "
	+" ro.`total_quantity_received` AS returnOrderTotalQuantityReceived, "
	+" ro.`total_quantity_return` AS returnOrderTotalQuantityReturned, "
	+" ro.`tracking_number` AS returnOrderTrackingNumber, "

	+" sloc.location_id AS returnOrderSourceLocationId, "
	+" sloc.location_name AS returnOrderSourceLocationName, "
	
	+" dloc.location_id AS returnOrderDestinationLocationId, "
	+" dloc.location_name AS returnOrderDestinationalLocationName, "
	
	+" cremp.`first_name` AS returnOrderCreatedByFirstName, "
	+" cremp.`last_name` AS returnOrderCreatedByLastName, "
	+" cremp.`middle_name` AS returnOrderCreatedByMiddleName, "
	
	+" lsemp.`first_name` AS returnOrderLastModifiedByFirstName, "
	+" lsemp.`last_name` AS returnOrderLastModifiedByLastName, "
	+" lsemp.`middle_name` AS returnOrderLastModifiedMiddleName, "
	
	+" oemp.first_name AS returnOrderOwnerFirstName, "
	+" oemp.last_name AS returnOrderownerLastName, "
	+" oemp.middle_name AS returnOrderOwnerMiddleName "
	
	
	+" FROM `bt_product_return_order` ro "
	+" JOIN location sloc ON ro.`source_location_id` = sloc.location_id "
	+" JOIN location dloc ON ro.`destination_location_id` =dloc.location_id "
	+" JOIN employee_details cremp ON cremp.email_id=ro.`created_by_email_id` "
	+" JOIN employee_details lsemp ON lsemp.email_id=ro.`last_modified_by_email_id` "
	+" JOIN employee_details oemp ON oemp.email_id=ro.`owner_email_id`",nativeQuery = true,countQuery = "SELECT count(*) as ct"
	
	+" FROM `bt_product_return_order` ro "
	+" JOIN location sloc ON ro.`source_location_id` = sloc.location_id "
	+" JOIN location dloc ON ro.`destination_location_id` =dloc.location_id "
	+" JOIN employee_details cremp ON cremp.email_id=ro.`created_by_email_id` "
	+" JOIN employee_details lsemp ON lsemp.email_id=ro.`last_modified_by_email_id` "
	+" JOIN employee_details oemp ON oemp.email_id=ro.`owner_email_id`")
    public Page<Map<String,Object>> getPage(Pageable page);

	@Query(value="SELECT "
	+" ro.`id` AS id, "
	+" ro.`created_date` AS createdDate, "
	+" ro.`last_modified_date` AS returnOrderLastModifiedDate, "
	+" ro.`actual_send_date` AS returnOrderActualSendDate, "
	+" ro.`manual_id` AS returnOrderManualId, "
	+" ro.`return_order_status` AS returnOrderStatus, "
	+" ro.`ship_to_name` AS returnOrderShipToName, "
	+" ro.`shipping_mode` AS returnOrderShippingName, "
	+" ro.`total_quantity_received` AS returnOrderTotalQuantityReceived, "
	+" ro.`total_quantity_return` AS returnOrderTotalQuantityReturned, "
	+" ro.`tracking_number` AS returnOrderTrackingNumber, "

	+" sloc.location_id AS returnOrderSourceLocationId, "
	+" sloc.location_name AS returnOrderSourceLocationName, "
	
	+" dloc.location_id AS returnOrderDestinationLocationId, "
	+" dloc.location_name AS returnOrderDestinationalLocationName, "
	
	+" cremp.`first_name` AS returnOrderCreatedByFirstName, "
	+" cremp.`last_name` AS returnOrderCreatedByLastName, "
	+" cremp.`middle_name` AS returnOrderCreatedByMiddleName, "
	
	+" lsemp.`first_name` AS returnOrderLastModifiedByFirstName, "
	+" lsemp.`last_name` AS returnOrderLastModifiedByLastName, "
	+" lsemp.`middle_name` AS returnOrderLastModifiedMiddleName, "
	
	+" oemp.first_name AS returnOrderOwnerFirstName, "
	+" oemp.last_name AS returnOrderownerLastName, "
	+" oemp.middle_name AS returnOrderOwnerMiddleName "
	
	
	+" FROM `bt_product_return_order` ro "
	+" JOIN location sloc ON ro.`source_location_id` = sloc.location_id "
	+" JOIN location dloc ON ro.`destination_location_id` =dloc.location_id "
	+" JOIN employee_details cremp ON cremp.email_id=ro.`created_by_email_id` "
	+" JOIN employee_details lsemp ON lsemp.email_id=ro.`last_modified_by_email_id` "
	+" JOIN employee_details oemp ON oemp.email_id=ro.`owner_email_id` where ro.return_order_status like :statusof",nativeQuery = true,countQuery = "SELECT count(*) as ct"
	
	+" FROM `bt_product_return_order` ro "
	+" JOIN location sloc ON ro.`source_location_id` = sloc.location_id "
	+" JOIN location dloc ON ro.`destination_location_id` =dloc.location_id "
	+" JOIN employee_details cremp ON cremp.email_id=ro.`created_by_email_id` "
	+" JOIN employee_details lsemp ON lsemp.email_id=ro.`last_modified_by_email_id` "
	+" JOIN employee_details oemp ON oemp.email_id=ro.`owner_email_id` where ro.return_order_status like :statusof")
    public Page<Map<String,Object>> getPageByStatus(@Param("statusof")String statusof,Pageable page);

    @Query(value="SELECT "
	+" ro.`id` AS id, "
	+" ro.`created_date` AS createdDate, "
	+" ro.`last_modified_date` AS returnOrderLastModifiedDate, "
	+" ro.`actual_send_date` AS returnOrderActualSendDate, "
	+" ro.`manual_id` AS returnOrderManualId, "
	+" ro.`return_order_status` AS returnOrderStatus, "
	+" ro.`ship_to_name` AS returnOrderShipToName, "
	+" ro.`shipping_mode` AS returnOrderShippingName, "
	+" ro.`total_quantity_received` AS returnOrderTotalQuantityReceived, "
	+" ro.`total_quantity_return` AS returnOrderTotalQuantityReturned, "
	+" ro.`tracking_number` AS returnOrderTrackingNumber, "

	+" sloc.location_id AS returnOrderSourceLocationId, "
	+" sloc.location_name AS returnOrderSourceLocationName, "
	
	+" dloc.location_id AS returnOrderDestinationLocationId, "
	+" dloc.location_name AS returnOrderDestinationalLocationName, "
	
	+" cremp.`first_name` AS returnOrderCreatedByFirstName, "
	+" cremp.`last_name` AS returnOrderCreatedByLastName, "
	+" cremp.`middle_name` AS returnOrderCreatedByMiddleName, "
	
	+" lsemp.`first_name` AS returnOrderLastModifiedByFirstName, "
	+" lsemp.`last_name` AS returnOrderLastModifiedByLastName, "
	+" lsemp.`middle_name` AS returnOrderLastModifiedMiddleName, "
	
	+" oemp.first_name AS returnOrderOwnerFirstName, "
	+" oemp.last_name AS returnOrderownerLastName, "
	+" oemp.middle_name AS returnOrderOwnerMiddleName "
	
	
	+" FROM `bt_product_return_order` ro "
	+" JOIN location sloc ON ro.`source_location_id` = sloc.location_id "
	+" JOIN location dloc ON ro.`destination_location_id` =dloc.location_id "
	+" JOIN employee_details cremp ON cremp.email_id=ro.`created_by_email_id` "
	+" JOIN employee_details lsemp ON lsemp.email_id=ro.`last_modified_by_email_id` "
	+" JOIN employee_details oemp ON oemp.email_id=ro.`owner_email_id` where dloc.location_id in :locationIds or sloc.location_id in :locationIds",nativeQuery = true,countQuery = "SELECT count(*) as ct"
	
	+" FROM `bt_product_return_order` ro "
	+" JOIN location sloc ON ro.`source_location_id` = sloc.location_id "
	+" JOIN location dloc ON ro.`destination_location_id` =dloc.location_id "
	+" JOIN employee_details cremp ON cremp.email_id=ro.`created_by_email_id` "
	+" JOIN employee_details lsemp ON lsemp.email_id=ro.`last_modified_by_email_id` "
	+" JOIN employee_details oemp ON oemp.email_id=ro.`owner_email_id` where dloc.location_id in :locationIds or sloc.location_id in :locationIds")
    public Page<Map<String,Object>> getPage(@Param("locationIds") List<Long>locationIds,Pageable page);

	@Query(value="SELECT "
	+" ro.`id` AS id, "
	+" ro.`created_date` AS createdDate, "
	+" ro.`last_modified_date` AS returnOrderLastModifiedDate, "
	+" ro.`actual_send_date` AS returnOrderActualSendDate, "
	+" ro.`manual_id` AS returnOrderManualId, "
	+" ro.`return_order_status` AS returnOrderStatus, "
	+" ro.`ship_to_name` AS returnOrderShipToName, "
	+" ro.`shipping_mode` AS returnOrderShippingName, "
	+" ro.`total_quantity_received` AS returnOrderTotalQuantityReceived, "
	+" ro.`total_quantity_return` AS returnOrderTotalQuantityReturned, "
	+" ro.`tracking_number` AS returnOrderTrackingNumber, "

	+" sloc.location_id AS returnOrderSourceLocationId, "
	+" sloc.location_name AS returnOrderSourceLocationName, "
	
	+" dloc.location_id AS returnOrderDestinationLocationId, "
	+" dloc.location_name AS returnOrderDestinationalLocationName, "
	
	+" cremp.`first_name` AS returnOrderCreatedByFirstName, "
	+" cremp.`last_name` AS returnOrderCreatedByLastName, "
	+" cremp.`middle_name` AS returnOrderCreatedByMiddleName, "
	
	+" lsemp.`first_name` AS returnOrderLastModifiedByFirstName, "
	+" lsemp.`last_name` AS returnOrderLastModifiedByLastName, "
	+" lsemp.`middle_name` AS returnOrderLastModifiedMiddleName, "
	
	+" oemp.first_name AS returnOrderOwnerFirstName, "
	+" oemp.last_name AS returnOrderownerLastName, "
	+" oemp.middle_name AS returnOrderOwnerMiddleName "
	
	
	+" FROM `bt_product_return_order` ro "
	+" JOIN location sloc ON ro.`source_location_id` = sloc.location_id "
	+" JOIN location dloc ON ro.`destination_location_id` =dloc.location_id "
	+" JOIN employee_details cremp ON cremp.email_id=ro.`created_by_email_id` "
	+" JOIN employee_details lsemp ON lsemp.email_id=ro.`last_modified_by_email_id` "
	+" JOIN employee_details oemp ON oemp.email_id=ro.`owner_email_id` where ro.return_order_status like :statusof and (dloc.location_id in :locationIds or sloc.location_id in :locationIds)",nativeQuery = true,countQuery = "SELECT count(*) as ct"
	
	+" FROM `bt_product_return_order` ro "
	+" JOIN location sloc ON ro.`source_location_id` = sloc.location_id "
	+" JOIN location dloc ON ro.`destination_location_id` =dloc.location_id "
	+" JOIN employee_details cremp ON cremp.email_id=ro.`created_by_email_id` "
	+" JOIN employee_details lsemp ON lsemp.email_id=ro.`last_modified_by_email_id` "
	+" JOIN employee_details oemp ON oemp.email_id=ro.`owner_email_id` where ro.return_order_status like :statusof and (dloc.location_id in :locationIds or sloc.location_id in :locationIds)")
    public Page<Map<String,Object>> getPageByStatus(@Param("statusof")String status,@Param("locationIds") List<Long>locationIds,Pageable page);


	@Query(value="SELECT "
	+" ro.`id` AS id, "
	+" ro.`created_date` AS createdDate, "
	+" ro.`last_modified_date` AS returnOrderLastModifiedDate, "
	+" ro.`actual_send_date` AS returnOrderActualSendDate, "
	+" ro.`manual_id` AS returnOrderManualId, "
	+" ro.`return_order_status` AS returnOrderStatus, "
	+" ro.`ship_to_name` AS returnOrderShipToName, "
	+" ro.`shipping_mode` AS returnOrderShippingName, "
	+" ro.`total_quantity_received` AS returnOrderTotalQuantityReceived, "
	+" ro.`total_quantity_return` AS returnOrderTotalQuantityReturned, "
	+" ro.`tracking_number` AS returnOrderTrackingNumber, "

	+" sloc.location_id AS returnOrderSourceLocationId, "
	+" sloc.location_name AS returnOrderSourceLocationName, "
	
	+" dloc.location_id AS returnOrderDestinationLocationId, "
	+" dloc.location_name AS returnOrderDestinationalLocationName, "
	
	+" cremp.`first_name` AS returnOrderCreatedByFirstName, "
	+" cremp.`last_name` AS returnOrderCreatedByLastName, "
	+" cremp.`middle_name` AS returnOrderCreatedByMiddleName, "
	
	+" lsemp.`first_name` AS returnOrderLastModifiedByFirstName, "
	+" lsemp.`last_name` AS returnOrderLastModifiedByLastName, "
	+" lsemp.`middle_name` AS returnOrderLastModifiedMiddleName, "
	
	+" oemp.first_name AS returnOrderOwnerFirstName, "
	+" oemp.last_name AS returnOrderownerLastName, "
	+" oemp.middle_name AS returnOrderOwnerMiddleName "
	
	
	+" FROM `bt_product_return_order` ro "
	+" JOIN location sloc ON ro.`source_location_id` = sloc.location_id "
	+" JOIN location dloc ON ro.`destination_location_id` =dloc.location_id "
	+" JOIN employee_details cremp ON cremp.email_id=ro.`created_by_email_id` "
	+" JOIN employee_details lsemp ON lsemp.email_id=ro.`last_modified_by_email_id` "
	+" JOIN employee_details oemp ON oemp.email_id=ro.`owner_email_id` where dloc.location_id=:destinationlocation and ro.return_order_status !='Draft'",nativeQuery = true,countQuery = "SELECT count(*) as ct"
	
	+" FROM `bt_product_return_order` ro "
	+" JOIN location sloc ON ro.`source_location_id` = sloc.location_id "
	+" JOIN location dloc ON ro.`destination_location_id` =dloc.location_id "
	+" JOIN employee_details cremp ON cremp.email_id=ro.`created_by_email_id` "
	+" JOIN employee_details lsemp ON lsemp.email_id=ro.`last_modified_by_email_id` "
	+" JOIN employee_details oemp ON oemp.email_id=ro.`owner_email_id` where dloc.location_id=:destinationlocation and ro.return_order_status !='Draft'")
    public Page<Map<String,Object>> getPageSendToMe(@Param("destinationlocation")Long destinationLocation,Pageable page);

	@Query(value="SELECT "
	+" ro.`id` AS id, "
	+" ro.`created_date` AS createdDate, "
	+" ro.`last_modified_date` AS returnOrderLastModifiedDate, "
	+" ro.`actual_send_date` AS returnOrderActualSendDate, "
	+" ro.`manual_id` AS returnOrderManualId, "
	+" ro.`return_order_status` AS returnOrderStatus, "
	+" ro.`ship_to_name` AS returnOrderShipToName, "
	+" ro.`shipping_mode` AS returnOrderShippingName, "
	+" ro.`total_quantity_received` AS returnOrderTotalQuantityReceived, "
	+" ro.`total_quantity_return` AS returnOrderTotalQuantityReturned, "
	+" ro.`tracking_number` AS returnOrderTrackingNumber, "

	+" sloc.location_id AS returnOrderSourceLocationId, "
	+" sloc.location_name AS returnOrderSourceLocationName, "
	
	+" dloc.location_id AS returnOrderDestinationLocationId, "
	+" dloc.location_name AS returnOrderDestinationalLocationName, "
	
	+" cremp.`first_name` AS returnOrderCreatedByFirstName, "
	+" cremp.`last_name` AS returnOrderCreatedByLastName, "
	+" cremp.`middle_name` AS returnOrderCreatedByMiddleName, "
	
	+" lsemp.`first_name` AS returnOrderLastModifiedByFirstName, "
	+" lsemp.`last_name` AS returnOrderLastModifiedByLastName, "
	+" lsemp.`middle_name` AS returnOrderLastModifiedMiddleName, "
	
	+" oemp.first_name AS returnOrderOwnerFirstName, "
	+" oemp.last_name AS returnOrderownerLastName, "
	+" oemp.middle_name AS returnOrderOwnerMiddleName "
	
	
	+" FROM `bt_product_return_order` ro "
	+" JOIN location sloc ON ro.`source_location_id` = sloc.location_id "
	+" JOIN location dloc ON ro.`destination_location_id` =dloc.location_id "
	+" JOIN employee_details cremp ON cremp.email_id=ro.`created_by_email_id` "
	+" JOIN employee_details lsemp ON lsemp.email_id=ro.`last_modified_by_email_id` "
	+" JOIN employee_details oemp ON oemp.email_id=ro.`owner_email_id` where sloc.location_id=:destinationlocation",nativeQuery = true,countQuery = "SELECT count(*) as ct"
	
	+" FROM `bt_product_return_order` ro "
	+" JOIN location sloc ON ro.`source_location_id` = sloc.location_id "
	+" JOIN location dloc ON ro.`destination_location_id` =dloc.location_id "
	+" JOIN employee_details cremp ON cremp.email_id=ro.`created_by_email_id` "
	+" JOIN employee_details lsemp ON lsemp.email_id=ro.`last_modified_by_email_id` "
	+" JOIN employee_details oemp ON oemp.email_id=ro.`owner_email_id` where sloc.location_id=:destinationlocation")
    public Page<Map<String,Object>> getPageSendByMe(@Param("destinationlocation")Long destinationLocation,Pageable page);

    @Query(value="SELECT "
	+" ro.`id` AS id, "
	+" ro.`created_date` AS createdDate, "
	+" ro.`last_modified_date` AS returnOrderLastModifiedDate, "
	+" ro.`actual_send_date` AS returnOrderActualSendDate, "
	+" ro.`manual_id` AS returnOrderManualId, "
	+" ro.`return_order_status` AS returnOrderStatus, "
	+" ro.`ship_to_name` AS returnOrderShipToName, "
	+" ro.`shipping_mode` AS returnOrderShippingName, "
	+" ro.`total_quantity_received` AS returnOrderTotalQuantityReceived, "
	+" ro.`total_quantity_return` AS returnOrderTotalQuantityReturned, "
	+" ro.`tracking_number` AS returnOrderTrackingNumber, "

	+" sloc.location_id AS returnOrderSourceLocationId, "
	+" sloc.location_name AS returnOrderSourceLocationName, "
	
	+" dloc.location_id AS returnOrderDestinationLocationId, "
	+" dloc.location_name AS returnOrderDestinationalLocationName, "
	+" cremp.email_id as returnOrdercreatedByEmailId,"
	+" cremp.`first_name` AS returnOrderCreatedByFirstName, "
	+" cremp.`last_name` AS returnOrderCreatedByLastName, "
	+" cremp.`middle_name` AS returnOrderCreatedByMiddleName, "
	+" lsemp.email_id as returnOrderLastModfiedByEmailId, "
	+" lsemp.`first_name` AS returnOrderLastModifiedByFirstName, "
	+" lsemp.`last_name` AS returnOrderLastModifiedByLastName, "
	+" lsemp.`middle_name` AS returnOrderLastModifiedMiddleName, "
	+" oemp.email_id as returnOrderOwnerEmailId, "
	+" oemp.first_name AS returnOrderOwnerFirstName, "
	+" oemp.last_name AS returnOrderownerLastName, "
	+" oemp.middle_name AS returnOrderOwnerMiddleName "
	
	
	+" FROM `bt_product_return_order` ro "
	+" JOIN location sloc ON ro.`source_location_id` = sloc.location_id "
	+" JOIN location dloc ON ro.`destination_location_id` =dloc.location_id "
	+" JOIN employee_details cremp ON cremp.email_id=ro.`created_by_email_id` "
	+" JOIN employee_details lsemp ON lsemp.email_id=ro.`last_modified_by_email_id` "
	+" JOIN employee_details oemp ON oemp.email_id=ro.`owner_email_id` where ro.id=:id",nativeQuery = true)
    public Map<String,Object> getSingle(@Param("id")Long id);

    @Query(value="SELECT "
	+" ro.`id` AS id, "
	+" ro.`created_date` AS createdDate, "
	+" ro.`last_modified_date` AS returnOrderLastModifiedDate, "
	+" ro.`actual_send_date` AS returnOrderActualSendDate, "
	+" ro.`manual_id` AS returnOrderManualId, "
	+" ro.`return_order_status` AS returnOrderStatus, "
	+" ro.`ship_to_name` AS returnOrderShipToName, "
	+" ro.`shipping_mode` AS returnOrderShippingName, "
	+" ro.`total_quantity_received` AS returnOrderTotalQuantityReceived, "
	+" ro.`total_quantity_return` AS returnOrderTotalQuantityReturned, "
	+" ro.`tracking_number` AS returnOrderTrackingNumber, "

	+" sloc.location_id AS returnOrderSourceLocationId, "
	+" sloc.location_name AS returnOrderSourceLocationName, "
	
	+" dloc.location_id AS returnOrderDestinationLocationId, "
	+" dloc.location_name AS returnOrderDestinationalLocationName, "
	+" cremp.email_id as returnOrdercreatedByEmailId,"
	+" cremp.`first_name` AS returnOrderCreatedByFirstName, "
	+" cremp.`last_name` AS returnOrderCreatedByLastName, "
	+" cremp.`middle_name` AS returnOrderCreatedByMiddleName, "
	+" lsemp.email_id as returnOrderLastModfiedByEmailId, "
	+" lsemp.`first_name` AS returnOrderLastModifiedByFirstName, "
	+" lsemp.`last_name` AS returnOrderLastModifiedByLastName, "
	+" lsemp.`middle_name` AS returnOrderLastModifiedMiddleName, "
	+" oemp.email_id as returnOrderOwnerEmailId, "
	+" oemp.first_name AS returnOrderOwnerFirstName, "
	+" oemp.last_name AS returnOrderownerLastName, "
	+" oemp.middle_name AS returnOrderOwnerMiddleName "
	
	
	+" FROM `bt_product_return_order` ro "
	+" JOIN location sloc ON ro.`source_location_id` = sloc.location_id "
	+" JOIN location dloc ON ro.`destination_location_id` =dloc.location_id "
	+" JOIN employee_details cremp ON cremp.email_id=ro.`created_by_email_id` "
	+" JOIN employee_details lsemp ON lsemp.email_id=ro.`last_modified_by_email_id` "
	+" JOIN employee_details oemp ON oemp.email_id=ro.`owner_email_id` where ro.id=:id and (dloc.location_id in :locationIds or sloc.location_id in :locationIds)",nativeQuery = true)
    public Map<String,Object> getSingle(@Param("id")Long id,@Param("locationIds")List<Long> locationIds);

    @Query(value="SELECT "
	+" ro.`id` AS id, "
	+" ro.`created_date` AS createdDate, "
	+" ro.`last_modified_date` AS returnOrderLastModifiedDate, "
	+" ro.`actual_send_date` AS returnOrderActualSendDate, "
	+" ro.`manual_id` AS returnOrderManualId, "
	+" ro.`return_order_status` AS returnOrderStatus, "
	+" ro.`ship_to_name` AS returnOrderShipToName, "
	+" ro.`shipping_mode` AS returnOrderShippingName, "
	+" ro.`total_quantity_received` AS returnOrderTotalQuantityReceived, "
	+" ro.`total_quantity_return` AS returnOrderTotalQuantityReturned, "
	+" ro.`tracking_number` AS returnOrderTrackingNumber, "

	+" sloc.location_id AS returnOrderSourceLocationId, "
	+" sloc.location_name AS returnOrderSourceLocationName, "
	
	+" dloc.location_id AS returnOrderDestinationLocationId, "
	+" dloc.location_name AS returnOrderDestinationalLocationName, "
	
	+" cremp.`first_name` AS returnOrderCreatedByFirstName, "
	+" cremp.`last_name` AS returnOrderCreatedByLastName, "
	+" cremp.`middle_name` AS returnOrderCreatedByMiddleName, "
	
	+" lsemp.`first_name` AS returnOrderLastModifiedByFirstName, "
	+" lsemp.`last_name` AS returnOrderLastModifiedByLastName, "
	+" lsemp.`middle_name` AS returnOrderLastModifiedMiddleName, "
	
	+" oemp.first_name AS returnOrderOwnerFirstName, "
	+" oemp.last_name AS returnOrderownerLastName, "
	+" oemp.middle_name AS returnOrderOwnerMiddleName "
	
	
	+" FROM `bt_product_return_order` ro "
	+" JOIN location sloc ON ro.`source_location_id` = sloc.location_id "
	+" JOIN location dloc ON ro.`destination_location_id` =dloc.location_id "
	+" JOIN employee_details cremp ON cremp.email_id=ro.`created_by_email_id` "
	+" JOIN employee_details lsemp ON lsemp.email_id=ro.`last_modified_by_email_id` "
	+" JOIN employee_details oemp ON oemp.email_id=ro.`owner_email_id` where dloc.location_id = :locationId and ro.return_order_status !='Draft'",nativeQuery = true,countQuery = "SELECT count(*) as ct"
	
	+" FROM `bt_product_return_order` ro "
	+" JOIN location sloc ON ro.`source_location_id` = sloc.location_id "
	+" JOIN location dloc ON ro.`destination_location_id` =dloc.location_id "
	+" JOIN employee_details cremp ON cremp.email_id=ro.`created_by_email_id` "
	+" JOIN employee_details lsemp ON lsemp.email_id=ro.`last_modified_by_email_id` "
	+" JOIN employee_details oemp ON oemp.email_id=ro.`owner_email_id` where dloc.location_id =:locationId and ro.return_order_status != 'Draft'")
    public Page<Map<String,Object>> sendToMe(@Param("locationId")Long locationId,Pageable page);

    @Query(value="SELECT "
	+" ro.`id` AS id, "
	+" ro.`created_date` AS createdDate, "
	+" ro.`last_modified_date` AS returnOrderLastModifiedDate, "
	+" ro.`actual_send_date` AS returnOrderActualSendDate, "
	+" ro.`manual_id` AS returnOrderManualId, "
	+" ro.`return_order_status` AS returnOrderStatus, "
	+" ro.`ship_to_name` AS returnOrderShipToName, "
	+" ro.`shipping_mode` AS returnOrderShippingName, "
	+" ro.`total_quantity_received` AS returnOrderTotalQuantityReceived, "
	+" ro.`total_quantity_return` AS returnOrderTotalQuantityReturned, "
	+" ro.`tracking_number` AS returnOrderTrackingNumber, "

	+" sloc.location_id AS returnOrderSourceLocationId, "
	+" sloc.location_name AS returnOrderSourceLocationName, "
	
	+" dloc.location_id AS returnOrderDestinationLocationId, "
	+" dloc.location_name AS returnOrderDestinationalLocationName, "
	
	+" cremp.`first_name` AS returnOrderCreatedByFirstName, "
	+" cremp.`last_name` AS returnOrderCreatedByLastName, "
	+" cremp.`middle_name` AS returnOrderCreatedByMiddleName, "
	
	+" lsemp.`first_name` AS returnOrderLastModifiedByFirstName, "
	+" lsemp.`last_name` AS returnOrderLastModifiedByLastName, "
	+" lsemp.`middle_name` AS returnOrderLastModifiedMiddleName, "
	
	+" oemp.first_name AS returnOrderOwnerFirstName, "
	+" oemp.last_name AS returnOrderownerLastName, "
	+" oemp.middle_name AS returnOrderOwnerMiddleName "
	
	
	+" FROM `bt_product_return_order` ro "
	+" JOIN location sloc ON ro.`source_location_id` = sloc.location_id "
	+" JOIN location dloc ON ro.`destination_location_id` =dloc.location_id "
	+" JOIN employee_details cremp ON cremp.email_id=ro.`created_by_email_id` "
	+" JOIN employee_details lsemp ON lsemp.email_id=ro.`last_modified_by_email_id` "
	+" JOIN employee_details oemp ON oemp.email_id=ro.`owner_email_id` where sloc.location_id = :locationId",nativeQuery = true,countQuery = "SELECT count(*) as ct"
	
	+" FROM `bt_product_return_order` ro "
	+" JOIN location sloc ON ro.`source_location_id` = sloc.location_id "
	+" JOIN location dloc ON ro.`destination_location_id` =dloc.location_id "
	+" JOIN employee_details cremp ON cremp.email_id=ro.`created_by_email_id` "
	+" JOIN employee_details lsemp ON lsemp.email_id=ro.`last_modified_by_email_id` "
	+" JOIN employee_details oemp ON oemp.email_id=ro.`owner_email_id` where sloc.location_id =:locationId")
    public Page<Map<String,Object>> sendByMe(@Param("locationId")Long locationId,Pageable page);

    @Query(value="SELECT "
	+" ro.`id` AS id, "
	+" ro.`created_date` AS createdDate, "
	+" ro.`last_modified_date` AS returnOrderLastModifiedDate, "
	+" ro.`actual_send_date` AS returnOrderActualSendDate, "
	+" ro.`manual_id` AS returnOrderManualId, "
	+" ro.`return_order_status` AS returnOrderStatus, "
	+" ro.`ship_to_name` AS returnOrderShipToName, "
	+" ro.`shipping_mode` AS returnOrderShippingName, "
	+" ro.`total_quantity_received` AS returnOrderTotalQuantityReceived, "
	+" ro.`total_quantity_return` AS returnOrderTotalQuantityReturned, "
	+" ro.`tracking_number` AS returnOrderTrackingNumber, "

	+" sloc.location_id AS returnOrderSourceLocationId, "
	+" sloc.location_name AS returnOrderSourceLocationName, "
	
	+" dloc.location_id AS returnOrderDestinationLocationId, "
	+" dloc.location_name AS returnOrderDestinationalLocationName, "
	
	+" cremp.`first_name` AS returnOrderCreatedByFirstName, "
	+" cremp.`last_name` AS returnOrderCreatedByLastName, "
	+" cremp.`middle_name` AS returnOrderCreatedByMiddleName, "
	
	+" lsemp.`first_name` AS returnOrderLastModifiedByFirstName, "
	+" lsemp.`last_name` AS returnOrderLastModifiedByLastName, "
	+" lsemp.`middle_name` AS returnOrderLastModifiedMiddleName, "
	
	+" oemp.first_name AS returnOrderOwnerFirstName, "
	+" oemp.last_name AS returnOrderownerLastName, "
	+" oemp.middle_name AS returnOrderOwnerMiddleName "
	
	
	+" FROM `bt_product_return_order` ro "
	+" JOIN location sloc ON ro.`source_location_id` = sloc.location_id "
	+" JOIN location dloc ON ro.`destination_location_id` =dloc.location_id "
	+" JOIN employee_details cremp ON cremp.email_id=ro.`created_by_email_id` "
	+" JOIN employee_details lsemp ON lsemp.email_id=ro.`last_modified_by_email_id` "
	+" JOIN employee_details oemp ON oemp.email_id=ro.`owner_email_id` where ro.return_order_status in :sstatus",nativeQuery = true,countQuery = "SELECT count(*) as ct"
	
	+" FROM `bt_product_return_order` ro "
	+" JOIN location sloc ON ro.`source_location_id` = sloc.location_id "
	+" JOIN location dloc ON ro.`destination_location_id` =dloc.location_id "
	+" JOIN employee_details cremp ON cremp.email_id=ro.`created_by_email_id` "
	+" JOIN employee_details lsemp ON lsemp.email_id=ro.`last_modified_by_email_id` "
	+" JOIN employee_details oemp ON oemp.email_id=ro.`owner_email_id` where ro.return_order_status in :sstatus")
    public Page<Map<String,Object>> getByStatus(@Param("sstatus")List<String> status,Pageable page);

    @Query(value="SELECT "
	+" ro.`id` AS id, "
	+" ro.`created_date` AS createdDate, "
	+" ro.`last_modified_date` AS returnOrderLastModifiedDate, "
	+" ro.`actual_send_date` AS returnOrderActualSendDate, "
	+" ro.`manual_id` AS returnOrderManualId, "
	+" ro.`return_order_status` AS returnOrderStatus, "
	+" ro.`ship_to_name` AS returnOrderShipToName, "
	+" ro.`shipping_mode` AS returnOrderShippingName, "
	+" ro.`total_quantity_received` AS returnOrderTotalQuantityReceived, "
	+" ro.`total_quantity_return` AS returnOrderTotalQuantityReturned, "
	+" ro.`tracking_number` AS returnOrderTrackingNumber, "

	+" sloc.location_id AS returnOrderSourceLocationId, "
	+" sloc.location_name AS returnOrderSourceLocationName, "
	
	+" dloc.location_id AS returnOrderDestinationLocationId, "
	+" dloc.location_name AS returnOrderDestinationalLocationName, "
	
	+" cremp.`first_name` AS returnOrderCreatedByFirstName, "
	+" cremp.`last_name` AS returnOrderCreatedByLastName, "
	+" cremp.`middle_name` AS returnOrderCreatedByMiddleName, "
	
	+" lsemp.`first_name` AS returnOrderLastModifiedByFirstName, "
	+" lsemp.`last_name` AS returnOrderLastModifiedByLastName, "
	+" lsemp.`middle_name` AS returnOrderLastModifiedMiddleName, "
	
	+" oemp.first_name AS returnOrderOwnerFirstName, "
	+" oemp.last_name AS returnOrderownerLastName, "
	+" oemp.middle_name AS returnOrderOwnerMiddleName "
	
	
	+" FROM `bt_product_return_order` ro "
	+" JOIN location sloc ON ro.`source_location_id` = sloc.location_id "
	+" JOIN location dloc ON ro.`destination_location_id` =dloc.location_id "
	+" JOIN employee_details cremp ON cremp.email_id=ro.`created_by_email_id` "
	+" JOIN employee_details lsemp ON lsemp.email_id=ro.`last_modified_by_email_id` "
	+" JOIN employee_details oemp ON oemp.email_id=ro.`owner_email_id` where (dloc.location_id in :locationIds or sloc.location_id in :locationIds) and ro.return_order_status in :sstatus",nativeQuery = true,countQuery = "SELECT count(*) as ct"
	
	+" FROM `bt_product_return_order` ro "
	+" JOIN location sloc ON ro.`source_location_id` = sloc.location_id "
	+" JOIN location dloc ON ro.`destination_location_id` =dloc.location_id "
	+" JOIN employee_details cremp ON cremp.email_id=ro.`created_by_email_id` "
	+" JOIN employee_details lsemp ON lsemp.email_id=ro.`last_modified_by_email_id` "
	+" JOIN employee_details oemp ON oemp.email_id=ro.`owner_email_id` where (dloc.location_id in :locationIds or sloc.location_id in :locationIds) and ro.return_order_status in :sstatus")
    public Page<Map<String,Object>> getByStatus(@Param("sstatus")List<String> status,@Param("locationIds")List<Long>locationIds,Pageable page);

    @Query(value="SELECT "
	+" ro.`id` AS id, "
	+" ro.`created_date` AS createdDate, "
	+" ro.`last_modified_date` AS returnOrderLastModifiedDate, "
	+" ro.`actual_send_date` AS returnOrderActualSendDate, "
	+" ro.`manual_id` AS returnOrderManualId, "
	+" ro.`return_order_status` AS returnOrderStatus, "
	+" ro.`ship_to_name` AS returnOrderShipToName, "
	+" ro.`shipping_mode` AS returnOrderShippingName, "
	+" ro.`total_quantity_received` AS returnOrderTotalQuantityReceived, "
	+" ro.`total_quantity_return` AS returnOrderTotalQuantityReturned, "
	+" ro.`tracking_number` AS returnOrderTrackingNumber, "

	+" sloc.location_id AS returnOrderSourceLocationId, "
	+" sloc.location_name AS returnOrderSourceLocationName, "
	
	+" dloc.location_id AS returnOrderDestinationLocationId, "
	+" dloc.location_name AS returnOrderDestinationalLocationName, "
	
	+" cremp.`first_name` AS returnOrderCreatedByFirstName, "
	+" cremp.`last_name` AS returnOrderCreatedByLastName, "
	+" cremp.`middle_name` AS returnOrderCreatedByMiddleName, "
	
	+" lsemp.`first_name` AS returnOrderLastModifiedByFirstName, "
	+" lsemp.`last_name` AS returnOrderLastModifiedByLastName, "
	+" lsemp.`middle_name` AS returnOrderLastModifiedMiddleName, "
	
	+" oemp.first_name AS returnOrderOwnerFirstName, "
	+" oemp.last_name AS returnOrderownerLastName, "
	+" oemp.middle_name AS returnOrderOwnerMiddleName "
	
	
	+" FROM `bt_product_return_order` ro "
	+" JOIN location sloc ON ro.`source_location_id` = sloc.location_id "
	+" JOIN location dloc ON ro.`destination_location_id` =dloc.location_id "
	+" JOIN employee_details cremp ON cremp.email_id=ro.`created_by_email_id` "
	+" JOIN employee_details lsemp ON lsemp.email_id=ro.`last_modified_by_email_id` "
	+" JOIN employee_details oemp ON oemp.email_id=ro.`owner_email_id` where ro.manual_id like :serchkey or ro.return_order_status like :serchkey or ro.ship_to_name like :serchkey or ro.shipping_mode like :serchkey or ro.tracking_number like :serchkey or sloc.location_name like :serchkey or dloc.location_name like :serchkey",nativeQuery = true,countQuery = "SELECT count(*) as ct"
	
	+" FROM `bt_product_return_order` ro "
	+" JOIN location sloc ON ro.`source_location_id` = sloc.location_id "
	+" JOIN location dloc ON ro.`destination_location_id` =dloc.location_id "
	+" JOIN employee_details cremp ON cremp.email_id=ro.`created_by_email_id` "
	+" JOIN employee_details lsemp ON lsemp.email_id=ro.`last_modified_by_email_id` "
	+" JOIN employee_details oemp ON oemp.email_id=ro.`owner_email_id` where ro.manual_id like :serchkey or ro.return_order_status like :serchkey or ro.ship_to_name like :serchkey or ro.shipping_mode like :serchkey or ro.tracking_number like :serchkey or sloc.location_name like :serchkey or dloc.location_name like :serchkey")
    public Page<Map<String,Object>> getSerachForList(@Param("serchkey")String searchkey,Pageable page);

	@Query(value="SELECT "
	+" ro.`id` AS id, "
	+" ro.`created_date` AS createdDate, "
	+" ro.`last_modified_date` AS returnOrderLastModifiedDate, "
	+" ro.`actual_send_date` AS returnOrderActualSendDate, "
	+" ro.`manual_id` AS returnOrderManualId, "
	+" ro.`return_order_status` AS returnOrderStatus, "
	+" ro.`ship_to_name` AS returnOrderShipToName, "
	+" ro.`shipping_mode` AS returnOrderShippingName, "
	+" ro.`total_quantity_received` AS returnOrderTotalQuantityReceived, "
	+" ro.`total_quantity_return` AS returnOrderTotalQuantityReturned, "
	+" ro.`tracking_number` AS returnOrderTrackingNumber, "

	+" sloc.location_id AS returnOrderSourceLocationId, "
	+" sloc.location_name AS returnOrderSourceLocationName, "
	
	+" dloc.location_id AS returnOrderDestinationLocationId, "
	+" dloc.location_name AS returnOrderDestinationalLocationName, "
	
	+" cremp.`first_name` AS returnOrderCreatedByFirstName, "
	+" cremp.`last_name` AS returnOrderCreatedByLastName, "
	+" cremp.`middle_name` AS returnOrderCreatedByMiddleName, "
	
	+" lsemp.`first_name` AS returnOrderLastModifiedByFirstName, "
	+" lsemp.`last_name` AS returnOrderLastModifiedByLastName, "
	+" lsemp.`middle_name` AS returnOrderLastModifiedMiddleName, "
	
	+" oemp.first_name AS returnOrderOwnerFirstName, "
	+" oemp.last_name AS returnOrderownerLastName, "
	+" oemp.middle_name AS returnOrderOwnerMiddleName "
	
	
	+" FROM `bt_product_return_order` ro "
	+" JOIN location sloc ON ro.`source_location_id` = sloc.location_id "
	+" JOIN location dloc ON ro.`destination_location_id` =dloc.location_id "
	+" JOIN employee_details cremp ON cremp.email_id=ro.`created_by_email_id` "
	+" JOIN employee_details lsemp ON lsemp.email_id=ro.`last_modified_by_email_id` "
	+" JOIN employee_details oemp ON oemp.email_id=ro.`owner_email_id` where ro.manual_id like :serchkey or ro.return_order_status like :serchkey or ro.ship_to_name like :serchkey or ro.shipping_mode like :serchkey or ro.tracking_number like :serchkey or sloc.location_name like :serchkey or dloc.location_name like :serchkey",nativeQuery = true)
    public List<Map<String,Object>> getSerachForList(@Param("serchkey")String searchkey);

    @Query(value="SELECT "
	+" ro.`id` AS id, "
	+" ro.`created_date` AS createdDate, "
	+" ro.`last_modified_date` AS returnOrderLastModifiedDate, "
	+" ro.`actual_send_date` AS returnOrderActualSendDate, "
	+" ro.`manual_id` AS returnOrderManualId, "
	+" ro.`return_order_status` AS returnOrderStatus, "
	+" ro.`ship_to_name` AS returnOrderShipToName, "
	+" ro.`shipping_mode` AS returnOrderShippingName, "
	+" ro.`total_quantity_received` AS returnOrderTotalQuantityReceived, "
	+" ro.`total_quantity_return` AS returnOrderTotalQuantityReturned, "
	+" ro.`tracking_number` AS returnOrderTrackingNumber, "

	+" sloc.location_id AS returnOrderSourceLocationId, "
	+" sloc.location_name AS returnOrderSourceLocationName, "
	
	+" dloc.location_id AS returnOrderDestinationLocationId, "
	+" dloc.location_name AS returnOrderDestinationalLocationName, "
	
	+" cremp.`first_name` AS returnOrderCreatedByFirstName, "
	+" cremp.`last_name` AS returnOrderCreatedByLastName, "
	+" cremp.`middle_name` AS returnOrderCreatedByMiddleName, "
	
	+" lsemp.`first_name` AS returnOrderLastModifiedByFirstName, "
	+" lsemp.`last_name` AS returnOrderLastModifiedByLastName, "
	+" lsemp.`middle_name` AS returnOrderLastModifiedMiddleName, "
	
	+" oemp.first_name AS returnOrderOwnerFirstName, "
	+" oemp.last_name AS returnOrderownerLastName, "
	+" oemp.middle_name AS returnOrderOwnerMiddleName "
	
	
	+" FROM `bt_product_return_order` ro "
	+" JOIN location sloc ON ro.`source_location_id` = sloc.location_id "
	+" JOIN location dloc ON ro.`destination_location_id` =dloc.location_id "
	+" JOIN employee_details cremp ON cremp.email_id=ro.`created_by_email_id` "
	+" JOIN employee_details lsemp ON lsemp.email_id=ro.`last_modified_by_email_id` "
	+" JOIN employee_details oemp ON oemp.email_id=ro.`owner_email_id` where (dloc.location_id in :locationIds or sloc.location_id in :locationIds) and (ro.manual_id like :serchkey or ro.return_order_status like :serchkey or ro.ship_to_name like :serchkey or ro.shipping_mode like :serchkey or ro.tracking_number like :serchkey or sloc.location_name like :serchkey or dloc.location_name like :serchkey)",nativeQuery = true,countQuery = "SELECT count(*) as ct"
	
	+" FROM `bt_product_return_order` ro "
	+" JOIN location sloc ON ro.`source_location_id` = sloc.location_id "
	+" JOIN location dloc ON ro.`destination_location_id` =dloc.location_id "
	+" JOIN employee_details cremp ON cremp.email_id=ro.`created_by_email_id` "
	+" JOIN employee_details lsemp ON lsemp.email_id=ro.`last_modified_by_email_id` "
	+" JOIN employee_details oemp ON oemp.email_id=ro.`owner_email_id` where (dloc.location_id in :locationIds or sloc.location_id in :locationIds) and (ro.manual_id like :serchkey or ro.return_order_status like :serchkey or ro.ship_to_name like :serchkey or ro.shipping_mode like :serchkey or ro.tracking_number like :serchkey or sloc.location_name like :serchkey or dloc.location_name like :serchkey)")
    public Page<Map<String,Object>> getSerachForList(@Param("serchkey")String searchkey,List<Long>locationIds,Pageable page);

	@Query(value="SELECT "
	+" ro.`id` AS id, "
	+" ro.`created_date` AS createdDate, "
	+" ro.`last_modified_date` AS returnOrderLastModifiedDate, "
	+" ro.`actual_send_date` AS returnOrderActualSendDate, "
	+" ro.`manual_id` AS returnOrderManualId, "
	+" ro.`return_order_status` AS returnOrderStatus, "
	+" ro.`ship_to_name` AS returnOrderShipToName, "
	+" ro.`shipping_mode` AS returnOrderShippingName, "
	+" ro.`total_quantity_received` AS returnOrderTotalQuantityReceived, "
	+" ro.`total_quantity_return` AS returnOrderTotalQuantityReturned, "
	+" ro.`tracking_number` AS returnOrderTrackingNumber, "

	+" sloc.location_id AS returnOrderSourceLocationId, "
	+" sloc.location_name AS returnOrderSourceLocationName, "
	
	+" dloc.location_id AS returnOrderDestinationLocationId, "
	+" dloc.location_name AS returnOrderDestinationalLocationName, "
	
	+" cremp.`first_name` AS returnOrderCreatedByFirstName, "
	+" cremp.`last_name` AS returnOrderCreatedByLastName, "
	+" cremp.`middle_name` AS returnOrderCreatedByMiddleName, "
	
	+" lsemp.`first_name` AS returnOrderLastModifiedByFirstName, "
	+" lsemp.`last_name` AS returnOrderLastModifiedByLastName, "
	+" lsemp.`middle_name` AS returnOrderLastModifiedMiddleName, "
	
	+" oemp.first_name AS returnOrderOwnerFirstName, "
	+" oemp.last_name AS returnOrderownerLastName, "
	+" oemp.middle_name AS returnOrderOwnerMiddleName "
	
	
	+" FROM `bt_product_return_order` ro "
	+" JOIN location sloc ON ro.`source_location_id` = sloc.location_id "
	+" JOIN location dloc ON ro.`destination_location_id` =dloc.location_id "
	+" JOIN employee_details cremp ON cremp.email_id=ro.`created_by_email_id` "
	+" JOIN employee_details lsemp ON lsemp.email_id=ro.`last_modified_by_email_id` "
	+" JOIN employee_details oemp ON oemp.email_id=ro.`owner_email_id` where (dloc.location_id in :locationIds or sloc.location_id in :locationIds) and (ro.manual_id like :serchkey or ro.return_order_status like :serchkey or ro.ship_to_name like :serchkey or ro.shipping_mode like :serchkey or ro.tracking_number like :serchkey or sloc.location_name like :serchkey or dloc.location_name like :serchkey)",nativeQuery = true,countQuery = "SELECT count(*) as ct"
	
	+" FROM `bt_product_return_order` ro "
	+" JOIN location sloc ON ro.`source_location_id` = sloc.location_id "
	+" JOIN location dloc ON ro.`destination_location_id` =dloc.location_id "
	+" JOIN employee_details cremp ON cremp.email_id=ro.`created_by_email_id` "
	+" JOIN employee_details lsemp ON lsemp.email_id=ro.`last_modified_by_email_id` "
	+" JOIN employee_details oemp ON oemp.email_id=ro.`owner_email_id` where (dloc.location_id in :locationIds or sloc.location_id in :locationIds) and (ro.manual_id like :serchkey or ro.return_order_status like :serchkey or ro.ship_to_name like :serchkey or ro.shipping_mode like :serchkey or ro.tracking_number like :serchkey or sloc.location_name like :serchkey or dloc.location_name like :serchkey)")
    public List<Map<String,Object>> getSerachForList(@Param("serchkey")String searchkey,List<Long>locationIds);


}
