package com.pratikshat.exicom.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.BtVendorPoDetails;
import com.pratikshat.exicom.entity.VendorPoDetails;

@Repository
public interface BtVendorPoDetailsRepository extends JpaRepository<BtVendorPoDetails, Long>,BtVendorPoDetailsRepositoryCustom{
	
	@Query("select p from BtVendorPoDetails p where p.id=:id")
	BtVendorPoDetails getPoDetailsWithID(long id);
	
	@Query(value="SELECT po.`id` AS id,\r\n"
			+ "				po.`active`,\r\n"
			+ "				po.`activity_price` activityPrice, \r\n"
			+ "				po.`busineed_area` AS busineesArea,\r\n"
			+ "				po.`code_for_internal_ref` AS codeForInternalRef,\r\n"
			+ "				po.`cost_centre` AS costCentre,\r\n"
			+ "				po.`gl_code` AS glCode,\r\n"
			+ "				po.`po_expiry_date` AS exipirtyDate, \r\n"
			+ "				po.`po_number` AS poNumber, \r\n"
			+ "				po.`po_start_date` AS poStartDate,\r\n"
			+ "				po.`remaining_quantity` AS remainingQuantity,\r\n"
			+ "				po.`total_quantity` AS totalQuantity, \r\n"
			+ "				po.`vendor_code` AS vendorCode, \r\n"
			+ "				po.`vendor_name` AS vendorName, \r\n"
			+ "				po.`po_type_id` AS poTypeId,\r\n"
			+ "				po.`activity_number` AS activityNumber,\r\n"
			+ "				po.`activity_proce_list_description` AS activityPriceListDescription,\r\n"
			+ "				po.`coas_center_description` AS costCenterDescription,\r\n"
			+ "				po.`gl_code_description` AS glCodeDescription, \r\n"
			+ "				po.`service_activity_code` AS serviceActivityCode,\r\n"
			+ "			 	po.tax_rate AS taxRate,\r\n"
			+ "				po.`service_activity_description` AS serviceActivityDescription \r\n"
			+ "				\r\n"
			+ "			FROM `bt_vendor_po_details` po \r\n"
			+ "			LEFT JOIN `contact_details` ct ON ct.`employee_code`=po.`vendor_code`\r\n"
			+ "			LEFT JOIN `legal_entity` lg ON lg.legal_entity_id=ct.legal_entity_id \r\n"
			+ "			LEFT JOIN `circle_details` crd ON crd.service_center_battery_legal_entity_id=lg.legal_entity_id AND crd.circle_name=po.circle_circle_name \r\n"
			+ "			WHERE po.`active`=1 AND ct.`email_id`=:userEmailId AND po.remaining_quantity>0 AND po.po_type_id=:wccType",nativeQuery=true )
	public List<Map<String,Object>> getPoListByUserIdAndWccType(@Param("userEmailId")String userEmailId,@Param("wccType")Long wccTypeId);
	
	@Query(value="SELECT t.id AS poTypeId,t.name AS poTypeName FROM vendor_po_type t",nativeQuery=true)
	public List<Map<String,Object>> getAllPoType();
	
	@Query(value="SELECT g.id AS vendorPoCategoryId,g.name AS vendorPoCategoryName FROM vendor_po_category g",nativeQuery=true)
	public List<Map<String,Object>> getPoCategory();

	

}