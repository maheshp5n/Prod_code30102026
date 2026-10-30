	package com.pratikshat.exicom.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.repository.custom.BtProductRequestRepositoryCustome;


@Repository
public interface BtProductRequestRepository extends JpaRepository<com.pratikshat.exicom.entity.BtProductRequest, Long>,BtProductRequestRepositoryCustome {
	@Query(value="SELECT pr.id AS productRequestId, \r\n" + 
			"pr.`created_date` AS productRequestCreatedDate,\r\n" + 
			"pr.`last_modified_date` AS productRequestLastModifiedDate,\r\n" + 
			"pr.`bt_product_request_status` AS productRequestStatus,\r\n" + 
			"pr.`description` AS productRequestDescription,\r\n" + 
			"pr.`dummy_status` AS productRequestDummayStatus,\r\n" + 
			"pr.`manual_id` AS productRequestManualId,\r\n" + 
			"pr.`need_by_date_time` AS productRequestNeedByDateTime,\r\n" + 
			"pr.`synced` AS productRequestScynced,\r\n" + 
			"pr.`total_quantity_issued` AS productRequestTotalQuantityIssued,\r\n" + 
			"pr.`total_quantity_received` AS productRequestTotalQuantityReceived,\r\n" + 
			"pr.`total_quantity_required` AS productRequestTotalQuantityRequired,\r\n" + 
			"pr.`created_by_email_id` AS productRequestCreatedByEmailId,\r\n" + 
			"pr.`last_modified_by_email_id` AS productRequestLastMdifiedByEmailId,\r\n" + 
			"pr.`destination_location_location_id` AS productRequestDestinationLocationId,\r\n" + 
			"pr.`owner_details_email_id` AS productRequestOwnerEmailId,\r\n" + 
			"pr.`source_location_location_id` AS productRequestSourceLocationId,\r\n" + 
			"sloc.location_name AS productRequestSourceLocationName,\r\n" + 
			"dloc.location_name AS productRequestDestinationLocationName,\r\n" + 
			"sloc.plant AS productRequestSourceLocationPlant,\r\n" + 
			
			" cemp.`first_name` AS createdByFirstName,"+
			" cemp.`last_name` AS createdByLastName,"+
			" cemp.`middle_name` AS createdByMiddleName,"+
			
			" memp.`first_name` AS lastModifiedByFirstName,"+
			" memp.`last_name` AS lastModifiedByLastName,"+
			" memp.`middle_name` AS lastModifiedByMiddleName,"+
			
			" oemp.`first_name` AS ownerFirstName,"+
			" oemp.`last_name` AS ownerLastName,"+
			" oemp.`middle_name` AS ownerMiddleName,"+

			"dloc.plant AS productRequestDestinationLocationPlant,"
			+ "pr.submitted_date AS productRequestSubmittedDate \r\n" + 
			"\r\n" + 
			"FROM `bt_product_request` pr \r\n" + 
			"JOIN employee_details cemp ON cemp.email_id=pr.`created_by_email_id` "+
			" JOIN employee_details memp ON memp.email_id=pr.`last_modified_by_email_id` "+
			" JOIN employee_details oemp ON oemp.email_id = pr.`owner_details_email_id` "+
			" JOIN `location` sloc ON sloc.location_id =pr.`source_location_location_id` \r\n" + 
			"JOIN location dloc ON pr.`destination_location_location_id`=dloc.location_id where sloc.location_id in :locationIds or dloc.location_id in :locationIds",
			nativeQuery=true,countQuery = "SELECT count(*) as ct" + 
					"\r\n" + 
					"FROM `bt_product_request` pr \r\n" + 
					"JOIN employee_details cemp ON cemp.email_id=pr.`created_by_email_id` "+
					" JOIN employee_details memp ON memp.email_id=pr.`last_modified_by_email_id` "+
					" JOIN employee_details oemp ON oemp.email_id = pr.`owner_details_email_id` "+
					"JOIN `location` sloc ON sloc.location_id =pr.`source_location_location_id` \r\n" + 
					"JOIN location dloc ON pr.`destination_location_location_id`=dloc.location_id where sloc.location_id in :locationIds or dloc.location_id in :locationIds")
	public Page<Map<String,Object>> getProductRequest(@Param("locationIds")List<Long> locationIds,Pageable page);
	
