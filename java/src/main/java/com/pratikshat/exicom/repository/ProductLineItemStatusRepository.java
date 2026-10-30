package com.pratikshat.exicom.repository;

import java.util.List;

import javax.persistence.Entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.ProductLineItemStatus;

@Repository
public interface ProductLineItemStatusRepository extends JpaRepository<ProductLineItemStatus, Long> {

	List<ProductLineItemStatus> findAll();
}
