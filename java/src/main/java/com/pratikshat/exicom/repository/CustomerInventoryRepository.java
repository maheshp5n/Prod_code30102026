package com.pratikshat.exicom.repository;


import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.CustomerInventory;
@Repository
public interface CustomerInventoryRepository extends JpaRepository<CustomerInventory, Long>,CustomerInventoryRepositoryCustome {
	@Query(value="SELECT \r\n" + 
			"	custin.`circle_circle_name` AS circle,\r\n" + 
			"	custin.`client_sap_customer_number` customerSapNumber,\r\n" + 
			"	acc.`name` AS customerName,\r\n" + 
			"	custin.`customer_challan_date` AS inChallanDate,\r\n" + 
			"	custin.`customer_challan_number` AS inChallNumber,\r\n" + 
			"	custin.`customer_docket_number` AS inDocketNumber,\r\n" + 
			"	custin.`id` AS inId,\r\n" + 
			"	custin.`previous_state_product_state_id` AS inStateCode,\r\n" + 
			"	instate.`product_state_name` AS inStateName,\r\n" + 
			"	custin.`product_product_part_code` AS productPartcode,\r\n" + 
			"	prd.`pdoduct_description` AS productDescription,\r\n" + 
			"	prd.`product_type` AS productType,\r\n" + 
			"	custin.`receive_date` AS receiveDate,\r\n" + 
			"	custin.`redmrks` AS remarks,\r\n" + 
			"	custin.`returned` AS returnd,\r\n" + 
			"	custin.`return_date` AS returnDate,\r\n" + 
			"	custin.`serial_number` AS productSerialNumebr,\r\n" + 
			"	custin.`warranty_status` AS warrantyStatus,\r\n" + 
			"	custout.`dispatch_challan_number` AS dispatchChallanNumber,\r\n" + 
			"	custout.`dispatch_date` AS dispatchDate,\r\n" + 
			"	custout.`dispatch_docket_number` AS dispatchDocketNumber,\r\n" + 
			"	custout.`previous_state_product_state_id` AS dispatchProductStatus\r\n," + 
			"	custin.`lot_no` AS `lotNo`\r\n"+
			"\r\n" + 
			" FROM `customer_inventory` custin \r\n" + 
			"	LEFT JOIN `customer_inventory_out` custout ON custin.`id`=custout.`inventory_id`\r\n" + 
			"	JOIN `accounts` acc ON acc.`sap_customer_number`=custin.`client_sap_customer_number`\r\n" + 
			"	JOIN `products` prd ON custin.`product_product_part_code`=prd.`product_part_code`\r\n" + 
			"	JOIN `product_line_item_product_state` instate ON instate.`product_state_id`=custin.`previous_state_product_state_id`\r\n" + 
			"	LEFT JOIN `product_line_item_product_state` outstate ON outstate.`product_state_id`=custout.`previous_state_product_state_id`",nativeQuery=true,countQuery = "SELECT \r\n" + 
					"	count(*) as counterData\r\n" + 
					"\r\n" + 
					" FROM `customer_inventory` custin \r\n" + 
					"	LEFT JOIN `customer_inventory_out` custout ON custin.`id`=custout.`inventory_id`\r\n" + 
					"	JOIN `accounts` acc ON acc.`sap_customer_number`=custin.`client_sap_customer_number`\r\n" + 
					"	JOIN `products` prd ON custin.`product_product_part_code`=prd.`product_part_code`\r\n" + 
					"	JOIN `product_line_item_product_state` instate ON instate.`product_state_id`=custin.`previous_state_product_state_id`\r\n" + 
					"	LEFT JOIN `product_line_item_product_state` outstate ON outstate.`product_state_id`=custout.`previous_state_product_state_id`")
	Page<Map<String,Object>> getCustomerAllInventory(Pageable page);
	
	@Query(value="SELECT \r\n" + 
			"	custin.`circle_circle_name` AS circle,\r\n" + 
			"	custin.`client_sap_customer_number` customerSapNumber,\r\n" + 
			"	acc.`name` AS customerName,\r\n" + 
			"	custin.`customer_challan_date` AS inChallanDate,\r\n" + 
			"	custin.`customer_challan_number` AS inChallNumber,\r\n" + 
			"	custin.`customer_docket_number` AS inDocketNumber,\r\n" + 
			"	custin.`id` AS inId,\r\n" + 
			"	custin.`previous_state_product_state_id` AS inStateCode,\r\n" + 
			"	instate.`product_state_name` AS inStateName,\r\n" + 
			"	custin.`product_product_part_code` AS productPartcode,\r\n" + 
			"	prd.`pdoduct_description` AS productDescription,\r\n" + 
			"	prd.`product_type` AS productType,\r\n" + 
			"	custin.`receive_date` AS receiveDate,\r\n" + 
			"	custin.`redmrks` AS remarks,\r\n" + 
			"	custin.`returned` AS returnd,\r\n" + 
			"	custin.`return_date` AS returnDate,\r\n" + 
			"	custin.`serial_number` AS productSerialNumebr,\r\n" + 
			"	custin.`warranty_status` AS warrantyStatus,\r\n" + 
			"	custout.`dispatch_challan_number` AS dispatchChallanNumber,\r\n" + 
			"	custout.`dispatch_date` AS dispatchDate,\r\n" + 
			"	custout.`dispatch_docket_number` AS dispatchDocketNumber,\r\n" + 
			"	custout.`previous_state_product_state_id` AS dispatchProductStatus,\r\n" + 
			"	custin.`lot_no` AS `lotNo`\r\n"+
			"\r\n" + 
			" FROM `customer_inventory` custin \r\n" + 
			"	LEFT JOIN `customer_inventory_out` custout ON custin.`id`=custout.`inventory_id`\r\n" + 
			"	JOIN `accounts` acc ON acc.`sap_customer_number`=custin.`client_sap_customer_number`\r\n" + 
			"	JOIN `products` prd ON custin.`product_product_part_code`=prd.`product_part_code`\r\n" + 
			"	JOIN `product_line_item_product_state` instate ON instate.`product_state_id`=custin.`previous_state_product_state_id`\r\n" + 
			"	LEFT JOIN `product_line_item_product_state` outstate ON outstate.`product_state_id`=custout.`previous_state_product_state_id`\r\n" + 
			"	\r\n" + 
			"	WHERE custin.`owner_email_id` IN (:emailId)",nativeQuery=true,countQuery="SELECT \r\n" + 
					"	count(*) as counterData\r\n" + 
					"\r\n" + 
					" FROM `customer_inventory` custin \r\n" + 
					"	LEFT JOIN `customer_inventory_out` custout ON custin.`id`=custout.`inventory_id`\r\n" + 
					"	JOIN `accounts` acc ON acc.`sap_customer_number`=custin.`client_sap_customer_number`\r\n" + 
					"	JOIN `products` prd ON custin.`product_product_part_code`=prd.`product_part_code`\r\n" + 
					"	JOIN `product_line_item_product_state` instate ON instate.`product_state_id`=custin.`previous_state_product_state_id`\r\n" + 
					"	LEFT JOIN `product_line_item_product_state` outstate ON outstate.`product_state_id`=custout.`previous_state_product_state_id`\r\n" + 
					"	\r\n" + 
					"	WHERE custin.`owner_email_id` IN (:emailId)")
	public Page<Map<String,Object>> getCustomerInventoryIn(@Param("emailId")List<String>userEmailId,Pageable page);
}
