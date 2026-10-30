package com.pratikshat.exicom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.ProductConsumptionType;
@Repository
public interface ProductConsumptionTypeRepository extends JpaRepository<ProductConsumptionType, String> {

	List<ProductConsumptionType> findAll();
	
	@Query("select pct from ProductConsumptionType pct where pct.consumptionTypeName=:name")
	public ProductConsumptionType getProductConsumptionTypeBtId(String name);
	
}
