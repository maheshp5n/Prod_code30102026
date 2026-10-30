package com.pratikshat.exicom.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pratikshat.exicom.entity.Location;
@Repository
public interface LocationRepository extends JpaRepository<Location, Long>,LocationRepositoryCustome {
	@Transactional(readOnly = true)
	int countByLocationId(long locationId);
	@Transactional(readOnly = true)
	Location findByLocationId(long locationId);
	@Transactional(readOnly = true)
	List<Location> findByParentLocation_LocationId(long parentLocationId);
	@Transactional(readOnly = true)
	@Query("select new com.pratikshat.exicom.model.response.LocationForList(loc.locationId,loc.locationName) from Location loc where loc.businessDomain.businessDomainId=:businessDomainId or loc.businessDomain.businessDomainId=3")
	List<com.pratikshat.exicom.model.response.LocationForList> getLocationForList(@Param("businessDomainId")int businessDomainId);
	
	@Transactional(readOnly = true)
	@Query("select new com.pratikshat.exicom.model.response.LocationForList(loc.locationId,loc.locationName) from Location loc join loc.locationType tp where tp.locationTypeName=:typeName and (loc.businessDomain.businessDomainId=:businessDomainId or loc.businessDomain.businessDomainId=3)")
	List<com.pratikshat.exicom.model.response.LocationForList> getLocationForList(@Param("typeName") String typeName, @Param("businessDomainId")int businessDomainId);
	
	@Transactional(readOnly = true)
	@Query("select new com.pratikshat.exicom.model.response.LocationResponse(loc) from Location loc left join loc.owner ow left join loc.parentLocation prtLocation left join loc.legalEntity legal left join loc.createdBy left join loc.modifiedBy left join loc.locationType where loc.businessDomain.businessDomainId=:businessDomainId or loc.businessDomain.businessDomainId=3")
	Page<com.pratikshat.exicom.model.response.LocationResponse> getByAll(Pageable page, @Param("businessDomainId")int businessDomainId);
	//@Query("select new com.pratikshat.exicom.model.response.LocationResponse(loc) from Location loc left join loc.owner ow left join loc.parentLocation prtLocation left join loc.legalEntity legal left join loc.createdBy left join loc.modifiedBy left join loc.locationType where loc.owner in :owner")
	//Page<com.pratikshat.exicom.model.response.LocationResponse> getByAll(@Param("owner")List<com.pratikshat.exicom.entity.EmployeeDetails>owner, Pageable page);
	
	@Query("select new com.pratikshat.exicom.model.response.LocationResponse(loc) from Location loc left join loc.owner ow left join loc.parentLocation prtLocation left join loc.legalEntity legal left join loc.createdBy left join loc.modifiedBy left join loc.locationType where loc.locationId in :childs")
	Page<com.pratikshat.exicom.model.response.LocationResponse> getByAllWithChilds(@Param("childs")List<Long>childsIds, Pageable page);
	
	@Transactional(readOnly = true)
	@Query("select new com.pratikshat.exicom.model.response.LocationResponse(loc) from Location loc left join loc.owner ow left join loc.parentLocation prtLocation left join loc.legalEntity legal left join loc.createdBy left join loc.modifiedBy left join loc.locationType where loc.locationId=:locationId")
	com.pratikshat.exicom.model.response.LocationResponse getByLocationId(@Param("locationId")long locationId);
	@Transactional(readOnly=true)
	@Query("select new com.pratikshat.exicom.model.response.LocationProductItemForList(lpr.id,lpr.locationProductItemId,pr.productPartCode,pr.productType) from LocationProductItem lpr join lpr.location lc join lpr.product pr where lc.locationId=:locationId")
	java.util.List<com.pratikshat.exicom.model.response.LocationProductItemForList> getLocationProductItemByLocationId(@Param("locationId") long locationId);
	
	@Transactional(readOnly=true)
	@Query("select ploc from Location loc left join loc.parentLocation ploc where loc.locationId=:locationId")
	Location getParentLocation(@Param("locationId")long locationId);
	
	
	@Query("select new com.pratikshat.exicom.model.response.LocationResponse(loc) from Location loc left join loc.owner ow left join loc.parentLocation prtLocation left join loc.legalEntity legal left join loc.createdBy left join loc.modifiedBy left join loc.locationType join LocationRecentVisit rvi on rvi.locationId=loc.locationId where rvi.visitorId=:emailId and (loc.businessDomain.businessDomainId=:businessDomainId or loc.businessDomain.businessDomainId=3)")
	Page<com.pratikshat.exicom.model.response.LocationResponse> getRecentVisit(@Param("emailId")String emailId,Pageable page, @Param("businessDomainId")int businessDomainId);
	@Query("select own from Location lc join lc.owner own where lc.locationId=:locationId")
	com.pratikshat.exicom.entity.EmployeeDetails getOwnerOfLocation(@Param("locationId")long locationId);
	
