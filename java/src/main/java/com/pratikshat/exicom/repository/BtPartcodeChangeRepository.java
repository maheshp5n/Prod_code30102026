package com.pratikshat.exicom.repository;

import java.util.List;
import java.util.Map;

import com.pratikshat.exicom.entity.BtPartcodeChange;
import com.pratikshat.exicom.repository.custom.BtPartcodeChangeRepositoryCustom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BtPartcodeChangeRepository extends JpaRepository<BtPartcodeChange,Long> ,BtPartcodeChangeRepositoryCustom{
    @Query(value = "SELECT "
	+" ch.`id` AS partCodeChangeId, "
	+" ch.`created_date` AS partCodeChangeCreatedDate, "
	+" ch.`last_modified_date` AS partCodeChnageLastModifiedDate, "
	+" ch.`manual_id` AS partCodeChangeManualId, "
	+" ch.`product_state` AS partCodeState, "
	+" ch.`quantity` AS partCodeChageQty, "
	+" ch.`created_by_email_id` AS partCodeCreatedBy, "
	+" ch.`last_modified_by_email_id` AS partCodeLastModifiedBy, "
	+" ch.`from_product_id` AS fromProductId, "
	+" ch.`location_location_id` AS locationId, "
	+" ch.`owner_email_id` AS partCodeOwnerEmailId, "
	+" ch.`to_product_id` AS toProductId, "
	+" toPrd.`manual_id` AS toPartCode, "
	+" toPrd.`product_type` AS toProductType, "
	+" toPrd.`product_description` AS toProductDescription, "
	+" fromPrd.`manual_id` AS fromPartCode, "
	+" fromPrd.`product_type` AS fromProductType, "
	+" fromPrd.`product_description` AS fromProductDescription, "
	+" loc.`location_name` AS locationName, "
	+" loc.plant AS locationPlant, "
	+" cemp.`first_name` AS createdByFirstName, "
	+" cemp.`middle_name` AS createdByMiddleName, "
	+" cemp.`last_name` AS createdByLastName, "
	
	+" oemp.`first_name` AS ownerFirstName, "
	+" oemp.`middle_name` AS ownerMiddleName, "
	+" oemp.`last_name` AS ownerLastName, "
	
	+" lemp.`first_name` AS lastModifiedByFirstName, "
	+" lemp.`middle_name` AS lastModifiedByMiddleName, "
	+" lemp.`last_name` AS lastModifiedByLastName "
	

	+" FROM `bt_partcode_change` ch  "
	+" JOIN bt_products toPrd ON toPrd.id=ch.`to_product_id` "
	+" JOIN bt_products fromPrd ON fromPrd.id=ch.`from_product_id` "
	+" JOIN location loc ON loc.location_id=ch.`location_location_id` "
	+" JOIN employee_details cemp ON cemp.`email_id`=ch.`created_by_email_id` "
	+" JOIN employee_details oemp ON oemp.`email_id`=ch.`owner_email_id` "
	+" JOIN employee_details lemp ON lemp.`email_id`=ch.`last_modified_by_email_id` where ch.id=:id",nativeQuery = true)
    Map<String,Object> getSingle(@Param("id") Long id);

    @Query(value = "SELECT "
	+" ch.`id` AS partCodeChangeId, "
	+" ch.`created_date` AS partCodeChangeCreatedDate, "
	+" ch.`last_modified_date` AS partCodeChnageLastModifiedDate, "
	+" ch.`manual_id` AS partCodeChangeManualId, "
	+" ch.`product_state` AS partCodeState, "
	+" ch.`quantity` AS partCodeChageQty, "
	+" ch.`created_by_email_id` AS partCodeCreatedBy, "
	+" ch.`last_modified_by_email_id` AS partCodeLastModifiedBy, "
	+" ch.`from_product_id` AS fromProductId, "
	+" ch.`location_location_id` AS locationId, "
	+" ch.`owner_email_id` AS partCodeOwnerEmailId, "
	+" ch.`to_product_id` AS toProductId, "
	+" toPrd.`manual_id` AS toPartCode, "
	+" toPrd.`product_type` AS toProductType, "
	+" toPrd.`product_description` AS toProductDescription, "
	+" fromPrd.`manual_id` AS fromPartCode, "
	+" fromPrd.`product_type` AS fromProductType, "
	+" fromPrd.`product_description` AS fromProductDescription, "
	+" loc.`location_name` AS locationName, "
	+" loc.plant AS locationPlant, "
	+" cemp.`first_name` AS createdByFirstName, "
	+" cemp.`middle_name` AS createdByMiddleName, "
	+" cemp.`last_name` AS createdByLastName, "
	
	+" oemp.`first_name` AS ownerFirstName, "
	+" oemp.`middle_name` AS ownerMiddleName, "
	+" oemp.`last_name` AS ownerLastName, "
	
	+" lemp.`first_name` AS lastModifiedByFirstName, "
	+" lemp.`middle_name` AS lastModifiedByMiddleName, "
	+" lemp.`last_name` AS lastModifiedByLastName "
	

	+" FROM `bt_partcode_change` ch  "
	+" JOIN bt_products toPrd ON toPrd.id=ch.`to_product_id` "
	+" JOIN bt_products fromPrd ON fromPrd.id=ch.`from_product_id` "
	+" JOIN location loc ON loc.location_id=ch.`location_location_id` "
	+" JOIN employee_details cemp ON cemp.`email_id`=ch.`created_by_email_id` "
	+" JOIN employee_details oemp ON oemp.`email_id`=ch.`owner_email_id` "
	+" JOIN employee_details lemp ON lemp.`email_id`=ch.`last_modified_by_email_id` where ch.id=:id and (loc.location_id in :locationIds or ch.`owner_email_id` in :ownerEmailids)",nativeQuery = true)
    Map<String,Object> getSingle(@Param("id") Long id,@Param("locationIds") List<Long>locationIds,@Param("ownerEmailids")List<String> ownerList);


    @Query(value = "SELECT "
	+" ch.`id` AS partCodeChangeId, "
	+" ch.`created_date` AS partCodeChangeCreatedDate, "
	+" ch.`last_modified_date` AS partCodeChnageLastModifiedDate, "
	+" ch.`manual_id` AS partCodeChangeManualId, "
	+" ch.`product_state` AS partCodeState, "
	+" ch.`quantity` AS partCodeChageQty, "
	+" ch.`created_by_email_id` AS partCodeCreatedBy, "
	+" ch.`last_modified_by_email_id` AS partCodeLastModifiedBy, "
	+" ch.`from_product_id` AS fromProductId, "
	+" ch.`location_location_id` AS locationId, "
	+" ch.`owner_email_id` AS partCodeOwnerEmailId, "
	+" ch.`to_product_id` AS toProductId, "
	+" toPrd.`manual_id` AS toPartCode, "
	+" toPrd.`product_type` AS toProductType, "
	+" toPrd.`product_description` AS toProductDescription, "
	+" fromPrd.`manual_id` AS fromPartCode, "
	+" fromPrd.`product_type` AS fromProductType, "
	+" fromPrd.`product_description` AS fromProductDescription, "
	+" loc.`location_name` AS locationName, "
	+" loc.plant AS locationPlant, "
	+" cemp.`first_name` AS createdByFirstName, "
	+" cemp.`middle_name` AS createdByMiddleName, "
	+" cemp.`last_name` AS createdByLastName, "
	
	+" oemp.`first_name` AS ownerFirstName, "
	+" oemp.`middle_name` AS ownerMiddleName, "
	+" oemp.`last_name` AS ownerLastName, "
	
	+" lemp.`first_name` AS lastModifiedByFirstName, "
	+" lemp.`middle_name` AS lastModifiedByMiddleName, "
	+" lemp.`last_name` AS lastModifiedByLastName "
	

	+" FROM `bt_partcode_change` ch  "
	+" JOIN bt_products toPrd ON toPrd.id=ch.`to_product_id` "
	+" JOIN bt_products fromPrd ON fromPrd.id=ch.`from_product_id` "
	+" JOIN location loc ON loc.location_id=ch.`location_location_id` "
	+" JOIN employee_details cemp ON cemp.`email_id`=ch.`created_by_email_id` "
	+" JOIN employee_details oemp ON oemp.`email_id`=ch.`owner_email_id` "
	+" JOIN employee_details lemp ON lemp.`email_id`=ch.`last_modified_by_email_id`",nativeQuery = true,countQuery = "SELECT count(*) as ct "
	
	+" FROM `bt_partcode_change` ch  "
	+" JOIN bt_products toPrd ON toPrd.id=ch.`to_product_id` "
	+" JOIN bt_products fromPrd ON fromPrd.id=ch.`from_product_id` "
	+" JOIN location loc ON loc.location_id=ch.`location_location_id` "
	+" JOIN employee_details cemp ON cemp.`email_id`=ch.`created_by_email_id` "
	+" JOIN employee_details oemp ON oemp.`email_id`=ch.`owner_email_id` "
	+" JOIN employee_details lemp ON lemp.`email_id`=ch.`last_modified_by_email_id`")
    Page<Map<String,Object>> getPage(Pageable page);

    @Query(value = "SELECT "
	+" ch.`id` AS partCodeChangeId, "
	+" ch.`created_date` AS partCodeChangeCreatedDate, "
	+" ch.`last_modified_date` AS partCodeChnageLastModifiedDate, "
	+" ch.`manual_id` AS partCodeChangeManualId, "
	+" ch.`product_state` AS partCodeState, "
	+" ch.`quantity` AS partCodeChageQty, "
	+" ch.`created_by_email_id` AS partCodeCreatedBy, "
	+" ch.`last_modified_by_email_id` AS partCodeLastModifiedBy, "
	+" ch.`from_product_id` AS fromProductId, "
	+" ch.`location_location_id` AS locationId, "
	+" ch.`owner_email_id` AS partCodeOwnerEmailId, "
	+" ch.`to_product_id` AS toProductId, "
	+" toPrd.`manual_id` AS toPartCode, "
	+" toPrd.`product_type` AS toProductType, "
	+" toPrd.`product_description` AS toProductDescription, "
	+" fromPrd.`manual_id` AS fromPartCode, "
	+" fromPrd.`product_type` AS fromProductType, "
	+" fromPrd.`product_description` AS fromProductDescription, "
	+" loc.`location_name` AS locationName, "
	+" loc.plant AS locationPlant, "
	+" cemp.`first_name` AS createdByFirstName, "
	+" cemp.`middle_name` AS createdByMiddleName, "
	+" cemp.`last_name` AS createdByLastName, "
	
	+" oemp.`first_name` AS ownerFirstName, "
	+" oemp.`middle_name` AS ownerMiddleName, "
	+" oemp.`last_name` AS ownerLastName, "
	
	+" lemp.`first_name` AS lastModifiedByFirstName, "
	+" lemp.`middle_name` AS lastModifiedByMiddleName, "
	+" lemp.`last_name` AS lastModifiedByLastName "
	

	+" FROM `bt_partcode_change` ch  "
	+" JOIN bt_products toPrd ON toPrd.id=ch.`to_product_id` "
	+" JOIN bt_products fromPrd ON fromPrd.id=ch.`from_product_id` "
	+" JOIN location loc ON loc.location_id=ch.`location_location_id` "
	+" JOIN employee_details cemp ON cemp.`email_id`=ch.`created_by_email_id` "
	+" JOIN employee_details oemp ON oemp.`email_id`=ch.`owner_email_id` "
	+" JOIN employee_details lemp ON lemp.`email_id`=ch.`last_modified_by_email_id` where loc.location_id in :locationIds or ch.`owner_email_id` in :ownerEmailids",nativeQuery = true,countQuery = "SELECT count(*) as ct "

	+" FROM `bt_partcode_change` ch  "
	+" JOIN bt_products toPrd ON toPrd.id=ch.`to_product_id` "
	+" JOIN bt_products fromPrd ON fromPrd.id=ch.`from_product_id` "
	+" JOIN location loc ON loc.location_id=ch.`location_location_id` "
	+" JOIN employee_details cemp ON cemp.`email_id`=ch.`created_by_email_id` "
	+" JOIN employee_details oemp ON oemp.`email_id`=ch.`owner_email_id` "
	+" JOIN employee_details lemp ON lemp.`email_id`=ch.`last_modified_by_email_id` where loc.location_id in :locationIds or ch.`owner_email_id` in :ownerEmailids")
    Page<Map<String,Object>> getPage(@Param("locationIds") List<Long>locationIds,@Param("ownerEmailids")List<String> ownerList,Pageable page);
}
