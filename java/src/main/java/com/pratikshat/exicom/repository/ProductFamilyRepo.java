package com.pratikshat.exicom.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pratikshat.exicom.entity.ProductFamily;
@Repository
public interface ProductFamilyRepo extends JpaRepository<ProductFamily, Long> {
	@Transactional(readOnly = true)
	@Query("select new com.pratikshat.exicom.model.request.ProductFamily(p.productFamilyId,p.productFamilyName) from ProductFamily p")
	List<com.pratikshat.exicom.model.request.ProductFamily> getByAll();
	@Transactional(readOnly = true)
	@Query("select new com.pratikshat.exicom.model.request.ProductFamily(p.productFamilyId,p.productFamilyName) from ProductFamily p where p.productFamilyId=:productFamilyId")
	com.pratikshat.exicom.model.request.ProductFamily getByProductFamilyId(@Param("productFamilyId")long productFamilyId);
	@Transactional(readOnly=true)
	@Query("select new com.pratikshat.exicom.model.request.ProductFamily(p.productFamilySubProductId,p.productFailySubProductName) from ProductFamilySubProduct p join p.productFamily  where p.productFamily.productFamilyId=:productFamilyId")
	List<com.pratikshat.exicom.model.request.ProductFamily> getSubProductByProductFamilyId(@Param("productFamilyId")long productFamilyId);
}