	@Query("select new com.pratikshat.exicom.model.response.LocationResponse(loc) from Location loc join loc.owner ow left join loc.parentLocation prtLocation left join loc.legalEntity legal left join loc.createdBy left join loc.modifiedBy left join loc.locationType join ServiceRecource sr on sr.location=loc where sr.user.emailId=:userEmail and (loc.businessDomain.businessDomainId=:businessDomainId or loc.businessDomain.businessDomainId=3)")
	Page<com.pratikshat.exicom.model.response.LocationResponse> getMyLocation(@Param("userEmail")String userEmail,Pageable page, @Param("businessDomainId")int businessDomainId);
	
	@Query("select new com.pratikshat.exicom.model.response.LocationProductItemForList(litem.id,litem.locationProductItemId,pr.productPartCode,pr.productType) from LocationProductItem litem join litem.location loc join litem.product pr where loc.locationId=:locationid and litem.okQuantity>0")
	java.util.List<com.pratikshat.exicom.model.response.LocationProductItemForList> getOkProductByLocationId(@Param("locationid")long locationid);
	
	@Query("select new com.pratikshat.exicom.model.response.LocationProductItemForList(litem.id,litem.locationProductItemId,pr.productPartCode,pr.productType,pr.pdoductDescription) from LocationProductItem litem join litem.location loc join litem.product pr where loc.locationId=:locationid and litem.okQuantity>0")
	java.util.List<com.pratikshat.exicom.model.response.LocationProductItemForList> getOkProductByLocationIdWtihDescription(@Param("locationid")long locationid);
	
	@Query("select new com.pratikshat.exicom.model.response.LocationResponse(loc) from Location loc join loc.parentLocation ploc join loc.locationType tp where ploc.locationId=:locationId")
	Page<com.pratikshat.exicom.model.response.LocationResponse> getChildLocation(@Param("locationId")long locationId, Pageable page);
	
	@Query("select new com.pratikshat.exicom.model.response.LocationProductItemResponse(litem) from LocationProductItem litem join litem.location loc join litem.product pr where loc.locationId=:locationId")
	Page<com.pratikshat.exicom.model.response.LocationProductItemResponse> getLocationProductItem(@Param("locationId")long locationId,Pageable page);
	
	@Query("select new com.pratikshat.exicom.model.response.LocationProductItemResponse(litem) from LocationProductItem litem join litem.location loc join litem.product pr where loc.locationId=:locationId and pr.productType in :types")
	Page<com.pratikshat.exicom.model.response.LocationProductItemResponse> getLocationProductItem(@Param("locationId")long locationId,@Param("types")List<String> types,Pageable page);
	
	@Query("select new com.pratikshat.exicom.model.response.LocationProductItemResponse(litem) from LocationProductItem litem join litem.location loc join litem.product pr join loc.locationType ltype where ltype.locationTypeName in :locationTypes and (loc.businessDomain.businessDomainId=:businessDomainId or loc.businessDomain.businessDomainId=3)")
	Page<com.pratikshat.exicom.model.response.LocationProductItemResponse> getLocationProductItem(@Param("locationTypes")List<String> locationTypes, Pageable page, @Param("businessDomainId")int businessDomainId);
	
	@Query("select new com.pratikshat.exicom.model.response.LocationProductItemResponse(litem) from LocationProductItem litem join litem.location loc join litem.product pr join loc.locationType ltype where pr.productType in :producttypes and ltype.locationTypeName in :locationTypes and (loc.businessDomain.businessDomainId=:businessDomainId or loc.businessDomain.businessDomainId=3)")
	Page<com.pratikshat.exicom.model.response.LocationProductItemResponse> getLocationProductItem(@Param("locationTypes")List<String> locationTypes,@Param("producttypes")List<String> types, Pageable page, @Param("businessDomainId")int businessDomainId);
	
