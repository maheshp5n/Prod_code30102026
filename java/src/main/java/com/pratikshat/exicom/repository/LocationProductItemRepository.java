package com.pratikshat.exicom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.Location;
import com.pratikshat.exicom.entity.LocationProductItem;
import com.pratikshat.exicom.entity.Products;
@Repository
public interface LocationProductItemRepository extends JpaRepository<LocationProductItem, Long> {
	LocationProductItem findByProductAndLocation(Products product,Location location);
}
