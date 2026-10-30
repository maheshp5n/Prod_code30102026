package com.pratikshat.exicom.repository;


import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pratikshat.exicom.entity.Products;

@Repository
public interface ProductRepository extends JpaRepository<Products, String>,ProductCustomeRepository {
	@Transactional(readOnly=true)
	int countByProductPartCode(String partCode);
	@Transactional(readOnly=true)
	com.pratikshat.exicom.entity.Products findByProductPartCode(String partCode);
	@Transactional(readOnly = true)
	@Query("select new com.pratikshat.exicom.model.response.Products(p,pfs) from Products p left join p.subProductFamily pf left join pf.productFamily pfs")
	Page<com.pratikshat.exicom.model.response.Products> getAll(Pageable page);
	
	@Query("select new com.pratikshat.exicom.model.response.Products(p,pfs) from Products p left join p.subProductFamily pf left join pf.productFamily pfs where p.productType=:type")
	Page<com.pratikshat.exicom.model.response.Products> getAll(@Param("type")String type, Pageable page);
	
	@Transactional(readOnly=true)
	@Query("select new com.pratikshat.exicom.model.response.Products(p,pfs) from Products p left join p.subProductFamily pf join pf.productFamily pfs left join p.createdBy c left join p.modifiedBy m where p.productPartCode=:productPartyCode")
	com.pratikshat.exicom.model.response.Products getByProductPartCode(@Param("productPartyCode")String productPartCode);
	@Transactional(readOnly=true)
	@Query("select new com.pratikshat.exicom.model.response.Products(p,pfs) from Products p left join p.subProductFamily pf left join pf.productFamily pfs join ProductsRecentViews pv on p.productPartCode=pv.visitedPartCode where pv.userId=:visitorId")
	Page<com.pratikshat.exicom.model.response.Products> getAllRecentViews(@Param("visitorId")String visitorId,Pageable page);
	
	@Query("select new com.pratikshat.exicom.model.response.ProductsForList(p.pdoductDescription,p.productPartCode) from Products p")
	java.util.List<com.pratikshat.exicom.model.response.ProductsForList> getProductsForList();
	
	@Query("select new com.pratikshat.exicom.model.response.ProductsForList(p.pdoductDescription,p.productPartCode) from Products p where p.active=true and (p.productPartCode like :pcode or p.pdoductDescription like :pcode)")
	Page<com.pratikshat.exicom.model.response.ProductsForList> getProductsForListSmall(@Param("pcode")String pcode,Pageable page);
	
	@Query("select new com.pratikshat.exicom.model.response.ProductsForList(p.pdoductDescription,p.productPartCode) from Products p where p.productType=:type and p.active=true")
	java.util.List<com.pratikshat.exicom.model.response.ProductsForList> getProductsForList(@Param("type")String type);
	
	@Query(value="SELECT prd.product_part_code as productPartCode,prd.`pdoduct_description` as pdoductDescription,prd.product_price as productPrice,prd.product_type as productType,pt.qty_on_hand as qtyOnHand,pt.qty_ok as qtyOk,pt.qty_faulty as qtyFaulty,subf.product_faily_sub_product_name as productFailySubProductName,pfa.product_family_name as productFamilyName FROM products prd\r\n" + 
			"	JOIN (SELECT pitem.`product_product_part_code` AS part_code,SUM(pitem.`quantity_in_on_hand`) AS qty_on_hand,SUM(pitem.`ok_quantity`) AS qty_ok,SUM(pitem.`faulty_quantity`) AS qty_faulty FROM `location_product_item` pitem GROUP BY pitem.`product_product_part_code`) AS pt ON pt.part_code=prd.`product_part_code` \r\n" + 
			"	JOIN `product_family_sub_product` subf ON subf.`product_family_sub_product_id` =prd.`sub_product_family_product_family_sub_product_id`\r\n" + 
			"	JOIN `product_family` pfa ON pfa.`product_family_id`=subf.`product_family_product_family_id`",nativeQuery = true,countQuery = "SELECT COUNT(*) FROM products prd\r\n" + 
					"	JOIN (SELECT pitem.`product_product_part_code` AS part_code,SUM(pitem.`quantity_in_on_hand`) AS qty_on_hand,SUM(pitem.`ok_quantity`) AS qty_ok,SUM(pitem.`faulty_quantity`) AS qty_faulty FROM `location_product_item` pitem GROUP BY pitem.`product_product_part_code`) AS pt ON pt.part_code=prd.`product_part_code` \r\n" + 
					"	JOIN `product_family_sub_product` subf ON subf.`product_family_sub_product_id` =prd.`sub_product_family_product_family_sub_product_id`\r\n" + 
					"	JOIN `product_family` pfa ON pfa.`product_family_id`=subf.`product_family_product_family_id`")
	Page<Map<String,Object>> getPanIndiaStockSummary(Pageable page);
	
	
	@Query(value="SELECT \r\n" + 
			"DISTINCT `product_family_name` AS `name`\r\n" + 
			"FROM `product_family`\r\n" + 
			"WHERE `product_family_name`!=''\r\n" + 
			"ORDER BY `product_family_name` ASC",nativeQuery=true)
	java.util.List<String> getProductFamilyList();

	@Query(value="SELECT \r\n" + 
			"DISTINCT `product_faily_sub_product_name` AS `name` \r\n" + 
			"FROM `product_family_sub_product`\r\n" + 
			"WHERE `product_faily_sub_product_name`!=''\r\n" + 
			"ORDER BY `product_faily_sub_product_name` ASC",nativeQuery=true)
	java.util.List<String> getProductSubFamilyList();

}