	@Query("select new com.pratikshat.exicom.model.response.LocationFaultyItemResponse(item) from LocationProductItem item join item.location loc join item.product prd where loc.locationId=:locationId and (item.faultyQuantity>0 or item.doaQuantity>0)")
	java.util.List<com.pratikshat.exicom.model.response.LocationFaultyItemResponse> getFaultyItemByLocationId(@Param("locationId")long locationId);
	@Query("select new com.pratikshat.exicom.model.response.LocationFaultyLineItemResponse(tm.id,item.id,item.locationProductLineItemId,item.itemSerialNumber) from LocationProductLineItems item join item.locationProductItem tm join item.locationProeuctLineItemState st where tm.id=:locationItemId and (st.productStateName= 'Faulty' or st.productStateName= 'DOA')")
	java.util.List<com.pratikshat.exicom.model.response.LocationFaultyLineItemResponse> getFaultyLineItemByLocationItemId(@Param("locationItemId")long locationItemId);
	@Query(value="SELECT loc.`location_name` AS locationName,\r\n"+
			"	loc.location_id as locationId,\r\n" + 
			"	prd.`product_part_code` AS partCode,\r\n" + 
			"	prd.`pdoduct_description` AS partDescription,\r\n" + 
			"	prd.`product_type` AS partType,\r\n" + 
			"	item.`msl_quantity` AS msl,\r\n" + 
			"	item.`hsl_quantity` AS hsl,\r\n" + 
			"	item.`ok_quantity` AS okQuantity\r\n" + 
			"	FROM location loc \r\n" + 
			"	JOIN `location_type` ltype ON ltype.`location_type_id`=loc.`location_type_location_type_id`\r\n" + 
			"	JOIN `location_product_item` item ON loc.`location_id`=item.`location_location_id`\r\n" + 
			"	JOIN `products` prd ON prd.`product_part_code`=item.`product_product_part_code` where ltype.location_type_name in :locationTypes and (loc.business_domain_business_domain_id=:businessDomainId or loc.business_domain_business_domain_id=3)",nativeQuery=true,countQuery = "SELECT COUNT(*)\r\n" + 
					"	FROM location loc \r\n" + 
					"	JOIN `location_type` ltype ON ltype.`location_type_id`=loc.`location_type_location_type_id`\r\n" + 
					"	JOIN `location_product_item` item ON loc.`location_id`=item.`location_location_id`\r\n" + 
					"	JOIN `products` prd ON prd.`product_part_code`=item.`product_product_part_code` where ltype.location_type_name in :locationTypes and (loc.business_domain_business_domain_id=:businessDomainId or loc.business_domain_business_domain_id=3)")
	Page<Map<String,Object>> getLocationMsl(@Param("locationTypes")List<String>locationTypes,Pageable page, @Param("businessDomainId")int businessDomainId);
	@Query(value="SELECT loc.`location_name` AS locationName,\r\n" + 
	
			"	loc.location_id as locationId,\r\n"+
			"	prd.`product_part_code` AS partCode,\r\n" + 
			"	prd.`pdoduct_description` AS partDescription,\r\n" + 
			"	prd.`product_type` AS partType,\r\n" + 
			"	item.`msl_quantity` AS msl,\r\n" + 
			"	item.`hsl_quantity` AS hsl,\r\n" + 
			"	item.`ok_quantity` AS okQuantity\r\n" + 
			"	FROM location loc \r\n" + 
			"	JOIN `location_type` ltype ON ltype.`location_type_id`=loc.`location_type_location_type_id`\r\n" + 
			"	JOIN `location_product_item` item ON loc.`location_id`=item.`location_location_id`\r\n" + 
			"	JOIN `products` prd ON prd.`product_part_code`=item.`product_product_part_code` WHERE loc.location_id=:locationId",nativeQuery=true,countQuery="SELECT COUNT(*)\r\n" + 
					"	FROM location loc \r\n" + 
					"	JOIN `location_type` ltype ON ltype.`location_type_id`=loc.`location_type_location_type_id`\r\n" + 
					"	JOIN `location_product_item` item ON loc.`location_id`=item.`location_location_id`\r\n" + 
					"	JOIN `products` prd ON prd.`product_part_code`=item.`product_product_part_code` WHERE loc.location_id=:locationId")
	Page<Map<String,Object>> getLocationMslByLocationId(@Param("locationId")long locationId,Pageable page);
	