	@Query(value="SELECT pr.id AS productRequestId, \r\n" + 
			"pr.`created_date` AS productRequestCreatedDate,\r\n" + 
			"pr.`last_modified_date` AS productRequestLastModifiedDate,\r\n" + 
			"pr.`bt_product_request_status` AS productRequestStatus,\r\n" + 
			"pr.`description` AS productRequestDescription,\r\n" + 
			"pr.`dummy_status` AS productRequestDummayStatus,\r\n" + 
			"pr.`manual_id` AS productRequestManualId,\r\n" + 
			"pr.`need_by_date_time` AS productRequestNeedByDateTime,\r\n" + 
			"pr.`synced` AS productRequestScynced,\r\n" + 
			"pr.`total_quantity_issued` AS productRequestTotalQuantityIssued,\r\n" + 
			"pr.`total_quantity_received` AS productRequestTotalQuantityReceived,\r\n" + 
			"pr.`total_quantity_required` AS productRequestTotalQuantityRequired,\r\n" + 
			"pr.`created_by_email_id` AS productRequestCreatedByEmailId,\r\n" + 
			"pr.`last_modified_by_email_id` AS productRequestLastMdifiedByEmailId,\r\n" + 
			"pr.`destination_location_location_id` AS productRequestDestinationLocationId,\r\n" + 
			"pr.`owner_details_email_id` AS productRequestOwnerEmailId,\r\n" + 
			"pr.`source_location_location_id` AS productRequestSourceLocationId,\r\n" + 
			"sloc.location_name AS productRequestSourceLocationName,\r\n" + 
			"dloc.location_name AS productRequestDestinationLocationName,\r\n" + 
			"sloc.plant AS productRequestSourceLocationPlant,\r\n" + 
			" cemp.`first_name` AS createdByFirstName,"+
			" cemp.`last_name` AS createdByLastName,"+
			" cemp.`middle_name` AS createdByMiddleName,"+
			
			" memp.`first_name` AS lastModifiedByFirstName,"+
			" memp.`last_name` AS lastModifiedByLastName,"+
			" memp.`middle_name` AS lastModifiedByMiddleName,"+
			
			" oemp.`first_name` AS ownerFirstName,"+
			" oemp.`last_name` AS ownerLastName,"+
			" oemp.`middle_name` AS ownerMiddleName,"+
			"dloc.plant AS productRequestDestinationLocationPlant,"
			+ "pr.submitted_date AS productRequestSubmittedDate \r\n" + 
			"\r\n" + 
			"FROM `bt_product_request` pr \r\n" + 
			"JOIN employee_details cemp ON cemp.email_id=pr.`created_by_email_id` "+
			" JOIN employee_details memp ON memp.email_id=pr.`last_modified_by_email_id` "+
			" JOIN employee_details oemp ON oemp.email_id = pr.`owner_details_email_id` "+
			"JOIN `location` sloc ON sloc.location_id =pr.`source_location_location_id` \r\n" + 
			"JOIN location dloc ON pr.`destination_location_location_id`=dloc.location_id",
			nativeQuery=true,countQuery = "SELECT count(*) as ct" + 
					"\r\n" + 
					"FROM `bt_product_request` pr \r\n" + 
					"JOIN employee_details cemp ON cemp.email_id=pr.`created_by_email_id` "+
					" JOIN employee_details memp ON memp.email_id=pr.`last_modified_by_email_id` "+
					" JOIN employee_details oemp ON oemp.email_id = pr.`owner_details_email_id` "+
					"JOIN `location` sloc ON sloc.location_id =pr.`source_location_location_id` \r\n" + 
					"JOIN location dloc ON pr.`destination_location_location_id`=dloc.location_id")
	public Page<Map<String,Object>> getProductRequest(Pageable page);
	
