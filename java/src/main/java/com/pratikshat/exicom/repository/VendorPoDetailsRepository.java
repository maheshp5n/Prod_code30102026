package com.pratikshat.exicom.repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.VendorPoDetails;
@Repository
public interface VendorPoDetailsRepository extends JpaRepository<VendorPoDetails, Long>,VendorPoDetailsRepositoryCustome {
	@Query(value="SELECT t.id AS poTypeId,t.name AS poTypeName FROM vendor_po_type t",nativeQuery=true)
	public List<Map<String,Object>> getAllPoType();
	@Query(value="SELECT g.id AS vendorPoCategoryId,g.name AS vendorPoCattegoryName FROM vendor_po_category g",nativeQuery=true)
	public List<Map<String,Object>> getPoCategory();
//	@Query(value="SELECT \r\n" + 
//			"	po.`active` AS poActive,\r\n" + 
//			"	po.`activity_price` AS poActivityPrice,\r\n" + 
//			"	po.`busineed_area` AS businessArea,\r\n" + 
//			"	po.`circle_circle_name` AS poCircleName,\r\n" + 
//			"	po.`code_for_internal_ref` AS internalRefCode,\r\n" + 
//			"	po.`cost_centre` AS costCentre,\r\n" + 
//			"	po.`gl_code` AS glCode,\r\n" + 
//			"	po.`id` AS id,\r\n" + 
//			"	po.`po_category_id` AS categoryId,\r\n" + 
//			"	poCategory.`name` AS categoryName,\r\n" + 
//			"	po.`po_expiry_date` AS expiryDate,\r\n" + 
//			"	po.`po_number` AS poNumber,\r\n" + 
//			"	po.`po_start_date` AS poStartDate,\r\n" + 
//			"	po.`po_type_id` AS poTypeId,\r\n" + 
//			"	poType.`name` AS poTypeName,\r\n" + 
//			"	po.`remaining_quantity` AS remainingQuantity,\r\n" + 
//			"	po.`total_quantity` AS totalQuantity,\r\n" + 
//			"	po.`vendor_code` AS vendorCode,\r\n" + 
//			"	po.`vendor_name` AS vendorName,\r\n" + 
//			"	po.`po_date` AS poDate,\r\n"+
//			"	po.tax_rate as taxRate\r\n"+
//			
//			" FROM vendor_po_details po \r\n" + 
//			"	JOIN `vendor_po_category` poCategory ON po.`po_category_id`=poCategory.`id`\r\n" + 
//			"	JOIN `vendor_po_type` poType ON po.`po_type_id`=poType.`id`\r\n" + 
//			"	JOIN `contact_details` ct ON ct.`employee_code`=po.`vendor_code`\r\n" + 
//			"	JOIN `accounts` act ON ct.`account_id`=act.`sap_customer_number`\r\n" + 
//			"WHERE po.`active`=1 AND ct.`email_id`=:userEmailId\r\n" + 
//			"	AND po.`po_type_id`=:poTypeId\r\n" + 
//			"	AND po.`po_start_date`<=:pDate\r\n" + 
//			"	 AND po.`po_expiry_date`>=:pDate\r\n" + 
//			"	  AND po.`remaining_quantity`>0\r\n" + 
//			"	",nativeQuery=true)
//	public List<Map<String,Object>> getPendingPoByVendorCodeAndPoTypeAndValidDate(@Param("userEmailId")String userEmailId,@Param("poTypeId")Integer typeId,@Param("pDate")Date validDate);
//	
//	
//	///new po demank 
//	@Query(value="select po.`id` AS id,\r\n" + 
//			"	po.`active`,\r\n" + 
//			"	po.`activity_price` activityPrice,\r\n" + 
//			"	po.`busineed_area` AS busineesArea,\r\n" + 
//			"	po.`code_for_internal_ref` AS codeForInternalRef,\r\n" + 
//			"	po.`cost_centre` AS costCentre,\r\n" + 
//			"	po.`gl_code` AS glCode,\r\n" + 
//			"	po.`po_expiry_date` AS exipirtyDate,\r\n" + 
//			"	po.`po_number` AS poNumber,\r\n" + 
//			"	po.`po_start_date` AS poStartDate,\r\n" + 
//			"	po.`remaining_quantity` AS remainingQuantity,\r\n" + 
//			"	po.`total_quantity` AS totalQuantity,\r\n" + 
//			"	po.`vendor_code` AS vendorCode,\r\n" + 
//			"	po.`vendor_name` AS vendorName,\r\n" + 
//			"	po.`po_type_id` AS poTypeId,\r\n" + 
//			"	po.`activity_number` AS activityNumber,\r\n" + 
//			"	po.`activity_proce_list_description` AS activityPriceListDescription,\r\n" + 
//			"	po.`coas_center_description` AS costCenterDescription,\r\n" + 
//			"	po.`gl_code_description` AS glCodeDescription,\r\n" + 
//			"	po.`service_activity_code` AS serviceActivityCode,\r\n" + 
//			" 	po.tax_rate as taxRate,\r\n"+
//			"	po.`service_activity_description` AS serviceActivityDescription from vendor_po_details po JOIN `contact_details` ct ON ct.`employee_code`=po.`vendor_code` where po.`active`=1 AND ct.`email_id`=:userEmailId and po.remaining_quantity>0 and po.po_type_id=:wccType",nativeQuery=true )
//	public List<Map<String,Object>> getPoListByUserIdAndWccType(@Param("userEmailId")String userEmailId,@Param("wccType")Long wccTypeId);
//	
//	@Query(value="select po.`id` AS id,\r\n" + 
//			"	po.`active`,\r\n" + 
//			"	po.`activity_price` activityPrice,\r\n" + 
//			"	po.`busineed_area` AS busineesArea,\r\n" + 
//			"	po.`code_for_internal_ref` AS codeForInternalRef,\r\n" + 
//			"	po.`cost_centre` AS costCentre,\r\n" + 
//			"	po.`gl_code` AS glCode,\r\n" + 
//			"	po.`po_expiry_date` AS exipirtyDate,\r\n" + 
//			"	po.`po_number` AS poNumber,\r\n" + 
//			"	po.`po_start_date` AS poStartDate,\r\n" + 
//			"	po.`remaining_quantity` AS remainingQuantity,\r\n" + 
//			"	po.`total_quantity` AS totalQuantity,\r\n" + 
//			"	po.`vendor_code` AS vendorCode,\r\n" + 
//			"	po.`vendor_name` AS vendorName,\r\n" + 
//			"	po.`po_type_id` AS poTypeId,\r\n" + 
//			"	po.`activity_number` AS activityNumber,\r\n" + 
//			"	po.`activity_proce_list_description` AS activityPriceListDescription,\r\n" + 
//			"	po.`coas_center_description` AS costCenterDescription,\r\n" + 
//			"	po.`gl_code_description` AS glCodeDescription,\r\n" + 
//			"	po.`service_activity_code` AS serviceActivityCode,\r\n" + 
//			" 	po.tax_rate as taxRate,\r\n"+
//			"	po.`service_activity_description` AS serviceActivityDescription from vendor_po_details po JOIN `contact_details` ct ON ct.`employee_code`=po.`vendor_code` where po.`active`=1 AND ct.`email_id`=:userEmailId and po.remaining_quantity>0 and po.po_number=:poNumber",nativeQuery=true )
//	public List<Map<String,Object>> getPoListByUserAndPoNumber(@Param("userEmailId")String userEmailId,@Param("poNumber")String poNumber);
	
