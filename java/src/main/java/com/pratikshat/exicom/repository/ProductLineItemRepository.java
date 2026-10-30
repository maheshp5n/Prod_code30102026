package com.pratikshat.exicom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.ProductLineItem;
import com.pratikshat.exicom.entity.Products;

@Repository
public interface ProductLineItemRepository extends JpaRepository<ProductLineItem, Long> {
	public static final String ATTACHED_TO_ASSETS="ATTACHED_TO_ASSETS";
	public static final String AT_LOCATION="AT_LOCATION";
	public static final String IN_TRANSIT="IN_TRANSIT";
	public static final String AT_REPAIR="AT_REPAIR";
	public static final String ASSIGNED_TO_ENGINEER_FOR_REPAIR="ASSIGNED_TO_ENGINEER_FOR_REPAIR";
	public static final String ASSETS_OUT="ASSETS_OUT";
	
	
	ProductLineItem findByProductAndSerialNumber(Products product,String serialNumber);
}