	@Query(value="SELECT pr.id AS productRequestId, \r\n" + 
			"pr.`created_date` AS productRequestCreatedDate,\r\n" + 
			"pr.`last_modified_date` AS productRequestLastModifiedDate,\r\n" + 
			"pr.`bt_product_request_status` AS productRequestStatus,\r\n" + 
			"pr.`description` AS productRequestDescription,\r\n" + 
			"pr.`dummy_status` AS productRequestDummayStatus,\r\n" + 
			"pr.`manual_id` AS productRequestManualId,\r\n" + 
			"pr.`need_by_date_time` AS productRequestNeedByDateTime,\r\n" + 
			"pr.`synced` AS productRequestScynced,\r\n" + 
			"pr.`total_quantity_issued` AS productRequestTotalQuantityIssued,\r\n" + 
			"pr.`total_quantity_received` AS productRequestTotalQuantityReceived,\r\n" + 
			"pr.`total_quantity_required` AS productRequestTotalQuantityRequired,\r\n" + 
			"pr.`created_by_email_id` AS productRequestCreatedByEmailId,\r\n" + 
			"pr.`last_modified_by_email_id` AS productRequestLastMdifiedByEmailId,\r\n" + 
			"pr.`destination_location_location_id` AS productRequestDestinationLocationId,\r\n" + 
			"pr.`owner_details_email_id` AS productRequestOwnerEmailId,\r\n" + 
			"pr.`source_location_location_id` AS productRequestSourceLocationId,\r\n" + 
			"sloc.location_name AS productRequestSourceLocationName,\r\n" + 
			"dloc.location_name AS productRequestDestinationLocationName,\r\n" + 
			"sloc.plant AS productRequestSourceLocationPlant,\r\n" + 
			" cemp.`first_name` AS createdByFirstName,"+
			" cemp.`last_name` AS createdByLastName,"+
			" cemp.`middle_name` AS createdByMiddleName,"+
			
			" memp.`first_name` AS lastModifiedByFirstName,"+
			" memp.`last_name` AS lastModifiedByLastName,"+
			" memp.`middle_name` AS lastModifiedByMiddleName,"+
			
			" oemp.`first_name` AS ownerFirstName,"+
			" oemp.`last_name` AS ownerLastName,"+
			" oemp.`middle_name` AS ownerMiddleName,"+
			"dloc.plant AS productRequestDestinationLocationPlant\r\n" + 
			"\r\n" + 
			"FROM `bt_product_request` pr \r\n" + 
			"JOIN employee_details cemp ON cemp.email_id=pr.`created_by_email_id` "+
			" JOIN employee_details memp ON memp.email_id=pr.`last_modified_by_email_id` "+
			" JOIN employee_details oemp ON oemp.email_id = pr.`owner_details_email_id` "+
			"JOIN `location` sloc ON sloc.location_id =pr.`source_location_location_id` \r\n" + 
			"JOIN location dloc ON pr.`destination_location_location_id`=dloc.location_id where (sloc.location_id in :locationIds or dloc.location_id in :locationIds) and pr.dummy_status in :productRequestStatus",
			nativeQuery=true,countQuery = "SELECT count(*) as ct" + 
					"\r\n" + 
					"FROM `bt_product_request` pr \r\n" + 
					"JOIN employee_details cemp ON cemp.email_id=pr.`created_by_email_id` "+
					" JOIN employee_details memp ON memp.email_id=pr.`last_modified_by_email_id` "+
					" JOIN employee_details oemp ON oemp.email_id = pr.`owner_details_email_id` "+
					"JOIN `location` sloc ON sloc.location_id =pr.`source_location_location_id` \r\n" + 
					"JOIN location dloc ON pr.`destination_location_location_id`=dloc.location_id where (sloc.location_id in :locationIds or dloc.location_id in :locationIds) and pr.dummy_status in :productRequestStatus")
	public Page<Map<String,Object>> getProductRequest(@Param("locationIds")List<Long> locationIds,@Param("productRequestStatus")List<String> status,Pageable page);
	
	
	@Query(value="SELECT pr.id AS productRequestId, \r\n" + 
			"pr.`created_date` AS productRequestCreatedDate,\r\n" + 
			"pr.`last_modified_date` AS productRequestLastModifiedDate,\r\n" + 
			"pr.`bt_product_request_status` AS productRequestStatus,\r\n" + 
			"pr.`description` AS productRequestDescription,\r\n" + 
			"pr.`dummy_status` AS productRequestDummayStatus,\r\n" + 
			"pr.`manual_id` AS productRequestManualId,\r\n" + 
			"pr.`need_by_date_time` AS productRequestNeedByDateTime,\r\n" + 
			"pr.`synced` AS productRequestScynced,\r\n" + 
			"pr.`total_quantity_issued` AS productRequestTotalQuantityIssued,\r\n" + 
			"pr.`total_quantity_received` AS productRequestTotalQuantityReceived,\r\n" + 
			"pr.`total_quantity_required` AS productRequestTotalQuantityRequired,\r\n" + 
			"pr.`created_by_email_id` AS productRequestCreatedByEmailId,\r\n" + 
			"pr.`last_modified_by_email_id` AS productRequestLastMdifiedByEmailId,\r\n" + 
			"pr.`destination_location_location_id` AS productRequestDestinationLocationId,\r\n" + 
			"pr.`owner_details_email_id` AS productRequestOwnerEmailId,\r\n" + 
			"pr.`source_location_location_id` AS productRequestSourceLocationId,\r\n" + 
			"sloc.location_name AS productRequestSourceLocationName,\r\n" + 
			"dloc.location_name AS productRequestDestinationLocationName,\r\n" + 
			"sloc.plant AS productRequestSourceLocationPlant,\r\n" + 
			" cemp.`first_name` AS createdByFirstName,"+
			" cemp.`last_name` AS createdByLastName,"+
			" cemp.`middle_name` AS createdByMiddleName,"+
			
			" memp.`first_name` AS lastModifiedByFirstName,"+
			" memp.`last_name` AS lastModifiedByLastName,"+
			" memp.`middle_name` AS lastModifiedByMiddleName,"+
			
			" oemp.`first_name` AS ownerFirstName,"+
			" oemp.`last_name` AS ownerLastName,"+
			" oemp.`middle_name` AS ownerMiddleName,"+
			"dloc.plant AS productRequestDestinationLocationPlant,"
			+ "pr.submitted_date AS productRequestSubmittedDate \r\n" + 
			"\r\n" + 
			"FROM `bt_product_request` pr \r\n" + 
			"JOIN employee_details cemp ON cemp.email_id=pr.`created_by_email_id` "+
			" JOIN employee_details memp ON memp.email_id=pr.`last_modified_by_email_id` "+
			" JOIN employee_details oemp ON oemp.email_id = pr.`owner_details_email_id` "+
			"JOIN `location` sloc ON sloc.location_id =pr.`source_location_location_id` \r\n" + 
			"JOIN location dloc ON pr.`destination_location_location_id`=dloc.location_id where pr.dummy_status in :productRequestStatus",
			nativeQuery=true,countQuery = "SELECT count(*) as ct" + 
					"\r\n" + 
					"FROM `bt_product_request` pr \r\n" + 
					"JOIN employee_details cemp ON cemp.email_id=pr.`created_by_email_id` "+
					" JOIN employee_details memp ON memp.email_id=pr.`last_modified_by_email_id` "+
					" JOIN employee_details oemp ON oemp.email_id = pr.`owner_details_email_id` "+
					"JOIN `location` sloc ON sloc.location_id =pr.`source_location_location_id` \r\n" + 
					"JOIN location dloc ON pr.`destination_location_location_id`=dloc.location_id where pr.dummy_status in :productRequestStatus")
	public Page<Map<String,Object>> getProductRequestByStatus(@Param("productRequestStatus")List<String> status,Pageable page);
	