	@Query(value="SELECT loc.`location_name` AS locationName,\r\n"+
			"	loc.location_id as locationId,\r\n" + 
			"	prd.`product_part_code` AS partCode,\r\n" + 
			"	prd.`pdoduct_description` AS partDescription,\r\n" + 
			"	prd.`product_type` AS partType,\r\n" + 
			"	item.`msl_quantity` AS msl,\r\n" + 
			"	item.`hsl_quantity` AS hsl,\r\n" + 
			"	item.`ok_quantity` AS okQuantity\r\n" + 
			"	FROM location loc \r\n" + 
			"	JOIN `location_type` ltype ON ltype.`location_type_id`=loc.`location_type_location_type_id`\r\n" + 
			"	JOIN `location_product_item` item ON loc.`location_id`=item.`location_location_id`\r\n" + 
			"	JOIN `products` prd ON prd.`product_part_code`=item.`product_product_part_code` where prd.product_type in :productTypes and ltype.location_type_name in :locationTypes and (loc.business_domain_business_domain_id=:businessDomainId or loc.business_domain_business_domain_id=3)",nativeQuery=true,countQuery = "SELECT COUNT(*)\r\n" + 
					"	FROM location loc \r\n" + 
					"	JOIN `location_type` ltype ON ltype.`location_type_id`=loc.`location_type_location_type_id`\r\n" + 
					"	JOIN `location_product_item` item ON loc.`location_id`=item.`location_location_id`\r\n" + 
					"	JOIN `products` prd ON prd.`product_part_code`=item.`product_product_part_code` where prd.product_type in :productTypes and ltype.location_type_name in :locationTypes and (loc.business_domain_business_domain_id=:businessDomainId or loc.business_domain_business_domain_id=3)")
	Page<Map<String,Object>> getLocationMsl(@Param("locationTypes")List<String>locationTypes,@Param("productTypes")List<String> productTypes,Pageable page, @Param("businessDomainId")int businessDomainId);
	@Query(value="SELECT loc.`location_name` AS locationName,\r\n" + 
			
			"	loc.location_id as locationId,\r\n"+
			"	prd.`product_part_code` AS partCode,\r\n" + 
			"	prd.`pdoduct_description` AS partDescription,\r\n" + 
			"	prd.`product_type` AS partType,\r\n" + 
			"	item.`msl_quantity` AS msl,\r\n" + 
			"	item.`hsl_quantity` AS hsl,\r\n" + 
			"	item.`ok_quantity` AS okQuantity\r\n" + 
			"	FROM location loc \r\n" + 
			"	JOIN `location_type` ltype ON ltype.`location_type_id`=loc.`location_type_location_type_id`\r\n" + 
			"	JOIN `location_product_item` item ON loc.`location_id`=item.`location_location_id`\r\n" + 
			"	JOIN `products` prd ON prd.`product_part_code`=item.`product_product_part_code` WHERE loc.location_id=:locationId and prd.product_type in :productTypes",nativeQuery=true,countQuery="SELECT COUNT(*)\r\n" + 
					"	FROM location loc \r\n" + 
					"	JOIN `location_type` ltype ON ltype.`location_type_id`=loc.`location_type_location_type_id`\r\n" + 
					"	JOIN `location_product_item` item ON loc.`location_id`=item.`location_location_id`\r\n" + 
					"	JOIN `products` prd ON prd.`product_part_code`=item.`product_product_part_code` WHERE loc.location_id=:locationId and prd.product_type in :productTypes")
	Page<Map<String,Object>> getLocationMslByLocationId(@Param("locationId")long locationId,@Param("productTypes")List<String>productTypes,Pageable page);
 
	@Query("select new com.pratikshat.exicom.model.response.LocationFaultyItemResponse(item) from LocationProductItem item join item.location loc join loc.owner onr join item.product prd join ServiceRecource sr on sr.location=loc where sr.user.emailId=:emailId and item.faultyQuantity>0 and (loc.businessDomain.businessDomainId=:businessDomainId or loc.businessDomain.businessDomainId=3)")
	Page<com.pratikshat.exicom.model.response.LocationFaultyItemResponse> getFaultyItemByLocationOwner(@Param("emailId")String emailId,Pageable page, @Param("businessDomainId")int businessDomainId);

