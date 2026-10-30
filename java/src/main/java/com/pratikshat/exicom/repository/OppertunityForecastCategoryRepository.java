package com.pratikshat.exicom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.OppertunityForecastCategory;

@Repository
public interface OppertunityForecastCategoryRepository extends JpaRepository<OppertunityForecastCategory, Long> {
	
	List<OppertunityForecastCategory> findAll();

}
