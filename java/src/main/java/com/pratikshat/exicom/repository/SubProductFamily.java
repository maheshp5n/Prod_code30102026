package com.pratikshat.exicom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.ProductFamilySubProduct;
@Repository
public interface SubProductFamily extends JpaRepository<ProductFamilySubProduct, Long> {

}