	@Query("select new com.pratikshat.exicom.model.response.LocationFaultyLineItemResponse(tm.id,item.id,item.locationProductLineItemId,item.itemSerialNumber, prd.productPartCode, prd.pdoductDescription, st.productStateId, st.productStateName) from LocationProductLineItems item join item.locationProductItem tm join item.locationProeuctLineItemState st join tm.location loc join tm.product prd join loc.owner onr join ServiceRecource sr on sr.location=loc where sr.user.emailId=:emailId and st.productStateName!='Ok' and (loc.businessDomain.businessDomainId=:businessDomainId or loc.businessDomain.businessDomainId=3)")
	Page<com.pratikshat.exicom.model.response.LocationFaultyLineItemResponse> getFaultyLineItemByLocationOwner(@Param("emailId")String emailId, Pageable page, @Param("businessDomainId")int businessDomainId);
	
	@Query("select new com.pratikshat.exicom.model.response.LocationFaultyLineItemResponse(tm.id,item.id,item.locationProductLineItemId,item.itemSerialNumber, prd.productPartCode, prd.pdoductDescription, st.productStateId, st.productStateName) from LocationProductLineItems item join item.locationProductItem tm join item.locationProeuctLineItemState st join tm.location loc join tm.product prd join loc.owner onr where item.id=:lineItemId")
	com.pratikshat.exicom.model.response.LocationFaultyLineItemResponse getFaultyLineItemById(@Param("lineItemId")long lineItemId);
	@Query(value="SELECT litem.`item_serial_number` as serialNumber,item.`product_product_part_code` as productPartCode,sstate.`product_state_id` as productStateId,sstate.`product_state_name` as productStateName, litem.`refurbished` as refurbished,litem.`customer_inventory` as isCustomerInventory FROM `location_product_line_items` litem\r\n" + 
			"	JOIN `location_product_item` item ON item.`id`=litem.`location_product_item_id`\r\n" + 
			"	JOIN product_line_item_product_state sstate ON sstate.`product_state_id`=litem.`location_proeuct_line_item_state_product_state_id`\r\n" + 
			"	WHERE item.`product_product_part_code`=:product_partcode AND item.`location_location_id`=:locationid",nativeQuery = true)
	List<Map<String,Object>> getProductSerialNumberByLoationAndProduct(@Param("product_partcode")String productpartcode,@Param("locationid")long locationid);
	@Query(value="SELECT \r\n" + 
			"	loc.`city` AS warehouseCity,\r\n" + 
			"	loc.`country_key`AS warehouseCountryCode,\r\n" + 
			"	loc.`inventory_location` AS isInventory,\r\n" + 
			"	loc.`legal_entity_legal_entity_id` AS legalEntityId,\r\n" + 
			"	lg.`legal_entity_name` AS legalEntityName,\r\n" + 
			"	loc.`location_id` AS locationId,\r\n" + 
			"	loc.`location_name` AS locationName,\r\n" + 
			"	loc.`location_type_location_type_id` AS locationTypeId,\r\n" + 
			"	loc.`mobile_location` AS isMobileLocation,\r\n" + 
			"	loc.`name2` AS name2,\r\n" + 
			"	loc.`owner_email_id` AS ownerEmailId,\r\n" + 
			"	loc.`parent_location_location_id` AS parentLocationId,\r\n" + 
			"	loc.`plant` AS plantCode,\r\n" + 
			"	loc.`postal_code` AS postalCode,\r\n" + 
			"	loc.`reason_code` AS reasonCode,\r\n" + 
			"	loc.`remarks` AS remarks,\r\n" + 
			"	ltype.`location_type_name` AS locationTypeName\r\n" + 
			" FROM location loc JOIN legal_entity lg ON loc.`legal_entity_legal_entity_id`=lg.`legal_entity_id`\r\n" + 
			"	JOIN `location_type` ltype ON ltype.`location_type_id`=loc.`location_type_location_type_id`\r\n" + 
			"  WHERE lg.`legal_entity_type_type_id`=2 and (loc.business_domain_business_domain_id=:businessDomainId or loc.business_domain_business_domain_id=3)",nativeQuery=true)
	public List<Map<String,Object>> getRepariCenter(@Param("businessDomainId")int businessDomainId);
	@Query(value="SELECT * FROM `location_product_item` WHERE `product_product_part_code`=:partcode AND `location_location_id`=:warehouseid",nativeQuery=true)
	Map<String,Object> getStockByLocationIdAndProductPartCode(@Param("partcode")String partcode,@Param("warehouseid")long warehouseid);
	