	@Query(value="SELECT pr.id AS productRequestId, \r\n" + 
			"pr.`created_date` AS productRequestCreatedDate,\r\n" + 
			"pr.`last_modified_date` AS productRequestLastModifiedDate,\r\n" + 
			"pr.`bt_product_request_status` AS productRequestStatus,\r\n" + 
			"pr.`description` AS productRequestDescription,\r\n" + 
			"pr.`dummy_status` AS productRequestDummayStatus,\r\n" + 
			"pr.`manual_id` AS productRequestManualId,\r\n" + 
			"pr.`need_by_date_time` AS productRequestNeedByDateTime,\r\n" + 
			"pr.`synced` AS productRequestScynced,\r\n" + 
			"pr.`total_quantity_issued` AS productRequestTotalQuantityIssued,\r\n" + 
			"pr.`total_quantity_received` AS productRequestTotalQuantityReceived,\r\n" + 
			"pr.`total_quantity_required` AS productRequestTotalQuantityRequired,\r\n" + 
			"pr.`created_by_email_id` AS productRequestCreatedByEmailId,\r\n" + 
			"pr.`last_modified_by_email_id` AS productRequestLastMdifiedByEmailId,\r\n" + 
			"pr.`destination_location_location_id` AS productRequestDestinationLocationId,\r\n" + 
			"pr.`owner_details_email_id` AS productRequestOwnerEmailId,\r\n" + 
			"pr.`source_location_location_id` AS productRequestSourceLocationId,\r\n" + 
			"sloc.location_name AS productRequestSourceLocationName,\r\n" + 
			"dloc.location_name AS productRequestDestinationLocationName,\r\n" + 
			"sloc.plant AS productRequestSourceLocationPlant,\r\n" + 
			" cemp.`first_name` AS createdByFirstName,"+
			" cemp.`last_name` AS createdByLastName,"+
			" cemp.`middle_name` AS createdByMiddleName,"+
			
			" memp.`first_name` AS lastModifiedByFirstName,"+
			" memp.`last_name` AS lastModifiedByLastName,"+
			" memp.`middle_name` AS lastModifiedByMiddleName,"+
			
			" oemp.`first_name` AS ownerFirstName,"+
			" oemp.`last_name` AS ownerLastName,"+
			" oemp.`middle_name` AS ownerMiddleName,"+
			"dloc.plant AS productRequestDestinationLocationPlant,"
			+ "pr.submitted_date AS productRequestSubmittedDate \r\n" + 
			"\r\n" + 
			"FROM `bt_product_request` pr \r\n" + 
			"JOIN employee_details cemp ON cemp.email_id=pr.`created_by_email_id` "+
			" JOIN employee_details memp ON memp.email_id=pr.`last_modified_by_email_id` "+
			" JOIN employee_details oemp ON oemp.email_id = pr.`owner_details_email_id` "+
			"JOIN `location` sloc ON sloc.location_id =pr.`source_location_location_id` \r\n" + 
			"JOIN location dloc ON pr.`destination_location_location_id`=dloc.location_id where dloc.location_id =:locationId",
			nativeQuery=true,countQuery = "SELECT count(*) as ct" + 
					"\r\n" + 
					"FROM `bt_product_request` pr \r\n" + 
					"JOIN employee_details cemp ON cemp.email_id=pr.`created_by_email_id` "+
					" JOIN employee_details memp ON memp.email_id=pr.`last_modified_by_email_id` "+
					" JOIN employee_details oemp ON oemp.email_id = pr.`owner_details_email_id` "+
					"JOIN `location` sloc ON sloc.location_id =pr.`source_location_location_id` \r\n" + 
					"JOIN location dloc ON pr.`destination_location_location_id`=dloc.location_id where dloc.location_id =:locationId")
	public Page<Map<String,Object>> getProductRequestSendByMe(@Param("locationId")Long locationIds,Pageable page);