	///new po demank 
	@Query(value="select po.`id` AS id,\r\n" + 
			"	po.`active`,\r\n" + 
			"	po.`activity_price` activityPrice,\r\n" + 
			"	po.`busineed_area` AS busineesArea,\r\n" + 
			"	po.`code_for_internal_ref` AS codeForInternalRef,\r\n" + 
			"	po.`cost_centre` AS costCentre,\r\n" + 
			"	po.`gl_code` AS glCode,\r\n" + 
			"	po.`po_expiry_date` AS exipirtyDate,\r\n" + 
			"	po.`po_number` AS poNumber,\r\n" + 
			"	po.`po_start_date` AS poStartDate,\r\n" + 
			"	po.`remaining_quantity` AS remainingQuantity,\r\n" + 
			"	po.`total_quantity` AS totalQuantity,\r\n" + 
			"	po.`vendor_code` AS vendorCode,\r\n" + 
			"	po.`vendor_name` AS vendorName,\r\n" + 
			"	po.`po_type_id` AS poTypeId,\r\n" + 
			"	po.`activity_number` AS activityNumber,\r\n" + 
			"	po.`activity_proce_list_description` AS activityPriceListDescription,\r\n" + 
			"	po.`coas_center_description` AS costCenterDescription,\r\n" + 
			"	po.`gl_code_description` AS glCodeDescription,\r\n" + 
			"	po.`service_activity_code` AS serviceActivityCode,\r\n" + 
			" 	po.tax_rate as taxRate,\r\n"+
			"	po.`service_activity_description` AS serviceActivityDescription "
			+ "from vendor_po_details po "
			+ "JOIN `contact_details` ct ON ct.`employee_code`=po.`vendor_code` "
			+ "JOIN `legal_entity` lg on lg.legal_entity_id=ct.legal_entity_id "
			+ "JOIN `circle_details` crd on crd.service_center_power_legal_entity_id=lg.legal_entity_id and crd.circle_name=po.circle_circle_name "
			+ " where po.`active`=1 AND ct.`email_id`=:userEmailId and po.remaining_quantity>0 and po.po_type_id=:wccType",nativeQuery=true )
	public List<Map<String,Object>> getPoListByUserIdAndWccType(@Param("userEmailId")String userEmailId,@Param("wccType")Long wccTypeId);
	