	@Query(value="select * from (SELECT loc.`location_id` AS locationId,\r\n" + 
			"	prd.`pdoduct_description` AS productDescription,\r\n" + 
			"	prd.`product_part_code` AS partCode,\r\n" + 
			"	prd.`product_type` AS productType,\r\n" + 
			"	prd.`sub_product_family_product_family_sub_product_id` AS subProductFamilay,\r\n" + 
			"	item.`id` AS itemId,\r\n" + 
			"	item.`ber_qty` AS berQty,\r\n" + 
			"	item.`customer_inventory` AS customerInventoryQty,\r\n" + 
			"	item.`faulty_quantity` AS faultyQty,\r\n" + 
			"	item.`ok_quantity` AS okQty,\r\n" + 
			"	item.`refurbished` AS refurbishedQty,\r\n" + 
			"	item.`rejected_qty` AS rejectedQty,\r\n" + 
			"	item.`scrap_qty` AS scrapQty,\r\n" + 
			"	item.`quantity_in_on_hand` AS quantityOnHand,\r\n"+
			"	litem.`id` AS lineItemId,\r\n" + 
			"	litem.`customer_inventory` AS customerInventory,\r\n" + 
			"	litem.`item_serial_number` AS serialNumber,\r\n" + 
			"	litem.remarks,\r\n"+
			"	st.`product_state_id` AS serialNumberStateId,\r\n" + 
			"	st.`product_state_name` AS srialNumberStae\r\n" + 
			"	FROM `location` loc\r\n" + 
			"	JOIN `location_product_item` item ON item.`location_location_id`=loc.`location_id`\r\n" + 
			"	JOIN `products` prd ON prd.`product_part_code` =item.`product_product_part_code`\r\n" + 
			"	JOIN `service_recource` sr on sr.`location_location_id`=loc.location_id\r\n"+
			"	LEFT JOIN `location_product_line_items` litem ON litem.`location_product_item_id` =item.`id`\r\n" + 
			"	LEFT JOIN `product_line_item_product_state` st ON st.`product_state_id`=litem.`location_proeuct_line_item_state_product_state_id`\r\n" + 
			"	WHERE sr.`user_email_id`=:username and item.`quantity_in_on_hand`>0) as mn",nativeQuery=true,countQuery = "SELECT	count(*) as counterq\r\n"+
					"	FROM `location` loc\r\n" + 
					"	JOIN `location_product_item` item ON item.`location_location_id`=loc.`location_id`\r\n" + 
					"	JOIN `products` prd ON prd.`product_part_code` =item.`product_product_part_code`\r\n" + 
					"	JOIN `service_recource` sr on sr.`location_location_id`=loc.location_id\r\n"+
					"	LEFT JOIN `location_product_line_items` litem ON litem.`location_product_item_id` =item.`id`\r\n" + 
					"	LEFT JOIN `product_line_item_product_state` st ON st.`product_state_id`=litem.`location_proeuct_line_item_state_product_state_id`\r\n" + 
					"	WHERE sr.`user_email_id`=:username and item.`quantity_in_on_hand`>0 order by litem.`customer_inventory` ASC")
	public Page<Map<String,Object>> getMyInventory(@Param("username")String username,Pageable page);