	@Query(value="SELECT pr.id AS productRequestId, \r\n" + 
			"pr.`created_date` AS productRequestCreatedDate,\r\n" + 
			"pr.`last_modified_date` AS productRequestLastModifiedDate,\r\n" + 
			"pr.`bt_product_request_status` AS productRequestStatus,\r\n" + 
			"pr.`description` AS productRequestDescription,\r\n" + 
			"pr.`dummy_status` AS productRequestDummayStatus,\r\n" + 
			"pr.`manual_id` AS productRequestManualId,\r\n" + 
			"pr.`need_by_date_time` AS productRequestNeedByDateTime,\r\n" + 
			"pr.`synced` AS productRequestScynced,\r\n" + 
			"pr.`total_quantity_issued` AS productRequestTotalQuantityIssued,\r\n" + 
			"pr.`total_quantity_received` AS productRequestTotalQuantityReceived,\r\n" + 
			"pr.`total_quantity_required` AS productRequestTotalQuantityRequired,\r\n" + 
			"pr.`created_by_email_id` AS productRequestCreatedByEmailId,\r\n" + 
			"pr.`last_modified_by_email_id` AS productRequestLastMdifiedByEmailId,\r\n" + 
			"pr.`destination_location_location_id` AS productRequestDestinationLocationId,\r\n" + 
			"pr.`owner_details_email_id` AS productRequestOwnerEmailId,\r\n" + 
			"pr.`source_location_location_id` AS productRequestSourceLocationId,\r\n" + 
			"sloc.location_name AS productRequestSourceLocationName,\r\n" + 
			"dloc.location_name AS productRequestDestinationLocationName,\r\n" + 
			"sloc.plant AS productRequestSourceLocationPlant,\r\n" + 
			" cemp.`first_name` AS createdByFirstName,"+
			" cemp.`last_name` AS createdByLastName,"+
			" cemp.`middle_name` AS createdByMiddleName,"+
			
			" memp.`first_name` AS lastModifiedByFirstName,"+
			" memp.`last_name` AS lastModifiedByLastName,"+
			" memp.`middle_name` AS lastModifiedByMiddleName,"+
			
			" oemp.`first_name` AS ownerFirstName,"+
			" oemp.`last_name` AS ownerLastName,"+
			" oemp.`middle_name` AS ownerMiddleName,"+
			"dloc.plant AS productRequestDestinationLocationPlant\r\n" + 
			"\r\n" + 
			"FROM `bt_product_request` pr \r\n" + 
			"JOIN employee_details cemp ON cemp.email_id=pr.`created_by_email_id` "+
			" JOIN employee_details memp ON memp.email_id=pr.`last_modified_by_email_id` "+
			" JOIN employee_details oemp ON oemp.email_id = pr.`owner_details_email_id` "+
			"JOIN `location` sloc ON sloc.location_id =pr.`source_location_location_id` \r\n" + 
			"JOIN location dloc ON pr.`destination_location_location_id`=dloc.location_id where dloc.location_id =:locationId and dloc.location_id in :locationIds",
			nativeQuery=true,countQuery = "SELECT count(*) as ct" + 
					"\r\n" + 
					"FROM `bt_product_request` pr \r\n" + 
					"JOIN employee_details cemp ON cemp.email_id=pr.`created_by_email_id` "+
					" JOIN employee_details memp ON memp.email_id=pr.`last_modified_by_email_id` "+
					" JOIN employee_details oemp ON oemp.email_id = pr.`owner_details_email_id` "+
					"JOIN `location` sloc ON sloc.location_id =pr.`source_location_location_id` \r\n" + 
					"JOIN location dloc ON pr.`destination_location_location_id`=dloc.location_id where dloc.location_id =:locationId and dloc.location_id in :locationIds")
	public Page<Map<String,Object>> getProductRequestSendByMe(@Param("locationId")Long locationId,@Param("locationIds")List<Long>locationIds,Pageable page);
	
	
	@Query(value="SELECT pr.id AS productRequestId, \r\n" + 
			"pr.`created_date` AS productRequestCreatedDate,\r\n" + 
			"pr.`last_modified_date` AS productRequestLastModifiedDate,\r\n" + 
			"pr.`bt_product_request_status` AS productRequestStatus,\r\n" + 
			"pr.`description` AS productRequestDescription,\r\n" + 
			"pr.`dummy_status` AS productRequestDummayStatus,\r\n" + 
			"pr.`manual_id` AS productRequestManualId,\r\n" + 
			"pr.`need_by_date_time` AS productRequestNeedByDateTime,\r\n" + 
			"pr.`synced` AS productRequestScynced,\r\n" + 
			"pr.`total_quantity_issued` AS productRequestTotalQuantityIssued,\r\n" + 
			"pr.`total_quantity_received` AS productRequestTotalQuantityReceived,\r\n" + 
			"pr.`total_quantity_required` AS productRequestTotalQuantityRequired,\r\n" + 
			"pr.`created_by_email_id` AS productRequestCreatedByEmailId,\r\n" + 
			"pr.`last_modified_by_email_id` AS productRequestLastMdifiedByEmailId,\r\n" + 
			"pr.`destination_location_location_id` AS productRequestDestinationLocationId,\r\n" + 
			"pr.`owner_details_email_id` AS productRequestOwnerEmailId,\r\n" + 
			"pr.`source_location_location_id` AS productRequestSourceLocationId,\r\n" + 
			"sloc.location_name AS productRequestSourceLocationName,\r\n" + 
			"dloc.location_name AS productRequestDestinationLocationName,\r\n" + 
			"sloc.plant AS productRequestSourceLocationPlant,\r\n" + 
			" cemp.`first_name` AS createdByFirstName,"+
			" cemp.`last_name` AS createdByLastName,"+
			" cemp.`middle_name` AS createdByMiddleName,"+
			
			" memp.`first_name` AS lastModifiedByFirstName,"+
			" memp.`last_name` AS lastModifiedByLastName,"+
			" memp.`middle_name` AS lastModifiedByMiddleName,"+
			
			" oemp.`first_name` AS ownerFirstName,"+
			" oemp.`last_name` AS ownerLastName,"+
			" oemp.`middle_name` AS ownerMiddleName,"+
			"dloc.plant AS productRequestDestinationLocationPlant,"
			+ "pr.submitted_date AS productRequestSubmittedDate \r\n" + 
			"\r\n" + 
			"FROM `bt_product_request` pr \r\n" + 
			"JOIN employee_details cemp ON cemp.email_id=pr.`created_by_email_id` "+
			" JOIN employee_details memp ON memp.email_id=pr.`last_modified_by_email_id` "+
			" JOIN employee_details oemp ON oemp.email_id = pr.`owner_details_email_id` "+
			"JOIN `location` sloc ON sloc.location_id =pr.`source_location_location_id` \r\n" + 
			"JOIN location dloc ON pr.`destination_location_location_id`=dloc.location_id where sloc.location_id =:locationId",
			nativeQuery=true,countQuery = "SELECT count(*) as ct" + 
					"\r\n" + 
					"FROM `bt_product_request` pr \r\n" + 
					"JOIN employee_details cemp ON cemp.email_id=pr.`created_by_email_id` "+
					" JOIN employee_details memp ON memp.email_id=pr.`last_modified_by_email_id` "+
					" JOIN employee_details oemp ON oemp.email_id = pr.`owner_details_email_id` "+
					"JOIN `location` sloc ON sloc.location_id =pr.`source_location_location_id` \r\n" + 
					"JOIN location dloc ON pr.`destination_location_location_id`=dloc.location_id where sloc.location_id =:locationId")
	public Page<Map<String,Object>> getProductRequestSendToMe(@Param("locationId")Long locationIds,Pageable page);

