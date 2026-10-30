package com.pratikshat.exicom.repository;

import java.util.List;

import javax.persistence.Entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.ProductLineItemProductState;

@Repository
public interface ProductLineItemProductStateRepository extends JpaRepository<ProductLineItemProductState, Long> {

	List<ProductLineItemProductState> findAll();
}
