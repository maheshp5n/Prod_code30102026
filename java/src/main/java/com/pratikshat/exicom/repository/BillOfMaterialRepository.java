package com.pratikshat.exicom.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.BillOfMaterial;
@Repository
public interface BillOfMaterialRepository extends JpaRepository<BillOfMaterial, Long>,BillOfMaterialRepositoryCustome  {
	@Query(value="SELECT loc.`id` AS locId,\r\n" + 
			"	loc.`location_name` AS locationName,\r\n" + 
			"	rpr.`product_part_code` AS rawProductPartCode,\r\n" + 
			"	rpr.`pdoduct_description` AS rawProductDescription,\r\n" + 
			"	rpr.`product_type` AS rawProductType,\r\n" + 
			"	bom.`req_qty` AS requiredQuantity,\r\n" + 
			"	bom.f_product_product_part_code as parent_part_code\r\n"+
			" FROM `bill_of_material_location` loc\r\n" + 
			"	JOIN `bill_of_material` bom ON loc.`bom_id`=bom.`id`\r\n" + 
			"	JOIN products rpr ON rpr.`product_part_code`=bom.`r_product_product_part_code`\r\n" + 
			"	WHERE bom.`f_product_product_part_code`=:productPartCode and length(loc.`location_name`)>0 and loc.location_name !=''",nativeQuery=true)
	List<Map<String,Object>> getBillOfMaterialOfproduct(@Param("productPartCode")String productPartCode);
}