	@Query(value="SELECT pr.id AS productRequestId, \r\n" + 
			"pr.`created_date` AS productRequestCreatedDate,\r\n" + 
			"pr.`last_modified_date` AS productRequestLastModifiedDate,\r\n" + 
			"pr.`bt_product_request_status` AS productRequestStatus,\r\n" + 
			"pr.`description` AS productRequestDescription,\r\n" + 
			"pr.`dummy_status` AS productRequestDummayStatus,\r\n" + 
			"pr.`manual_id` AS productRequestManualId,\r\n" + 
			"pr.`need_by_date_time` AS productRequestNeedByDateTime,\r\n" + 
			"pr.`synced` AS productRequestScynced,\r\n" + 
			"pr.`total_quantity_issued` AS productRequestTotalQuantityIssued,\r\n" + 
			"pr.`total_quantity_received` AS productRequestTotalQuantityReceived,\r\n" + 
			"pr.`total_quantity_required` AS productRequestTotalQuantityRequired,\r\n" + 
			"pr.`created_by_email_id` AS productRequestCreatedByEmailId,\r\n" + 
			"pr.`last_modified_by_email_id` AS productRequestLastMdifiedByEmailId,\r\n" + 
			"pr.`destination_location_location_id` AS productRequestDestinationLocationId,\r\n" + 
			"pr.`owner_details_email_id` AS productRequestOwnerEmailId,\r\n" + 
			"pr.`source_location_location_id` AS productRequestSourceLocationId,\r\n" + 
			"sloc.location_name AS productRequestSourceLocationName,\r\n" + 
			"dloc.location_name AS productRequestDestinationLocationName,\r\n" + 
			"sloc.plant AS productRequestSourceLocationPlant,\r\n" + 
			" cemp.`first_name` AS createdByFirstName,"+
			" cemp.`last_name` AS createdByLastName,"+
			" cemp.`middle_name` AS createdByMiddleName,"+
			
			" memp.`first_name` AS lastModifiedByFirstName,"+
			" memp.`last_name` AS lastModifiedByLastName,"+
			" memp.`middle_name` AS lastModifiedByMiddleName,"+
			
			" oemp.`first_name` AS ownerFirstName,"+
			" oemp.`last_name` AS ownerLastName,"+
			" oemp.`middle_name` AS ownerMiddleName,"+
			"dloc.plant AS productRequestDestinationLocationPlant\r\n" + 
			"\r\n" + 
			"FROM `bt_product_request` pr \r\n" + 
			"JOIN employee_details cemp ON cemp.email_id=pr.`created_by_email_id` "+
			" JOIN employee_details memp ON memp.email_id=pr.`last_modified_by_email_id` "+
			" JOIN employee_details oemp ON oemp.email_id = pr.`owner_details_email_id` "+
			"JOIN `location` sloc ON sloc.location_id =pr.`source_location_location_id` \r\n" + 
			"JOIN location dloc ON pr.`destination_location_location_id`=dloc.location_id where sloc.location_id =:locationId and sloc.location_id in :locationIds",
			nativeQuery=true,countQuery = "SELECT count(*) as ct" + 
					"\r\n" + 
					"FROM `bt_product_request` pr \r\n" + 
					"JOIN employee_details cemp ON cemp.email_id=pr.`created_by_email_id` "+
					" JOIN employee_details memp ON memp.email_id=pr.`last_modified_by_email_id` "+
					" JOIN employee_details oemp ON oemp.email_id = pr.`owner_details_email_id` "+
					"JOIN `location` sloc ON sloc.location_id =pr.`source_location_location_id` \r\n" + 
					"JOIN location dloc ON pr.`destination_location_location_id`=dloc.location_id where sloc.location_id =:locationId and sloc.location_id in :locationIds")
	public Page<Map<String,Object>> getProductRequestSendToMe(@Param("locationId")Long locationId,@Param("locationIds")List<Long> locationIds,Pageable page);
	