	@Query(value="SELECT \r\n" + 
	"	po.`active` AS poActive,\r\n" + 
	"	po.`activity_price` AS poActivityPrice,\r\n" + 
	"	po.`busineed_area` AS businessArea,\r\n" + 
	"	po.`circle_circle_name` AS poCircleName,\r\n" + 
	"	po.`code_for_internal_ref` AS internalRefCode,\r\n" + 
	"	po.`cost_centre` AS costCentre,\r\n" + 
	"	po.`gl_code` AS glCode,\r\n" + 
	"	po.`id` AS id,\r\n" + 
	"	po.`po_category_id` AS categoryId,\r\n" + 
	"	poCategory.`name` AS categoryName,\r\n" + 
	"	po.`po_expiry_date` AS expiryDate,\r\n" + 
	"	po.`po_number` AS poNumber,\r\n" + 
	"	po.`po_start_date` AS poStartDate,\r\n" + 
	"	po.`po_type_id` AS poTypeId,\r\n" + 
	"	poType.`name` AS poTypeName,\r\n" + 
	"	po.`remaining_quantity` AS remainingQuantity,\r\n" + 
	"	po.`total_quantity` AS totalQuantity,\r\n" + 
	"	po.`vendor_code` AS vendorCode,\r\n" + 
	"	po.`vendor_name` AS vendorName,\r\n" + 
	"	po.`po_date` AS poDate,\r\n"+
	"	po.tax_rate as taxRate\r\n"+
	
	" FROM vendor_po_details po \r\n" + 
	"	JOIN `vendor_po_category` poCategory ON po.`po_category_id`=poCategory.`id`\r\n" + 
	"	JOIN `vendor_po_type` poType ON po.`po_type_id`=poType.`id`\r\n" + 
	"	JOIN `contact_details` ct ON ct.`employee_code`=po.`vendor_code`\r\n" + 
	"	JOIN `accounts` act ON ct.`account_id`=act.`sap_customer_number`\r\n" + 
	"	JOIN `legal_entity` lg on lg.legal_entity_id=ct.legal_entity_id\r\n "+
	"	JOIN `circle_details` crd on crd.service_center_power_legal_entity_id=lg.legal_entity_id and crd.circle_name=po.circle_circle_name\r\n"+
	"   WHERE po.`active`=1 AND ct.`email_id`=:userEmailId\r\n" + 
	"	AND po.`po_type_id`=:poTypeId\r\n" +  
	"	  AND po.`remaining_quantity`>0\r\n" + 
	"	",nativeQuery=true)
public List<Map<String,Object>> getPendingPoByVendorCodeAndPoTypeAndValidDate(@Param("userEmailId")String userEmailId,@Param("poTypeId")Integer typeId);
}
