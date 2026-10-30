package com.pratikshat.exicom.repository;

import java.util.List;
import java.util.Map;

import com.pratikshat.exicom.entity.BtCustomerInventoryIn;
import com.pratikshat.exicom.entity.BtJobCard;
import com.pratikshat.exicom.repository.custom.BtJobCardRepositoryCustom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BtJobCardRepository extends JpaRepository<BtJobCard,Long>,BtJobCardRepositoryCustom{

	@Query("select jc from BtJobCard jc join jc.owner onr join jc.serviceOrderItem oitem join oitem.inCustomerInventory cinven where cinven=:brcnumber")
	List<BtJobCard> getByBrcCaseNumber(@Param("brcnumber") BtCustomerInventoryIn brcnumber);
    
    @Query(value = "SELECT "
    +" ct.`contact_id` AS contactId, "
    +" ct.`first_name` AS contactFirstName, "
    +" ct.`middle_name` AS cotactMiddleName, "
    +" ct.`last_name` AS contactLastName, "
    +" ct.`mobile` AS contactMobileNumber, "
    +" ct.`email_id` AS contactEmailId, "
	+" ct.`contact_designation_designation_id` as designationId"
    
    +" FROM `contact_details` ct JOIN `legal_entity` lg ON lg.`legal_entity_id`=ct.`legal_entity_id` JOIN `employee_details` emp ON emp.`email_id`=ct.email_id WHERE ct.`contact_type_type_id` IN (1,3) AND lg.`legal_entity_type_type_id`=2 AND lg.`legal_entity_id` IN (SELECT legal_entity_id FROM `contact_details` WHERE `email_id`=:emailId)",nativeQuery = true)
    public List<Map<String,Object>> getRepariEngineerList(@Param("emailId")String emailId);

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

    
	+" btin.`account_sap_number` AS accountSapNumber, "

	+" crd.`id` AS jobCardId, "
	+" crd.`manual_id` AS jobCardManualId, "
	+" crd.`qc_done` AS jobCardIsQcDone, "
	+" crd.`repair_date` AS jobCardRepariDate, "
	+" crd.`repair_product_state` AS jobCardRepairProductState, "
	+" crd.`repared` AS isJobCardRepaired, "
	+" crd.`return_date` AS jobCardReturnedDate, "
	+" crd.`returned_to_source` AS jobCardIsReturndToSource, "
	+" crd.`created_by_email_id` AS jobCardCreatedByEmailId, "
	+" crd.`last_modified_by_email_id` AS jobCardLastModifiedByEmailId, "
	+" crd.`assigned_to_email_id` AS jobCardAssignedToEmailId, "
	

	+" crd.`service_order_item_id` AS jobCardServiceOrderItemId, "
	+" crd.battery_ah_aft as batteryAhAft, "
	+" crd.battery_voltage_aft as batteryVoltageAft,"
	+" crd.battery_voltage_bef  as batteryVoltageBef, "
	+" crd.bt_balance_at as btBalanceAt, "
	+" crd.cell_bledge as cellBledge, "
	+" crd.cell_vmax as cellVmax, "
	+" crd.cell_vmin as cellVmin, "
	+" crd.corrective_action as correctiveAction, "
	+" crd.eng_remarks as engRemarks, "
	+" crd.eng_resolution_method as engResolutionMethod, "
	+" crd.ber_remarks as berRemarks, "
	+" crd.mat_sorteg as matSorteg, "
	+" crd.`observation1` as observation1, "
	+" crd.`observation2` AS observation2, "
	+" crd.`observation3` AS observation3, "
	+" crd.`observation4` AS observation4, "

	+" crd.created_date as jobCardCreatedDate, "
	+" crd.last_modified_date as jobCardLastModifiedDate, "
	+" crd.assigned_date as jobCardAssignedDate, "
	+" cemp.`first_name` AS createdByfirstName, "
	+" cemp.`last_name` AS createdBylastName, "
	+" cemp.`middle_name` AS createdBymiddleName, "

	+" oemp.`first_name` AS ownerFirstName, "
	+" oemp.`last_name` AS ownerLastName, "
	+" oemp.`middle_name` AS ownerMiddleName, "

	+" lemp.`first_name` AS lastModifiedByfirstName, "
	+" lemp.`last_name` AS lastModifiedBylastName, "
	+" lemp.`middle_name` AS lastModifiedByMiddleName, "


	+" btin.`challan_number` AS brcCaseChallanNumber,"

	+" btin.`customer_inventory_stage` AS brcCaseStage, "
	+" btin.`customer_inventory_status` AS brcCaseStatus "
	 
	+" FROM `bt_service_order` bts  "
	+" JOIN `bt_service_order_item` titem ON titem.`service_order_id`=bts.id  "
	+" JOIN bt_products prd ON prd.id=titem.product_id "
	+" JOIN location sloc ON sloc.location_id = bts.`source_location_location_id`  "
	+" JOIN employee_details aemp ON aemp.email_id=titem.`assigned_to_email_id` "
    +" JOIN `bt_customer_inventory_in` btin ON btin.id=titem.`in_customer_inventory_id`"
    +" JOIN bt_job_card crd on crd.id=titem.id "
	+" JOIN employee_details cemp on cemp.email_id = crd.created_by_email_id "
	+" JOIN employee_details oemp on oemp.email_id = crd.owner_email_id "
	+" JOIN employee_details lemp on lemp.email_id = crd.last_modified_by_email_id "
	
	

    +" where crd.assigned_to_email_id =:emailid",nativeQuery = true)
    List<Map<String,Object>> getListByUserId(@Param("emailid")String emailId);

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

    
	+" btin.`account_sap_number` AS accountSapNumber, "

	+" crd.`id` AS jobCardId, "
	+" crd.`manual_id` AS jobCardManualId, "
	+" crd.`qc_done` AS jobCardIsQcDone, "
	+" crd.`repair_date` AS jobCardRepariDate, "
	+" crd.`repair_product_state` AS jobCardRepairProductState, "
	+" crd.`repared` AS isJobCardRepaired, "
	+" crd.`return_date` AS jobCardReturnedDate, "
	+" crd.`returned_to_source` AS jobCardIsReturndToSource, "
	+" crd.`created_by_email_id` AS jobCardCreatedByEmailId, "
	+" crd.`last_modified_by_email_id` AS jobCardLastModifiedByEmailId, "
	+" crd.`assigned_to_email_id` AS jobCardAssignedToEmailId, "
	

	+" crd.`service_order_item_id` AS jobCardServiceOrderItemId, "
	+" crd.battery_ah_aft as batteryAhAft, "
	+" crd.battery_voltage_aft as batteryVoltageAft,"
	+" crd.battery_voltage_bef  as batteryVoltageBef, "
	+" crd.bt_balance_at as btBalanceAt, "
	+" crd.cell_bledge as cellBledge, "
	+" crd.cell_vmax as cellVmax, "
	+" crd.cell_vmin as cellVmin, "
	+" crd.corrective_action as correctiveAction, "
	+" crd.eng_remarks as engRemarks, "
	+" crd.eng_resolution_method as engResolutionMethod, "
	+" crd.ber_remarks as berRemarks, "
	+" crd.mat_sorteg as matSorteg, "
	+" crd.`observation1` as observation1, "
	+" crd.`observation2` AS observation2, "
	+" crd.`observation3` AS observation3, "
	+" crd.`observation4` AS observation4, "

	+" crd.created_date as jobCardCreatedDate, "
	+" crd.last_modified_date as jobCardLastModifiedDate, "
	+" crd.assigned_date as jobCardAssignedDate, "
	+" cemp.`first_name` AS createdByfirstName, "
	+" cemp.`last_name` AS createdBylastName, "
	+" cemp.`middle_name` AS createdBymiddleName, "

	+" oemp.`first_name` AS ownerFirstName, "
	+" oemp.`last_name` AS ownerLastName, "
	+" oemp.`middle_name` AS ownerMiddleName, "

	+" lemp.`first_name` AS lastModifiedByfirstName, "
	+" lemp.`last_name` AS lastModifiedBylastName, "
	+" lemp.`middle_name` AS lastModifiedByMiddleName, "


	+" btin.`challan_number` AS brcCaseChallanNumber,"

	+" btin.`customer_inventory_stage` AS brcCaseStage, "
	+" btin.`customer_inventory_status` AS brcCaseStatus "
	 
	+" FROM `bt_service_order` bts  "
	+" JOIN `bt_service_order_item` titem ON titem.`service_order_id`=bts.id  "
	+" JOIN bt_products prd ON prd.id=titem.product_id "
	+" JOIN location sloc ON sloc.location_id = bts.`source_location_location_id`  "
	+" JOIN employee_details aemp ON aemp.email_id=titem.`assigned_to_email_id` "
    +" JOIN `bt_customer_inventory_in` btin ON btin.id=titem.`in_customer_inventory_id`"
    +" JOIN bt_job_card crd on crd.id=titem.id "
	+" JOIN employee_details cemp on cemp.email_id = crd.created_by_email_id "
	+" JOIN employee_details oemp on oemp.email_id = crd.owner_email_id "
	+" JOIN employee_details lemp on lemp.email_id = crd.last_modified_by_email_id "
	
	

    +" where crd.assigned_to_email_id =:emailid",nativeQuery = true,countQuery =" select count(*) as ct FROM `bt_service_order` bts  "
	+" JOIN `bt_service_order_item` titem ON titem.`service_order_id`=bts.id  "
	+" JOIN bt_products prd ON prd.id=titem.product_id "
	+" JOIN location sloc ON sloc.location_id = bts.`source_location_location_id`  "
	+" JOIN employee_details aemp ON aemp.email_id=titem.`assigned_to_email_id` "
    +" JOIN `bt_customer_inventory_in` btin ON btin.id=titem.`in_customer_inventory_id`"
    +" JOIN bt_job_card crd on crd.id=titem.id "
	+" JOIN employee_details cemp on cemp.email_id = crd.created_by_email_id "
	+" JOIN employee_details oemp on oemp.email_id = crd.owner_email_id "
	+" JOIN employee_details lemp on lemp.email_id = crd.last_modified_by_email_id " )
    Page<Map<String,Object>> getAllPageByUserId(@Param("emailid")String emailId,Pageable page);

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

    
	+" btin.`account_sap_number` AS accountSapNumber, "

	+" crd.`id` AS jobCardId, "
	+" crd.`manual_id` AS jobCardManualId, "
	+" crd.`qc_done` AS jobCardIsQcDone, "
	+" crd.`repair_date` AS jobCardRepariDate, "
	+" crd.`repair_product_state` AS jobCardRepairProductState, "
	+" crd.`repared` AS isJobCardRepaired, "
	+" crd.`return_date` AS jobCardReturnedDate, "
	+" crd.`returned_to_source` AS jobCardIsReturndToSource, "
	+" crd.`created_by_email_id` AS jobCardCreatedByEmailId, "
	+" crd.`last_modified_by_email_id` AS jobCardLastModifiedByEmailId, "
	+" crd.`assigned_to_email_id` AS jobCardAssignedToEmailId, "
	

	+" crd.`service_order_item_id` AS jobCardServiceOrderItemId, "
	+" crd.battery_ah_aft as batteryAhAft, "
	+" crd.battery_voltage_aft as batteryVoltageAft,"
	+" crd.battery_voltage_bef  as batteryVoltageBef, "
	+" crd.bt_balance_at as btBalanceAt, "
	+" crd.cell_bledge as cellBledge, "
	+" crd.cell_vmax as cellVmax, "
	+" crd.cell_vmin as cellVmin, "
	+" crd.corrective_action as correctiveAction, "
	+" crd.eng_remarks as engRemarks, "
	+" crd.eng_resolution_method as engResolutionMethod, "
	+" crd.ber_remarks as berRemarks, "
	+" crd.mat_sorteg as matSorteg, "
	+" crd.`observation1` as observation1, "
	+" crd.`observation2` AS observation2, "
	+" crd.`observation3` AS observation3, "
	+" crd.`observation4` AS observation4, "

	+" crd.created_date as jobCardCreatedDate, "
	+" crd.last_modified_date as jobCardLastModifiedDate, "
	+" crd.assigned_date as jobCardAssignedDate, "
	+" cemp.`first_name` AS createdByfirstName, "
	+" cemp.`last_name` AS createdBylastName, "
	+" cemp.`middle_name` AS createdBymiddleName, "

	+" oemp.`first_name` AS ownerFirstName, "
	+" oemp.`last_name` AS ownerLastName, "
	+" oemp.`middle_name` AS ownerMiddleName, "

	+" lemp.`first_name` AS lastModifiedByfirstName, "
	+" lemp.`last_name` AS lastModifiedBylastName, "
	+" lemp.`middle_name` AS lastModifiedByMiddleName, "


	+" btin.`challan_number` AS brcCaseChallanNumber,"

	+" btin.`customer_inventory_stage` AS brcCaseStage, "
	+" btin.`customer_inventory_status` AS brcCaseStatus "
	 
	+" FROM `bt_service_order` bts  "
	+" JOIN `bt_service_order_item` titem ON titem.`service_order_id`=bts.id  "
	+" JOIN bt_products prd ON prd.id=titem.product_id "
	+" JOIN location sloc ON sloc.location_id = bts.`source_location_location_id`  "
	+" JOIN employee_details aemp ON aemp.email_id=titem.`assigned_to_email_id` "
    +" JOIN `bt_customer_inventory_in` btin ON btin.id=titem.`in_customer_inventory_id`"
    +" JOIN bt_job_card crd on crd.id=titem.id "
	+" JOIN employee_details cemp on cemp.email_id = crd.created_by_email_id "
	+" JOIN employee_details oemp on oemp.email_id = crd.owner_email_id "
	+" JOIN employee_details lemp on lemp.email_id = crd.last_modified_by_email_id "
    +" where crd.assigned_to_email_id =:emailid and  crd.repared=false",nativeQuery = true,countQuery =" select count(*) as ct FROM `bt_service_order` bts  "
	+" JOIN `bt_service_order_item` titem ON titem.`service_order_id`=bts.id  "
	+" JOIN bt_products prd ON prd.id=titem.product_id "
	+" JOIN location sloc ON sloc.location_id = bts.`source_location_location_id`  "
	+" JOIN employee_details aemp ON aemp.email_id=titem.`assigned_to_email_id` "
    +" JOIN `bt_customer_inventory_in` btin ON btin.id=titem.`in_customer_inventory_id`"
    +" JOIN bt_job_card crd on crd.id=titem.id "
	+" JOIN employee_details cemp on cemp.email_id = crd.created_by_email_id "
	+" JOIN employee_details oemp on oemp.email_id = crd.owner_email_id "
	+" JOIN employee_details lemp on lemp.email_id = crd.last_modified_by_email_id where  crd.repared=false and crd.assigned_to_email_id =:emailid" )
    Page<Map<String,Object>> getAllPageByUserIdPending(@Param("emailid")String emailId,Pageable page);

	@Query(value = "SELECT DISTINCT  "
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

    
	+" btin.`account_sap_number` AS accountSapNumber, "

	+" crd.`id` AS jobCardId, "
	+" crd.`manual_id` AS jobCardManualId, "
	+" crd.`qc_done` AS jobCardIsQcDone, "
	+" crd.`repair_date` AS jobCardRepariDate, "
	+" crd.`repair_product_state` AS jobCardRepairProductState, "
	+" crd.`repared` AS isJobCardRepaired, "
	+" crd.`return_date` AS jobCardReturnedDate, "
	+" crd.`returned_to_source` AS jobCardIsReturndToSource, "
	+" crd.`created_by_email_id` AS jobCardCreatedByEmailId, "
	+" crd.`last_modified_by_email_id` AS jobCardLastModifiedByEmailId, "
	+" crd.`assigned_to_email_id` AS jobCardAssignedToEmailId, "
	

	+" crd.`service_order_item_id` AS jobCardServiceOrderItemId, "
	+" crd.battery_ah_aft as batteryAhAft, "
	+" crd.battery_voltage_aft as batteryVoltageAft,"
	+" crd.battery_voltage_bef  as batteryVoltageBef, "
	+" crd.bt_balance_at as btBalanceAt, "
	+" crd.cell_bledge as cellBledge, "
	+" crd.cell_vmax as cellVmax, "
	+" crd.cell_vmin as cellVmin, "
	+" crd.corrective_action as correctiveAction, "
	+" crd.eng_remarks as engRemarks, "
	+" crd.eng_resolution_method as engResolutionMethod, "
	+" crd.ber_remarks as berRemarks, "
	+" crd.mat_sorteg as matSorteg, "
	+" crd.`observation1` as observation1, "
	+" crd.`observation2` AS observation2, "
	+" crd.`observation3` AS observation3, "
	+" crd.`observation4` AS observation4, "

	+" crd.created_date as jobCardCreatedDate, "
	+" crd.last_modified_date as jobCardLastModifiedDate, "
	+" crd.assigned_date as jobCardAssignedDate, "
	+" cemp.`first_name` AS createdByfirstName, "
	+" cemp.`last_name` AS createdBylastName, "
	+" cemp.`middle_name` AS createdBymiddleName, "

	+" oemp.`first_name` AS ownerFirstName, "
	+" oemp.`last_name` AS ownerLastName, "
	+" oemp.`middle_name` AS ownerMiddleName, "

	+" lemp.`first_name` AS lastModifiedByfirstName, "
	+" lemp.`last_name` AS lastModifiedBylastName, "
	+" lemp.`middle_name` AS lastModifiedByMiddleName, "


	+" btin.`challan_number` AS brcCaseChallanNumber,"

	+" btin.`customer_inventory_stage` AS brcCaseStage, "
	+" btin.`customer_inventory_status` AS brcCaseStatus "
	 
	+" FROM `bt_service_order` bts  "
	+" JOIN `bt_service_order_item` titem ON titem.`service_order_id`=bts.id  "
	+" JOIN bt_products prd ON prd.id=titem.product_id "
	+" JOIN location sloc ON sloc.location_id = bts.`source_location_location_id`  "
	+" JOIN employee_details aemp ON aemp.email_id=titem.`assigned_to_email_id` "
    +" JOIN `bt_customer_inventory_in` btin ON btin.id=titem.`in_customer_inventory_id`"
    +" JOIN bt_job_card crd on crd.id=titem.id "
	+" JOIN employee_details cemp on cemp.email_id = crd.created_by_email_id "
	+" JOIN employee_details oemp on oemp.email_id = crd.owner_email_id "
	+" JOIN employee_details lemp on lemp.email_id = crd.last_modified_by_email_id "
	+" JOIN bt_job_card_qc qc on qc.job_card_id=crd.id "
    +" where crd.assigned_to_email_id =:emailid and  crd.repared=false and qc.final_approval ='No'",
	nativeQuery = true,countQuery =" select count(DISTINCT  crd.id) as ct FROM `bt_service_order` bts  "
	+" JOIN `bt_service_order_item` titem ON titem.`service_order_id`=bts.id  "
	+" JOIN bt_products prd ON prd.id=titem.product_id "
	+" JOIN location sloc ON sloc.location_id = bts.`source_location_location_id`  "
	+" JOIN employee_details aemp ON aemp.email_id=titem.`assigned_to_email_id` "
    +" JOIN `bt_customer_inventory_in` btin ON btin.id=titem.`in_customer_inventory_id`"
    +" JOIN bt_job_card crd on crd.id=titem.id "
	+" JOIN employee_details cemp on cemp.email_id = crd.created_by_email_id "
	+" JOIN employee_details oemp on oemp.email_id = crd.owner_email_id "
	+" JOIN employee_details lemp on lemp.email_id = crd.last_modified_by_email_id where  crd.repared=false and crd.assigned_to_email_id =:emailid and qc.final_approval ='No'" )
    Page<Map<String,Object>> getAllPageByUserIdPendingRejected(@Param("emailid")String emailId,Pageable page);


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

    
	+" btin.`account_sap_number` AS accountSapNumber, "

	+" crd.`id` AS jobCardId, "
	+" crd.`manual_id` AS jobCardManualId, "
	+" crd.`qc_done` AS jobCardIsQcDone, "
	+" crd.`repair_date` AS jobCardRepariDate, "
	+" crd.`repair_product_state` AS jobCardRepairProductState, "
	+" crd.`repared` AS isJobCardRepaired, "
	+" crd.`return_date` AS jobCardReturnedDate, "
	+" crd.`returned_to_source` AS jobCardIsReturndToSource, "
	+" crd.`created_by_email_id` AS jobCardCreatedByEmailId, "
	+" crd.`last_modified_by_email_id` AS jobCardLastModifiedByEmailId, "
	+" crd.`assigned_to_email_id` AS jobCardAssignedToEmailId, "
	+" crd.`service_order_item_id` AS jobCardServiceOrderItemId, "
	+" crd.battery_ah_aft as batteryAhAft, "
	+" crd.battery_voltage_aft as batteryVoltageAft,"
	+" crd.battery_voltage_bef  as batteryVoltageBef, "
	+" crd.bt_balance_at as btBalanceAt, "
	+" crd.cell_bledge as cellBledge, "
	+" crd.cell_vmax as cellVmax, "
	+" crd.cell_vmin as cellVmin, "
	+" crd.corrective_action as correctiveAction, "
	+" crd.eng_remarks as engRemarks, "
	+" crd.eng_resolution_method as engResolutionMethod, "
	+" crd.ber_remarks as berRemarks, "
	+" crd.mat_sorteg as matSorteg, "
	+" crd.`observation1` as observation1, "
	+" crd.`observation2` AS observation2, "
	+" crd.`observation3` AS observation3, "
	+" crd.`observation4` AS observation4, "

	+" crd.created_date as jobCardCreatedDate, "
	+" crd.last_modified_date as jobCardLastModifiedDate, "
	+" crd.assigned_date as jobCardAssignedDate, "
	+" cemp.`first_name` AS createdByfirstName, "
	+" cemp.`last_name` AS createdBylastName, "
	+" cemp.`middle_name` AS createdBymiddleName, "

	+" oemp.`first_name` AS ownerFirstName, "
	+" oemp.`last_name` AS ownerLastName, "
	+" oemp.`middle_name` AS ownerMiddleName, "

	+" lemp.`first_name` AS lastModifiedByfirstName, "
	+" lemp.`last_name` AS lastModifiedBylastName, "
	+" lemp.`middle_name` AS lastModifiedByMiddleName, "

	
	+" btin.`challan_number` AS brcCaseChallanNumber,"

	+" btin.`customer_inventory_stage` AS brcCaseStage, "
	+" btin.`customer_inventory_status` AS brcCaseStatus "
	
	 
	+" FROM `bt_service_order` bts  "
	+" JOIN `bt_service_order_item` titem ON titem.`service_order_id`=bts.id  "
	+" JOIN bt_products prd ON prd.id=titem.product_id "
	+" JOIN location sloc ON sloc.location_id = bts.`source_location_location_id`  "
	+" JOIN employee_details aemp ON aemp.email_id=titem.`assigned_to_email_id` "
    +" JOIN `bt_customer_inventory_in` btin ON btin.id=titem.`in_customer_inventory_id`"
    +" JOIN bt_job_card crd on crd.id=titem.id "
	+" JOIN employee_details cemp on cemp.email_id = crd.created_by_email_id "
	+" JOIN employee_details oemp on oemp.email_id = crd.owner_email_id "
	+" JOIN employee_details lemp on lemp.email_id = crd.last_modified_by_email_id "

	,nativeQuery = true,countQuery = "SELECT count(*) "
	+" FROM `bt_service_order` bts   "
	+" JOIN `bt_service_order_item` titem ON titem.`service_order_id`=bts.id   "
	+" JOIN bt_products prd ON prd.id=titem.product_id  "
	+" JOIN location sloc ON sloc.location_id = bts.`source_location_location_id`   "
	+" JOIN employee_details aemp ON aemp.email_id=titem.`assigned_to_email_id`  "
	+" JOIN `bt_customer_inventory_in` btin ON btin.id=titem.`in_customer_inventory_id` "

	+" JOIN bt_job_card crd ON crd.id=titem.id "
	+" JOIN employee_details cemp on cemp.email_id = crd.created_by_email_id "
	+" JOIN employee_details oemp on oemp.email_id = crd.owner_email_id "
	+" JOIN employee_details lemp on lemp.email_id = crd.last_modified_by_email_id "
	)
	Page<Map<String,Object>> getPage(Pageable page);


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

    
	+" btin.`account_sap_number` AS accountSapNumber, "

	+" crd.`id` AS jobCardId, "
	+" crd.`manual_id` AS jobCardManualId, "
	+" crd.`qc_done` AS jobCardIsQcDone, "
	+" crd.`repair_date` AS jobCardRepariDate, "
	+" crd.`repair_product_state` AS jobCardRepairProductState, "
	+" crd.`repared` AS isJobCardRepaired, "
	+" crd.`return_date` AS jobCardReturnedDate, "
	+" crd.`returned_to_source` AS jobCardIsReturndToSource, "
	+" crd.`created_by_email_id` AS jobCardCreatedByEmailId, "
	+" crd.`last_modified_by_email_id` AS jobCardLastModifiedByEmailId, "
	+" crd.`assigned_to_email_id` AS jobCardAssignedToEmailId, "
	+" crd.`service_order_item_id` AS jobCardServiceOrderItemId, "
	+" crd.battery_ah_aft as batteryAhAft, "
	+" crd.battery_voltage_aft as batteryVoltageAft,"
	+" crd.battery_voltage_bef  as batteryVoltageBef, "
	+" crd.bt_balance_at as btBalanceAt, "
	+" crd.cell_bledge as cellBledge, "
	+" crd.cell_vmax as cellVmax, "
	+" crd.cell_vmin as cellVmin, "
	+" crd.corrective_action as correctiveAction, "
	+" crd.eng_remarks as engRemarks, "
	+" crd.eng_resolution_method as engResolutionMethod, "
	+" crd.ber_remarks as berRemarks, "
	+" crd.mat_sorteg as matSorteg, "
	+" crd.`observation1` as observation1, "
	+" crd.`observation2` AS observation2, "
	+" crd.`observation3` AS observation3, "
	+" crd.`observation4` AS observation4, "

	+" crd.created_date as jobCardCreatedDate, "
	+" crd.last_modified_date as jobCardLastModifiedDate, "
	+" crd.assigned_date as jobCardAssignedDate, "
	+" cemp.`first_name` AS createdByfirstName, "
	+" cemp.`last_name` AS createdBylastName, "
	+" cemp.`middle_name` AS createdBymiddleName, "

	+" oemp.`first_name` AS ownerFirstName, "
	+" oemp.`last_name` AS ownerLastName, "
	+" oemp.`middle_name` AS ownerMiddleName, "

	+" lemp.`first_name` AS lastModifiedByfirstName, "
	+" lemp.`last_name` AS lastModifiedBylastName, "
	+" lemp.`middle_name` AS lastModifiedByMiddleName, "


	+" btin.`challan_number` AS brcCaseChallanNumber,"

	+" btin.`customer_inventory_stage` AS brcCaseStage, "
	+" btin.`customer_inventory_status` AS brcCaseStatus "
	
	 
	+" FROM `bt_service_order` bts  "
	+" JOIN `bt_service_order_item` titem ON titem.`service_order_id`=bts.id  "
	+" JOIN bt_products prd ON prd.id=titem.product_id "
	+" JOIN location sloc ON sloc.location_id = bts.`source_location_location_id`  "
	+" JOIN employee_details aemp ON aemp.email_id=titem.`assigned_to_email_id` "
    +" JOIN `bt_customer_inventory_in` btin ON btin.id=titem.`in_customer_inventory_id`"
    +" JOIN bt_job_card crd on crd.id=titem.id "
	+" JOIN employee_details cemp on cemp.email_id = crd.created_by_email_id "
	+" JOIN employee_details oemp on oemp.email_id = crd.owner_email_id "
	+" JOIN employee_details lemp on lemp.email_id = crd.last_modified_by_email_id "
	
	+" where crd.assigned_to_email_id in :assignedtolist"
	,nativeQuery = true,countQuery = "SELECT count(*) "
	+" FROM `bt_service_order` bts   "
	+" JOIN `bt_service_order_item` titem ON titem.`service_order_id`=bts.id   "
	+" JOIN bt_products prd ON prd.id=titem.product_id  "
	+" JOIN location sloc ON sloc.location_id = bts.`source_location_location_id`   "
	+" JOIN employee_details aemp ON aemp.email_id=titem.`assigned_to_email_id`  "
	+" JOIN `bt_customer_inventory_in` btin ON btin.id=titem.`in_customer_inventory_id` "
	+" JOIN bt_job_card crd ON crd.id=titem.id "

	+" JOIN employee_details cemp on cemp.email_id = crd.created_by_email_id "
	+" JOIN employee_details oemp on oemp.email_id = crd.owner_email_id "
	+" JOIN employee_details lemp on lemp.email_id = crd.last_modified_by_email_id "
	
	+" where crd.assigned_to_email_id in :assignedtolist")
	Page<Map<String,Object>> getPage(@Param("assignedtolist") List<String> assignedToList, Pageable page);

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

    
	+" btin.`account_sap_number` AS accountSapNumber, "

	+" crd.`id` AS jobCardId, "
	+" crd.`manual_id` AS jobCardManualId, "
	+" crd.`qc_done` AS jobCardIsQcDone, "
	+" crd.`repair_date` AS jobCardRepariDate, "
	+" crd.`repair_product_state` AS jobCardRepairProductState, "
	+" crd.`repared` AS isJobCardRepaired, "
	+" crd.`return_date` AS jobCardReturnedDate, "
	+" crd.`returned_to_source` AS jobCardIsReturndToSource, "
	+" crd.`created_by_email_id` AS jobCardCreatedByEmailId, "
	+" crd.`last_modified_by_email_id` AS jobCardLastModifiedByEmailId, "
	+" crd.`assigned_to_email_id` AS jobCardAssignedToEmailId, "
	+" crd.`service_order_item_id` AS jobCardServiceOrderItemId, "
	+" crd.battery_ah_aft as batteryAhAft, "
	+" crd.battery_voltage_aft as batteryVoltageAft,"
	+" crd.battery_voltage_bef  as batteryVoltageBef, "
	+" crd.bt_balance_at as btBalanceAt, "
	+" crd.cell_bledge as cellBledge, "
	+" crd.cell_vmax as cellVmax, "
	+" crd.cell_vmin as cellVmin, "
	+" crd.corrective_action as correctiveAction, "
	+" crd.eng_remarks as engRemarks, "
	+" crd.eng_resolution_method as engResolutionMethod, "
	+" crd.ber_remarks as berRemarks, "
	+" crd.mat_sorteg as matSorteg, "
	+" crd.`observation1` as observation1, "
	+" crd.`observation2` AS observation2, "
	+" crd.`observation3` AS observation3, "
	+" crd.`observation4` AS observation4, "

	+" crd.created_date as jobCardCreatedDate, "
	+" crd.last_modified_date as jobCardLastModifiedDate, "
	+" crd.assigned_date as jobCardAssignedDate, "
	+" cemp.`first_name` AS createdByfirstName, "
	+" cemp.`last_name` AS createdBylastName, "
	+" cemp.`middle_name` AS createdBymiddleName, "

	+" oemp.`first_name` AS ownerFirstName, "
	+" oemp.`last_name` AS ownerLastName, "
	+" oemp.`middle_name` AS ownerMiddleName, "

	+" lemp.`first_name` AS lastModifiedByfirstName, "
	+" lemp.`last_name` AS lastModifiedBylastName, "
	+" lemp.`middle_name` AS lastModifiedByMiddleName, "


	+" btin.`challan_number` AS brcCaseChallanNumber,"

	+" btin.`customer_inventory_stage` AS brcCaseStage, "
	+" btin.`customer_inventory_status` AS brcCaseStatus "
	
	 
	+" FROM `bt_service_order` bts  "
	+" JOIN `bt_service_order_item` titem ON titem.`service_order_id`=bts.id  "
	+" JOIN bt_products prd ON prd.id=titem.product_id "
	+" JOIN location sloc ON sloc.location_id = bts.`source_location_location_id`  "
	+" JOIN employee_details aemp ON aemp.email_id=titem.`assigned_to_email_id` "
    +" JOIN `bt_customer_inventory_in` btin ON btin.id=titem.`in_customer_inventory_id`"
    +" JOIN bt_job_card crd on crd.id=titem.id "
	+" JOIN employee_details cemp on cemp.email_id = crd.created_by_email_id "
	+" JOIN employee_details oemp on oemp.email_id = crd.owner_email_id "
	+" JOIN employee_details lemp on lemp.email_id = crd.last_modified_by_email_id "
	
	+" where crd.repared=false",nativeQuery = true,countQuery = "SELECT count(*) "
	+" FROM `bt_service_order` bts   "
	+" JOIN `bt_service_order_item` titem ON titem.`service_order_id`=bts.id   "
	+" JOIN bt_products prd ON prd.id=titem.product_id  "
	+" JOIN location sloc ON sloc.location_id = bts.`source_location_location_id`   "
	+" JOIN employee_details aemp ON aemp.email_id=titem.`assigned_to_email_id`  "
	+" JOIN `bt_customer_inventory_in` btin ON btin.id=titem.`in_customer_inventory_id` "
	+" JOIN bt_job_card crd ON crd.id=titem.id "
	+" JOIN employee_details cemp on cemp.email_id = crd.created_by_email_id "
	+" JOIN employee_details oemp on oemp.email_id = crd.owner_email_id "
	+" JOIN employee_details lemp on lemp.email_id = crd.last_modified_by_email_id where crd.repared=false")
	Page<Map<String,Object>> getPagePendingToRepair(Pageable page);

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

    
	+" btin.`account_sap_number` AS accountSapNumber, "

	+" crd.`id` AS jobCardId, "
	+" crd.`manual_id` AS jobCardManualId, "
	+" crd.`qc_done` AS jobCardIsQcDone, "
	+" crd.`repair_date` AS jobCardRepariDate, "
	+" crd.`repair_product_state` AS jobCardRepairProductState, "
	+" crd.`repared` AS isJobCardRepaired, "
	+" crd.`return_date` AS jobCardReturnedDate, "
	+" crd.`returned_to_source` AS jobCardIsReturndToSource, "
	+" crd.`created_by_email_id` AS jobCardCreatedByEmailId, "
	+" crd.`last_modified_by_email_id` AS jobCardLastModifiedByEmailId, "
	+" crd.`assigned_to_email_id` AS jobCardAssignedToEmailId, "
	+" crd.`service_order_item_id` AS jobCardServiceOrderItemId, "
	+" crd.battery_ah_aft as batteryAhAft, "
	+" crd.battery_voltage_aft as batteryVoltageAft,"
	+" crd.battery_voltage_bef  as batteryVoltageBef, "
	+" crd.bt_balance_at as btBalanceAt, "
	+" crd.cell_bledge as cellBledge, "
	+" crd.cell_vmax as cellVmax, "
	+" crd.cell_vmin as cellVmin, "
	+" crd.corrective_action as correctiveAction, "
	+" crd.eng_remarks as engRemarks, "
	+" crd.eng_resolution_method as engResolutionMethod, "
	+" crd.ber_remarks as berRemarks, "
	+" crd.mat_sorteg as matSorteg, "
	+" crd.`observation1` as observation1, "
	+" crd.`observation2` AS observation2, "
	+" crd.`observation3` AS observation3, "
	+" crd.`observation4` AS observation4, "

	+" crd.created_date as jobCardCreatedDate, "
	+" crd.last_modified_date as jobCardLastModifiedDate, "
	+" crd.assigned_date as jobCardAssignedDate, "
	+" cemp.`first_name` AS createdByfirstName, "
	+" cemp.`last_name` AS createdBylastName, "
	+" cemp.`middle_name` AS createdBymiddleName, "

	+" oemp.`first_name` AS ownerFirstName, "
	+" oemp.`last_name` AS ownerLastName, "
	+" oemp.`middle_name` AS ownerMiddleName, "

	+" lemp.`first_name` AS lastModifiedByfirstName, "
	+" lemp.`last_name` AS lastModifiedBylastName, "
	+" lemp.`middle_name` AS lastModifiedByMiddleName, "


	+" btin.`challan_number` AS brcCaseChallanNumber,"

	+" btin.`customer_inventory_stage` AS brcCaseStage, "
	+" btin.`customer_inventory_status` AS brcCaseStatus "
	
	 
	+" FROM `bt_service_order` bts  "
	+" JOIN `bt_service_order_item` titem ON titem.`service_order_id`=bts.id  "
	+" JOIN bt_products prd ON prd.id=titem.product_id "
	+" JOIN location sloc ON sloc.location_id = bts.`source_location_location_id`  "
	+" JOIN employee_details aemp ON aemp.email_id=titem.`assigned_to_email_id` "
    +" JOIN `bt_customer_inventory_in` btin ON btin.id=titem.`in_customer_inventory_id`"
    +" JOIN bt_job_card crd on crd.id=titem.id "
	+" JOIN employee_details cemp on cemp.email_id = crd.created_by_email_id "
	+" JOIN employee_details oemp on oemp.email_id = crd.owner_email_id "
	+" JOIN employee_details lemp on lemp.email_id = crd.last_modified_by_email_id "


	+" where crd.repared=false crd.assigned_to_email_id in :assignedtolist",nativeQuery = true,countQuery = "SELECT count(*) "
	+" FROM `bt_service_order` bts   "
	+" JOIN `bt_service_order_item` titem ON titem.`service_order_id`=bts.id   "
	+" JOIN bt_products prd ON prd.id=titem.product_id  "
	+" JOIN location sloc ON sloc.location_id = bts.`source_location_location_id`   "
	+" JOIN employee_details aemp ON aemp.email_id=titem.`assigned_to_email_id`  "
	+" JOIN `bt_customer_inventory_in` btin ON btin.id=titem.`in_customer_inventory_id` "
	+" JOIN bt_job_card crd ON crd.id=titem.id  "
	+" JOIN employee_details cemp on cemp.email_id = crd.created_by_email_id "
	+" JOIN employee_details oemp on oemp.email_id = crd.owner_email_id "
	+" JOIN employee_details lemp on lemp.email_id = crd.last_modified_by_email_id "
	
	+" where crd.repared=false and crd.assigned_to_email_id in :assignedtolist")
	Page<Map<String,Object>> getPagePendingToRepair(@Param("assignedtolist") List<String> assignedToList, Pageable page);


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


    
	+" btin.`account_sap_number` AS accountSapNumber, "

	+" crd.`id` AS jobCardId, "
	+" crd.`manual_id` AS jobCardManualId, "
	+" crd.`qc_done` AS jobCardIsQcDone, "
	+" crd.`repair_date` AS jobCardRepariDate, "
	+" crd.`repair_product_state` AS jobCardRepairProductState, "
	+" crd.`repared` AS isJobCardRepaired, "
	+" crd.`return_date` AS jobCardReturnedDate, "
	+" crd.`returned_to_source` AS jobCardIsReturndToSource, "
	+" crd.`created_by_email_id` AS jobCardCreatedByEmailId, "
	+" crd.`last_modified_by_email_id` AS jobCardLastModifiedByEmailId, "
	+" crd.`assigned_to_email_id` AS jobCardAssignedToEmailId, "
	+" crd.`service_order_item_id` AS jobCardServiceOrderItemId, "
	+" crd.battery_ah_aft as batteryAhAft, "
	+" crd.battery_voltage_aft as batteryVoltageAft,"
	+" crd.battery_voltage_bef  as batteryVoltageBef, "
	+" crd.bt_balance_at as btBalanceAt, "
	+" crd.cell_bledge as cellBledge, "
	+" crd.cell_vmax as cellVmax, "
	+" crd.cell_vmin as cellVmin, "
	+" crd.corrective_action as correctiveAction, "
	+" crd.eng_remarks as engRemarks, "
	+" crd.eng_resolution_method as engResolutionMethod, "
	+" crd.ber_remarks as berRemarks, "
	+" crd.mat_sorteg as matSorteg, "
	+" crd.`observation1` as observation1, "
	+" crd.`observation2` AS observation2, "
	+" crd.`observation3` AS observation3, "
	+" crd.`observation4` AS observation4, "
	

	+" crd.created_date as jobCardCreatedDate, "
	+" crd.last_modified_date as jobCardLastModifiedDate, "
	+" crd.assigned_date as jobCardAssignedDate, "
	+" cemp.`first_name` AS createdByfirstName, "
	+" cemp.`last_name` AS createdBylastName, "
	+" cemp.`middle_name` AS createdBymiddleName, "

	+" oemp.`first_name` AS ownerFirstName, "
	+" oemp.`last_name` AS ownerLastName, "
	+" oemp.`middle_name` AS ownerMiddleName, "

	+" lemp.`first_name` AS lastModifiedByfirstName, "
	+" lemp.`last_name` AS lastModifiedBylastName, "
	+" lemp.`middle_name` AS lastModifiedByMiddleName, "


	+" btin.`challan_number` AS brcCaseChallanNumber,"

	+" btin.`customer_inventory_stage` AS brcCaseStage, "
	+" btin.`customer_inventory_status` AS brcCaseStatus "
	 
	+" FROM `bt_service_order` bts  "
	+" JOIN `bt_service_order_item` titem ON titem.`service_order_id`=bts.id  "
	+" JOIN bt_products prd ON prd.id=titem.product_id "
	+" JOIN location sloc ON sloc.location_id = bts.`source_location_location_id`  "
	+" JOIN employee_details aemp ON aemp.email_id=titem.`assigned_to_email_id` "
    +" JOIN `bt_customer_inventory_in` btin ON btin.id=titem.`in_customer_inventory_id`"
    +" JOIN bt_job_card crd on crd.id=titem.id "
	+" JOIN employee_details cemp on cemp.email_id = crd.created_by_email_id "
	+" JOIN employee_details oemp on oemp.email_id = crd.owner_email_id "
	+" JOIN employee_details lemp on lemp.email_id = crd.last_modified_by_email_id "
	
	
	+" where crd.id =:jobcardid and crd.assigned_to_email_id in :assignedtolist",nativeQuery = true)
	Map<String,Object> getSingle(@Param("jobcardid")Long jobcardid, @Param("assignedtolist") List<String> assignedToList);

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

    
	+" btin.`account_sap_number` AS accountSapNumber, "

	+" crd.`id` AS jobCardId, "
	+" crd.`manual_id` AS jobCardManualId, "
	+" crd.`qc_done` AS jobCardIsQcDone, "
	+" crd.`repair_date` AS jobCardRepariDate, "
	+" crd.`repair_product_state` AS jobCardRepairProductState, "
	+" crd.`repared` AS isJobCardRepaired, "
	+" crd.`return_date` AS jobCardReturnedDate, "
	+" crd.`returned_to_source` AS jobCardIsReturndToSource, "
	+" crd.`created_by_email_id` AS jobCardCreatedByEmailId, "
	+" crd.`last_modified_by_email_id` AS jobCardLastModifiedByEmailId, "
	+" crd.`assigned_to_email_id` AS jobCardAssignedToEmailId, "
	+" crd.`service_order_item_id` AS jobCardServiceOrderItemId, "
	+" crd.battery_ah_aft as batteryAhAft, "
	+" crd.battery_voltage_aft as batteryVoltageAft,"
	+" crd.battery_voltage_bef  as batteryVoltageBef, "
	+" crd.bt_balance_at as btBalanceAt, "
	+" crd.cell_bledge as cellBledge, "
	+" crd.cell_vmax as cellVmax, "
	+" crd.cell_vmin as cellVmin, "
	+" crd.corrective_action as correctiveAction, "
	+" crd.eng_remarks as engRemarks, "
	+" crd.eng_resolution_method as engResolutionMethod, "
	+" crd.ber_remarks as berRemarks, "
	+" crd.mat_sorteg as matSorteg, "
	+" crd.`observation1` as observation1, "
	+" crd.`observation2` as observation2, "
	+" crd.`observation3` as observation3, "
	+" crd.`observation4` as observation4, "
	
	+" crd.created_date as jobCardCreatedDate, "
	+" crd.last_modified_date as jobCardLastModifiedDate, "
	+" crd.assigned_date as jobCardAssignedDate, "
	+" cemp.`first_name` AS createdByfirstName, "
	+" cemp.`last_name` AS createdBylastName, "
	+" cemp.`middle_name` AS createdBymiddleName, "

	+" oemp.`first_name` AS ownerFirstName, "
	+" oemp.`last_name` AS ownerLastName, "
	+" oemp.`middle_name` AS ownerMiddleName, "

	+" lemp.`first_name` AS lastModifiedByfirstName, "
	+" lemp.`last_name` AS lastModifiedBylastName, "
	+" lemp.`middle_name` AS lastModifiedByMiddleName, "


	+" btin.`challan_number` AS brcCaseChallanNumber,"

	+" btin.`customer_inventory_stage` AS brcCaseStage, "
	+" btin.`customer_inventory_status` AS brcCaseStatus "
	
	 
	+" FROM `bt_service_order` bts  "
	+" JOIN `bt_service_order_item` titem ON titem.`service_order_id`=bts.id  "
	+" JOIN bt_products prd ON prd.id=titem.product_id "
	+" JOIN location sloc ON sloc.location_id = bts.`source_location_location_id`  "
	+" JOIN employee_details aemp ON aemp.email_id=titem.`assigned_to_email_id` "
    +" JOIN `bt_customer_inventory_in` btin ON btin.id=titem.`in_customer_inventory_id`"
    +" JOIN bt_job_card crd on crd.id=titem.id "
	+" JOIN employee_details cemp on cemp.email_id = crd.created_by_email_id "
	+" JOIN employee_details oemp on oemp.email_id = crd.owner_email_id "
	+" JOIN employee_details lemp on lemp.email_id = crd.last_modified_by_email_id "

	+" where crd.id =:jobcardid",nativeQuery = true)
	Map<String,Object> getSingle(@Param("jobcardid")Long jobcardid);


	@Query(value = "SELECT "
	+" prdc.`id` AS productConsumedId, "
	+" prdc.`created_date` AS consumedDate, "
	+" prdc.`last_modified_date` AS consumedLastModifiedDate, "
	+" prdc.`consume_from_stock` AS isConsumedFromStock, "
	+" prdc.`manual_id` AS productConsumedManualId, "
	+" prdc.`quantity` AS quantityConsumed, "
	+" prdc.`created_by_email_id` AS consumedByEmailId, "
	+" prdc.`last_modified_by_email_id` AS consumedLastModifiedByEmailId, "
	+" prdc.`job_card_id` AS jobCardId, "
	+" prdc.`owner_email_id` AS consumedByOwnerEmailId, "
	+" prdc.`product_id` AS consumedProductId, "
	+" prd.`manual_id` AS partCode, " 
	+" prd.`product_price` AS productPrice, "
	+" prd.`product_type` AS productType, "
	+" prd.`product_description` AS productDescription "

	+" FROM `bt_job_card_product_consume` prdc JOIN `bt_products` prd ON prd.id=prdc.`product_id` WHERE prdc.job_card_id=:jobcardid",nativeQuery = true)
	public List<Map<String,Object>> getProductConsumedListByJobcardid(@Param("jobcardid") Long jobCardId);

	@Query(value = "SELECT "
	+" prdc.`id` AS productConsumedId, "
	+" prdc.`created_date` AS consumedDate, "
	+" prdc.`last_modified_date` AS consumedLastModifiedDate, "
	+" prdc.`consume_from_stock` AS isConsumedFromStock, "
	+" prdc.`manual_id` AS productConsumedManualId, "
	+" prdc.`quantity` AS quantityConsumed, "
	+" prdc.`created_by_email_id` AS consumedByEmailId, "
	+" prdc.`last_modified_by_email_id` AS consumedLastModifiedByEmailId, "
	+" prdc.`job_card_id` AS jobCardId, "
	+" prdc.`owner_email_id` AS consumedByOwnerEmailId, "
	+" prdc.`product_id` AS consumedProductId, "
	+" prd.`manual_id` AS partCode, " 
	+" prd.`product_price` AS productPrice, "
	+" prd.`product_type` AS productType, "
	+" prd.`product_description` AS productDescription "

	+" FROM `bt_job_card_product_consume` prdc JOIN `bt_products` prd ON prd.id=prdc.`product_id` WHERE prdc.job_card_id=:jobcardid",nativeQuery = true,countQuery = "SELECT count(*) as ct "
	

	+" FROM `bt_job_card_product_consume` prdc JOIN `bt_products` prd ON prd.id=prdc.`product_id` WHERE prdc.job_card_id=:jobcardid")
	public Page<Map<String,Object>> getProductConsumedPageByJobcardid(@Param("jobcardid") Long jobCardId,Pageable page);

	@Query(value = "SELECT "
	+" qc.`id` AS qcId, "
	+" qc.`created_date` AS qcCreatedDate, "
	+" qc.`last_modified_date` AS qcLastModifiedDate, "
	+" qc.`assigned_date` AS qcAssignedDate, "
	+" qc.`corrective_action_taken` AS qcCorrectiveActionTaken, "
	+" qc.`electrical_check` AS qcElectricalCheck, "
	+" qc.`electrical_remarks` AS qcElectricalRemarks, "
	+" qc.`fault_observed_during_pid` AS qcFaultObserved, "
	+" qc.`final_approval` AS qcFinalApproval, "
	+" qc.`manual_id` AS qcManualId, " 
	+" qc.`parameters_check` AS qcParameterCheck, "
	+" qc.`physical_check` AS qcPhysicalCheck, "
	+" qc.`physical_remarks` AS qcPhysicalRemarks, "
	+" qc.`software_updation` AS qcSoftWareUpdation, "
	+" qc.`soh_updation` AS qcSohUpdation, "
	+" qc.`v_max` AS qcVmax, "
	+" qc.`v_min` AS qcVmin, "
	+" qc.`voltage_gap_lt150mv` AS qcVoltageGapLessThen150mv, "
	+" qc.`created_by_email_id` AS qcCreatedByEmailId, "
	+" qc.`last_modified_by_email_id` AS qcLastModifiedByEmailid, "
	+" qc.`assigned_to_email_id` AS qcAssignedToEmailId, "
	+" qc.`job_card_id` AS qcJobCardId, "
	+" qc.`owner_email_id` AS qcOwnerEmailId, "
	+" qc.`pid_date` AS qcPidDate, "
	
	+" aemp.`first_name` AS assignedTofirstName, "
	+" aemp.`last_name` AS assignedTolastName, "
	+" aemp.`middle_name` AS assignedTomiddleName, "
	
	+" cemp.`first_name` AS createdByfirstName, "
	+" cemp.`last_name` AS createdBylastName, "
	+" cemp.`middle_name` AS createdBymiddleName, "
	
	+" lemp.`first_name` AS lastModifiedByfirstName, "
	+" lemp.`last_name` AS lastModifiedBylastName, "
	+" lemp.`middle_name` AS lastModifiedBymiddleName, "
	
	+" oemp.`first_name` AS ownerfirstName, "
	+" oemp.`last_name` AS ownerlastName, "
	+" oemp.`middle_name` AS ownermiddleName "
	
	
	
	+" FROM `bt_job_card_qc` qc  "
	+" JOIN `bt_job_card` jc ON jc.id=qc.job_card_id "
	+" JOIN `employee_details` aemp ON aemp.`email_id`=qc.assigned_to_email_id "
	+" JOIN  `employee_details` cemp ON cemp.`email_id`=qc.created_by_email_id "
	+" JOIN  `employee_details` lemp ON lemp.`email_id`=qc.last_modified_by_email_id "
	+" JOIN  `employee_details` oemp ON oemp.`email_id`=qc.owner_email_id "
	+" WHERE qc.job_card_id=:jobcardid",nativeQuery = true,countQuery = "SELECT count(*) as ct "
	
	
	+" FROM `bt_job_card_qc` qc  "
	+" JOIN `bt_job_card` jc ON jc.id=qc.job_card_id "
	+" JOIN `employee_details` aemp ON aemp.`email_id`=qc.assigned_to_email_id "
	+" JOIN  `employee_details` cemp ON cemp.`email_id`=qc.created_by_email_id "
	+" JOIN  `employee_details` lemp ON lemp.`email_id`=qc.last_modified_by_email_id "
	+" JOIN  `employee_details` oemp ON oemp.`email_id`=qc.owner_email_id "
	+" WHERE qc.job_card_id=:jobcardid")
	Page<Map<String,Object>> getQcPageByJobCardId(@Param("jobcardid")Long jobCardId,Pageable page);

	@Query(value = "SELECT "
	+" qc.`id` AS qcId, "
	+" qc.`created_date` AS qcCreatedDate, "
	+" qc.`last_modified_date` AS qcLastModifiedDate, "
	+" qc.`assigned_date` AS qcAssignedDate, "
	+" qc.`corrective_action_taken` AS qcCorrectiveActionTaken, "
	+" qc.`electrical_check` AS qcElectricalCheck, "
	+" qc.`electrical_remarks` AS qcElectricalRemarks, "
	+" qc.`fault_observed_during_pid` AS qcFaultObserved, "
	+" qc.`final_approval` AS qcFinalApproval, "
	+" qc.`manual_id` AS qcManualId, " 
	+" qc.`parameters_check` AS qcParameterCheck, "
	+" qc.`physical_check` AS qcPhysicalCheck, "
	+" qc.`physical_remarks` AS qcPhysicalRemarks, "
	+" qc.`software_updation` AS qcSoftWareUpdation, "
	+" qc.`soh_updation` AS qcSohUpdation, "
	+" qc.`v_max` AS qcVmax, "
	+" qc.`v_min` AS qcVmin, "
	+" qc.`voltage_gap_lt150mv` AS qcVoltageGapLessThen150mv, "
	+" qc.`created_by_email_id` AS qcCreatedByEmailId, "
	+" qc.`last_modified_by_email_id` AS qcLastModifiedByEmailid, "
	+" qc.`assigned_to_email_id` AS qcAssignedToEmailId, "
	+" qc.`job_card_id` AS qcJobCardId, "
	+" qc.`owner_email_id` AS qcOwnerEmailId, "
	+" qc.`pid_date` AS qcPidDate, "
	
	+" aemp.`first_name` AS assignedTofirstName, "
	+" aemp.`last_name` AS assignedTolastName, "
	+" aemp.`middle_name` AS assignedTomiddleName, "
	
	+" cemp.`first_name` AS createdByfirstName, "
	+" cemp.`last_name` AS createdBylastName, "
	+" cemp.`middle_name` AS createdBymiddleName, "
	
	+" lemp.`first_name` AS lastModifiedByfirstName, "
	+" lemp.`last_name` AS lastModifiedBylastName, "
	+" lemp.`middle_name` AS lastModifiedBymiddleName, "
	
	+" oemp.`first_name` AS ownerfirstName, "
	+" oemp.`last_name` AS ownerlastName, "
	+" oemp.`middle_name` AS ownermiddleName "
	
	
	
	+" FROM `bt_job_card_qc` qc  "
	+" JOIN `bt_job_card` jc ON jc.id=qc.job_card_id "
	+" JOIN `employee_details` aemp ON aemp.`email_id`=qc.assigned_to_email_id "
	+" JOIN  `employee_details` cemp ON cemp.`email_id`=qc.created_by_email_id "
	+" JOIN  `employee_details` lemp ON lemp.`email_id`=qc.last_modified_by_email_id "
	+" JOIN  `employee_details` oemp ON oemp.`email_id`=qc.owner_email_id "
	+" WHERE qc.job_card_id=:jobcardid",nativeQuery = true)
	List<Map<String,Object>> getQcListByJobCardId(@Param("jobcardid")Long jobCardId);


	@Query(value = "select * from bt_job_card_eng_resolution",nativeQuery = true)
	public List<Map<String,Object>> getResolutionMethod();
	@Query(value = "select * from bt_job_card_battery_balance_at",nativeQuery = true)
	public List<Map<String,Object>> getBatteryBalanceAt();
	@Query(value = "select * from bt_ber_remarks",nativeQuery = true)
	public List<Map<String,Object>> getBerRemarks();
	
	@Query(value = "select * from bt_job_card_eng_corrective",nativeQuery = true)
	public List<Map<String, Object>> getCorrectiveAction();
	@Query(value = "select * from bt_job_card_eng_remarks",nativeQuery = true)
	public List<Map<String, Object>> getEngRemarks();
	
	@Query(value = "SELECT DISTINCT  "
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

		    
			+" btin.`account_sap_number` AS accountSapNumber, "

			+" crd.`id` AS jobCardId, "
			+" crd.`manual_id` AS jobCardManualId, "
			+" crd.`qc_done` AS jobCardIsQcDone, "
			+" crd.`repair_date` AS jobCardRepariDate, "
			+" crd.`repair_product_state` AS jobCardRepairProductState, "
			+" crd.`repared` AS isJobCardRepaired, "
			+" crd.`return_date` AS jobCardReturnedDate, "
			+" crd.`returned_to_source` AS jobCardIsReturndToSource, "
			+" crd.`created_by_email_id` AS jobCardCreatedByEmailId, "
			+" crd.`last_modified_by_email_id` AS jobCardLastModifiedByEmailId, "
			+" crd.`assigned_to_email_id` AS jobCardAssignedToEmailId, "
			

			+" crd.`service_order_item_id` AS jobCardServiceOrderItemId, "
			+" crd.battery_ah_aft as batteryAhAft, "
			+" crd.battery_voltage_aft as batteryVoltageAft,"
			+" crd.battery_voltage_bef  as batteryVoltageBef, "
			+" crd.bt_balance_at as btBalanceAt, "
			+" crd.cell_bledge as cellBledge, "
			+" crd.cell_vmax as cellVmax, "
			+" crd.cell_vmin as cellVmin, "
			+" crd.corrective_action as correctiveAction, "
			+" crd.eng_remarks as engRemarks, "
			+" crd.eng_resolution_method as engResolutionMethod, "
			+" crd.ber_remarks as berRemarks, "
			+" crd.mat_sorteg as matSorteg, "
			+" crd.`observation1` as observation1, "
			+" crd.`observation2` AS observation2, "
			+" crd.`observation3` AS observation3, "
			+" crd.`observation4` AS observation4, "

			+" crd.created_date as jobCardCreatedDate, "
			+" crd.last_modified_date as jobCardLastModifiedDate, "
			+" crd.assigned_date as jobCardAssignedDate, "
			+" cemp.`first_name` AS createdByfirstName, "
			+" cemp.`last_name` AS createdBylastName, "
			+" cemp.`middle_name` AS createdBymiddleName, "

			+" oemp.`first_name` AS ownerFirstName, "
			+" oemp.`last_name` AS ownerLastName, "
			+" oemp.`middle_name` AS ownerMiddleName, "

			+" lemp.`first_name` AS lastModifiedByfirstName, "
			+" lemp.`last_name` AS lastModifiedBylastName, "
			+" lemp.`middle_name` AS lastModifiedByMiddleName, "


			+" btin.`challan_number` AS brcCaseChallanNumber,"

			+" btin.`customer_inventory_stage` AS brcCaseStage, "
			+" btin.`customer_inventory_status` AS brcCaseStatus "
			 
			+" FROM `bt_service_order` bts  "
			+" JOIN `bt_service_order_item` titem ON titem.`service_order_id`=bts.id  "
			+" JOIN bt_products prd ON prd.id=titem.product_id "
			+" JOIN location sloc ON sloc.location_id = bts.`source_location_location_id`  "
			+" JOIN employee_details aemp ON aemp.email_id=titem.`assigned_to_email_id` "
		    +" JOIN `bt_customer_inventory_in` btin ON btin.id=titem.`in_customer_inventory_id`"
		    +" JOIN bt_job_card crd on crd.id=titem.id "
			+" JOIN employee_details cemp on cemp.email_id = crd.created_by_email_id "
			+" JOIN employee_details oemp on oemp.email_id = crd.owner_email_id "
			+" JOIN employee_details lemp on lemp.email_id = crd.last_modified_by_email_id "
			+" JOIN bt_job_card_qc qc on qc.job_card_id=crd.id "
		    +" where crd.assigned_to_email_id =:emailid and  crd.assigned_for_qc=true",
			nativeQuery = true,countQuery =" select count(DISTINCT  crd.id) as ct FROM `bt_service_order` bts  "
			+" JOIN `bt_service_order_item` titem ON titem.`service_order_id`=bts.id  "
			+" JOIN bt_products prd ON prd.id=titem.product_id "
			+" JOIN location sloc ON sloc.location_id = bts.`source_location_location_id`  "
			+" JOIN employee_details aemp ON aemp.email_id=titem.`assigned_to_email_id` "
		    +" JOIN `bt_customer_inventory_in` btin ON btin.id=titem.`in_customer_inventory_id`"
		    +" JOIN bt_job_card crd on crd.id=titem.id "
			+" JOIN employee_details cemp on cemp.email_id = crd.created_by_email_id "
			+" JOIN employee_details oemp on oemp.email_id = crd.owner_email_id "
			+" JOIN employee_details lemp on lemp.email_id = crd.last_modified_by_email_id where  crd.assigned_to_email_id =:emailid and crd.assigned_for_qc=true" )
	Page<Map<String, Object>> getAllPageByUserIdSubmitToQc(String emailid, Pageable page);


}