	@Query(value="SELECT pr.id AS productRequestId, \r\n" + 
			"pr.`created_date` AS productRequestCreatedDate,\r\n" + 
			"pr.`last_modified_date` AS productRequestLastModifiedDate,\r\n" + 
			"pr.`bt_product_request_status` AS productRequestStatus,\r\n" + 
			"pr.`description` AS productRequestDescription,\r\n" + 
			"pr.`dummy_status` AS productRequestDummayStatus,\r\n" + 
			"pr.`manual_id` AS productRequestManualId,\r\n" + 
			"pr.`need_by_date_time` AS productRequestNeedByDateTime,\r\n" + 
			"pr.`synced` AS productRequestScynced,\r\n" + 
			"pr.`total_quantity_issued` AS productRequestTotalQuantityIssued,\r\n" + 
			"pr.`total_quantity_received` AS productRequestTotalQuantityReceived,\r\n" + 
			"pr.`total_quantity_required` AS productRequestTotalQuantityRequired,\r\n" + 
			"pr.`created_by_email_id` AS productRequestCreatedByEmailId,\r\n" + 
			"pr.`last_modified_by_email_id` AS productRequestLastMdifiedByEmailId,\r\n" + 
			"pr.`destination_location_location_id` AS productRequestDestinationLocationId,\r\n" + 
			"pr.`owner_details_email_id` AS productRequestOwnerEmailId,\r\n" + 
			"pr.`source_location_location_id` AS productRequestSourceLocationId,\r\n" + 
			"sloc.location_name AS productRequestSourceLocationName,\r\n" + 
			"dloc.location_name AS productRequestDestinationLocationName,\r\n" + 
			"sloc.plant AS productRequestSourceLocationPlant,\r\n" + 
			" cemp.`first_name` AS createdByFirstName,"+
			" cemp.`last_name` AS createdByLastName,"+
			" cemp.`middle_name` AS createdByMiddleName,"+
			
			" memp.`first_name` AS lastModifiedByFirstName,"+
			" memp.`last_name` AS lastModifiedByLastName,"+
			" memp.`middle_name` AS lastModifiedByMiddleName,"+
			
			" oemp.`first_name` AS ownerFirstName,"+
			" oemp.`last_name` AS ownerLastName,"+
			" oemp.`middle_name` AS ownerMiddleName,"+
			"dloc.plant AS productRequestDestinationLocationPlant,"
			+ "pr.submitted_date AS productRequestSubmittedDate \r\n" + 
			"\r\n" + 
			"FROM `bt_product_request` pr \r\n" + 
			"JOIN employee_details cemp ON cemp.email_id=pr.`created_by_email_id` "+
			" JOIN employee_details memp ON memp.email_id=pr.`last_modified_by_email_id` "+
			" JOIN employee_details oemp ON oemp.email_id = pr.`owner_details_email_id` "+
			"JOIN `location` sloc ON sloc.location_id =pr.`source_location_location_id` \r\n" + 
			"JOIN location dloc ON pr.`destination_location_location_id`=dloc.location_id where pr.id=:id and (dloc.location_id in :locationIds or sloc.location_id in :locationIds)",nativeQuery=true)
	public Map<String,Object> getById(@Param("id")Long id,@Param("locationIds")List<Long> locationIds);
	
	@Query(value="SELECT pr.id AS productRequestId, \r\n" + 
			"pr.`created_date` AS productRequestCreatedDate,\r\n" + 
			"pr.`last_modified_date` AS productRequestLastModifiedDate,\r\n" + 
			"pr.`bt_product_request_status` AS productRequestStatus,\r\n" + 
			"pr.`description` AS productRequestDescription,\r\n" + 
			"pr.`dummy_status` AS productRequestDummayStatus,\r\n" + 
			"pr.`manual_id` AS productRequestManualId,\r\n" + 
			"pr.`need_by_date_time` AS productRequestNeedByDateTime,\r\n" + 
			"pr.`synced` AS productRequestScynced,\r\n" + 
			"pr.`total_quantity_issued` AS productRequestTotalQuantityIssued,\r\n" + 
			"pr.`total_quantity_received` AS productRequestTotalQuantityReceived,\r\n" + 
			"pr.`total_quantity_required` AS productRequestTotalQuantityRequired,\r\n" + 
			"pr.`created_by_email_id` AS productRequestCreatedByEmailId,\r\n" + 
			"pr.`last_modified_by_email_id` AS productRequestLastMdifiedByEmailId,\r\n" + 
			"pr.`destination_location_location_id` AS productRequestDestinationLocationId,\r\n" + 
			"pr.`owner_details_email_id` AS productRequestOwnerEmailId,\r\n" + 
			"pr.`source_location_location_id` AS productRequestSourceLocationId,\r\n" + 
			"sloc.location_name AS productRequestSourceLocationName,\r\n" + 
			"dloc.location_name AS productRequestDestinationLocationName,\r\n" + 
			"sloc.plant AS productRequestSourceLocationPlant,\r\n" + 
			" cemp.`first_name` AS createdByFirstName,"+
			" cemp.`last_name` AS createdByLastName,"+
			" cemp.`middle_name` AS createdByMiddleName,"+
			
			" memp.`first_name` AS lastModifiedByFirstName,"+
			" memp.`last_name` AS lastModifiedByLastName,"+
			" memp.`middle_name` AS lastModifiedByMiddleName,"+
			
			" oemp.`first_name` AS ownerFirstName,"+
			" oemp.`last_name` AS ownerLastName,"+
			" oemp.`middle_name` AS ownerMiddleName,"+
			"dloc.plant AS productRequestDestinationLocationPlant,"
			+ "pr.submitted_date AS productRequestSubmittedDate \r\n" + 
			"\r\n" + 
			"FROM `bt_product_request` pr \r\n" + 
			"JOIN employee_details cemp ON cemp.email_id=pr.`created_by_email_id` "+
			" JOIN employee_details memp ON memp.email_id=pr.`last_modified_by_email_id` "+
			" JOIN employee_details oemp ON oemp.email_id = pr.`owner_details_email_id` "+
			"JOIN `location` sloc ON sloc.location_id =pr.`source_location_location_id` \r\n" + 
			"JOIN location dloc ON pr.`destination_location_location_id`=dloc.location_id where pr.id=:id",nativeQuery=true)
	public Map<String,Object> getById(@Param("id")Long id);
	