	@Query(value = "SELECT * FROM `location` lo JOIN `location_type` loc ON loc.`location_type_id` = lo.`location_type_location_type_id`\r\n"
			+ "WHERE lo.`location_id` =:warehouseId AND loc.`location_type_id` =:id",nativeQuery = true )
	Location getLocationWithidAndType(long warehouseId ,int id);
	
	
	@Query(value="SELECT \r\n" + 
			"SUM(dd.`ber_qty`) AS 'BER',\r\n" + 
			"SUM(`doa_quantity`) AS 'DOA',\r\n" + 
			"SUM(`faulty_quantity`) AS 'Faulty',\r\n" + 
			"SUM(`ok_quantity`) AS 'Ok',\r\n" + 
			"SUM(`rejected_qty`) AS 'Rejected',\r\n" + 
			"SUM(`scrap_qty`) AS 'Scrap'\r\n" + 
			"FROM (\r\n" + 
			"SELECT \r\n" + 
			"IFNULL(SUM(`ber_qty`),0) AS `ber_qty`,\r\n" + 
			"0 AS `doa_quantity`,\r\n" + 
			"0 AS `faulty_quantity`,\r\n" + 
			"0 AS `ok_quantity`,\r\n" + 
			"0 AS `rejected_qty`,\r\n" + 
			"0 AS `scrap_qty`\r\n" + 
			"FROM `location_product_item` item\r\n" + 
			"JOIN `location` loc ON loc.`location_id`=item.`location_location_id`\r\n" + 
			"JOIN `service_recource` sr ON sr.`location_location_id`=loc.`location_id`\r\n" + 
			"WHERE sr.`user_email_id`=:user \r\n" + 
			"UNION ALL\r\n" + 
			"SELECT \r\n" + 
			"0 AS `ber_qty`,\r\n" + 
			"IFNULL(SUM(`doa_quantity`),0) AS `doa_quantity`,\r\n" + 
			"0 AS `faulty_quantity`,\r\n" + 
			"0 AS `ok_quantity`,\r\n" + 
			"0 AS `rejected_qty`,\r\n" + 
			"0 AS `scrap_qty`\r\n" + 
			"FROM `location_product_item` item\r\n" + 
			"JOIN `location` loc ON loc.`location_id`=item.`location_location_id`\r\n" + 
			"JOIN `service_recource` sr ON sr.`location_location_id`=loc.`location_id`\r\n" + 
			"WHERE sr.`user_email_id`=:user \r\n" + 
			"UNION ALL\r\n" + 
			"SELECT \r\n" + 
			"0 AS `ber_qty`,\r\n" + 
			"0 AS `doa_quantity`,\r\n" + 
			"IFNULL(SUM(`faulty_quantity`),0) AS `faulty_quantity`,\r\n" + 
			"0 AS `ok_quantity`,\r\n" + 
			"0 AS `rejected_qty`,\r\n" + 
			"0 AS `scrap_qty`\r\n" + 
			"FROM `location_product_item` item\r\n" + 
			"JOIN `location` loc ON loc.`location_id`=item.`location_location_id`\r\n" + 
			"JOIN `service_recource` sr ON sr.`location_location_id`=loc.`location_id`\r\n" + 
			"WHERE sr.`user_email_id`=:user \r\n" + 
			"UNION ALL\r\n" + 
			"SELECT \r\n" + 
			"0 AS `ber_qty`,\r\n" + 
			"0 AS `doa_quantity`,\r\n" + 
			"0 AS `faulty_quantity`,\r\n" + 
			"IFNULL(SUM(`ok_quantity`),0) AS `ok_quantity`,\r\n" + 
			"0 AS `rejected_qty`,\r\n" + 
			"0 AS `scrap_qty`\r\n" + 
			"FROM `location_product_item` item\r\n" + 
			"JOIN `location` loc ON loc.`location_id`=item.`location_location_id`\r\n" + 
			"JOIN `service_recource` sr ON sr.`location_location_id`=loc.`location_id`\r\n" + 
			"WHERE sr.`user_email_id`=:user \r\n" + 
			"UNION ALL\r\n" + 
			"SELECT \r\n" + 
			"0 AS `ber_qty`,\r\n" + 
			"0 AS `doa_quantity`,\r\n" + 
			"0 AS `faulty_quantity`,\r\n" + 
			"0 AS `ok_quantity`,\r\n" + 
			"IFNULL(SUM(`rejected_qty`),0) AS `rejected_qty`,\r\n" + 
			"0 AS `scrap_qty`\r\n" + 
			"FROM `location_product_item` item\r\n" + 
			"JOIN `location` loc ON loc.`location_id`=item.`location_location_id`\r\n" + 
			"JOIN `service_recource` sr ON sr.`location_location_id`=loc.`location_id`\r\n" + 
			"WHERE sr.`user_email_id`=:user \r\n" + 
			"UNION ALL\r\n" + 
			"SELECT \r\n" + 
			"0 AS `ber_qty`,\r\n" + 
			"0 AS `doa_quantity`,\r\n" + 
			"0 AS `faulty_quantity`,\r\n" + 
			"0 AS `ok_quantity`,\r\n" + 
			"0 AS `rejected_qty`,\r\n" + 
			"IFNULL(SUM(`scrap_qty`),0) AS `scrap_qty`\r\n" + 
			"FROM `location_product_item` item\r\n" + 
			"JOIN `location` loc ON loc.`location_id`=item.`location_location_id`\r\n" + 
			"JOIN `service_recource` sr ON sr.`location_location_id`=loc.`location_id`\r\n" + 
			"WHERE sr.`user_email_id`=:user) dd", nativeQuery=true)
	Map<String,Object> getInventory(@Param("user")String user);
	
