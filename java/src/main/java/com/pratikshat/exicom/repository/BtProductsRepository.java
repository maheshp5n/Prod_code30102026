
package com.pratikshat.exicom.repository;

import com.pratikshat.exicom.entity.BtProducts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface BtProductsRepository extends JpaRepository<BtProducts,Long>,BtProductsRepositoryCustome{
    @Query(value = "SELECT btp.id,btp.manual_id AS partCode,btp.active,btp.product_description AS productDescription,btp.product_price AS productPrice,\r\n"
    		+ "btp.product_type AS productType,spf.product_family_product_family_id AS productFamilyId,pf.product_family_name AS productFamilyName,btp.sub_product_family_product_family_sub_product_id AS subProductFamily,spf.product_faily_sub_product_name AS subProductFamilyName,btp.synced,btp.created_by_email_id AS createdByEmailId,\r\n"
    		+ "btp.last_modified_by_email_id AS lastModifiedByEmailId,btp.created_date AS createdDate,btp.last_modified_date AS lastModifiedDate,\r\n"
    		+ "btp.hsn_details_hsn_code AS hsnCode,btp.owner_email_id AS ownerEmailId FROM bt_products btp JOIN product_family_sub_product spf ON btp.sub_product_family_product_family_sub_product_id= spf.product_family_sub_product_id JOIN product_family pf ON spf.product_family_product_family_id =pf.product_family_id WHERE btp.id=:id",nativeQuery = true)
    Map<String,Object> getSingleProducts(@Param("id") Long id);
    @Query(value = "SELECT btp.id,btp.manual_id AS partCode,btp.active,btp.product_description AS productDescription,btp.product_price AS productPrice,\r\n"
    		+ "btp.product_type AS productType,spf.product_family_product_family_id AS productFamilyId,pf.product_family_name AS productFamilyName,btp.sub_product_family_product_family_sub_product_id AS subProductFamily,spf.product_faily_sub_product_name AS subProductFamilyName,btp.synced,btp.created_by_email_id AS createdByEmailId,\r\n"
    		+ "btp.last_modified_by_email_id AS lastModifiedByEmailId,btp.created_date AS createdDate,btp.last_modified_date AS lastModifiedDate,\r\n"
    		+ "btp.hsn_details_hsn_code AS hsnCode,btp.owner_email_id AS ownerEmailId FROM bt_products btp JOIN product_family_sub_product spf ON btp.sub_product_family_product_family_sub_product_id= spf.product_family_sub_product_id JOIN product_family pf ON spf.product_family_product_family_id =pf.product_family_id order by btp.created_date desc",countQuery="SELECT btp.id,btp.manual_id AS partCode,btp.active,btp.product_description AS productDescription,btp.product_price AS productPrice,\r\n"
    				+ "btp.product_type AS productType,spf.product_family_product_family_id AS productFamilyId,pf.product_family_name AS productFamilyName,btp.sub_product_family_product_family_sub_product_id AS subProductFamily,spf.product_faily_sub_product_name AS subProductFamilyName,btp.synced,btp.created_by_email_id AS createdByEmailId,\r\n"
    				+ "btp.last_modified_by_email_id AS lastModifiedByEmailId,btp.created_date AS createdDate,btp.last_modified_date AS lastModifiedDate,\r\n"
    				+ "btp.hsn_details_hsn_code AS hsnCode,btp.owner_email_id AS ownerEmailId FROM bt_products btp JOIN product_family_sub_product spf ON btp.sub_product_family_product_family_sub_product_id= spf.product_family_sub_product_id JOIN product_family pf ON spf.product_family_product_family_id =pf.product_family_id",nativeQuery = true)
    Page<Map<String,Object>> getPage(Pageable pageable);
    
    @Query(value = "SELECT btp.id,btp.manual_id AS partCode,btp.active,btp.product_description AS productDescription,btp.product_price AS productPrice,\r\n"
    		+ "btp.product_type AS productType,spf.product_family_product_family_id AS productFamilyId,pf.product_family_name AS productFamilyName,btp.sub_product_family_product_family_sub_product_id AS subProductFamily,spf.product_faily_sub_product_name AS subProductFamilyName,btp.synced,btp.created_by_email_id AS createdByEmailId,\r\n"
    		+ "btp.last_modified_by_email_id AS lastModifiedByEmailId,btp.created_date AS createdDate,btp.last_modified_date AS lastModifiedDate,\r\n"
    		+ "btp.hsn_details_hsn_code AS hsnCode,btp.owner_email_id AS ownerEmailId FROM bt_products btp JOIN product_family_sub_product spf ON btp.sub_product_family_product_family_sub_product_id= spf.product_family_sub_product_id JOIN product_family pf ON spf.product_family_product_family_id =pf.product_family_id WHERE btp.manual_id=:partCode",nativeQuery = true)
    Map<String,Object> getProductByPartcode(@Param("partCode") String partCode);
    @Query(value = "SELECT * FROM `bt_products` prd \n" +
            "\tJOIN `product_family_sub_product` sf ON prd.`sub_product_family_product_family_sub_product_id`=sf.`product_family_sub_product_id` \n" +
            "\tJOIN product_family fm ON fm.`product_family_id`=sf.product_family_product_family_id WHERE prd.id=:id",nativeQuery = true)
    BtProducts getById(@Param("id") Long id);
    
    @Query(value="SELECT btp.`id` AS id,btp.`manual_id` AS partCode, btp.`product_type` AS productType, btp.`product_description` AS productDescription FROM bt_products btp WHERE btp.`active`=true AND btp.`manual_id` LIKE :manualId",nativeQuery=true,countQuery = "SELECT COUNT(*) AS countNumber FROM bt_products btp WHERE btp.`active`=true AND btp.`manual_id` LIKE :manualId")
	Page<Map<String,Object>> getBtProductsForListSmall(@Param("manualId")String manualId,Pageable page);
    
    @Query(value = "SELECT btp.id,btp.manual_id AS partCode,btp.active,btp.product_description AS productDescription,btp.product_price AS productPrice,\r\n"
    		+ "btp.product_type AS productType,spf.product_family_product_family_id AS productFamilyId,pf.product_family_name AS productFamilyName,btp.sub_product_family_product_family_sub_product_id AS subProductFamily,spf.product_faily_sub_product_name AS subProductFamilyName,btp.synced,btp.created_by_email_id AS createdByEmailId,\r\n"
    		+ "btp.last_modified_by_email_id AS lastModifiedByEmailId,btp.created_date AS createdDate,btp.last_modified_date AS lastModifiedDate,\r\n"
    		+ "btp.hsn_details_hsn_code AS hsnCode,btp.owner_email_id AS ownerEmailId FROM bt_products btp JOIN product_family_sub_product spf ON btp.sub_product_family_product_family_sub_product_id= spf.product_family_sub_product_id JOIN product_family pf ON spf.product_family_product_family_id =pf.product_family_id  where btp.product_type =:filterParam order by btp.created_date desc",countQuery="SELECT btp.id,btp.manual_id AS partCode,btp.active,btp.product_description AS productDescription,btp.product_price AS productPrice,\r\n"
    				+ "btp.product_type AS productType,spf.product_family_product_family_id AS productFamilyId,pf.product_family_name AS productFamilyName,btp.sub_product_family_product_family_sub_product_id AS subProductFamily,spf.product_faily_sub_product_name AS subProductFamilyName,btp.synced,btp.created_by_email_id AS createdByEmailId,\r\n"
    				+ "btp.last_modified_by_email_id AS lastModifiedByEmailId,btp.created_date AS createdDate,btp.last_modified_date AS lastModifiedDate,\r\n"
    				+ "btp.hsn_details_hsn_code AS hsnCode,btp.owner_email_id AS ownerEmailId FROM bt_products btp JOIN product_family_sub_product spf ON btp.sub_product_family_product_family_sub_product_id= spf.product_family_sub_product_id JOIN product_family pf ON spf.product_family_product_family_id =pf.product_family_id where btp.product_type =:filterParam",nativeQuery = true)
    Page<Map<String,Object>> getFilterPage(@Param("filterParam")String filterParam,Pageable pageable);
	
    @Query(value="SELECT \r\n"
    		+ "	btp.`manual_id` AS productManualId,\r\n"
    		+ "	btp.`product_description` AS productDescription\r\n"
    		+ "		FROM `bt_products` btp\r\n"
    		+ "		WHERE btp.`manual_id` LIKE :manualId",nativeQuery = true,countQuery = "SELECT COUNT(*) AS countNumber FROM `bt_products` btp WHERE btp.`manual_id` LIKE :manualId")
    Page<Map<String, Object>> getBtProductsForListData(@Param("manualId")String manualId, Pageable page);
    
	BtProducts findByManualId(String manualId);
    
	
}
