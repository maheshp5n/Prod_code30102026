package com.pratikshat.exicom.repository;

import java.util.List;
import java.util.Map;

import com.pratikshat.exicom.entity.BtServiceOrder;
import com.pratikshat.exicom.repository.custom.BtServiceOrderCustomRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BtServiceOrderRepository extends JpaRepository<BtServiceOrder,Long>,BtServiceOrderCustomRepository {
    @Query(value = "SELECT so.id AS serviceOrderId, "
	+" so.`created_date` AS searviceOrderCreatedDate, "
	+" so.`last_modified_date` AS serviceOrderLastModifiedDate, "
	+" so.`manual_id` AS serviceOrderManualId, "
	+" so.`shipping_mode` AS searviceOrderShippingMode, "
	+" so.`shipping_to` AS serviceOrderShippingTo, "
	+" so.`total_number_of_item` AS searviceOrderTotalNumberOfItem, "
	+" so.`total_number_of_item_assigned` AS serviceOrderTotalNumberOfItemAssigned, "
	+" so.`total_number_of_item_assigned_returned` AS serviceOrderTotalNumberOfItemAssignedReturned, "
	+" so.`total_number_of_item_received` AS searviceOrderTotalNumberOfItemReceived, "
	+" so.`total_number_of_item_returned` AS searviceOrderTotalNumberOfItemReturned, "
	+" so.`tracking_number` AS searviceOrderTrackingNumber, "
	+" so.`created_by_email_id` searviceOrderCreatedByEmailId, "
	+" so.`destination_location_location_id` AS serviceOrderRepairCenterLocationId, "
	+" so.`owner_email_id` AS serviceOrderOwnerEmailid, "
	+" so.`source_location_location_id` AS serviceOrderSourceLocationId, "
	
	+" dloc.location_name AS serviceOrderRepairCenterLocationName, "
	+" dloc.plant AS serviceOrderRepairCenterLocationPlant, "
	+" sloc.location_name AS serviceOrderSourceLocationName, "
	+" sloc.plant AS serviceOrderSourceLocationPlant, "

	+" cemp.`first_name` as createdByFirstName, "
	+" cemp.`middle_name` as createdByMiddleName,"
	+" cemp.`last_name` as createdByLastName, "

	+" lemp.`first_name` as lastModifiedByFirstName, "
	+" lemp.`middle_name` as lastModifiedByMiddleName,"
	+" lemp.`last_name` as lastModifiedByLastName, "

	+" oemp.`first_name` as ownerFirstName, "
	+" oemp.`middle_name` as ownerMiddleName,"
	+" oemp.`last_name` as ownerByLastName "
	
	
	+" FROM `bt_service_order` so  "
	+" JOIN location dloc ON dloc.location_id = so.`destination_location_location_id` "
	+" JOIN location sloc ON sloc.location_id = so.`source_location_location_id` "
	+" JOIN employee_details cemp ON cemp.email_id=so.`created_by_email_id` "
	+" JOIN employee_details oemp ON oemp.email_id=so.`owner_email_id` "
	+" JOIN employee_details lemp ON lemp.email_id=`last_modified_by_email_id`",nativeQuery = true,countQuery = "SELECT count(*) as ct "
	
	
	
	+" FROM `bt_service_order` so  "
	+" JOIN location dloc ON dloc.location_id = so.`destination_location_location_id` "
	+" JOIN location sloc ON sloc.location_id = so.`source_location_location_id` "
	+" JOIN employee_details cemp ON cemp.email_id=so.`created_by_email_id` "
	+" JOIN employee_details oemp ON oemp.email_id=so.`owner_email_id` "
	+" JOIN employee_details lemp ON lemp.email_id=`last_modified_by_email_id`")
    Page<Map<String,Object>> getPage(Pageable page);

    @Query(value = "SELECT so.id AS serviceOrderId, "
	+" so.`created_date` AS searviceOrderCreatedDate, "
	+" so.`last_modified_date` AS serviceOrderLastModifiedDate, "
	+" so.`manual_id` AS serviceOrderManualId, "
	+" so.`shipping_mode` AS searviceOrderShippingMode, "
	+" so.`shipping_to` AS serviceOrderShippingTo, "
	+" so.`total_number_of_item` AS searviceOrderTotalNumberOfItem, "
	+" so.`total_number_of_item_assigned` AS serviceOrderTotalNumberOfItemAssigned, "
	+" so.`total_number_of_item_assigned_returned` AS serviceOrderTotalNumberOfItemAssignedReturned, "
	+" so.`total_number_of_item_received` AS searviceOrderTotalNumberOfItemReceived, "
	+" so.`total_number_of_item_returned` AS searviceOrderTotalNumberOfItemReturned, "
	+" so.`tracking_number` AS searviceOrderTrackingNumber, "
	+" so.`created_by_email_id` searviceOrderCreatedByEmailId, "
	+" so.`destination_location_location_id` AS serviceOrderRepairCenterLocationId, "
	+" so.`owner_email_id` AS serviceOrderOwnerEmailid, "
	+" so.`source_location_location_id` AS serviceOrderSourceLocationId, "
	
	+" cemp.`first_name` as createdByFirstName, "
	+" cemp.`middle_name` as createdByMiddleName,"
	+" cemp.`last_name` as createdByLastName, "

	+" lemp.`first_name` as lastModifiedByFirstName, "
	+" lemp.`middle_name` as lastModifiedByMiddleName,"
	+" lemp.`last_name` as lastModifiedByLastName, "

	+" oemp.`first_name` as ownerFirstName, "
	+" oemp.`middle_name` as ownerMiddleName,"
	+" oemp.`last_name` as ownerByLastName, "
	

	+" dloc.location_name AS serviceOrderRepairCenterLocationName, "
	+" dloc.plant AS serviceOrderRepairCenterLocationPlant, "
	+" sloc.location_name AS serviceOrderSourceLocationName, "
	+" sloc.plant AS serviceOrderSourceLocationPlant "
	
	
	+" FROM `bt_service_order` so  "
	+" JOIN location dloc ON dloc.location_id = so.`destination_location_location_id` "
	+" JOIN location sloc ON sloc.location_id = so.`source_location_location_id` "
	+" JOIN employee_details cemp ON cemp.email_id=so.`created_by_email_id` "
	+" JOIN employee_details oemp ON oemp.email_id=so.`owner_email_id` "
	+" JOIN employee_details lemp ON lemp.email_id=`last_modified_by_email_id` WHERE dloc.location_id in :locationIds OR sloc.location_id in :locationIds",nativeQuery = true,countQuery = "SELECT count(*) as ct "
	
	
	
	+" FROM `bt_service_order` so  "
	+" JOIN location dloc ON dloc.location_id = so.`destination_location_location_id` "
	+" JOIN location sloc ON sloc.location_id = so.`source_location_location_id` "
	+" JOIN employee_details cemp ON cemp.email_id=so.`created_by_email_id` "
	+" JOIN employee_details oemp ON oemp.email_id=so.`owner_email_id` "
	+" JOIN employee_details lemp ON lemp.email_id=`last_modified_by_email_id` WHERE dloc.location_id in :locationIds OR sloc.location_id in :locationIds")
    Page<Map<String,Object>> getPage(@Param("locationIds")List<Long> locationIds,Pageable page);

    @Query(value = "SELECT so.id AS serviceOrderId, "
	+" so.`created_date` AS searviceOrderCreatedDate, "
	+" so.`last_modified_date` AS serviceOrderLastModifiedDate, "
	+" so.`manual_id` AS serviceOrderManualId, "
	+" so.`shipping_mode` AS searviceOrderShippingMode, "
	+" so.`shipping_to` AS serviceOrderShippingTo, "
	+" so.`total_number_of_item` AS searviceOrderTotalNumberOfItem, "
	+" so.`total_number_of_item_assigned` AS serviceOrderTotalNumberOfItemAssigned, "
	+" so.`total_number_of_item_assigned_returned` AS serviceOrderTotalNumberOfItemAssignedReturned, "
	+" so.`total_number_of_item_received` AS searviceOrderTotalNumberOfItemReceived, "
	+" so.`total_number_of_item_returned` AS searviceOrderTotalNumberOfItemReturned, "
	+" so.`tracking_number` AS searviceOrderTrackingNumber, "
	+" so.`created_by_email_id` searviceOrderCreatedByEmailId, "
	+" so.`destination_location_location_id` AS serviceOrderRepairCenterLocationId, "
	+" so.`owner_email_id` AS serviceOrderOwnerEmailid, "
	+" so.`source_location_location_id` AS serviceOrderSourceLocationId, "

	+" cemp.`first_name` as createdByFirstName, "
	+" cemp.`middle_name` as createdByMiddleName,"
	+" cemp.`last_name` as createdByLastName, "

	+" lemp.`first_name` as lastModifiedByFirstName, "
	+" lemp.`middle_name` as lastModifiedByMiddleName,"
	+" lemp.`last_name` as lastModifiedByLastName, "

	+" oemp.`first_name` as ownerFirstName, "
	+" oemp.`middle_name` as ownerMiddleName,"
	+" oemp.`last_name` as ownerByLastName, "
	
	+" dloc.location_name AS serviceOrderRepairCenterLocationName, "
	+" dloc.plant AS serviceOrderRepairCenterLocationPlant, "
	+" sloc.location_name AS serviceOrderSourceLocationName, "
	+" sloc.plant AS serviceOrderSourceLocationPlant "
	
	
	+" FROM `bt_service_order` so  "
	+" JOIN location dloc ON dloc.location_id = so.`destination_location_location_id` "
	+" JOIN location sloc ON sloc.location_id = so.`source_location_location_id` "
	+" JOIN employee_details cemp ON cemp.email_id=so.`created_by_email_id` "
	+" JOIN employee_details oemp ON oemp.email_id=so.`owner_email_id` "
	+" JOIN employee_details lemp ON lemp.email_id=`last_modified_by_email_id` WHERE sloc.location_id= :locationId",nativeQuery = true,countQuery = "SELECT count(*) as ct "
	
	
	
	+" FROM `bt_service_order` so  "
	+" JOIN location dloc ON dloc.location_id = so.`destination_location_location_id` "
	+" JOIN location sloc ON sloc.location_id = so.`source_location_location_id` "
	+" JOIN employee_details cemp ON cemp.email_id=so.`created_by_email_id` "
	+" JOIN employee_details oemp ON oemp.email_id=so.`owner_email_id` "
	+" JOIN employee_details lemp ON lemp.email_id=`last_modified_by_email_id` WHERE sloc.location_id = :locationId")
    Page<Map<String,Object>> getSendByMe(@Param("locationId")Long locationId,Pageable page);

    @Query(value = "SELECT so.id AS serviceOrderId, "
	+" so.`created_date` AS searviceOrderCreatedDate, "
	+" so.`last_modified_date` AS serviceOrderLastModifiedDate, "
	+" so.`manual_id` AS serviceOrderManualId, "
	+" so.`shipping_mode` AS searviceOrderShippingMode, "
	+" so.`shipping_to` AS serviceOrderShippingTo, "
	+" so.`total_number_of_item` AS searviceOrderTotalNumberOfItem, "
	+" so.`total_number_of_item_assigned` AS serviceOrderTotalNumberOfItemAssigned, "
	+" so.`total_number_of_item_assigned_returned` AS serviceOrderTotalNumberOfItemAssignedReturned, "
	+" so.`total_number_of_item_received` AS searviceOrderTotalNumberOfItemReceived, "
	+" so.`total_number_of_item_returned` AS searviceOrderTotalNumberOfItemReturned, "
	+" so.`tracking_number` AS searviceOrderTrackingNumber, "
	+" so.`created_by_email_id` searviceOrderCreatedByEmailId, "
	+" so.`destination_location_location_id` AS serviceOrderRepairCenterLocationId, "
	+" so.`owner_email_id` AS serviceOrderOwnerEmailid, "
	+" so.`source_location_location_id` AS serviceOrderSourceLocationId, "

	+" cemp.`first_name` as createdByFirstName, "
	+" cemp.`middle_name` as createdByMiddleName,"
	+" cemp.`last_name` as createdByLastName, "

	+" lemp.`first_name` as lastModifiedByFirstName, "
	+" lemp.`middle_name` as lastModifiedByMiddleName,"
	+" lemp.`last_name` as lastModifiedByLastName, "

	+" oemp.`first_name` as ownerFirstName, "
	+" oemp.`middle_name` as ownerMiddleName,"
	+" oemp.`last_name` as ownerByLastName, "
	
	+" dloc.location_name AS serviceOrderRepairCenterLocationName, "
	+" dloc.plant AS serviceOrderRepairCenterLocationPlant, "
	+" sloc.location_name AS serviceOrderSourceLocationName, "
	+" sloc.plant AS serviceOrderSourceLocationPlant "
	
	
	+" FROM `bt_service_order` so  "
	+" JOIN location dloc ON dloc.location_id = so.`destination_location_location_id` "
	+" JOIN location sloc ON sloc.location_id = so.`source_location_location_id` "
	+" JOIN employee_details cemp ON cemp.email_id=so.`created_by_email_id` "
	+" JOIN employee_details oemp ON oemp.email_id=so.`owner_email_id` "
	+" JOIN employee_details lemp ON lemp.email_id=`last_modified_by_email_id` WHERE dloc.location_id= :locationId ",nativeQuery = true,countQuery = "SELECT count(*) as ct "
	
	
	
	+" FROM `bt_service_order` so  "
	+" JOIN location dloc ON dloc.location_id = so.`destination_location_location_id` "
	+" JOIN location sloc ON sloc.location_id = so.`source_location_location_id` "
	+" JOIN employee_details cemp ON cemp.email_id=so.`created_by_email_id` "
	+" JOIN employee_details oemp ON oemp.email_id=so.`owner_email_id` "
	+" JOIN employee_details lemp ON lemp.email_id=`last_modified_by_email_id` WHERE dloc.location_id = :locationId ")
    Page<Map<String,Object>> getSendToMe(@Param("locationId")Long locationId,Pageable page);

    @Query(value = "SELECT so.id AS serviceOrderId, "
	+" so.`created_date` AS searviceOrderCreatedDate, "
	+" so.`last_modified_date` AS serviceOrderLastModifiedDate, "
	+" so.`manual_id` AS serviceOrderManualId, "
	+" so.`shipping_mode` AS searviceOrderShippingMode, "
	+" so.`shipping_to` AS serviceOrderShippingTo, "
	+" so.`total_number_of_item` AS searviceOrderTotalNumberOfItem, "
	+" so.`total_number_of_item_assigned` AS serviceOrderTotalNumberOfItemAssigned, "
	+" so.`total_number_of_item_assigned_returned` AS serviceOrderTotalNumberOfItemAssignedReturned, "
	+" so.`total_number_of_item_received` AS searviceOrderTotalNumberOfItemReceived, "
	+" so.`total_number_of_item_returned` AS searviceOrderTotalNumberOfItemReturned, "
	+" so.`tracking_number` AS searviceOrderTrackingNumber, "
	+" so.`created_by_email_id` searviceOrderCreatedByEmailId, "
	+" so.`destination_location_location_id` AS serviceOrderRepairCenterLocationId, "
	+" so.`owner_email_id` AS serviceOrderOwnerEmailid, "
	+" so.`source_location_location_id` AS serviceOrderSourceLocationId, "

	+" cemp.`first_name` as createdByFirstName, "
	+" cemp.`middle_name` as createdByMiddleName,"
	+" cemp.`last_name` as createdByLastName, "

	+" lemp.`first_name` as lastModifiedByFirstName, "
	+" lemp.`middle_name` as lastModifiedByMiddleName,"
	+" lemp.`last_name` as lastModifiedByLastName, "

	+" oemp.`first_name` as ownerFirstName, "
	+" oemp.`middle_name` as ownerMiddleName,"
	+" oemp.`last_name` as ownerByLastName, "
	
	+" dloc.location_name AS serviceOrderRepairCenterLocationName, "
	+" dloc.plant AS serviceOrderRepairCenterLocationPlant, "
	+" sloc.location_name AS serviceOrderSourceLocationName, "
	+" sloc.plant AS serviceOrderSourceLocationPlant "
	
	
	+" FROM `bt_service_order` so  "
	+" JOIN location dloc ON dloc.location_id = so.`destination_location_location_id` "
	+" JOIN location sloc ON sloc.location_id = so.`source_location_location_id` "
	+" JOIN employee_details cemp ON cemp.email_id=so.`created_by_email_id` "
	+" JOIN employee_details oemp ON oemp.email_id=so.`owner_email_id` "
	+" JOIN employee_details lemp ON lemp.email_id=`last_modified_by_email_id` WHERE so.id = :serviceOrderId and (dloc.location_id in :locationIds or sloc.location_id in :locationIds)",nativeQuery = true)
    Map<String,Object> getById(@Param("serviceOrderId")Long serviceOrderid,@Param("locationIds")List<Long> locationIds);


    @Query(value = "SELECT so.id AS serviceOrderId, "
	+" so.`created_date` AS searviceOrderCreatedDate, "
	+" so.`last_modified_date` AS serviceOrderLastModifiedDate, "
	+" so.`manual_id` AS serviceOrderManualId, "
	+" so.`shipping_mode` AS searviceOrderShippingMode, "
	+" so.`shipping_to` AS serviceOrderShippingTo, "
	+" so.`total_number_of_item` AS searviceOrderTotalNumberOfItem, "
	+" so.`total_number_of_item_assigned` AS serviceOrderTotalNumberOfItemAssigned, "
	+" so.`total_number_of_item_assigned_returned` AS serviceOrderTotalNumberOfItemAssignedReturned, "
	+" so.`total_number_of_item_received` AS searviceOrderTotalNumberOfItemReceived, "
	+" so.`total_number_of_item_returned` AS searviceOrderTotalNumberOfItemReturned, "
	+" so.`tracking_number` AS searviceOrderTrackingNumber, "
	+" so.`created_by_email_id` searviceOrderCreatedByEmailId, "
	+" so.`destination_location_location_id` AS serviceOrderRepairCenterLocationId, "
	+" so.`owner_email_id` AS serviceOrderOwnerEmailid, "
	+" so.`source_location_location_id` AS serviceOrderSourceLocationId, "

	+" cemp.`first_name` as createdByFirstName, "
	+" cemp.`middle_name` as createdByMiddleName,"
	+" cemp.`last_name` as createdByLastName, "

	+" lemp.`first_name` as lastModifiedByFirstName, "
	+" lemp.`middle_name` as lastModifiedByMiddleName,"
	+" lemp.`last_name` as lastModifiedByLastName, "

	+" oemp.`first_name` as ownerFirstName, "
	+" oemp.`middle_name` as ownerMiddleName,"
	+" oemp.`last_name` as ownerByLastName, "
	
	+" dloc.location_name AS serviceOrderRepairCenterLocationName, "
	+" dloc.plant AS serviceOrderRepairCenterLocationPlant, "
	+" sloc.location_name AS serviceOrderSourceLocationName, "
	+" sloc.plant AS serviceOrderSourceLocationPlant "
	
	
	+" FROM `bt_service_order` so  "
	+" JOIN location dloc ON dloc.location_id = so.`destination_location_location_id` "
	+" JOIN location sloc ON sloc.location_id = so.`source_location_location_id` "
	+" JOIN employee_details cemp ON cemp.email_id=so.`created_by_email_id` "
	+" JOIN employee_details oemp ON oemp.email_id=so.`owner_email_id` "
	+" JOIN employee_details lemp ON lemp.email_id=`last_modified_by_email_id` WHERE so.id = :serviceOrderId",nativeQuery = true)
    Map<String,Object> getById(@Param("serviceOrderId")Long serviceOrderid);


	@Query(value = "SELECT "
	+" loc.`location_id` AS locationId, "
	+" loc.location_name AS locationName, "
	+" loc.plant AS locationPlant, "
	+" lg.`legal_entity_id` AS legalEntityId, "
	+" lg.`legal_entity_name` AS legalEntityName "
	
	+" FROM location loc JOIN `legal_entity` lg ON lg.`legal_entity_id`=loc.`legal_entity_legal_entity_id`  "
	+" WHERE (lg.`business_doamin_business_domain_id`=1 OR lg.business_doamin_business_domain_id=3) AND lg.`legal_entity_type_type_id`=2 AND loc.`location_type_location_type_id`=1 and (loc.business_domain_business_domain_id=1 or loc.business_domain_business_domain_id=3)",nativeQuery = true)
	List<Map<String,Object>> getRepairCenterList();

	@Query(value = "SELECT so.id AS serviceOrderId, "
			+" so.`created_date` AS searviceOrderCreatedDate, "
			+" so.`last_modified_date` AS serviceOrderLastModifiedDate, "
			+" so.`manual_id` AS serviceOrderManualId, "
			+" so.`shipping_mode` AS searviceOrderShippingMode, "
			+" so.`shipping_to` AS serviceOrderShippingTo, "
			+" so.`total_number_of_item` AS searviceOrderTotalNumberOfItem, "
			+" so.`total_number_of_item_assigned` AS serviceOrderTotalNumberOfItemAssigned, "
			+" so.`total_number_of_item_assigned_returned` AS serviceOrderTotalNumberOfItemAssignedReturned, "
			+" so.`total_number_of_item_received` AS searviceOrderTotalNumberOfItemReceived, "
			+" so.`total_number_of_item_returned` AS searviceOrderTotalNumberOfItemReturned, "
			+" so.`tracking_number` AS searviceOrderTrackingNumber, "
			+" so.`created_by_email_id` searviceOrderCreatedByEmailId, "
			+" so.`destination_location_location_id` AS serviceOrderRepairCenterLocationId, "
			+" so.`owner_email_id` AS serviceOrderOwnerEmailid, "
			+" so.`source_location_location_id` AS serviceOrderSourceLocationId, "

			+" cemp.`first_name` as createdByFirstName, "
			+" cemp.`middle_name` as createdByMiddleName,"
			+" cemp.`last_name` as createdByLastName, "

			+" lemp.`first_name` as lastModifiedByFirstName, "
			+" lemp.`middle_name` as lastModifiedByMiddleName,"
			+" lemp.`last_name` as lastModifiedByLastName, "

			+" oemp.`first_name` as ownerFirstName, "
			+" oemp.`middle_name` as ownerMiddleName,"
			+" oemp.`last_name` as ownerByLastName, "
			
			+" dloc.location_name AS serviceOrderRepairCenterLocationName, "
			+" dloc.plant AS serviceOrderRepairCenterLocationPlant, "
			+" sloc.location_name AS serviceOrderSourceLocationName, "
			+" sloc.plant AS serviceOrderSourceLocationPlant "
			
			
			+" FROM `bt_service_order` so  "
			+" JOIN location dloc ON dloc.location_id = so.`destination_location_location_id` "
			+" JOIN location sloc ON sloc.location_id = so.`source_location_location_id` "
			+" JOIN employee_details cemp ON cemp.email_id=so.`created_by_email_id` "
			+" JOIN employee_details oemp ON oemp.email_id=so.`owner_email_id` "
			+" JOIN employee_details lemp ON lemp.email_id=`last_modified_by_email_id` WHERE "
			+ "so.`total_number_of_item`-so.`total_number_of_item_received`<>0",nativeQuery = true,countQuery = "SELECT count(*) as ct "
			+" FROM `bt_service_order` so  "
			+" JOIN location dloc ON dloc.location_id = so.`destination_location_location_id` "
			+" JOIN location sloc ON sloc.location_id = so.`source_location_location_id` "
			+" JOIN employee_details cemp ON cemp.email_id=so.`created_by_email_id` "
			+" JOIN employee_details oemp ON oemp.email_id=so.`owner_email_id` "
			+" JOIN employee_details lemp ON lemp.email_id=`last_modified_by_email_id` WHERE "
			+" so.`total_number_of_item`-so.`total_number_of_item_received`<>0")
	Page<Map<String, Object>> getPendingToReceive(long locationId, Pageable page);

	@Query(value = "SELECT so.id AS serviceOrderId, "
			+" so.`created_date` AS searviceOrderCreatedDate, "
			+" so.`last_modified_date` AS serviceOrderLastModifiedDate, "
			+" so.`manual_id` AS serviceOrderManualId, "
			+" so.`shipping_mode` AS searviceOrderShippingMode, "
			+" so.`shipping_to` AS serviceOrderShippingTo, "
			+" so.`total_number_of_item` AS searviceOrderTotalNumberOfItem, "
			+" so.`total_number_of_item_assigned` AS serviceOrderTotalNumberOfItemAssigned, "
			+" so.`total_number_of_item_assigned_returned` AS serviceOrderTotalNumberOfItemAssignedReturned, "
			+" so.`total_number_of_item_received` AS searviceOrderTotalNumberOfItemReceived, "
			+" so.`total_number_of_item_returned` AS searviceOrderTotalNumberOfItemReturned, "
			+" so.`tracking_number` AS searviceOrderTrackingNumber, "
			+" so.`created_by_email_id` searviceOrderCreatedByEmailId, "
			+" so.`destination_location_location_id` AS serviceOrderRepairCenterLocationId, "
			+" so.`owner_email_id` AS serviceOrderOwnerEmailid, "
			+" so.`source_location_location_id` AS serviceOrderSourceLocationId, "

			+" cemp.`first_name` as createdByFirstName, "
			+" cemp.`middle_name` as createdByMiddleName,"
			+" cemp.`last_name` as createdByLastName, "

			+" lemp.`first_name` as lastModifiedByFirstName, "
			+" lemp.`middle_name` as lastModifiedByMiddleName,"
			+" lemp.`last_name` as lastModifiedByLastName, "

			+" oemp.`first_name` as ownerFirstName, "
			+" oemp.`middle_name` as ownerMiddleName,"
			+" oemp.`last_name` as ownerByLastName, "
			
			+" dloc.location_name AS serviceOrderRepairCenterLocationName, "
			+" dloc.plant AS serviceOrderRepairCenterLocationPlant, "
			+" sloc.location_name AS serviceOrderSourceLocationName, "
			+" sloc.plant AS serviceOrderSourceLocationPlant "
			
			
			+" FROM `bt_service_order` so  "
			+" JOIN location dloc ON dloc.location_id = so.`destination_location_location_id` "
			+" JOIN location sloc ON sloc.location_id = so.`source_location_location_id` "
			+" JOIN employee_details cemp ON cemp.email_id=so.`created_by_email_id` "
			+" JOIN employee_details oemp ON oemp.email_id=so.`owner_email_id` "
			+" JOIN employee_details lemp ON lemp.email_id=`last_modified_by_email_id` WHERE "
			+ "so.`total_number_of_item`-so.`total_number_of_item_received`=0",nativeQuery = true,countQuery = "SELECT count(*) as ct "	
			+" FROM `bt_service_order` so  "
			+" JOIN location dloc ON dloc.location_id = so.`destination_location_location_id` "
			+" JOIN location sloc ON sloc.location_id = so.`source_location_location_id` "
			+" JOIN employee_details cemp ON cemp.email_id=so.`created_by_email_id` "
			+" JOIN employee_details oemp ON oemp.email_id=so.`owner_email_id` "
			+" JOIN employee_details lemp ON lemp.email_id=`last_modified_by_email_id` WHERE "
			+ "so.`total_number_of_item`-so.`total_number_of_item_received`=0")
	Page<Map<String, Object>> getReceived(long locationId, Pageable page);

	@Query(value = "SELECT so.id AS serviceOrderId, "
			+" so.`created_date` AS searviceOrderCreatedDate, "
			+" so.`last_modified_date` AS serviceOrderLastModifiedDate, "
			+" so.`manual_id` AS serviceOrderManualId, "
			+" so.`shipping_mode` AS searviceOrderShippingMode, "
			+" so.`shipping_to` AS serviceOrderShippingTo, "
			+" so.`total_number_of_item` AS searviceOrderTotalNumberOfItem, "
			+" so.`total_number_of_item_assigned` AS serviceOrderTotalNumberOfItemAssigned, "
			+" so.`total_number_of_item_assigned_returned` AS serviceOrderTotalNumberOfItemAssignedReturned, "
			+" so.`total_number_of_item_received` AS searviceOrderTotalNumberOfItemReceived, "
			+" so.`total_number_of_item_returned` AS searviceOrderTotalNumberOfItemReturned, "
			+" so.`tracking_number` AS searviceOrderTrackingNumber, "
			+" so.`created_by_email_id` searviceOrderCreatedByEmailId, "
			+" so.`destination_location_location_id` AS serviceOrderRepairCenterLocationId, "
			+" so.`owner_email_id` AS serviceOrderOwnerEmailid, "
			+" so.`source_location_location_id` AS serviceOrderSourceLocationId, "

			+" cemp.`first_name` as createdByFirstName, "
			+" cemp.`middle_name` as createdByMiddleName,"
			+" cemp.`last_name` as createdByLastName, "

			+" lemp.`first_name` as lastModifiedByFirstName, "
			+" lemp.`middle_name` as lastModifiedByMiddleName,"
			+" lemp.`last_name` as lastModifiedByLastName, "

			+" oemp.`first_name` as ownerFirstName, "
			+" oemp.`middle_name` as ownerMiddleName,"
			+" oemp.`last_name` as ownerByLastName, "
			
			+" dloc.location_name AS serviceOrderRepairCenterLocationName, "
			+" dloc.plant AS serviceOrderRepairCenterLocationPlant, "
			+" sloc.location_name AS serviceOrderSourceLocationName, "
			+" sloc.plant AS serviceOrderSourceLocationPlant "
			
			
			+" FROM `bt_service_order` so  "
			+" JOIN location dloc ON dloc.location_id = so.`destination_location_location_id` "
			+" JOIN location sloc ON sloc.location_id = so.`source_location_location_id` "
			+" JOIN employee_details cemp ON cemp.email_id=so.`created_by_email_id` "
			+" JOIN employee_details oemp ON oemp.email_id=so.`owner_email_id` "
			+" JOIN employee_details lemp ON lemp.email_id=`last_modified_by_email_id` WHERE "
			+ "so.`total_number_of_item`-so.`total_number_of_item_assigned`<>0",nativeQuery = true,countQuery = "SELECT count(*) as ct "
			+" FROM `bt_service_order` so  "
			+" JOIN location dloc ON dloc.location_id = so.`destination_location_location_id` "
			+" JOIN location sloc ON sloc.location_id = so.`source_location_location_id` "
			+" JOIN employee_details cemp ON cemp.email_id=so.`created_by_email_id` "
			+" JOIN employee_details oemp ON oemp.email_id=so.`owner_email_id` "
			+" JOIN employee_details lemp ON lemp.email_id=`last_modified_by_email_id` WHERE "
			+ "so.`total_number_of_item`-so.`total_number_of_item_assigned`<>0")
	Page<Map<String, Object>> getPartiallyAssigned(long locationId, Pageable page);

	@Query(value = "SELECT so.id AS serviceOrderId, "
			+" so.`created_date` AS searviceOrderCreatedDate, "
			+" so.`last_modified_date` AS serviceOrderLastModifiedDate, "
			+" so.`manual_id` AS serviceOrderManualId, "
			+" so.`shipping_mode` AS searviceOrderShippingMode, "
			+" so.`shipping_to` AS serviceOrderShippingTo, "
			+" so.`total_number_of_item` AS searviceOrderTotalNumberOfItem, "
			+" so.`total_number_of_item_assigned` AS serviceOrderTotalNumberOfItemAssigned, "
			+" so.`total_number_of_item_assigned_returned` AS serviceOrderTotalNumberOfItemAssignedReturned, "
			+" so.`total_number_of_item_received` AS searviceOrderTotalNumberOfItemReceived, "
			+" so.`total_number_of_item_returned` AS searviceOrderTotalNumberOfItemReturned, "
			+" so.`tracking_number` AS searviceOrderTrackingNumber, "
			+" so.`created_by_email_id` searviceOrderCreatedByEmailId, "
			+" so.`destination_location_location_id` AS serviceOrderRepairCenterLocationId, "
			+" so.`owner_email_id` AS serviceOrderOwnerEmailid, "
			+" so.`source_location_location_id` AS serviceOrderSourceLocationId, "

			+" cemp.`first_name` as createdByFirstName, "
			+" cemp.`middle_name` as createdByMiddleName,"
			+" cemp.`last_name` as createdByLastName, "

			+" lemp.`first_name` as lastModifiedByFirstName, "
			+" lemp.`middle_name` as lastModifiedByMiddleName,"
			+" lemp.`last_name` as lastModifiedByLastName, "

			+" oemp.`first_name` as ownerFirstName, "
			+" oemp.`middle_name` as ownerMiddleName,"
			+" oemp.`last_name` as ownerByLastName, "
			
			+" dloc.location_name AS serviceOrderRepairCenterLocationName, "
			+" dloc.plant AS serviceOrderRepairCenterLocationPlant, "
			+" sloc.location_name AS serviceOrderSourceLocationName, "
			+" sloc.plant AS serviceOrderSourceLocationPlant "
			
			
			+" FROM `bt_service_order` so  "
			+" JOIN location dloc ON dloc.location_id = so.`destination_location_location_id` "
			+" JOIN location sloc ON sloc.location_id = so.`source_location_location_id` "
			+" JOIN employee_details cemp ON cemp.email_id=so.`created_by_email_id` "
			+" JOIN employee_details oemp ON oemp.email_id=so.`owner_email_id` "
			+" JOIN employee_details lemp ON lemp.email_id=`last_modified_by_email_id` WHERE "
			+ "so.`total_number_of_item`-so.`total_number_of_item_assigned`=0",nativeQuery = true,countQuery = "SELECT count(*) as ct "
			+" FROM `bt_service_order` so  "
			+" JOIN location dloc ON dloc.location_id = so.`destination_location_location_id` "
			+" JOIN location sloc ON sloc.location_id = so.`source_location_location_id` "
			+" JOIN employee_details cemp ON cemp.email_id=so.`created_by_email_id` "
			+" JOIN employee_details oemp ON oemp.email_id=so.`owner_email_id` "
			+" JOIN employee_details lemp ON lemp.email_id=`last_modified_by_email_id` WHERE "
			+" so.`total_number_of_item`-so.`total_number_of_item_assigned`=0")
	Page<Map<String, Object>> getFullyAssigned(long locationId, Pageable page);
    
}