	@Query(value="SELECT pr.id AS productRequestId, \r\n" + 
			"pr.`created_date` AS productRequestCreatedDate,\r\n" + 
			"pr.`last_modified_date` AS productRequestLastModifiedDate,\r\n" + 
			"pr.`bt_product_request_status` AS productRequestStatus,\r\n" + 
			"pr.`description` AS productRequestDescription,\r\n" + 
			"pr.`dummy_status` AS productRequestDummayStatus,\r\n" + 
			"pr.`manual_id` AS productRequestManualId,\r\n" + 
			"pr.`need_by_date_time` AS productRequestNeedByDateTime,\r\n" + 
			"pr.`synced` AS productRequestScynced,\r\n" + 
			"pr.`total_quantity_issued` AS productRequestTotalQuantityIssued,\r\n" + 
			"pr.`total_quantity_received` AS productRequestTotalQuantityReceived,\r\n" + 
			"pr.`total_quantity_required` AS productRequestTotalQuantityRequired,\r\n" + 
			"pr.`created_by_email_id` AS productRequestCreatedByEmailId,\r\n" + 
			"pr.`last_modified_by_email_id` AS productRequestLastMdifiedByEmailId,\r\n" + 
			"pr.`destination_location_location_id` AS productRequestDestinationLocationId,\r\n" + 
			"pr.`owner_details_email_id` AS productRequestOwnerEmailId,\r\n" + 
			"pr.`source_location_location_id` AS productRequestSourceLocationId,\r\n" + 
			"sloc.location_name AS productRequestSourceLocationName,\r\n" + 
			"dloc.location_name AS productRequestDestinationLocationName,\r\n" + 
			"sloc.plant AS productRequestSourceLocationPlant,\r\n" + 
			" cemp.`first_name` AS createdByFirstName,"+
			" cemp.`last_name` AS createdByLastName,"+
			" cemp.`middle_name` AS createdByMiddleName,"+
			
			" memp.`first_name` AS lastModifiedByFirstName,"+
			" memp.`last_name` AS lastModifiedByLastName,"+
			" memp.`middle_name` AS lastModifiedByMiddleName,"+
			
			" oemp.`first_name` AS ownerFirstName,"+
			" oemp.`last_name` AS ownerLastName,"+
			" oemp.`middle_name` AS ownerMiddleName,"+
			"dloc.plant AS productRequestDestinationLocationPlant\r\n" + 
			"\r\n" + 
			"FROM `bt_product_request` pr \r\n" + 
			"JOIN employee_details cemp ON cemp.email_id=pr.`created_by_email_id` "+
			" JOIN employee_details memp ON memp.email_id=pr.`last_modified_by_email_id` "+
			" JOIN employee_details oemp ON oemp.email_id = pr.`owner_details_email_id` "+
			"JOIN `location` sloc ON sloc.location_id =pr.`source_location_location_id` \r\n" + 
			"JOIN location dloc ON pr.`destination_location_location_id`=dloc.location_id \r\n" + 
			"WHERE (sloc.location_location_id in :locationIds or dloc.location_location_id in :locationIds) and (pr.bt_product_request_status LIKE :searchKey OR pr.description LIKE :searchKey OR pr.dummy_status LIKE :searchKey OR pr.manual_id LIKE :searchKey OR sloc.location_name LIKE :searchKey OR dloc.location_name LIKE :searchKey)\r\n" + 
			"",nativeQuery=true)
	public List<Map<String,Object>> searchProductRequest(@Param("searchKey")String searchKey,@Param("locationIds")List<Long> locationIds);

	
	@Query(value="SELECT created_date AS `createdDate` ,`value` AS `newValue`,old_value  AS `oldValue` FROM object_log WHERE object_name='btproductrequest'  AND VALUE='Submitted' AND old_value='Draft' AND key_name='BtProductRequestStatus' AND object_id=15  ORDER BY `created_date` DESC ",nativeQuery = true)
	public List<Map<String, Object>> getProductRequestSubmittedDate(Long productRequestId);
	
	
	
	
}
