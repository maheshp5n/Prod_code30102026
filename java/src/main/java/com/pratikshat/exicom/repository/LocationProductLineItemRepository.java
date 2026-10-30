package com.pratikshat.exicom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

import com.pratikshat.exicom.entity.LocationProductItem;
import com.pratikshat.exicom.entity.LocationProductLineItems;
import com.pratikshat.exicom.entity.ProductLineItemProductState;
import com.pratikshat.exicom.entity.Products;
@Repository
public interface LocationProductLineItemRepository extends JpaRepository<LocationProductLineItems, Long> {
	int countByLocationProductItem_ProductAndItemSerialNumber(Products product,String serialNumber);
	@Query("select litem from LocationProductLineItems litem join litem.locationProductItem item join item.location loc join item.product prd where litem.itemSerialNumber=:itemSerialNumber and item.product=:product")
	LocationProductLineItems findByItemSerialNumberAndLocationProductItem_product(@Param("itemSerialNumber")String itemSerialNumber,@Param("product")Products product);
	
	List<LocationProductLineItems> findByLocationProductItemAndLocationProeuctLineItemStateAndCustomerInventory(LocationProductItem locationProductItem,ProductLineItemProductState status,boolean customerInventory);
}