	@Query(value = "SELECT \r\n"
			+ "	SUM(dd.`BER`) AS `BER`,\r\n"
			+ "	SUM(dd.`Faulty`) AS `Faulty`,\r\n"
			+ "	SUM(dd.`Ok`) AS `Ok`,\r\n"
			+ "	SUM(dd.`Rejected`) AS `Rejected`,\r\n"
			+ "	SUM(dd.`Scrap`) AS `Scrap`\r\n"
			+ "		FROM (\r\n"
			+ "SELECT \r\n"
			+ "	IFNULL(SUM(`exicom_total_ber_quantity`),0) AS `BER`,\r\n"
			+ "	0 AS `Faulty`,\r\n"
			+ "	0 AS `Ok`,\r\n"
			+ "	0 AS `Rejected`,\r\n"
			+ "	0 AS `Scrap` \r\n"
			+ "	    FROM `bt_product_item` prd	\r\n"
			+ "	    JOIN `location` loc ON loc.`location_id` =prd.`warehouse_location_id`\r\n"
			+ "	    JOIN `service_recource` sr ON sr.`location_location_id`=loc.`location_id`\r\n"
			+ "	    WHERE sr.`user_email_id`=:user\r\n"
			+ "UNION ALL	\r\n"
			+ "SELECT \r\n"
			+ "	0 AS `BER`,\r\n"
			+ "	IFNULL(SUM(`exicom_total_faulty_quantity`),0) AS `Faulty`,\r\n"
			+ "	0 AS `Ok`,\r\n"
			+ "	0 AS `Rejected`,\r\n"
			+ "	0 AS `Scrap` \r\n"
			+ "	    FROM `bt_product_item` prd	\r\n"
			+ "	    JOIN `location` loc ON loc.`location_id` =prd.`warehouse_location_id`\r\n"
			+ "	    JOIN `service_recource` sr ON sr.`location_location_id`=loc.`location_id`\r\n"
			+ "	    WHERE sr.`user_email_id`=:user   \r\n"
			+ "UNION ALL\r\n"
			+ "SELECT \r\n"
			+ "	0 AS `BER`,\r\n"
			+ "	0 AS `Faulty`,\r\n"
			+ "	IFNULL(SUM(`exicom_total_ok_quantity`),0) AS `Ok`,\r\n"
			+ "	0 AS `Rejected`,\r\n"
			+ "	0 AS `Scrap` \r\n"
			+ "	    FROM `bt_product_item` prd	\r\n"
			+ "	    JOIN `location` loc ON loc.`location_id` =prd.`warehouse_location_id`\r\n"
			+ "	    JOIN `service_recource` sr ON sr.`location_location_id`=loc.`location_id`\r\n"
			+ "	    WHERE sr.`user_email_id`=:user\r\n"
			+ "UNION ALL\r\n"
			+ "SELECT \r\n"
			+ "	0 AS `BER`,\r\n"
			+ "	0 AS `Faulty`,\r\n"
			+ "	0 AS `Ok`,\r\n"
			+ "	IFNULL(SUM(`exicom_total_rejected_quantity`),0) AS `Rejected`,\r\n"
			+ "	0 AS `Scrap` \r\n"
			+ "	    FROM `bt_product_item` prd	\r\n"
			+ "	    JOIN `location` loc ON loc.`location_id` =prd.`warehouse_location_id`\r\n"
			+ "	    JOIN `service_recource` sr ON sr.`location_location_id`=loc.`location_id`\r\n"
			+ "	    WHERE sr.`user_email_id`=:user\r\n"
			+ "UNION ALL\r\n"
			+ "SELECT \r\n"
			+ "	0 AS `BER`,\r\n"
			+ "	0 AS `Faulty`,\r\n"
			+ "	0 AS `Ok`,\r\n"
			+ "	0 AS `Rejected`,\r\n"
			+ "	IFNULL(SUM(`exicom_total_scrap_quantity`),0) AS `Scrap` \r\n"
			+ "	    FROM `bt_product_item` prd	\r\n"
			+ "	    JOIN `location` loc ON loc.`location_id` =prd.`warehouse_location_id`\r\n"
			+ "	    JOIN `service_recource` sr ON sr.`location_location_id`=loc.`location_id`\r\n"
			+ "	    WHERE sr.`user_email_id`=:user\r\n"
			+ "	    ) dd	     " ,nativeQuery = true)
	Map<String, Object> getBtInventory(@Param("user") String user);
}
